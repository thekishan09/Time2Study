package com.google.android.youtube.player.internal;

import android.os.IBinder;
import com.google.android.youtube.player.internal.C1154u;
import java.lang.reflect.Field;

/* renamed from: com.google.android.youtube.player.internal.v */
public final class C1157v<T> extends C1154u.C1155a {

    /* renamed from: a */
    private final T f258a;

    private C1157v(T t) {
        this.f258a = t;
    }

    /* renamed from: a */
    public static <T> C1154u m367a(T t) {
        return new C1157v(t);
    }

    /* renamed from: a */
    public static <T> T m368a(C1154u uVar) {
        if (uVar instanceof C1157v) {
            return ((C1157v) uVar).f258a;
        }
        IBinder asBinder = uVar.asBinder();
        Field[] declaredFields = asBinder.getClass().getDeclaredFields();
        if (declaredFields.length == 1) {
            Field field = declaredFields[0];
            if (!field.isAccessible()) {
                field.setAccessible(true);
                try {
                    return field.get(asBinder);
                } catch (NullPointerException e) {
                    throw new IllegalArgumentException("Binder object is null.", e);
                } catch (IllegalArgumentException e2) {
                    throw new IllegalArgumentException("remoteBinder is the wrong class.", e2);
                } catch (IllegalAccessException e3) {
                    throw new IllegalArgumentException("Could not access the field in remoteBinder.", e3);
                }
            } else {
                throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly one declared *private* field for the wrapped object. Preferably, this is an instance of the ObjectWrapper<T> class.");
            }
        } else {
            throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly *one* declared private field for the wrapped object.  Preferably, this is an instance of the ObjectWrapper<T> class.");
        }
    }
}
