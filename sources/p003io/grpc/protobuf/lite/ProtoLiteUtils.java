package p003io.grpc.protobuf.lite;

import com.google.common.base.Preconditions;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.Reference;
import p003io.grpc.Metadata;
import p003io.grpc.MethodDescriptor;

/* renamed from: io.grpc.protobuf.lite.ProtoLiteUtils */
public final class ProtoLiteUtils {
    private static final int BUF_SIZE = 8192;
    static final int DEFAULT_MAX_MESSAGE_SIZE = 4194304;
    static volatile ExtensionRegistryLite globalRegistry = ExtensionRegistryLite.getEmptyRegistry();

    public static void setExtensionRegistry(ExtensionRegistryLite newRegistry) {
        globalRegistry = (ExtensionRegistryLite) Preconditions.checkNotNull(newRegistry, "newRegistry");
    }

    public static <T extends MessageLite> MethodDescriptor.Marshaller<T> marshaller(T defaultInstance) {
        return new MessageMarshaller(defaultInstance);
    }

    public static <T extends MessageLite> Metadata.BinaryMarshaller<T> metadataMarshaller(T defaultInstance) {
        return new MetadataMarshaller(defaultInstance);
    }

    static long copy(InputStream from, OutputStream to) throws IOException {
        Preconditions.checkNotNull(from, "inputStream cannot be null!");
        Preconditions.checkNotNull(to, "outputStream cannot be null!");
        byte[] buf = new byte[8192];
        long total = 0;
        while (true) {
            int r = from.read(buf);
            if (r == -1) {
                return total;
            }
            to.write(buf, 0, r);
            total += (long) r;
        }
    }

    private ProtoLiteUtils() {
    }

    /* renamed from: io.grpc.protobuf.lite.ProtoLiteUtils$MessageMarshaller */
    private static final class MessageMarshaller<T extends MessageLite> implements MethodDescriptor.PrototypeMarshaller<T> {
        private static final ThreadLocal<Reference<byte[]>> bufs = new ThreadLocal<>();
        private final T defaultInstance;
        private final Parser<T> parser;

        MessageMarshaller(T defaultInstance2) {
            this.defaultInstance = defaultInstance2;
            this.parser = defaultInstance2.getParserForType();
        }

        public Class<T> getMessageClass() {
            return this.defaultInstance.getClass();
        }

        public T getMessagePrototype() {
            return this.defaultInstance;
        }

