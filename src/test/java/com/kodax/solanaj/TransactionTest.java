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

import com.kodax.solanaj.message.Message;
import com.kodax.solanaj.util.Base58;
import com.kodax.solanaj.util.ByteUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    private PublicKey fromPublicKey;
    private PublicKey toPublicKey;
    private KeyPair keyPair;
    private Message message;

    @BeforeEach
    public void setUp() {
        fromPublicKey = PublicKey.fromString("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo");
        toPublicKey = PublicKey.fromString("GrDMoeqMLFjeXQ24H56S1RLgT4R76jsuWCd6SvXyGPQ5");
        keyPair = KeyPair.fromSecretKey(Base58.decode("4Z7cXSyeFR8wNGMVXUE1TwtKn5D5Vu7FzEv69dokLv7KrQk7h6pu4LF8ZRR9yQBhc7uSM6RTTZtU1fmaxiNrxXrs"));

        // Create a message for testing
        ArrayList<AccountMeta> keys = new ArrayList<>();
        keys.add(new AccountMeta(fromPublicKey, true, true));
        keys.add(new AccountMeta(toPublicKey, false, true));

        byte[] data = new byte[4 + 8];
        ByteUtils.uint32ToByteArrayLE(2, data, 0);
        ByteUtils.int64ToByteArrayLE(3000, data, 4);

        message = Message.compile(
                keyPair.getPublicKey(),
                List.of(new Instruction(PublicKey.fromString("11111111111111111111111111111111"), keys, data)),
                "Eit7RCyhUixAe2hGBS8oqnw59QK3kgMMjfLME5bm9wRn"
        );
    }

    @Test
    public void testTransactionCreation() {
        Transaction transaction = Transaction.of(message);
        assertNotNull(transaction, "Transaction should be created.");
    }

    @Test
    public void testTransactionSigning() {
        Transaction transaction = Transaction.of(message);
        transaction.sign(keyPair);

        byte[] serializedTransaction = transaction.serialize();
        assertNotNull(serializedTransaction, "Serialized transaction should not be null.");
        assertTrue(serializedTransaction.length > 0, "Serialized transaction should have content.");
    }

    @Test
    public void testMultipleSignatures() {
        KeyPair anotherKeyPair = KeyPair.fromSecretKey(Base58.decode("3Z7cXSyeFR8wNGMVXUE1TwtKn5D5Vu7FzEv69dokLv7KrQk7h6pu4LF8ZRR9yQBhc7uSM6RTTZtU1fmaxiNrxXrs"));

        Transaction transaction = Transaction.of(message);
        transaction.sign(List.of(keyPair, anotherKeyPair));

        byte[] serializedTransaction = transaction.serialize();
        assertNotNull(serializedTransaction, "Serialized transaction should not be null.");
        assertTrue(serializedTransaction.length > 0, "Serialized transaction should have content.");

        int expectedSignaturesCount = 2;
        assertEquals(expectedSignaturesCount, transaction.getSignatures().size(), "The number of signatures should match the expected count.");
    }
}