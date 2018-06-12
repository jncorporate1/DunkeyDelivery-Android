package app.com.dunkeydelivery.modules.deals.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.modules.deals.items.DealsItem;
import app.com.dunkeydelivery.modules.filter.pager.items.CuisineItem;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.LogUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Developer on 6/2/2017.
 */

public class DealsAdapter extends RecyclerView.Adapter {
    private List<DealsItem> dataSet;
    Context mContext;

    //constructor...
    public DealsAdapter(List<DealsItem> data, Context context) {
        this.dataSet = data;
        this.mContext = context;

    }

    class ItemTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_promotion)
        ImageView iv_promotion;
        @BindView(R.id.ll_dealsnpromotions)
        LinearLayout llDealsnPromotions;

        public ItemTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            iv_promotion.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.iv_promotion:
                {
                    clickListener.onRowClick(dataSet.get(getAdapterPosition()),v);
                    break;
                }
                default:{

                }
            }
        }
    }

    public void addItems(List<DealsItem> streamBOs) {
        dataSet.clear(); //if pagination not needed
        dataSet.addAll(streamBOs);
        notifyDataSetChanged();
    }

    public List<DealsItem> getItems() {
        return dataSet;
    }

    public void addLoadingItem() {
        dataSet.add(null);
        notifyItemInserted(dataSet.size() - 1);
    }

    public void clearItems() {
        dataSet.clear();
    }

    public DealsItem getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deals_promotion, parent, false);
        return new ItemTypeViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        ItemTypeViewHolder mHolder = (ItemTypeViewHolder) holder;
        LogUtils.i("mess",dataSet.get(listPosition).getOffer().getImageUrl());
        ImageUtils.setCenterImage(dataSet.get(listPosition).getOffer().getImageUrl(), mHolder.iv_promotion, mContext, R.drawable.logo);
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
        void onRowClick(DealsItem item,View view);

    }
}