        public InputStream stream(T value) {
            return new ProtoInputStream(value, this.parser);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:22:0x003c, code lost:
            if (r4.length >= r1) goto L_0x004b;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public T parse(java.io.InputStream r10) {
            /*
                r9 = this;
                boolean r0 = r10 instanceof p003io.grpc.protobuf.lite.ProtoInputStream
                if (r0 == 0) goto L_0x0018
                r0 = r10
                io.grpc.protobuf.lite.ProtoInputStream r0 = (p003io.grpc.protobuf.lite.ProtoInputStream) r0
                com.google.protobuf.Parser r1 = r0.parser()
                com.google.protobuf.Parser<T> r2 = r9.parser
                if (r1 != r2) goto L_0x0018
                r1 = r10
                io.grpc.protobuf.lite.ProtoInputStream r1 = (p003io.grpc.protobuf.lite.ProtoInputStream) r1     // Catch:{ IllegalStateException -> 0x0017 }
                com.google.protobuf.MessageLite r1 = r1.message()     // Catch:{ IllegalStateException -> 0x0017 }
                return r1
            L_0x0017:
                r1 = move-exception
            L_0x0018:
                r0 = 0
                boolean r1 = r10 instanceof p003io.grpc.KnownLength     // Catch:{ IOException -> 0x00ad }
                if (r1 == 0) goto L_0x0089
                int r1 = r10.available()     // Catch:{ IOException -> 0x00ad }
                if (r1 <= 0) goto L_0x0084
                r2 = 4194304(0x400000, float:5.877472E-39)
                if (r1 > r2) goto L_0x0084
                java.lang.ThreadLocal<java.lang.ref.Reference<byte[]>> r2 = bufs     // Catch:{ IOException -> 0x00ad }
                java.lang.Object r2 = r2.get()     // Catch:{ IOException -> 0x00ad }
                java.lang.ref.Reference r2 = (java.lang.ref.Reference) r2     // Catch:{ IOException -> 0x00ad }
                r3 = r2
                if (r2 == 0) goto L_0x003e
                java.lang.Object r2 = r3.get()     // Catch:{ IOException -> 0x00ad }
                byte[] r2 = (byte[]) r2     // Catch:{ IOException -> 0x00ad }
                r4 = r2
                if (r2 == 0) goto L_0x003e
                int r2 = r4.length     // Catch:{ IOException -> 0x00ad }
                if (r2 >= r1) goto L_0x004b
            L_0x003e:
                byte[] r2 = new byte[r1]     // Catch:{ IOException -> 0x00ad }
                r4 = r2
                java.lang.ThreadLocal<java.lang.ref.Reference<byte[]>> r2 = bufs     // Catch:{ IOException -> 0x00ad }
                java.lang.ref.WeakReference r5 = new java.lang.ref.WeakReference     // Catch:{ IOException -> 0x00ad }
                r5.<init>(r4)     // Catch:{ IOException -> 0x00ad }
                r2.set(r5)     // Catch:{ IOException -> 0x00ad }
            L_0x004b:
                r2 = r1
            L_0x004c:
                if (r2 <= 0) goto L_0x005a
                int r5 = r1 - r2
                int r6 = r10.read(r4, r5, r2)     // Catch:{ IOException -> 0x00ad }
                r7 = -1
                if (r6 != r7) goto L_0x0058
                goto L_0x005a
            L_0x0058:
                int r2 = r2 - r6
                goto L_0x004c
            L_0x005a:
                if (r2 != 0) goto L_0x0063
                r5 = 0
                com.google.protobuf.CodedInputStream r5 = com.google.protobuf.CodedInputStream.newInstance(r4, r5, r1)     // Catch:{ IOException -> 0x00ad }
                r0 = r5
            L_0x0062:
                goto L_0x0089
            L_0x0063:
                int r5 = r1 - r2
                java.lang.RuntimeException r6 = new java.lang.RuntimeException     // Catch:{ IOException -> 0x00ad }
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00ad }
                r7.<init>()     // Catch:{ IOException -> 0x00ad }
                java.lang.String r8 = "size inaccurate: "
                r7.append(r8)     // Catch:{ IOException -> 0x00ad }
                r7.append(r1)     // Catch:{ IOException -> 0x00ad }
                java.lang.String r8 = " != "
                r7.append(r8)     // Catch:{ IOException -> 0x00ad }
                r7.append(r5)     // Catch:{ IOException -> 0x00ad }
                java.lang.String r7 = r7.toString()     // Catch:{ IOException -> 0x00ad }
                r6.<init>(r7)     // Catch:{ IOException -> 0x00ad }
                throw r6     // Catch:{ IOException -> 0x00ad }
            L_0x0084:
                if (r1 != 0) goto L_0x0062
                T r2 = r9.defaultInstance     // Catch:{ IOException -> 0x00ad }
                return r2
            L_0x0089:
                if (r0 != 0) goto L_0x0090
                com.google.protobuf.CodedInputStream r0 = com.google.protobuf.CodedInputStream.newInstance((java.io.InputStream) r10)
            L_0x0090:
                r1 = 2147483647(0x7fffffff, float:NaN)
                r0.setSizeLimit(r1)
                com.google.protobuf.MessageLite r1 = r9.parseFrom(r0)     // Catch:{ InvalidProtocolBufferException -> 0x009b }
                return r1
            L_0x009b:
                r1 = move-exception
                io.grpc.Status r2 = p003io.grpc.Status.INTERNAL
                java.lang.String r3 = "Invalid protobuf byte sequence"
                io.grpc.Status r2 = r2.withDescription(r3)
                io.grpc.Status r2 = r2.withCause(r1)
                io.grpc.StatusRuntimeException r2 = r2.asRuntimeException()
                throw r2
            L_0x00ad:
                r1 = move-exception
                java.lang.RuntimeException r2 = new java.lang.RuntimeException
                r2.<init>(r1)
                goto L_0x00b5
            L_0x00b4:
                throw r2
            L_0x00b5:
                goto L_0x00b4
            */
            throw new UnsupportedOperationException("Method not decompiled: p003io.grpc.protobuf.lite.ProtoLiteUtils.MessageMarshaller.parse(java.io.InputStream):com.google.protobuf.MessageLite");
        }

        private T parseFrom(CodedInputStream stream) throws InvalidProtocolBufferException {
            T message = (MessageLite) this.parser.parseFrom(stream, ProtoLiteUtils.globalRegistry);
            try {
                stream.checkLastTagWas(0);
                return message;
            } catch (InvalidProtocolBufferException e) {
                e.setUnfinishedMessage(message);
                throw e;
            }
        }
    }

    /* renamed from: io.grpc.protobuf.lite.ProtoLiteUtils$MetadataMarshaller */
    private static final class MetadataMarshaller<T extends MessageLite> implements Metadata.BinaryMarshaller<T> {
        private final T defaultInstance;

        MetadataMarshaller(T defaultInstance2) {
            this.defaultInstance = defaultInstance2;
        }

        public byte[] toBytes(T value) {
            return value.toByteArray();
        }

        public T parseBytes(byte[] serialized) {
            try {
                return (MessageLite) this.defaultInstance.getParserForType().parseFrom(serialized, ProtoLiteUtils.globalRegistry);
            } catch (InvalidProtocolBufferException ipbe) {
                throw new IllegalArgumentException(ipbe);
            }
        }
    }
}
