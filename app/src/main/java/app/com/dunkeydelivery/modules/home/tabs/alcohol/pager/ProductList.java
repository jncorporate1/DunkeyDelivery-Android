package app.com.dunkeydelivery.modules.home.tabs.alcohol.pager;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.common.StringUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.filter.pager.items.FilterItem;
import app.com.dunkeydelivery.modules.home.items.ProductBO;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.adapters.AlcoholProductsAdapter;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.fragments.ProductDetail;
import app.com.dunkeydelivery.modules.home.items.SubCategoryBO;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.items.AlcoholSeeAllBO;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.items.Categories;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.FiltersOP;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.PixelsOp;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProductList extends ToolbarFragment {

    private Context context;
    public Stack<String> alcoholSeeAllStack;
    Unbinder unbinder;
    private Boolean checkIsLastProduct;
    private String lastCategoryName;
    private ArrayList<Categories> categoriesList;
    private ArrayList<ProductBO> lastProductsArray;
    private String alcoholType;
    private String categoryId;
    private static String ARG_PARAM1 = "alcoholCategories";
    private static String ARG_PARAM2 = "alcoholType";
    private static String ARG_PARAM3 = "lastProductsList";

    @BindView(R.id.ll_content)
    LinearLayout llContent;

    @BindView(R.id.ll_see_all)
    LinearLayout llSeeAll;

    @BindView(R.id.sv_category)
    ScrollView svCategory;

    @BindView(R.id.ll_relativelayout)
    RelativeLayout llRelativeLayout;

    public static ProductList newInstance(ArrayList<Categories> categories, String alcoholType, ArrayList<ProductBO> lastProducts) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, categories);
        args.putString(ARG_PARAM2, alcoholType);
        args.putParcelableArrayList(ARG_PARAM3, lastProducts);
        ProductList fragment = new ProductList();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alohol_list,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize all views

        alcoholSeeAllStack = new Stack<String>();

        if (getArguments() != null) {
            if (getArguments().getParcelableArrayList(ARG_PARAM1) != null && getArguments().getString(ARG_PARAM2) != null) {
                this.categoriesList = getArguments().getParcelableArrayList(ARG_PARAM1);
                this.alcoholType = getArguments().getString(ARG_PARAM2);
                this.lastProductsArray = getArguments().getParcelableArrayList(ARG_PARAM3);
            }
        }
        if (categoriesList != null && categoriesList.size() > 0) {
            setUpCategories(categoriesList);
        } else if (lastProductsArray != null && lastProductsArray.size() > 0) {
            lastCategoryName = ((AllProductsViewPager) getParentFragment()).getCategoryName();
            setUpSeeAllSection(categoriesList, lastProductsArray);
        } else {
            svCategory.setVisibility(View.GONE);
            llRelativeLayout.setVisibility(View.VISIBLE);
        }
    }

    //check seeAll layout visiblity
    public boolean isProductsShown() {
        if (llSeeAll.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }

    //popup previous category id and pass to api call
    public void showCategories() {
        try {

            if (checkIsLastProduct == true && !alcoholSeeAllStack.isEmpty()) {
//                categoryId = alcoholSeeAllStack.pop();
                categoryId = alcoholSeeAllStack.pop();
                callGetAlcoholCategorySubcategoryApi(Integer.parseInt(categoryId.split(",")[1])
                        , Integer.parseInt(categoryId.split(",")[0]));
            } else if (!alcoholSeeAllStack.isEmpty()) {
                categoryId = alcoholSeeAllStack.pop();
                callGetAlcoholCategorySubcategoryApi(Integer.parseInt(categoryId.split(",")[1])
                        , Integer.parseInt(categoryId.split(",")[0]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //setup alcohol sub categories for one time
    private void setUpCategories(final ArrayList<Categories> categoriesList) {
//        alcoholSeeAllStack.push(((AllProductsViewPager) getParentFragment()).getParentCategoryId()
//                + "," + ((AllProductsViewPager) getParentFragment()).getCategoryId());
        for (final Categories categories : categoriesList) {
            try {
                if (categories.getProducts() != null) {
                    if (categories.getProducts().size() > 0) {
                        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_alcohol_content, null, false);

                        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
                        final TextView tvSeeAll = (TextView) contentView.findViewById(R.id.tv_see_all);

                        tvSeeAll.setTag(categories);

                        tvSeeAll.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Categories categoryBO = (Categories) v.getTag();
                                lastCategoryName = categoryBO.getName();
                                alcoholSeeAllStack.push(categories.getParentCategoryId() + "," + categories.getId());
                                categoryId = categories.getParentCategoryId() + "," + categories.getId();
                                callGetAlcoholCategorySubcategoryApi(categories.getParentCategoryId(), categories.getId());
                                ((AllProductsViewPager) getParentFragment()).getSwipeRefresh1().setEnabled(false);
                            }
                        });
                        tvTitle.setText(categories.getName());
                        //setup products...
                        setUpProducts(categories.getProducts(), contentView, false);

                        llContent.addView(contentView);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //setup alcohol sub categories for when click on seeAll
    private void setUpSeeAllSection(final ArrayList<Categories> categoryBO, ArrayList<ProductBO> lastCategoryProducts) {
        llSeeAll.removeAllViews();
        llSeeAll.setVisibility(View.VISIBLE);
        Boolean isGrid = false;
        if (categoryBO.size() == 0 && lastCategoryProducts != null && lastCategoryProducts.size() > 0) {
            isGrid = true;
            llContent.setVisibility(View.GONE);
            View contentView = LayoutInflater.from(context).inflate(R.layout.layout_alcohol_content, null, false);

            TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
            final TextView tvSeeAll = (TextView) contentView.findViewById(R.id.tv_see_all);

            tvSeeAll.setVisibility(View.GONE);
            /*if(!TextUtils.isEmpty(lastCategoryName)) {
                String arr[] = lastCategoryName.split(" ");
                String capitalizedString = "";
                for (String word : arr) {
                    String firstLetter = word.substring(0, 1).toUpperCase();
                    capitalizedString = capitalizedString + firstLetter + word.substring(1) + " ";
                }*/
            tvTitle.setText("All " + alcoholType);

            //setup products...
            setUpProducts(lastCategoryProducts, contentView, isGrid);

            if (alcoholSeeAllStack.isEmpty()) {
                llContent.setVisibility(View.VISIBLE);
                llContent.addView(contentView);
                llSeeAll.setVisibility(View.GONE);
            } else {
                llContent.setVisibility(View.GONE);
                llSeeAll.addView(contentView);
                llSeeAll.setVisibility(View.VISIBLE);
            }
            //}
        }
        for (final Categories categories : categoryBO) {
            try {
                View contentView = LayoutInflater.from(context).inflate(R.layout.layout_alcohol_content, null, false);

                TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
                final TextView tvSeeAll = (TextView) contentView.findViewById(R.id.tv_see_all);

                tvSeeAll.setTag(categories);
                tvSeeAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Categories categoriesBO = (Categories) v.getTag();
                        lastCategoryName = categories.getName();
                        alcoholSeeAllStack.push(categories.getParentCategoryId() + "," + categories.getId());
                        categoryId = categories.getParentCategoryId() + "," + categories.getId();
                        callGetAlcoholCategorySubcategoryApi(categories.getParentCategoryId(), categories.getId());
                    }
                });
                lastCategoryName = categories.getName();
                tvTitle.setText(categories.getName());

                //setup products...
                setUpProducts(categories.getProducts(), contentView, isGrid);

                llContent.setVisibility(View.GONE);
                llSeeAll.addView(contentView);
                llSeeAll.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            if (alcoholSeeAllStack.isEmpty()) {
                llSeeAll.setVisibility(View.GONE);
                llContent.setVisibility(View.VISIBLE);
                ((AllProductsViewPager) getParentFragment()).getSwipeRefresh1().setEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //setup products for alcohol categories
    private void setUpProducts(List<ProductBO> products, View contentView, boolean isGrid) {
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);

        if (isGrid) {
            recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        } else {
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (isGrid) {
            recyclerView.addItemDecoration(new GridSpacesItemDecoration(PixelsOp.convertToDensity(getContext(), 5), true));
        }

        final AlcoholProductsAdapter mAdapter = new AlcoholProductsAdapter(products, context, isGrid);

        mAdapter.setClickListener(new AlcoholProductsAdapter.ClickListeners() {
            @Override
            public void onRowClick(int position) {
                Activities.gotoNextFragment(context, ProductDetail.newInstance(mAdapter.getItem(position), false));
            }
        });

        recyclerView.setAdapter(mAdapter);
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
    public void refreshToolbar() {
        if (!TextUtils.isEmpty(categoryId)) {
            callGetAlcoholCategorySubcategoryApi(Integer.parseInt(categoryId.split(",")[1])
                    ,
                    Integer.parseInt(categoryId.split(",")[0]));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        categoryId = ((AllProductsViewPager) getParentFragment()).getParentCategoryId()
                + "," + ((AllProductsViewPager) getParentFragment()).getCategoryId();
    }

    //setup gridView for products when alcohol sub category have no further categories
    public class GridSpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final boolean includeEdge;
        private int spacing;


        public GridSpacesItemDecoration(int spacing, boolean includeEdge) {
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getLayoutManager() instanceof GridLayoutManager) {
                GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
                int spanCount = layoutManager.getSpanCount();
                int position = parent.getChildAdapterPosition(view); // item position
                int column = position % spanCount; // item column

                if (includeEdge) {
                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing; // item bottom
                } else {
                    outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                    outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                    if (position >= spanCount) {
                        outRect.top = spacing; // item top
                    }
                }

            }

        }
    }

    //call api to get alcohol sub categories with and without filter and get products if no more sub categories available
    private void callGetAlcoholCategorySubcategoryApi(int parentCategoryId, int categoryId) {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().accessToken);
        FilterItem filterItem = FiltersOP.getFilters(Keys.Filter_ALCOHOL);
        EnumUtils.ServiceName serviceName = null;
        if (filterItem != null) {
            serviceName = EnumUtils.ServiceName.AlcoholFilterTypeStoreCategoryDetails;
            serviceParams.put(Keys.SortBy, filterItem.getSortBy());
            serviceParams.put(Keys.Country, filterItem.getCountry());
            serviceParams.put(Keys.Price, filterItem.getPrice());
            serviceParams.put(Keys.Size, filterItem.getSize());
            serviceParams.put(Keys.Type_Id, categoryId);
            serviceParams.put(Keys.ParentName, alcoholType);
        } else {
            serviceName = EnumUtils.ServiceName.AlcoholStoreCategoryDetails;
            serviceParams.put(Keys.Store_id, ((AllProductsViewPager) getParentFragment()).getStoreId());
            serviceParams.put(Keys.Category_id, parentCategoryId);
            serviceParams.put(Keys.CategoryName, alcoholType);
            serviceParams.put(Keys.Category_ParentId, categoryId);
        }
        String num = serviceParams.toString();
        new WebServicesVolleyTask(context, true, "Loading...",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);

                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        if (taskItem.getResponse() != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                Gson gson = new Gson();
                                AlcoholSeeAllBO alcoholSeeAllBO = gson.fromJson(jsonObject.getString("Categories"), AlcoholSeeAllBO.class);
                                ArrayList<ProductBO> lastCategoryProducts = null;
                                checkIsLastProduct = jsonObject.getBoolean("IsLast");
                                if (jsonObject.getBoolean("IsLast") || !jsonObject.getBoolean("IsLast")) {
                                    Type typeToken = new TypeToken<ArrayList<ProductBO>>() {
                                    }.getType();
                                    lastCategoryProducts = gson.fromJson(jsonObject.getString("Products").toString(), typeToken);
                                    if (alcoholType.equals("Wine")) {
                                        setUpSeeAllSection(alcoholSeeAllBO.getWineSeeAll(), lastCategoryProducts);
                                    } else if (alcoholType.equals("Liquor")) {
                                        setUpSeeAllSection(alcoholSeeAllBO.getLiquorSeeAll(), lastCategoryProducts);
                                    } else if (alcoholType.equals("Beer")) {
                                        setUpSeeAllSection(alcoholSeeAllBO.getBeerSeeAll(), lastCategoryProducts);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }

}