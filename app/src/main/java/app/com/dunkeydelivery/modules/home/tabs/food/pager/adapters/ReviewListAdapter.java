package app.com.dunkeydelivery.modules.home.tabs.food.pager.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.modules.home.tabs.food.pager.items.ReviewBO;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.LogUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Developer on 6/2/2017.
 */

public class ReviewListAdapter extends RecyclerView.Adapter {
    private boolean isForAlcoholStore;
    private List<ReviewBO> dataSet;
    Context mContext;
    int total_types;
    public final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int visibleThreshold = 2;
    private boolean isLoading;

    //constructor...
    public ReviewListAdapter(List<ReviewBO> data, boolean isForAlcoholStore, Context context, RecyclerView mRecyclerView) {
        this.dataSet = data;
        this.mContext = context;
        this.isForAlcoholStore = isForAlcoholStore;
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
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_review_count)
        TextView tvReviewCount;
        @BindView(R.id.tv_review)
        TextView tvReview;
        @BindView(R.id.ll_root)
        View mParentFrame;
        @BindView(R.id.iv_item)
        ImageView iv_item;
        @BindView(R.id.ratingbar)
        RatingBar ratingbar;

        public ItemTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.mParentFrame.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_root:
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

    public void addItems(List<ReviewBO> streamBOs) {
        dataSet.addAll(streamBOs);
        notifyDataSetChanged();
    }

    public List<ReviewBO> getItems() {
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

    public ReviewBO getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
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

        ReviewBO reviewBO = dataSet.get(listPosition);
        if (holder instanceof ItemTypeViewHolder) {
            ItemTypeViewHolder itemTypeViewHolder = (ItemTypeViewHolder) holder;
            itemTypeViewHolder.tvTitle.setText(reviewBO.getUserBO().getFullName());
            if (isForAlcoholStore && reviewBO.userBO.getTotalOrders() != null) {
                itemTypeViewHolder.tvReviewCount.setText(reviewBO.getUserBO().getTotalReviews() + " reviews  |  " + reviewBO.getUserBO().getTotalOrders() + " Orders");
            } else {
                itemTypeViewHolder.tvReviewCount.setText(reviewBO.getUserBO().getTotalReviews() + " reviews");
            }
            itemTypeViewHolder.tv_date.setText(formatdate(reviewBO.getDateOfRating().split("T")[0]));
            itemTypeViewHolder.tvReview.setText(reviewBO.getFeedback());
            itemTypeViewHolder.ratingbar.setStar(reviewBO.getRating());
            ImageUtils.setCenterImage(reviewBO.getUserBO().getProfilePictureUrl(), itemTypeViewHolder.iv_item, mContext, R.drawable.logo);


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }
    public String formatdate(String fdate)
    {
        String newdate=null;
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");
        try {
            Date date = inputFormat.parse(fdate);
            newdate=outputFormat.format(date);
        }
        catch (Exception e)
        {
            LogUtils.i("mess",e.toString());
        }
        return newdate;
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
