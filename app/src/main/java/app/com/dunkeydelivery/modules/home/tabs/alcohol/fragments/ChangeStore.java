package app.com.dunkeydelivery.modules.home.tabs.alcohol.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.account.Items.CardItem;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.adapters.ChangeStoreAdapter;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.items.AlcoholStoreBO;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.SnackBarUtil;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChangeStore extends ToolbarFragment {

    private Context context;
    private ChangeStoreAdapter mAdapter;
    private StoreBO getStoreBO;
    private boolean isHidden = false;
    private Unbinder unbinder;
    private Integer pageNumber = 0;
    private Integer TotalRecords;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.rv_swipe_refresh)
    SwipeRefreshLayout rvSwipeRefresh;

    @BindView(R.id.tv_swipe_refresh)
    SwipeRefreshLayout tvSwipeRefresh;

    @BindView(R.id.tv_new_streams)
    TextView tvNewStreams;

    @BindView(R.id.btn_select)
    Button btnSelect;

    boolean isAddTag = false;
    Integer storeId;
    String[] ids;

    public static ChangeStore newInstance(boolean isAddTag,String storeId) {
        Bundle args = new Bundle();
        ChangeStore fragment = new ChangeStore();
        args.putBoolean("isAddTag", isAddTag);
        args.putString("storeId",storeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_change_store,
                container, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = inflater.getContext();
        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize all views
        initViews(view);

        if (getArguments() != null) {
            isAddTag = getArguments().getBoolean("isAddTag");
            ids = getArguments().getString("storeId").split("/");
            storeId = Integer.parseInt(ids[0]);
        }
        rvSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Activities.hideBottomNavigation(context, true);
                    rvSwipeRefresh.setRefreshing(true);
                    callGetStoresApi();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        rvSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    rvSwipeRefresh.setRefreshing(false);
                    if (mAdapter != null) {
                        mAdapter.clearItems();
                    }
                    pageNumber = 0;
                    callGetStoresApi();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
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

    //setup stores data
    private void setUpRecycler(List<StoreBO> storeBOs) {

        if (mAdapter == null || recyclerView.getAdapter() == null) {

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            //currently passing the boolean to show tags..
            //in future it will come in data...

            for (StoreBO storeBO : storeBOs) {
                if (storeBO.getId().equals(storeId)) {
                    storeBOs.get(storeBOs.indexOf(storeBO)).setSelected(true);
                }
            }

            mAdapter = new ChangeStoreAdapter(storeBOs, context, recyclerView, isAddTag , storeId);

            mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (mAdapter.getItems().size() >= Constants.MAX_ITEMS_TO_LOAD && mAdapter.getItems().size() < TotalRecords) {
                        pageNumber++;
                        callGetStoresApi();
                    }
                }
            });

            recyclerView.setAdapter(mAdapter);
            setListeners();
        } else {
            for (StoreBO storeBO : storeBOs) {
                if (storeBO.getId().equals(mAdapter.getStoreId())) {
                    storeBOs.get(storeBOs.indexOf(storeBO)).setSelected(true);
                }
            }
            mAdapter.addItems(storeBOs);
            mAdapter.setLoaded();
        }
    }

    //setup listeners
    private void setListeners() {

        mAdapter.setClickListener(new ChangeStoreAdapter.ClickListeners() {
            @Override
            public void onRowClick(int position) {
                getStoreBO=mAdapter.getItem(position);
                mAdapter.setSelectedStore(position);
            }

            @Override
            public void onStoreSelect(int position) {
                getStoreBO=mAdapter.getItem(position);
                mAdapter.setSelectedStore(position);
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getStoreBO!=null) {
                    if(ids.length==2)
                    {
                        EventBus.getDefault().post(getStoreBO.getId()+","+ids[1]);
                    }
                    else
                    {
                        EventBus.getDefault().post(getStoreBO.getId()+"");
                    }
                }
//                Activities.goBackFragment(context, 1);
                Activities.goBackFragment(context, 2);
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

    }


    @Override
    public void refreshToolbar() {
        Activities.hideBottomNavigation(context, true);
        ToolbarOp.refresh(getView(), getActivity(), getString(R.string.select_a_store),
                null, ToolbarOp.Theme.Dark, 0, null, null);
    }


    private void initViews(View view) {
        //Initialize main content Linear layout.
        rvSwipeRefresh.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimaryDark);

        tvSwipeRefresh.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimaryDark);

        //refresh layout of recycler view...
        rvSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                callStreamsService(startIndex, maxItems, false);
            }
        });

        //refresh layout of textview noResult...
        tvSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                callStreamsService(startIndex, maxItems, false);
            }
        });
    }

    //call api to get stores
    private void callGetStoresApi() {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.TOKEN, UserSharedPreference.readUserToken().accessToken);
        if (isAddTag == false) {
            serviceParams.put(Keys.Type, 2);
        } else {
            serviceParams.put(Keys.Type, 3);
        }
        serviceParams.put(Keys.latitude, -33.8826912922134);
        serviceParams.put(Keys.longitude, 151.208953857422);
        serviceParams.put(Keys.Page, pageNumber);
        serviceParams.put(Keys.Items, Constants.MAX_ITEMS_TO_LOAD);

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.ChangeStore;
        ;

        new WebServicesVolleyTask(context, false, "",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);

                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                        setRefreshing();
                    } else {
                        try {
                            if (taskItem.getResponse() != null) {
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                JSONArray jsonArray = jsonObject.getJSONArray("Stores");
                                TotalRecords = jsonObject.getInt("TotalRecords");
                                Gson gson = new Gson();
                                Type typeToken = new TypeToken<List<StoreBO>>() {
                                }.getType();
                                List<StoreBO> storesByCaregory = gson.fromJson(jsonArray.toString(), typeToken);
                                setUpRecycler(storesByCaregory);
                            }

                            setRefreshing();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        // if response is successful then do something
                    }
                }
                else
                {
                    setRefreshing();
                }
            }
        });
    }

    private void setRefreshing()
    {
        if(rvSwipeRefresh!=null) {
            rvSwipeRefresh.setRefreshing(false);
        }
    }
}