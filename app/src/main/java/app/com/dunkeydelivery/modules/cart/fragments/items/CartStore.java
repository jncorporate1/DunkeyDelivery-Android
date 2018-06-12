package app.com.dunkeydelivery.modules.cart.fragments.items;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.items.DeliveryTypes;
import app.com.dunkeydelivery.modules.home.items.ProductBO;

/**
 * Created by Developer on 12/29/2017.
 */

public class CartStore implements Serializable {

    private Integer Type_Id;
    private String OrderDateTime;
    private String openFrom;
    private String openTo;
    private ArrayList<DeliveryTypes> deliveryTypes;

    @SerializedName("storeId")
    int storeId;
    @SerializedName("storeName")
    String storeName;
    @SerializedName("businessType")
    String businessType;
    @SerializedName("minDeliveryTime")
    String minDeliveryTime;
    @SerializedName("minDeliveryCharges")
    int minDeliveryCharges;
    @SerializedName("minOrderPrice")
    String minOrderPrice;

    @SerializedName("StoreTax")
    double storeTax;
    @SerializedName("StoreSubTotal")
    double storeSubTotal;
    @SerializedName("StoreTotal")
    double stoteTotal;

    @SerializedName("products")
    ArrayList<ProductBO> products;

    public Integer getType_Id() {
        if(Type_Id==null)
        {
            return 0;
        }
        return Type_Id;
    }

    public void setType_Id(Integer type_Id) {
        Type_Id = type_Id;
    }

    public String getOrderDateTime() {
        if(OrderDateTime==null)
        {
            return "";
        }
        return OrderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        OrderDateTime = orderDateTime;
    }

    public CartStore(int id, String name, String businessType, int minDeliveryCharges, String minDeliveryTime, String minOrderPrice) {
        this.storeId = id;
        this.storeName = name;
        this.businessType = businessType;
        this.minDeliveryCharges = minDeliveryCharges;
        this.minDeliveryTime = minDeliveryTime;
        this.minOrderPrice = minOrderPrice;
    }

    public CartStore(int id, String name, String businessType, int minDeliveryCharges, String minDeliveryTime, String minOrderPrice, Integer scheduleTypeId, String orderDateTime, String storeOpenFrom, String storeOpenTo, ArrayList<DeliveryTypes> deliveryTypes) {
        this.storeId = id;
        this.storeName = name;
        this.businessType = businessType;
        this.minDeliveryCharges = minDeliveryCharges;
        this.minDeliveryTime = minDeliveryTime;
        this.minOrderPrice = minOrderPrice;
        this.Type_Id=scheduleTypeId;
        this.OrderDateTime=orderDateTime;
        this.openFrom=storeOpenFrom;
        this.openTo=storeOpenTo;
        this.deliveryTypes=deliveryTypes;
    }

    public CartStore() {
    }

    public ArrayList<DeliveryTypes> getDeliveryTypes() {
        return deliveryTypes;
    }

    public void setDeliveryTypes(ArrayList<DeliveryTypes> deliveryTypes) {
        this.deliveryTypes = deliveryTypes;
    }

    public String getOpenFrom() {
        return openFrom;
    }

    public void setOpenFrom(String openFrom) {
        this.openFrom = openFrom;
    }

    public String getOpenTo() {
        return openTo;
    }

    public void setOpenTo(String openTo) {
        this.openTo = openTo;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getMinDeliveryTime() {
        return minDeliveryTime;
    }

    public void setMinDeliveryTime(String minDeliveryTime) {
        this.minDeliveryTime = minDeliveryTime;
    }

    public int getMinDeliveryCharges() {
        return minDeliveryCharges;
    }

    public void setMinDeliveryCharges(int minDeliveryCharges) {
        this.minDeliveryCharges = minDeliveryCharges;
    }

    public String getMinOrderPrice() {
        if(minOrderPrice== null || minOrderPrice.equals(""))
        {
            return "0";
        }
        return minOrderPrice;
    }

    public void setMinOrderPrice(String minOrderPrice) {
        this.minOrderPrice = minOrderPrice;
    }

    public int getId() {
        return storeId;
    }

    public void setId(int id) {
        this.storeId = id;
    }

    public String getName() {
        return storeName;
    }

    public void setName(String name) {
        this.storeName = name;
    }

    public ArrayList<ProductBO> getProducts() {
        if (products != null && products.size() > 0) {
            return products;
        } else {
            products = new ArrayList<>();
            return products;
        }
    }

    public void setProducts(ArrayList<ProductBO> products) {
        this.products = products;
    }

    public String getTotalPrice() {
        int totalPrice = 0;
        if (products == null || products.size() == 0) {
            return Constants.CURRENCY_SYMBOL + "0";
        } else {
            for (int i = 0; i < products.size(); i++) {
                ProductBO productBO = products.get(i);
                if (productBO != null) {
                    int price;
                    if (productBO.getQuantity() > 0)
                        if(productBO.price.contains(".")) {
                            price = productBO.getQuantity() * (int) Double.parseDouble(productBO.price);
                        }
                        else
                        {
                            price = productBO.getQuantity() * Integer.parseInt(productBO.price);
                        }
                    else
                        if(productBO.price.contains(".")) {
                            price = (int)Double.parseDouble(productBO.price);
                        }
                        else
                        {
                            price = Integer.parseInt(productBO.price);
                        }

                    totalPrice += price;
                }

            }
        }
        return Constants.CURRENCY_SYMBOL + totalPrice;
    }

    public int getTotalPriceRaw() {
        int totalPrice = 0;
        if (products == null || products.size() == 0) {
            return 0;
        } else {
            for (int i = 0; i < products.size(); i++)
            {
                ProductBO productBO = products.get(i);
                if(productBO.getId()!=0) {
                    if (productBO != null) {
                        int price;
                        if (productBO.getQuantity() > 0)
                            if(productBO.price.contains(".")) {
                                price = productBO.getQuantity() * (int) Double.parseDouble(productBO.price);
                            }
                            else
                            {
                                price = productBO.getQuantity() * Integer.parseInt(productBO.price);
                            }
                        else {
                            if (productBO.price.contains(".")) {
                                price = (int)Double.parseDouble(productBO.price);
                            } else {
                                price = productBO.getQuantity() * Integer.parseInt(productBO.price);
                            }
                        }

                        totalPrice += price;
                    }
                }

            }
        }
        return totalPrice;
    }

    public double getStoreTax() {
        return storeTax;
    }

    public void setStoreTax(double storeTax) {
        this.storeTax = storeTax;
    }

    public double getStoreSubTotal() {
        return storeSubTotal;
    }

    public void setStoreSubTotal(double storeSubTotal) {
        this.storeSubTotal = storeSubTotal;
    }

    public double getStoteTotal() {
        return stoteTotal;
    }

    public void setStoteTotal(double stoteTotal) {
        this.stoteTotal = stoteTotal;
    }

}
