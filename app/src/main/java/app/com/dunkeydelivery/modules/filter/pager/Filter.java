package app.com.dunkeydelivery.modules.filter.pager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.modules.filter.pager.items.FilterItem;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.FiltersOP;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.customviews.ToggleButtonGroupTableLayout;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomCheckBox;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Filter extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Context context;
    private String TAG = this.getClass().getSimpleName();
    Unbinder unbinder;

    @BindView(R.id.tv_distance)
    TextView tvDistance;

    @BindView(R.id.tg_layout)
    ToggleButtonGroupTableLayout toggleButtonGroupTableLayout;

    @BindView(R.id.rb_distance)
    RadioButton rbDistance;
    @BindView(R.id.rb_rating)
    RadioButton rbRating;
    @BindView(R.id.rb_time)
    RadioButton rbTime;
    @BindView(R.id.rb_price)
    RadioButton rbPrice;
    @BindView(R.id.rb_delivery)
    RadioButton rbDelivery;
    @BindView(R.id.rb_atoz)
    RadioButton rbAtoZ;
    @BindView(R.id.rb_relevence)
    RadioButton rbRelevence;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;


    //delivery layouts for now we are setting it like this..
    //only for alpha...
    @BindView(R.id.ll_d1)
    LinearLayout llD1;
    @BindView(R.id.ll_d2)
    LinearLayout llD2;
    @BindView(R.id.ll_d3)
    LinearLayout llD3;
    @BindView(R.id.ll_d4)
    LinearLayout llD4;
    @BindView(R.id.rb_delivery_1)
    RadioButton rbD1;
    @BindView(R.id.rb_delivery_2)
    RadioButton rbD2;
    @BindView(R.id.rb_delivery_3)
    RadioButton rbD3;
    @BindView(R.id.rb_delivery_4)
    RadioButton rbD4;

    @BindView(R.id.ll_rd_time1)
    LinearLayout llTime1;
    @BindView(R.id.ll_rd_time2)
    LinearLayout llTime2;
    @BindView(R.id.ll_rd_time3)
    LinearLayout llTime3;

    @BindView(R.id.rb_time_1)
    RadioButton rbTime1;
    @BindView(R.id.rb_time_2)
    RadioButton rbTime2;
    @BindView(R.id.rb_time_3)
    RadioButton rbTime3;

    @BindView(R.id.rb_price_1)
    CustomCheckBox rbPrice1;
    @BindView(R.id.rb_price_2)
    CustomCheckBox rbPrice2;
    @BindView(R.id.rb_price_3)
    CustomCheckBox rbPrice3;
    @BindView(R.id.rb_price_4)
    CustomCheckBox rbPrice4;

    @BindView(R.id.sw_special)
    Switch swSpecial;
    @BindView(R.id.sw_free_delivery)
    Switch swFreeDelivery;
    @BindView(R.id.sw_new_restaurants)
    Switch swNewRestaurants;

    boolean isShowDistance = false;
    private FilterItem filter;
    private float userRating;

    public static Filter newInstance(boolean isShowDistance) {
        Bundle args = new Bundle();
        Filter fragment = new Filter();
        args.putBoolean("isShowDistance", isShowDistance);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter,
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
            isShowDistance = getArguments().getBoolean("isShowDistance");
        }


        if (isShowDistance) {
            toggleButtonGroupTableLayout.setVisibility(View.VISIBLE);
        }

        rbDistance.performClick();

        setUpData();
        setListener();
    }

    private void setUpData() {
        filter = FiltersOP.getFilters(Keys.Filter_FOOD);
        if (filter != null) {
            ratingbar.setStar(filter.getRating());
            userRating = filter.getRating();
            setSortByData(filter.getSortBy());

            Integer estTime = filter.getMinDeliveryTime();
            if (estTime == 30) {
                rbTime1.setChecked(true);
            } else if (estTime == 45) {
                rbTime2.setChecked(true);
            } else if (estTime == 60) {
                rbTime3.setChecked(true);
            }

            setUpPriceData(filter.getPriceRanges());

            setUpMinDelivery(filter.getMinDeliveryCharges());

            if (filter.isFreeDelivery()) {
                swFreeDelivery.performClick();
            }

            if (filter.isNewRestaurants()) {
                swNewRestaurants.performClick();
            }

            if (filter.isSpecial()) {
                swSpecial.performClick();
            }
        }
    }

    private void setUpMinDelivery(Double minDeliveryCharges) {
        if (minDeliveryCharges == 5) {
            rbD1.setChecked(true);
        } else if (minDeliveryCharges == 10) {
            rbD2.setChecked(true);
        } else if (minDeliveryCharges == 15) {
            rbD3.setChecked(true);
        } else if (minDeliveryCharges == 20) {
            rbD4.setChecked(true);
        }

    }

    private void setUpPriceData(String price) {
        String[] priceRanges = price.split(",");
        for (String currentPrice : priceRanges) {
            if (currentPrice.equalsIgnoreCase("10")) {
                rbPrice1.setChecked(true);
            } else if (currentPrice.equalsIgnoreCase("100")) {
                rbPrice2.setChecked(true);
            } else if (currentPrice.equalsIgnoreCase("1000")) {
                rbPrice3.setChecked(true);
            } else if (currentPrice.equalsIgnoreCase("10000")) {
                rbPrice4.setChecked(true);
            }
        }
    }

    private void setSortByData(Integer sortBy) {
        RadioButton radioButton;
        switch (EnumUtils.SortBy.getSortBy(sortBy)) {
            case Distance:
                radioButton = rbDistance;
                break;
            case Rating:
                radioButton = rbRating;
                break;
            case DeliveryTime:
                radioButton = rbTime;
                break;
            case Price:
                radioButton = rbPrice;
                break;
            case MinDelivery:
                radioButton = rbDelivery;
                break;
            case AtoZ:
                radioButton = rbAtoZ;
                break;
            case Relevance:
                radioButton = rbRelevence;
                break;
            default:
                radioButton = rbDistance;
                break;
        }
        radioButton.performClick();
        tvDistance.setText(radioButton.getText());
    }

    private void setListener() {
        tvDistance.setOnClickListener(this);

        llTime1.setOnClickListener(this);
        llTime2.setOnClickListener(this);
        llTime3.setOnClickListener(this);

        llD1.setOnClickListener(this);
        llD2.setOnClickListener(this);
        llD3.setOnClickListener(this);
        llD4.setOnClickListener(this);

        rbDistance.setOnCheckedChangeListener(this);
        rbRating.setOnCheckedChangeListener(this);
        rbDelivery.setOnCheckedChangeListener(this);
        rbPrice.setOnCheckedChangeListener(this);
        rbTime.setOnCheckedChangeListener(this);
        rbAtoZ.setOnCheckedChangeListener(this);
        rbRelevence.setOnCheckedChangeListener(this);


        ratingbar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                userRating = RatingCount;
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    private void initViews(View view) {
        //Initialize main content Linear layout.

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_distance:
                if (toggleButtonGroupTableLayout.getVisibility() == View.VISIBLE) {
                    toggleButtonGroupTableLayout.setVisibility(View.GONE);
                } else {
                    toggleButtonGroupTableLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_rd_time1:
                rbTime1.setChecked(true);
                rbTime2.setChecked(false);
                rbTime3.setChecked(false);
                break;
            case R.id.ll_rd_time2:
                rbTime1.setChecked(false);
                rbTime2.setChecked(true);
                rbTime3.setChecked(false);
                break;
            case R.id.ll_rd_time3:
                rbTime1.setChecked(false);
                rbTime2.setChecked(false);
                rbTime3.setChecked(true);
                break;
            case R.id.ll_d1:
                rbD1.setChecked(true);
                rbD2.setChecked(false);
                rbD3.setChecked(false);
                rbD4.setChecked(false);
                break;
            case R.id.ll_d2:
                rbD1.setChecked(false);
                rbD2.setChecked(true);
                rbD3.setChecked(false);
                rbD4.setChecked(false);
                break;
            case R.id.ll_d3:
                rbD1.setChecked(false);
                rbD2.setChecked(false);
                rbD3.setChecked(true);
                rbD4.setChecked(false);
                break;
            case R.id.ll_d4:
                rbD1.setChecked(false);
                rbD2.setChecked(false);
                rbD3.setChecked(false);
                rbD4.setChecked(true);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id) {
            case R.id.rb_distance:
                if (isChecked)
                    tvDistance.setText(rbDistance.getText());
                break;
            case R.id.rb_rating:
                if (isChecked)
                    tvDistance.setText(rbRating.getText());
                break;
            case R.id.rb_time:
                if (isChecked)
                    tvDistance.setText(rbTime.getText());
                break;
            case R.id.rb_price:
                if (isChecked)
                    tvDistance.setText(rbPrice.getText());
                break;
            case R.id.rb_delivery:
                if (isChecked)
                    tvDistance.setText(rbDelivery.getText());
                break;
            case R.id.rb_atoz:
                if (isChecked)
                    tvDistance.setText(rbAtoZ.getText());
                break;
            case R.id.rb_relevence:
                if (isChecked)
                    tvDistance.setText(rbRelevence.getText());
                break;
        }
    }

    public void clearFilter() {
        rbDistance.performClick();
        ratingbar.setStar(0);
        rbD1.setChecked(false);
        rbD2.setChecked(false);
        rbD3.setChecked(false);
        rbD4.setChecked(false);

        rbTime1.setChecked(false);
        rbTime2.setChecked(false);
        rbTime3.setChecked(false);

        rbPrice1.setChecked(false);
        rbPrice2.setChecked(false);
        rbPrice3.setChecked(false);
        rbPrice4.setChecked(false);

        if (swFreeDelivery.isChecked())
            swFreeDelivery.performClick();

        if (swNewRestaurants.isChecked())
            swNewRestaurants.performClick();

        if (swSpecial.isChecked())
            swSpecial.performClick();
        userRating = 0;
    }

    public FilterItem getFilter() {
        return getFilterData();
    }

    private FilterItem getFilterData() {
        filter = new FilterItem();

        filter.setSortBy(getSortByData());
        filter.setRating((int) userRating);
        int estTime = 0;
        if (rbTime1.isChecked())
            estTime = 30;
        if (rbTime2.isChecked())
            estTime = 45;
        if (rbTime3.isChecked())
            estTime = 60;
        filter.setMinDeliveryTime(estTime);

        String price = getPriceData();
        filter.setPriceRanges(price);

        double minDelivery = getMinDelivery();
        filter.setMinDeliveryCharges(minDelivery);

        if (swFreeDelivery.isChecked())
            filter.setFreeDelivery(true);
        else
            filter.setFreeDelivery(false);
        if (swNewRestaurants.isChecked())
            filter.setNewRestaurants(true);
        else
            filter.setNewRestaurants(false);
        if (swSpecial.isChecked())
            filter.setSpecial(true);
        else
            filter.setSpecial(false);

        if (filter != null && (filter.getPriceRanges() == null || filter.getPriceRanges().trim().equals("")) &&
                (filter.getCuisines() == null || filter.getCuisines().trim().equals("")) &&
                (filter.getMinDeliveryCharges() == null || filter.getMinDeliveryCharges() == 0) &&
                (filter.getMinDeliveryTime() == null || filter.getMinDeliveryTime() == 0) &&
                (filter.getRating() == null || filter.getRating() == 0) &&
                (!filter.isFreeDelivery()) &&
                (!filter.isNewRestaurants()) &&
                (!filter.isSpecial())
                )
            return null;

        return filter;
    }

    private double getMinDelivery() {
        if (rbD1.isChecked())
            return 5;
        else if (rbD2.isChecked())
            return 10;
        else if (rbD3.isChecked())
            return 15;
        else if (rbD4.isChecked())
            return 20;
        return 0;
    }

    private String getPriceData() {
        String price = "";
        if (rbPrice1.isChecked()) {
            price += "10,";
        }
        if (rbPrice2.isChecked()) {
            price += "100,";
        }
        if (rbPrice3.isChecked()) {
            price += "1000,";
        }
        if (rbPrice4.isChecked()) {
            price += "10000,";
        }
        if (price.length() > 0) {
            return price.substring(0, price.length() - 1);
        } else {
            return "";
        }
    }

    private int getSortByData() {
        if (rbDistance.isChecked()) {
            return EnumUtils.SortBy.getNumValue(EnumUtils.SortBy.Distance);
        } else if (rbRating.isChecked()) {
            return EnumUtils.SortBy.getNumValue(EnumUtils.SortBy.Rating);
        } else if (rbTime.isChecked()) {
            return EnumUtils.SortBy.getNumValue(EnumUtils.SortBy.DeliveryTime);
        } else if (rbPrice.isChecked()) {
            return EnumUtils.SortBy.getNumValue(EnumUtils.SortBy.Price);
        } else if (rbDelivery.isChecked()) {
            return EnumUtils.SortBy.getNumValue(EnumUtils.SortBy.MinDelivery);
        } else if (rbAtoZ.isChecked()) {
            return EnumUtils.SortBy.getNumValue(EnumUtils.SortBy.AtoZ);
        } else if (rbRelevence.isChecked()) {
            return EnumUtils.SortBy.getNumValue(EnumUtils.SortBy.Relevance);
        } else {
            return EnumUtils.SortBy.getNumValue(EnumUtils.SortBy.Distance);

        }
    }

}