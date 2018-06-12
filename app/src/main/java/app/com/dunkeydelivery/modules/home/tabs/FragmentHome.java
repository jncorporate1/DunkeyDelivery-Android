package app.com.dunkeydelivery.modules.home.tabs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.modules.cart.fragments.CartMain;
import app.com.dunkeydelivery.modules.filter.pager.FilterAlcohol;
import app.com.dunkeydelivery.modules.filter.pager.FilterOther;
import app.com.dunkeydelivery.modules.filter.pager.FilterViewPager;
import app.com.dunkeydelivery.modules.home.adapters.ViewPagerAdapter;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.fragments.AlcoholMain;
import app.com.dunkeydelivery.modules.home.tabs.food.FoodMain;
import app.com.dunkeydelivery.modules.home.tabs.pharmacy.PharmacyDetail;
import app.com.dunkeydelivery.modules.search.Search;
import app.com.dunkeydelivery.modules.search.items.AddressItem;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.EnumUtils.HomeTabs;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.MarshMallowPermission;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.MenuItemImgOrStr;
import app.com.dunkeydelivery.utils.toolbar.MenuItemSearch;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentHome extends ToolbarFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private boolean isHidden = false;
    Unbinder unbinder;

//    private HomePagerAdapter pagerAdapter;

    //Location variables....
    private GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    private Location mLastLocation;
    private static final long INTERVAL = 1000 * 60; // 1 minute
    private static final long FASTEST_INTERVAL = 1000 * 30; // 30 secs
    //Set the minimum displacement between location updates in meters
    private static final long SMALLEST_DISPLACEMENT = 20;

    private ViewPagerAdapter viewPagerAdapter;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.ll_search)
    LinearLayout llSearch;

    HomeTabs selectedTab = HomeTabs.Food;

    private AddressItem addressItem;
    private String selectedAddress = "";

    //tab titles...
    private int[] tabTitles = {R.string.food, R.string.alcohol, R.string.grocery,
            R.string.laundry, R.string.pharmacy, R.string.retail, R.string.ride
    };

    //tab icons...
    protected int[] imageResIdSelected = {
            R.drawable.food,
            R.drawable.alcohol,
            R.drawable.grocery,
            R.drawable.laundry,
            R.drawable.pharmacy,
            R.drawable.retail,
            R.drawable.ride,
    };

    //tab icons...
    protected int[] imageResId = {R.drawable.food_unselected,
            R.drawable.alcohol_unselected,
            R.drawable.grocery_unselected,
            R.drawable.laundary_unselected,
            R.drawable.pharmacy_unselected,
            R.drawable.retail_unselected,
            R.drawable.ride_unselected,
    };


    public static FragmentHome newInstance() {
        Bundle args = new Bundle();
        FragmentHome fragment = new FragmentHome();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home2,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpAdapter();

        addTabListener();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .enableAutoManage(((FragmentActivity) getActivity()), this)
                    .build();
            LogUtils.i("mess", "onViewCreated");
        }

        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activities.gotoNextFragment(context, Search.newInstance(true, addressItem));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        Log.i("mess", "isConnected ...............: " + mGoogleApiClient.isConnected());
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("mess", "Location update not resumed   " + mGoogleApiClient.isConnected());
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.i("mess", "Location update resumed .....................");
        }
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

    public void setSelectedTab(int position) {
        try {
            viewPager.setCurrentItem(position);
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            tab.select();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Fragment getSelectedFragment() {
        Fragment fragment = viewPagerAdapter.getRegisteredFragment(viewPager.getCurrentItem());
        return fragment;
    }

    private void addTabListener() {
        tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tabs = tabLayout.getTabAt(i);
            tabs.setCustomView(viewPagerAdapter.getTabView(i));
        }

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        LinearLayout llRoot = (LinearLayout) tab.getCustomView().findViewById(R.id.ll_root);
        TextView tvTitle = (TextView) llRoot.findViewById(R.id.tv_title);
        tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
        ImageView img = (ImageView) llRoot.findViewById(R.id.iv_icon);
        img.setImageResource(imageResIdSelected[0]);
        llRoot.setBackgroundColor(ContextCompat.getColor(context, R.color.food_selected_color));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                try {
                    selectedTab = HomeTabs.getTab(tab.getPosition());
                    LinearLayout llRoot = (LinearLayout) tab.getCustomView().findViewById(R.id.ll_root);
                    TextView tvTitle = (TextView) llRoot.findViewById(R.id.tv_title);
                    tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
                    ImageView img = (ImageView) llRoot.findViewById(R.id.iv_icon);
                    img.setImageResource(imageResIdSelected[tab.getPosition()]);
                    llRoot.setBackgroundColor(ContextCompat.getColor(context, HomeTabs.getTabColor(tab.getPosition())));
                    refreshToolbar();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                try {
                    LinearLayout llRoot = (LinearLayout) tab.getCustomView().findViewById(R.id.ll_root);
                    TextView tvTitle = (TextView) llRoot.findViewById(R.id.tv_title);
                    tvTitle.setTextColor(ContextCompat.getColor(context, R.color.grey3));
                    ImageView img = (ImageView) llRoot.findViewById(R.id.iv_icon);
                    img.setImageResource(imageResId[tab.getPosition()]);
                    llRoot.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_lightest));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setUpAdapter() {

        if (viewPagerAdapter == null) {
            viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), context, tabTitles, imageResId);
        }

        viewPager.setOffscreenPageLimit(6);
        viewPager.setAdapter(viewPagerAdapter);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHidden = hidden;
        refreshToolbar();
    }


    public String getAddressFromLocation(Context context, double lat, double lng) {

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null && addresses.size() > 0) {

            Address address = addresses.get(0);

            String city = address.getLocality();
            String country = address.getCountryName();

            if (TextUtils.isEmpty(city))
                city = address.getAdminArea();

            return city + ", " + country;
        }
        return "";
    }


    @Override
    public void refreshToolbar() {
        if (!isHidden) {
            Activities.unLockDrawer(context);
            Activities.hideBottomNavigation(context, false);
            switch (selectedTab) {
                case Food:
//                    refreshToolbar();
                case Alcohol:
                case Grocery:
                case Laundry:
                case Pharmacy:
                case Retail:

                    MenuItemImgOrStr menuItemFilter = new MenuItemImgOrStr(R.drawable.ic_filter, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (selectedTab == HomeTabs.Alcohol) {
                                Activities.gotoNextFragment(context,
                                        FilterAlcohol.newInstance(EnumUtils.FilterType.Alcohol.ordinal()));
                            } else if (selectedTab == HomeTabs.Food) {
                                Activities.gotoNextFragment(context, FilterViewPager.newInstance(false));
                            } else {
                                Activities.gotoNextFragment(context, FilterOther.newInstance(false));
                            }

                        }
                    });
                    MenuItemImgOrStr menuItemCart = new MenuItemImgOrStr(R.drawable.ic_cart, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Activities.gotoNextFragment(context, CartMain.newInstance());
                        }
                    });


                    MenuItemSearch menuItemSearch = new MenuItemSearch("Search", selectedAddress, null, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //On Search click...
                            Activities.gotoNextFragment(context, Search.newInstance(false, addressItem));
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //on Target button click...
                            //Toast.makeText(context, "Get User Location", Toast.LENGTH_SHORT).show();
                            try {
                                getUserLocation();
                                selectedAddress = getAddressFromLocation(context, Double.parseDouble(UserSharedPreference.readUserLat()), Double.parseDouble(UserSharedPreference.readUserLng()));
                                refreshToolbar();
                            } catch (Exception e) {
                                LogUtils.i("mess", "" + e.toString());
                            }
//                            Fragment fragment = viewPagerAdapter.getRegisteredFragment(HomeTabs.Food.getNumVal());
//                            if (fragment instanceof FoodMain) {
//                                ((FoodMain) fragment).callGetStoresService(HomeTabs.Food.getNumVal());
//
//                            }
//                            if (viewPagerAdapter != null) {
//                                for (int i = 0; i < 6; i++) {
//                                    Fragment fragment = viewPagerAdapter.getRegisteredFragment(i);
//                                    if (fragment instanceof FoodMain) {
//                                        ((FoodMain) fragment).setSelectedAddressItem(addressItem);
//                                        ((FoodMain) fragment).callGetStoresService(i);
//                                    }
//                                }
//                            }
                        }
                    });
                    ToolbarOp.refresh(getView(), getActivity(), "",
                            null, ToolbarOp.Theme.Dark, 0, null, menuItemSearch, menuItemFilter, menuItemCart);
                    break;

                case Ride:
                    ToolbarOp.refresh(getView(), getActivity(), getString(R.string.ride),
                            null, ToolbarOp.Theme.Dark, 0, null, null);
                    break;


            }
        }
    }


    //************************** Current Location Methods ***************************//

    private void checkPermissionAndStartLocation() {
        if (!MarshMallowPermission.checkPermissionForLocation()) {
            // If request is not allowed then show request alert
            MarshMallowPermission.requestPermissionForLocation(getActivity(), FragmentHome.this);
        } else {

            try {
                if (mGoogleApiClient.isConnected()) {
                    startLocationUpdates();
                    getUserLocation();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void getUserLocation() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mLastLocation == null) {
                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                mGoogleApiClient);
                    }
                    if (mLastLocation != null) {
                        // TODO call service if True
//                        Toast.makeText(context, "LAt: " + mLastLocation.getLatitude() +
//                                " Lng: " + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                        UserSharedPreference.saveUserLocation(mLastLocation.getLatitude() + "",
                                mLastLocation.getLongitude() + "");
                        if (addressItem == null) {
                            addressItem = new AddressItem();
                        }
                        addressItem.setLatitude(mLastLocation.getLatitude());
                        addressItem.setLongitude(mLastLocation.getLongitude());
                        LogUtils.i("mess", "la long   " + mLastLocation.getLatitude() + "    " + mLastLocation.getLongitude());

                        //display user current location in search field
                        selectedAddress = getAddressFromLocation(context, Double.parseDouble(UserSharedPreference.readUserLat()), Double.parseDouble(UserSharedPreference.readUserLng()));
                        if (Activities.getCurrentFragment(context) instanceof FragmentHome) {
                            refreshToolbar();
                        }


                    } else {
                        final LocationManager manager = (LocationManager)
                                getActivity().getSystemService(Context.LOCATION_SERVICE);

                        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                            AlertOP.showAlert(context, getString(R.string.enable_location),
                                    getString(R.string.enable_location_msg), "Settings",
                                    "Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
                                        }
                                    }, null);
                        }

                    }
                    callServices(addressItem);
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }, 100);


    }

    private void callServices(AddressItem addressItem) {
        if (viewPagerAdapter != null) {
            if (selectedTab == HomeTabs.Alcohol) {
                Fragment fragment = viewPagerAdapter.getRegisteredFragment(HomeTabs.Alcohol.getNumVal());
                if (fragment != null) {
                    ((AlcoholMain) fragment).setSelectedAddressItem(addressItem);
                    ((AlcoholMain) fragment).callGetAlcoholDetailsApi();
                }
            } else {
                for (int i = 0; i < 6; i++) {
                    Fragment fragment = viewPagerAdapter.getRegisteredFragment(i);
                    if (fragment instanceof FoodMain) {
                        this.addressItem = addressItem;
                        ((FoodMain) fragment).setSelectedAddressItem(addressItem);
                        ((FoodMain) fragment).callGetStoresService(i);
                    } else if (fragment instanceof AlcoholMain) {
                        ((AlcoholMain) fragment).setSelectedAddressItem(addressItem);
                        ((AlcoholMain) fragment).callGetAlcoholDetailsApi();
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSearchLocation(AddressItem addressItem) {
        if (addressItem != null) {
            this.addressItem = addressItem;
            selectedAddress = addressItem.getAddressLine1();
            callServices(addressItem);
        }
        refreshToolbar();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCancelSearch(String empty) {
        if (selectedTab == HomeTabs.Alcohol) {
            Fragment fragment = getSelectedFragment();
            if (fragment instanceof AlcoholMain) {
                ((AlcoholMain) (fragment)).storeIds = empty;
            }
//        if(storeIds instanceof String) {

        }

        addressItem = null;
//        selectedAddress = empty;
        refreshToolbar();
        callServices(addressItem);
    }

    // Trigger new location updates at interval
    protected void startLocationUpdates() {
        try {
            // Create the location request
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(INTERVAL)
                    .setFastestInterval(FASTEST_INTERVAL)
                    .setSmallestDisplacement(SMALLEST_DISPLACEMENT);
            // Request location updates
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, FragmentHome.this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkPermissionAndStartLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        LogUtils.i("mess", "Developer    " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            mLastLocation = location;
            getUserLocation();
        }
    }

}