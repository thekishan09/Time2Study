package com.google.api;

import com.google.api.LabelDescriptor;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class LogDescriptor extends GeneratedMessageLite<LogDescriptor, Builder> implements LogDescriptorOrBuilder {
    /* access modifiers changed from: private */
    public static final LogDescriptor DEFAULT_INSTANCE;
    public static final int DESCRIPTION_FIELD_NUMBER = 3;
    public static final int DISPLAY_NAME_FIELD_NUMBER = 4;
    public static final int LABELS_FIELD_NUMBER = 2;
    public static final int NAME_FIELD_NUMBER = 1;
    private static volatile Parser<LogDescriptor> PARSER;
    private String description_ = "";
    private String displayName_ = "";
    private Internal.ProtobufList<LabelDescriptor> labels_ = emptyProtobufList();
    private String name_ = "";

    private LogDescriptor() {
    }

    public String getName() {
        return this.name_;
    }

    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.name_);
    }

    /* access modifiers changed from: private */
    public void setName(String value) {
        value.getClass();
        this.name_ = value;
    }

    /* access modifiers changed from: private */
    public void clearName() {
        this.name_ = getDefaultInstance().getName();
    }

    /* access modifiers changed from: private */
    public void setNameBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.name_ = value.toStringUtf8();
    }

    public List<LabelDescriptor> getLabelsList() {
        return this.labels_;
    }

    public List<? extends LabelDescriptorOrBuilder> getLabelsOrBuilderList() {
        return this.labels_;
    }

    public int getLabelsCount() {
        return this.labels_.size();
    }

    public LabelDescriptor getLabels(int index) {
        return (LabelDescriptor) this.labels_.get(index);
    }

    public LabelDescriptorOrBuilder getLabelsOrBuilder(int index) {
        return (LabelDescriptorOrBuilder) this.labels_.get(index);
    }

    private void ensureLabelsIsMutable() {
        if (!this.labels_.isModifiable()) {
            this.labels_ = GeneratedMessageLite.mutableCopy(this.labels_);
        }
    }

    /* access modifiers changed from: private */
    public void setLabels(int index, LabelDescriptor value) {
        value.getClass();
        ensureLabelsIsMutable();
        this.labels_.set(index, value);
    }

    /* access modifiers changed from: private */
    public void addLabels(LabelDescriptor value) {
        value.getClass();
        ensureLabelsIsMutable();
        this.labels_.add(value);
    }

    /* access modifiers changed from: private */
    public void addLabels(int index, LabelDescriptor value) {
        value.getClass();
        ensureLabelsIsMutable();
        this.labels_.add(index, value);
    }

    /* access modifiers changed from: private */
    public void addAllLabels(Iterable<? extends LabelDescriptor> values) {
        ensureLabelsIsMutable();
        AbstractMessageLite.addAll(values, this.labels_);
    }

    /* access modifiers changed from: private */
    public void clearLabels() {
        this.labels_ = emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void removeLabels(int index) {
        ensureLabelsIsMutable();
        this.labels_.remove(index);
    }

    public String getDescription() {
        return this.description_;
    }

    public ByteString getDescriptionBytes() {
        return ByteString.copyFromUtf8(this.description_);
    }

    /* access modifiers changed from: private */
    public void setDescription(String value) {
        value.getClass();
        this.description_ = value;
    }

    /* access modifiers changed from: private */
    public void clearDescription() {
        this.description_ = getDefaultInstance().getDescription();
    }

    /* access modifiers changed from: private */
    public void setDescriptionBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.description_ = value.toStringUtf8();
    }

    public String getDisplayName() {
        return this.displayName_;
    }

    public ByteString getDisplayNameBytes() {
        return ByteString.copyFromUtf8(this.displayName_);
    }

    /* access modifiers changed from: private */
    public void setDisplayName(String value) {
        value.getClass();
        this.displayName_ = value;
    }

    /* access modifiers changed from: private */
    public void clearDisplayName() {
        this.displayName_ = getDefaultInstance().getDisplayName();
    }

    /* access modifiers changed from: private */
    public void setDisplayNameBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.displayName_ = value.toStringUtf8();
    }

    public static LogDescriptor parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (LogDescriptor) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static LogDescriptor parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (LogDescriptor) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static LogDescriptor parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (LogDescriptor) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static LogDescriptor parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (LogDescriptor) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static LogDescriptor parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (LogDescriptor) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static LogDescriptor parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (LogDescriptor) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static LogDescriptor parseFrom(InputStream input) throws IOException {
        return (LogDescriptor) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static LogDescriptor parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (LogDescriptor) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static LogDescriptor parseDelimitedFrom(InputStream input) throws IOException {
        return (LogDescriptor) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static LogDescriptor parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (LogDescriptor) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static LogDescriptor parseFrom(CodedInputStream input) throws IOException {
        return (LogDescriptor) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static LogDescriptor parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (LogDescriptor) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(LogDescriptor prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<LogDescriptor, Builder> implements LogDescriptorOrBuilder {
        /* synthetic */ Builder(C11901 x0) {
            this();
        }

        private Builder() {
            super(LogDescriptor.DEFAULT_INSTANCE);
        }

        public String getName() {
            return ((LogDescriptor) this.instance).getName();
        }

        public ByteString getNameBytes() {
            return ((LogDescriptor) this.instance).getNameBytes();
        }

        public Builder setName(String value) {
            copyOnWrite();
            ((LogDescriptor) this.instance).setName(value);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((LogDescriptor) this.instance).clearName();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            copyOnWrite();
            ((LogDescriptor) this.instance).setNameBytes(value);
            return this;
        }

        public List<LabelDescriptor> getLabelsList() {
            return Collections.unmodifiableList(((LogDescriptor) this.instance).getLabelsList());
        }

        public int getLabelsCount() {
            return ((LogDescriptor) this.instance).getLabelsCount();
        }

        public LabelDescriptor getLabels(int index) {
            return ((LogDescriptor) this.instance).getLabels(index);
        }

        public Builder setLabels(int index, LabelDescriptor value) {
            copyOnWrite();
            ((LogDescriptor) this.instance).setLabels(index, value);
            return this;
        }

        public Builder setLabels(int index, LabelDescriptor.Builder builderForValue) {
            copyOnWrite();
            ((LogDescriptor) this.instance).setLabels(index, (LabelDescriptor) builderForValue.build());
            return this;
        }

        public Builder addLabels(LabelDescriptor value) {
            copyOnWrite();
            ((LogDescriptor) this.instance).addLabels(value);
            return this;
        }

        public Builder addLabels(int index, LabelDescriptor value) {
            copyOnWrite();
            ((LogDescriptor) this.instance).addLabels(index, value);
            return this;
        }

        public Builder addLabels(LabelDescriptor.Builder builderForValue) {
            copyOnWrite();
            ((LogDescriptor) this.instance).addLabels((LabelDescriptor) builderForValue.build());
            return this;
        }

        public Builder addLabels(int index, LabelDescriptor.Builder builderForValue) {
            copyOnWrite();
            ((LogDescriptor) this.instance).addLabels(index, (LabelDescriptor) builderForValue.build());
            return this;
        }

        public Builder addAllLabels(Iterable<? extends LabelDescriptor> values) {
            copyOnWrite();
            ((LogDescriptor) this.instance).addAllLabels(values);
            return this;
        }

        public Builder clearLabels() {
            copyOnWrite();
            ((LogDescriptor) this.instance).clearLabels();
            return this;
        }

        public Builder removeLabels(int index) {
            copyOnWrite();
            ((LogDescriptor) this.instance).removeLabels(index);
            return this;
        }

        public String getDescription() {
            return ((LogDescriptor) this.instance).getDescription();
        }

        public ByteString getDescriptionBytes() {
            return ((LogDescriptor) this.instance).getDescriptionBytes();
        }

        public Builder setDescription(String value) {
            copyOnWrite();
            ((LogDescriptor) this.instance).setDescription(value);
            return this;
        }

        public Builder clearDescription() {
            copyOnWrite();
            ((LogDescriptor) this.instance).clearDescription();
            return this;
        }

        public Builder setDescriptionBytes(ByteString value) {
            copyOnWrite();
            ((LogDescriptor) this.instance).setDescriptionBytes(value);
            return this;
        }

        public String getDisplayName() {
            return ((LogDescriptor) this.instance).getDisplayName();
        }

        public ByteString getDisplayNameBytes() {
            return ((LogDescriptor) this.instance).getDisplayNameBytes();
        }

        public Builder setDisplayName(String value) {
            copyOnWrite();
            ((LogDescriptor) this.instance).setDisplayName(value);
            return this;
        }

        public Builder clearDisplayName() {
            copyOnWrite();
            ((LogDescriptor) this.instance).clearDisplayName();
            return this;
        }

        public Builder setDisplayNameBytes(ByteString value) {
            copyOnWrite();
            ((LogDescriptor) this.instance).setDisplayNameBytes(value);
            return this;
        }
    }

    /* renamed from: com.google.api.LogDescriptor$1 */
    static /* synthetic */ class C11901 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        static final /* synthetic */ int[] f284xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f284xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f284xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f284xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f284xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f284xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f284xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f284xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (C11901.f284xa1df5c61[method.ordinal()]) {
            case 1:
                return new LogDescriptor();
            case 2:
                return new Builder((C11901) null);
            case 3:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0001\u0000\u0001Ȉ\u0002\u001b\u0003Ȉ\u0004Ȉ", new Object[]{"name_", "labels_", LabelDescriptor.class, "description_", "displayName_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser<LogDescriptor> parser = PARSER;
                if (parser == null) {
                    synchronized (LogDescriptor.class) {
                        parser = PARSER;
                        if (parser == null) {
                            parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                            PARSER = parser;
                        }
                    }
                }
                return parser;
            case 6:
                return (byte) 1;
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    static {
        LogDescriptor defaultInstance = new LogDescriptor();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(LogDescriptor.class, defaultInstance);
    }

    public static LogDescriptor getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<LogDescriptor> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
