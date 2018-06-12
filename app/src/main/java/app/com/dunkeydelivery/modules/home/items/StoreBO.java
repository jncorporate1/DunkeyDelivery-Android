package app.com.dunkeydelivery.modules.home.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.App;
import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.modules.home.tabs.food.pager.items.ReviewBO;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.NumberUtils;

/**
 * Created by Developer on 7/5/2017.
 */

public class StoreBO implements Parcelable {

    String storeName;
    boolean isHeader;
    boolean isSelected;

    @SerializedName("MinDeliveryCharges")
    @Expose
    String MinDeliveryCharges;


    @SerializedName("Id")
    @Expose
    public Integer id;
    @SerializedName("BusinessType")
    @Expose
    public String businessType;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("BusinessName")
    @Expose
    public String businessName;
    @SerializedName("Latitude")
    @Expose
    public Float latitude;
    @SerializedName("Longitude")
    @Expose
    public Float longitude;
    @SerializedName("Open_From")
    @Expose
    public String openFrom;
    @SerializedName("Open_To")
    @Expose
    public String openTo;
    @SerializedName("AverageRating")
    @Expose
    public Float averageRating;
    @SerializedName("ImageUrl")
    @Expose
    public String imageUrl;
    @SerializedName("Address")
    @Expose
    public String address;
    @SerializedName("MinOrderPrice")
    @Expose
    public String minOrderPrice;

    @SerializedName(value = "storeDeliveryHours", alternate = {"StoreDeliveryHours"})
    @Expose
//    public List<StoreHourBO> storeDeliveryHours = null;       //changed from array to object..
    public StoreHourBO storeDeliveryHours;
    @SerializedName("storeTags")
    @Expose
    public List<StoreTag> storeTags;

    @SerializedName("Distance")
    @Expose
    public String Distance;
    @SerializedName("ContactNumber")
    @Expose
    public String ContactNumber;
    @SerializedName("MinDeliveryTime")
    @Expose
    public String MinDeliveryTime;

    @SerializedName("StoreRatings")
    @Expose
    public ArrayList<ReviewBO> storeReviews;

    @SerializedName("RatingType")
    @Expose
    public RatingsItem ratingsItem;


    public StoreBO(String storeName, boolean isHeader) {
        this.storeName = storeName;
        this.isHeader = isHeader;
    }


    public StoreBO(String minDeliveryCharges, String storeName, Integer id, String businessType, String description, String businessName, Float latitude, Float longitude, String openFrom, String openTo, Float averageRating, String imageUrl, String address, String minOrderPrice, StoreHourBO storeDeliveryHours, List<StoreTag> storeTags, String distance, String contactNumber, String minDeliveryTime, ArrayList<ReviewBO> storeReviews, RatingsItem ratingsItem) {
        this.storeName = storeName;
        this.id = id;
        this.businessType = businessType;
        this.description = description;
        this.businessName = businessName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openFrom = openFrom;
        this.openTo = openTo;
        this.averageRating = averageRating;
        this.imageUrl = imageUrl;
        this.address = address;
        this.minOrderPrice = minOrderPrice;
        this.storeDeliveryHours = storeDeliveryHours;
        this.storeTags = storeTags;
        Distance = distance;
        ContactNumber = contactNumber;
        MinDeliveryTime = minDeliveryTime;
        this.storeReviews = storeReviews;
        this.ratingsItem = ratingsItem;
        this.MinDeliveryCharges = minDeliveryCharges;
    }


    public StoreBO(String storeName, boolean isSelected, Integer id, String businessType, String description, String businessName, Float latitude, Float longitude, String openFrom, String openTo, Float averageRating, String imageUrl, String address, String minOrderPrice, StoreHourBO storeDeliveryHours, List<StoreTag> storeTags, String distance, String contactNumber, String minDeliveryTime, ArrayList<ReviewBO> storeReviews, RatingsItem ratingsItem) {
        this.storeName = storeName;
        this.isSelected = isSelected;
        this.id = id;
        this.businessType = businessType;
        this.description = description;
        this.businessName = businessName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openFrom = openFrom;
        this.openTo = openTo;
        this.averageRating = averageRating;
        this.imageUrl = imageUrl;
        this.address = address;
        this.minOrderPrice = minOrderPrice;
        this.storeDeliveryHours = storeDeliveryHours;
        this.storeTags = storeTags;
        Distance = distance;
        ContactNumber = contactNumber;
        MinDeliveryTime = minDeliveryTime;
        this.storeReviews = storeReviews;
        this.ratingsItem = ratingsItem;
    }

    public String getMinDeliveryCharges() {
        if(MinDeliveryCharges==null)
        {
            return "0";
        }
        return MinDeliveryCharges;
    }

    public void setMinDeliveryCharges(String minDeliveryCharges) {
        MinDeliveryCharges = minDeliveryCharges;
    }

