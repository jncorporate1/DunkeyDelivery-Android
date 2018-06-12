package app.com.dunkeydelivery.modules.points.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.adapters.ListAdapter;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.points.Adapters.RewardsListAdapter;
import app.com.dunkeydelivery.modules.points.items.Points;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.PixelsOp;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentPoints extends ToolbarFragment {

    @BindView(R.id.rv_showReward)
    RecyclerView rvShowReward;

    @BindView(R.id.ll_showRewardPoints)
    LinearLayout llShowRewardPoints;

    @BindView(R.id.progress_bar_points)
    ProgressBar progressBarPoints;

    @BindView(R.id.progress_bar_points1)
    ProgressBar progressBarPoints1;

    @BindView(R.id.progress_bar_points2)
    ProgressBar progressBarPoints2;

    @BindView(R.id.progress_bar_points4)
    ProgressBar progressBarPoints4;

    RewardsListAdapter rewardsListAdapter;

    private Points points;
    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private ListAdapter mAdapter;
    private int totalRecords = 0;
    private ProgressDialog progressDialog;
    private int maxItems = Constants.MAX_ITEMS_TO_LOAD;
    private int startIndex = 0;
    private boolean isHidden = false;
    private Unbinder unbinder;

    public static FragmentPoints newInstance() {
        Bundle args = new Bundle();
        FragmentPoints fragment = new FragmentPoints();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_points, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = inflater.getContext();
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpProgressDialog();
        callGetUserPointsApi();
        // Initialize all views
    }

    private void setUpProgressDialog() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading Points");
        progressDialog.setTitle("Please Wait");
    }

    private void handleProgressDialog(Boolean isShown)
    {
        if(isShown) {
            progressDialog.show();
        }
        else
        {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHidden = hidden;
        refreshToolbar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void setRewardPoints() {
        String longRewardPointsToString = String.valueOf(points.getUserPoints().getRewardPoints());
        //String longRewardPointsToString = "98000";
        int rewardValueCount;
        int padding = (int) PixelsOp.pxFromDp(context, 15.0f);

        if (llShowRewardPoints.getChildCount() > 0)
            llShowRewardPoints.removeAllViews();

        if(longRewardPointsToString.length()>5)
        {
            rewardValueCount=5;
        }
        else
        {
            rewardValueCount=longRewardPointsToString.length();
        }

        for (int i = 0; i < rewardValueCount ; i++) {

            CustomTextView customTextView1 = new CustomTextView(context);
            customTextView1.setText(String.valueOf(longRewardPointsToString.charAt(i)));
            customTextView1.setTextColor(getResources().getColor(R.color.grey3));
            customTextView1.setPadding(padding, padding, padding, padding);
            customTextView1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_medium));
            customTextView1.setWidth(PixelsOp.convertToDensity(context, 55));
            customTextView1.setHeight(PixelsOp.convertToDensity(context, 55));
            customTextView1.setBackgroundResource(R.drawable.rect_grey_border);
            customTextView1.setGravity(Gravity.CENTER);
            llShowRewardPoints.addView(customTextView1);
        }

        setUpRatingBar(longRewardPointsToString);
    }

    private void setUpRatingBar(String longRewardPointsToString)
    {
        int rewardValue=Integer.parseInt(longRewardPointsToString);
        int calculatedValue=rewardValue/10000;
        int calculatedValue1=rewardValue/100000;
        int calculatedValue2=rewardValue/500000;
        if(calculatedValue<=10000)
        {
            if(calculatedValue<100)
            {
                progressBarPoints.setProgress(calculatedValue);
            }
            else
            {
                progressBarPoints.setProgress(100);
            }
        }else if(calculatedValue1<=100000)
        {
            if(calculatedValue1<100)
            {
                progressBarPoints1.setProgress(calculatedValue1);
            }
            else
            {
                progressBarPoints1.setProgress(100);
            }
        }
        else if(calculatedValue2<=500000)
        {
            if(calculatedValue2<=100)
            {
                progressBarPoints2.setProgress(calculatedValue2);
            }
            else
            {
                if((calculatedValue2-100)>100)
                {
                    progressBarPoints4.setProgress(100);
                }
                else
                {
                    progressBarPoints4.setProgress(calculatedValue2-100);
                }
                progressBarPoints2.setProgress(100);
            }
        }
    }

    private void setUpRecycler() {
        if (rewardsListAdapter == null && rvShowReward.getAdapter() == null) {
            rvShowReward.setLayoutManager(new LinearLayoutManager(context));
            rvShowReward.setItemAnimator(new DefaultItemAnimator());
            rewardsListAdapter = new RewardsListAdapter(points.getRewards(), context, points);
            rewardsListAdapter.setClickListener(new RewardsListAdapter.ClickListeners() {
                @Override
                public void onRowClick(int position) {
                    callRedeemRewardApi(rewardsListAdapter.getItem(position).getId());
                }
            });
            rvShowReward.setAdapter(rewardsListAdapter);
        }
    }

    private void clear() {
        if (rewardsListAdapter != null && rvShowReward.getAdapter() != null) {
            rewardsListAdapter = null;
            rvShowReward.setAdapter(null);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void refreshToolbar() {

        if (!isHidden) {
            Activities.hideBottomNavigation(context, false);
            ToolbarOp.refresh(getView(), getActivity(), getString(R.string.title_delivery_points),
                    null, ToolbarOp.Theme.Dark, 0, null, null);
        }
    }

    private void callGetUserPointsApi()
    {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().accessToken);
        serviceParams.put(Keys.USER_ID_1, UserSharedPreference.readUserBO().getId());

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.GetRewardPrizes;

        new WebServicesVolleyTask(context, false, "Loading your points",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);

                    if (taskItem.isError()) {
                        handleProgressDialog(false);
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                Gson gson = new Gson();
                                points = gson.fromJson(jsonObject.toString(), Points.class);
                            }
                            //updateSwipeLoader(false);
                            setRewardPoints();
                            setUpRecycler();
                            startTimer();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }

    private void startTimer()
    {
        new CountDownTimer(3000,1000){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                handleProgressDialog(false);
            }
        }.start();
    }

    private void callRedeemRewardApi(int rewardId) {

        handleProgressDialog(true);

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().accessToken);
        serviceParams.put(Keys.USER_ID_1, UserSharedPreference.readUserBO().getId());
        serviceParams.put(Keys.RewardID, rewardId);

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.RedeemPrize;

        new WebServicesVolleyTask(context, false, "Redeeming Points",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);

                    if (taskItem.isError()) {

                        handleProgressDialog(false);

                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            //if (taskItem.getResponse() != null) {
//                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
//                                Gson gson = new Gson();
//                                points = gson.fromJson(jsonObject.toString(), Points.class);
//                            }
                            //updateSwipeLoader(false);
                            refreshRewardPointsLayout();
                            clear();
                            callGetUserPointsApi();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }

    private void refreshRewardPointsLayout() {
        llShowRewardPoints.removeAllViews();
    }

}