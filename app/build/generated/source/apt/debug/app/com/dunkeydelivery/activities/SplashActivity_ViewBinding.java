// Generated code from Butter Knife. Do not modify!
package app.com.dunkeydelivery.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ProgressBar;
import app.com.dunkeydelivery.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SplashActivity_ViewBinding implements Unbinder {
  private SplashActivity target;

  @UiThread
  public SplashActivity_ViewBinding(SplashActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SplashActivity_ViewBinding(SplashActivity target, View source) {
    this.target = target;

    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progress_bar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SplashActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressBar = null;
  }
}
