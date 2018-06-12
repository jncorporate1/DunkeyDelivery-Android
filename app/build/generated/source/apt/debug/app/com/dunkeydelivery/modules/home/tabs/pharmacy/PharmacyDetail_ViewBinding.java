// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.modules.home.tabs.pharmacy;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.hedgehog.ratingbar.RatingBar;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.apmem.tools.layouts.FlowLayout;

public class PharmacyDetail_ViewBinding implements Unbinder {
  private PharmacyDetail target;

  @UiThread
  public PharmacyDetail_ViewBinding(PharmacyDetail target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.pb_search, "field 'progressBar'", ProgressBar.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvDeliveryFee = Utils.findRequiredViewAsType(source, R.id.tv_delivery_fee, "field 'tvDeliveryFee'", TextView.class);
    target.tvMinOrder = Utils.findRequiredViewAsType(source, R.id.tv_subtitle1, "field 'tvMinOrder'", TextView.class);
    target.tvDistance = Utils.findRequiredViewAsType(source, R.id.tv_distance, "field 'tvDistance'", TextView.class);
    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tv_time, "field 'tvTime'", TextView.class);
    target.flowLayout = Utils.findRequiredViewAsType(source, R.id.flow_layout, "field 'flowLayout'", FlowLayout.class);
    target.tvRate = Utils.findRequiredViewAsType(source, R.id.tv_rate, "field 'tvRate'", TextView.class);
    target.ratingBar = Utils.findRequiredViewAsType(source, R.id.ratingbar, "field 'ratingBar'", RatingBar.class);
    target.ibSearch = Utils.findRequiredViewAsType(source, R.id.ib_search, "field 'ibSearch'", ImageButton.class);
    target.ibReview = Utils.findRequiredViewAsType(source, R.id.ib_review, "field 'ibReview'", ImageButton.class);
    target.ibInfo = Utils.findRequiredViewAsType(source, R.id.ib_info, "field 'ibInfo'", ImageButton.class);
    target.ibAdd = Utils.findRequiredViewAsType(source, R.id.ib_add, "field 'ibAdd'", ImageButton.class);
    target.flTags = Utils.findRequiredViewAsType(source, R.id.fl_tags, "field 'flTags'", FlowLayout.class);
    target.etMedication = Utils.findRequiredViewAsType(source, R.id.et_medication, "field 'etMedication'", EditText.class);
    target.etDrFirstName = Utils.findRequiredViewAsType(source, R.id.et_dr_first_name, "field 'etDrFirstName'", EditText.class);
    target.etDrLastName = Utils.findRequiredViewAsType(source, R.id.et_dr_last_name, "field 'etDrLastName'", EditText.class);
    target.etDrPhone = Utils.findRequiredViewAsType(source, R.id.et_dr_phone, "field 'etDrPhone'", EditText.class);
    target.etFirstName = Utils.findRequiredViewAsType(source, R.id.et_first_name, "field 'etFirstName'", EditText.class);
    target.etLastName = Utils.findRequiredViewAsType(source, R.id.et_last_name, "field 'etLastName'", EditText.class);
    target.tvDate = Utils.findRequiredViewAsType(source, R.id.tv_dob, "field 'tvDate'", EditText.class);
    target.tvGender = Utils.findRequiredViewAsType(source, R.id.tv_gender, "field 'tvGender'", EditText.class);
    target.iv_store = Utils.findRequiredViewAsType(source, R.id.iv_store, "field 'iv_store'", ImageView.class);
    target.btnSubmit = Utils.findRequiredViewAsType(source, R.id.btn_submit, "field 'btnSubmit'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PharmacyDetail target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.progressBar = null;
    target.tvTitle = null;
    target.tvDeliveryFee = null;
    target.tvMinOrder = null;
    target.tvDistance = null;
    target.tvTime = null;
    target.flowLayout = null;
    target.tvRate = null;
    target.ratingBar = null;
    target.ibSearch = null;
    target.ibReview = null;
    target.ibInfo = null;
    target.ibAdd = null;
    target.flTags = null;
    target.etMedication = null;
    target.etDrFirstName = null;
    target.etDrLastName = null;
    target.etDrPhone = null;
    target.etFirstName = null;
    target.etLastName = null;
    target.tvDate = null;
    target.tvGender = null;
    target.iv_store = null;
    target.btnSubmit = null;
  }
}
