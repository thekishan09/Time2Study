package com.google.firestore.p012v1;

import com.google.firestore.p012v1.Precondition;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Timestamp;

/* renamed from: com.google.firestore.v1.PreconditionOrBuilder */
public interface PreconditionOrBuilder extends MessageLiteOrBuilder {
    Precondition.ConditionTypeCase getConditionTypeCase();

    boolean getExists();

    Timestamp getUpdateTime();

    boolean hasUpdateTime();
}
