package app.com.dunkeydelivery.modules.filter.pager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AlcoholFilterSize;
import app.com.dunkeydelivery.items.ItemBO;
import app.com.dunkeydelivery.modules.filter.pager.adapters.AlcoholFilterSizes;
import app.com.dunkeydelivery.modules.filter.pager.adapters.BeerFilterListAdapter;
import app.com.dunkeydelivery.modules.filter.pager.items.FilterBO;
import app.com.dunkeydelivery.modules.filter.pager.items.FilterItem;
import app.com.dunkeydelivery.modules.filter.pager.items.FilterProductSizes;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.FiltersOP;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.customviews.ToggleButtonGroupTableLayout;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomCheckBox;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomRadioButton;
import app.com.dunkeydelivery.utils.sharedprefs.SharedPref;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.MenuItemImgOrStr;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FilterAlcohol extends ToolbarFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private Unbinder unbinder;
    private AlcoholFilterSizes alcoholFilterSizes;
    private ArrayList<FilterProductSizes> filterProductSizes;

    public static String ARG_PARAM1 = "filterType";

    @BindView(R.id.rvFilterSize)
    RecyclerView rvFilterSize;

    @BindView(R.id.tv_selling)
    TextView tvSelling;

    @BindView(R.id.rb_bestselling)
    RadioButton rbBestSelling;

    @BindView(R.id.rb_price_htol)
    RadioButton rbPriceHtoL;

    @BindView(R.id.rb_atoz)
    RadioButton rbAtoZ;

    @BindView(R.id.rb_price_ltoh)
    RadioButton rbPriceLtoH;

    @BindView(R.id.rb_ztoa)
    RadioButton rbZtoA;

    @BindView(R.id.ll_content)
    LinearLayout llContent;

    @BindView(R.id.tg_layout)
    ToggleButtonGroupTableLayout toggleButtonGroupTableLayout;


    @BindView(R.id.tv_country_1)
    CustomCheckBox tv_country_1;

    @BindView(R.id.tv_country_2)
    CustomCheckBox tv_country_2;


    @BindView(R.id.tv_country_3)
    CustomCheckBox tv_country_3;


    @BindView(R.id.tv_country_4)
    CustomCheckBox tv_country_4;


    @BindView(R.id.tv_country_5)
    CustomCheckBox tv_country_5;


    @BindView(R.id.tv_country_6)
    CustomCheckBox tv_country_6;


    @BindView(R.id.tv_price_1)
    RadioButton tv_price_1;


    @BindView(R.id.tv_price_2)
    RadioButton tv_price_2;


    @BindView(R.id.tv_price_3)
    RadioButton tv_price_3;

    @BindView(R.id.tv_price_4)
    RadioButton tv_price_4;

    @BindView(R.id.tv_price_5)
    RadioButton tv_price_5;

    private String selectedPrice;
    private EnumUtils.FilterType selectedAlcohat;

    public static FilterAlcohol newInstance(int filterType) {
        Bundle args = new Bundle();
        FilterAlcohol fragment = new FilterAlcohol();
        args.putInt(ARG_PARAM1, filterType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alcohol_filter,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            selectedAlcohat = EnumUtils.FilterType.getType(getArguments().getInt(ARG_PARAM1));
        }
        rbBestSelling.performClick();
        setUpData();
        setListeners();

        setUpFilterProductSizesList();
    }

    //setup filter by size recycler
    private void setUpFilterProductSizesList() {
        ArrayList<FilterProductSizes> filterProductSizesTemp=new ArrayList<FilterProductSizes>();
        if (!TextUtils.isEmpty(UserSharedPreference.getFilterProductSizes())) {
            Type type = new TypeToken<ArrayList<FilterProductSizes>>() {
            }.getType();
            Gson gson = new Gson();
            filterProductSizes = gson.fromJson(UserSharedPreference.getFilterProductSizes(), type);
            rvFilterSize.setLayoutManager(new GridLayoutManager(context, 2));

            for(FilterProductSizes filterProductSize:filterProductSizes)
            {
                int typeId=Integer.parseInt(filterProductSize.getType());
                int mainType=Integer.parseInt(filterProductSize.getMainType());
                if(typeId==SharedPref.getCheckForFilterSizes())
                {
                    filterProductSizesTemp.add(filterProductSize);
                }
            }
            if(filterProductSizesTemp.size()>0) {
                FilterItem filterItem = FiltersOP.getFilters(Keys.Filter_ALCOHOL);
                if (filterItem != null && filterItem.getSize() != null) {
                    String[] sizes = filterItem.getSize().split("#");
                    for (FilterProductSizes filterProductSize : filterProductSizesTemp) {
                        for (String size : sizes) {
                            if (size.equals(filterProductSize.getNetWeight()) && Integer.parseInt(filterProductSize.getType()) == SharedPref.getCheckForFilterSizes()) {
                                filterProductSizesTemp.get(filterProductSizesTemp.indexOf(filterProductSize)).setCheck(true);
                            }
                        }

                    }
                }
                alcoholFilterSizes = new AlcoholFilterSizes(filterProductSizesTemp, context, SharedPref.getCheckForFilterSizes());
            }
            else
            {
                FilterItem filterItem = FiltersOP.getFilters(Keys.Filter_ALCOHOL);
                if (filterItem != null && filterItem.getSize() != null) {
                    String[] sizes = filterItem.getSize().split("#");
                    for (FilterProductSizes filterProductSize : filterProductSizes) {
                        for (String size : sizes) {
                            if (size.equals(filterProductSize.getNetWeight())) {
                                filterProductSizes.get(filterProductSizes.indexOf(filterProductSize)).setCheck(true);
                            }
                        }

                    }
                }
                alcoholFilterSizes = new AlcoholFilterSizes(filterProductSizes, context, SharedPref.getCheckForFilterSizes());
            }
            rvFilterSize.setNestedScrollingEnabled(false);
            rvFilterSize.setAdapter(alcoholFilterSizes);

            alcoholFilterSizes.setAlcoholFilterSize(new AlcoholFilterSize() {
                @Override
                public void onSizeClick(int position) {
                    alcoholFilterSizes.setRadioButtonStatus(position);
                }
            });
        }
    }

    //setup filerData
    private void setUpData() {
//        String keyValue = "";
//        if (selectedAlcohat == EnumUtils.FilterType.Beer) {
//            keyValue = Keys.Filter_BEER;
//        } else if (selectedAlcohat == EnumUtils.FilterType.Wine || selectedAlcohat == EnumUtils.FilterType.Liqour) {
//            keyValue = Keys.Filter_LIQUOR_WINE;
//        } else {
//            keyValue = Keys.Filter_ALCOHOL;
//        }

        FilterItem item = FiltersOP.getFilters(Keys.Filter_ALCOHOL);
        if (item != null) {

            String country = item.getCountry();
            if (country != null && !country.equalsIgnoreCase("")) {
                String[] countryList = country.split(",");
                for (String cont : countryList) {
                    if (cont.equalsIgnoreCase("USA")) {
                        tv_country_1.performClick();
                    } else if (cont.equalsIgnoreCase("Canada")) {
                        tv_country_2.performClick();
                    } else if (cont.equalsIgnoreCase("Cuba")) {
                        tv_country_3.performClick();
                    } else if (cont.equalsIgnoreCase("Brazil")) {
                        tv_country_4.performClick();
                    } else if (cont.equalsIgnoreCase("Peru")) {
                        tv_country_5.performClick();
                    } else if (cont.equalsIgnoreCase("Columbia")) {
                        tv_country_6.performClick();
                    }
                }
            }

            String price = item.getPrice();
            if (price != null && !price.trim().isEmpty()) {
                if (price.equalsIgnoreCase("0,10")) {
                    tv_price_1.performClick();
                    selectedPrice = "0,10";
                } else if (price.equalsIgnoreCase("10,20")) {
                    tv_price_2.performClick();
                    selectedPrice = "10,20";
                } else if (price.equalsIgnoreCase("20,30")) {
                    tv_price_3.performClick();
                    selectedPrice = "20,30";
                } else if (price.equalsIgnoreCase("30,50")) {
                    tv_price_4.performClick();
                    selectedPrice = "30,50";
                } else if (price.equalsIgnoreCase("50")) {
                    tv_price_5.performClick();
                    selectedPrice = "50";
                }
            }
//            String Size = item.getSize();
//            if (Size != null && !Size.equalsIgnoreCase("")) {
//                String[] SizeList = Size.split(",");
//                for (String sizes : SizeList) {
//                    if (sizes.equalsIgnoreCase("500ml")) {
//                        tv_size_1.performClick();
//
//                    } else if (sizes.equalsIgnoreCase("720ml")) {
//                        tv_size_2.performClick();
//                    } else if (sizes.equalsIgnoreCase("750ml")) {
//                        tv_size_3.performClick();
//                    } else if (sizes.equalsIgnoreCase("1L")) {
//                        tv_size_4.performClick();
//                    } else if (sizes.equalsIgnoreCase("1.5L")) {
//                        tv_size_5.performClick();
//                    } else if (sizes.equalsIgnoreCase("3L")) {
//                        tv_size_6.performClick();
//                    }
//                }
//            }
            int sortBy = item.getSortBy();
            setSortByData(sortBy);

        }//outer if
    }

    private void setSortByData(Integer sortBy) {
        RadioButton radioButton;
        switch (sortBy) {
            case 0:
                radioButton = rbBestSelling;
                break;
            case 2:
                radioButton = rbAtoZ;
                break;
            case 4:
                radioButton = rbZtoA;
                break;
            case 3:
                radioButton = rbPriceHtoL;
                break;
            case 1:
                radioButton = rbPriceLtoH;
                break;
            default:
                radioButton = rbBestSelling;
                break;
        }
        radioButton.performClick();
        tvSelling.setText(radioButton.getText());
    }

    private void setListeners() {
        tvSelling.setOnClickListener(this);
        rbBestSelling.setOnCheckedChangeListener(this);
        rbPriceHtoL.setOnCheckedChangeListener(this);
        rbAtoZ.setOnCheckedChangeListener(this);
        rbPriceLtoH.setOnCheckedChangeListener(this);
        rbZtoA.setOnCheckedChangeListener(this);
        tv_price_1.setOnCheckedChangeListener(this);
        tv_price_2.setOnCheckedChangeListener(this);
        tv_price_3.setOnCheckedChangeListener(this);
        tv_price_4.setOnCheckedChangeListener(this);
        tv_price_5.setOnCheckedChangeListener(this);
    }



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


    @Override
    public void refreshToolbar() {
        Activities.hideBottomNavigation(context, true);
        MenuItemImgOrStr menuItemTick = new MenuItemImgOrStr(R.drawable.tick, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFilter();
                EventBus.getDefault().post("");
                Activities.goBackFragment(context, 1);

            }
        });

        MenuItemImgOrStr menuItemReset = new MenuItemImgOrStr(R.drawable.refreshbtn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Activities.goBackFragment(context, 1);
                clearFilter();
                alcoholFilterSizes.resetSizes();
            }
        });

        String title = EnumUtils.FilterType.getLabel(selectedAlcohat.ordinal(), context);

        ToolbarOp.refresh(getView(), getActivity(), title,
                null, ToolbarOp.Theme.Dark, 0, null, null, menuItemReset, menuItemTick);
    }

    private void clearFilter() {
        FiltersOP.clearFilter(Keys.Filter_ALCOHOL);
        clearDataFromView();
    }

    private void clearDataFromView() {

//        reseting counties
        if (tv_country_1.isChecked()) {
            tv_country_1.performClick();
        }
        if (tv_country_2.isChecked()) {
            tv_country_2.performClick();
        }
        if (tv_country_3.isChecked()) {
            tv_country_3.performClick();
        }
        if (tv_country_4.isChecked()) {
            tv_country_4.performClick();
        }
        if (tv_country_5.isChecked()) {
            tv_country_5.performClick();
        }
        if (tv_country_6.isChecked()) {
            tv_country_6.performClick();
        }
        //reseting sortyBy
        rbBestSelling.performClick();
        tvSelling.setText(rbBestSelling.getText().toString());

        //resting price
        if (tv_price_1.isChecked()) {
            tv_price_1.setChecked(false);
        }
        if (tv_price_2.isChecked()) {
            tv_price_2.setChecked(false);
        }
        if (tv_price_3.isChecked()) {
            tv_price_3.setChecked(false);
        }
        if (tv_price_4.isChecked()) {
            tv_price_4.setChecked(false);
        }
        if (tv_price_5.isChecked()) {
            tv_price_5.setChecked(false);
        }
        selectedPrice = "";
    }

    private Integer getSortByValue() {
        if (rbBestSelling.isChecked()) {
            return 0;
        } else if (rbAtoZ.isChecked()) {
            return 2;
        } else if (rbZtoA.isChecked()) {
            return 4;
        } else if (rbPriceHtoL.isChecked()) {
            return 3;
        } else if (rbPriceLtoH.isChecked()) {
            return 1;
        } else {
            return 0;
        }
    }

    private String getSelectedCountry() {
        String countryName = "";
        if (tv_country_1.isChecked()) {
            countryName = countryName + tv_country_1.getText().toString().trim() + ",";
        }
        if (tv_country_2.isChecked()) {
            countryName = countryName + tv_country_2.getText().toString().trim() + ",";
        }
        if (tv_country_3.isChecked()) {
            countryName = countryName + tv_country_3.getText().toString().trim() + ",";
        }
        if (tv_country_4.isChecked()) {
            countryName = countryName + tv_country_4.getText().toString().trim() + ",";
        }
        if (tv_country_5.isChecked()) {
            countryName = countryName + tv_country_5.getText().toString().trim() + ",";
        }
        if (tv_country_6.isChecked()) {
            countryName = countryName + tv_country_6.getText().toString().trim() + ",";
        }
        if (!countryName.trim().isEmpty()) {
            countryName = countryName.substring(0, countryName.length() - 1);
        }
        return countryName;
    }

    private String getSelectedPrice() {
        if (selectedPrice != null && selectedPrice.trim().isEmpty()) {
            selectedPrice = "";
        }
        return selectedPrice;
    }

    private String getSelectedSize() {
        String selectedSize = "";
        selectedSize = alcoholFilterSizes.getSelectedSize();
        if (!TextUtils.isEmpty(selectedSize)) {
            selectedSize = selectedSize.substring(0, selectedSize.length() - 1);
        }
        return selectedSize;
    }

    private void saveFilter()
    {
        FilterItem item = new FilterItem();
        item.setSortBy(getSortByValue());
        item.setCountry(getSelectedCountry());
        item.setPrice(getSelectedPrice());
        item.setSize(getSelectedSize());
        if ((item.getCountry() == null || item.getCountry().trim().equals("")) &&
                (item.getSize() == null || item.getSize().trim().isEmpty()) &&
                (item.getPrice() == null || item.getPrice().trim().isEmpty()) &&
                (item.getSortBy().equals(0)))

            FiltersOP.addFilter(Keys.Filter_ALCOHOL, null);
        else
            FiltersOP.addFilter(Keys.Filter_ALCOHOL, item);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_selling:
                if (toggleButtonGroupTableLayout.getVisibility() == View.VISIBLE) {
                    toggleButtonGroupTableLayout.setVisibility(View.GONE);
                } else {
                    toggleButtonGroupTableLayout.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id) {
            case R.id.rb_bestselling:
                if (isChecked)
                    tvSelling.setText(rbBestSelling.getText().toString());
                break;
            case R.id.rb_price_ltoh:
                if (isChecked)
                    tvSelling.setText(rbPriceLtoH.getText().toString());
                break;
            case R.id.rb_atoz:
                if (isChecked)
                    tvSelling.setText(rbAtoZ.getText().toString());
                break;
            case R.id.rb_price_htol:
                if (isChecked)
                    tvSelling.setText(rbPriceHtoL.getText().toString());
                break;
            case R.id.rb_ztoa:
                if (isChecked)
                    tvSelling.setText(rbZtoA.getText().toString());
                break;
            case R.id.tv_price_1: {
                if (isChecked)
                    selectedPrice = "0,10";
                break;
            }
            case R.id.tv_price_2: {
                if (isChecked)
                    selectedPrice = "10,20";
                break;
            }
            case R.id.tv_price_3: {
                if (isChecked)
                    selectedPrice = "20,30";
                break;
            }
            case R.id.tv_price_4: {
                if (isChecked)
                    selectedPrice = "30,50";
                break;
            }
            case R.id.tv_price_5: {
                if (isChecked)
                    selectedPrice = "50";
                break;
            }
        }
    }
}//main