package app.com.dunkeydelivery.modules.account.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.security.Key;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.activities.MainActivity;
import app.com.dunkeydelivery.items.SettingBO;
import app.com.dunkeydelivery.modules.account.fragments.EmailUs;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.MiscUtils;
import app.com.dunkeydelivery.utils.sharedprefs.ObjectSharedPreference;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsBSDialogFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {

            switch (newState) {
                case BottomSheetBehavior.STATE_DRAGGING:
                    break;
                case BottomSheetBehavior.STATE_COLLAPSED:
                    break;
                case BottomSheetBehavior.STATE_EXPANDED:
                    break;
                case BottomSheetBehavior.STATE_HIDDEN:
                    dismiss();
                    break;
                case BottomSheetBehavior.STATE_SETTLING:
                    break;
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    public ContactUsBSDialogFragment() {
    }


    public static ContactUsBSDialogFragment newInstance() {
        Bundle args = new Bundle();
        ContactUsBSDialogFragment fragment = new ContactUsBSDialogFragment();

        return fragment;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);

    }

    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View bottomSheetView = View.inflate(getContext(), R.layout.fragment_bs_contactus, null);
        dialog.setContentView(bottomSheetView);

        TextView btnCall = (TextView) bottomSheetView.findViewById(R.id.tv_call);
        TextView btnEmail = (TextView) bottomSheetView.findViewById(R.id.tv_email);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).closeDrawer();
                SettingBO settingBO=ObjectSharedPreference.getObject(SettingBO.class,Keys.SETTINGS);
                if(settingBO.getContactNo()!=null) {
                    MiscUtils.callPhone(getActivity(), settingBO.getContactNo(), ContactUsBSDialogFragment.this);
                }
                else
                {
                    MiscUtils.callPhone(getActivity(), "1234", ContactUsBSDialogFragment.this);
                }
                 dismiss();
            }
        });



        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Activities.gotoNextFragment(getContext(), EmailUs.newInstance());
                dismiss();
            }
        });

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) bottomSheetView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        ((View) bottomSheetView.getParent()).setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(bottomSheetCallback);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getDialog().getWindow();
            if (window != null)
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


    }
}
