package app.com.dunkeydelivery.modules.home.tabs.alcohol.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kogitune.activity_transition.fragment.ExitFragmentTransition;
import com.kogitune.activity_transition.fragment.FragmentTransition;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.adapters.AlcoholSizes;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.interfaces.ProductSizeSelection;
import app.com.dunkeydelivery.items.DeliveryTypes;
import app.com.dunkeydelivery.items.ItemBO;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.cart.fragments.CartMain;
import app.com.dunkeydelivery.modules.deals.items.DealsItem;
import app.com.dunkeydelivery.modules.deals.items.PackageProducts;
import app.com.dunkeydelivery.modules.home.items.ProductBO;
import app.com.dunkeydelivery.modules.home.items.ProductSizes;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.items.Products;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.CartOP;
import app.com.dunkeydelivery.utils.DateTimeOp;
import app.com.dunkeydelivery.utils.DialogFragmentSheduling;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.SnackBarUtil;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomRadioButton;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import app.com.dunkeydelivery.utils.sharedprefs.SharedPref;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.MenuItemImgOrStr;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import worker8.com.github.radiogroupplus.RadioGroupPlus;

public class ProductDetail extends ToolbarFragment implements View.OnClickListener, ProductSizeSelection {
    public static final String ARG_PARAM2 = "dealsItem";
    private String sheduleTime;
    private Integer scheduleTypeID;
    private ArrayList<DeliveryTypes> deliveryTypes;
    private String openFrom;
    private String openTo;
    private ArrayList<PackageProducts> packageProducts;
    private Context context;
    Unbinder unbinder;
    private AlcoholSizes adapter;
    private Integer quantity = 0;

    public static String ARG_PARAM1 = "productBO";
    public static String ARG_PARAM3 = "animationHandle";

