package app.com.dunkeydelivery.modules.filter.pager.items;

import android.text.TextUtils;

import app.com.dunkeydelivery.modules.filter.pager.items.CuisineItem;

/**
 * Created by Developer on 1/31/2018.
 */

public class FilterItem {
    public Integer SortBy;
    public Integer Rating;
    public Integer MinDeliveryTime;
    public String PriceRanges;
    public Double MinDeliveryCharges;
    public Boolean IsSpecial;
    public Boolean IsFreeDelivery;
    public Boolean IsNewRestaurants;
    public String Cuisines;
    public Double latitude;
    public Double longitude;

    //Alcohal
    public String getPrice() {
        if(TextUtils.isEmpty(Price))
        {
            return "";
        }
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCountry() {
        if(TextUtils.isEmpty(Country))
        {
            return "";
        }
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getSize() {
        if(TextUtils.isEmpty(Size))
        {
            return "";
        }
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String Price;
    public String Country;
    public String Size;

    public Integer getSortBy() {
        if (SortBy == null)
            return 0;
        return SortBy;
    }

    public void setSortBy(Integer sortBy) {
        SortBy = sortBy;
    }

    public Integer getRating() {
        if (Rating == null)
            return 0;
        return Rating;
    }

    public void setRating(Integer rating) {
        Rating = rating;
    }

    public Integer getMinDeliveryTime() {
        if (MinDeliveryTime == null)
            return 0;
        return MinDeliveryTime;
    }

    public void setMinDeliveryTime(Integer minDeliveryTime) {
        MinDeliveryTime = minDeliveryTime;
    }

    public String getPriceRanges() {
        if (TextUtils.isEmpty(PriceRanges))
            return "";
        return PriceRanges;
    }

    public void setPriceRanges(String priceRanges) {
        PriceRanges = priceRanges;
    }

    public Double getMinDeliveryCharges() {
        if (MinDeliveryCharges == null)
            return 0.0;
        return MinDeliveryCharges;
    }

    public void setMinDeliveryCharges(Double minDeliveryCharges) {
        MinDeliveryCharges = minDeliveryCharges;
    }

    public boolean isSpecial() {
        if (IsSpecial == null)
            return false;
        return IsSpecial;
    }

    public void setSpecial(boolean special) {
        IsSpecial = special;
    }

    public boolean isFreeDelivery() {
        if (IsFreeDelivery == null)
            return false;
        return IsFreeDelivery;
    }

    public void setFreeDelivery(boolean freeDelivery) {
        IsFreeDelivery = freeDelivery;
    }

    public boolean isNewRestaurants() {
        if (IsNewRestaurants == null)
            return false;
        return IsNewRestaurants;
    }

    public void setNewRestaurants(boolean newRestaurants) {
        IsNewRestaurants = newRestaurants;
    }

    public String getCuisines() {
        return Cuisines;
    }

    public void setCuisines(String cuisines) {
        Cuisines = cuisines;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
