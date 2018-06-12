// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.food.pager;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.gms.maps.MapView;
import com.hedgehog.ratingbar.RatingBar;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.apmem.tools.layouts.FlowLayout;

public class StoreInfo_ViewBinding implements Unbinder {
  private StoreInfo target;

  @UiThread
  public StoreInfo_ViewBinding(StoreInfo target, View source) {
    this.target = target;

    target.mMapView = Utils.findRequiredViewAsType(source, R.id.mapView, "field 'mMapView'", MapView.class);
    target.ivLogo = Utils.findRequiredViewAsType(source, R.id.iv_logo, "field 'ivLogo'", ImageView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRate = Utils.findRequiredViewAsType(source, R.id.tv_rate, "field 'tvRate'", TextView.class);
    target.flowLayout = Utils.findRequiredViewAsType(source, R.id.flow_layout, "field 'flowLayout'", FlowLayout.class);
    target.tvStoreAddress = Utils.findRequiredViewAsType(source, R.id.tv_store_address, "field 'tvStoreAddress'", TextView.class);
    target.tvStorePhone = Utils.findRequiredViewAsType(source, R.id.tv_store_phone, "field 'tvStorePhone'", TextView.class);
    target.tvMondayTime = Utils.findRequiredViewAsType(source, R.id.tv_monday_time, "field 'tvMondayTime'", TextView.class);
    target.tvTuesdayTime = Utils.findRequiredViewAsType(source, R.id.tv_tuesday_time, "field 'tvTuesdayTime'", TextView.class);
    target.tvWednesdayTime = Utils.findRequiredViewAsType(source, R.id.tv_wednessday_time, "field 'tvWednesdayTime'", TextView.class);
    target.tvThursdayTime = Utils.findRequiredViewAsType(source, R.id.tv_thursday_time, "field 'tvThursdayTime'", TextView.class);
    target.tvFridayTime = Utils.findRequiredViewAsType(source, R.id.tv_friday_time, "field 'tvFridayTime'", TextView.class);
    target.tvSatTime = Utils.findRequiredViewAsType(source, R.id.tv_saturday_time, "field 'tvSatTime'", TextView.class);
    target.tvSunTime = Utils.findRequiredViewAsType(source, R.id.tv_sunday_time, "field 'tvSunTime'", TextView.class);
    target.ratingBar = Utils.findRequiredViewAsType(source, R.id.ratingbar, "field 'ratingBar'", RatingBar.class);
    target.llStore = Utils.findRequiredViewAsType(source, R.id.ll_store, "field 'llStore'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StoreInfo target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mMapView = null;
    target.ivLogo = null;
    target.tvTitle = null;
    target.tvRate = null;
    target.flowLayout = null;
    target.tvStoreAddress = null;
    target.tvStorePhone = null;
    target.tvMondayTime = null;
    target.tvTuesdayTime = null;
    target.tvWednesdayTime = null;
    target.tvThursdayTime = null;
    target.tvFridayTime = null;
    target.tvSatTime = null;
    target.tvSunTime = null;
    target.ratingBar = null;
    target.llStore = null;
  }
}
