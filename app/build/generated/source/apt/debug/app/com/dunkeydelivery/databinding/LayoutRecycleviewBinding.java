package app.com.dunkeydelivery.databinding;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutRecycleviewBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ll_search, 1);
        sViewsWithIds.put(R.id.ib_back, 2);
        sViewsWithIds.put(R.id.et_search, 3);
        sViewsWithIds.put(R.id.ib_cancel, 4);
        sViewsWithIds.put(R.id.rv_swipe_refresh, 5);
        sViewsWithIds.put(R.id.recyclerView, 6);
        sViewsWithIds.put(R.id.tv_new_streams, 7);
        sViewsWithIds.put(R.id.tv_swipe_refresh, 8);
        sViewsWithIds.put(R.id.tv_noresult, 9);
    }
    // views
    @NonNull
    public final app.com.dunkeydelivery.utils.customviews.widgets.CustomEditText etSearch;
    @NonNull
    public final android.widget.ImageButton ibBack;
    @NonNull
    public final android.widget.ImageButton ibCancel;
    @NonNull
    public final android.widget.LinearLayout llSearch;
    @NonNull
    public final android.widget.LinearLayout parentLayout;
    @NonNull
    public final android.support.v7.widget.RecyclerView recyclerView;
    @NonNull
    public final android.support.v4.widget.SwipeRefreshLayout rvSwipeRefresh;
    @NonNull
    public final android.widget.TextView tvNewStreams;
    @NonNull
    public final app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView tvNoresult;
    @NonNull
    public final android.support.v4.widget.SwipeRefreshLayout tvSwipeRefresh;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutRecycleviewBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds);
        this.etSearch = (app.com.dunkeydelivery.utils.customviews.widgets.CustomEditText) bindings[3];
        this.ibBack = (android.widget.ImageButton) bindings[2];
        this.ibCancel = (android.widget.ImageButton) bindings[4];
        this.llSearch = (android.widget.LinearLayout) bindings[1];
        this.parentLayout = (android.widget.LinearLayout) bindings[0];
        this.parentLayout.setTag(null);
        this.recyclerView = (android.support.v7.widget.RecyclerView) bindings[6];
        this.rvSwipeRefresh = (android.support.v4.widget.SwipeRefreshLayout) bindings[5];
        this.tvNewStreams = (android.widget.TextView) bindings[7];
        this.tvNoresult = (app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView) bindings[9];
        this.tvSwipeRefresh = (android.support.v4.widget.SwipeRefreshLayout) bindings[8];
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    @NonNull
    public static LayoutRecycleviewBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static LayoutRecycleviewBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<LayoutRecycleviewBinding>inflate(inflater, app.com.dunkeydelivery.R.layout.layout_recycleview, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static LayoutRecycleviewBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static LayoutRecycleviewBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(app.com.dunkeydelivery.R.layout.layout_recycleview, null, false), bindingComponent);
    }
    @NonNull
    public static LayoutRecycleviewBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static LayoutRecycleviewBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/layout_recycleview_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new LayoutRecycleviewBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}