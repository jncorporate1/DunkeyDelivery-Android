package app.com.dunkeydelivery.modules.filter.pager;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.filter.pager.adapters.CuisineFilterListAdapter;
import app.com.dunkeydelivery.modules.filter.pager.items.CuisineItem;
import app.com.dunkeydelivery.modules.filter.pager.items.FilterItem;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.ColorOp;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.FiltersOP;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Cuisines extends Fragment {

    private Context context;
    private String TAG = this.getClass().getSimpleName();
    Unbinder unbinder;
    @BindView(R.id.rv_cuisines)
    RecyclerView rv_cuisine;
    @BindView(R.id.rv_swipe_refresh)
    SwipeRefreshLayout rvSwipeRefresh;

    @BindView(R.id.tv_swipe_refresh)
    SwipeRefreshLayout tvSwipeRefreshLayout;
    private CuisineFilterListAdapter mAdapter;
    FilterItem filter;
    private ArrayList<CuisineItem> cuisineItemArrayList;

    public static Cuisines newInstance() {
        Bundle args = new Bundle();
        Cuisines fragment = new Cuisines();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cuisines,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize all views

        filter = FiltersOP.getFilters(Keys.Filter_FOOD);

        initViews();
    }

    private void showNoResult(boolean isShow) {
        if (isShow) {
            if (tvSwipeRefreshLayout != null)
                tvSwipeRefreshLayout.setVisibility(View.VISIBLE);
            if (rvSwipeRefresh != null)
                rvSwipeRefresh.setVisibility(View.GONE);
        } else {
            if (tvSwipeRefreshLayout != null)
                tvSwipeRefreshLayout.setVisibility(View.GONE);
            if (rvSwipeRefresh != null)
                rvSwipeRefresh.setVisibility(View.VISIBLE);
        }
    }

    private void initViews() {

        tvSwipeRefreshLayout.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorAccent
        );
        rvSwipeRefresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorAccent
        );

        //refresh layout of recycler view...
        tvSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callGetStoresService();
            }
        });


        rvSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callGetStoresService();
            }
        });
        rvSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                updateRefreshing(true);
                callGetStoresService();

            }
        });
//
        rvSwipeRefresh.setColorSchemeColors(
                ColorOp.getColor(context, R.color.colorPrimary),
                ColorOp.getColor(context, R.color.colorAccent),
                ColorOp.getColor(context, R.color.colorPrimaryDark));
    }

    private void setUpData() {
        if (filter != null && cuisineItemArrayList != null && cuisineItemArrayList.size() > 0 && mAdapter != null) {
            if (filter.getCuisines() != null || !filter.getCuisines().equalsIgnoreCase("")) {
                String[] cuisines = filter.getCuisines().split(",");
                for (String filerName : cuisines) {
                    for (CuisineItem item : cuisineItemArrayList) {
                        if (filerName.equalsIgnoreCase(item.getTag())) {
                            cuisineItemArrayList.get(cuisineItemArrayList.indexOf(item)).setChecked(true);
                        }
                    }//inner loop end

                }//outerloop end
            }
            mAdapter.notifyDataSetChanged();

        }
    }


    //this method is used to get stores from service and saved in Prefs...
    public void callGetStoresService() {

        HashMap<String, Object> serviceParams = null;
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();
        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().getAccessToken());

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.GetCousines;

        new WebServicesVolleyTask(context, false, "",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);

                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Activities.goBackFragment(context, 1);
                            }
                        });
                        showNoResult(true);
                    } else {
                        try {

                            JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                            Gson gson = new Gson();
                            Type type = new TypeToken<ArrayList<CuisineItem>>() {
                            }.getType();

                            cuisineItemArrayList = gson.fromJson(jsonObject.getJSONArray("cuisines").toString(), type);
                            if (cuisineItemArrayList != null && cuisineItemArrayList.size() > 0) {
                                setupAdapter(cuisineItemArrayList);
                                setUpData();
                                showNoResult(false);
                            } else {
                                showNoResult(true);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            showNoResult(true);
                        }

                        // if response is successful then do something
                    }
//                    updateSwipeLoader(false);
                }
                updateRefreshing(false);
            }
        });
    }

    private void setupAdapter(ArrayList<CuisineItem> items) {

        if (mAdapter == null || rv_cuisine.getAdapter() == null) {

            rv_cuisine.setLayoutManager(new GridLayoutManager(context, 2));
            rv_cuisine.setItemAnimator(new DefaultItemAnimator());

            mAdapter = new CuisineFilterListAdapter(items, context);
            mAdapter.setClickListener(new CuisineFilterListAdapter.ClickListeners() {
                @Override
                public void onRowClick(CuisineItem item) {

                }
            });
            rv_cuisine.setAdapter(mAdapter);
        } else {
            if (mAdapter.getItemCount() > 0) {
                mAdapter.clearItems();
            }
            mAdapter.addItems(items);
        }
    }


    @OnClick(R.id.tv_add_item)
    public void refreshStore(View view) {
        updateRefreshing(true);
        callGetStoresService();
    }

    private void updateRefreshing(boolean show) {
        if (rvSwipeRefresh != null)
            rvSwipeRefresh.setRefreshing(show);
        if (tvSwipeRefreshLayout != null)
            tvSwipeRefreshLayout.setRefreshing(show);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    private void initViews(View view) {
        //Initialize main content Linear layout.

    }

    public void clearFilter() {
        filter = FiltersOP.getFilters(Keys.Filter_FOOD);
        setUpData();
    }

    public String getSelectedCuisines() {
        if (mAdapter != null && mAdapter.getItemCount() > 0)
            return mAdapter.getSelectedCusines();
        return "";
    }
}