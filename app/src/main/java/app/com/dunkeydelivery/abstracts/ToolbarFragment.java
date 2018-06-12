package app.com.dunkeydelivery.abstracts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.items.ItemBO;
import app.com.dunkeydelivery.utils.KeyboardOp;

public abstract class ToolbarFragment extends Fragment {

    private static boolean isActive;

    public abstract void refreshToolbar();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this))
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        KeyboardOp.hide(getActivity());
        Activities.hideBottomNavigation(getActivity(), false);
        refreshToolbar();
        isActive = true;

    }

    //this method will call when any fragment will destroy...
    //so this is like the onResume of every previous fragment...
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ItemBO event){
        KeyboardOp.hide(getActivity());
        refreshToolbar();
    }

    @Override
    public void onPause() {
        isActive = false;
        super.onPause();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().post(new ItemBO("test", "test"));
        super.onDestroy();
    }

    public static boolean isFragmentActive() {
        return isActive;
    }

}
