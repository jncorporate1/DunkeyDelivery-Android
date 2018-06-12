package app.com.dunkeydelivery.modules.home.tabs;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kogitune.activity_transition.fragment.FragmentTransitionLauncher;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.cart.fragments.CartMain;
import app.com.dunkeydelivery.modules.home.adapters.ProductListAdapter;
import app.com.dunkeydelivery.modules.home.items.ProductBO;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.items.SubCategoryBO;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.toolbar.MenuItemImgOrStr;
import app.com.dunkeydelivery.utils.toolbar.MenuItemSearch;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentProductList extends ToolbarFragment {
    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private boolean isHidden;
    Unbinder unbinder;

    private SubCategoryBO subCategoryBO = null;
    public static String ARG_PARAM1 = "categoryBO";
    public static String ARG_PARAM2 = "storeBO";
    private int totalRecords = 0;
    private StoreBO storeBO;
    private int maxItems = Constants.MAX_ITEMS_TO_LOAD;
    private int pageIndex = 0;
    private ProductListAdapter mAdapter;
    private Handler handler;
    private Runnable runnable;
    private WebServicesVolleyTask asyncTask;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.rv_swipe_refresh)
    SwipeRefreshLayout rvSwipeRefresh;

    @BindView(R.id.tv_swipe_refresh)
    SwipeRefreshLayout tvSwipeRefresh;

    @BindView(R.id.tv_new_streams)
    TextView tvNewStreams;

    private TextWatcher searchTextWatcher;

    public static FragmentProductList newInstance(SubCategoryBO subCategoryBO, StoreBO storeBO) {
        Bundle args = new Bundle();
        FragmentProductList fragment = new FragmentProductList();
        args.putParcelable(ARG_PARAM1, subCategoryBO);
        args.putParcelable(ARG_PARAM2, storeBO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_recycleview,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            subCategoryBO = getArguments().getParcelable(ARG_PARAM1);
            storeBO = getArguments().getParcelable(ARG_PARAM2);
        }

        // Initialize all views
        initViews();

        showNoResult(true);
        callGetProductsService(false);

    }//onViewCreated

    private void showNoResult(boolean isShow) {
        if (isShow)
            tvSwipeRefresh.setVisibility(View.VISIBLE);
        else
            tvSwipeRefresh.setVisibility(View.GONE);
    }

    private void startRefreshingLoader() {
        if (tvSwipeRefresh.getVisibility() == View.VISIBLE) {
            //if noresult is shown then display start its refreshlayout
            tvSwipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    tvSwipeRefresh.setRefreshing(true);
                }
            });

        } else {
            //if content is shown...
            rvSwipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    rvSwipeRefresh.setRefreshing(true);
                }
            });
        }
    }


    private void initViews() {
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
                totalRecords = 0;
                pageIndex = 0;
                callGetProductsService(false);
            }
        });

        //refresh layout of textview noResult...
        tvSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                totalRecords = 0;
                pageIndex = 0;
                callGetProductsService(false);
            }
        });

        searchTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String search = s.toString();
                if (!TextUtils.isEmpty(search)) {
                    if (handler != null && runnable != null)
                        handler.removeCallbacks(runnable);

                    handler = new Handler();
                    runnable = new Runnable() {

                        @Override
                        public void run() {
                            try {
                                if (search.length() > 2) {
                                    callSearchProductsService(search);
                                } else {
                                    cancelTask();
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    };
                    handler.postDelayed(runnable, 500);
                } else {
                    totalRecords = 0;
                    pageIndex = 0;
                    callGetProductsService(false);
                }
            }
        };
    }

    // method use to cancel task...
    private void cancelTask() {
//		rlLv.setVisibility(View.GONE);
        if (asyncTask != null) {
            asyncTask.cancelTask();
            asyncTask = null;
//            pb.setVisibility(View.GONE);
        }

    }

    private void setUpRecycler(List<ProductBO> productBOs, boolean isFromLoadmore) {


        if (mAdapter == null || recyclerView.getAdapter() == null) {

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            mAdapter = new ProductListAdapter(productBOs, context, recyclerView);

            mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
//                    streamBOs = mAdapter.getItems();
                    if (asyncTask == null && mAdapter.getItemCount() >= maxItems && mAdapter.getItemCount() < totalRecords) {
                        mAdapter.addLoadingItem();

                        pageIndex = pageIndex + 1;
                        callGetProductsService(true);
                    }
                }
            });

            mAdapter.setClickListener(new ProductListAdapter.ClickListeners() {
                @Override
                public void onRowClick(int position,View view) {
                    final ProductBO itemBO = mAdapter.getItem(position);
                    LinearLayout clickedProductLayout=(LinearLayout)view.findViewById(R.id.ll_root);
                    final ImageView get = (ImageView) clickedProductLayout.findViewById(R.id.iv_item);
//                    Activities.gotoNextFragment(context, FragmentProductDetail.newInstance(itemBO));

                    RequestOptions requestOptions = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL);
                    Glide.with(context).asBitmap().load(itemBO.getImage()).apply(requestOptions)
                            .into(new BitmapImageViewTarget(get) {

                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    super.onResourceReady(resource, transition);
                                    FragmentProductDetail fragmentProductDetail=new FragmentProductDetail();
                                    FragmentTransitionLauncher
                                            .with(view.getContext())
                                            .image(resource)
                                            .from(get).prepare(fragmentProductDetail);
                                    fragmentProductDetail.getArguments().putParcelable(FragmentProductDetail.ARG_PARAM1,itemBO);
//                                    fragmentProductDetail.getArguments().putParcelable(ARG_PARAM2,storeBO);
                                    fragmentProductDetail.getArguments().putBoolean(FragmentProductDetail.ARG_PARAM2,true);
                                    Activities.gotoNextFragmentWithTransition(context, fragmentProductDetail);
                                }
                            });
                }
            });

            recyclerView.setAdapter(mAdapter);

        } else {
            if (isFromLoadmore) {
                mAdapter.setLoaded();
                mAdapter.addItems(productBOs);
            } else {
                mAdapter.setItems(productBOs);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void refreshToolbar() {
        Activities.hideBottomNavigation(context, true);
        MenuItemImgOrStr menuItemImgOrStr = new MenuItemImgOrStr(R.drawable.ic_cart, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activities.gotoNextFragment(context, CartMain.newInstance());
            }
        });

        MenuItemSearch menuItemSearch = new MenuItemSearch(getString(R.string.search_your_product), R.drawable.ic_search_grey,
                searchTextWatcher, null, null);

        ToolbarOp.refresh(getView(), getActivity(), "",
                null, ToolbarOp.Theme.Dark, 0, null, menuItemSearch, menuItemImgOrStr);
    }


    private void initViews(View view) {
        //Initialize main content Linear layout.

    }

    private void stopSwipeLoader()
    {
        if(tvSwipeRefresh!=null && rvSwipeRefresh!=null)
        {
            tvSwipeRefresh.setRefreshing(false);
            rvSwipeRefresh.setRefreshing(false);
        }
    }

    //this method is used to get medications from service...
    public void callGetProductsService(final boolean isFromLoadMore) {

        if (!isFromLoadMore)
            startRefreshingLoader();

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = null;
        serviceParams.put(Keys.Category_Id, subCategoryBO.getId());
        serviceParams.put(Keys.Page, pageIndex);
        serviceParams.put(Keys.Items, maxItems);

        new WebServicesVolleyTask(context, false, "",
                EnumUtils.ServiceName.GetCategoryProducts,
                EnumUtils.ServiceName.getServicePath(EnumUtils.ServiceName.GetCategoryProducts),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {

                    if (mAdapter != null) {
                        //set isLoading boolean to false....
                        mAdapter.setLoaded();
                        if (isFromLoadMore)
                            mAdapter.removeLoadingItem();
                    }

                    stopSwipeLoader();
                    if (taskItem.isError()) {
                        //show alert only on the selected tab...
                        showNoResult(true);
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                JSONArray productslist = jsonObject.getJSONArray("productslist");
                                totalRecords = jsonObject.getInt("TotalRecords");
                                Gson gson = new Gson();
                                if (productslist.length() > 0) {
                                    showNoResult(false);
                                    Type listType = new TypeToken<List<ProductBO>>() {
                                    }.getType();
                                    List<ProductBO> productBOList = (List<ProductBO>) gson.fromJson(productslist.toString(),
                                            listType);
                                    setUpRecycler(productBOList, isFromLoadMore);
                                } else {
                                    if (!isFromLoadMore)
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


    //this method is used to get medications from service...
    public void callSearchProductsService(final String searchStr) {

        cancelTask();
        startRefreshingLoader();

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = null;
        serviceParams.put(Keys.Category_Type, EnumUtils.HomeTabs.Food.ordinal());
        serviceParams.put(Keys.Category_Id, subCategoryBO.getId());
        serviceParams.put(Keys.search_string, searchStr);

        asyncTask = new WebServicesVolleyTask(context, false, "",
                EnumUtils.ServiceName.ProductByName,
                EnumUtils.ServiceName.getServicePath(EnumUtils.ServiceName.ProductByName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {

                    if (mAdapter != null) {
                        //set isLoading boolean to false....
                        mAdapter.setLoaded();
                        mAdapter.removeLoadingItem();
                    }

                    stopSwipeLoader();
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
                                    setUpRecycler(productBOList, false);
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