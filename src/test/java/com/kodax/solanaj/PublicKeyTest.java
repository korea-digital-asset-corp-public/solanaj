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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.kodax.solanaj.util.Base58;

public class PublicKeyTest {

  private static final String VALID_BASE58_STRING = "3sezgDXgZpfiZCU7d6qG5NcYq8yrq8MXVwzZfpdB7f5A";
  private static final byte[] VALID_BYTE_ARRAY = Base58.decode(VALID_BASE58_STRING);

  @Test
  public void testFromByte() {
    PublicKey publicKey = PublicKey.fromByte(VALID_BYTE_ARRAY);
    assertNotNull(publicKey);
    assertArrayEquals(VALID_BYTE_ARRAY, publicKey.toByteArray());
  }

  @Test
  public void testFromByte_ThrowsIllegalArgumentExceptionWhenInvalidByteSize() {
    byte[] invalidByteArray = new byte[PublicKey.PUBLIC_KEY_LENGTH - 1];
    assertThrows(IllegalArgumentException.class, () -> PublicKey.fromByte(invalidByteArray));
  }

  @Test
  public void testFromString() {
    PublicKey publicKey = PublicKey.fromString(VALID_BASE58_STRING);
    assertNotNull(publicKey);
    assertEquals(VALID_BASE58_STRING, publicKey.toBase58());
  }

  @Test
  public void testFromString_ThrowsIllegalArgumentExceptionWhenInvalidBase58Format() {
    String invalidBase58String = "short";
    assertThrows(IllegalArgumentException.class, () -> PublicKey.fromString(invalidBase58String));
  }

  @Test
  public void testToByteArray() {
    PublicKey publicKey = PublicKey.fromByte(VALID_BYTE_ARRAY);
    assertArrayEquals(VALID_BYTE_ARRAY, publicKey.toByteArray());
  }

  @Test
  public void testToBase58() {
    PublicKey publicKey = PublicKey.fromByte(VALID_BYTE_ARRAY);
    assertEquals(VALID_BASE58_STRING, publicKey.toBase58());
  }

  @Test
  public void testEquals_When() {
    PublicKey publicKey = PublicKey.fromByte(VALID_BYTE_ARRAY);
    assertTrue(publicKey.equals(publicKey));
  }

  @Test
  public void testEquals_WhenPublicKeyIsFromSameByte() {
    PublicKey publicKey1 = PublicKey.fromByte(VALID_BYTE_ARRAY);
    PublicKey publicKey2 = PublicKey.fromByte(VALID_BYTE_ARRAY);
    assertTrue(publicKey1.equals(publicKey2));
  }

  @Test
  public void testEquals_WhenDifferentPublicKeys() {
    byte[] differentByteArray = new byte[PublicKey.PUBLIC_KEY_LENGTH];
    differentByteArray[0] = 1;
    PublicKey publicKey1 = PublicKey.fromByte(VALID_BYTE_ARRAY);
    PublicKey publicKey2 = PublicKey.fromByte(differentByteArray);
    assertFalse(publicKey1.equals(publicKey2));
  }

  @Test
  public void testEquals_WhenNull() {
    PublicKey publicKey = PublicKey.fromByte(VALID_BYTE_ARRAY);
    assertFalse(publicKey.equals(null));
  }

  @Test
  public void testToString() {
    PublicKey publicKey = PublicKey.fromByte(VALID_BYTE_ARRAY);
    assertEquals(VALID_BASE58_STRING, publicKey.toString());
  }
}
