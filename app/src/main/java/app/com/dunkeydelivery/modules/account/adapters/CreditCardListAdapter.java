package app.com.dunkeydelivery.modules.account.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.modules.account.Items.CardItem;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Developer on 6/2/2017.
 */
public class CreditCardListAdapter extends RecyclerView.Adapter {
    private List<CardItem> dataSet;
    Context mContext;
    int total_types;
    public final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int visibleThreshold = 2;
    private boolean isLoading;

    //constructor...
    public CreditCardListAdapter(List<CardItem> data, Context context, RecyclerView mRecyclerView) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    class ItemTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_subtitle)
        TextView tvSubTitle;
        @BindView(R.id.ll_root)
        View mParentFrame;
        @BindView(R.id.ib_delete)
        ImageButton ibDelete;
        @BindView(R.id.swipe)
        SwipeLayout swipeLayout;

        public ItemTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mParentFrame.setOnClickListener(this);
            ibDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_root:
                    if (clickListener != null)
                        clickListener.onRowClick(getAdapterPosition());
                    break;
                case R.id.ib_delete:
                    if(clickListener!=null)
                    {
                        clickListener.onRowDelete(getAdapterPosition(),swipeLayout);
                    }
                    break;
            }
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar1)
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setLoaded() {
        isLoading = false;
    }


    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public void addItems(List<CardItem> streamBOs) {
        dataSet.addAll(streamBOs);
        notifyDataSetChanged();
    }

    public List<CardItem> getItems() {
        return dataSet;
    }

    public void removeLoadingItem() {
        dataSet.remove(dataSet.size() - 1);
        notifyItemRemoved(dataSet.size());
    }

    public void addLoadingItem() {
        dataSet.add(null);
        notifyItemInserted(dataSet.size() - 1);
    }

    public void clearItems() {
        dataSet.clear();
    }

    public CardItem getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_creditcard_address, parent, false);
                return new ItemTypeViewHolder(view);
            case VIEW_TYPE_LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);

        }
        return null;


    }


    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        CardItem creditCardItem = dataSet.get(listPosition);
        if (holder instanceof ItemTypeViewHolder) {
            ItemTypeViewHolder itemTypeViewHolder = (ItemTypeViewHolder) holder;
            itemTypeViewHolder.tvTitle.setText(creditCardItem.getCardNumber());
            itemTypeViewHolder.tvSubTitle.setText(creditCardItem.getExpiryDate());

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    /*Listeners*/
    ClickListeners clickListener;

    public void setClickListener(ClickListeners clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListeners {
        void onRowClick(int position);

        void onRowDelete(int position,SwipeLayout swipeLayout);

    }

}
