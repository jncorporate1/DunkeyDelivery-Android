package app.com.dunkeydelivery.abstracts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import app.com.dunkeydelivery.activities.Activities;

/**
 * Created by Developer on 1/2/2018.
 */

public abstract class EventBusFragment extends Fragment {
    public abstract void refreshData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Subscribe
    public void getMessage(Object object) {
        refreshData();
    }
}
