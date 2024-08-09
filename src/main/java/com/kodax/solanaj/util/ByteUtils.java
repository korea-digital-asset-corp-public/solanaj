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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteUtils {
    public static int[] toUnsignedByteArray(byte[] input) {
        int[] unsignedArray = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            unsignedArray[i] = input[i] & 0xFF;
        }
        return unsignedArray;
    }

    public static void uint32ToByteArrayLE(int value, byte[] array, int offset) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(value);
        System.arraycopy(buffer.array(), 0, array, offset, 4);
    }

    public static void int64ToByteArrayLE(long value, byte[] array, int offset) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(value);
        System.arraycopy(buffer.array(), 0, array, offset, 8);
    }
}
