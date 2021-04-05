package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;

public interface SystemParameterOrBuilder extends MessageLiteOrBuilder {
    String getHttpHeader();

    ByteString getHttpHeaderBytes();

    String getName();

    ByteString getNameBytes();

    String getUrlQueryParameter();

    ByteString getUrlQueryParameterBytes();
}
