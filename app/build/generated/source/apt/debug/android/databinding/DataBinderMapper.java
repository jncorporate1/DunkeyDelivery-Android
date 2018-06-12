
package android.databinding;
import app.com.dunkeydelivery.BR;
class DataBinderMapper  {
    final static int TARGET_MIN_SDK = 19;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case app.com.dunkeydelivery.R.layout.fragment_main4:
                    return app.com.dunkeydelivery.databinding.FragmentMain4Binding.bind(view, bindingComponent);
                case app.com.dunkeydelivery.R.layout.layout_recycleview:
                    return app.com.dunkeydelivery.databinding.LayoutRecycleviewBinding.bind(view, bindingComponent);
                case app.com.dunkeydelivery.R.layout.fragment_main3:
                    return app.com.dunkeydelivery.databinding.FragmentMain3Binding.bind(view, bindingComponent);
                case app.com.dunkeydelivery.R.layout.activity_view_pager:
                    return app.com.dunkeydelivery.databinding.ActivityViewPagerBinding.bind(view, bindingComponent);
        }
        return null;
    }
    android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case -497796126: {
                if(tag.equals("layout/fragment_main4_0")) {
                    return app.com.dunkeydelivery.R.layout.fragment_main4;
                }
                break;
            }
            case 426752729: {
                if(tag.equals("layout/layout_recycleview_0")) {
                    return app.com.dunkeydelivery.R.layout.layout_recycleview;
                }
                break;
            }
            case -497797087: {
                if(tag.equals("layout/fragment_main3_0")) {
                    return app.com.dunkeydelivery.R.layout.fragment_main3;
                }
                break;
            }
            case 520473893: {
                if(tag.equals("layout/activity_view_pager_0")) {
                    return app.com.dunkeydelivery.R.layout.activity_view_pager;
                }
                break;
            }
        }
        return 0;
    }
    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"};
    }
}