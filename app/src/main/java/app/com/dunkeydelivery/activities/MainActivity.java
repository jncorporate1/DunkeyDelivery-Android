package app.com.dunkeydelivery.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.activities.auth.LoginActivity;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.SettingBO;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.items.UserBO;
import app.com.dunkeydelivery.modules.account.dialogs.ContactUsBSDialogFragment;
import app.com.dunkeydelivery.modules.account.fragments.pager.AccountViewPager;
import app.com.dunkeydelivery.modules.cart.fragments.CartMain;
import app.com.dunkeydelivery.modules.cart.fragments.Checkout;
import app.com.dunkeydelivery.modules.cart.fragments.ConfirmOrder;
import app.com.dunkeydelivery.modules.cart.fragments.items.CheckoutItem;
import app.com.dunkeydelivery.modules.deals.DealsAndPromotions;
import app.com.dunkeydelivery.modules.home.tabs.FragmentHome;
import app.com.dunkeydelivery.modules.home.tabs.ride.RideMain;
import app.com.dunkeydelivery.modules.orders.fragments.FragmentOrders;
import app.com.dunkeydelivery.modules.orders.fragments.OrderDetail;
import app.com.dunkeydelivery.modules.points.fragments.FragmentPoints;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.CartOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.FiltersOP;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.MarshMallowPermission;
import app.com.dunkeydelivery.utils.MiscUtils;
import app.com.dunkeydelivery.utils.PhoneUtils;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.ObjectSharedPreference;
import app.com.dunkeydelivery.utils.sharedprefs.SharedPref;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawer;
    private BottomNavigationView navigation;
    private FragmentManager fm;
    private FragmentHome fragmentHome = null;
    private FragmentOrders fragmentOrders = null;
    private FragmentPoints fragmentPoints = null;
    private RelativeLayout rlMainView;
    private Context context;
    private TextView tvUserName;
    public static Boolean checkMainActivity;
    private TextView tvEmail;
    private ImageView imvUser;

    //bottom tab click listener
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    removeAllFragments();
                    if (fragmentHome == null) {
                        fragmentHome = FragmentHome.newInstance();
//                        gotoNextFragmentNoAnimation(fragmentHome);
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, fragmentHome).commit();
                    } else {
                        fragmentHome.refreshToolbar();

                        getSupportFragmentManager().beginTransaction()
                                .show(fragmentHome).commit();
                        hideOthersFragment(fragmentHome);
                    }
                    return true;
                case R.id.navigation_order:

                    removeAllFragments();
                    if (fragmentOrders == null) {
                        fragmentOrders = FragmentOrders.newInstance();
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, fragmentOrders).commit();
                    } else {
                        fragmentOrders.refreshToolbar();
                        getSupportFragmentManager().beginTransaction()
                                .show(fragmentOrders).commit();
                        hideOthersFragment(fragmentOrders);
                    }
                    return true;
                case R.id.navigation_points:
                    removeAllFragments();
                    if (fragmentPoints == null) {
                        fragmentPoints = FragmentPoints.newInstance();
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, fragmentPoints).commit();
                    } else {
                        fragmentPoints.refreshToolbar();
                        getSupportFragmentManager().beginTransaction()
                                .show(fragmentPoints).commit();
                        hideOthersFragment(fragmentPoints);
                    }
                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        setUpUserDetail();

        setListeners(toolbar);

        setDrawerMenuListener();

        checkMainActivity = true;

        //set the base fragment which is home...
        if (fragmentHome == null) {
            fragmentHome = FragmentHome.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragmentHome).commit();
        }


        //keyboard listener...
        drawer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Fragment fragment = Activities.getCurrentFragment(context);
                if (fragment instanceof FragmentHome) {
                    Fragment fragment1 = ((FragmentHome) fragment).getSelectedFragment();
                    if (fragment1 instanceof RideMain) {

                        Rect r = new Rect();
                        drawer.getWindowVisibleDisplayFrame(r);
                        int screenHeight = drawer.getRootView().getHeight();

                        // r.bottom is the position above soft keypad or device button.
                        // if keypad is shown, the r.bottom is smaller than that before.
                        int keypadHeight = screenHeight - r.bottom;

                        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                            // keyboard is opened

                            hideBottomNavigation();
                        } else {
                            // keyboard is closed
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    showBottomNavigation();
                                }
                            }, 100);

                        }
                    }
                }

            }
        });

        if (getIntent() != null && getIntent().getStringExtra("EntityId") != null) {
            navigation.setSelectedItemId(R.id.navigation_order);
            gotoNextFragment(OrderDetail.newInstance(getIntent().getStringExtra("EntityId")));
        }

    }//onCreate


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null && intent.getStringExtra("EntityId") != null) {
            navigation.setSelectedItemId(R.id.navigation_order);
            gotoNextFragment(OrderDetail.newInstance(intent.getStringExtra("EntityId")));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        callRegisterPushNotificationApi();

    }

    //setup user details on login
    private void setUpUserDetail() {
        try {
            UserBO userBO = UserSharedPreference.readUserBO();
            if (userBO != null) {
                tvUserName.setText(userBO.getFullName().isEmpty() ? userBO.getFirstName() : userBO.getFullName());
                tvEmail.setText(userBO.getEmail());
                LogUtils.i("mess", "" + userBO.getProfilePictureUrl());
                ImageUtils.setCenterImage(userBO.getProfilePictureUrl(), imvUser, context, R.drawable.icon_user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //goto next fragment
    public void gotoNextFragment(Fragment fragment) {
        closeDrawer();
        FragmentTransaction ft = fm.beginTransaction();

        if (fragment instanceof CartMain) {
            ft.setCustomAnimations(R.anim.slide_in_from_bottom,
                    0, 0, R.anim.slide_out_to_bottom);
        } else {
            ft.setCustomAnimations(R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left, R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right);
        }

        ft.add(R.id.container, fragment);
        ft.addToBackStack("");
        ft.commit();
    }

    //goto next fragment with image transition
    public void gotoNextFragmentWithAnimationTransition(Fragment fragment) {
        closeDrawer();
        FragmentTransaction ft = fm.beginTransaction();

        if (fragment instanceof CartMain) {
            ft.setCustomAnimations(R.anim.slide_in_from_bottom,
                    0, 0, R.anim.slide_out_to_bottom);
        }

        ft.add(R.id.container, fragment);
        ft.addToBackStack("");
        ft.commit();
    }

    private void setListeners(final Toolbar toolbar) {

        //set drawer open/close listner...
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                rlMainView.setTranslationX(slideOffset * drawerView.getWidth());
                drawer.bringChildToFront(drawerView);
                drawer.requestLayout();
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //set bottom tab click listener
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    //cast widgets in drawer layout
    private void initViews() {
        tvEmail = (TextView) findViewById(R.id.tv_email);
        tvUserName = (TextView) findViewById(R.id.tv_username);
        imvUser = (ImageView) findViewById(R.id.imv_user);
        rlMainView = (RelativeLayout) findViewById(R.id.rl_mainview);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fm = getSupportFragmentManager();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
    }

    //hide other fragments to show one
    private void hideOthersFragment(Fragment fragment) {

        if (fragment instanceof FragmentHome) {
            hideFragment(fragmentOrders);
            hideFragment(fragmentPoints);
        } else if (fragment instanceof FragmentOrders) {
            hideFragment(fragmentHome);
            hideFragment(fragmentPoints);
        } else if (fragment instanceof FragmentPoints) {
            hideFragment(fragmentHome);
            hideFragment(fragmentOrders);
        }
    }

    //hide specific fragment
    private void hideFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .hide(fragment).commit();
        }
    }

    //set listeners on drawer menu widgets
    private void setDrawerMenuListener() {
        RadioButton rbAccount = (RadioButton) findViewById(R.id.rb_account);
        RadioButton rbContactUs = (RadioButton) findViewById(R.id.rb_contactus);
        RadioButton rbDeals = (RadioButton) findViewById(R.id.rb_deals);
        RadioButton rbLogout = (RadioButton) findViewById(R.id.rb_logout);

        rbAccount.setOnClickListener(this);
        rbContactUs.setOnClickListener(this);
        rbDeals.setOnClickListener(this);
        rbLogout.setOnClickListener(this);

    }

    //goto back fragment with animation
    public void goBackFragmentWithAnimation(int count) {
        // if count greater than zero popbackstack
        try {
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.slide_out_to_left, R.anim.slide_out_to_right
                    , R.anim.slide_out_to_left, R.anim.slide_out_to_right);
            if (count > 0) {
                for (int i = 0; i < count; i++)
                    fm.popBackStack();
            }

            ft.commit();
        } catch (Exception e) {
        }
    }

    //get reference of current visible fragment
    public Fragment getCurrentFragment() {
        Fragment fragment = (Fragment) fm.findFragmentById(R.id.container);
        return fragment;
    }

    //check visible fragment and get its reference
    public Fragment getVisibleFragment(Activity activity) {
        FragmentManager fragmentManager = (FragmentManager) fm;
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    //remove all fragments in fragment stack
    public void removeAllFragments() {
        try {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeAllFragmentsImmediate() {
        try {
            fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    public void gotoNextWithAddFragment(Fragment fragment) {
        closeDrawer();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left, R.anim.slide_in_from_left,
                R.anim.slide_out_to_right);
        ft.add(R.id.container, fragment);
        ft.addToBackStack("");
        ft.commit();
    }

    public void gotoNextFragmentNoAnimation(Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(0, 0);
        ft.add(R.id.container, fragment);
        ft.addToBackStack("");
        ft.commit();
    }

    public void goBackFragment(int count) {
        // if count greater than zero popbackstack
        try {
            if (count > 0) {
                for (int i = 0; i < count; i++)
                    fm.popBackStack();
            }
        } catch (Exception e) {
        }
    }

    public void gotoNextFragmentWithAnimation(Fragment fragment, int enterAnim, int exitAnim, int popEnter, int popExit) {
        try {
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(enterAnim, exitAnim
                    , popEnter, popExit);
            ft.replace(R.id.container, fragment);
            ft.addToBackStack("");
            ft.commit();
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void gotoSkipFragment(Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left, R.anim.slide_in_from_left,
                R.anim.slide_out_to_right);
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    public void removeFragment(Fragment fragment) {
        try {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.remove(fragment);
            fm.popBackStack();
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        //check if home page is not selected...
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fm.getBackStackEntryCount() == 0 && (navigation.getSelectedItemId() != R.id.navigation_home)) {
                setSelectedTab(0);
            } else if (getCurrentFragment() instanceof ConfirmOrder) {
                Activities.removeAllFragments(context);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkMainActivity = false;
    }

    public void openLeftDrawer() {
        try {
            drawer.openDrawer(Gravity.LEFT);
        } catch (Exception e) {
        }
    }

    public void closeDrawer() {
        try {
            drawer.closeDrawers();
        } catch (Exception e) {
        }
    }


    public void lockDrawer() {
        try {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                    Gravity.LEFT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unlockDrawer() {
        try {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.LEFT);
        } catch (Exception e) {
        }
    }

    public void hideBottomNavigation() {
        navigation.setVisibility(View.GONE);
    }

    public void showBottomNavigation() {
        navigation.setVisibility(View.VISIBLE);
    }

    public void setSelectedTab(int selectedTab) {
        switch (selectedTab) {
            case 0:
                navigation.setSelectedItemId(R.id.navigation_home);
                break;
            case 1:
                navigation.setSelectedItemId(R.id.navigation_order);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.rb_account:
                closeDrawer();
                Activities.gotoNextFragment(context, AccountViewPager.newInstance());
                break;

            case R.id.rb_deals:
                //open deals and promotions...
                Activities.gotoNextFragment(context, DealsAndPromotions.newInstance());
                break;

            case R.id.rb_contactus:
                //show bottom sheet
                ContactUsBSDialogFragment.
                        newInstance().
                        show(getSupportFragmentManager(), "Settings Bottom Sheet");
                break;
            case R.id.rb_logout:
                showLogoutAlert();
                break;
        }
    }

    //show alert when user wants to logout
    private void showLogoutAlert() {
        AlertOP.showAlert(context, getString(R.string.logout),
                getString(R.string.logout_confirmation), getString(R.string.yes),
                getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPref.clearCache();
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        closeDrawer();
                    }
                });
    }

    //call api to register push notification token on server
    private void callRegisterPushNotificationApi() {

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        serviceParams.put(Keys.DeviceName, PhoneUtils.getDeviceName());
        serviceParams.put(Keys.UDID, PhoneUtils.getDeviceID(context));
        serviceParams.put(Keys.IsAndroidPlatform, true);
        serviceParams.put(Keys.IsPlayStore, false);
        serviceParams.put(Keys.IsProduction, true);
        serviceParams.put(Keys.AuthToken, FirebaseInstanceId.getInstance().getToken());
        serviceParams.put(Keys.User_ID, UserSharedPreference.readUserBO().getId());
        serviceParams.put(Keys.SignInType, EnumUtils.SignInType.getSignInType(EnumUtils.SignInType.Other));

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.RegisterPushNotification;

        new WebServicesVolleyTask(context, false, "",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.POST, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);

                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                    } else {
                        try {

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SettingBO settingBO = ObjectSharedPreference.getObject(SettingBO.class, Keys.SETTINGS);
        if (settingBO.getContactNo() != null) {
            MiscUtils.callPhone(this, settingBO.getContactNo(), getCurrentFragment());
        } else {
            MiscUtils.callPhone(this, "1234", getCurrentFragment());
        }

    }

}//main
