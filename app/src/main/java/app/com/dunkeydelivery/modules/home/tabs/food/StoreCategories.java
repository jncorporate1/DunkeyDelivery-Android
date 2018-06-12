package app.com.dunkeydelivery.modules.home.tabs.food;

import android.content.Context;
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
import android.widget.ImageButton;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;

import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.items.ItemBO;
import app.com.dunkeydelivery.modules.cart.fragments.CartMain;
import app.com.dunkeydelivery.modules.home.adapters.CategoryListAdapter;
import app.com.dunkeydelivery.modules.home.tabs.FragmentProductList;
import app.com.dunkeydelivery.modules.home.tabs.food.pager.StoreViewPager;
import app.com.dunkeydelivery.modules.search.Search;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.toolbar.MenuItemImgOrStr;
import app.com.dunkeydelivery.utils.toolbar.MenuItemSearch;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StoreCategories extends ToolbarFragment implements View.OnClickListener {
    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private boolean isHidden;
    Unbinder unbinder;
    private int totalRecords = 0;
    private int maxItems = Constants.MAX_ITEMS_TO_LOAD;
    private int startIndex = 0;
    private CategoryListAdapter mAdapter;

    public static String ARG_PARAM1 = "selectedTab";
    EnumUtils.HomeTabs selectedTab;

    @BindView(R.id.ratingbar)
    RatingBar ratingBar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.rv_swipe_refresh)
    SwipeRefreshLayout rvSwipeRefresh;

    @BindView(R.id.tv_swipe_refresh)
    SwipeRefreshLayout tvSwipeRefresh;

    @BindView(R.id.tv_new_streams)
    TextView tvNewStreams;

    @BindView(R.id.ib_search)
    ImageButton ibSearch;

    @BindView(R.id.ib_review)
    ImageButton ibReview;

    @BindView(R.id.ib_info)
    ImageButton ibInfo;

    public static StoreCategories newInstance(int selectedTabIndex) {
        Bundle args = new Bundle();
        StoreCategories fragment = new StoreCategories();
        args.putInt(ARG_PARAM1, selectedTabIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_store_detail,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize all views

        if (getArguments() != null) {
            selectedTab = EnumUtils.HomeTabs.getTab(getArguments().getInt(ARG_PARAM1, 0));
        }

        ratingBar.setStar(4f);
        ratingBar.setmClickable(false);

        initViews();

        setListeners();

        setUpRecycler();


    }//onViewCreated

    private void setListeners() {
        ibInfo.setOnClickListener(this);
        ibReview.setOnClickListener(this);
        ibSearch.setOnClickListener(this);
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
                startIndex = 0;
                totalRecords = 0;
                stopSwipeLoader();
//                callStreamsService(startIndex, maxItems, false);
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

    private void setUpRecycler() {

        final List<ItemBO> itemBOs = new ArrayList<>();

        itemBOs.add(new ItemBO("Starter", "123"));
        itemBOs.add(new ItemBO("Pizzas", "123"));
        itemBOs.add(new ItemBO("Chick Meals", "123"));
        itemBOs.add(new ItemBO("Burgers", "123"));
        itemBOs.add(new ItemBO("Beverages", "123"));

        if (mAdapter == null || recyclerView.getAdapter() == null) {

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            mAdapter = new CategoryListAdapter(itemBOs, context, recyclerView);

            mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
//                    streamBOs = mAdapter.getItems();
                    if (mAdapter.getItems().size() >= maxItems) {
                        mAdapter.addLoadingItem();
                    }
                }
            });

            mAdapter.setClickListener(new CategoryListAdapter.ClickListeners() {
                @Override
                public void onRowClick(int position) {
                    Activities.gotoNextFragment(context, FragmentProductList.newInstance(null,null));
                }
            });

            recyclerView.setAdapter(mAdapter);

        } else {
            mAdapter.addItems(itemBOs);
            mAdapter.setLoaded();
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
        Log.i("", "refreshToolbar: ");
        Activities.hideBottomNavigation(context, false);
        MenuItemImgOrStr menuItemImgOrStr = new MenuItemImgOrStr(R.drawable.ic_cart, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activities.gotoNextFragment(context, CartMain.newInstance());
            }
        });

        MenuItemSearch menuItemSearch = new MenuItemSearch(getString(R.string.search_your_product), R.drawable.ic_search_grey,
                null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On Search click...
                Activities.gotoNextFragment(context, Search.newInstanceForSearch(true, null));
            }
        }, null);

        ToolbarOp.refresh(getView(), getActivity(), "",
                null, ToolbarOp.Theme.Dark, 0, null, menuItemSearch, menuItemImgOrStr);
    }


    private void initViews(View view) {
        //Initialize main content Linear layout.

    }

    private void stopSwipeLoader() {
        tvSwipeRefresh.setRefreshing(false);
        rvSwipeRefresh.setRefreshing(false);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_search:
                Activities.gotoNextFragment(context, Search.newInstanceForSearch(true, null));
                break;
            case R.id.ib_review:
                Activities.gotoNextFragment(context, StoreViewPager.newInstance(false, false, null));
                break;
            case R.id.ib_info:
                Activities.gotoNextFragment(context, StoreViewPager.newInstance(true, false, null));
                break;
        }
    }
}