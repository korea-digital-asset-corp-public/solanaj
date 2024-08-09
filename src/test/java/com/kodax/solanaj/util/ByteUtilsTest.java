package com.kodax.solanaj.util;

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

public class ByteUtilsTest {

  @Test
  public void testToUnsignedByteArray() {
    byte[] input = {-1, 0, 1, 127, -128};
    int[] expected = {255, 0, 1, 127, 128};

    int[] result = ByteUtils.toUnsignedByteArray(input);

    assertArrayEquals(
        expected, result, "The unsigned byte array does not match the expected values.");
  }

  @Test
  public void testUint32ToByteArrayLE() {
    int value = 16909060;
    byte[] expected = {4, 3, 2, 1};
    byte[] result = new byte[4];

    ByteUtils.uint32ToByteArrayLE(value, result, 0);

    assertArrayEquals(
        expected,
        result,
        "The uint32 value was not correctly converted to a little-endian byte array.");
  }

  @Test
  public void testInt64ToByteArrayLE() {
    long value = 72623859790382856L;
    byte[] expected = {8, 7, 6, 5, 4, 3, 2, 1};
    byte[] result = new byte[8];

    ByteUtils.int64ToByteArrayLE(value, result, 0);

    assertArrayEquals(
        expected,
        result,
        "The int64 value was not correctly converted to a little-endian byte array.");
  }

  @Test
  public void testUint32ToByteArrayLEWithOffset() {
    int value = 16909060;
    byte[] expected = {0, 0, 4, 3, 2, 1};
    byte[] result = new byte[6];

    ByteUtils.uint32ToByteArrayLE(value, result, 2);

    assertArrayEquals(
        expected,
        result,
        "The uint32 value was not correctly converted to a little-endian byte array with an offset.");
  }

  @Test
  public void testInt64ToByteArrayLEWithOffset() {
    long value = 72623859790382856L;
    byte[] expected = {0, 0, 8, 7, 6, 5, 4, 3, 2, 1};
    byte[] result = new byte[10];

    ByteUtils.int64ToByteArrayLE(value, result, 2);

    assertArrayEquals(
        expected,
        result,
        "The int64 value was not correctly converted to a little-endian byte array with an offset.");
  }
}
