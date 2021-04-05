package com.google.firestore.p012v1;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;

/* renamed from: com.google.firestore.v1.BeginTransactionRequestOrBuilder */
public interface BeginTransactionRequestOrBuilder extends MessageLiteOrBuilder {
    String getDatabase();

    ByteString getDatabaseBytes();

    TransactionOptions getOptions();

    boolean hasOptions();
}
