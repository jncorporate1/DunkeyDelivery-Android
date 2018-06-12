package app.com.dunkeydelivery.modules.orders.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.modules.orders.items.StoreOrderItem;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Developer on 3/22/2016.
 */
public class OrderProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<StoreOrderItem> storeOrderItems;
    private int mLastExpandedViewPosition = -1;
    public final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int visibleThreshold = 2;
    private RecyclerView recyclerView;
    private boolean isLoading;

    public OrderProductsAdapter(Context context, List<StoreOrderItem> storeOrderItems, RecyclerView recyclerView) {

        this.storeOrderItems = storeOrderItems;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemCount() {

        if (storeOrderItems == null)
            return 0;
        return storeOrderItems.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vHolder, final int i) {

        if (vHolder instanceof SubOrderViewHolder) {

            SubOrderViewHolder holder = (SubOrderViewHolder) vHolder;
            int position = vHolder.getAdapterPosition();
            StoreOrderItem storeOrderItem = storeOrderItems.get(position);

            ImageUtils.setCenterImage(storeOrderItem.getImageUrl(), holder.iv_item, context, R.drawable.logo);
            holder.tv_title.setText(storeOrderItem.getName());
            String price = context.getString(R.string.price_dollar) + storeOrderItem.getPrice();
            holder.tv_price.setText(price);
            if (storeOrderItem.getQty() == 0) {
                holder.ll_quantity.setVisibility(View.GONE);
            } else {
                holder.ll_quantity.setVisibility(View.VISIBLE);
                holder.tv_quantity.setText(storeOrderItem.getQty() + "");
            }

        } else if (vHolder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) vHolder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
                return new SubOrderViewHolder(view);
            case VIEW_TYPE_LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);

        }
        return null;
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public List<StoreOrderItem> getItems() {
        return storeOrderItems;
    }

    public StoreOrderItem getItem(int position) {
        return storeOrderItems.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return storeOrderItems.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public class SubOrderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_item)
        ImageView iv_item;
        @BindView(R.id.tv_title)
        CustomTextView tv_title;
        @BindView(R.id.tv_price)
        CustomTextView tv_price;
        @BindView(R.id.ll_quantity)
        LinearLayout ll_quantity;
        @BindView(R.id.tv_quantity)
        CustomTextView tv_quantity;

        SubOrderViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    /*Listeners*/
    ClickListeners clickListener;

    public void setClickListener(ClickListeners clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListeners {
        void onRowClick(int position);

        void onViewClick(int position);

        void onDeleteClick(int position);
    }
}

