// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.filter.pager;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.utils.customviews.ToggleButtonGroupTableLayout;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomCheckBox;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.hedgehog.ratingbar.RatingBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FilterOther_ViewBinding implements Unbinder {
  private FilterOther target;

  @UiThread
  public FilterOther_ViewBinding(FilterOther target, View source) {
    this.target = target;

    target.tvDistance = Utils.findRequiredViewAsType(source, R.id.tv_distance, "field 'tvDistance'", TextView.class);
    target.toggleButtonGroupTableLayout = Utils.findRequiredViewAsType(source, R.id.tg_layout, "field 'toggleButtonGroupTableLayout'", ToggleButtonGroupTableLayout.class);
    target.rbDistance = Utils.findRequiredViewAsType(source, R.id.rb_distance, "field 'rbDistance'", RadioButton.class);
    target.rbRating = Utils.findRequiredViewAsType(source, R.id.rb_rating, "field 'rbRating'", RadioButton.class);
    target.rbTime = Utils.findRequiredViewAsType(source, R.id.rb_time, "field 'rbTime'", RadioButton.class);
    target.rbPrice = Utils.findRequiredViewAsType(source, R.id.rb_price, "field 'rbPrice'", RadioButton.class);
    target.rbDelivery = Utils.findRequiredViewAsType(source, R.id.rb_delivery, "field 'rbDelivery'", RadioButton.class);
    target.rbAtoZ = Utils.findRequiredViewAsType(source, R.id.rb_atoz, "field 'rbAtoZ'", RadioButton.class);
    target.rbRelevence = Utils.findRequiredViewAsType(source, R.id.rb_relevence, "field 'rbRelevence'", RadioButton.class);
    target.ratingbar = Utils.findRequiredViewAsType(source, R.id.ratingbar, "field 'ratingbar'", RatingBar.class);
    target.llD1 = Utils.findRequiredViewAsType(source, R.id.ll_d1, "field 'llD1'", LinearLayout.class);
    target.llD2 = Utils.findRequiredViewAsType(source, R.id.ll_d2, "field 'llD2'", LinearLayout.class);
    target.llD3 = Utils.findRequiredViewAsType(source, R.id.ll_d3, "field 'llD3'", LinearLayout.class);
    target.llD4 = Utils.findRequiredViewAsType(source, R.id.ll_d4, "field 'llD4'", LinearLayout.class);
    target.rbD1 = Utils.findRequiredViewAsType(source, R.id.rb_delivery_1, "field 'rbD1'", RadioButton.class);
    target.rbD2 = Utils.findRequiredViewAsType(source, R.id.rb_delivery_2, "field 'rbD2'", RadioButton.class);
    target.rbD3 = Utils.findRequiredViewAsType(source, R.id.rb_delivery_3, "field 'rbD3'", RadioButton.class);
    target.rbD4 = Utils.findRequiredViewAsType(source, R.id.rb_delivery_4, "field 'rbD4'", RadioButton.class);
    target.llTime1 = Utils.findRequiredViewAsType(source, R.id.ll_rd_time1, "field 'llTime1'", LinearLayout.class);
    target.llTime2 = Utils.findRequiredViewAsType(source, R.id.ll_rd_time2, "field 'llTime2'", LinearLayout.class);
    target.llTime3 = Utils.findRequiredViewAsType(source, R.id.ll_rd_time3, "field 'llTime3'", LinearLayout.class);
    target.rbTime1 = Utils.findRequiredViewAsType(source, R.id.rb_time_1, "field 'rbTime1'", RadioButton.class);
    target.rbTime2 = Utils.findRequiredViewAsType(source, R.id.rb_time_2, "field 'rbTime2'", RadioButton.class);
    target.rbTime3 = Utils.findRequiredViewAsType(source, R.id.rb_time_3, "field 'rbTime3'", RadioButton.class);
    target.rbPrice1 = Utils.findRequiredViewAsType(source, R.id.rb_price_1, "field 'rbPrice1'", CustomCheckBox.class);
    target.rbPrice2 = Utils.findRequiredViewAsType(source, R.id.rb_price_2, "field 'rbPrice2'", CustomCheckBox.class);
    target.rbPrice3 = Utils.findRequiredViewAsType(source, R.id.rb_price_3, "field 'rbPrice3'", CustomCheckBox.class);
    target.rbPrice4 = Utils.findRequiredViewAsType(source, R.id.rb_price_4, "field 'rbPrice4'", CustomCheckBox.class);
    target.swSpecial = Utils.findRequiredViewAsType(source, R.id.sw_special, "field 'swSpecial'", Switch.class);
    target.swFreeDelivery = Utils.findRequiredViewAsType(source, R.id.sw_free_delivery, "field 'swFreeDelivery'", Switch.class);
    target.swNewRestaurants = Utils.findRequiredViewAsType(source, R.id.sw_new_restaurants, "field 'swNewRestaurants'", Switch.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FilterOther target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvDistance = null;
    target.toggleButtonGroupTableLayout = null;
    target.rbDistance = null;
    target.rbRating = null;
    target.rbTime = null;
    target.rbPrice = null;
    target.rbDelivery = null;
    target.rbAtoZ = null;
    target.rbRelevence = null;
    target.ratingbar = null;
    target.llD1 = null;
    target.llD2 = null;
    target.llD3 = null;
    target.llD4 = null;
    target.rbD1 = null;
    target.rbD2 = null;
    target.rbD3 = null;
    target.rbD4 = null;
    target.llTime1 = null;
    target.llTime2 = null;
    target.llTime3 = null;
    target.rbTime1 = null;
    target.rbTime2 = null;
    target.rbTime3 = null;
    target.rbPrice1 = null;
    target.rbPrice2 = null;
    target.rbPrice3 = null;
    target.rbPrice4 = null;
    target.swSpecial = null;
    target.swFreeDelivery = null;
    target.swNewRestaurants = null;
  }
}
