package androidx.recyclerview.widget;

import android.view.ViewGroup;
import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StableIdStorage;
import androidx.recyclerview.widget.ViewTypeStorage;

class NestedAdapterWrapper {
    public final RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    private RecyclerView.AdapterDataObserver mAdapterObserver = new RecyclerView.AdapterDataObserver() {
        public void onChanged() {
            NestedAdapterWrapper nestedAdapterWrapper = NestedAdapterWrapper.this;
            nestedAdapterWrapper.mCachedItemCount = nestedAdapterWrapper.adapter.getItemCount();
            NestedAdapterWrapper.this.mCallback.onChanged(NestedAdapterWrapper.this);
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            NestedAdapterWrapper.this.mCallback.onItemRangeChanged(NestedAdapterWrapper.this, positionStart, itemCount, (Object) null);
        }

        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            NestedAdapterWrapper.this.mCallback.onItemRangeChanged(NestedAdapterWrapper.this, positionStart, itemCount, payload);
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            NestedAdapterWrapper.this.mCachedItemCount += itemCount;
            NestedAdapterWrapper.this.mCallback.onItemRangeInserted(NestedAdapterWrapper.this, positionStart, itemCount);
            if (NestedAdapterWrapper.this.mCachedItemCount > 0 && NestedAdapterWrapper.this.adapter.getStateRestorationPolicy() == RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY) {
                NestedAdapterWrapper.this.mCallback.onStateRestorationPolicyChanged(NestedAdapterWrapper.this);
            }
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            NestedAdapterWrapper.this.mCachedItemCount -= itemCount;
            NestedAdapterWrapper.this.mCallback.onItemRangeRemoved(NestedAdapterWrapper.this, positionStart, itemCount);
            if (NestedAdapterWrapper.this.mCachedItemCount < 1 && NestedAdapterWrapper.this.adapter.getStateRestorationPolicy() == RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY) {
                NestedAdapterWrapper.this.mCallback.onStateRestorationPolicyChanged(NestedAdapterWrapper.this);
            }
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            boolean z = true;
            if (itemCount != 1) {
                z = false;
            }
            Preconditions.checkArgument(z, "moving more than 1 item is not supported in RecyclerView");
            NestedAdapterWrapper.this.mCallback.onItemRangeMoved(NestedAdapterWrapper.this, fromPosition, toPosition);
        }

        public void onStateRestorationPolicyChanged() {
            NestedAdapterWrapper.this.mCallback.onStateRestorationPolicyChanged(NestedAdapterWrapper.this);
        }
    };
    int mCachedItemCount;
    final Callback mCallback;
    private final StableIdStorage.StableIdLookup mStableIdLookup;
    private final ViewTypeStorage.ViewTypeLookup mViewTypeLookup;

    interface Callback {
        void onChanged(NestedAdapterWrapper nestedAdapterWrapper);

        void onItemRangeChanged(NestedAdapterWrapper nestedAdapterWrapper, int i, int i2);

        void onItemRangeChanged(NestedAdapterWrapper nestedAdapterWrapper, int i, int i2, Object obj);

        void onItemRangeInserted(NestedAdapterWrapper nestedAdapterWrapper, int i, int i2);

        void onItemRangeMoved(NestedAdapterWrapper nestedAdapterWrapper, int i, int i2);

        void onItemRangeRemoved(NestedAdapterWrapper nestedAdapterWrapper, int i, int i2);

        void onStateRestorationPolicyChanged(NestedAdapterWrapper nestedAdapterWrapper);
    }

    NestedAdapterWrapper(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter2, Callback callback, ViewTypeStorage viewTypeStorage, StableIdStorage.StableIdLookup stableIdLookup) {
        this.adapter = adapter2;
        this.mCallback = callback;
        this.mViewTypeLookup = viewTypeStorage.createViewTypeWrapper(this);
        this.mStableIdLookup = stableIdLookup;
        this.mCachedItemCount = this.adapter.getItemCount();
        this.adapter.registerAdapterDataObserver(this.mAdapterObserver);
    }

    /* access modifiers changed from: package-private */
    public void dispose() {
        this.adapter.unregisterAdapterDataObserver(this.mAdapterObserver);
        this.mViewTypeLookup.dispose();
    }

    /* access modifiers changed from: package-private */
    public int getCachedItemCount() {
        return this.mCachedItemCount;
    }

    /* access modifiers changed from: package-private */
    public int getItemViewType(int localPosition) {
        return this.mViewTypeLookup.localToGlobal(this.adapter.getItemViewType(localPosition));
    }

    /* access modifiers changed from: package-private */
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int globalViewType) {
        return this.adapter.onCreateViewHolder(parent, this.mViewTypeLookup.globalToLocal(globalViewType));
    }

    /* access modifiers changed from: package-private */
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int localPosition) {
        this.adapter.bindViewHolder(viewHolder, localPosition);
    }

    public long getItemId(int localPosition) {
        return this.mStableIdLookup.localToGlobal(this.adapter.getItemId(localPosition));
    }
}
