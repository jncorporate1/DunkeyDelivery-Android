package app.com.dunkeydelivery.modules.points.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.modules.account.adapters.AddressListAdapter;
import app.com.dunkeydelivery.modules.points.items.Points;
import app.com.dunkeydelivery.modules.points.items.Reward;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.SnackBarUtil;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Developer on 1/15/2018.
 */

public class RewardsListAdapter extends RecyclerView.Adapter {
    private List<Reward> rewards;
    private Context mContext;
    private Points points;

    public RewardsListAdapter(List<Reward> rewards, Context mContext, Points points) {
        this.points=points;
        this.rewards = rewards;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_layout,parent,false);
        return new RewardsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if(rewards!=null) {
                RewardsViewHolder rewardsViewHolder = (RewardsViewHolder) holder;
                if (rewards.get(position).getPointsRequired() != null && rewards.get(position).getAmountAward() != null) {
                    rewardsViewHolder.tv_PointsReward.setText(String.valueOf(rewards.get(position).getPointsRequired()) + " Points Rewards");
                    rewardsViewHolder.tv_dollarValue.setText(String.valueOf(rewards.get(position).getAmountAward()));
                }
                if (rewards.get(position).getAmountAward() == 0) {
                    rewardsViewHolder.ll_dollarValue.setVisibility(View.GONE);
                    //rewardsViewHolder.ivRewardPrizeImage.setImageResource(R.drawable.alcohol);
                    ImageUtils.setRoundedImage(mContext, rewards.get(position).getRewardPrize().getImageUrlDummy(), rewardsViewHolder.ivRewardPrizeImage);
                } else {
                    rewardsViewHolder.ll_dollarValue.setVisibility(View.VISIBLE);
                    rewardsViewHolder.ivRewardPrizeImage.setVisibility(View.GONE);
                }
                if (points.getUserPoints().getRewardPoints() >= rewards.get(position).getPointsRequired()) {
                    rewardsViewHolder.btnRedeem1.setEnabled(true);
                    rewardsViewHolder.btnRedeem1.setTag("1");
                    rewardsViewHolder.btnRedeem1.setBackground(mContext.getResources().getDrawable(R.drawable.green_rounded_btn));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return rewards.size();
    }
    public class RewardsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.tv_points_reward)
        CustomTextView tv_PointsReward;

        @BindView(R.id.tv_dollarValue)
        CustomTextView tv_dollarValue;

        @BindView(R.id.btn_radeem1)
        Button btnRedeem1;

        @BindView(R.id.ll_dollarValue)
        LinearLayout ll_dollarValue;

        @BindView(R.id.iv_rewardPrizeImage)
        ImageView ivRewardPrizeImage;

        public RewardsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            btnRedeem1.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.btn_radeem1:
                {
                    if(((String)view.getTag()).equals("1")) {
                        clickListener.onRowClick(getAdapterPosition());
                    }
                    else
                    {
                        SnackBarUtil.showSnackbar(mContext,"You don't have enough points to redeem this reward.",true);
                    }
                    break;
                }
                default:
                {

                }
            }
        }
    }

    public Reward getItem(int position)
    {
        return rewards.get(position);
    }

    ClickListeners clickListener;

    public void setClickListener(ClickListeners clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListeners {
        void onRowClick(int position);
        //void onRowDelete(int position,SwipeLayout swipeLayout);
    }
}
