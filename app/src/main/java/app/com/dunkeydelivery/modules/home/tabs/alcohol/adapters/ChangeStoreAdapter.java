package app.com.dunkeydelivery.modules.home.tabs.alcohol.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.items.StoreTag;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;

/**
 * Created by Developer on 6/2/2017.
 */

public class ChangeStoreAdapter extends RecyclerView.Adapter {
    private List<StoreBO> dataSet;
    Context mContext;
    int total_types;
    int storeId;
    public final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int visibleThreshold = 2;
    private boolean isLoading;
    private boolean isAddTag = false; //just for alpha now...

    //constructor...
    public ChangeStoreAdapter(final List<StoreBO> data, Context context, RecyclerView mRecyclerView, boolean addTag,int storeId) {
        this.dataSet = data;
        this.storeId=storeId;
        this.mContext = context;
        total_types = dataSet.size();
        this.isAddTag = addTag;

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
        TextView tvSubTitle;
        TextView tvRate,tvDistance,tvTime,tvSubtitle1,tvDeliveryFee;
        RatingBar ratingBar;
        FlowLayout flowLayout;
        RadioButton radioButton;
        View mParentFrame;


        public ItemTypeViewHolder(View itemView) {
            super(itemView);
            this.mParentFrame = itemView.findViewById(R.id.ll_root);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvSubTitle = (TextView) itemView.findViewById(R.id.tv_subtitle);
            this.ratingBar = (RatingBar) itemView.findViewById(R.id.ratingbar);
            this.flowLayout = (FlowLayout) itemView.findViewById(R.id.flow_layout);
            this.radioButton = (RadioButton) itemView.findViewById(R.id.rb);
            this.tvRate = (TextView) itemView.findViewById(R.id.tv_rate);
            this.tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);
            this.tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            this.tvSubtitle1 = (TextView) itemView.findViewById(R.id.tv_subtitle1);
            this.tvDeliveryFee = (TextView) itemView.findViewById(R.id.tv_delivery_fee);

            this.mParentFrame.setOnClickListener(this);
            this.radioButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_root:
                    if(clickListener != null)
                        clickListener.onRowClick(getAdapterPosition());
                    break;
                case R.id.rb:
                    if(clickListener != null)
                        clickListener.onStoreSelect(getAdapterPosition());
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

    public void unSelectedAll() {
        for(StoreBO storeBO : dataSet){
            if(storeBO.isSelected()){
                storeBO.setSelected(false);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public void setSelectedStore(int position) {
        unSelectedAll();
        dataSet.get(position).setSelected(true);
        storeId=dataSet.get(position).getId();
        notifyDataSetChanged();
    }

    public int getStoreId()
    {
        return storeId;
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
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_change_store, parent, false);
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
        try {
            StoreBO itemBO = dataSet.get(listPosition);
            if (holder instanceof ItemTypeViewHolder) {
                ItemTypeViewHolder itemTypeViewHolder = (ItemTypeViewHolder) holder;
                itemTypeViewHolder.tvTitle.setText(itemBO.getBusinessName());

                itemTypeViewHolder.ratingBar.setStar(itemBO.getAverageRating());
                itemTypeViewHolder.ratingBar.setmClickable(false);
                itemTypeViewHolder.ratingBar.halfStar(true);
                itemTypeViewHolder.tvRate.setText(""+itemBO.getAverageRating());
                itemTypeViewHolder.tvSubtitle1.setText(Html.fromHtml("<font color='#000000' size='14'>Min Order</font>" + " " + Constants.CURRENCY_SYMBOL + Double.parseDouble(itemBO.getMinOrderPrice().substring(1))));
                itemTypeViewHolder.tvSubtitle1.setTextColor(mContext.getResources().getColor(R.color.grey4));
                if (Integer.parseInt(itemBO.getMinDeliveryCharges()) != 0) {
                    itemTypeViewHolder.tvDeliveryFee.setText(Html.fromHtml("<font color='#000000' size='14'>Delivery Fee</font>" + " " + Constants.CURRENCY_SYMBOL + Double.parseDouble(itemBO.getMinDeliveryCharges())));
                    itemTypeViewHolder.tvDeliveryFee.setTextColor(mContext.getResources().getColor(R.color.grey4));
                }
                itemTypeViewHolder.tvDistance.setText(itemBO.getDistance() + " m");
                itemTypeViewHolder.tvTime.setText(itemBO.getMinDeliveryTime());

                if (itemBO.isSelected()) {
                    itemTypeViewHolder.radioButton.setChecked(true);
                } else {
                    itemTypeViewHolder.radioButton.setChecked(false);
                }

                if (isAddTag) {
                    if(itemBO.getStoreTags()!=null) {
                        itemTypeViewHolder.flowLayout.setVisibility(View.VISIBLE);
                        addTags(itemTypeViewHolder.flowLayout, itemBO.getStoreTags());
                    }
                } else {
                    itemTypeViewHolder.flowLayout.setVisibility(View.GONE);
                }


            } else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
        }
        catch (Exception e)
        {
            LogUtils.i("mess",e.toString());
        }
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    private void addTags(FlowLayout flowLayout,List<StoreTag> storeTag) {
        flowLayout.removeAllViews();

        for(int i=0; i<storeTag.size(); i++){
            View view =  LayoutInflater.from(mContext).inflate(R.layout.item_tag, null);

            TextView tvTag = (TextView) view.findViewById(R.id.tv_tag);
            tvTag.setText(storeTag.get(i).getTag());
            GradientDrawable bgShape = (GradientDrawable)tvTag.getBackground();
            bgShape.setColor(ContextCompat.getColor(mContext, ImageUtils.tagColors[i]));
            flowLayout.addView(view);
        }
    }


    /*Listeners*/
    ClickListeners clickListener;

    public void setClickListener(ClickListeners clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListeners {
        void onRowClick(int position);
        void onStoreSelect(int position);

    }

}
