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

import static com.kodax.solanaj.util.ByteUtils.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.kodax.solanaj.AccountMeta;
import com.kodax.solanaj.Instruction;
import com.kodax.solanaj.KeyPair;
import com.kodax.solanaj.PublicKey;
import com.kodax.solanaj.util.Base58;

public class MessageTest {

  @Test
  public void testSerialize() throws Exception {
    PublicKey fromPublicKey = PublicKey.fromString("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo");
    PublicKey toPublicKey = PublicKey.fromString("GrDMoeqMLFjeXQ24H56S1RLgT4R76jsuWCd6SvXyGPQ5");
    int lamports = 3000;

    KeyPair keyPair =
        KeyPair.fromSecretKey(
            Base58.decode(
                "4Z7cXSyeFR8wNGMVXUE1TwtKn5D5Vu7FzEv69dokLv7KrQk7h6pu4LF8ZRR9yQBhc7uSM6RTTZtU1fmaxiNrxXrs"));

    ArrayList<AccountMeta> keys = new ArrayList<>();
    keys.add(new AccountMeta(fromPublicKey, true, true));
    keys.add(new AccountMeta(toPublicKey, false, true));

    byte[] data = new byte[4 + 8];
    uint32ToByteArrayLE(2, data, 0);
    int64ToByteArrayLE(lamports, data, 4);

    Message message =
        Message.compile(
            keyPair.getPublicKey(),
            List.of(
                new Instruction(
                    PublicKey.fromString("11111111111111111111111111111111"), keys, data)),
            "Eit7RCyhUixAe2hGBS8oqnw59QK3kgMMjfLME5bm9wRn");

    assertArrayEquals(
        new int[] {
          1, 0, 0, 3, 6, 26, 217, 208, 83, 135, 21, 72, 83, 126, 222, 62, 38, 24, 73, 163, 223, 183,
          253, 2, 250, 188, 117, 178, 35, 200, 228, 106, 219, 133, 61, 12, 235, 122, 188, 208, 216,
          117, 235, 194, 109, 161, 177, 129, 163, 51, 155, 62, 242, 163, 22, 149, 187, 122, 189,
          188, 103, 130, 115, 188, 173, 205, 229, 170, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 203, 226, 136, 193, 153, 148, 240, 50,
          230, 98, 9, 79, 221, 179, 243, 174, 90, 67, 104, 169, 6, 187, 165, 72, 36, 156, 19, 57,
          132, 38, 69, 245, 1, 2, 2, 0, 1, 12, 2, 0, 0, 0, 184, 11, 0, 0, 0, 0, 0, 0
        },
        toUnsignedByteArray(message.serialize()));
  }
}
