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

import lombok.Getter;

@Getter
public class MessageHeader {
  static final int HEADER_LENGTH = 3;

  byte numRequiredSignatures = 0;
  byte numReadonlySignedAccounts = 0;
  byte numReadonlyUnsignedAccounts = 0;

  private MessageHeader(
      byte numRequiredSignatures,
      byte numReadonlySignedAccounts,
      byte numReadonlyUnsignedAccounts) {
    this.numRequiredSignatures = numRequiredSignatures;
    this.numReadonlySignedAccounts = numReadonlySignedAccounts;
    this.numReadonlyUnsignedAccounts = numReadonlyUnsignedAccounts;
  }

  public static MessageHeader of(
      byte numRequiredSignatures,
      byte numReadonlySignedAccounts,
      byte numReadonlyUnsignedAccounts) {
    return new MessageHeader(
        numRequiredSignatures, numReadonlySignedAccounts, numReadonlyUnsignedAccounts);
  }
}
