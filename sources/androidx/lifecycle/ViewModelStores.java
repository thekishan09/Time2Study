package androidx.lifecycle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

@Deprecated
public class ViewModelStores {
    private ViewModelStores() {
    }

    @Deprecated
    /* renamed from: of */
    public static ViewModelStore m19of(FragmentActivity activity) {
        return activity.getViewModelStore();
    }

    @Deprecated
    /* renamed from: of */
    public static ViewModelStore m18of(Fragment fragment) {
        return fragment.getViewModelStore();
    }
}
