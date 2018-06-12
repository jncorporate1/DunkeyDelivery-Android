package app.com.dunkeydelivery.modules.cart.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.EventBusFragment;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.account.Items.Address;
import app.com.dunkeydelivery.modules.account.Items.CardItem;
import app.com.dunkeydelivery.modules.account.fragments.AddNewCreditCard;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomRadioButton;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;

import static app.com.dunkeydelivery.App.context;

public class CreditCardInformation extends ToolbarFragment implements View.OnClickListener {

    private Context context;
    private List<CardItem> cardItemList;

    @BindView(R.id.rg_credit_card)
    RadioGroup rgCreditCard;
    @BindView(R.id.rv_swipe_refresh)
    SwipeRefreshLayout rvSwipeRefresh;
    @BindView(R.id.tv_add_credit_card)
    TextView tvAddCreditCard;
    @BindView(R.id.btn_update)
    Button btnUpdate;

    public static CreditCardInformation newInstance() {
        CreditCardInformation fragment = new CreditCardInformation();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public void refreshData() {
        rvSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                callGetCreditCardApi(UserSharedPreference.readUserBO().getId());
            }
        });
    }

    @Override
    public void refreshToolbar() {
        Activities.hideBottomNavigation(context, true);
        ToolbarOp.refresh(getView(), getActivity(), getString(R.string.credit_card_info),
                null, ToolbarOp.Theme.Dark, 0, null, null);

        refreshData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_credit_card_information, container, false);
        context=inflater.getContext();
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners();

        rvSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callGetCreditCardApi(UserSharedPreference.readUserBO().getId());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        rvSwipeRefresh.post(new Runnable() {
//            @Override
//            public void run() {
//                stopRefreshing(true);
//                callGetCreditCardApi(UserSharedPreference.readUserBO().getId());
//            }
//        });
    }

    private void setListeners()
    {
        btnUpdate.setOnClickListener(this);
        tvAddCreditCard.setOnClickListener(this);
    }

    private void stopRefreshing(Boolean isRefreshing)
    {
        rvSwipeRefresh.setRefreshing(isRefreshing);
    }

    private void setUpData(List<CardItem> creditCardList) {
        cardItemList=creditCardList;
        rgCreditCard.removeAllViews();
        int id = 5;     //using this variable to set Id of radiobutton when radiobutton is added in layout.
        //id can be any number
        for ( CardItem cardItem : creditCardList) {
            CustomRadioButton customRadioButton = new CustomRadioButton(context);
            customRadioButton.setId(id++);
            customRadioButton.setText(cardItem.getCardNumber());
            customRadioButton.setTextColor(getResources().getColor(R.color.black));
            if (cardItem != null) {
                if (cardItem.isPrimary()==1) {
                    customRadioButton.setChecked(true);
                } else {
                    customRadioButton.setChecked(false);
                }
            }
            rgCreditCard.addView(customRadioButton);
        }
    }

    private void goToBackFragment(CardItem cardItem)
    {
        EventBus.getDefault().post(cardItem);
        Activities.goBackFragment(context,1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_add_credit_card:
            {
                Activities.gotoNextFragment(context,AddNewCreditCard.newInstance(null));
                break;
            }
            case R.id.btn_update:
            {
                try {
                    if (rgCreditCard != null && cardItemList != null) {
                        if (rgCreditCard.getChildCount() > 0) {
                            RadioButton radioButton = (RadioButton) rgCreditCard.findViewById(rgCreditCard.getCheckedRadioButtonId());
                            if (cardItemList.size() > 0) {
                                callUpdateCreditCardStatusApi(cardItemList.get(rgCreditCard.indexOfChild(radioButton)));
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void callGetCreditCardApi(String userId) {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.TOKEN, UserSharedPreference.readUserToken().accessToken);
        serviceParams.put(Keys.USER_ID, userId);


        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.GetCreditCards;

        new WebServicesVolleyTask(context, false, "",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);

                    if (taskItem.isError()) {
                        stopRefreshing(false);
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                stopRefreshing(false);
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                JSONArray jsonArray = jsonObject.getJSONArray("CreditCards");
                                Gson gson = new Gson();
                                Type typeToken = new TypeToken<List<CardItem>>() {
                                }.getType();
                                List<CardItem> cardItems = gson.fromJson(jsonArray.toString(), typeToken);
                                removeAllViews();
                                setUpData(cardItems);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
                else {
                    stopRefreshing(false);
                }
            }
        });
    }

    private void removeAllViews()
    {
        rgCreditCard.removeAllViews();
    }

    private void callUpdateCreditCardStatusApi(final CardItem cardItem) {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = null;


        serviceParams.put(Keys.USER_ID_3, UserSharedPreference.readUserBO().getId());
        serviceParams.put(Keys.Creditcard_Id, cardItem.getId());
        serviceParams.put(Keys.Mark, true);

        serviceParams.put(Keys.Authorization, UserSharedPreference.readUserToken().accessToken);

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.UpdateCreditCardById;

        new WebServicesVolleyTask(context, true, "",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);
                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {
                            goToBackFragment(cardItem);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }
}
