package app.com.dunkeydelivery.modules.home.tabs.alcohol.pager;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.icu.util.IslamicCalendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.adapters.TitlePagerAdapter;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.cart.fragments.CartMain;
import app.com.dunkeydelivery.modules.filter.pager.FilterAlcohol;
import app.com.dunkeydelivery.modules.filter.pager.items.FilterItem;
import app.com.dunkeydelivery.modules.home.items.ProductBO;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.items.AlcoholSeeAllBO;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.items.AlcoholStoreBO;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.items.Categories;
import app.com.dunkeydelivery.modules.search.Search;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.FiltersOP;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.SharedPref;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.MenuItemImgOrStr;
import app.com.dunkeydelivery.utils.toolbar.MenuItemSearch;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AllProductsViewPager extends ToolbarFragment {
    private Context context;
    private TitlePagerAdapter pagerAdapter;
    private Unbinder unbinder;
    private int tabNumber;
    private boolean check;
    private ArrayList<ProductBO> wineLastProducts;
    private ArrayList<ProductBO> liquorLastProducts;
    private ArrayList<ProductBO> beerLastProducts;
    private AlcoholStoreBO alcoholStoreBO;
    private Integer parentCategoryId;
    private Integer categoryId;
    private Float lat;
    private Float lon;
    private String categoryName;
    private static String ARG_PARAM1="alcoholStoreBO";
    private static String ARG_PARAM2="parentCategoryId";
    private static String ARG_PARAM3="categoryId";
    private static String ARG_PARAM4="lat";
    private static String ARG_PARAM5="lon";
    private static String ARG_PARAM6="value";
    private static String ARG_PARAM7="categoryName";


    @BindView(R.id.swipe_refresh1)
    SwipeRefreshLayout swipeRefresh1;

    @BindView(R.id.swipe_refresh2)
    SwipeRefreshLayout swipe_refresh2;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    protected List<String> pageTitle = new ArrayList<>();


    public static AllProductsViewPager newInstance(AlcoholStoreBO alcoholStoreBO , int parentCategoryId, int categoryId,Float lat,Float lon, String value,String categoryName) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1,alcoholStoreBO);
        args.putInt(ARG_PARAM2,parentCategoryId);
        args.putInt(ARG_PARAM3,categoryId);
        args.putFloat(ARG_PARAM4,lat);
        args.putFloat(ARG_PARAM5,lon);
        args.putString(ARG_PARAM6,value);
        args.putString(ARG_PARAM7,categoryName);
        AllProductsViewPager fragment = new AllProductsViewPager();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_viewpager_filter,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pageTitle.add(getString(R.string.wine));
        pageTitle.add(getString(R.string.liquor));
        pageTitle.add(getString(R.string.beer));

        tabNumber=0;

        check = false;

        if(getArguments()!=null)
        {
            alcoholStoreBO=getArguments().getParcelable(ARG_PARAM1);
            parentCategoryId=getArguments().getInt(ARG_PARAM2);
            categoryId=getArguments().getInt(ARG_PARAM3);
            lat=getArguments().getFloat(ARG_PARAM4);
            lon=getArguments().getFloat(ARG_PARAM5);
            tabNumber=Integer.parseInt(getArguments().getString(ARG_PARAM6));
            categoryName=getArguments().getString(ARG_PARAM7);
        }

        swipeRefresh1.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh1.setRefreshing(false);
                if(alcoholStoreBO!=null && parentCategoryId!=null && categoryId!=null) {
                    callGetAlcoholCategorySubcategoryApi(alcoholStoreBO.getId(),parentCategoryId,categoryId);
                }
            }
        });

        swipeRefresh1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh1.setRefreshing(false);
                check=false;
                if(alcoholStoreBO!=null && parentCategoryId!=null && categoryId!=null) {
                    callGetAlcoholCategorySubcategoryApi(alcoholStoreBO.getId(),parentCategoryId,categoryId);
                }
            }
        });

        swipe_refresh2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh2.setRefreshing(false);
                check=false;
                if(alcoholStoreBO!=null && parentCategoryId!=null && categoryId!=null) {
                    callGetAlcoholCategorySubcategoryApi(alcoholStoreBO.getId(),parentCategoryId,categoryId);
                }
            }
        });

        //set value in preference to show size in alcohol filter
        setFilterCheckValue();
    }

    private void setFilterCheckValue()
    {
        if(tabNumber==0 || tabNumber==1) {
            SharedPref.setCheckForFilterSizes(0);
        }
        else if(tabNumber==2)
        {
            SharedPref.setCheckForFilterSizes(1);
        }
    }

    //getter for swipe refresh layout reference
    public SwipeRefreshLayout getSwipeRefresh1()
    {
        return swipeRefresh1;
    }

    //getter for storeId
    public Integer getStoreId()
    {
        return alcoholStoreBO.getId();
    }

    //getter for main categoryId
    public Integer getCategoryId()
    {
        return categoryId;
    }

    //getter for main parentCategoryId
    public Integer getParentCategoryId()
    {
        return parentCategoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        SharedPref.setCheckForFilterSizes(2);
    }

    //create alcohol seeAll tabs for Wine , Liquor and beer
    private void createTabIcons() {

        //add divider line between tabs...
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(ContextCompat.getColor(context, R.color.grey_light));
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(10);
        linearLayout.setDividerDrawable(drawable);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (check == true) {
                    tabNumber = tab.getPosition();
                }
                if(tab.getPosition()==0 || tab.getPosition()==1) {
                    SharedPref.setCheckForFilterSizes(0);
                }
                else if(tab.getPosition()==2)
                {
                    SharedPref.setCheckForFilterSizes(1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //setup adapter for alcohol sub categories view pager
    private void setUpAdapter(AlcoholSeeAllBO alcoholSeeAllBO) {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ProductList.newInstance(alcoholSeeAllBO.getWineSeeAll() , "Wine",wineLastProducts));
        fragmentList.add(ProductList.newInstance(alcoholSeeAllBO.getLiquorSeeAll() , "Liquor",liquorLastProducts));
        fragmentList.add(ProductList.newInstance(alcoholSeeAllBO.getBeerSeeAll() , "Beer",beerLastProducts));

        pagerAdapter = new TitlePagerAdapter(getChildFragmentManager(), fragmentList, pageTitle);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());

        tabLayout.setupWithViewPager(viewPager);

        viewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
//                //select first tab...
//                TabLayout.Tab tab = tabLayout.getTabAt(tabNumber);
//                tab.select();
                viewPager.setCurrentItem(tabNumber);
            }
        }, 80);

        check = true;

        createTabIcons();
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP &&
                        keyCode == KeyEvent.KEYCODE_BACK) {
                    ProductList productList = (ProductList) pagerAdapter.getItem(viewPager.getCurrentItem());
                    if (productList.isProductsShown()) {
                        productList.showCategories();
                    } else {
                        Activities.goBackFragment(context, 1);
                    }
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void refreshToolbar() {
        Activities.hideBottomNavigation(context, true);
        MenuItemImgOrStr menuItemFilter = new MenuItemImgOrStr(R.drawable.ic_filter, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int filerType = viewPager.getCurrentItem() + 1;
                Activities.gotoNextFragment(context,
                        FilterAlcohol.newInstance(filerType));
                check=false;
            }
        });
        MenuItemImgOrStr menuItemCart = new MenuItemImgOrStr(R.drawable.ic_cart, new View.OnClickListener() {
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
                StoreBO storeBO=new StoreBO();
                storeBO.setId(alcoholStoreBO.getId());
                Activities.gotoNextFragment(context, Search.newInstanceForSearch(true, storeBO));
            }
        }, null);

        ToolbarOp.refresh(getView(), getActivity(), "",
                null, ToolbarOp.Theme.Dark, 0, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //on toolbar back press...
                        //check if the products are shown from see all then show the catrgories view....
                        if(pagerAdapter!=null) {
                            ProductList productList = (ProductList) pagerAdapter.getItem(viewPager.getCurrentItem());
                            if (productList.isProductsShown()) {
                                productList.showCategories();
                            } else {
                                Activities.goBackFragment(context, 1);
                            }
                        }
                        else
                        {
                            Activities.goBackFragment(context, 1);
                        }
                    }
                }, menuItemSearch, menuItemFilter, menuItemCart);
    }

    //set swipe refresh layouts visibility
    private void showNoResult(Boolean check)
    {
        if(check==true)
        {
            swipeRefresh1.setVisibility(View.VISIBLE);
            swipe_refresh2.setVisibility(View.GONE);
        }
        else
        {
            swipeRefresh1.setVisibility(View.GONE);
            swipe_refresh2.setVisibility(View.VISIBLE);
        }
    }

    //call api to get alcohol store category sub categories
    private void callGetAlcoholCategorySubcategoryApi(int storeId, int parentCategoryId,int categoryId) {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().accessToken);
        FilterItem filterItem= FiltersOP.getFilters(Keys.Filter_ALCOHOL);
        EnumUtils.ServiceName serviceName=null;
        if(filterItem!=null)
        {
            serviceName=EnumUtils.ServiceName.AlcoholFilterTypeStoreCategoryDetails;
            serviceParams.put(Keys.SortBy, filterItem.getSortBy() + "");
            serviceParams.put(Keys.Country, filterItem.getCountry() + "");
            serviceParams.put(Keys.Price, filterItem.getPrice() + "");
            serviceParams.put(Keys.Size, filterItem.getSize() + "");
            serviceParams.put(Keys.Type, categoryId);
            serviceParams.put(Keys.latitude, lat);
            serviceParams.put(Keys.longitude, lon);
        }
        else
        {
            serviceParams.put(Keys.Store_id, storeId);
            serviceName=EnumUtils.ServiceName.AlcoholStoreCategoryDetails;
        }
        serviceParams.put(Keys.Category_id, categoryId);
        serviceParams.put(Keys.Category_ParentId, parentCategoryId);

        new WebServicesVolleyTask(context, true, "Loading...",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);

                    if (taskItem.isError()) {
                        showNoResult(false);
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                showNoResult(true);
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                Gson gson = new Gson();
                                Type wineLastProductsArray=new TypeToken<ArrayList<ProductBO>>() {}.getType();
                                Type beerLastProductsArray=new TypeToken<ArrayList<ProductBO>>() {}.getType();
                                Type liquorLastProductsArray=new TypeToken<ArrayList<ProductBO>>() {}.getType();

                                wineLastProducts=gson.fromJson(jsonObject.getJSONArray("WineLastProducts").toString(),wineLastProductsArray);
                                beerLastProducts=gson.fromJson(jsonObject.getJSONArray("BeerLastProducts").toString(),beerLastProductsArray);
                                liquorLastProducts=gson.fromJson(jsonObject.getJSONArray("LiquorLastProducts").toString(),liquorLastProductsArray);

                                Type typeToken = new TypeToken<Categories>() {
                                }.getType();
                                AlcoholSeeAllBO alcoholSeeAllBO = gson.fromJson(jsonObject.getString("Categories"), AlcoholSeeAllBO.class);
                                setUpAdapter(alcoholSeeAllBO);
                            }
                            else
                            {
                                showNoResult(false);
                            }
                        } catch (Exception ex) {
                            LogUtils.i("mess ",""+ex.toString());
                        }
                        // if response is successful then do something
                    }
                }
                else
                {
                    showNoResult(false);
                }
            }
        });
    }

    @Subscribe
    public void getMessage(String val)
    {
        ProductList productList = (ProductList) pagerAdapter.getItem(viewPager.getCurrentItem());
        if(productList.alcoholSeeAllStack.isEmpty()) {
            callGetAlcoholCategorySubcategoryApi(alcoholStoreBO.getId(), 0, categoryId);
        }
    }
}//main