    ProductBO productBO;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_detail)
    TextView tvDetail;

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.et_instructions)
    EditText etInstructions;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @BindView(R.id.tv_price)
    TextView tv_price;

    @BindView(R.id.tv_detail_1)
    TextView tvDetail1;

    @BindView(R.id.tv_detail_3)
    TextView tvDetail3;

    @BindView(R.id.tvDetail2)
    TextView tvDetail2;


    @BindView(R.id.btn_increment)
    ImageButton btnIncrement;

    @BindView(R.id.btn_decrement)
    ImageButton btnDecrement;

    @BindView(R.id.tv_display_count)
    TextView tvCount;

    @BindView(R.id.rgp_bottlesize)
    RecyclerView rgpBottleSize;

    @BindView(R.id.btn_add)
    Button btnAdd;

    @BindView(R.id.llProductSizes)
    LinearLayout llProductSizes;

    @BindView(R.id.llProductSizesLine)
    View llProductSizesLine;

    @BindView(R.id.storeNameLine)
    View storeNameLine;

    @BindView(R.id.productsInOfferLine)
    View productsInOfferLine;

    @BindView(R.id.ll_storeNameLayout)
    LinearLayout llStoreNameLayout;

    @BindView(R.id.ll_productsInOfferLayout)
    LinearLayout ll_productsInOfferLayout;

    @BindView(R.id.tv_storename)
    TextView tvStoreName;

    @BindView(R.id.ll_addmoreproducts)
    LinearLayout llAddmoreproducts;

    @BindView(R.id.tv_see)
    TextView tvSee;

    @BindView(R.id.tv_less)
    TextView tvLess;

    @BindView(R.id.llPriceLine)
    LinearLayout llPriceLine;

    @BindView(R.id.tvSeeLessProductSizes)
    TextView tvSeeLessProductSizes;

    @BindView(R.id.tvSeeMoreProductSizes)
    TextView tvSeeMoreProductSizes;

    int themeColor;
    String selectedPrice;
    private DealsItem dealsItem;

    String hoursFrom[];

    Boolean checkAnamiation;

    String hoursTo[];
    public ProductSizes selectedAlcohalSize;

    public static ProductDetail newInstance(ProductBO productBO, Boolean animationHandle) {
        Bundle args = new Bundle();
        if (productBO != null) {
            args.putParcelable(ARG_PARAM1, productBO);
            args.putBoolean(ARG_PARAM3, animationHandle);
        }
        ProductDetail fragment = new ProductDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beer_product_detail,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkAnamiation = false;
        if (getArguments() != null) {
            if (getArguments().getParcelable(ARG_PARAM1) != null) {
                productBO = getArguments().getParcelable(ARG_PARAM1);
                dealsItem = null;
                callGetStoreSchedulingTypesApi(productBO.getStoreId());
            } else if (getArguments().getParcelable(ARG_PARAM2) != null) {
                dealsItem = getArguments().getParcelable(ARG_PARAM2);
                productBO = null;
            }
            if (getArguments().getBoolean(ARG_PARAM3) == true) {
                checkAnamiation = getArguments().getBoolean(ARG_PARAM3);
            }
        }

        themeColor = (ContextCompat.getColor(context, R.color.colorPrimary));

        setListners();
        setProductData();

        if (checkAnamiation == true) {
            setImageTransitionAnimation(view, savedInstanceState);
        }

    }//onViewCreated

    //setup image transition
    private void setImageTransitionAnimation(View rootView, Bundle savedInstanceState) {
        try {
            final ExitFragmentTransition exitFragmentTransition
                    = FragmentTransition
                    .with(this)
                    .to(rootView.findViewById(R.id.imageView))
                    .start(savedInstanceState);
            exitFragmentTransition.startExitListening();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //setup listeners
    private void setListners() {
        btnIncrement.setOnClickListener(this);
        btnDecrement.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        tvSee.setOnClickListener(this);
        tvLess.setOnClickListener(this);
        tvSeeMoreProductSizes.setOnClickListener(this);
        tvSeeLessProductSizes.setOnClickListener(this);
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

        //TODO; in this we have to add category title
        if (productBO != null) {
            ToolbarOp.refresh(getView(), getActivity(), productBO.getName(),
                    null, ToolbarOp.Theme.Dark, 0, null, menuItemImgOrStr);
        } else if (dealsItem != null) {

            ToolbarOp.refresh(getView(), getActivity(), dealsItem.getOffer().getName(),
                    null, ToolbarOp.Theme.Dark, 0, null, menuItemImgOrStr);
        } else {
            ToolbarOp.refresh(getView(), getActivity(), getResources().getString(R.string.default_product_name),
                    null, ToolbarOp.Theme.Dark, 0, null, menuItemImgOrStr);
        }
    }

    //setup product details
    private void setProductData() {
        if (productBO != null) {
            tvDetail.setText(productBO.getName());
            tvDetail1.setText(productBO.getDescription());
            ImageUtils.setCenterImage(productBO.getImage(), imageView, context);
            tvTotal.setText(getTotalPrice(quantity, productBO.price));
            tv_price.setText(productBO.getPrice());
            if (productBO.getCheck() == 1) {
                tvDetail2.setVisibility(View.GONE);
                tvDetail3.setVisibility(View.GONE);
            } else {
                tvDetail3.setText(productBO.getAddress());
            }
            if (productBO.getProductSizes() != null && productBO.getProductSizes().size() > 0) {
                llPriceLine.setVisibility(View.GONE);
                llProductSizes.setVisibility(View.VISIBLE);
                rgpBottleSize.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                rgpBottleSize.setNestedScrollingEnabled(false);
                selectedAlcohalSize=productBO.getProductSizes().get(0);
                selectedPrice=selectedAlcohalSize.getPrice();
                adapter = new AlcoholSizes(context, productBO.getProductSizes());
                adapter.setProductSizeSelection(new ProductSizeSelection() {
                    @Override
                    public void onRowClick(int position) {
                        selectedAlcohalSize = productBO.getProductSizes().get(position);
                        if(!TextUtils.isEmpty(selectedAlcohalSize.getPrice())) {
                            if (selectedPrice != selectedAlcohalSize.getPrice()) {
                                tvCount.setText("0");
                                tvTotal.setText(Constants.CURRENCY_SYMBOL + "0.0");
                                selectedPrice = selectedAlcohalSize.getPrice();
                                quantity = 0;
                            }
                        }
                    }
                });
                setUpAlcohalSizes(productBO.getProductSizes());
                rgpBottleSize.setAdapter(adapter);
            } else {
                llPriceLine.setVisibility(View.VISIBLE);
            }
            storeNameLine.setVisibility(View.GONE);
            productsInOfferLine.setVisibility(View.GONE);
            llStoreNameLayout.setVisibility(View.GONE);
            ll_productsInOfferLayout.setVisibility(View.GONE);

        } else if (dealsItem != null) {
            if(checkAnamiation==false)
            {
                ImageUtils.setCenterImage(dealsItem.getOffer().getImageUrl(), imageView, context);
            }
            packageProducts = dealsItem.getPackage().getPackageProducts();
            tvDetail2.setVisibility(View.GONE);
            tvDetail3.setVisibility(View.GONE);
            tvDetail.setText(dealsItem.getOffer().getName());
            tvDetail1.setText(dealsItem.getOffer().getDescription());
            tvStoreName.setText(dealsItem.getOffer().getStore().getBusinessName());
            removeProducts();
            if (dealsItem.getPackage().getPackageProducts().size() <= 4) {
                showMoreProducts();
                tvSee.setVisibility(View.GONE);
            } else {
                showLessProducts();
                tvSee.setVisibility(View.VISIBLE);
            }
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            tv_price.setText(dealsItem.getPrice());

        } else {
            tvDetail.setText("");
            tvDetail1.setText("");
            ImageUtils.setCenterImage("", imageView, context);
            tvTotal.setText("0");
        }
    }

    //remove all products in layout
    private void removeProducts() {
        llAddmoreproducts.removeAllViews();
    }

    //remove all products sizes in layout
    private void removeProductSizes() {
        rgpBottleSize.removeAllViews();
    }

    //setup alcohol sizes
    private void setUpAlcohalSizes(ArrayList<ProductSizes> productSizes) {
        if (productSizes.size() < 4) {
            adapter.seeMore(true);
            tvSeeMoreProductSizes.setVisibility(View.GONE);
        } else {
            adapter.seeMore(false);
            tvSeeMoreProductSizes.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_increment:
                if (quantity < Constants.MAX_ITEMS_TO_LOAD) {
                    quantity = quantity + 1;
                    tvCount.setText(quantity + "");
                    tvTotal.setText(Constants.CURRENCY_SYMBOL + quantity);
                    tvCount.setTextColor(themeColor);
                    btnIncrement.setColorFilter(themeColor, PorterDuff.Mode.SRC_IN);
                }
                if (productBO != null) {
                    if(!TextUtils.isEmpty(selectedPrice)) {
                        productBO.price=getTotalPrice(quantity, selectedPrice).substring(1);
                        tvTotal.setText(getTotalPrice(quantity, selectedPrice));
                    }
                    else
                    {
                        tvTotal.setText(getTotalPrice(quantity, productBO.price));
                    }
                } else if (dealsItem != null) {
                    tvTotal.setText(getTotalPrice(quantity, dealsItem.getPrice()));
                }

                break;
            case R.id.btn_decrement:
                if (quantity > 0) {
                    quantity = quantity - 1;
                    tvCount.setText(quantity + "");
                    tvTotal.setText(Constants.CURRENCY_SYMBOL + quantity);
                }
                if (quantity == 0) {
                    tvCount.setTextColor(ContextCompat.getColor(context, R.color.grey));
                    btnIncrement.clearColorFilter();
                }
                if (productBO != null) {
                    if(!TextUtils.isEmpty(selectedPrice)) {
                        tvTotal.setText(getTotalPrice(quantity, selectedPrice));
                    }
                    else
                    {
                        tvTotal.setText(getTotalPrice(quantity, productBO.price));
                    }
                } else if (dealsItem != null) {
                    tvTotal.setText(getTotalPrice(quantity, dealsItem.getPrice()));
                }

                break;
            case R.id.btn_add:
                checkQuantity();
                break;
            case R.id.tv_see:
                showMoreProducts();
                tvSee.setVisibility(View.GONE);
                tvLess.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_less:
                showLessProducts();
                tvSee.setVisibility(View.VISIBLE);
                tvLess.setVisibility(View.GONE);
                break;
            case R.id.tvSeeMoreProductSizes:
                adapter.seeMore(true);
                tvSeeMoreProductSizes.setVisibility(View.GONE);
                tvSeeLessProductSizes.setVisibility(View.VISIBLE);
                break;
            case R.id.tvSeeLessProductSizes:
                adapter.seeMore(false);
                tvSeeMoreProductSizes.setVisibility(View.VISIBLE);
                tvSeeLessProductSizes.setVisibility(View.GONE);
                break;
        }
    }

    //check selected time is between store hours
    private Boolean checkTimeInBetweenStoreHours(String selectedTime) {
        Boolean check = false;
        try {
            String timeFrom = hoursFrom[0] + ":" + hoursFrom[1];
            Date formattedTimeFrom = new SimpleDateFormat(Constants.dateFormat30).parse(timeFrom);
            Calendar calendarTimeFrom = Calendar.getInstance();
            calendarTimeFrom.setTime(formattedTimeFrom);

            String timeTo = hoursTo[0] + ":" + hoursTo[1];
            Date formattedTimeTo = new SimpleDateFormat(Constants.dateFormat30).parse(timeTo);
            Calendar calendarTimeTo = Calendar.getInstance();
            calendarTimeTo.setTime(formattedTimeTo);
            calendarTimeTo.add(Calendar.DATE, 1);

            String userSelectedTime = selectedTime;
            Date formattedTimeSelected = new SimpleDateFormat(Constants.dateFormat30).parse(userSelectedTime);
            Calendar calendarTimeSelected = Calendar.getInstance();
            calendarTimeSelected.setTime(formattedTimeSelected);
            calendarTimeSelected.add(Calendar.DATE, 1);

            Date x = calendarTimeSelected.getTime();
            if (x.after(calendarTimeFrom.getTime()) && x.before(calendarTimeTo.getTime())) {
                check = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return check;
    }

    //show dialog to shedule order
    private void showDialogue() {
        try {
            Boolean check = false;
            if (CartOP.getStorePosition(productBO.getStoreId()) == -1) {
                check = false;
            } else {
                check = true;
            }

            if (!check && deliveryTypes!=null && deliveryTypes.size()>0)
            {
                try {
                    DialogFragmentSheduling dialogFragmentSheduling = DialogFragmentSheduling.newInstance(
                            deliveryTypes,hoursFrom,hoursTo
                    );
                    dialogFragmentSheduling.setLisenter(new DialogFragmentSheduling.SetDialogDismissListener() {
                        @Override
                        public void setASAPListener(String scheduleTypeId) {
                            sheduleTime = getScheduleForASAP(productBO);

                            if (!TextUtils.isEmpty(scheduleTypeId)) {
                                scheduleTypeID = Integer.parseInt(scheduleTypeId);
                            } else {
                                scheduleTypeID = null;
                            }
                            goToCartMain();
                        }

                        @Override
                        public void setTodayListener(String selectedTime, String scheduleTypeId) {
                            if (checkTimeInBetweenStoreHours(selectedTime) == true) {
                                if (!TextUtils.isEmpty(scheduleTypeId)) {
                                    scheduleTypeID = Integer.parseInt(scheduleTypeId);
                                } else {
                                    scheduleTypeID = null;
                                }
                                sheduleTime = DateTimeOp.getCurrentDateTime(Constants.dateFormat16) + " " + selectedTime;
                                goToCartMain();
                            } else {
                                SnackBarUtil.showSnackbar(context, getResources().getString(R.string.store_hours_time), true);
                            }
                        }

                        @Override
                        public void setLaterListener(String selectedDate, String selectedTime, String scheduleTypeId) {
                            if (checkTimeInBetweenStoreHours(selectedTime) == true) {
                                if (!TextUtils.isEmpty(scheduleTypeId)) {
                                    scheduleTypeID = Integer.parseInt(scheduleTypeId);
                                } else {
                                    scheduleTypeID = null;
                                }
                                sheduleTime = selectedDate + " " + selectedTime;
                                goToCartMain();
                            } else {
                                SnackBarUtil.showSnackbar(context, getResources().getString(R.string.store_hours_time), true);
                            }
                        }
                    });
                    dialogFragmentSheduling.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                    dialogFragmentSheduling.setCancelable(false);
                    dialogFragmentSheduling.show(getActivity().getFragmentManager(), "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    if(!check && deliveryTypes.size()==0)
                    {
                        sheduleTime = getScheduleForASAP(productBO);
                        scheduleTypeID=0;
                        goToCartMain();
                    }
                    else {
                        goToCartMainWithoutSchedule();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //goto cartmain if store already added to cart
    private void goToCartMainWithoutSchedule() {
        if (productBO != null) {
            productBO.setQuantity(quantity);
            productBO.setAdditionNote(etInstructions.getText().toString().trim());
            CartOP.addITem(productBO);
            if(selectedAlcohalSize!=null) {
                SharedPref.saveProductSize(selectedAlcohalSize.getSize() + selectedAlcohalSize.getUnit());
            }
            else
            {
                SharedPref.saveProductSize("");
            }
        } else if (dealsItem != null) {
            SharedPref.saveProductSize("");
            ProductBO productBO = setProductModel();
            CartOP.addITem(productBO, 0, getScheduleForASAP(productBO), " ", " ", null);
        }
        Activities.removeAllFragments(context);
        Activities.gotoNextFragment(context, CartMain.newInstance());
    }

    //setup productBO and goto cart main
    private void goToCartMain() {
        if (productBO != null) {
            productBO.setQuantity(quantity);
            productBO.setAdditionNote(etInstructions.getText().toString().trim());
            CartOP.addITem(productBO, scheduleTypeID, sheduleTime, openFrom, openTo, deliveryTypes);
            if(selectedAlcohalSize!=null) {
                SharedPref.saveProductSize(selectedAlcohalSize.getSize() + selectedAlcohalSize.getUnit());
            }
            else {
                SharedPref.saveProductSize("");
            }
        } else if (dealsItem != null) {
            ProductBO productBO = setProductModel();
            CartOP.addITem(productBO, 0, getScheduleForASAP(productBO), " ", " ", null);
            SharedPref.saveProductSize("");
        }
        Activities.removeAllFragments(context);
        Activities.gotoNextFragment(context, CartMain.newInstance());
    }

    //setup shedule for ASAP
    private String getScheduleForASAP(ProductBO productBO) {
        String time = DateTimeOp.oneFormatToAnotherInUTC(Integer.parseInt(productBO.getMinDeliveryTime()), Constants.dateFormat12, Constants.dateFormat30);
        if (!TextUtils.isEmpty(time)) {
            String arr[] = time.split(" ");
            sheduleTime = DateTimeOp.getCurrentDateTime(Constants.dateFormat16) + " " + arr[0];
        }
        return sheduleTime;
    }

    //check product quantity
    private void checkQuantity() {
        if (quantity == 0) {
            SnackBarUtil.showSnackbar(context, context.getString(R.string.no_quantity_selected), true);
        } else {
            if (productBO != null) {
                showDialogue();
            } else {
                goToCartMain();
            }
        }
    }

    //setup product model to save in cartOP
    private ProductBO setProductModel() {
        try {
            ProductBO productBO = new ProductBO();
            productBO.setId(Integer.parseInt(dealsItem.getId()));
            productBO.setName(dealsItem.getOffer().getName());
            productBO.setDescription(dealsItem.getOffer().getDescription());
            productBO.setPrice(dealsItem.getPrice());
            productBO.setImage(dealsItem.getOffer().getImageUrl());
            productBO.setBusinessType(dealsItem.getOffer().getStore().getBusinessType());
            productBO.setStoreId(Integer.valueOf(dealsItem.getOffer().getStore_Id()));
            productBO.setStoreName(dealsItem.getOffer().getStore().getBusinessName());
            productBO.setMinDeliveryCharges(Integer.valueOf(dealsItem.getOffer().getStore().getMinDeliveryCharges()));
            productBO.setMinDeliveryTime(dealsItem.getOffer().getStore().getMinDeliveryTimeWithoutMin());
            productBO.setMinOrderPrice(dealsItem.getOffer().getStore().minOrderPrice);
            productBO.setAddress(dealsItem.getOffer().getStore().getAddress());
            productBO.setItemType(3);
            productBO.setQuantity(quantity);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return productBO;
    }

    //show less products
    private void showLessProducts() {
        removeProducts();
        int count = 0;
        for (PackageProducts packageProducts : packageProducts) {
            try {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View productNameAndQuantity = layoutInflater.inflate(R.layout.layout_products_in_deals, null);
                ((TextView) productNameAndQuantity.findViewById(R.id.tv_productname)).setText(packageProducts.getProduct().getName());
                ((TextView) productNameAndQuantity.findViewById(R.id.tv_quantity)).setText(packageProducts.getQty());
                llAddmoreproducts.addView(productNameAndQuantity);
                ++count;
                if (count == 4) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //show all products
    private void showMoreProducts() {
        removeProducts();
        for (PackageProducts packageProducts : packageProducts) {
            try {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View productNameAndQuantity = layoutInflater.inflate(R.layout.layout_products_in_deals, null);
                ((TextView) productNameAndQuantity.findViewById(R.id.tv_productname)).setText(packageProducts.getProduct().getName());
                ((TextView) productNameAndQuantity.findViewById(R.id.tv_quantity)).setText(packageProducts.getQty());
                llAddmoreproducts.addView(productNameAndQuantity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //show less product sizes
    private void showLessProductSizes(ArrayList<ProductSizes> productSizes, String price) {
        removeProductSizes();
        int count = 0;

        for (final ProductSizes productSize : productSizes) {
            try {
                View sizeView = LayoutInflater.from(context).inflate(R.layout.layout_price, null);
                TextView tv_price = sizeView.findViewById(R.id.tv_price);
                RadioButton customRadioButton = sizeView.findViewById(R.id.customRadioButton);
                tv_price.setText(price);
                customRadioButton.setText(productSize.getSize() + " " + productSize.getUnit());
                rgpBottleSize.addView(sizeView);
                ++count;
                if (count == 4) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //show all product sizes
    private void showMoreProductSizes(ArrayList<ProductSizes> productSizes, String price) {
        removeProductSizes();

        for (final ProductSizes productSize : productSizes) {
            try {
                View sizeView = LayoutInflater.from(context).inflate(R.layout.layout_price, null);
                TextView tv_price = sizeView.findViewById(R.id.tv_price);
                final RadioButton customRadioButton = sizeView.findViewById(R.id.customRadioButton);
                tv_price.setText(price);
                customRadioButton.setText(productSize.getSize() + " " + productSize.getUnit());
                rgpBottleSize.addView(sizeView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //calculate total price according to quantity
    private String getTotalPrice(int quantity, String price) {
        return Constants.CURRENCY_SYMBOL + (quantity * Double.parseDouble(price));
    }

    //set dealItem and set animation check
    public static ProductDetail newInstance(DealsItem item, Boolean checkAnamiation) {
        Bundle args = new Bundle();
        if (item != null) {
            args.putParcelable(ARG_PARAM2, item);
            args.putBoolean(ARG_PARAM3, checkAnamiation);
        }
        ProductDetail fragment = new ProductDetail();
        fragment.setArguments(args);
        return fragment;
    }

    //get store sheduling types api call
    private void callGetStoreSchedulingTypesApi(Integer storeId) {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.TOKEN, UserSharedPreference.readUserToken().accessToken);

        serviceParams.put(Keys.Store_id, storeId);

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.GetStoreSchedule;

        new WebServicesVolleyTask(context, true, "",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);

                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {
                            if (taskItem.getResponse() != null) {
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                JSONObject store = jsonObject.getJSONObject("Store");
                                openFrom = store.getString("Open_From");
                                openTo = store.getString("Open_To");
                                hoursFrom = openFrom.trim().split(":");
                                hoursTo = openTo.trim().split(":");
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<DeliveryTypes>>() {
                                }.getType();
                                deliveryTypes = gson.fromJson(store.getJSONArray("StoreDeliveryTypes").toString(), type);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }

    @Override
    public void onRowClick(int position) {

    }
}
