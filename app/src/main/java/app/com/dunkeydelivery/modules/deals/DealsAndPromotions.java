package app.com.dunkeydelivery.modules.deals;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kogitune.activity_transition.fragment.FragmentTransitionLauncher;

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
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.cart.fragments.CartMain;
import app.com.dunkeydelivery.modules.deals.adapter.DealsAdapter;
import app.com.dunkeydelivery.modules.deals.items.DealsItem;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.fragments.ProductDetail;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.MenuItemImgOrStr;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DealsAndPromotions extends ToolbarFragment {

    private Context context;
    private DealsAdapter mAdapter;
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

    public static DealsAndPromotions newInstance() {
        Bundle args = new Bundle();
        DealsAndPromotions fragment = new DealsAndPromotions();
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

        Activities.lockDrawer(context);
        Activities.hideBottomNavigation(context, true);
        // Initialize all views
        initViews(view);

        rvSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                rvSwipeRefresh.setRefreshing(true);
                callStreamsService(startIndex, maxItems, false);
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

    private void setUpRecycler(final List<DealsItem> itemBOs) {

        if (mAdapter == null || recyclerView.getAdapter() == null) {

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            mAdapter = new DealsAdapter(itemBOs, context);

            recyclerView.setAdapter(mAdapter);
            setListeners();
        } else {
            mAdapter.addItems(itemBOs);
            //mAdapter.notifyDataSetChanged();
        }
    }


    private void setListeners() {

        mAdapter.setClickListener(new DealsAdapter.ClickListeners() {
            @Override
            public void onRowClick(DealsItem item, View view) {
                try {
                    ImageView get = (ImageView) view.findViewById(R.id.iv_promotion);
                    if(get.getDrawable()!=null || !get.getDrawable().equals(null))
                    {
                        ProductDetail productDetail = new ProductDetail();
                        FragmentTransitionLauncher
                                .with(view.getContext())
                                .image(((BitmapDrawable) get.getDrawable()).getBitmap())
                                .from(get).prepare(productDetail);
                        productDetail.getArguments().putParcelable(ProductDetail.ARG_PARAM2, item);
                        productDetail.getArguments().putBoolean(ProductDetail.ARG_PARAM3, true);
                        Activities.gotoNextFragmentWithTransition(context, productDetail);
                    }
                    else
                    {
                        ProductDetail productDetail = new ProductDetail();
                        productDetail.getArguments().putParcelable(ProductDetail.ARG_PARAM2, item);
                        productDetail.getArguments().putBoolean(ProductDetail.ARG_PARAM3, false);
                        Activities.gotoNextFragmentWithTransition(context, productDetail);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
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
    }


    @Override
    public void refreshToolbar() {

        if (!isHidden) {
            Activities.hideBottomNavigation(context, true);
            MenuItemImgOrStr menuItemCart = new MenuItemImgOrStr(R.drawable.ic_cart, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activities.gotoNextFragment(context, CartMain.newInstance());
                }
            });

            ToolbarOp.refresh(getView(), getActivity(), getString(R.string.deals_and_promotions),
                    null, ToolbarOp.Theme.Dark, 0, null, menuItemCart);
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
                try {
                    callStreamsService(startIndex, maxItems, false);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        //refresh layout of textview noResult...
        tvSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startIndex = 0;
                totalRecords = 0;
                try {
                    callStreamsService(startIndex, maxItems, false);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }


    //method to call get streams service..
    private void callStreamsService(int offset, int limit, boolean isShowLoader) {

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<String, Object>();

        tokenServiceHeaderParams.put(Keys.TOKEN, UserSharedPreference.readUserToken().getAccessToken());


        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.GetOfferPackage;
        new WebServicesVolleyTask(context, isShowLoader, "",
                serviceName,
                EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {
                if (taskItem != null)
                {

                    if (taskItem.isError()) {
                        if (!isHidden)
                            AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                        if (mAdapter == null) {
                            showNoResult(true);
                        }

                        stopRefreshing();

                    } else {
                        try {
                            if (taskItem.getResponse() != null) {
                                // if response is successful then do parsing
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                JSONArray Offer_Packages = jsonObject.getJSONArray("Offer_Packages");
                                //JSONArray Offer_Products = jsonObject.getJSONArray("Offer_Products");
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<DealsItem>>() {
                                }.getType();
                                List<DealsItem> newStreamBOs = (List<DealsItem>) gson.fromJson(Offer_Packages.toString(),
                                        listType);

                                List<DealsItem> itemBOs = new ArrayList<>();
                                if (newStreamBOs != null && newStreamBOs.size() > 0) {
                                    for (DealsItem streamBO : newStreamBOs) {
                                        streamBO.setItemType(3);
                                        itemBOs.add(streamBO);
                                    }

                                }
                                setUpRecycler(itemBOs);
                                if (itemBOs.size() > 0) {
                                    showNoResult(false);
                                } else {
                                    showNoResult(true);
                                }
                            }

                            stopRefreshing();

                        } catch (Exception ex) {
                            showNoResult(true);
                            ex.printStackTrace();
                        }

                    }
                }
                else
                {
                    stopRefreshing();
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
        if (isShow) {
            tvSwipeRefresh.setVisibility(View.VISIBLE);
            rvSwipeRefresh.setVisibility(View.GONE);
        } else {
            tvSwipeRefresh.setVisibility(View.GONE);
            rvSwipeRefresh.setVisibility(View.VISIBLE);
        }
    }

    private void stopRefreshing()
    {
        if(rvSwipeRefresh!=null && tvSwipeRefresh!=null) {
            if(rvSwipeRefresh.getVisibility()==View.VISIBLE) {
                rvSwipeRefresh.setRefreshing(false);
            }
            else {
                tvSwipeRefresh.setRefreshing(false);
            }
        }
    }
}