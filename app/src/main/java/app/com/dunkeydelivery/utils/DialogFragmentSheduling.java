package app.com.dunkeydelivery.utils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.items.DeliveryTypes;
import app.com.dunkeydelivery.modules.home.tabs.laundry.LaundryStoreDetail;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Developer on 3/13/2018.
 */

public class DialogFragmentSheduling extends DialogFragment implements CompoundButton.OnCheckedChangeListener
        , View.OnClickListener, DatePickerDialog.OnDateSetListener
        , TimePickerDialog.OnTimeSetListener {

    private Context context;
    private ArrayList<DeliveryTypes> deliveryTypes;
    private String selectedTime;
    private String orderDateAndTime;
    private String selectedDate;
    private String arr[];
    private String []hoursFrom;
    private String []hoursTo;
    private int id;
    private SetDialogDismissListener setDialogDismissListener;

    @BindView(R.id.rb_asap)
    RadioButton rbASAP;

    @BindView(R.id.rb_today)
    RadioButton rbToday;

    @BindView(R.id.rb_later)
    RadioButton rbLater;

    @BindView(R.id.lineOptions)
    View lineOptions;

    @BindView(R.id.dateLine)
    View dateLine;

    @BindView(R.id.lineTime)
    View lineTime;

    @BindView(R.id.ll_date)
    LinearLayout llDate;

    @BindView(R.id.ll_time)
    LinearLayout llTime;

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.tv_select_time)
    TextView tvSelectTime;

    public static DialogFragmentSheduling newInstance(ArrayList<DeliveryTypes> deliveryTypes,String [] hoursFrom,String [] hoursTo) {
        Bundle bundle = new Bundle();
        DialogFragmentSheduling dialogFragmentSheduling = new DialogFragmentSheduling();
        bundle.putParcelableArrayList("deliveryTypes", deliveryTypes);
        bundle.putStringArray("hoursFrom",hoursFrom);
        bundle.putStringArray("hoursTo",hoursTo);
        dialogFragmentSheduling.setArguments(bundle);
        return dialogFragmentSheduling;
    }

    public static DialogFragmentSheduling newInstance(ArrayList<DeliveryTypes> deliveryTypes, String orderDateAndTime, Integer typeId,String []hoursFrom,String []hoursTo) {
        Bundle bundle = new Bundle();
        DialogFragmentSheduling dialogFragmentSheduling = new DialogFragmentSheduling();
        bundle.putParcelableArrayList("deliveryTypes", deliveryTypes);
        bundle.putString("orderDateAndTime", orderDateAndTime);
        bundle.putInt("id", typeId);
        bundle.putStringArray("hoursFrom",hoursFrom);
        bundle.putStringArray("hoursTo",hoursTo);
        dialogFragmentSheduling.setArguments(bundle);
        return dialogFragmentSheduling;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sheduling_layout, container, false);
        context = inflater.getContext();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            if (getArguments().getParcelableArrayList("deliveryTypes") != null) {
                deliveryTypes = getArguments().getParcelableArrayList("deliveryTypes");
                hoursFrom=getArguments().getStringArray("hoursFrom");
                hoursTo=getArguments().getStringArray("hoursTo");
            }
            if (getArguments().getString("orderDateAndTime") != null) {
                orderDateAndTime = getArguments().getString("orderDateAndTime");
                id = getArguments().getInt("id");
                arr = orderDateAndTime.trim().split(" ");
                hoursFrom=getArguments().getStringArray("hoursFrom");
                hoursTo=getArguments().getStringArray("hoursTo");
            }
        }

        selectedDate = " ";
        selectedTime = " ";

        setVisibility();
        setListeners();
    }

    //set previous selected shedule type and date , time
    private void setPreviosSelectedShedule() {
        for (DeliveryTypes deliveryType : deliveryTypes) {
            try {
                if (Integer.parseInt(deliveryType.getType_Id()) == id && deliveryType.getType_Name().trim().equals("ASAP")) {
                    rbASAP.setChecked(true);

                    setASAP();
                } else if (Integer.parseInt(deliveryType.getType_Id()) == id && deliveryType.getType_Name().trim().equals("Today")) {
                    rbToday.setChecked(true);

                    setToday();

                    selectedTime = arr[1];
                    selectedDate = arr[0];

                    tvSelectTime.setText(DateTimeOp.oneFormatToAnother(arr[1] + ":00", Constants.dateFormat10, Constants.dateFormat4));
                } else if (Integer.parseInt(deliveryType.getType_Id()) == id && deliveryType.getType_Name().trim().equals("Later")) {
                    rbLater.setChecked(true);

                    setLater();

                    selectedTime = arr[1];
                    selectedDate = arr[0];

                    tvDate.setText(DateTimeOp.oneFormatToAnother(arr[0], Constants.dateFormat16, Constants.dateFormat3));
                    tvSelectTime.setText(DateTimeOp.oneFormatToAnother(arr[1] + ":00", Constants.dateFormat10, Constants.dateFormat4));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(arr!=null && id!=3) {
            if (arr != null && id != 0) {
                setPreviosSelectedShedule();
            }
        }

        setScheduleTypes();
    }

    //set shedule type available for store
    private void setScheduleTypes() {
        if(deliveryTypes!=null && deliveryTypes.size()>0)
        {
            if(deliveryTypes.size()<3)
            {
                if (deliveryTypes.size() == 1) {
                    try {

                        if (deliveryTypes.get(0).getType_Name().trim().equals(getResources().getString(R.string.asap))) {

                            rbASAP.setTag(deliveryTypes.get(0).getType_Id());
                            rbASAP.setBackground(getResources().getDrawable(R.drawable.full_radio_selector));
                            rbASAP.setVisibility(View.VISIBLE);

                        } else if (deliveryTypes.get(0).getType_Name().trim().equals(getResources().getString(R.string.today))) {

                            rbToday.setTag(deliveryTypes.get(0).getType_Id());
                            rbToday.setBackground(getResources().getDrawable(R.drawable.full_radio_selector));
                            rbToday.setVisibility(View.VISIBLE);

                        } else if (deliveryTypes.get(0).getType_Name().trim().equals(getResources().getString(R.string.later))) {
                            rbLater.setTag(deliveryTypes.get(0).getType_Id());
                            rbLater.setBackground(getResources().getDrawable(R.drawable.full_radio_selector));
                            rbLater.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (deliveryTypes.size() == 2) {
                    try {
                        if (deliveryTypes.get(0).getType_Name().trim().equals(getResources().getString(R.string.asap))) {
                            rbASAP.setTag(deliveryTypes.get(0).getType_Id());
                            rbASAP.setBackground(getResources().getDrawable(R.drawable.left_radio_selector));
                            rbASAP.setVisibility(View.VISIBLE);
                            if (deliveryTypes.get(1).getType_Name().trim().equals(getResources().getString(R.string.today))) {
                                rbToday.setTag(deliveryTypes.get(1).getType_Id());
                                rbToday.setBackground(getResources().getDrawable(R.drawable.right_radio_selector));
                                rbToday.setVisibility(View.VISIBLE);

                            } else if (deliveryTypes.get(1).getType_Name().trim().equals(getResources().getString(R.string.later))) {
                                rbLater.setTag(deliveryTypes.get(1).getType_Id());
                                rbLater.setBackground(getResources().getDrawable(R.drawable.right_radio_selector));
                                rbLater.setVisibility(View.VISIBLE);
                            }
                        } else if (deliveryTypes.get(0).getType_Name().trim().equals(getResources().getString(R.string.today))) {
                            rbToday.setTag(deliveryTypes.get(0).getType_Id());
                            rbToday.setBackground(getResources().getDrawable(R.drawable.left_radio_selector));
                            rbToday.setVisibility(View.VISIBLE);
                            if (deliveryTypes.get(1).getType_Name().trim().equals(getResources().getString(R.string.asap))) {
                                rbASAP.setTag(deliveryTypes.get(1).getType_Id());
                                rbASAP.setBackground(getResources().getDrawable(R.drawable.left_radio_selector));
                                rbToday.setBackground(getResources().getDrawable(R.drawable.right_radio_selector));
                                rbASAP.setVisibility(View.VISIBLE);
                            } else if (deliveryTypes.get(1).getType_Name().trim().equals(getResources().getString(R.string.later))) {
                                rbLater.setTag(deliveryTypes.get(1).getType_Id());
                                rbLater.setBackground(getResources().getDrawable(R.drawable.right_radio_selector));
                                rbLater.setVisibility(View.VISIBLE);
                            }
                        } else if (deliveryTypes.get(0).getType_Name().trim().equals(getResources().getString(R.string.later))) {
                            rbLater.setTag(deliveryTypes.get(0).getType_Id());
                            rbLater.setBackground(getResources().getDrawable(R.drawable.left_radio_selector));
                            rbLater.setVisibility(View.VISIBLE);
                            if (deliveryTypes.get(1).getType_Name().trim().equals(getResources().getString(R.string.asap))) {
                                rbASAP.setTag(deliveryTypes.get(1).getType_Id());
                                rbASAP.setBackground(getResources().getDrawable(R.drawable.left_radio_selector));
                                rbLater.setBackground(getResources().getDrawable(R.drawable.right_radio_selector));
                                rbASAP.setVisibility(View.VISIBLE);
                            } else if (deliveryTypes.get(1).getType_Name().trim().equals(getResources().getString(R.string.today))) {
                                rbToday.setTag(deliveryTypes.get(1).getType_Id());
                                rbToday.setBackground(getResources().getDrawable(R.drawable.right_radio_selector));
                                rbToday.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            else
            {
                setAllScheduleTypes();
            }
        }
        if(arr==null)
        {
            try {
                if (rbASAP.getVisibility() == View.VISIBLE) {
                    if ((rbToday.getVisibility() == View.VISIBLE && !rbToday.isChecked()) || (rbLater.getVisibility() == View.VISIBLE && !rbLater.isChecked())) {
                        rbASAP.setChecked(true);
                    } else {
                        rbASAP.setChecked(true);
                    }
                } else if (rbToday.getVisibility() == View.VISIBLE) {
                    if ((rbASAP.getVisibility() == View.VISIBLE && !rbASAP.isChecked())) {
                        rbASAP.setChecked(true);
                    } else if ((rbLater.getVisibility() == View.VISIBLE && !rbLater.isChecked())) {
                        rbToday.setChecked(true);
                    } else {
                        rbToday.setChecked(true);
                    }
                } else if (rbLater.getVisibility() == View.VISIBLE) {
                    if ((rbASAP.getVisibility() == View.VISIBLE && !rbASAP.isChecked())) {
                        rbASAP.setChecked(true);
                    } else if ((rbToday.getVisibility() == View.VISIBLE && !rbLater.isChecked())) {
                        rbToday.setChecked(true);
                    } else {
                        rbLater.setChecked(true);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    //setup all shedule types
    private void setAllScheduleTypes()
    {
        rbASAP.setTag("0");
        rbASAP.setVisibility(View.VISIBLE);
        rbToday.setTag("1");
        rbToday.setVisibility(View.VISIBLE);
        rbLater.setTag("2");
        rbLater.setVisibility(View.VISIBLE);
    }

    //set widgets visibility on dialog open
    private void setVisibility() {
        lineOptions.setVisibility(View.VISIBLE);
        dateLine.setVisibility(View.GONE);
        lineTime.setVisibility(View.GONE);
        llDate.setVisibility(View.GONE);
        llTime.setVisibility(View.GONE);
    }

    //set listeners
    private void setListeners() {
        rbASAP.setOnCheckedChangeListener(this);
        rbToday.setOnCheckedChangeListener(this);
        rbLater.setOnCheckedChangeListener(this);
        btnSubmit.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvSelectTime.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.rb_asap && isChecked) {
            setASAP();
        } else if (buttonView.getId() == R.id.rb_today && isChecked) {
            setToday();
        } else if (buttonView.getId() == R.id.rb_later && isChecked) {
            setLater();
        }
    }

    //set widgets visibility for asap
    private void setASAP()
    {
        lineOptions.setVisibility(View.VISIBLE);
        dateLine.setVisibility(View.GONE);
        lineTime.setVisibility(View.GONE);
        llDate.setVisibility(View.GONE);
        llTime.setVisibility(View.GONE);
    }

    //set widgets visibility for today
    private void setToday() {
        lineOptions.setVisibility(View.GONE);
        dateLine.setVisibility(View.VISIBLE);
        llDate.setVisibility(View.GONE);
        llTime.setVisibility(View.VISIBLE);
        lineTime.setVisibility(View.VISIBLE);
    }

    //set widgets visibility for later
    private void setLater() {
        lineOptions.setVisibility(View.VISIBLE);
        dateLine.setVisibility(View.VISIBLE);
        llDate.setVisibility(View.VISIBLE);
        llTime.setVisibility(View.VISIBLE);
        lineTime.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            try {
                dismiss();
                setSchedule();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.tv_date) {
            try {
                showDatePicker();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.tv_select_time) {
            try {
                showTimePicker();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    //set schedule value for ASAP , Today , Later
    private void setSchedule() {
        if (rbASAP.isChecked()) {
            setDialogDismissListener.setASAPListener((String) rbASAP.getTag());
        } else if (rbToday.isChecked()) {
            if (!selectedTime.equals(" ") && !tvSelectTime.getText().toString().trim().equals(null)) {
                setDialogDismissListener.setTodayListener(selectedTime, (String) rbToday.getTag());
            } else {
                SnackBarUtil.showSnackbar(getActivity(), getResources().getString(R.string.time_required), true);
            }
        } else if (rbLater.isChecked()) {
            if ((selectedTime.equals(" ")
                    &&
                    tvSelectTime.getText().toString().trim().equals("")) &&
                    (selectedDate.equals(" ")
                    &&
                    tvDate.getText().toString().trim().equals("")))
            {
                SnackBarUtil.showSnackbar(getActivity(),getResources().getString(R.string.time_date_required),true);
            }
            else if(selectedTime.equals(" ")
                    &&
                    tvSelectTime.getText().toString().trim().equals("")
                    )
            {
                SnackBarUtil.showSnackbar(getActivity(),getResources().getString(R.string.time_required),true);
            }
            else if(selectedDate.equals(" ")
                    &&
                    tvDate.getText().toString().trim().equals(""))
            {
                SnackBarUtil.showSnackbar(getActivity(),getResources().getString(R.string.date_required),true);
            }
            else {
                setDialogDismissListener.setLaterListener(selectedDate,selectedTime,(String)rbLater.getTag());
            }
        }
        else
        {
            SnackBarUtil.showSnackbar(getActivity(),getResources().getString(R.string.no_shedule),true);
        }
    }

    //initialize dialogDismissListener
    public void setLisenter(SetDialogDismissListener setDialogDismissListener)
    {
        this.setDialogDismissListener=setDialogDismissListener;
    }

    //interface for schedule type listeners
    public interface SetDialogDismissListener
    {
        public void setASAPListener(String scheduleTypeId);
        public void setTodayListener(String selectedTime,String scheduleTypeId);
        public void setLaterListener(String selectedDate,String selectedTime,String scheduleTypeId);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        String time = hourString + ":" + minuteString + ":" + secondString;
        selectedTime = hourString+":"+minuteString;
        tvSelectTime.setText(DateTimeOp.oneFormatToAnother(time, Constants.dateFormat10, Constants.dateFormat4));
    }

    //setup and show time picker dialog
    private void showTimePicker() {
        try {
            int hoursOfDay;
            int min;
            Calendar now = DateTimeOp.getCalendarFromFormat(tvSelectTime.getText().toString(), Constants.dateFormat4);
            if (now == null) {
                now = Calendar.getInstance();
            }
            hoursOfDay = now.get(Calendar.HOUR_OF_DAY);
            min = now.get(Calendar.MINUTE);
            if (arr == null) {
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        this,
                        hoursOfDay,
                        min,
                        true
                );
                tpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
                tpd.setVersion(TimePickerDialog.Version.VERSION_2);
                if ((hoursFrom != null && hoursFrom.length > 0) && (hoursTo != null && hoursTo.length > 0)) {
                    tpd.setMaxTime(Integer.parseInt(hoursTo[0]), Integer.parseInt(hoursTo[1]), Integer.parseInt(hoursTo[2]));
                    tpd.setMinTime(Integer.parseInt(hoursFrom[0]), Integer.parseInt(hoursFrom[1]), Integer.parseInt(hoursFrom[2]));
                }
                tpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
                tpd.setCancelColor(ContextCompat.getColor(context, R.color.colorPrimary));
                tpd.setOkColor(ContextCompat.getColor(context, R.color.colorPrimary));
                tpd.show(getActivity().getFragmentManager(), "TimePickerDialog");
            } else {
                if (arr != null)
                {
                    hoursOfDay = Integer.parseInt(arr[1].trim().split(":")[0]);
                    min = Integer.parseInt(arr[1].trim().split(":")[1]);
                }
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        this,
                        hoursOfDay
                        ,
                        min,
                        true
                );
                if ((hoursFrom != null && hoursFrom.length > 0) && (hoursTo != null && hoursTo.length > 0)) {
                    tpd.setMaxTime(Integer.parseInt(hoursTo[0]), Integer.parseInt(hoursTo[1]), Integer.parseInt(hoursTo[2]));
                    tpd.setMinTime(Integer.parseInt(hoursFrom[0]), Integer.parseInt(hoursFrom[1]), Integer.parseInt(hoursFrom[2]));
                }
                tpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
                tpd.setVersion(TimePickerDialog.Version.VERSION_2);
                tpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
                tpd.setCancelColor(ContextCompat.getColor(context, R.color.colorPrimary));
                tpd.setOkColor(ContextCompat.getColor(context, R.color.colorPrimary));
                tpd.show(getActivity().getFragmentManager(), "TimePickerDialog");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void showDatePicker() {
        try {
            if (arr == null) {
                Calendar now = DateTimeOp.getCalendarFromFormat(tvDate.getText().toString(), Constants.dateFormat3);
                if (now == null) {
                    now = Calendar.getInstance();
                }
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
                dpd.setVersion(com.wdullaer.materialdatetimepicker.date.DatePickerDialog.Version.VERSION_2);
                dpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
                dpd.setCancelColor(ContextCompat.getColor(context, R.color.colorPrimary));
                dpd.setOkColor(ContextCompat.getColor(context, R.color.colorPrimary));
                dpd.setMinDate(DateTimeOp.getCalendarFromFormat(DateTimeOp.getCurrentDateTime(Constants.dateFormat3), Constants.dateFormat3));
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            } else {
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        this,
                        Integer.parseInt(arr[0].trim().split("-")[0]),
                        Integer.parseInt(arr[0].trim().split("-")[1]),
                        Integer.parseInt(arr[0].trim().split("-")[2])
                );
                dpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
                dpd.setVersion(com.wdullaer.materialdatetimepicker.date.DatePickerDialog.Version.VERSION_2);
                dpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
                dpd.setCancelColor(ContextCompat.getColor(context, R.color.colorPrimary));
                dpd.setOkColor(ContextCompat.getColor(context, R.color.colorPrimary));
                dpd.setMinDate(DateTimeOp.getCalendarFromFormat(DateTimeOp.getCurrentDateTime(Constants.dateFormat3), Constants.dateFormat3));
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        tvDate.setText(DateTimeOp.oneFormatToAnother(date, Constants.dateFormat6, Constants.dateFormat3));
    }
}
