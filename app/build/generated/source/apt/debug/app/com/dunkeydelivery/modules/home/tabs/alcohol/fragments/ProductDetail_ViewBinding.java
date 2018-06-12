// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.alcohol.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProductDetail_ViewBinding implements Unbinder {
  private ProductDetail target;

  @UiThread
  public ProductDetail_ViewBinding(ProductDetail target, View source) {
    this.target = target;

    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvDetail = Utils.findRequiredViewAsType(source, R.id.tv_detail, "field 'tvDetail'", TextView.class);
    target.imageView = Utils.findRequiredViewAsType(source, R.id.imageView, "field 'imageView'", ImageView.class);
    target.etInstructions = Utils.findRequiredViewAsType(source, R.id.et_instructions, "field 'etInstructions'", EditText.class);
    target.tvTotal = Utils.findRequiredViewAsType(source, R.id.tv_total, "field 'tvTotal'", TextView.class);
    target.tv_price = Utils.findRequiredViewAsType(source, R.id.tv_price, "field 'tv_price'", TextView.class);
    target.tvDetail1 = Utils.findRequiredViewAsType(source, R.id.tv_detail_1, "field 'tvDetail1'", TextView.class);
    target.tvDetail3 = Utils.findRequiredViewAsType(source, R.id.tv_detail_3, "field 'tvDetail3'", TextView.class);
    target.tvDetail2 = Utils.findRequiredViewAsType(source, R.id.tvDetail2, "field 'tvDetail2'", TextView.class);
    target.btnIncrement = Utils.findRequiredViewAsType(source, R.id.btn_increment, "field 'btnIncrement'", ImageButton.class);
    target.btnDecrement = Utils.findRequiredViewAsType(source, R.id.btn_decrement, "field 'btnDecrement'", ImageButton.class);
    target.tvCount = Utils.findRequiredViewAsType(source, R.id.tv_display_count, "field 'tvCount'", TextView.class);
    target.rgpBottleSize = Utils.findRequiredViewAsType(source, R.id.rgp_bottlesize, "field 'rgpBottleSize'", RecyclerView.class);
    target.btnAdd = Utils.findRequiredViewAsType(source, R.id.btn_add, "field 'btnAdd'", Button.class);
    target.llProductSizes = Utils.findRequiredViewAsType(source, R.id.llProductSizes, "field 'llProductSizes'", LinearLayout.class);
    target.llProductSizesLine = Utils.findRequiredView(source, R.id.llProductSizesLine, "field 'llProductSizesLine'");
    target.storeNameLine = Utils.findRequiredView(source, R.id.storeNameLine, "field 'storeNameLine'");
    target.productsInOfferLine = Utils.findRequiredView(source, R.id.productsInOfferLine, "field 'productsInOfferLine'");
    target.llStoreNameLayout = Utils.findRequiredViewAsType(source, R.id.ll_storeNameLayout, "field 'llStoreNameLayout'", LinearLayout.class);
    target.ll_productsInOfferLayout = Utils.findRequiredViewAsType(source, R.id.ll_productsInOfferLayout, "field 'll_productsInOfferLayout'", LinearLayout.class);
    target.tvStoreName = Utils.findRequiredViewAsType(source, R.id.tv_storename, "field 'tvStoreName'", TextView.class);
    target.llAddmoreproducts = Utils.findRequiredViewAsType(source, R.id.ll_addmoreproducts, "field 'llAddmoreproducts'", LinearLayout.class);
    target.tvSee = Utils.findRequiredViewAsType(source, R.id.tv_see, "field 'tvSee'", TextView.class);
    target.tvLess = Utils.findRequiredViewAsType(source, R.id.tv_less, "field 'tvLess'", TextView.class);
    target.llPriceLine = Utils.findRequiredViewAsType(source, R.id.llPriceLine, "field 'llPriceLine'", LinearLayout.class);
    target.tvSeeLessProductSizes = Utils.findRequiredViewAsType(source, R.id.tvSeeLessProductSizes, "field 'tvSeeLessProductSizes'", TextView.class);
    target.tvSeeMoreProductSizes = Utils.findRequiredViewAsType(source, R.id.tvSeeMoreProductSizes, "field 'tvSeeMoreProductSizes'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProductDetail target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvDetail = null;
    target.imageView = null;
    target.etInstructions = null;
    target.tvTotal = null;
    target.tv_price = null;
    target.tvDetail1 = null;
    target.tvDetail3 = null;
    target.tvDetail2 = null;
    target.btnIncrement = null;
    target.btnDecrement = null;
    target.tvCount = null;
    target.rgpBottleSize = null;
    target.btnAdd = null;
    target.llProductSizes = null;
    target.llProductSizesLine = null;
    target.storeNameLine = null;
    target.productsInOfferLine = null;
    target.llStoreNameLayout = null;
    target.ll_productsInOfferLayout = null;
    target.tvStoreName = null;
    target.llAddmoreproducts = null;
    target.tvSee = null;
    target.tvLess = null;
    target.llPriceLine = null;
    target.tvSeeLessProductSizes = null;
    target.tvSeeMoreProductSizes = null;
  }
}
