package app.com.dunkeydelivery.modules.home.tabs.food.pager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hedgehog.ratingbar.RatingBar;

import org.apmem.tools.layouts.FlowLayout;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.PixelsOp;
import app.com.dunkeydelivery.utils.StoreUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StoreInfo extends Fragment {
    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private Unbinder unbinder;
    private StoreBO storeBO;
    @BindView(R.id.mapView)
    MapView mMapView;
    private GoogleMap googleMap;

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.flow_layout)
    FlowLayout flowLayout;
    @BindView(R.id.tv_store_address)
    TextView tvStoreAddress;
    @BindView(R.id.tv_store_phone)
    TextView tvStorePhone;


    @BindView(R.id.tv_monday_time)
    TextView tvMondayTime;
    @BindView(R.id.tv_tuesday_time)
    TextView tvTuesdayTime;
    @BindView(R.id.tv_wednessday_time)
    TextView tvWednesdayTime;
    @BindView(R.id.tv_thursday_time)
    TextView tvThursdayTime;
    @BindView(R.id.tv_friday_time)
    TextView tvFridayTime;
    @BindView(R.id.tv_saturday_time)
    TextView tvSatTime;
    @BindView(R.id.tv_sunday_time)
    TextView tvSunTime;


    @BindView(R.id.ratingbar)
    RatingBar ratingBar;

    @BindView(R.id.ll_store)
    LinearLayout llStore;

    boolean isForAlcoholStore = false;

    public static StoreInfo newInstance(boolean isForAlcoholStore, StoreBO storeBO) {
        Bundle args = new Bundle();
        StoreInfo fragment = new StoreInfo();
        args.putBoolean(StoreViewPager.ARG_PARAM2, isForAlcoholStore);
        args.putParcelable(StoreViewPager.ARG_PARAM3, storeBO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_store_info,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            isForAlcoholStore = getArguments().getBoolean(StoreViewPager.ARG_PARAM2);
            storeBO = getArguments().getParcelable(StoreViewPager.ARG_PARAM3);
            if (isForAlcoholStore) {
                llStore.setVisibility(View.GONE);
            }
        }

        ratingBar.setmClickable(false);

        setUpStoreDetail();


        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                try {
                    googleMap = mMap;
                    if (storeBO != null) {
                        final LatLng source = new LatLng(storeBO.getLatitude(),
                                storeBO.getLongitude());
                        MarkerOptions marker = new MarkerOptions().position(source).title("");
                        googleMap.addMarker(marker);
                        final LatLng destination = new LatLng(storeBO.getLatitude(), storeBO.getLongitude());
                        LatLngBounds.Builder bld = new LatLngBounds.Builder();
                        bld.include(source);
                        bld.include(destination);
                        final LatLngBounds bounds = bld.build();
                        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                            @Override
                            public void onMapLoaded() {
                                try {
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                                    googleMap.setMaxZoomPreference(18);
                                } catch (Exception ex) {
                                    ex.printStackTrace();

                                }

                            }
                        });
                    }
                } catch (Exception ex) {
                }

                // For showing a move to my location button
            }
        });

    }


    private void setUpStoreDetail() {
        try {
            if (storeBO != null) {
                tvTitle.setText(storeBO.getBusinessName());
                tvRate.setText(storeBO.getAverageRating() + "");
                ratingBar.setStar(storeBO.getAverageRating());
                tvStoreAddress.setText(storeBO.getAddress());
                tvStorePhone.setText(storeBO.getContactNumber());
                ImageUtils.setCenterImage(storeBO.getImageUrl(), ivLogo, context, R.drawable.logo);


                //setting timings
                tvMondayTime.setText(storeBO.getStoreDeliveryHours().getMondayTime());

                tvTuesdayTime.setText(storeBO.getStoreDeliveryHours().getTuesdayTime());

                tvWednesdayTime.setText(storeBO.getStoreDeliveryHours().getWedTime());

                tvThursdayTime.setText(storeBO.getStoreDeliveryHours().getThursdayTime());

                tvFridayTime.setText(storeBO.getStoreDeliveryHours().getFridayTime());

                tvSatTime.setText(storeBO.getStoreDeliveryHours().getSatTime());

                tvSunTime.setText(storeBO.getStoreDeliveryHours().getSunTime());
                tvSunTime.setText(storeBO.getStoreDeliveryHours().getSunTime());

                StoreUtils.addStoreTags(context, flowLayout, storeBO.getStoreTags());

            }

        } catch (Exception ex) {
        }


        //add tags..
//        StoreUtils.addStoreTags(context, flowLayout, storeBO.getStoreTags());
    }
//
//    private void setUpTags(List<StoreTag> storeTags) {
//        if (storeTags != null && storeTags.size() > 0) {
//            for (StoreTag tag : storeTags) {
//                View view = LayoutInflater.from(context).inflate(R.layout.item_tag, null);
//                TextView tv_tag = view.findViewById(R.id.tv_tag);
//                tv_tag.setText(tag.getTag());
//                flowLayout.addView(view);
//            }
//        }
//    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void initViews(View view) {
        //Initialize main content Linear layout.

    }

    public void setStore(StoreBO storeBO) {

        this.storeBO = storeBO;
        setUpStoreDetail();
    }
}//main