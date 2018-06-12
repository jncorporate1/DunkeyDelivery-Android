package app.com.dunkeydelivery.modules.home.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;

import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.utils.ImageUtils;

/**
 * Created by Developer on 6/2/2017.
 */

public class PopularStoreAdapter extends RecyclerView.Adapter {
    private List<StoreBO> dataSet;
    Context mContext;
    int total_types;

    //constructor...
    public PopularStoreAdapter(List<StoreBO> data, Context context, RecyclerView mRecyclerView) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    public void setItems(List<StoreBO> items) {
        this.dataSet = items;
        notifyDataSetChanged();
    }

    class ItemTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tvTitle;
        TextView tvSubTitle;
        TextView tvView;
        TextView tvRate;
        ImageView ivLogo;
        RatingBar ratingBar;
        View mParentFrame;


        public ItemTypeViewHolder(View itemView) {
            super(itemView);
            this.mParentFrame = itemView.findViewById(R.id.ll_root);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvRate = (TextView) itemView.findViewById(R.id.tv_rate);
            this.tvSubTitle = (TextView) itemView.findViewById(R.id.tv_subtitle1);
            this.tvView = (TextView) itemView.findViewById(R.id.tv_view);
            this.ratingBar = (RatingBar) itemView.findViewById(R.id.ratingbar);
            this.ivLogo = (ImageView) itemView.findViewById(R.id.iv_logo);
            this.tvView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_view:
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
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_store, parent, false);
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
