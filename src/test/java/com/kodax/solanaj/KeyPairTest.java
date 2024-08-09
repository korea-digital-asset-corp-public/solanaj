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

public class KeyPairTest {

    private static final byte[] VALID_SEED = new byte[32];
    private static final byte[] VALID_SECRET_KEY = new byte[64];

    @Test
    public void testGenerate() {
        KeyPair keyPair = KeyPair.generate();
        assertNotNull(keyPair);
        assertNotNull(keyPair.getPublicKey());
        assertNotNull(keyPair.getSecretKey());
    }

    @Test
    public void testFromSecretKey() {
        // Use a valid secret key for testing
        KeyPair keyPair = KeyPair.fromSecretKey(VALID_SECRET_KEY);
        assertNotNull(keyPair);
        assertNotNull(keyPair.getPublicKey());
        assertArrayEquals(VALID_SECRET_KEY, keyPair.getSecretKey());
    }

    @Test
    public void testFromSeed() {
        // Use a valid seed for testing
        KeyPair keyPair = KeyPair.fromSeed(VALID_SEED);
        assertNotNull(keyPair);
        assertNotNull(keyPair.getPublicKey());
    }

    @Test
    public void testGetPublicKey() {
        KeyPair keyPair = KeyPair.generate();
        assertNotNull(keyPair.getPublicKey());
    }

    @Test
    public void testGetSecretKey() {
        KeyPair keyPair = KeyPair.generate();
        assertNotNull(keyPair.getSecretKey());
    }

    @Test
    public void testFromSecretKey_ThrowsIllegalArgumentExceptionWhenInvalidKeySize() {
        byte[] invalidSecretKey = new byte[VALID_SECRET_KEY.length - 1];
        assertThrows(IllegalArgumentException.class, () -> KeyPair.fromSecretKey(invalidSecretKey));
    }

    @Test
    public void testFromSeed_ThrowsIllegalArgumentExceptionWhenInvalidSeedSize() {
        byte[] invalidSeed = new byte[VALID_SEED.length - 1];
        assertThrows(IllegalArgumentException.class, () -> KeyPair.fromSeed(invalidSeed));
    }
}
