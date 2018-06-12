package app.com.dunkeydelivery.modules.account.dialogs;

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
import app.com.dunkeydelivery.modules.account.interfaces.ReasonTypeClickListener;
import app.com.dunkeydelivery.utils.EnumUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Developer on 7/4/2017.
 */

public class SelectReasonDialog extends DialogFragment {

    private View view;
    private Dialog dialog;
    public static String ARG_PARAM1 = "selectedReason";
    private EnumUtils.ReasonType reasonType;

    Unbinder unbinder;

    @BindView(R.id.rg_reason)
    RadioGroup radioGroup;

    @BindView(R.id.rb_comment)
    RadioButton rbComment;

    @BindView(R.id.rb_question)
    RadioButton rbQuestion;

    @BindView(R.id.rb_report)
    RadioButton rbReport;

    @BindView(R.id.rb_inquiry)
    RadioButton rbInquiry;

    private static ReasonTypeClickListener mListener;


    public static SelectReasonDialog newInstance(int selectedReason, ReasonTypeClickListener listener) {
        SelectReasonDialog fragment = new SelectReasonDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_PARAM1, selectedReason);
        fragment.setArguments(bundle);
        mListener = listener;
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
            reasonType = EnumUtils.ReasonType.getReason(getArguments().getInt(ARG_PARAM1));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_reason_layout, null);
        unbinder = ButterKnife.bind(this, view);
        setCancelable(true);

        setSelectedOption();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    switch (checkedId){
                        case R.id.rb_comment:
                            mListener.onReasonClick(EnumUtils.ReasonType.Comment);
                            break;

                        case R.id.rb_question:
                            mListener.onReasonClick(EnumUtils.ReasonType.Question);
                            break;

                        case R.id.rb_report:
                            mListener.onReasonClick(EnumUtils.ReasonType.Report);
                            break;

                        case R.id.rb_inquiry:
                            mListener.onReasonClick(EnumUtils.ReasonType.Inquiry);
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

        switch (reasonType){
            case Comment:
                rbComment.setChecked(true);
                break;
            case Question:
                rbQuestion.setChecked(true);
                break;
            case Report:
                rbReport.setChecked(true);
                break;
            case Inquiry:
                rbInquiry.setChecked(true);
                break;
            case None:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
