// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.filter.pager;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.utils.customviews.ToggleButtonGroupTableLayout;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomCheckBox;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FilterAlcohol_ViewBinding implements Unbinder {
  private FilterAlcohol target;

  @UiThread
  public FilterAlcohol_ViewBinding(FilterAlcohol target, View source) {
    this.target = target;

    target.rvFilterSize = Utils.findRequiredViewAsType(source, R.id.rvFilterSize, "field 'rvFilterSize'", RecyclerView.class);
    target.tvSelling = Utils.findRequiredViewAsType(source, R.id.tv_selling, "field 'tvSelling'", TextView.class);
    target.rbBestSelling = Utils.findRequiredViewAsType(source, R.id.rb_bestselling, "field 'rbBestSelling'", RadioButton.class);
    target.rbPriceHtoL = Utils.findRequiredViewAsType(source, R.id.rb_price_htol, "field 'rbPriceHtoL'", RadioButton.class);
    target.rbAtoZ = Utils.findRequiredViewAsType(source, R.id.rb_atoz, "field 'rbAtoZ'", RadioButton.class);
    target.rbPriceLtoH = Utils.findRequiredViewAsType(source, R.id.rb_price_ltoh, "field 'rbPriceLtoH'", RadioButton.class);
    target.rbZtoA = Utils.findRequiredViewAsType(source, R.id.rb_ztoa, "field 'rbZtoA'", RadioButton.class);
    target.llContent = Utils.findRequiredViewAsType(source, R.id.ll_content, "field 'llContent'", LinearLayout.class);
    target.toggleButtonGroupTableLayout = Utils.findRequiredViewAsType(source, R.id.tg_layout, "field 'toggleButtonGroupTableLayout'", ToggleButtonGroupTableLayout.class);
    target.tv_country_1 = Utils.findRequiredViewAsType(source, R.id.tv_country_1, "field 'tv_country_1'", CustomCheckBox.class);
    target.tv_country_2 = Utils.findRequiredViewAsType(source, R.id.tv_country_2, "field 'tv_country_2'", CustomCheckBox.class);
    target.tv_country_3 = Utils.findRequiredViewAsType(source, R.id.tv_country_3, "field 'tv_country_3'", CustomCheckBox.class);
    target.tv_country_4 = Utils.findRequiredViewAsType(source, R.id.tv_country_4, "field 'tv_country_4'", CustomCheckBox.class);
    target.tv_country_5 = Utils.findRequiredViewAsType(source, R.id.tv_country_5, "field 'tv_country_5'", CustomCheckBox.class);
    target.tv_country_6 = Utils.findRequiredViewAsType(source, R.id.tv_country_6, "field 'tv_country_6'", CustomCheckBox.class);
    target.tv_price_1 = Utils.findRequiredViewAsType(source, R.id.tv_price_1, "field 'tv_price_1'", RadioButton.class);
    target.tv_price_2 = Utils.findRequiredViewAsType(source, R.id.tv_price_2, "field 'tv_price_2'", RadioButton.class);
    target.tv_price_3 = Utils.findRequiredViewAsType(source, R.id.tv_price_3, "field 'tv_price_3'", RadioButton.class);
    target.tv_price_4 = Utils.findRequiredViewAsType(source, R.id.tv_price_4, "field 'tv_price_4'", RadioButton.class);
    target.tv_price_5 = Utils.findRequiredViewAsType(source, R.id.tv_price_5, "field 'tv_price_5'", RadioButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FilterAlcohol target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvFilterSize = null;
    target.tvSelling = null;
    target.rbBestSelling = null;
    target.rbPriceHtoL = null;
    target.rbAtoZ = null;
    target.rbPriceLtoH = null;
    target.rbZtoA = null;
    target.llContent = null;
    target.toggleButtonGroupTableLayout = null;
    target.tv_country_1 = null;
    target.tv_country_2 = null;
    target.tv_country_3 = null;
    target.tv_country_4 = null;
    target.tv_country_5 = null;
    target.tv_country_6 = null;
    target.tv_price_1 = null;
    target.tv_price_2 = null;
    target.tv_price_3 = null;
    target.tv_price_4 = null;
    target.tv_price_5 = null;
  }
}
