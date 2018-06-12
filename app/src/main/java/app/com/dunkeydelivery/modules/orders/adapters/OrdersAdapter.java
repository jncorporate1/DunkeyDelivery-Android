package app.com.dunkeydelivery.modules.orders.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.andexert.expandablelayout.library.ExpandableLayout;

import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.modules.orders.items.OrderHistory;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Developer on 3/22/2016.
 */
public class OrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<OrderHistory> ordersBOs;
    private int mLastExpandedViewPosition = -1;
    public final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int visibleThreshold = 2;
    private RecyclerView recyclerView;
    private boolean isLoading;
    private String EntityId;

    public OrdersAdapter(Context context, List<OrderHistory> ordersBOs, RecyclerView recyclerView,String EntityId) {

        this.ordersBOs = ordersBOs;
        this.context = context;
        this.recyclerView = recyclerView;
        this.EntityId=EntityId;


        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItem, totalItemCount;
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ordersBOs.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vHolder, final int position) {

        if (vHolder instanceof OrderExpandViewHolder) {

            final OrderExpandViewHolder holder = (OrderExpandViewHolder) vHolder;

//            final int position = vHolder.getAdapterPosition();

//            OrderHistory orderHistory = ordersBOs.get(position);

            holder.el.setOnExpandChangeListener(null);
//            if(EntityId!=null) {
//                if (ordersBOs.get(position).getId() == Integer.parseInt( EntityId))
//                {
//                    ordersBOs.get(position).setExpanded(true);
//                }
//            }

            if (ordersBOs.get(position).isExpanded()) {
//            holder.tvHeader.setTextColor(ContextCompat.getColor(App.context, R.color.theme_color_shop));
                holder.el.show();
            } else {
//            holder.tvHeader.setTextColor(ContextCompat.getColor(App.context, R.color.black));
                holder.el.hide();
            }

            String title = context.getString(R.string.order_id) + " <b>" + ordersBOs.get(position).getId() + "</b>";
            holder.tv_order_id.setText(Html.fromHtml(title));

            holder.recyclerViewSubOrder.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            SubOrderAdapter subOrderAdapter = new SubOrderAdapter(context, ordersBOs.get(position), holder.recyclerViewSubOrder);
            holder.recyclerViewSubOrder.setAdapter(subOrderAdapter);

            holder.el.setOnExpandChangeListener(new ExpandableLayout.OnExpandChangeListener() {
                @Override
                public void elExpandChanged(boolean isExpanded) {
                    try {
                        if (isExpanded) {
                            if (recyclerView != null) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        recyclerView.smoothScrollToPosition(position);
                                    }
                                }, 200);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.ll_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*if (getLastExpandedViewPosition() != -1 && getLastExpandedViewPosition() != position && ordersBOs.get(getLastExpandedViewPosition()).isExpanded()) {
                        ordersBOs.get(getLastExpandedViewPosition()).setExpanded(false);
                        notifyItemChanged(getLastExpandedViewPosition());
                    }*/

                    if (holder.el.isOpened()) {
                        ordersBOs.get(position).setExpanded(false);
                        holder.el.hide();
                    } else {
                        setLastExpandedViewPosition(position);
                        ordersBOs.get(position).setExpanded(true);
                        holder.el.show();
                    }
                }
            });

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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_row, parent, false);
                return new OrderExpandViewHolder(view);
            case VIEW_TYPE_LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);

        }
        return null;
    }

    public void setItems(List<OrderHistory> ordersBOList) {
        ordersBOs = ordersBOList;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        ordersBOs.remove(position);
        notifyItemRemoved(position);
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

    public void addItems(List<OrderHistory> newOrdersBOs) {
        ordersBOs.addAll(newOrdersBOs);
        notifyDataSetChanged();
    }

    public List<OrderHistory> getItems() {
        return ordersBOs;
    }

    public void removeLoadingItem() {
        ordersBOs.remove(ordersBOs.size() - 1);
        notifyItemRemoved(ordersBOs.size());
    }

    public void addLoadingItem() {
        ordersBOs.add(null);
        notifyItemInserted(ordersBOs.size() - 1);
    }

    public void clearItems() {
        ordersBOs.clear();
    }

    public OrderHistory getItem(int position) {
        return ordersBOs.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return ordersBOs.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public int getLastExpandedViewPosition() {
        return mLastExpandedViewPosition;
    }

    public void setLastExpandedViewPosition(int lastPosition) {
        mLastExpandedViewPosition = lastPosition;
    }

    public class OrderExpandViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_order_id)
        CustomTextView tv_order_id;

        @BindView(R.id.ll_root)
        LinearLayout ll_root;

        @BindView(R.id.el)
        ExpandableLayout el;

        @BindView(R.id.recyclerViewSubOrder)
        RecyclerView recyclerViewSubOrder;

        OrderExpandViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            el.setArrow(itemView.findViewById(R.id.iv_arrow_up), itemView.findViewById(R.id.iv_arrow_dn));
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

    public interface ExpandableLayoutListener {
        public void onLayoutExpanded(int position);
    }
}

