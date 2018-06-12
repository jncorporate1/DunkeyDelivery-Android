package app.com.dunkeydelivery.modules.home.tabs.laundry.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.modules.home.items.ProductBO;
import app.com.dunkeydelivery.utils.ImageUtils;

/**
 * Created by Developer on 6/2/2017.
 */

public class LaundryAdapter extends RecyclerView.Adapter {
    private List<ProductBO> dataSet;
    Context mContext;
    int total_types;
    public final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int visibleThreshold = 2;
    private boolean isLoading;

    //constructor...
    public LaundryAdapter(List<ProductBO> data, Context context, RecyclerView mRecyclerView) {
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

    public ArrayList<ProductBO> getProducts() {
        ArrayList<ProductBO> list = new ArrayList<>();
        for (ProductBO productBO : dataSet) {
            if (productBO.getQuantity() > 0) {
                list.add(productBO);
            }
        }
        return list;
    }

    class ItemTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tvTitle;
        TextView tvSubTitle;
        TextView tvQuantity;
        ImageButton ibIncrement;
        ImageButton ibDecrement;
        ImageView imageView;
        View mParentFrame;


        public ItemTypeViewHolder(View itemView) {
            super(itemView);
            this.mParentFrame = itemView.findViewById(R.id.ll_root);
            this.imageView = (ImageView) itemView.findViewById(R.id.imv_icon);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvQuantity = (TextView) itemView.findViewById(R.id.tv_display_count);
            this.ibIncrement = (ImageButton) itemView.findViewById(R.id.btn_increment);
            this.ibDecrement = (ImageButton) itemView.findViewById(R.id.btn_decrement);
            this.tvSubTitle = (TextView) itemView.findViewById(R.id.tv_subtitle);

            this.mParentFrame.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.parentLayout:
                    if (clickListener != null)
                        clickListener.onRowClick(getAdapterPosition());
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

    public void addItems(List<ProductBO> productBOS) {
        dataSet.addAll(productBOS);
        notifyDataSetChanged();
    }

    public List<ProductBO> getItems() {
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

    public ProductBO getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_laundry, parent, false);
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

        final ProductBO itemBO = dataSet.get(listPosition);

        if (holder instanceof ItemTypeViewHolder) {
            final ItemTypeViewHolder itemTypeViewHolder = (ItemTypeViewHolder) holder;
            itemTypeViewHolder.tvTitle.setText(itemBO.getName());


            itemTypeViewHolder.tvQuantity.setText(itemBO.getQuantity() + "");

            if (itemBO.getQuantity() > 0) {
                ImageUtils.setImageCentered(mContext, itemBO.getImage_Selected(), itemTypeViewHolder.imageView);
                //itemTypeViewHolder.imageView.setImageResource(itemBO.getImage_Selected());
            } else {
                ImageUtils.setImageCentered(mContext, itemBO.getImage(), itemTypeViewHolder.imageView);
                //itemTypeViewHolder.imageView.setImageResource(itemBO.getImResourceId());
            }

            itemTypeViewHolder.ibIncrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = itemBO.getQuantity();
                    itemBO.setQuantity(quantity + 1);
                    dataSet.get(listPosition).setQuantity(quantity + 1);
                    itemTypeViewHolder.tvQuantity.setText(itemBO.getQuantity() + "");
                    ImageUtils.setImageCentered(mContext, itemBO.getImage_Selected(), itemTypeViewHolder.imageView);
                    //itemTypeViewHolder.imageView.setImageResource(itemBO.getImResourceIdSelected());
                    itemTypeViewHolder.tvQuantity.setTextColor(ContextCompat.getColor(mContext,
                            R.color.colorPrimaryDark));
                    itemTypeViewHolder.ibIncrement.setColorFilter(ContextCompat.getColor(mContext,
                            R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
//                    notifyItemChanged(listPosition);
                }
            });

            itemTypeViewHolder.ibDecrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = itemBO.getQuantity();
                    if (quantity > 0) {
                        itemBO.setQuantity(quantity - 1);
                        dataSet.get(listPosition).setQuantity(quantity - 1);
                        itemTypeViewHolder.tvQuantity.setText(itemBO.getQuantity() + "");
//                        notifyItemChanged(listPosition);
                        if (itemBO.getQuantity() == 0) {
                            ImageUtils.setImageCentered(mContext, itemBO.getImage(), itemTypeViewHolder.imageView);
                            /*itemTypeViewHolder.imageView.setImageResource(itemBO.getImResourceId());*/
                            itemTypeViewHolder.tvQuantity.setTextColor(ContextCompat.getColor(mContext,
                                    R.color.grey));
                            itemTypeViewHolder.ibIncrement.clearColorFilter();
                        }
                    }

                }
            });

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

    }

}
