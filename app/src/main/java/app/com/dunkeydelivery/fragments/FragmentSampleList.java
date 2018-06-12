package app.com.dunkeydelivery.fragments;

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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.adapters.ListAdapter;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.items.ItemBO;
import app.com.dunkeydelivery.items.StreamBO;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentSampleList extends ToolbarFragment {

    private Context context;
    private ListAdapter mAdapter;
    private int totalRecords = 0;
    private int maxItems = Constants.MAX_ITEMS_TO_LOAD;
    private int startIndex = 0;
    private boolean isHidden = false;
    private Unbinder unbinder;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.rv_swipe_refresh)
    SwipeRefreshLayout rvSwipeRefresh;

    @BindView(R.id.tv_swipe_refresh)
    SwipeRefreshLayout tvSwipeRefresh;

    @BindView(R.id.tv_new_streams)
    TextView tvNewStreams;

    public static FragmentSampleList newInstance() {
        Bundle args = new Bundle();
        FragmentSampleList fragment = new FragmentSampleList();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_recycleview,
                container, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = inflater.getContext();
        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activities.hideBottomNavigation(context, false);
        // Initialize all views
        initViews(view);

        rvSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
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

    private void setUpRecycler(final List<ItemBO> itemBOs) {

        if (mAdapter == null || recyclerView.getAdapter() == null) {

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            mAdapter = new ListAdapter(itemBOs, context, recyclerView);

            mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (mAdapter.getItems().size() >= maxItems) {
                        mAdapter.addLoadingItem();

                        startIndex = mAdapter.getItems().size() - 2;
                        callStreamsService(startIndex, maxItems, false);
                    }
                }
            });

            recyclerView.setAdapter(mAdapter);
            setListeners();
        } else {
            mAdapter.addItems(itemBOs);
            mAdapter.setLoaded();
        }
    }


    private void setListeners() {

        mAdapter.setClickListener(new ListAdapter.ClickListeners() {
            @Override
            public void onRowClick(int position) {
                ItemBO itemBO = mAdapter.getItem(position);
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

        if(!isHidden){
            ToolbarOp.refresh(getView(), getActivity(), getString(R.string.title_delivery_points),
                    null, ToolbarOp.Theme.Dark, 0, null, null);
        }
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
                startIndex = 0;
                totalRecords = 0;
                callStreamsService(startIndex, maxItems, false);
            }
        });

        //refresh layout of textview noResult...
        tvSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startIndex = 0;
                totalRecords = 0;
//                callStreamsService(startIndex, maxItems, false);
            }
        });
    }


    //method to call get streams service..
    private void callStreamsService(int offset, int limit, boolean isShowLoader) {

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<String, Object>();

        tokenServiceHeaderParams.put(Keys.TOKEN, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjM1LCJpc3MiOiJodHRwOlwvXC8zNS4xNjAuMTc1LjE2NVwvc3RyZWFtaXhcL2FwaVwvbG9naW4iLCJpYXQiOjE0OTY2NjAyNzUsImV4cCI6MTQ5Nzg2OTg3NSwibmJmIjoxNDk2NjYwMjc1LCJqdGkiOiJWVEF3TWtjRHlzc3FoOU9VIn0.2RmXk5QrSpjtQbUKvYJ10PAquzuFMPYeRJc-kEj-G8Q");
        serviceParams.put(Keys.OFFSET, offset);
        serviceParams.put(Keys.LIMIT, limit);

        new WebServicesVolleyTask(context, isShowLoader, "",
                EnumUtils.ServiceName.streams,
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                rvSwipeRefresh.setRefreshing(false);
                tvSwipeRefresh.setRefreshing(false);
                if (taskItem != null) {

                    if (taskItem.isError()) {
                        if(!isHidden)
                            AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                        if (mAdapter == null) {
                            showNoResult(true);
                        }

                    } else {
                        try {

                            if (taskItem.getResponse() != null) {

                                // if response is successful then do parsing
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                JSONArray streamsJsonArray = jsonObject.getJSONArray("streams");

                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<StreamBO>>() {
                                }.getType();
                                List<StreamBO> newStreamBOs = (List<StreamBO>) gson.fromJson(streamsJsonArray.toString(),
                                        listType);

                                List<ItemBO> itemBOs = new ArrayList<>();
                                if(newStreamBOs != null && newStreamBOs.size() > 0){
                                    for(StreamBO streamBO : newStreamBOs){
                                        itemBOs.add(new ItemBO(streamBO.getName(), streamBO.getStatusText()));
                                    }

                                }

                                //check for new records...
                                checkForNewStreams(jsonObject.getInt("total_records"));

                                if (totalRecords == 0) {
                                    //if noRecordsFouns then show noresult...
                                    showNoResult(true);
                                } else {
                                    if (mAdapter != null) {
                                        if (startIndex != 0) {
                                            //for the case of load more...
                                            mAdapter.removeLoadingItem();
                                        } else {
                                            //for the case of pulltoRefresh...
                                            mAdapter.clearItems();
                                        }
                                    }
                                    showNoResult(false);
                                    setUpRecycler(itemBOs);
                                }
                            }

                        } catch (Exception ex) {
                            showNoResult(true);
                            ex.printStackTrace();
                        }

                    }
                }
            }
        });
    }//end of streams service

    private void checkForNewStreams(int newRecordsCount) {
        tvNewStreams.setVisibility(View.GONE);
        if (totalRecords != 0) {
            if (newRecordsCount > totalRecords) {
                tvNewStreams.setVisibility(View.VISIBLE);
            }
        }
        totalRecords = newRecordsCount;
    }

    private void showNoResult(boolean isShow) {
        if (isShow)
            tvSwipeRefresh.setVisibility(View.VISIBLE);
        else
            tvSwipeRefresh.setVisibility(View.GONE);
    }

}