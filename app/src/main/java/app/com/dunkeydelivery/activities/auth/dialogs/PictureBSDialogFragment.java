package app.com.dunkeydelivery.activities.auth.dialogs;


import android.app.Dialog;
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
import android.widget.Button;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.activities.auth.SignUpActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PictureBSDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_PARAM1 = "param1";

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

    public PictureBSDialogFragment() {
    }


    public static PictureBSDialogFragment newInstance() {
        Bundle args = new Bundle();
        PictureBSDialogFragment fragment = new PictureBSDialogFragment();

        return fragment;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);

    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View bottomSheetView = View.inflate(getContext(), R.layout.fragment_bs_picture, null);
        dialog.setContentView(bottomSheetView);

        Button btnTake = (Button) bottomSheetView.findViewById(R.id.btn_take_photo);
        Button btnChoose = (Button) bottomSheetView.findViewById(R.id.btn_choose_photo);

        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SignUpActivity)getActivity()).capturePhoto();
                dismiss();
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SignUpActivity)getActivity()).choosePhotoVideoFromLibrary();
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
