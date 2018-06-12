// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.pharmacy.dialogs;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SelectGenderDialog_ViewBinding implements Unbinder {
  private SelectGenderDialog target;

  @UiThread
  public SelectGenderDialog_ViewBinding(SelectGenderDialog target, View source) {
    this.target = target;

    target.radioGroup = Utils.findRequiredViewAsType(source, R.id.rg_gender, "field 'radioGroup'", RadioGroup.class);
    target.rbMale = Utils.findRequiredViewAsType(source, R.id.rb_male, "field 'rbMale'", RadioButton.class);
    target.rbFemale = Utils.findRequiredViewAsType(source, R.id.rb_female, "field 'rbFemale'", RadioButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SelectGenderDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.radioGroup = null;
    target.rbMale = null;
    target.rbFemale = null;
  }
}
