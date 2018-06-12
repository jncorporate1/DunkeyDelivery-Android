// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.points.Adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RewardsListAdapter$RewardsViewHolder_ViewBinding implements Unbinder {
  private RewardsListAdapter.RewardsViewHolder target;

  @UiThread
  public RewardsListAdapter$RewardsViewHolder_ViewBinding(RewardsListAdapter.RewardsViewHolder target,
      View source) {
    this.target = target;

    target.tv_PointsReward = Utils.findRequiredViewAsType(source, R.id.tv_points_reward, "field 'tv_PointsReward'", CustomTextView.class);
    target.tv_dollarValue = Utils.findRequiredViewAsType(source, R.id.tv_dollarValue, "field 'tv_dollarValue'", CustomTextView.class);
    target.btnRedeem1 = Utils.findRequiredViewAsType(source, R.id.btn_radeem1, "field 'btnRedeem1'", Button.class);
    target.ll_dollarValue = Utils.findRequiredViewAsType(source, R.id.ll_dollarValue, "field 'll_dollarValue'", LinearLayout.class);
    target.ivRewardPrizeImage = Utils.findRequiredViewAsType(source, R.id.iv_rewardPrizeImage, "field 'ivRewardPrizeImage'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RewardsListAdapter.RewardsViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_PointsReward = null;
    target.tv_dollarValue = null;
    target.btnRedeem1 = null;
    target.ll_dollarValue = null;
    target.ivRewardPrizeImage = null;
  }
}
