package app.com.dunkeydelivery.modules.home.tabs.alcohol.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.items.StoreTag;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.items.StoreInfoItem;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.items.StoreTags;
import app.com.dunkeydelivery.utils.ImageUtils;

/**
 * Created by Developer on 6/2/2017.
 */

public class StoreInfoAdapter extends RecyclerView.Adapter {
    private List<StoreBO> dataSet;
    Context mContext;
    int total_types;
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_HEADER = 1;
    private final int VIEW_TYPE_LOADING = 2;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int visibleThreshold = 2;
    private boolean isLoading;

    //constructor...
    public StoreInfoAdapter(List<StoreBO> data, Context context, RecyclerView mRecyclerView) {
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

    class HeaderTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvChange;

        public HeaderTypeViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvChange = (TextView) itemView.findViewById(R.id.tv_change);

            this.tvChange.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_change:
                    if(clickListener != null)
                        clickListener.onChangeStoreClick(getAdapterPosition());
                    break;
            }
        }
    }

    class ItemTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tvTitle,tvSubtitle1,tvDistance,tvTime,tvRate,tvDeliveryFee;
        ImageView ivLogo;
        RatingBar ratingBar;
        FlowLayout flowLayout;
        Button btnReview;
        Button btnInfo;
        View mParentFrame;


        public ItemTypeViewHolder(View itemView) {
            super(itemView);
            this.mParentFrame = itemView.findViewById(R.id.ll_root);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.ratingBar = (RatingBar) itemView.findViewById(R.id.ratingbar);
            this.btnReview = (Button) itemView.findViewById(R.id.btn_review);
            this.flowLayout = (FlowLayout) itemView.findViewById(R.id.flow_layout);
            this.btnInfo = (Button) itemView.findViewById(R.id.btn_info);
            this.tvSubtitle1 = (TextView) itemView.findViewById(R.id.tv_subtitle1);
            this.tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);
            this.tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            this.ivLogo = (ImageView) itemView.findViewById(R.id.iv_logo);
            this.tvRate = (TextView) itemView.findViewById(R.id.tv_rate);
            this.tvDeliveryFee = (TextView) itemView.findViewById(R.id.tv_delivery_fee);

            this.mParentFrame.setOnClickListener(this);
            this.btnInfo.setOnClickListener(this);
            this.btnReview.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_root:
                    if(clickListener != null)
                        clickListener.onRowClick(getAdapterPosition());
                    break;

                case R.id.btn_review:
                    if(clickListener != null)
                        clickListener.onStoreReviewClick(getAdapterPosition());
                    break;

                case R.id.btn_info:
                    if(clickListener != null)
                        clickListener.onStoreInfoClick(getAdapterPosition());
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

    public void addItems(List<StoreBO> streamBOs) {
        dataSet.addAll(streamBOs);
        notifyDataSetChanged();
    }

    public List<StoreBO> getItems() {
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

    public StoreBO getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beer_storeinfo_header, parent, false);
                return new HeaderTypeViewHolder(view);
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beer_storeinfo, parent, false);
                return new ItemTypeViewHolder(view);
            case VIEW_TYPE_LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);

        }
        return null;


    }


    @Override
    public int getItemViewType(int position) {

        if(dataSet.get(position) == null){
            return VIEW_TYPE_LOADING;
        }else if(dataSet.get(position).isHeader()){
            return VIEW_TYPE_HEADER;
        }else{
            return VIEW_TYPE_ITEM;
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        StoreBO itemBO = dataSet.get(listPosition);
        if(holder instanceof HeaderTypeViewHolder){

            //set header content

            HeaderTypeViewHolder headerTypeViewHolder = (HeaderTypeViewHolder) holder;
            headerTypeViewHolder.tvTitle.setText(itemBO.getStoreName());

        }else if(holder instanceof ItemTypeViewHolder) {

            //set content
            ItemTypeViewHolder itemTypeViewHolder = (ItemTypeViewHolder) holder;
            itemTypeViewHolder.tvTitle.setText(itemBO.getBusinessName());
            itemTypeViewHolder.tvSubtitle1.setText(Html.fromHtml("<font color='#000000' size='12'>Min Order</font>"+" "+ Constants.CURRENCY_SYMBOL+Double.parseDouble(itemBO.getMinOrderPrice().substring(1))));
            itemTypeViewHolder.tvSubtitle1.setTextColor(mContext.getResources().getColor(R.color.grey4));
            itemTypeViewHolder.tvTime.setText(itemBO.getMinDeliveryTime());
            itemTypeViewHolder.tvDistance.setText(itemBO.getDistance()+" m");
            itemTypeViewHolder.ratingBar.setStar(itemBO.getAverageRating());
            itemTypeViewHolder.tvRate.setText(""+itemBO.getAverageRating());
            if(Integer.parseInt(itemBO.getMinDeliveryCharges())!=0) {
                itemTypeViewHolder.tvDeliveryFee.setText(Html.fromHtml("<font color='#000000' size='14'>Delivery Fee</font>" + " $" + Double.parseDouble(itemBO.getMinDeliveryCharges())));
                itemTypeViewHolder.tvDeliveryFee.setTextColor(mContext.getResources().getColor(R.color.grey4));
            }
            itemTypeViewHolder.ratingBar.setmClickable(false);
            ImageUtils.setCenterImage(itemBO.getImageUrlDummy(), itemTypeViewHolder.ivLogo, mContext);

            if(itemBO.getStoreTags()!=null) {
                itemTypeViewHolder.flowLayout.setVisibility(View.VISIBLE);
                addTags(itemTypeViewHolder.flowLayout, itemBO.getStoreTags());
            }
            else
            {
                itemTypeViewHolder.flowLayout.setVisibility(View.GONE);
            }

        }else if(holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    private void addTags(FlowLayout flowLayout, List<StoreTag> storeTags) {
        flowLayout.removeAllViews();

        for(int i=0; i<storeTags.size(); i++){
            View view =  LayoutInflater.from(mContext).inflate(R.layout.item_tag, null);

            TextView tvTag = (TextView) view.findViewById(R.id.tv_tag);
            tvTag.setText(storeTags.get(i).getTag());
            GradientDrawable bgShape = (GradientDrawable)tvTag.getBackground();
            bgShape.setColor(ContextCompat.getColor(mContext, ImageUtils.tagColors[i]));
            flowLayout.addView(view);
        }

    }

    public void removeItemFromPosition(int position)
    {
        dataSet.remove(position);
    }

    public void addSingleItem(StoreBO storeBO,int position)
    {
        dataSet.add(position,storeBO);
        notifyDataSetChanged();
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
        void onChangeStoreClick(int position);
        void onStoreReviewClick(int position);
        void onStoreInfoClick(int position);

    }

}
