package app.com.dunkeydelivery.modules.home.tabs.pharmacy;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hedgehog.ratingbar.RatingBar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apmem.tools.layouts.FlowLayout;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.account.fragments.AddNewAddress;
import app.com.dunkeydelivery.modules.cart.fragments.CartMain;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.tabs.food.pager.StoreViewPager;
import app.com.dunkeydelivery.modules.home.tabs.pharmacy.adapters.MedicationAdapter;
import app.com.dunkeydelivery.modules.home.tabs.pharmacy.dialogs.SelectGenderDialog;
import app.com.dunkeydelivery.modules.home.tabs.pharmacy.interfaces.GenderTypeClickListener;
import app.com.dunkeydelivery.modules.home.tabs.pharmacy.items.Medication;
import app.com.dunkeydelivery.modules.search.Search;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.DateTimeOp;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.SnackBarUtil;
import app.com.dunkeydelivery.utils.StoreUtils;
import app.com.dunkeydelivery.utils.Validation;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.MenuItemImgOrStr;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PharmacyDetail extends ToolbarFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private boolean isHidden;
    public static String ARG_PARAM1 = "storeBO";
    private StoreBO storeBO;
    Unbinder unbinder;
    private Runnable runnable;
    private Handler handler;
    public static Boolean isActive = false;
    private TextWatcher textWatcher;

    private MedicationAdapter medicationAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.pb_search)
    ProgressBar progressBar;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_delivery_fee)
    TextView tvDeliveryFee;
    @BindView(R.id.tv_subtitle1)
    TextView tvMinOrder;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.flow_layout)
    FlowLayout flowLayout;
    @BindView(R.id.tv_rate)
    TextView tvRate;

    @BindView(R.id.ratingbar)
    RatingBar ratingBar;

    @BindView(R.id.ib_search)
    ImageButton ibSearch;

    @BindView(R.id.ib_review)
    ImageButton ibReview;

    @BindView(R.id.ib_info)
    ImageButton ibInfo;

    @BindView(R.id.ib_add)
    ImageButton ibAdd;

    @BindView(R.id.fl_tags)
    FlowLayout flTags;

    @BindView(R.id.et_medication)
    EditText etMedication;

    @BindView(R.id.et_dr_first_name)
    EditText etDrFirstName;
    @BindView(R.id.et_dr_last_name)
    EditText etDrLastName;
    @BindView(R.id.et_dr_phone)
    EditText etDrPhone;
    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;


    @BindView(R.id.tv_dob)
    EditText tvDate;

    @BindView(R.id.tv_gender)
    EditText tvGender;

    @BindView(R.id.iv_store)
    ImageView iv_store;

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private WebServicesVolleyTask asyncTask;

    private EnumUtils.Gender selectedGender = EnumUtils.Gender.None;

    ArrayList<Medication> medications = new ArrayList<>();
    Medication currentMedication;

    public static PharmacyDetail newInstance(StoreBO storeBO) {

        Bundle args = new Bundle();
        PharmacyDetail fragment = new PharmacyDetail();
        args.putParcelable(ARG_PARAM1, storeBO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pharmacy_detail, container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        handler = new Handler();
        if (getArguments() != null) {
            storeBO = getArguments().getParcelable(ARG_PARAM1);
        }
        // Initialize all views
        setUpStoreDetail();

        setListeners();
        isActive = true;


    }//onViewCreated

    private void setUpStoreDetail() {
        try {
            tvTitle.setText(storeBO.getBusinessName());
            tvRate.setText(storeBO.getAverageRating() + "");
            ratingBar.setClickable(false);
            ratingBar.setStar(storeBO.getAverageRating());
            tvMinOrder.setText(getString(R.string.min_order) + "  " + storeBO.getMinOrderPrice());

            if (!storeBO.getDistance().isEmpty())
                tvDistance.setText(storeBO.getDistance() + " m");
            else
                tvDistance.setVisibility(View.GONE);

            if (!storeBO.getMinDeliveryTime().isEmpty())
                tvTime.setText(storeBO.getMinDeliveryTime());
            else
                tvTime.setVisibility(View.GONE);
            //TODO: Set delivery fee..

//            ImageUtils.setFitXYImage(storeBO.getImageUrl(), iv_store, context, R.drawable.logo);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //add tags..
        StoreUtils.addStoreTags(context, flowLayout, storeBO.getStoreTags());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHidden = hidden;
        refreshToolbar();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        refreshToolbar();
    }

    private void setUpRecycler(final List<Medication> itemBOs) {

        if (medicationAdapter == null || recyclerView.getAdapter() == null) {

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            medicationAdapter = new MedicationAdapter(itemBOs, context, recyclerView);

//            mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
//                @Override
//                public void onLoadMore() {
////                    streamBOs = mAdapter.getItems();
//                    if (mAdapter.getItems().size() >= maxItems) {
//                        mAdapter.addLoadingItem();
//
//                        startIndex = mAdapter.getItems().size() - 2;
//                        callStreamsService(startIndex, maxItems, false);
//                    }
//                }
//            });

            medicationAdapter.setClickListener(new MedicationAdapter.ClickListeners() {
                @Override
                public void onRowClick(int position) {
                    Medication medication = medicationAdapter.getItem(position);
                    currentMedication = medication;
                    etMedication.removeTextChangedListener(textWatcher);
                    etMedication.setText(medication.getName());
                    etMedication.setSelection(etMedication.getText().length());
                    etMedication.addTextChangedListener(textWatcher);
                    showSearchList(false);
                }
            });

            recyclerView.setAdapter(medicationAdapter);
        } else {
            medicationAdapter.setItems(itemBOs);
        }
    }


    // method use to cancel task...
    private void cancelTask() {
        if (asyncTask != null) {
            asyncTask.cancelTask();
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }

    }

    private void setListeners() {
        ibInfo.setOnClickListener(this);
        ibReview.setOnClickListener(this);
        ibSearch.setOnClickListener(this);
        ibAdd.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        tvGender.setOnClickListener(this);

        etMedication.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    KeyboardOp.hide(context, etMedication);
                    final String search = etMedication.getText().toString().trim();
                    if (!TextUtils.isEmpty(search)) {
                        callGetMedicationsService(search);
                        return true;
                    }
                }
                return false;
            }
        });


        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String search = s.toString();
                if (search.isEmpty()) {
                    cancelTask();
                } else if (search.length() > 2) {
                    if (runnable != null) {
                        handler.removeCallbacks(runnable);
                        runnable = null;
                    }
                    runnable = new Runnable() {
                        @Override
                        public void run() {
//                                cancelTask();
                            callGetMedicationsService(search);
                        }
                    };
                    handler.postDelayed(runnable, 500);

                }
            }
        };

        etMedication.addTextChangedListener(textWatcher);
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isHidden) {
            refreshToolbar();
        }
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void refreshToolbar() {
        Log.i(TAG, "refreshToolbar: ");
        Activities.hideBottomNavigation(context, true);
        MenuItemImgOrStr menuItemImgOrStr = new MenuItemImgOrStr(R.drawable.ic_cart, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activities.gotoNextFragment(context, CartMain.newInstance());
            }
        });

        ToolbarOp.refresh(getView(), getActivity(), storeBO.getBusinessName(),
                null, ToolbarOp.Theme.Dark, 0, null, menuItemImgOrStr);
    }


    private void initViews(View view) {
        //Initialize main content Linear layout.

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_add:
                etMedication.setError(null);
                if (currentMedication != null) {
                    addTag(currentMedication);
                    currentMedication = null;
                } else if (etMedication.getText().toString().trim().equals("")) {
                    etMedication.setError(getString(R.string.error_field_required));
                } else {
                    if (medicationAdapter == null || medicationAdapter.getItemCount() == 0) {
                        etMedication.setError("Medicine not Found");
                    } else {
                        for (int i = 0; i < medicationAdapter.getItemCount(); i++) {
                            if (medicationAdapter.getItem(i).getName().equalsIgnoreCase(etMedication.getText().toString())) {
                                currentMedication = medicationAdapter.getItem(i);
                                break;
                            }
                        }
                        if (currentMedication == null) {
                            etMedication.setError("Select Medication");
                        } else {
                            addTag(currentMedication);
                        }
                        currentMedication = null;
                    }
                }


                break;
            case R.id.ib_search:
                Activities.gotoNextFragment(context, Search.newInstanceForSearch(true, storeBO));
                break;
            case R.id.ib_review:
                Activities.gotoNextFragment(context, StoreViewPager.newInstance(false, false, storeBO));
                break;
            case R.id.ib_info:
                Activities.gotoNextFragment(context, StoreViewPager.newInstance(true, false, storeBO));
                break;
            case R.id.tv_dob:
                showDatePicker();
                break;
            case R.id.tv_gender:
                showGenderPopup();
                break;
            case R.id.btn_submit:
                submitData();
                break;
        }
    }

    private void submitData() {
        etDrFirstName.setError(null);
        etDrLastName.setError(null);
        etDrPhone.setError(null);
        etFirstName.setError(null);
        etLastName.setError(null);
        tvDate.setError(null);
        tvGender.setError(null);

        // Store values at the time of the SIGNUP attempt.
        String drFirstName = etDrFirstName.getText().toString().trim();
        String drLastName = etDrLastName.getText().toString().trim();
        String drPhone = etDrPhone.getText().toString().trim();
        String ptFirstName = etFirstName.getText().toString().trim();
        String ptLastName = etLastName.getText().toString().trim();
        String dob = tvDate.getText().toString().trim();
        String gender = tvGender.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(gender)) {
            tvGender.setError(getString(R.string.error_field_required));
            focusView = tvGender;
            cancel = true;
        }

        if (TextUtils.isEmpty(dob)) {
            tvDate.setError(getString(R.string.error_field_required));
            focusView = tvDate;
            cancel = true;
        }

        if (TextUtils.isEmpty(ptLastName)) {
            etLastName.setError(getString(R.string.error_field_required));
            focusView = etLastName;
            cancel = true;
        }

        if (TextUtils.isEmpty(ptFirstName)) {
            etFirstName.setError(getString(R.string.error_field_required));
            focusView = etFirstName;
            cancel = true;
        }

        if (TextUtils.isEmpty(drPhone)) {
            etDrPhone.setError(getString(R.string.error_field_required));
            focusView = etDrPhone;
            cancel = true;
        } else if (!Validation.isPhoneValid(drPhone)) {
            etDrPhone.setError(getString(R.string.error_invalid_phone));
            focusView = etDrPhone;
            cancel = true;
        }

        if (TextUtils.isEmpty(drLastName)) {
            etLastName.setError(getString(R.string.error_field_required));
            focusView = etLastName;
            cancel = true;
        }

        if (TextUtils.isEmpty(drFirstName)) {
            etDrFirstName.setError(getString(R.string.error_field_required));
            focusView = etDrFirstName;
            cancel = true;
        }

        if (medications == null || medications.size() == 0) {
            etMedication.setError(getString(R.string.error_field_medication));
            focusView = etMedication;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null)
                focusView.requestFocus();
        } else {

            ArrayList<Integer> products = new ArrayList<>();

            for (Medication medication : medications) {
                products.add(medication.getId());
            }

            callSubmitPharmacyRequest(drFirstName, drLastName, drPhone, ptFirstName, ptLastName, dob, selectedGender.getNumVal(), products);
        }

    }

    //this method is used to get medications from service...
    public void callSubmitPharmacyRequest(String doctorFN, String doctorLN, String doctorPhoneNumber, String patientFN, String patientLN, String patientDOB, int gender, ArrayList<Integer> products) {

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<String, Object>();

        StringBuilder productIds = new StringBuilder();

        for (int i = 0; i < products.size(); i++) {
            if (productIds.length() == 0)
                productIds = new StringBuilder(String.valueOf(products.get(i)));
            else
                productIds.append(", ").append(products.get(i));
        }

        /*JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < products.size(); i++)
            jsonArray.put(products.get(i));*/

        serviceParams.put(Keys.PRODUCT_ID, productIds.toString());

        serviceParams.put(Keys.DOCTOR_FIRST_NAME, doctorFN);
        serviceParams.put(Keys.DOCTOR_LAST_NAME, doctorLN);
        serviceParams.put(Keys.DOCTOR_PHONE, doctorPhoneNumber);
        serviceParams.put(Keys.PATIENT_FIRST_NAME, patientFN);
        serviceParams.put(Keys.PATIENT_LAST_NAME, patientLN);
        serviceParams.put(Keys.PATIENT_DOB, patientDOB);
        serviceParams.put(Keys.GENDER, gender);
        serviceParams.put(Keys.USER_ID_3, UserSharedPreference.readUserBO().getId());

        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().getAccessToken());

        asyncTask = new WebServicesVolleyTask(context, true, "",
                EnumUtils.ServiceName.SubmitPharmacyRequestMobile,
                EnumUtils.ServiceName.getServicePath(EnumUtils.ServiceName.SubmitPharmacyRequestMobile),
                EnumUtils.RequestMethod.POST, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    if (taskItem.isError()) {
                        showSearchList(false);
                        //show alert only on the selected tab...

                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                        if (taskItem.getErrorCode() == 404 && taskItem.getServiceStatusMessage().equalsIgnoreCase("User addresses not found."))
                            Activities.gotoNextFragment(context, AddNewAddress.newInstance(null));
                    } else {
                        try {
                            if (taskItem.getResponse() != null) {

                                SnackBarUtil.showSnackbar(context, getString(R.string.detail_submitted), false);
                                Activities.goBackFragment(context, 1);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }//end of GetStores method

    private void showGenderPopup() {
        KeyboardOp.hide(context, etMedication);
        SelectGenderDialog dFragment = SelectGenderDialog.newInstance(selectedGender.ordinal(),
                new GenderTypeClickListener() {
                    @Override
                    public void onGender(EnumUtils.Gender gender) {
                        selectedGender = gender;
                        tvGender.setText(EnumUtils.Gender.getLabel(selectedGender.ordinal(), context));
                        tvGender.setError(null);
                    }
                });
        dFragment.show(getActivity().getSupportFragmentManager(), "");
    }

    private void showDatePicker() {
        /*Calendar now = Calendar.getInstance();
        if (!tvDate.getText().toString().isEmpty()) {
            now = DateTimeOp.getCalendarFromFormat(tvDate.getText().toString(), Constants.dateFormat0);
        }

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                PharmacyDetail.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
        dpd.setCancelColor(ContextCompat.getColor(context, R.color.colorPrimary));
        dpd.setOkColor(ContextCompat.getColor(context, R.color.colorPrimary));
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");*/


        Calendar now = Calendar.getInstance();
        if (!tvDate.getText().toString().isEmpty()) {
            now = DateTimeOp.getCalendarFromFormat(tvDate.getText().toString(), Constants.dateFormat0);
        }
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
    }

    private boolean isAddMedication() {
        if (medications != null && medications.size() > 4) {
            return false;
        }
        return true;
    }

    private void addTag(Medication medication) {
        etMedication.setText("");
        KeyboardOp.hide(context, etMedication);

        medications.add(medication);

        View view = LayoutInflater.from(context).inflate(R.layout.item_tag_pharmacy, null);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 4, 4, 0);
        view.setLayoutParams(lp);

        view.setTag(medications.size() - 1);
        TextView tvTag = (TextView) view.findViewById(R.id.tv_tag);
        tvTag.setText(medication.getName());

        LinearLayout llRoot = (LinearLayout) view.findViewById(R.id.ll_root);

        GradientDrawable bgShape = (GradientDrawable) llRoot.getBackground();
        int colorIndex = 0;
        if ((medications.size() - 1) % 2 == 0) {
            colorIndex = 0;
        } else {
            colorIndex = 2;
        }
        bgShape.setColor(ContextCompat.getColor(context, ImageUtils.tagColors[colorIndex]));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    try {
                        int index = (Integer) v.getTag();
                        flTags.removeViewAt(index);
                        medications.remove(index);
                        for (int i = 0; i < flTags.getChildCount(); i++) {
                            flTags.getChildAt(i).setTag(i);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        flTags.addView(view);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        tvDate.setText(DateTimeOp.oneFormatToAnother(date, Constants.dateFormat6, Constants.dateFormat29));
        tvDate.setError(null);
    }

    //this method is used to get medications from service...
    public void callGetMedicationsService(final String searchString) {

        progressBar.setVisibility(View.VISIBLE);
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = null;

        serviceParams.put(Keys.Store_id, storeBO.getId());
        serviceParams.put(Keys.search_string, searchString);

        asyncTask = new WebServicesVolleyTask(context, false, "",
                EnumUtils.ServiceName.GetMedicationNames,
                EnumUtils.ServiceName.getServicePath(EnumUtils.ServiceName.GetMedicationNames),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    progressBar.setVisibility(View.GONE);
                    if (taskItem.isError()) {
                        showSearchList(false);
                        //show alert only on the selected tab...
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                JSONArray medications = jsonObject.getJSONArray("medications");
                                Gson gson = new Gson();

                                if (medications.length() > 0) {

                                    showSearchList(true);
                                    Type listType = new TypeToken<List<Medication>>() {
                                    }.getType();
                                    List<Medication> medicationList = (List<Medication>) gson.fromJson(medications.toString(),
                                            listType);
                                    setUpRecycler(medicationList);

                                } else {
                                    showSearchList(false);
                                }
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }//end of GetStores method

    private void showSearchList(boolean isShow) {
        if (isShow) {
            recyclerView.setVisibility(View.VISIBLE);
        } else
            recyclerView.setVisibility(View.GONE);
    }
}