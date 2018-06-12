package app.com.dunkeydelivery.modules.home.tabs.alcohol.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.modules.home.items.RatingsItem;
import app.com.dunkeydelivery.modules.home.items.StoreHourBO;
import app.com.dunkeydelivery.modules.home.items.StoreTag;
import app.com.dunkeydelivery.modules.home.items.SubCategoryBO;

/**
 * Created by Developer on 7/15/2017.
 */

public class AlcoholStoreBO implements Parcelable {

    /*@SerializedName("Stores")
    List<AlcoholStoreBO> alcoholStoreBOS;*/


    @SerializedName("Id")
    Integer Id;

    @SerializedName("BusinessType")
    String BusinessType;

    @SerializedName("Description")
    String Description;

    @SerializedName("BusinessName")
    String BusinessName;

    @SerializedName("Distance")
    String Distance;

    @SerializedName("Latitude")
    Float Latitude;

    @SerializedName("Longitude")
    Float Longitude;

    @SerializedName("Open_From")
    String Open_From;

    @SerializedName("Open_To")
    String Open_To;

    @SerializedName("AverageRating")
    Float AverageRating;

    @SerializedName("ImageUrl")
    String ImageUrl;

    @SerializedName("Address")
    String Address;

    @SerializedName("ContactNumber")
    String ContactNumber;

    @SerializedName("MinDeliveryTime")
    String MinDeliveryTime;

    @SerializedName("MinDeliveryCharges")
    String MinDeliveryCharges;

    @SerializedName("MinOrderPrice")
    String MinOrderPrice;

    @SerializedName("IsDeleted")
    Boolean IsDeleted;

    @SerializedName("Location")
    Location Location;

    @SerializedName("RatingType")
    RatingsItem RatingType;

    @SerializedName("ImageDeletedOnEdit")
    Boolean ImageDeletedOnEdit;

    @SerializedName("Categories")
    List<Categories> Categories;

    @SerializedName("StoreTags")
    ArrayList<StoreTag> StoreTags;

    @SerializedName("StoreDeliveryHours")
    StoreHourBO StoreDeliveryHours;

    public AlcoholStoreBO(Integer id, String businessType, String description, String businessName, String Distance, Float latitude, Float longitude, String open_From, String open_To, Float averageRating, String imageUrl, String address, String contactNumber, String minDeliveryTime, String minDeliveryCharges, String minOrderPrice, Boolean isDeleted, Location location, RatingsItem ratingType, Boolean imageDeletedOnEdit, List<Categories> categories, ArrayList<StoreTag> storeTags, StoreHourBO storeDeliveryHours) {

        Id = id;
        BusinessType = businessType;
        Description = description;
        BusinessName = businessName;
        Distance = Distance;
        Latitude = latitude;
        Longitude = longitude;
        Open_From = open_From;
        Open_To = open_To;
        AverageRating = averageRating;
        ImageUrl = imageUrl;
        Address = address;
        ContactNumber = contactNumber;
        MinDeliveryTime = minDeliveryTime;
        MinDeliveryCharges = minDeliveryCharges;
        MinOrderPrice = minOrderPrice;
        IsDeleted = isDeleted;
        Location = location;
        RatingType = ratingType;
        ImageDeletedOnEdit = imageDeletedOnEdit;
        Categories = categories;
        StoreTags = StoreTags;
        StoreDeliveryHours = storeDeliveryHours;
    }

    public StoreHourBO getStoreDeliveryHours() {
        return StoreDeliveryHours;
    }

