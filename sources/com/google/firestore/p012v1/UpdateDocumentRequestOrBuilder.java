package com.google.firestore.p012v1;

import com.google.protobuf.MessageLiteOrBuilder;

/* renamed from: com.google.firestore.v1.UpdateDocumentRequestOrBuilder */
public interface UpdateDocumentRequestOrBuilder extends MessageLiteOrBuilder {
    Precondition getCurrentDocument();

    Document getDocument();

    DocumentMask getMask();

    DocumentMask getUpdateMask();

    boolean hasCurrentDocument();

    boolean hasDocument();

    boolean hasMask();

    boolean hasUpdateMask();
}
