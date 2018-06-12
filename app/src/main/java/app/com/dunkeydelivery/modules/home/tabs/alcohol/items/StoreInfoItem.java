package app.com.dunkeydelivery.modules.home.tabs.alcohol.items;

import com.koushikdutta.ion.builder.Builders;

import java.util.ArrayList;

/**
 * Created by Developer on 1/17/2018.
 */

public class StoreInfoItem {

    String storeName;
    Integer minOrderCharges;
    Double distance;
    Integer minDeliveryTime;
    Boolean isHeader;
    String ImageUrl;
    Float AverageRating;
    ArrayList<StoreTags> storeTags;

    public StoreInfoItem(String storeName,Boolean isHeader)
    {
        this.storeName = storeName;
        this.isHeader = isHeader;
    }

    public StoreInfoItem(String storeName, Integer minOrderCharges, Double distance, Integer minDeliveryTime, ArrayList<StoreTags> storeTags, String ImageUrl, Float AverageRating, Boolean isHeader) {
        this.storeName = storeName;
        this.minOrderCharges = minOrderCharges;
        this.distance = distance;
        this.minDeliveryTime = minDeliveryTime;
        this.ImageUrl = ImageUrl;
        this.isHeader = isHeader;
        this.storeTags = storeTags;
        this.AverageRating=AverageRating;
    }

    public Float getAverageRating() {
        return AverageRating;
    }

    public void setAverageRating(Float averageRating) {
        AverageRating = averageRating;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getMinOrderCharges() {
        return minOrderCharges;
    }

    public void setMinOrderCharges(Integer minOrderCharges) {
        this.minOrderCharges = minOrderCharges;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getMinDeliveryTime() {
        return minDeliveryTime;
    }

    public void setMinDeliveryTime(Integer minDeliveryTime) {
        this.minDeliveryTime = minDeliveryTime;
    }

    public Boolean getHeader() {
        return isHeader;
    }

    public void setHeader(Boolean header) {
        isHeader = header;
    }

    public ArrayList<StoreTags> getStoreTags() {
        return storeTags;
    }

    public void setStoreTags(ArrayList<StoreTags> storeTags) {
        this.storeTags = storeTags;
    }
}
