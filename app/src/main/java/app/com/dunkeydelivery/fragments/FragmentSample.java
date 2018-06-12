package app.com.dunkeydelivery.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.utils.toolbar.MenuItemImgOrStr;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentSample extends ToolbarFragment {
    private Context context;
    private String TAG = this.getClass().getSimpleName();
    Unbinder unbinder;

    public static FragmentSample newInstance() {
        Bundle args = new Bundle();
        FragmentSample fragment = new FragmentSample();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main1,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();

    }


    @Override
    public void onResume() {
        super.onResume();
        Activities.hideBottomNavigation(context, true);
        Log.i(TAG, "onResume: ");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void refreshToolbar() {
        Log.i("", "refreshToolbar: ");
        MenuItemImgOrStr menuItemImgOrStr = new MenuItemImgOrStr(R.drawable.ic_launcher_round, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        ToolbarOp.refresh(getView(), getActivity(), getString(R.string.title_settings),
                null, ToolbarOp.Theme.Dark, 0, null, menuItemImgOrStr);
    }
}