    public void setStoreDeliveryHours(StoreHourBO storeDeliveryHours) {
        StoreDeliveryHours = storeDeliveryHours;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public ArrayList<StoreTag> getStoreTags() {
        return StoreTags;
    }

    public void setStoreTags(ArrayList<StoreTag> storeTags) {
        StoreTags = storeTags;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public Float getLatitude() {
        return Latitude;
    }

    public void setLatitude(Float latitude) {
        Latitude = latitude;
    }

    public Float getLongitude() {
        return Longitude;
    }

    public void setLongitude(Float longitude) {
        Longitude = longitude;
    }

    public String getOpen_From() {
        return Open_From;
    }

    public void setOpen_From(String open_From) {
        Open_From = open_From;
    }

    public String getOpen_To() {
        return Open_To;
    }

    public void setOpen_To(String open_To) {
        Open_To = open_To;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getMinDeliveryTime() {
        if (MinDeliveryTime == null || MinDeliveryTime.equals(""))
            return "0";
        return MinDeliveryTime;
    }

    public void setMinDeliveryTime(String minDeliveryTime) {
        MinDeliveryTime = minDeliveryTime;
    }

    public String getMinDeliveryCharges() {
        if (MinDeliveryCharges == null || MinDeliveryCharges.equals(""))
            return "0";
        return MinDeliveryCharges;
    }

    public void setMinDeliveryCharges(String minDeliveryCharges) {
        MinDeliveryCharges = minDeliveryCharges;
    }

    public String getMinOrderPrice() {

        if (MinOrderPrice == null || MinOrderPrice.equals(""))
            return "0";
        return MinOrderPrice;
    }

    public void setMinOrderPrice(String minOrderPrice) {
        MinOrderPrice = minOrderPrice;
    }

    public Boolean getDeleted() {
        return IsDeleted;
    }

    public void setDeleted(Boolean deleted) {
        IsDeleted = deleted;
    }

    public app.com.dunkeydelivery.modules.home.tabs.alcohol.items.Location getLocation() {
        return Location;
    }

    public void setLocation(app.com.dunkeydelivery.modules.home.tabs.alcohol.items.Location location) {
        Location = location;
    }

    public RatingsItem getRatingType() {
        return RatingType;
    }

    public void setRatingType(RatingsItem ratingType) {
        RatingType = ratingType;
    }

    public Boolean getImageDeletedOnEdit() {
        return ImageDeletedOnEdit;
    }

    public void setImageDeletedOnEdit(Boolean imageDeletedOnEdit) {
        ImageDeletedOnEdit = imageDeletedOnEdit;
    }

    public List<app.com.dunkeydelivery.modules.home.tabs.alcohol.items.Categories> getCategories() {
        return Categories;
    }

    public void setCategories(List<app.com.dunkeydelivery.modules.home.tabs.alcohol.items.Categories> categories) {
        Categories = categories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.Id);
        dest.writeString(this.BusinessType);
        dest.writeString(this.Description);
        dest.writeString(this.BusinessName);
        dest.writeString(this.Distance);
        dest.writeValue(this.Latitude);
        dest.writeValue(this.Longitude);
        dest.writeString(this.Open_From);
        dest.writeString(this.Open_To);
        dest.writeValue(this.AverageRating);
        dest.writeString(this.ImageUrl);
        dest.writeString(this.Address);
        dest.writeValue(this.ContactNumber);
        dest.writeValue(this.MinDeliveryTime);
        dest.writeValue(this.MinDeliveryCharges);
        dest.writeValue(this.MinOrderPrice);
        dest.writeValue(this.IsDeleted);
        dest.writeParcelable(this.Location, flags);
        dest.writeParcelable(this.RatingType, flags);
        dest.writeParcelable(this.StoreDeliveryHours, flags);
        dest.writeValue(this.ImageDeletedOnEdit);
        dest.writeList(this.Categories);
        dest.writeList(this.StoreTags);
    }

    protected AlcoholStoreBO(Parcel in) {
        this.Id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.BusinessType = in.readString();
        this.Description = in.readString();
        this.BusinessName = in.readString();
        this.Distance = in.readString();
        this.Latitude = (Float) in.readValue(Double.class.getClassLoader());
        this.Longitude = (Float) in.readValue(Double.class.getClassLoader());
        this.Open_From = in.readString();
        this.Open_To = in.readString();
        this.AverageRating = (Float) in.readValue(Integer.class.getClassLoader());
        this.ImageUrl = in.readString();
        this.Address = in.readString();
        this.ContactNumber = (String) in.readValue(Integer.class.getClassLoader());
        this.MinDeliveryTime = (String) in.readValue(Integer.class.getClassLoader());
        this.MinDeliveryCharges = (String) in.readValue(Integer.class.getClassLoader());
        this.MinOrderPrice = (String) in.readValue(Integer.class.getClassLoader());
        this.IsDeleted = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.Location = in.readParcelable(app.com.dunkeydelivery.modules.home.tabs.alcohol.items.Location.class.getClassLoader());
        this.StoreDeliveryHours = in.readParcelable(StoreHourBO.class.getClassLoader());
        this.RatingType = in.readParcelable(RatingsItem.class.getClassLoader());
        this.ImageDeletedOnEdit = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.Categories = new ArrayList<app.com.dunkeydelivery.modules.home.tabs.alcohol.items.Categories>();
        this.StoreTags = new ArrayList<StoreTag>();
        in.readList(this.Categories, app.com.dunkeydelivery.modules.home.tabs.alcohol.items.Categories.class.getClassLoader());
    }

    public static final Creator<AlcoholStoreBO> CREATOR = new Creator<AlcoholStoreBO>() {
        @Override
        public AlcoholStoreBO createFromParcel(Parcel source) {
            return new AlcoholStoreBO(source);
        }

        @Override
        public AlcoholStoreBO[] newArray(int size) {
            return new AlcoholStoreBO[size];
        }
    };
}
