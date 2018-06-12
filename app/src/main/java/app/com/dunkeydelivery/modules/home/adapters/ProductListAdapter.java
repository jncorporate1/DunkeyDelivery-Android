package app.com.dunkeydelivery.modules.home.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.modules.home.items.ProductBO;
import app.com.dunkeydelivery.utils.ImageUtils;

/**
 * Created by Developer on 6/2/2017.
 */

public class ProductListAdapter extends RecyclerView.Adapter {
    private List<ProductBO> dataSet;
    Context mContext;
    int total_types;
    public final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int visibleThreshold = 2;
    private boolean isLoading;

    //constructor...
    public ProductListAdapter(List<ProductBO> data, Context context, RecyclerView mRecyclerView) {
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

        TextView tvTitle;
        ImageView ivItem;
        TextView tvDescription;
        TextView tvPrice;
        TextView tvPopular;
        View mParentFrame;


        public ItemTypeViewHolder(View itemView) {
            super(itemView);
            this.mParentFrame = itemView.findViewById(R.id.ll_root);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvDescription = (TextView) itemView.findViewById(R.id.tv_sub_title);
            this.tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            this.ivItem = (ImageView) itemView.findViewById(R.id.iv_item);
            this.tvPopular = (TextView) itemView.findViewById(R.id.tv_popular);

            this.mParentFrame.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_root:
                    if (clickListener != null)
                        clickListener.onRowClick(getAdapterPosition(),v);
                    break;
            }
        }
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

    public void addItems(List<ProductBO> productBOs) {
        dataSet.addAll(productBOs);
        notifyDataSetChanged();
    }

    public void setItems(List<ProductBO> productBOs) {
        dataSet = productBOs;
        notifyDataSetChanged();
    }

    public List<ProductBO> getItems() {
        return dataSet;
    }

    public void removeLoadingItem() {
        try {
            if (dataSet.size() > 0) {
                dataSet.remove(dataSet.size() - 1);
                notifyItemRemoved(dataSet.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLoadingItem() {
        dataSet.add(null);
        notifyItemInserted(dataSet.size() - 1);
    }

    public void clearItems() {
        dataSet.clear();
        notifyDataSetChanged();
    }

    public ProductBO getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
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

        ProductBO itemBO = dataSet.get(listPosition);
        if (holder instanceof ItemTypeViewHolder) {
            ItemTypeViewHolder itemTypeViewHolder = (ItemTypeViewHolder) holder;
            itemTypeViewHolder.tvTitle.setText(itemBO.getName());
            itemTypeViewHolder.tvDescription.setText(itemBO.getDescription());

            itemTypeViewHolder.tvPrice.setText(mContext.getString(R.string.price) + " " + itemBO.getPrice());

            ImageUtils.setCenterImage(itemBO.getImage(), itemTypeViewHolder.ivItem, mContext, R.drawable.logo);

//            if(listPosition % 2 == 0){
//                itemTypeViewHolder.tvPopular.setVisibility(View.VISIBLE);
//            }else {
//                itemTypeViewHolder.tvPopular.setVisibility(View.GONE);
//            }

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
        void onRowClick(int position,View layoutReference);

    }

}
