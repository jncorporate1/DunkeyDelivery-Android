// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.account.dialogs;

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

public class SelectReasonDialog_ViewBinding implements Unbinder {
  private SelectReasonDialog target;

  @UiThread
  public SelectReasonDialog_ViewBinding(SelectReasonDialog target, View source) {
    this.target = target;

    target.radioGroup = Utils.findRequiredViewAsType(source, R.id.rg_reason, "field 'radioGroup'", RadioGroup.class);
    target.rbComment = Utils.findRequiredViewAsType(source, R.id.rb_comment, "field 'rbComment'", RadioButton.class);
    target.rbQuestion = Utils.findRequiredViewAsType(source, R.id.rb_question, "field 'rbQuestion'", RadioButton.class);
    target.rbReport = Utils.findRequiredViewAsType(source, R.id.rb_report, "field 'rbReport'", RadioButton.class);
    target.rbInquiry = Utils.findRequiredViewAsType(source, R.id.rb_inquiry, "field 'rbInquiry'", RadioButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SelectReasonDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.radioGroup = null;
    target.rbComment = null;
    target.rbQuestion = null;
    target.rbReport = null;
    target.rbInquiry = null;
  }
}
