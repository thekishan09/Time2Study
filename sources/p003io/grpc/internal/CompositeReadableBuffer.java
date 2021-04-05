package p003io.grpc.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Queue;

/* renamed from: io.grpc.internal.CompositeReadableBuffer */
public class CompositeReadableBuffer extends AbstractReadableBuffer {
    private final Queue<ReadableBuffer> buffers = new ArrayDeque();
    private int readableBytes;

    public void addBuffer(ReadableBuffer buffer) {
        if (!(buffer instanceof CompositeReadableBuffer)) {
            this.buffers.add(buffer);
            this.readableBytes += buffer.readableBytes();
            return;
        }
        CompositeReadableBuffer compositeBuffer = (CompositeReadableBuffer) buffer;
        while (!compositeBuffer.buffers.isEmpty()) {
            this.buffers.add(compositeBuffer.buffers.remove());
        }
        this.readableBytes += compositeBuffer.readableBytes;
        compositeBuffer.readableBytes = 0;
        compositeBuffer.close();
    }

    public int readableBytes() {
        return this.readableBytes;
    }

    public int readUnsignedByte() {
        ReadOperation op = new ReadOperation() {
            /* access modifiers changed from: package-private */
            public int readInternal(ReadableBuffer buffer, int length) {
                return buffer.readUnsignedByte();
            }
        };
        execute(op, 1);
        return op.value;
    }

    public void skipBytes(int length) {
        execute(new ReadOperation() {
            public int readInternal(ReadableBuffer buffer, int length) {
                buffer.skipBytes(length);
                return 0;
            }
        }, length);
    }

    public void readBytes(final byte[] dest, final int destOffset, int length) {
        execute(new ReadOperation() {
            int currentOffset = destOffset;

            public int readInternal(ReadableBuffer buffer, int length) {
                buffer.readBytes(dest, this.currentOffset, length);
                this.currentOffset += length;
                return 0;
            }
        }, length);
    }

    public void readBytes(final ByteBuffer dest) {
        execute(new ReadOperation() {
            public int readInternal(ReadableBuffer buffer, int length) {
                int prevLimit = dest.limit();
                ByteBuffer byteBuffer = dest;
                byteBuffer.limit(byteBuffer.position() + length);
                buffer.readBytes(dest);
                dest.limit(prevLimit);
                return 0;
            }
        }, dest.remaining());
    }

    public void readBytes(final OutputStream dest, int length) throws IOException {
        ReadOperation op = new ReadOperation() {
            public int readInternal(ReadableBuffer buffer, int length) throws IOException {
                buffer.readBytes(dest, length);
                return 0;
            }
        };
        execute(op, length);
        if (op.isError()) {
            throw op.f558ex;
        }
    }

    public CompositeReadableBuffer readBytes(int length) {
        checkReadable(length);
        this.readableBytes -= length;
        CompositeReadableBuffer newBuffer = new CompositeReadableBuffer();
        while (length > 0) {
            ReadableBuffer buffer = this.buffers.peek();
            if (buffer.readableBytes() > length) {
                newBuffer.addBuffer(buffer.readBytes(length));
                length = 0;
            } else {
                newBuffer.addBuffer(this.buffers.poll());
                length -= buffer.readableBytes();
            }
        }
        return newBuffer;
    }

    public void close() {
        while (!this.buffers.isEmpty()) {
            this.buffers.remove().close();
        }
    }

    private void execute(ReadOperation op, int length) {
        checkReadable(length);
        if (!this.buffers.isEmpty()) {
            advanceBufferIfNecessary();
        }
        while (length > 0 && !this.buffers.isEmpty()) {
            ReadableBuffer buffer = this.buffers.peek();
            int lengthToCopy = Math.min(length, buffer.readableBytes());
            op.read(buffer, lengthToCopy);
            if (!op.isError()) {
                length -= lengthToCopy;
                this.readableBytes -= lengthToCopy;
                advanceBufferIfNecessary();
            } else {
                return;
            }
        }
        if (length > 0) {
            throw new AssertionError("Failed executing read operation");
        }
    }

    private void advanceBufferIfNecessary() {
        if (this.buffers.peek().readableBytes() == 0) {
            this.buffers.remove().close();
        }
    }

    /* renamed from: io.grpc.internal.CompositeReadableBuffer$ReadOperation */
    private static abstract class ReadOperation {

        /* renamed from: ex */
        IOException f558ex;
        int value;

        /* access modifiers changed from: package-private */
        public abstract int readInternal(ReadableBuffer readableBuffer, int i) throws IOException;

        private ReadOperation() {
        }

        /* access modifiers changed from: package-private */
        public final void read(ReadableBuffer buffer, int length) {
            try {
                this.value = readInternal(buffer, length);
            } catch (IOException e) {
                this.f558ex = e;
            }
        }

        /* access modifiers changed from: package-private */
        public final boolean isError() {
            return this.f558ex != null;
        }
    }
}
