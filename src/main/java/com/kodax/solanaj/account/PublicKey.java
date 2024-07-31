package com.kodax.solanaj.account;

/*
 * Copyright (c) 2024 Korea Digital Asset
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of $Korea Digital Asset.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered
 * into with Korea Digital Asset.
 */

import java.util.Arrays;

import com.kodax.solanaj.Base58;

public class PublicKey {

  public static final int PUBLIC_KEY_LENGTH = 32;

  private byte[] pubkey;

  private PublicKey(byte[] pubkey) {
    this.pubkey = pubkey;
  }

  public static PublicKey fromByte(byte[] pubkey) {
    if (pubkey.length < PUBLIC_KEY_LENGTH) {
      throw new IllegalArgumentException("Invalid public key input");
    }

    return new PublicKey(pubkey);
  }

  public static PublicKey fromString(String pubkey) {
    if (pubkey.length() < PUBLIC_KEY_LENGTH) {
      throw new IllegalArgumentException("Invalid public key input");
    }

    return new PublicKey(Base58.decode(pubkey));
  }

  public byte[] toByteArray() {
    return pubkey;
  }

  public String toBase58() {
    return Base58.encode(pubkey);
  }

  public boolean equals(PublicKey pubkey) {
    if (pubkey == null) return false;
    return Arrays.equals(this.pubkey, pubkey.toByteArray());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (getClass() != o.getClass()) return false;
    PublicKey pubkey = (PublicKey) o;
    return equals(pubkey);
  }

  @Override
  public String toString() {
    return toBase58();
  }
}
