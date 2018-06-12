package app.com.dunkeydelivery.modules.home.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.StoreUtils;

/**
 * Created by Developer on 6/2/2017.
 */

public class NearByStoreAdapter extends RecyclerView.Adapter {
    private List<StoreBO> dataSet;
    Context mContext;
    int total_types;

    //constructor...
    public NearByStoreAdapter(List<StoreBO> data, Context context, RecyclerView mRecyclerView) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    class ItemTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tvTitle;
        TextView tvSubTitle;
        TextView tvRate;
        TextView tvDistance;
        TextView tvTime;
        RatingBar ratingBar;
        ImageView ivLogo;
        FlowLayout flowLayout;
        View mParentFrame;


        public ItemTypeViewHolder(View itemView) {
            super(itemView);
            this.mParentFrame = itemView.findViewById(R.id.ll_root);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvSubTitle = (TextView) itemView.findViewById(R.id.tv_subtitle1);
            this.tvRate = (TextView) itemView.findViewById(R.id.tv_rate);
            this.ivLogo = (ImageView) itemView.findViewById(R.id.iv_logo);
            this.ratingBar = (RatingBar) itemView.findViewById(R.id.ratingbar);
            this.tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);
            this.tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            this.flowLayout = (FlowLayout) itemView.findViewById(R.id.flow_layout);
            this.mParentFrame.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_root:
                    if (clickListener != null)
                        clickListener.onRowClick(dataSet.get(getAdapterPosition()));
                    break;
            }
        }
    }

    public void setItems(List<StoreBO> storeBOs) {
        dataSet.clear();
        dataSet.addAll(storeBOs);
        notifyDataSetChanged();
    }


    public void addItems(List<StoreBO> streamBOs) {
        dataSet.addAll(streamBOs);
        notifyDataSetChanged();
    }

    public List<StoreBO> getItems() {
        return dataSet;
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
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nearby_store, parent, false);
        return new ItemTypeViewHolder(view);
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        StoreBO itemBO = dataSet.get(listPosition);
        ItemTypeViewHolder itemTypeViewHolder = (ItemTypeViewHolder) holder;
        itemTypeViewHolder.tvTitle.setText(itemBO.getBusinessName());

        ImageUtils.setCenterImage(itemBO.getImageUrl(), itemTypeViewHolder.ivLogo, mContext, R.drawable.logo);
        itemTypeViewHolder.ratingBar.setStar(itemBO.getAverageRating());
        itemTypeViewHolder.tvRate.setText(itemBO.getAverageRating() + "");
        itemTypeViewHolder.ratingBar.setmClickable(false);
        itemTypeViewHolder.tvDistance.setText(itemBO.getDistance() + " m");

        if (!itemBO.getMinDeliveryTime().isEmpty()) {
            itemTypeViewHolder.tvTime.setVisibility(View.VISIBLE);
            itemTypeViewHolder.tvTime.setText(itemBO.getMinDeliveryTime());
        } else
            itemTypeViewHolder.tvTime.setVisibility(View.GONE);

        itemTypeViewHolder.tvSubTitle.setText(mContext.getString(R.string.min_order) + "  " + itemBO.getMinOrderPrice());

        StoreUtils.addStoreTags(mContext, itemTypeViewHolder.flowLayout, itemBO.getStoreTags());
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
        void onRowClick(StoreBO storeBO);
    }

}
