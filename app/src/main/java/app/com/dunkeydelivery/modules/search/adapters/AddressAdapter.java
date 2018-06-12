package app.com.dunkeydelivery.modules.search.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.modules.search.items.AddressItem;

/**
 * Created by Developer on 6/2/2017.
 */

public class AddressAdapter extends RecyclerView.Adapter {
    private List<AddressItem> dataSet;
    Context mContext;
    int total_types;
    public final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int visibleThreshold = 2;
    private boolean isLoading;

    //constructor...
    public AddressAdapter(List<AddressItem> data, Context context, RecyclerView mRecyclerView) {
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


        TextView tvAddressLine1;
        TextView tvAddressLine2;
        View mParentFrame;


        public ItemTypeViewHolder(View itemView) {
            super(itemView);
            this.mParentFrame = itemView.findViewById(R.id.ll_root);
            tvAddressLine1 = (TextView) itemView.findViewById(R.id.text_store_address);
            tvAddressLine2 = (TextView) itemView.findViewById(R.id.tv_city);

            this.mParentFrame.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_root:
                    if(clickListener != null)
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

    public void addItems(List<AddressItem> streamBOs) {
        dataSet.addAll(streamBOs);
        notifyDataSetChanged();
    }

    public List<AddressItem> getItems() {
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
        notifyDataSetChanged();
    }

    public AddressItem getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
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

        AddressItem address = dataSet.get(listPosition);
        if(holder instanceof ItemTypeViewHolder){
            ItemTypeViewHolder itemTypeViewHolder = (ItemTypeViewHolder) holder;
            String addressLine1 = "", addressLine2 = "";

            //In address line 1
            itemTypeViewHolder.tvAddressLine1.setText(address.getAddressLine1());

            //In address line 2
            //City
            if (address.getCity() != null && !address.getCity().isEmpty()) {
                addressLine2 = address.getCity() + "";
            }

            //State
            if (address.getStateCode() != null && !address.getStateCode().isEmpty()) {

                if (addressLine2.isEmpty()) {
                    addressLine2 = address.getStateCode();
                } else {
                    addressLine2 = addressLine2 + ", " + address.getStateCode();
                }
            }

            //ZipCode
            if (address.getZipCode() != null && !address.getZipCode().isEmpty()) {

                if (addressLine2.isEmpty()) {
                    addressLine2 = address.getZipCode();
                } else {
                    addressLine2 = addressLine2 + " " + address.getZipCode();
                }
            }

            if(addressLine2 != null && !addressLine2.isEmpty())
                itemTypeViewHolder.tvAddressLine2.setText(addressLine2);
            else
                itemTypeViewHolder.tvAddressLine2.setVisibility(View.GONE);

        }else if(holder instanceof LoadingViewHolder) {
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
