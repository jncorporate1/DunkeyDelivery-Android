package app.com.dunkeydelivery.modules.account.fragments.pager;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.EventBusFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.account.Items.CardItem;
import app.com.dunkeydelivery.modules.account.adapters.CreditCardListAdapter;
import app.com.dunkeydelivery.modules.account.fragments.AddNewCreditCard;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.ColorOp;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.SnackBarUtil;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CreditCard extends EventBusFragment {
    private Context context;
    Unbinder unbinder;
    @BindView(R.id.tv_add_credit_card)
    TextView tvAddCreditCard;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.rv_swipe_refresh)
    SwipeRefreshLayout rv_swipe_refresh;
    private CreditCardListAdapter mAdapter;

    public static CreditCard newInstance() {
        Bundle args = new Bundle();
        CreditCard fragment = new CreditCard();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_creditcard,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize all views
        rv_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callGetCreditCardApi(UserSharedPreference.readUserBO().getId());
            }
        });

        rv_swipe_refresh.setColorSchemeColors(
                ColorOp.getColor(context, R.color.colorPrimary),
                ColorOp.getColor(context, R.color.colorAccent),
                ColorOp.getColor(context, R.color.colorPrimaryDark));

        tvAddCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activities.gotoNextFragment(context, AddNewCreditCard.newInstance(null));
            }
        });
    }

    //get creditCards information from api
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
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                JSONArray jsonArray = jsonObject.getJSONArray("CreditCards");
                                Gson gson = new Gson();
                                Type typeToken = new TypeToken<List<CardItem>>() {
                                }.getType();
                                List<CardItem> cardItems = gson.fromJson(jsonArray.toString(), typeToken);
                                setUpRecycler(cardItems);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        updateSwipeLoader(false);
                        // if response is successful then do something
                    }
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        rv_swipe_refresh.post(new Runnable() {
            @Override
            public void run() {
                updateSwipeLoader(true);
                callGetCreditCardApi(UserSharedPreference.readUserBO().getId());

            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    //setup cardItems list
    private void setUpRecycler(final List<CardItem> cardItems) {

        if (mAdapter == null || recyclerView.getAdapter() == null) {

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            mAdapter = new CreditCardListAdapter(cardItems, context, recyclerView);

            mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {

                }
            });

            mAdapter.setClickListener(new CreditCardListAdapter.ClickListeners() {
                @Override
                public void onRowClick(int position) {
                    //setting the dummy data for alpha..
                    CardItem creditCardItem = mAdapter.getItem(position);
                    Activities.gotoNextFragment(context, AddNewCreditCard.newInstance(creditCardItem));
                }

                @Override
                public void onRowDelete(final int position, final SwipeLayout swipeLayout) {
                    AlertOP.showAlert(context, "", context.getString(R.string.delete_creditcard), "Yes", "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            callDeleteCreditCardApi(mAdapter.getItem(position).getId());
                            SnackBarUtil.showSnackbar(context, context.getString(R.string.card_deleted_success), true);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            swipeLayout.close();
                        }
                    });
                }
            });

            recyclerView.setAdapter(mAdapter);
        } else {
            if (mAdapter.getItemCount() > 0) {
                mAdapter.clearItems();
            }
            mAdapter.addItems(cardItems);
            mAdapter.setLoaded();
        }
    }

    //set swipe loader refreshing
    private void updateSwipeLoader(boolean showloader) {
        rv_swipe_refresh.setRefreshing(showloader);
    }

    //delete creditCard Api
    private void callDeleteCreditCardApi(int creditCardId) {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.TOKEN, UserSharedPreference.readUserToken().accessToken);

        serviceParams.put(Keys.Card_Id, creditCardId);
        serviceParams.put(Keys.USER_ID, UserSharedPreference.readUserBO().getId());

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.RemoveCreditCard;

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
                            updateSwipeLoader(true);
                            callGetCreditCardApi(UserSharedPreference.readUserBO().getId());

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }

    @Override
    public void refreshData() {
        updateSwipeLoader(true);
        callGetCreditCardApi(UserSharedPreference.readUserBO().getId());
    }
}