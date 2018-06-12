// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.points.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentPoints_ViewBinding implements Unbinder {
  private FragmentPoints target;

  @UiThread
  public FragmentPoints_ViewBinding(FragmentPoints target, View source) {
    this.target = target;

    target.rvShowReward = Utils.findRequiredViewAsType(source, R.id.rv_showReward, "field 'rvShowReward'", RecyclerView.class);
    target.llShowRewardPoints = Utils.findRequiredViewAsType(source, R.id.ll_showRewardPoints, "field 'llShowRewardPoints'", LinearLayout.class);
    target.progressBarPoints = Utils.findRequiredViewAsType(source, R.id.progress_bar_points, "field 'progressBarPoints'", ProgressBar.class);
    target.progressBarPoints1 = Utils.findRequiredViewAsType(source, R.id.progress_bar_points1, "field 'progressBarPoints1'", ProgressBar.class);
    target.progressBarPoints2 = Utils.findRequiredViewAsType(source, R.id.progress_bar_points2, "field 'progressBarPoints2'", ProgressBar.class);
    target.progressBarPoints4 = Utils.findRequiredViewAsType(source, R.id.progress_bar_points4, "field 'progressBarPoints4'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FragmentPoints target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvShowReward = null;
    target.llShowRewardPoints = null;
    target.progressBarPoints = null;
    target.progressBarPoints1 = null;
    target.progressBarPoints2 = null;
    target.progressBarPoints4 = null;
  }
}
