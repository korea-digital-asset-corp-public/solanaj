package com.kodax.solanaj.message;

/*
 * Copyright (c) 2024 Korea Digital Asset
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Korea Digital Asset.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered
 * into with Korea Digital Asset.
 */

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.kodax.solanaj.AccountMeta;
import com.kodax.solanaj.Instruction;
import com.kodax.solanaj.PublicKey;
import com.kodax.solanaj.util.Base58;
import com.kodax.solanaj.util.ShortvecEncoding;

public class Message {
  private static final int PACKET_DATA_SIZE = 1280 - 40 - 8;

  private final String recentBlockHash;
  private final List<PublicKey> accountKeys;
  private final List<Instruction> instructions;
  private final MessageHeader header;

  private Message(
      MessageHeader header,
      String recentBlockHash,
      List<PublicKey> accountKeys,
      List<Instruction> instructions) {
    this.header = header;
    this.recentBlockHash = recentBlockHash;
    this.accountKeys = accountKeys;
    this.instructions = instructions;
  }

  public static Message compile(
      PublicKey payer, List<Instruction> instructions, String recentBlockHash) {
    List<PublicKey> accountKeys =
        Stream.concat(
                Stream.of(payer),
                instructions.stream()
                    .flatMap(
                        instruction ->
                            Stream.concat(
                                instruction.getKeys().stream().map(AccountMeta::getPublicKey),
                                Stream.of(instruction.getProgramId()))))
            .distinct()
            .toList();

    int numRequiredSignatures =
        (int)
            instructions.stream()
                .flatMap(instruction -> instruction.getKeys().stream())
                .filter(AccountMeta::isSigner)
                .distinct()
                .count();

    int numReadonlySignedAccounts =
        (int)
            instructions.stream()
                .flatMap(instruction -> instruction.getKeys().stream())
                .filter(meta -> meta.isSigner() && !meta.isWritable())
                .distinct()
                .count();

    int numReadonlyUnsignedAccounts =
        (int)
            instructions.stream()
                .flatMap(instruction -> instruction.getKeys().stream())
                .filter(meta -> !meta.isSigner() && !meta.isWritable())
                .distinct()
                .count();

    MessageHeader header =
        MessageHeader.of(
            (byte) numRequiredSignatures,
            (byte) numReadonlySignedAccounts,
            (byte) numReadonlyUnsignedAccounts);

    return new Message(header, recentBlockHash, accountKeys, instructions);
  }

  public byte[] serialize() {
    int numKeys = this.accountKeys.size();
    byte[] keyCount = ShortvecEncoding.encode(numKeys);
    List<byte[]> instructionBuffers = new ArrayList<>();
    for (Instruction instruction : instructions) {
      int programIdIndex = accountKeys.indexOf(instruction.getProgramId());
      List<Integer> accounts =
          instruction.getKeys().stream()
              .map(meta -> accountKeys.indexOf(meta.getPublicKey()))
              .toList();
      byte[] data = instruction.getData();

      byte[] keyIndicesCount = ShortvecEncoding.encode(accounts.size());
      byte[] dataCount = ShortvecEncoding.encode(data.length);

      ByteBuffer instructionBuffer =
          ByteBuffer.allocate(
              1 + keyIndicesCount.length + accounts.size() + dataCount.length + data.length);
      instructionBuffer.put((byte) programIdIndex);
      instructionBuffer.put(keyIndicesCount);
      for (int account : accounts) {
        instructionBuffer.put((byte) account);
      }
      instructionBuffer.put(dataCount);
      instructionBuffer.put(data);

      instructionBuffers.add(instructionBuffer.array());
    }

    byte[] instructionCount = ShortvecEncoding.encode(instructionBuffers.size());
    ByteBuffer instructionBuffer = ByteBuffer.allocate(PACKET_DATA_SIZE);
    instructionBuffer.put(instructionCount);
    for (byte[] buffer : instructionBuffers) {
      instructionBuffer.put(buffer);
    }

    instructionBuffer.flip();
    byte[] instructionBytes = new byte[instructionBuffer.limit()];
    instructionBuffer.get(instructionBytes);

    ByteBuffer signDataBuffer = ByteBuffer.allocate(2048);

    signDataBuffer.put(this.header.getNumRequiredSignatures());
    signDataBuffer.put(this.header.getNumReadonlySignedAccounts());
    signDataBuffer.put(this.header.getNumReadonlyUnsignedAccounts());
    signDataBuffer.put(keyCount);

    for (PublicKey key : accountKeys) {
      signDataBuffer.put(key.toByteArray());
    }

    signDataBuffer.put(Base58.decode(this.recentBlockHash));
    signDataBuffer.put(instructionBytes);

    signDataBuffer.flip();
    byte[] signData = new byte[signDataBuffer.limit()];
    signDataBuffer.get(signData);

    return signData;
  }
}