    public StoreBO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        if (!TextUtils.isEmpty(address))
            return address;
        else
            return "";
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMinOrderPrice() {
        if (!TextUtils.isEmpty(minOrderPrice))
            return Constants.CURRENCY_SYMBOL + minOrderPrice;
        else
            return Constants.CURRENCY_SYMBOL + "0";
    }

    public String getDistance() {
        if (!TextUtils.isEmpty(Distance))
            return NumberUtils.formatNumberTo2DecimalPlaces(Distance);
        else
            return "";
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getContactNumber() {
        if (!TextUtils.isEmpty(ContactNumber))
            return ContactNumber;
        else
            return "";
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getMinDeliveryTime() {
        if (!TextUtils.isEmpty(MinDeliveryTime))
            return MinDeliveryTime + " min";
        else
            return "";
    }

    public String getMinDeliveryTimeWithoutMin() {
        if (!TextUtils.isEmpty(MinDeliveryTime))
            return MinDeliveryTime;
        else
            return "";
    }

    public void setMinDeliveryTime(String minDeliveryTime) {
        MinDeliveryTime = minDeliveryTime;
    }

    public void setMinOrderPrice(String minOrderPrice) {
        this.minOrderPrice = minOrderPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessName() {
        return businessName;
    }


    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
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

    public Float getAverageRating() {
        if (averageRating == null)
            return Float.valueOf(0);
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public String getImageUrl() {
        return ImageUtils.getBaseImageUrlDummy(App.context) + imageUrl;
    }

    public String getImageUrlDummy() { //todo be change
        return ImageUtils.getBaseImageUrlDummy(App.context) + imageUrl;
    }

    public StoreHourBO getStoreDeliveryHours() {
        return storeDeliveryHours;
    }


    public void setStoreDeliveryHours(StoreHourBO storeDeliveryHours) {
        this.storeDeliveryHours = storeDeliveryHours;
    }

//    public String getStoreTags() {

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public StoreBO(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<StoreTag> getStoreTags() {
        return storeTags;
    }

    public void setStoreTags(List<StoreTag> storeTags) {
        this.storeTags = storeTags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //    }
//        this.storeTags = storeTags;
//    public void setStoreTags(String storeTags) {
//
//    }
//        return storeTags;
    public ArrayList<ReviewBO> getStoreReviews() {
        if (storeReviews == null) {
            return new ArrayList<ReviewBO>();
        }
        return storeReviews;
    }

    public void setStoreReviews(ArrayList<ReviewBO> storeReviews) {
        this.storeReviews = storeReviews;
    }

    public RatingsItem getRatingsItem() {
        if (ratingsItem == null) {
            ratingsItem = new RatingsItem();
        }
        return ratingsItem;
    }

    public void setRatingsItem(RatingsItem ratingsItem) {
        this.ratingsItem = ratingsItem;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.storeName);
        dest.writeByte(this.isHeader ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeValue(this.id);
        dest.writeString(this.businessType);
        dest.writeString(this.MinDeliveryCharges);
        dest.writeString(this.description);
        dest.writeString(this.businessName);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeString(this.openFrom);
        dest.writeString(this.openTo);
        dest.writeValue(this.averageRating);
        dest.writeString(this.imageUrl);
        dest.writeString(this.address);
        dest.writeString(this.minOrderPrice);
        dest.writeParcelable(this.storeDeliveryHours, flags);
        dest.writeParcelable(this.ratingsItem, flags);
        dest.writeTypedList(this.storeTags);
        dest.writeString(this.Distance);
        dest.writeString(this.ContactNumber);
        dest.writeString(this.MinDeliveryTime);
    }

    protected StoreBO(Parcel in) {
        this.storeName = in.readString();
        this.isHeader = in.readByte() != 0;
        this.isSelected = in.readByte() != 0;
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.businessType = in.readString();
        this.MinDeliveryCharges = in.readString();
        this.description = in.readString();
        this.businessName = in.readString();
        this.latitude = (Float) in.readValue(Float.class.getClassLoader());
        this.longitude = (Float) in.readValue(Float.class.getClassLoader());
        this.openFrom = in.readString();
        this.openTo = in.readString();
        this.averageRating = (Float) in.readValue(Float.class.getClassLoader());
        this.imageUrl = in.readString();
        this.address = in.readString();
        this.minOrderPrice = in.readString();
        this.storeDeliveryHours = in.readParcelable(StoreHourBO.class.getClassLoader());
        this.ratingsItem = in.readParcelable(RatingsItem.class.getClassLoader());
        this.storeTags = in.createTypedArrayList(StoreTag.CREATOR);
        this.Distance = in.readString();
        this.ContactNumber = in.readString();
        this.MinDeliveryTime = in.readString();
    }

    public static final Creator<StoreBO> CREATOR = new Creator<StoreBO>() {
        @Override
        public StoreBO createFromParcel(Parcel source) {
            return new StoreBO(source);
        }

        @Override
        public StoreBO[] newArray(int size) {
            return new StoreBO[size];
        }
    };
}
