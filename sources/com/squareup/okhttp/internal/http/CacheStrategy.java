package com.squareup.okhttp.internal.http;

import com.google.common.net.HttpHeaders;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class CacheStrategy {
    public final Response cacheResponse;
    public final Request networkRequest;

    private CacheStrategy(Request networkRequest2, Response cacheResponse2) {
        this.networkRequest = networkRequest2;
        this.cacheResponse = cacheResponse2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0056, code lost:
        if (r3.cacheControl().isPrivate() == false) goto L_0x0059;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0059, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isCacheable(com.squareup.okhttp.Response r3, com.squareup.okhttp.Request r4) {
        /*
            int r0 = r3.code()
            r1 = 200(0xc8, float:2.8E-43)
            r2 = 0
            if (r0 == r1) goto L_0x005a
            r1 = 410(0x19a, float:5.75E-43)
            if (r0 == r1) goto L_0x005a
            r1 = 414(0x19e, float:5.8E-43)
            if (r0 == r1) goto L_0x005a
            r1 = 501(0x1f5, float:7.02E-43)
            if (r0 == r1) goto L_0x005a
            r1 = 203(0xcb, float:2.84E-43)
            if (r0 == r1) goto L_0x005a
            r1 = 204(0xcc, float:2.86E-43)
            if (r0 == r1) goto L_0x005a
            r1 = 307(0x133, float:4.3E-43)
            if (r0 == r1) goto L_0x0031
            r1 = 308(0x134, float:4.32E-43)
            if (r0 == r1) goto L_0x005a
            r1 = 404(0x194, float:5.66E-43)
            if (r0 == r1) goto L_0x005a
            r1 = 405(0x195, float:5.68E-43)
            if (r0 == r1) goto L_0x005a
            switch(r0) {
                case 300: goto L_0x005a;
                case 301: goto L_0x005a;
                case 302: goto L_0x0031;
                default: goto L_0x0030;
            }
        L_0x0030:
            goto L_0x0059
        L_0x0031:
            java.lang.String r0 = "Expires"
            java.lang.String r0 = r3.header(r0)
            if (r0 != 0) goto L_0x005b
            com.squareup.okhttp.CacheControl r0 = r3.cacheControl()
            int r0 = r0.maxAgeSeconds()
            r1 = -1
            if (r0 != r1) goto L_0x005b
            com.squareup.okhttp.CacheControl r0 = r3.cacheControl()
            boolean r0 = r0.isPublic()
            if (r0 != 0) goto L_0x005b
            com.squareup.okhttp.CacheControl r0 = r3.cacheControl()
            boolean r0 = r0.isPrivate()
            if (r0 == 0) goto L_0x0059
            goto L_0x005b
        L_0x0059:
            return r2
        L_0x005a:
        L_0x005b:
            com.squareup.okhttp.CacheControl r0 = r3.cacheControl()
            boolean r0 = r0.noStore()
            if (r0 != 0) goto L_0x0070
            com.squareup.okhttp.CacheControl r0 = r4.cacheControl()
            boolean r0 = r0.noStore()
            if (r0 != 0) goto L_0x0070
            r2 = 1
        L_0x0070:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.CacheStrategy.isCacheable(com.squareup.okhttp.Response, com.squareup.okhttp.Request):boolean");
    }

    public static class Factory {
        private int ageSeconds = -1;
        final Response cacheResponse;
        private String etag;
        private Date expires;
        private Date lastModified;
        private String lastModifiedString;
        final long nowMillis;
        private long receivedResponseMillis;
        final Request request;
        private long sentRequestMillis;
        private Date servedDate;
        private String servedDateString;

        public Factory(long nowMillis2, Request request2, Response cacheResponse2) {
            this.nowMillis = nowMillis2;
            this.request = request2;
            this.cacheResponse = cacheResponse2;
            if (cacheResponse2 != null) {
                Headers headers = cacheResponse2.headers();
                int size = headers.size();
                for (int i = 0; i < size; i++) {
                    String fieldName = headers.name(i);
                    String value = headers.value(i);
                    if (HttpHeaders.DATE.equalsIgnoreCase(fieldName)) {
                        this.servedDate = HttpDate.parse(value);
                        this.servedDateString = value;
                    } else if (HttpHeaders.EXPIRES.equalsIgnoreCase(fieldName)) {
                        this.expires = HttpDate.parse(value);
                    } else if (HttpHeaders.LAST_MODIFIED.equalsIgnoreCase(fieldName)) {
                        this.lastModified = HttpDate.parse(value);
                        this.lastModifiedString = value;
                    } else if (HttpHeaders.ETAG.equalsIgnoreCase(fieldName)) {
                        this.etag = value;
                    } else if (HttpHeaders.AGE.equalsIgnoreCase(fieldName)) {
                        this.ageSeconds = HeaderParser.parseSeconds(value, -1);
                    } else if (OkHeaders.SENT_MILLIS.equalsIgnoreCase(fieldName)) {
                        this.sentRequestMillis = Long.parseLong(value);
                    } else if (OkHeaders.RECEIVED_MILLIS.equalsIgnoreCase(fieldName)) {
                        this.receivedResponseMillis = Long.parseLong(value);
                    }
                }
            }
        }

        public CacheStrategy get() {
            CacheStrategy candidate = getCandidate();
            if (candidate.networkRequest == null || !this.request.cacheControl().onlyIfCached()) {
                return candidate;
            }
            return new CacheStrategy((Request) null, (Response) null);
        }

        /* JADX WARNING: type inference failed for: r14v0, types: [com.squareup.okhttp.internal.http.CacheStrategy$1, com.squareup.okhttp.Response] */
        /* JADX WARNING: type inference failed for: r14v1 */
        /* JADX WARNING: type inference failed for: r14v4 */
        private CacheStrategy getCandidate() {
            ? r14;
            if (this.cacheResponse == null) {
                return new CacheStrategy(this.request, (Response) null);
            }
            if (this.request.isHttps() && this.cacheResponse.handshake() == null) {
                return new CacheStrategy(this.request, (Response) null);
            }
            if (!CacheStrategy.isCacheable(this.cacheResponse, this.request)) {
                return new CacheStrategy(this.request, (Response) null);
            }
            CacheControl requestCaching = this.request.cacheControl();
            if (requestCaching.noCache()) {
                r14 = 0;
            } else if (hasConditions(this.request)) {
                CacheControl cacheControl = requestCaching;
                r14 = 0;
            } else {
                long ageMillis = cacheResponseAge();
                long freshMillis = computeFreshnessLifetime();
                if (requestCaching.maxAgeSeconds() != -1) {
                    freshMillis = Math.min(freshMillis, TimeUnit.SECONDS.toMillis((long) requestCaching.maxAgeSeconds()));
                }
                long minFreshMillis = 0;
                if (requestCaching.minFreshSeconds() != -1) {
                    minFreshMillis = TimeUnit.SECONDS.toMillis((long) requestCaching.minFreshSeconds());
                }
                long maxStaleMillis = 0;
                CacheControl responseCaching = this.cacheResponse.cacheControl();
                if (!responseCaching.mustRevalidate() && requestCaching.maxStaleSeconds() != -1) {
                    maxStaleMillis = TimeUnit.SECONDS.toMillis((long) requestCaching.maxStaleSeconds());
                }
                if (responseCaching.noCache() || ageMillis + minFreshMillis >= freshMillis + maxStaleMillis) {
                    Request.Builder conditionalRequestBuilder = this.request.newBuilder();
                    String str = this.etag;
                    if (str != null) {
                        conditionalRequestBuilder.header(HttpHeaders.IF_NONE_MATCH, str);
                    } else if (this.lastModified != null) {
                        conditionalRequestBuilder.header(HttpHeaders.IF_MODIFIED_SINCE, this.lastModifiedString);
                    } else if (this.servedDate != null) {
                        conditionalRequestBuilder.header(HttpHeaders.IF_MODIFIED_SINCE, this.servedDateString);
                    }
                    Request conditionalRequest = conditionalRequestBuilder.build();
                    return hasConditions(conditionalRequest) ? new CacheStrategy(conditionalRequest, this.cacheResponse) : new CacheStrategy(conditionalRequest, (Response) null);
                }
                Response.Builder builder = this.cacheResponse.newBuilder();
                if (ageMillis + minFreshMillis >= freshMillis) {
                    builder.addHeader(HttpHeaders.WARNING, "110 HttpURLConnection \"Response is stale\"");
                }
                if (ageMillis > 86400000 && isFreshnessLifetimeHeuristic()) {
                    builder.addHeader(HttpHeaders.WARNING, "113 HttpURLConnection \"Heuristic expiration\"");
                }
                CacheControl cacheControl2 = requestCaching;
                return new CacheStrategy((Request) null, builder.build());
            }
            return new CacheStrategy(this.request, r14);
        }

        private long computeFreshnessLifetime() {
            CacheControl responseCaching = this.cacheResponse.cacheControl();
            if (responseCaching.maxAgeSeconds() != -1) {
                return TimeUnit.SECONDS.toMillis((long) responseCaching.maxAgeSeconds());
            }
            if (this.expires != null) {
                Date date = this.servedDate;
                long delta = this.expires.getTime() - (date != null ? date.getTime() : this.receivedResponseMillis);
                if (delta > 0) {
                    return delta;
                }
                return 0;
            } else if (this.lastModified == null || this.cacheResponse.request().httpUrl().query() != null) {
                return 0;
            } else {
                Date date2 = this.servedDate;
                long delta2 = (date2 != null ? date2.getTime() : this.sentRequestMillis) - this.lastModified.getTime();
                if (delta2 > 0) {
                    return delta2 / 10;
                }
                return 0;
            }
        }

        private long cacheResponseAge() {
            Date date = this.servedDate;
            long j = 0;
            if (date != null) {
                j = Math.max(0, this.receivedResponseMillis - date.getTime());
            }
            long apparentReceivedAge = j;
            long receivedAge = this.ageSeconds != -1 ? Math.max(apparentReceivedAge, TimeUnit.SECONDS.toMillis((long) this.ageSeconds)) : apparentReceivedAge;
            long j2 = this.receivedResponseMillis;
            return receivedAge + (j2 - this.sentRequestMillis) + (this.nowMillis - j2);
        }

        private boolean isFreshnessLifetimeHeuristic() {
            return this.cacheResponse.cacheControl().maxAgeSeconds() == -1 && this.expires == null;
        }

        private static boolean hasConditions(Request request2) {
            return (request2.header(HttpHeaders.IF_MODIFIED_SINCE) == null && request2.header(HttpHeaders.IF_NONE_MATCH) == null) ? false : true;
        }
    }
}
