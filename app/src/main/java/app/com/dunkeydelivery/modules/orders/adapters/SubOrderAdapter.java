package app.com.dunkeydelivery.modules.orders.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.modules.orders.fragments.OrderDetail;
import app.com.dunkeydelivery.modules.orders.items.OrderHistory;
import app.com.dunkeydelivery.modules.orders.items.StoreOrder;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomRadioButton;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Developer on 3/22/2016.
 */
public class SubOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private OrderHistory orderHistory;
    private List<StoreOrder> storeOrders;
    private int mLastExpandedViewPosition = -1;
    public final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int visibleThreshold = 2;
    private RecyclerView recyclerView;
    private boolean isLoading;

    public SubOrderAdapter(Context context, OrderHistory orderHistory, RecyclerView recyclerView) {

        this.orderHistory = orderHistory;
        this.storeOrders = orderHistory.getStoreOrders();
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemCount() {

        if (storeOrders == null)
            return 0;
        return storeOrders.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vHolder, final int i) {

        if (vHolder instanceof SubOrderViewHolder) {

            final SubOrderViewHolder holder = (SubOrderViewHolder) vHolder;

            final int position = vHolder.getAdapterPosition();

            final StoreOrder storeOrder = storeOrders.get(position);

            ImageUtils.setCenterImage(storeOrder.getImageUrl(), holder.imv_logo, context, R.drawable.logo);
            holder.tv_title.setText(storeOrder.getStoreName());

            int orderStatus = storeOrder.getStatus();
            LogUtils.i("mess", "" + orderStatus);

            holder.tv_status.setText(EnumUtils.OrderStatus.getStatusText(orderStatus));

            holder.rb_init.setChecked(false);
            holder.rb_in_progress.setChecked(false);
            holder.rb_shipped.setChecked(false);
            holder.rb_delivered.setChecked(false);

//            holder.tv_status.setText(EnumUtils.OrderStatus.getStatusText(orderStatus));

            if (orderStatus >= 0 && orderStatus <= 2) {
                holder.rb_init.setChecked(true);
            } else if (orderStatus >= 3 && orderStatus <= 5) {
                holder.rb_init.setChecked(true);
                holder.rb_in_progress.setChecked(true);
            } else if (orderStatus >= 6 && orderStatus <= 7) {
                holder.rb_init.setChecked(true);
                holder.rb_in_progress.setChecked(true);
                holder.rb_shipped.setChecked(true);
            } else if (orderStatus >= 8) {
                holder.rb_init.setChecked(true);
                holder.rb_in_progress.setChecked(true);
                holder.rb_shipped.setChecked(true);
                holder.rb_delivered.setChecked(true);
            }

            holder.iv_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activities.gotoNextFragment(context, OrderDetail.newInstance(orderHistory, storeOrder));
                }
            });
        } else if (vHolder instanceof LoadingViewHolder)

        {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) vHolder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suborder_row, parent, false);
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

    public List<StoreOrder> getItems() {
        return storeOrders;
    }

    public StoreOrder getItem(int position) {
        return storeOrders.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return storeOrders.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public class SubOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imv_logo)
        ImageView imv_logo;

        @BindView(R.id.tv_title)
        CustomTextView tv_title;

        @BindView(R.id.iv_view)
        ImageView iv_view;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;

        @BindView(R.id.tv_status)
        CustomTextView tv_status;

        @BindView(R.id.rb_init)
        CustomRadioButton rb_init;
        @BindView(R.id.rb_in_progress)
        CustomRadioButton rb_in_progress;
        @BindView(R.id.rb_shipped)
        CustomRadioButton rb_shipped;
        @BindView(R.id.rb_delivered)
        CustomRadioButton rb_delivered;

        SubOrderViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            /*iv_view.setOnClickListener(this);
            iv_delete.setOnClickListener(this);*/
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            /*switch (id) {
                case R.id.iv_view:
                    if (clickListener != null) {
                        clickListener.onViewClick(getAdapterPosition());
                    }
                    break;

                case R.id.iv_delete:
                    if (clickListener != null) {
                        clickListener.onDeleteClick(getAdapterPosition());
                    }
                    break;
            }*/
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

