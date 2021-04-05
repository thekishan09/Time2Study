package com.firebase.p002ui.auth.data.model;

/* renamed from: com.firebase.ui.auth.data.model.Resource */
public final class Resource<T> {
    private final Exception mException;
    private boolean mIsUsed;
    private final State mState;
    private final T mValue;

    private Resource(State state, T value, Exception exception) {
        this.mState = state;
        this.mValue = value;
        this.mException = exception;
    }

    public static <T> Resource<T> forSuccess(T value) {
        return new Resource<>(State.SUCCESS, value, (Exception) null);
    }

    public static <T> Resource<T> forFailure(Exception e) {
        return new Resource<>(State.FAILURE, (Object) null, e);
    }

    public static <T> Resource<T> forLoading() {
        return new Resource<>(State.LOADING, (Object) null, (Exception) null);
    }

    public State getState() {
        return this.mState;
    }

    public final Exception getException() {
        this.mIsUsed = true;
        return this.mException;
    }

    public T getValue() {
        this.mIsUsed = true;
        return this.mValue;
    }

    public boolean isUsed() {
        return this.mIsUsed;
    }

    public boolean equals(Object o) {
        T t;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resource<?> resource = (Resource) o;
        if (this.mState == resource.mState && ((t = this.mValue) != null ? t.equals(resource.mValue) : resource.mValue == null)) {
            Exception exc = this.mException;
            if (exc == null) {
                if (resource.mException == null) {
                    return true;
                }
            } else if (exc.equals(resource.mException)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hashCode = this.mState.hashCode() * 31;
        T t = this.mValue;
        int i = 0;
        int result = (hashCode + (t == null ? 0 : t.hashCode())) * 31;
        Exception exc = this.mException;
        if (exc != null) {
            i = exc.hashCode();
        }
        return result + i;
    }

    public String toString() {
        return "Resource{mState=" + this.mState + ", mValue=" + this.mValue + ", mException=" + this.mException + '}';
    }
}
