package app.com.dunkeydelivery.modules.search;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kogitune.activity_transition.fragment.FragmentTransitionLauncher;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.Key;
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
import app.com.dunkeydelivery.modules.home.adapters.ProductListAdapter;
import app.com.dunkeydelivery.modules.home.items.ProductBO;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.tabs.FragmentProductDetail;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.fragments.ProductDetail;
import app.com.dunkeydelivery.modules.search.adapters.AddressAdapter;
import app.com.dunkeydelivery.modules.search.items.AddressItem;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AddressLookupUtils;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Search extends ToolbarFragment implements View.OnClickListener, TextWatcher {

    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private ProductListAdapter mProductAdapter;
    private AddressAdapter mAddressesAdapter;
    private int totalRecords = 0;
    private int maxItems = Constants.MAX_ITEMS_TO_LOAD;
    private int startIndex = 0;
    private int pageIndex = 0;
    List<ProductBO> itemBOs = new ArrayList<>();
    private Unbinder unbinder;

    private Handler handler;
    private Runnable runnable;

    public static String ARG_PARAM1 = "isProductSearch";
    public static String ARG_PARAM2 = "addressItem";
    public static String ARG_PARAM3 = "storeBO";
    private boolean isProductSearch = false;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.ib_cancel)
    ImageButton ibCancel;
    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.rv_swipe_refresh)
    SwipeRefreshLayout rvSwipeRefresh;

    @BindView(R.id.tv_swipe_refresh)
    SwipeRefreshLayout tvSwipeRefresh;

    @BindView(R.id.tv_noresult)
    TextView tvNoResult;

    @BindView(R.id.tv_new_streams)
    TextView tvNewRecords;

    private AddressItem addressItem; //selected address

    WebServicesVolleyTask asyncTask, productAsyncTask;

    private StoreBO storeBO;

    public static Search newInstanceForSearch(boolean isProductSearch, StoreBO storeBO) {

        Bundle args = new Bundle();
        Search fragment = new Search();
        args.putBoolean(ARG_PARAM1, isProductSearch);
        args.putParcelable(ARG_PARAM3, storeBO);
        fragment.setArguments(args);
        return fragment;
    }

    public static Search newInstance(boolean isProductSearch, AddressItem addressItem) {

        Bundle args = new Bundle();
        Search fragment = new Search();
        args.putBoolean(ARG_PARAM1, isProductSearch);
        args.putParcelable(ARG_PARAM2, addressItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_recycleview, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = inflater.getContext();
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        llSearch.setVisibility(View.VISIBLE);

        if (getArguments() != null) {
            isProductSearch = getArguments().getBoolean(ARG_PARAM1);
            addressItem = getArguments().getParcelable(ARG_PARAM2);
            storeBO = getArguments().getParcelable(ARG_PARAM3);
        }

        // Initialize all views
        initViews(view);

        rvSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
//                binding.rvSwipeRefresh.setRefreshing(true);
//                callStreamsService(startIndex, maxItems, false);
            }
        });

        setListeners();

        if (addressItem != null && !isProductSearch) {
            etSearch.setText(addressItem.getAddressLine1());
        }
    }


    public void showLoader() {
        if (tvSwipeRefresh.getVisibility() == View.VISIBLE) {
            tvSwipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    tvSwipeRefresh.setRefreshing(true);
                }
            });
        } else {
            rvSwipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    rvSwipeRefresh.setRefreshing(true);
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void setUpAddressRecycler(final List<AddressItem> itemBOs) {

        if (mProductAdapter == null || recyclerView.getAdapter() == null) {

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            mAddressesAdapter = new AddressAdapter(itemBOs, context, recyclerView);

            mAddressesAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
//                    streamBOs = mProductAdapter.getItems();
                    if (mAddressesAdapter.getItems().size() >= maxItems) {
//                        mProductAdapter.addLoadingItem();
//                        startIndex = mProductAdapter.getItems().size() - 2;
//                        callStreamsService(startIndex, maxItems, false);
                    }
                }
            });

            mAddressesAdapter.setClickListener(new AddressAdapter.ClickListeners() {
                @Override
                public void onRowClick(int position) {
                    addressItem = mAddressesAdapter.getItem(position);
                    goBack();
                }
            });

            recyclerView.setAdapter(mAddressesAdapter);
        } else {
            mAddressesAdapter.addItems(itemBOs);
            mAddressesAdapter.setLoaded();
        }
    }

    private void setUpRecycler(final List<ProductBO> itemBOs, boolean isLoadMore) {

        if (mProductAdapter == null || recyclerView.getAdapter() == null) {

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            mProductAdapter = new ProductListAdapter(itemBOs, context, recyclerView);

            mProductAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
//                    streamBOs = mProductAdapter.getItems();
                    if (mProductAdapter.getItemCount() >= maxItems && mProductAdapter.getItemCount() < totalRecords) {
                        mProductAdapter.addLoadingItem();

                        pageIndex = pageIndex + 1;
                        callSearchProductsService(etSearch.getText().toString(), true);
                    }
                }
            });

            mProductAdapter.setClickListener(new ProductListAdapter.ClickListeners() {
                @Override
                public void onRowClick(int position, View view) {
                    try {
                        LinearLayout clickedProductLayout = (LinearLayout) view.findViewById(R.id.ll_root);
                        ImageView get = (ImageView) clickedProductLayout.findViewById(R.id.iv_item);
                        if (get.getDrawable() != null || get.getDrawable().equals(null)) {
                            ProductBO itemBO = mProductAdapter.getItem(position);
                            FragmentProductDetail fragmentProductDetail = new FragmentProductDetail();
                            FragmentTransitionLauncher
                                    .with(view.getContext())
                                    .image(((BitmapDrawable) get.getDrawable()).getBitmap())
                                    .from(get).prepare(fragmentProductDetail);
                            fragmentProductDetail.getArguments().putParcelable(FragmentProductDetail.ARG_PARAM1, itemBO);
                            fragmentProductDetail.getArguments().putBoolean(FragmentProductDetail.ARG_PARAM2, true);
                            Activities.gotoNextFragmentWithTransition(context, fragmentProductDetail);
                        } else {
                            ProductBO itemBO = mProductAdapter.getItem(position);
                            FragmentProductDetail fragmentProductDetail = new FragmentProductDetail();
                            fragmentProductDetail.getArguments().putParcelable(FragmentProductDetail.ARG_PARAM1, itemBO);
                            fragmentProductDetail.getArguments().putBoolean(FragmentProductDetail.ARG_PARAM2, false);
                            Activities.gotoNextFragmentWithTransition(context, fragmentProductDetail);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            recyclerView.setAdapter(mProductAdapter);
        } else {

            if (isLoadMore) {
                mProductAdapter.setLoaded();
                mProductAdapter.addItems(itemBOs);
            } else {
                mProductAdapter.setItems(itemBOs);
            }

        }
    }


    private void setListeners() {

        ibBack.setOnClickListener(this);
        ibCancel.setOnClickListener(this);
        etSearch.addTextChangedListener(this);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    KeyboardOp.hide(context, etSearch);
                    final String search = etSearch.getText().toString().trim();
                    if (!TextUtils.isEmpty(search)) {

                        if (isProductSearch) {

                            //Todo call search service, currently setting the dummy data...
//                            itemBOs.clear();
//                            if ("Krunch Burger".contains(search)) {
//                                showNoResult(false);
//                                itemBOs.add(new ProductBO("Krunch Burger"));
//                                setUpRecycler(itemBOs);
//                            } else {
//                                showNoResult(true);
//                                clearList();
//                            }
                            callSearchProductsService(search, false);

                        } else {
                            //call location service...
                            getLocationFromAddressUsingGoogleApi(search);
                        }
                        return true;
                    } else {
                        cancelTask();
                    }
                }
                return false;
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

        if (addressItem != null) {
            LogUtils.i("mess", "Developer " + addressItem.getLatitude() + "   " + addressItem.getLongitude());
        } else {
            LogUtils.i("mess", "Developer ");
        }

    }


    @Override
    public void refreshToolbar() {
        Activities.hideBottomNavigation(context, true);
        ToolbarOp.hideToolbarRetainViews(getView(), getContext());
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
                pageIndex = 0;
                totalRecords = 0;
                String searchStr = etSearch.getText().toString().trim();
                if (!searchStr.isEmpty()) {
                    if (isProductSearch) {
                        callSearchProductsService(searchStr, false);
                    } else {
                        getLocationFromAddressUsingGoogleApi(searchStr);
                    }
                }
            }
        });

        //refresh layout of textview noResult...
        tvSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 0;
                totalRecords = 0;
                String searchStr = etSearch.getText().toString().trim();
                if (!searchStr.isEmpty()) {
                    if (isProductSearch) {
                        callSearchProductsService(searchStr, false);
                    } else {
                        getLocationFromAddressUsingGoogleApi(searchStr);
                    }
                }
            }
        });
    }

    private void stopSwipLoader() {
        rvSwipeRefresh.setRefreshing(false);
        tvSwipeRefresh.setRefreshing(false);
    }


    private void showNoResult(boolean isShow) {
        if (isShow)
            tvSwipeRefresh.setVisibility(View.VISIBLE);
        else
            tvSwipeRefresh.setVisibility(View.GONE);
    }

    private void goBack() {
        if (!isProductSearch) {
            if (addressItem != null)
                EventBus.getDefault().post(addressItem);
            else
                EventBus.getDefault().post("");
        }
        Activities.goBackFragment(context, 1);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_back:
                goBack();
                break;
            case R.id.ib_cancel:
                etSearch.setText("");
                stopSwipLoader();
                addressItem = null;
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        final String searchStr = s.toString().trim();
        if (!TextUtils.isEmpty(searchStr) && s.length() > 2) {

            if (isProductSearch) {
                //Todo call search service, currently setting the dummy data...
//                itemBOs.clear();
//                if ("Krunch Burger".contains(search)) {..
//                    showNoResult(false);
//                    itemBOs.add(new ProductBO("Krunch Burger"));
//                    setUpRecycler(itemBOs, false);
//                } else {
//                    showNoResult(true);
//                    clearList();
//                }

                callSearchProductsService(searchStr, false);

            } else {
                //call location service...
                if (handler != null && runnable != null)
                    handler.removeCallbacks(runnable);

                handler = new Handler();
                runnable = new Runnable() {

                    @Override
                    public void run() {
                        try {
                            getLocationFromAddressUsingGoogleApi(searchStr);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                };
                handler.postDelayed(runnable, 500);
            }

        } else {
            if (TextUtils.isEmpty(searchStr))
                cancelTask();
        }
    }


    // method use to cancel task...
    private void cancelTask() {
        if (isProductSearch) {
            if (productAsyncTask != null) {
                productAsyncTask.cancelTask();
            }
            //  Clear list
            if (mProductAdapter != null)
                mProductAdapter.clearItems();
        } else {
            if (asyncTask != null) {
                asyncTask.cancelTask();
            }
            //  Clear list
            if (mAddressesAdapter != null)
                mAddressesAdapter.clearItems();
        }


    }

    private void getLocationFromAddressUsingGoogleApi(String locationName) {
        cancelTask();
        showLoader();
        String[] keyArray = {Keys.Address, Keys.Key};
        Object[] valuesArray = {locationName, Constants.APIKey};
        HashMap<String, Object> serviceParams = WebServiceUtils
                .getServiceParams(keyArray, valuesArray);

        asyncTask = new WebServicesVolleyTask(context, false, null,
                EnumUtils.ServiceName.ExternalService,
                EnumUtils.RequestMethod.GET, serviceParams, null, Constants.GoogleApiURl, null, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {
                if (taskItem != null) {
                    stopSwipLoader();
                    if (taskItem.isError()) {
//                        SnackBarUtil.showSnackbar(context, WebServiceUtils.getResponseMessage(taskItem), false);
                    } else {
                        try {
                            List<AddressItem> addressItems = AddressLookupUtils.parseAddressResults(taskItem.getRawResponse(), false);
                            if (addressItems != null && addressItems.size() > 0) {
                                //For  setting custom adapter
                                showNoResult(false);
                                setUpAddressRecycler(addressItems);
                            } else {
                                showNoResult(true);
                                setUpAddressRecycler(new ArrayList<AddressItem>());
                            }
//							Log.i(TAG, "onTaskComplete: " + taskItem.getRawResponse());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

    }


    //this method is used to get medications from service...
    public void callSearchProductsService(final String searchStr, final boolean isFromLoadMore) {


        cancelTask();
        showLoader();
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();
//        serviceParams.put(Keys.Category_Type, EnumUtils.HomeTabs.Food.ordinal());
//        serviceParams.put(Keys.Category_Id, 24);
        serviceParams.put(Keys.search_string, searchStr);
        serviceParams.put(Keys.Items, maxItems);
        serviceParams.put(Keys.Page, pageIndex);

        if (addressItem != null) {
            serviceParams.put(Keys.latitude, addressItem.getLatitude());
            serviceParams.put(Keys.longitude, addressItem.getLongitude());
        }

        if (storeBO != null && storeBO.getId() != null) {
            serviceParams.put(Keys.Store_id, storeBO.getId());
        }

        tokenServiceHeaderParams.put(Keys.TOKEN, UserSharedPreference.readUserToken().accessToken);

        productAsyncTask = new WebServicesVolleyTask(context, false, "",
                EnumUtils.ServiceName.ProductByName,
                EnumUtils.ServiceName.getServicePath(EnumUtils.ServiceName.ProductByName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {

                    if (mProductAdapter != null) {
                        //set isLoading boolean to false....
                        mProductAdapter.setLoaded();
                        if (isFromLoadMore)
                            mProductAdapter.removeLoadingItem();
                    }

                    stopSwipLoader();
                    if (taskItem.isError()) {
                        //show alert only on the selected tab...
                        showNoResult(true);
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                JSONArray productslist = jsonObject.getJSONArray("productslist");
                                Gson gson = new Gson();
                                if (productslist.length() > 0) {
                                    showNoResult(false);
                                    Type listType = new TypeToken<List<ProductBO>>() {
                                    }.getType();
                                    List<ProductBO> productBOList = (List<ProductBO>) gson.fromJson(productslist.toString(),
                                            listType);
                                    setUpRecycler(productBOList, isFromLoadMore);
                                } else {
                                    showNoResult(true);
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
    }//end of GetProducts method

}