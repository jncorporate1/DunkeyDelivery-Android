// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentProductDetail_ViewBinding implements Unbinder {
  private FragmentProductDetail target;

  @UiThread
  public FragmentProductDetail_ViewBinding(FragmentProductDetail target, View source) {
    this.target = target;

    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.btnIncrement = Utils.findRequiredViewAsType(source, R.id.btn_increment, "field 'btnIncrement'", ImageButton.class);
    target.btnDecrement = Utils.findRequiredViewAsType(source, R.id.btn_decrement, "field 'btnDecrement'", ImageButton.class);
    target.tvCount = Utils.findRequiredViewAsType(source, R.id.tv_display_count, "field 'tvCount'", TextView.class);
    target.btnAdd = Utils.findRequiredViewAsType(source, R.id.btn_add, "field 'btnAdd'", Button.class);
    target.imageView = Utils.findRequiredViewAsType(source, R.id.imageView, "field 'imageView'", ImageView.class);
    target.tv_detail = Utils.findRequiredViewAsType(source, R.id.tv_detail, "field 'tv_detail'", TextView.class);
    target.tv_price = Utils.findRequiredViewAsType(source, R.id.tv_price, "field 'tv_price'", TextView.class);
    target.tv_total = Utils.findRequiredViewAsType(source, R.id.tv_total, "field 'tv_total'", TextView.class);
    target.et_instructions = Utils.findRequiredViewAsType(source, R.id.et_instructions, "field 'et_instructions'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FragmentProductDetail target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.btnIncrement = null;
    target.btnDecrement = null;
    target.tvCount = null;
    target.btnAdd = null;
    target.imageView = null;
    target.tv_detail = null;
    target.tv_price = null;
    target.tv_total = null;
    target.et_instructions = null;
  }
}
