package p003io.grpc.internal;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.common.base.Preconditions;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Queue;
import javax.annotation.Nullable;
import p003io.grpc.Decompressor;
import p003io.grpc.internal.MessageDeframer;
import p003io.grpc.internal.StreamListener;

/* renamed from: io.grpc.internal.ApplicationThreadDeframer */
public class ApplicationThreadDeframer implements Deframer, MessageDeframer.Listener {
    /* access modifiers changed from: private */
    public final MessageDeframer deframer;
    /* access modifiers changed from: private */
    public final Queue<InputStream> messageReadQueue = new ArrayDeque();
    /* access modifiers changed from: private */
    public final MessageDeframer.Listener storedListener;
    private final TransportExecutor transportExecutor;

    /* renamed from: io.grpc.internal.ApplicationThreadDeframer$TransportExecutor */
    interface TransportExecutor {
        void runOnTransportThread(Runnable runnable);
    }

    ApplicationThreadDeframer(MessageDeframer.Listener listener, TransportExecutor transportExecutor2, MessageDeframer deframer2) {
        this.storedListener = (MessageDeframer.Listener) Preconditions.checkNotNull(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        this.transportExecutor = (TransportExecutor) Preconditions.checkNotNull(transportExecutor2, "transportExecutor");
        deframer2.setListener(this);
        this.deframer = deframer2;
    }

    public void setMaxInboundMessageSize(int messageSize) {
        this.deframer.setMaxInboundMessageSize(messageSize);
    }

    public void setDecompressor(Decompressor decompressor) {
        this.deframer.setDecompressor(decompressor);
    }

    public void setFullStreamDecompressor(GzipInflatingBuffer fullStreamDecompressor) {
        this.deframer.setFullStreamDecompressor(fullStreamDecompressor);
    }

    public void request(final int numMessages) {
        this.storedListener.messagesAvailable(new InitializingMessageProducer(new Runnable() {
            public void run() {
                if (!ApplicationThreadDeframer.this.deframer.isClosed()) {
                    try {
                        ApplicationThreadDeframer.this.deframer.request(numMessages);
                    } catch (Throwable t) {
                        ApplicationThreadDeframer.this.storedListener.deframeFailed(t);
                        ApplicationThreadDeframer.this.deframer.close();
                    }
                }
            }
        }));
    }

    public void deframe(final ReadableBuffer data) {
        this.storedListener.messagesAvailable(new InitializingMessageProducer(new Runnable() {
            public void run() {
                try {
                    ApplicationThreadDeframer.this.deframer.deframe(data);
                } catch (Throwable t) {
                    ApplicationThreadDeframer.this.deframeFailed(t);
                    ApplicationThreadDeframer.this.deframer.close();
                }
            }
        }));
    }

    public void closeWhenComplete() {
        this.storedListener.messagesAvailable(new InitializingMessageProducer(new Runnable() {
            public void run() {
                ApplicationThreadDeframer.this.deframer.closeWhenComplete();
            }
        }));
    }

    public void close() {
        this.deframer.stopDelivery();
        this.storedListener.messagesAvailable(new InitializingMessageProducer(new Runnable() {
            public void run() {
                ApplicationThreadDeframer.this.deframer.close();
            }
        }));
    }

    public void bytesRead(final int numBytes) {
        this.transportExecutor.runOnTransportThread(new Runnable() {
            public void run() {
                ApplicationThreadDeframer.this.storedListener.bytesRead(numBytes);
            }
        });
    }

    public void messagesAvailable(StreamListener.MessageProducer producer) {
        while (true) {
            InputStream next = producer.next();
            InputStream message = next;
            if (next != null) {
                this.messageReadQueue.add(message);
            } else {
                return;
            }
        }
    }

    public void deframerClosed(final boolean hasPartialMessage) {
        this.transportExecutor.runOnTransportThread(new Runnable() {
            public void run() {
                ApplicationThreadDeframer.this.storedListener.deframerClosed(hasPartialMessage);
            }
        });
    }

    public void deframeFailed(final Throwable cause) {
        this.transportExecutor.runOnTransportThread(new Runnable() {
            public void run() {
                ApplicationThreadDeframer.this.storedListener.deframeFailed(cause);
            }
        });
    }

    /* renamed from: io.grpc.internal.ApplicationThreadDeframer$InitializingMessageProducer */
    private class InitializingMessageProducer implements StreamListener.MessageProducer {
        private boolean initialized;
        private final Runnable runnable;

        private InitializingMessageProducer(Runnable runnable2) {
            this.initialized = false;
            this.runnable = runnable2;
        }

        private void initialize() {
            if (!this.initialized) {
                this.runnable.run();
                this.initialized = true;
            }
        }

        @Nullable
        public InputStream next() {
            initialize();
            return (InputStream) ApplicationThreadDeframer.this.messageReadQueue.poll();
        }
    }
}
