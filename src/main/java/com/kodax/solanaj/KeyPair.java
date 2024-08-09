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
import com.kodax.solanaj.util.TweetNaclFast;

public class KeyPair {
  private final TweetNaclFast.Signature.KeyPair keyPair;

  private static final int SECRET_KEY_LENGTH = 64;
  private static final int SEED_LENGTH = 32;

  private KeyPair(TweetNaclFast.Signature.KeyPair keyPair) {
    this.keyPair = keyPair;
  }

  public static KeyPair generate() {
    return new KeyPair(TweetNaclFast.Signature.keyPair());
  }

  public static KeyPair fromSecretKey(byte[] secret) {
    if (secret.length != SECRET_KEY_LENGTH) {
      throw new IllegalArgumentException("Invalid secret key length");
    }
    return new KeyPair(TweetNaclFast.Signature.keyPair_fromSecretKey(secret));
  }

  public static KeyPair fromSeed(byte[] seed) {
    if (seed.length != SEED_LENGTH) {
      throw new IllegalArgumentException("Invalid seed length");
    }
    return new KeyPair(TweetNaclFast.Signature.keyPair_fromSeed(seed));
  }

  public PublicKey getPublicKey() {
    return PublicKey.fromByte(this.keyPair.getPublicKey());
  }

  public byte[] getSecretKey() {
    return this.keyPair.getSecretKey();
  }

  public String sign(byte[] message) {
    TweetNaclFast.Signature signatureProvider =
        new TweetNaclFast.Signature(new byte[0], this.getSecretKey());
    byte[] signature = signatureProvider.detached(message);
    return Base58.encode(signature);
  }
}
