package com.kodax.solanaj;

/*
 * Copyright (c) 2024 Korea Digital Asset
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Korea Digital Asset.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered
 * into with Korea Digital Asset.
 */

import com.kodax.solanaj.util.Base58;
import com.kodax.solanaj.message.Message;
import com.kodax.solanaj.util.ShortvecEncoding;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Transaction {
    public static final int SIGNATURE_LENGTH = 64;

    private Message message;
    private final List<String> signatures;

    private Transaction(Message message) {
        this.message = message;
        this.signatures = new ArrayList<>();
    }

    public static Transaction of(
            Message message
    ) {
        return new Transaction(
                message
        );
    }

    public void sign(KeyPair keyPair) {
        byte[] serializedMessage = message.serialize();
        this.signatures.add(keyPair.sign(serializedMessage));
    }

    public void sign(List<KeyPair> keyPair) {
        byte[] serializedMessage = message.serialize();
        this.signatures.addAll(keyPair.stream().map(key -> key.sign(serializedMessage)).toList());
    }

    public byte[] serialize() {
        int signaturesSize = signatures.size();
        byte[] signaturesLength = ShortvecEncoding.encode(signaturesSize);
        byte[] serializedMessage = message.serialize();
        ByteBuffer out = ByteBuffer
                .allocate(signaturesLength.length + signaturesSize * SIGNATURE_LENGTH + serializedMessage.length);

        out.put(signaturesLength);
        for (String signature : signatures) {
            byte[] rawSignature = Base58.decode(signature);
            out.put(rawSignature);
        }

        out.put(serializedMessage);
        return out.array();
    }
}
