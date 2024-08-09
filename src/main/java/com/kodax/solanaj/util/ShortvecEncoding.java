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

import java.util.ArrayList;
import java.util.List;

public class ShortvecEncoding {

    public static byte[] encode(int value) {
        List<Byte> bytes = new ArrayList<>();

        while (true) {
            int byteVal = value & 0x7F;
            value >>= 7;
            if (value == 0) {
                bytes.add((byte) byteVal);
                break;
            } else {
                byteVal |= 0x80;
                bytes.add((byte) byteVal);
            }
        }

        byte[] byteArray = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            byteArray[i] = bytes.get(i);
        }

        return byteArray;
    }
}