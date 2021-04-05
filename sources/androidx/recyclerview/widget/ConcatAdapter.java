package androidx.recyclerview.widget;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ConcatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final String TAG = "ConcatAdapter";
    private final ConcatAdapterController mController;

    @SafeVarargs
    public ConcatAdapter(RecyclerView.Adapter<? extends RecyclerView.ViewHolder>... adapters) {
        this(Config.DEFAULT, adapters);
    }

    @SafeVarargs
    public ConcatAdapter(Config config, RecyclerView.Adapter<? extends RecyclerView.ViewHolder>... adapters) {
        this(config, (List<? extends RecyclerView.Adapter<? extends RecyclerView.ViewHolder>>) Arrays.asList(adapters));
    }

    public ConcatAdapter(List<? extends RecyclerView.Adapter<? extends RecyclerView.ViewHolder>> adapters) {
        this(Config.DEFAULT, adapters);
    }

    public ConcatAdapter(Config config, List<? extends RecyclerView.Adapter<? extends RecyclerView.ViewHolder>> adapters) {
        this.mController = new ConcatAdapterController(this, config);
        for (RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter : adapters) {
            addAdapter(adapter);
        }
        super.setHasStableIds(this.mController.hasStableIds());
    }

    public boolean addAdapter(RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter) {
        return this.mController.addAdapter(adapter);
    }

    public boolean addAdapter(int index, RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter) {
        return this.mController.addAdapter(index, adapter);
    }

    public boolean removeAdapter(RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter) {
        return this.mController.removeAdapter(adapter);
    }

    public int getItemViewType(int position) {
        return this.mController.getItemViewType(position);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return this.mController.onCreateViewHolder(parent, viewType);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        this.mController.onBindViewHolder(holder, position);
    }

    public void setHasStableIds(boolean hasStableIds) {
        throw new UnsupportedOperationException("Calling setHasStableIds is not allowed on the ConcatAdapter. Use the Config object passed in the constructor to control this behavior");
    }

    public void setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy strategy) {
        throw new UnsupportedOperationException("Calling setStateRestorationPolicy is not allowed on the ConcatAdapter. This value is inferred from added adapters");
    }

    public long getItemId(int position) {
        return this.mController.getItemId(position);
    }

    /* access modifiers changed from: package-private */
    public void internalSetStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy strategy) {
        super.setStateRestorationPolicy(strategy);
    }

    public int getItemCount() {
        return this.mController.getTotalCount();
    }

    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        return this.mController.onFailedToRecycleView(holder);
    }

    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        this.mController.onViewAttachedToWindow(holder);
    }

    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        this.mController.onViewDetachedFromWindow(holder);
    }

    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        this.mController.onViewRecycled(holder);
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.mController.onAttachedToRecyclerView(recyclerView);
    }

    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.mController.onDetachedFromRecyclerView(recyclerView);
    }

    public List<? extends RecyclerView.Adapter<? extends RecyclerView.ViewHolder>> getAdapters() {
        return Collections.unmodifiableList(this.mController.getCopyOfAdapters());
    }

    public int findRelativeAdapterPositionIn(RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter, RecyclerView.ViewHolder viewHolder, int globalPosition) {
        return this.mController.getLocalAdapterPosition(adapter, viewHolder, globalPosition);
    }

    public static final class Config {
        public static final Config DEFAULT = new Config(true, StableIdMode.NO_STABLE_IDS);
        public final boolean isolateViewTypes;
        public final StableIdMode stableIdMode;

        public enum StableIdMode {
            NO_STABLE_IDS,
            ISOLATED_STABLE_IDS,
            SHARED_STABLE_IDS
        }

        Config(boolean isolateViewTypes2, StableIdMode stableIdMode2) {
            this.isolateViewTypes = isolateViewTypes2;
            this.stableIdMode = stableIdMode2;
        }

        public static final class Builder {
            private boolean mIsolateViewTypes;
            private StableIdMode mStableIdMode = StableIdMode.NO_STABLE_IDS;

            public Builder setIsolateViewTypes(boolean isolateViewTypes) {
                this.mIsolateViewTypes = isolateViewTypes;
                return this;
            }

            public Builder setStableIdMode(StableIdMode stableIdMode) {
                this.mStableIdMode = stableIdMode;
                return this;
            }

            public Config build() {
                return new Config(this.mIsolateViewTypes, this.mStableIdMode);
            }
        }
    }
}
