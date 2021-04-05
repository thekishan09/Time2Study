package p003io.grpc.internal;

import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import p003io.grpc.ChannelLogger;
import p003io.grpc.InternalChannelz;
import p003io.grpc.InternalLogId;

/* renamed from: io.grpc.internal.ChannelTracer */
final class ChannelTracer {
    static final Logger logger = Logger.getLogger(ChannelLogger.class.getName());
    private final long channelCreationTimeNanos;
    @Nullable
    private final Collection<InternalChannelz.ChannelTrace.Event> events;
    private int eventsLogged;
    private final Object lock = new Object();
    private final InternalLogId logId;

    static /* synthetic */ int access$008(ChannelTracer x0) {
        int i = x0.eventsLogged;
        x0.eventsLogged = i + 1;
        return i;
    }

    ChannelTracer(InternalLogId logId2, final int maxEvents, long channelCreationTimeNanos2, String description) {
        Preconditions.checkNotNull(description, "description");
        this.logId = (InternalLogId) Preconditions.checkNotNull(logId2, "logId");
        if (maxEvents > 0) {
            this.events = new ArrayDeque<InternalChannelz.ChannelTrace.Event>() {
                public boolean add(InternalChannelz.ChannelTrace.Event event) {
                    if (size() == maxEvents) {
                        removeFirst();
                    }
                    ChannelTracer.access$008(ChannelTracer.this);
                    return super.add(event);
                }
            };
        } else {
            this.events = null;
        }
        this.channelCreationTimeNanos = channelCreationTimeNanos2;
        InternalChannelz.ChannelTrace.Event.Builder builder = new InternalChannelz.ChannelTrace.Event.Builder();
        reportEvent(builder.setDescription(description + " created").setSeverity(InternalChannelz.ChannelTrace.Event.Severity.CT_INFO).setTimestampNanos(channelCreationTimeNanos2).build());
    }

    /* renamed from: io.grpc.internal.ChannelTracer$2 */
    static /* synthetic */ class C23232 {
        static final /* synthetic */ int[] $SwitchMap$io$grpc$InternalChannelz$ChannelTrace$Event$Severity;

        static {
            int[] iArr = new int[InternalChannelz.ChannelTrace.Event.Severity.values().length];
            $SwitchMap$io$grpc$InternalChannelz$ChannelTrace$Event$Severity = iArr;
            try {
                iArr[InternalChannelz.ChannelTrace.Event.Severity.CT_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$grpc$InternalChannelz$ChannelTrace$Event$Severity[InternalChannelz.ChannelTrace.Event.Severity.CT_WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void reportEvent(InternalChannelz.ChannelTrace.Event event) {
        Level logLevel;
        int i = C23232.$SwitchMap$io$grpc$InternalChannelz$ChannelTrace$Event$Severity[event.severity.ordinal()];
        if (i == 1) {
            logLevel = Level.FINE;
        } else if (i != 2) {
            logLevel = Level.FINEST;
        } else {
            logLevel = Level.FINER;
        }
        traceOnly(event);
        logOnly(this.logId, logLevel, event.description);
    }

    /* access modifiers changed from: package-private */
    public boolean isTraceEnabled() {
        boolean z;
        synchronized (this.lock) {
            z = this.events != null;
        }
        return z;
    }

    /* access modifiers changed from: package-private */
    public void traceOnly(InternalChannelz.ChannelTrace.Event event) {
        synchronized (this.lock) {
            if (this.events != null) {
                this.events.add(event);
            }
        }
    }

    static void logOnly(InternalLogId logId2, Level logLevel, String msg) {
        if (logger.isLoggable(logLevel)) {
            LogRecord lr = new LogRecord(logLevel, "[" + logId2 + "] " + msg);
            lr.setLoggerName(logger.getName());
            lr.setSourceClassName(logger.getName());
            lr.setSourceMethodName("log");
            logger.log(lr);
        }
    }

    /* access modifiers changed from: package-private */
    public InternalLogId getLogId() {
        return this.logId;
    }

    /* access modifiers changed from: package-private */
    public void updateBuilder(InternalChannelz.ChannelStats.Builder builder) {
        synchronized (this.lock) {
            if (this.events != null) {
                int eventsLoggedSnapshot = this.eventsLogged;
                List<InternalChannelz.ChannelTrace.Event> eventsSnapshot = new ArrayList<>(this.events);
                builder.setChannelTrace(new InternalChannelz.ChannelTrace.Builder().setNumEventsLogged((long) eventsLoggedSnapshot).setCreationTimeNanos(this.channelCreationTimeNanos).setEvents(eventsSnapshot).build());
            }
        }
    }
}
