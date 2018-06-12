package app.com.dunkeydelivery.modules.account.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.HashMap;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.account.Items.CardItem;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.DateTimeOp;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.SnackBarUtil;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddNewCreditCard extends ToolbarFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private static final String ARGS_1 = "argument_1";
    private Context context;
    private String TAG = this.getClass().getSimpleName();

    CardItem cardItem;
    Unbinder unbinder;
    String date;

    @BindView(R.id.et_label)
    EditText etLabel;

    @BindView(R.id.cb_default)
    CheckBox cbDefault;

    @BindView(R.id.et_cc_number)
    EditText etCardNumber;

    @BindView(R.id.et_cvv)
    EditText etCvv;

    @BindView(R.id.et_zipcode)
    EditText etZip;

    @BindView(R.id.tv_date)
    EditText tvDate;

    @BindView(R.id.btn_add_new_creditcard)
    Button btnContinue;

    public static AddNewCreditCard newInstance(CardItem creditCardItem) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_1, creditCardItem);
        AddNewCreditCard fragment = new AddNewCreditCard();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_credit_card,
                container, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = inflater.getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize all views
        if (getArguments() != null) {
            if(getArguments().getSerializable(ARGS_1)!=null) {
                cardItem = (CardItem) getArguments().getSerializable(ARGS_1);
            }
        }
        if (cardItem != null) {
            setUpCardDetails();
        }
        setListeners();
    }

    private void setUpCardDetails() {
        etCardNumber.setText(cardItem.getCardNumber());
        tvDate.setText(cardItem.getExpiryDate());
        etZip.setText(cardItem.getZipCode());
        etCvv.setText(cardItem.getCvv());
        etLabel.setText(cardItem.getLabel());
        btnContinue.setText("Update");
        if (cardItem.isPrimary() == 1) {
            cbDefault.setChecked(true);
        }
    }

    private void setListeners() {
        tvDate.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void refreshToolbar() {
        Activities.hideBottomNavigation(context, true);
        ToolbarOp.refresh(getView(), getActivity(), getString(R.string.add_new_credit_card_capital),
                null, ToolbarOp.Theme.Dark, 0, null, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_date:
                showDatePicker();
                break;
            case R.id.btn_add_new_creditcard:
                KeyboardOp.hide(context);
                addCard();
                break;
        }
    }

    private void addCard() {

        etCardNumber.setError(null);
        etCvv.setError(null);
        tvDate.setError(null);
        etZip.setError(null);

        // Store values at the time of the SIGNUP attempt.
        String cardNumber = etCardNumber.getText().toString().trim();
        String cvv = etCvv.getText().toString().trim();
        String date = tvDate.getText().toString().trim();
        String zip = etZip.getText().toString().trim();
        String label = etLabel.getText().toString().trim();
        int is_primary = 0;
        if (cbDefault.isChecked()) {
            is_primary = 1;
        }

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(zip)) {
            etZip.setError(getString(R.string.error_field_required));
            focusView = etZip;
            cancel = true;
        }

        if (TextUtils.isEmpty(cvv)) {
            etCvv.setError(getString(R.string.error_field_required));
            focusView = etCvv;
            cancel = true;
        }

        if (TextUtils.isEmpty(date)) {
            tvDate.setError(getString(R.string.error_field_required));
            focusView = tvDate;
            cancel = true;
        }

        if (TextUtils.isEmpty(cardNumber)) {
            etCardNumber.setError(getString(R.string.error_field_required));
            focusView = etCardNumber;
            cancel = true;
        }
        else if (cardNumber.length()<12 || cardNumber.length()>19) {
            etCardNumber.setError(getString(R.string.error_creditcardnumberlength));
            focusView = etCardNumber;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            callSetCreditCardApi(cardNumber, cvv, date, zip, label, is_primary);
        }


    }

    private void showDatePicker() {

        Calendar now = Calendar.getInstance();
        if (!tvDate.getText().toString().isEmpty()) {
            now = DateTimeOp.getCalendarFromFormat(tvDate.getText().toString(), Constants.dateFormat22);
        }

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                AddNewCreditCard.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
        dpd.setCancelColor(ContextCompat.getColor(context, R.color.colorPrimary));
        dpd.setOkColor(ContextCompat.getColor(context, R.color.colorPrimary));
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        tvDate.setText(DateTimeOp.oneFormatToAnother(date, Constants.dateFormat6, Constants.dateFormat22));
        tvDate.setError(null);
    }

    private void callSetCreditCardApi(final String cardNumber, final String cvv, final String date, final String zip, final String label, final int is_primary) {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.TOKEN, UserSharedPreference.readUserToken().accessToken);

        serviceParams.put(Keys.CCNo, cardNumber);
        serviceParams.put(Keys.ExpiryDate, date);
        serviceParams.put(Keys.CCV, cvv);
        serviceParams.put(Keys.BillingCode, zip);
        serviceParams.put(Keys.Is_Primary, is_primary);
        serviceParams.put(Keys.Label, label);
        serviceParams.put(Keys.USER_ID, UserSharedPreference.readUserBO().getId());
        EnumUtils.ServiceName serviceName;
        if (cardItem == null) {
            serviceName = EnumUtils.ServiceName.AddCreditCard;
        } else {
            serviceParams.put(Keys.id, cardItem.getId());
            serviceName = EnumUtils.ServiceName.EditUserCreditCards;
        }


        new WebServicesVolleyTask(context, true, "",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.POST, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);

                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            String message = (cardItem == null) ? getString(R.string.card_added_sucessfully) : getString(R.string.card_updated_successfully);
                            SnackBarUtil.showSnackbar(context, message, false);
                            if(cardItem==null) {
                                cardItem=new CardItem(cardNumber,date,cvv,zip,label,is_primary);
                                EventBus.getDefault().post(cardItem);
                            }
                            Activities.goBackFragment(context, 1);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}