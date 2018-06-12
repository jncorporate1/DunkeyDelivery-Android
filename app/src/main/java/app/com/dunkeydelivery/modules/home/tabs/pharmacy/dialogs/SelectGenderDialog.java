package app.com.dunkeydelivery.modules.home.tabs.pharmacy.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.modules.home.tabs.pharmacy.interfaces.GenderTypeClickListener;
import app.com.dunkeydelivery.utils.EnumUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Developer on 7/4/2017.
 */

public class SelectGenderDialog extends DialogFragment {

    private View view;
    private Dialog dialog;
    private String TAG = this.getClass().getSimpleName();
    public static String ARG_PARAM1 = "selectedGender";
    private EnumUtils.Gender selectedGender;

    Unbinder unbinder;

    @BindView(R.id.rg_gender)
    RadioGroup radioGroup;

    @BindView(R.id.rb_male)
    RadioButton rbMale;

    @BindView(R.id.rb_female)
    RadioButton rbFemale;

    private static GenderTypeClickListener mListener;


    public static SelectGenderDialog newInstance(int selectedGender, GenderTypeClickListener genderTypeClickListener) {
        SelectGenderDialog fragment = new SelectGenderDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_PARAM1, selectedGender);
        fragment.setArguments(bundle);
        mListener = genderTypeClickListener;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getDialog() == null ) {  // Returns mDialog
            // Tells DialogFragment to not use the fragment as a dialog, and so won't try to use mDialog
            setShowsDialog( false );
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (getArguments() != null) {
            selectedGender = EnumUtils.Gender.getGender(getArguments().getInt(ARG_PARAM1));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_gender_layout, null);
        unbinder = ButterKnife.bind(this, view);
        setCancelable(true);

        setSelectedOption();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    switch (checkedId){
                        case R.id.rb_male:
                            mListener.onGender(EnumUtils.Gender.Male);
                            break;

                        case R.id.rb_female:
                            mListener.onGender(EnumUtils.Gender.Female);
                            break;


                    }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 300);

            }
        });

        builder.setView(view);
        dialog = builder.create();

        if(dialog != null)
            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }//onCreateDialog

    private void setSelectedOption() {

        switch (selectedGender){
            case Male:
                rbMale.setChecked(true);
                break;
            case Female:
                rbFemale.setChecked(true);
                break;
            case None:
                rbMale.setChecked(false);
                rbFemale.setChecked(false);
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
