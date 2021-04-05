package com.google.firebase.database.core;

import com.google.firebase.database.core.view.QuerySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ZombieEventManager implements EventRegistrationZombieListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static ZombieEventManager defaultInstance = new ZombieEventManager();
    final HashMap<EventRegistration, List<EventRegistration>> globalEventRegistrations = new HashMap<>();

    private ZombieEventManager() {
    }

    public static ZombieEventManager getInstance() {
        return defaultInstance;
    }

    public void recordEventRegistration(EventRegistration registration) {
        synchronized (this.globalEventRegistrations) {
            List<EventRegistration> registrationList = this.globalEventRegistrations.get(registration);
            if (registrationList == null) {
                registrationList = new ArrayList<>();
                this.globalEventRegistrations.put(registration, registrationList);
            }
            registrationList.add(registration);
            if (!registration.getQuerySpec().isDefault()) {
                EventRegistration defaultRegistration = registration.clone(QuerySpec.defaultQueryAtPath(registration.getQuerySpec().getPath()));
                List<EventRegistration> registrationList2 = this.globalEventRegistrations.get(defaultRegistration);
                if (registrationList2 == null) {
                    registrationList2 = new ArrayList<>();
                    this.globalEventRegistrations.put(defaultRegistration, registrationList2);
                }
                registrationList2.add(registration);
            }
            registration.setIsUserInitiated(true);
            registration.setOnZombied(this);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0039, code lost:
        r3 = r7.clone(com.google.firebase.database.core.view.QuerySpec.defaultQueryAtPath(r7.getQuerySpec().getPath()));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void unRecordEventRegistration(com.google.firebase.database.core.EventRegistration r7) {
        /*
            r6 = this;
            java.util.HashMap<com.google.firebase.database.core.EventRegistration, java.util.List<com.google.firebase.database.core.EventRegistration>> r0 = r6.globalEventRegistrations
            monitor-enter(r0)
            r1 = 0
            java.util.HashMap<com.google.firebase.database.core.EventRegistration, java.util.List<com.google.firebase.database.core.EventRegistration>> r2 = r6.globalEventRegistrations     // Catch:{ all -> 0x0076 }
            java.lang.Object r2 = r2.get(r7)     // Catch:{ all -> 0x0076 }
            java.util.List r2 = (java.util.List) r2     // Catch:{ all -> 0x0076 }
            if (r2 == 0) goto L_0x002e
            r3 = 0
        L_0x000f:
            int r4 = r2.size()     // Catch:{ all -> 0x0076 }
            if (r3 >= r4) goto L_0x0023
            java.lang.Object r4 = r2.get(r3)     // Catch:{ all -> 0x0076 }
            if (r4 != r7) goto L_0x0020
            r1 = 1
            r2.remove(r3)     // Catch:{ all -> 0x0076 }
            goto L_0x0023
        L_0x0020:
            int r3 = r3 + 1
            goto L_0x000f
        L_0x0023:
            boolean r3 = r2.isEmpty()     // Catch:{ all -> 0x0076 }
            if (r3 == 0) goto L_0x002e
            java.util.HashMap<com.google.firebase.database.core.EventRegistration, java.util.List<com.google.firebase.database.core.EventRegistration>> r3 = r6.globalEventRegistrations     // Catch:{ all -> 0x0076 }
            r3.remove(r7)     // Catch:{ all -> 0x0076 }
        L_0x002e:
            com.google.firebase.database.core.view.QuerySpec r3 = r7.getQuerySpec()     // Catch:{ all -> 0x0076 }
            boolean r3 = r3.isDefault()     // Catch:{ all -> 0x0076 }
            if (r3 != 0) goto L_0x0074
            com.google.firebase.database.core.view.QuerySpec r3 = r7.getQuerySpec()     // Catch:{ all -> 0x0076 }
            com.google.firebase.database.core.Path r3 = r3.getPath()     // Catch:{ all -> 0x0076 }
            com.google.firebase.database.core.view.QuerySpec r3 = com.google.firebase.database.core.view.QuerySpec.defaultQueryAtPath(r3)     // Catch:{ all -> 0x0076 }
            com.google.firebase.database.core.EventRegistration r3 = r7.clone(r3)     // Catch:{ all -> 0x0076 }
            java.util.HashMap<com.google.firebase.database.core.EventRegistration, java.util.List<com.google.firebase.database.core.EventRegistration>> r4 = r6.globalEventRegistrations     // Catch:{ all -> 0x0076 }
            java.lang.Object r4 = r4.get(r3)     // Catch:{ all -> 0x0076 }
            java.util.List r4 = (java.util.List) r4     // Catch:{ all -> 0x0076 }
            r2 = r4
            if (r2 == 0) goto L_0x0074
            r4 = 0
        L_0x0056:
            int r5 = r2.size()     // Catch:{ all -> 0x0076 }
            if (r4 >= r5) goto L_0x0069
            java.lang.Object r5 = r2.get(r4)     // Catch:{ all -> 0x0076 }
            if (r5 != r7) goto L_0x0066
            r2.remove(r4)     // Catch:{ all -> 0x0076 }
            goto L_0x0069
        L_0x0066:
            int r4 = r4 + 1
            goto L_0x0056
        L_0x0069:
            boolean r4 = r2.isEmpty()     // Catch:{ all -> 0x0076 }
            if (r4 == 0) goto L_0x0074
            java.util.HashMap<com.google.firebase.database.core.EventRegistration, java.util.List<com.google.firebase.database.core.EventRegistration>> r4 = r6.globalEventRegistrations     // Catch:{ all -> 0x0076 }
            r4.remove(r3)     // Catch:{ all -> 0x0076 }
        L_0x0074:
            monitor-exit(r0)     // Catch:{ all -> 0x0076 }
            return
        L_0x0076:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0076 }
            goto L_0x007a
        L_0x0079:
            throw r1
        L_0x007a:
            goto L_0x0079
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.core.ZombieEventManager.unRecordEventRegistration(com.google.firebase.database.core.EventRegistration):void");
    }

    public void zombifyForRemove(EventRegistration registration) {
        synchronized (this.globalEventRegistrations) {
            List<EventRegistration> registrationList = this.globalEventRegistrations.get(registration);
            if (registrationList != null && !registrationList.isEmpty()) {
                if (registration.getQuerySpec().isDefault()) {
                    HashSet<QuerySpec> zombiedQueries = new HashSet<>();
                    for (int i = registrationList.size() - 1; i >= 0; i--) {
                        EventRegistration currentRegistration = registrationList.get(i);
                        if (!zombiedQueries.contains(currentRegistration.getQuerySpec())) {
                            zombiedQueries.add(currentRegistration.getQuerySpec());
                            currentRegistration.zombify();
                        }
                    }
                } else {
                    registrationList.get(0).zombify();
                }
            }
        }
    }

    public void onZombied(EventRegistration zombiedInstance) {
        unRecordEventRegistration(zombiedInstance);
    }
}
