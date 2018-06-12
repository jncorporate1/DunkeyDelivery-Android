// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.ride;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RideMain_ViewBinding implements Unbinder {
  private RideMain target;

  @UiThread
  public RideMain_ViewBinding(RideMain target, View source) {
    this.target = target;

    target.et_fullname = Utils.findRequiredViewAsType(source, R.id.et_fullname, "field 'et_fullname'", EditText.class);
    target.et_bussiness_name = Utils.findRequiredViewAsType(source, R.id.et_bussiness_name, "field 'et_bussiness_name'", EditText.class);
    target.et_bussiness_type = Utils.findRequiredViewAsType(source, R.id.et_bussiness_type, "field 'et_bussiness_type'", EditText.class);
    target.et_email = Utils.findRequiredViewAsType(source, R.id.et_email, "field 'et_email'", EditText.class);
    target.et_phone = Utils.findRequiredViewAsType(source, R.id.et_phone, "field 'et_phone'", EditText.class);
    target.et_zipcode = Utils.findRequiredViewAsType(source, R.id.et_zipcode, "field 'et_zipcode'", EditText.class);
    target.btnSubmit = Utils.findRequiredViewAsType(source, R.id.btn_submit, "field 'btnSubmit'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RideMain target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.et_fullname = null;
    target.et_bussiness_name = null;
    target.et_bussiness_type = null;
    target.et_email = null;
    target.et_phone = null;
    target.et_zipcode = null;
    target.btnSubmit = null;
  }
}
