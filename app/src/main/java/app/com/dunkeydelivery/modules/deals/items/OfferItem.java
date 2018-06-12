package app.com.dunkeydelivery.modules.deals.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import app.com.dunkeydelivery.App;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.utils.ImageUtils;

/**
 * Created by Developer on 2/19/2018.
 */

public class OfferItem implements Parcelable {

    @SerializedName("Name")
    String Name;
    @SerializedName("Status")
    String Status;
    @SerializedName("ImageDeletedOnEdit")
    String ImageDeletedOnEdit;
    @SerializedName("Description")
    String Description;
    @SerializedName("ValidFrom")
    String ValidFrom;
    @SerializedName("ValidUpto")
    String ValidUpto;
    @SerializedName("Store")
    StoreBO Store;
    @SerializedName("Store_Id")
    String Store_Id;
    @SerializedName("IsDeleted")
    String IsDeleted;
    @SerializedName("ImageUrl")
    String ImageUrl;
    @SerializedName("Id")
    String Id;
    @SerializedName("Title")
    String Title;


    public String getName() {
        if(TextUtils.isEmpty(Name))
        {
            return "";
        }
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        if(TextUtils.isEmpty(Status))
        {
            return "";
        }
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getImageDeletedOnEdit() {
        if(TextUtils.isEmpty(ImageDeletedOnEdit))
        {
            return "";
        }
        return ImageDeletedOnEdit;
    }

    public void setImageDeletedOnEdit(String imageDeletedOnEdit) {
        ImageDeletedOnEdit = imageDeletedOnEdit;
    }

    public String getDescription() {
        if(TextUtils.isEmpty(Description))
        {
            return "";
        }
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getValidFrom() {
        if(TextUtils.isEmpty(ValidFrom))
        {
            return "";
        }
        return ValidFrom;
    }

    public void setValidFrom(String validFrom) {
        ValidFrom = validFrom;
    }

    public String getValidUpto() {
        if(TextUtils.isEmpty(ValidUpto))
        {
            return "";
        }
        return ValidUpto;
    }

    public void setValidUpto(String validUpto) {
        ValidUpto = validUpto;
    }

    public StoreBO getStore() {
        return Store;
    }

    public void setStore(StoreBO store) {
        Store = store;
    }

    public String getStore_Id() {
        if(TextUtils.isEmpty(Store_Id))
        {
            return "";
        }
        return Store_Id;
    }

    public void setStore_Id(String store_Id) {
        Store_Id = store_Id;
    }

    public String getIsDeleted() {
        if(TextUtils.isEmpty(IsDeleted))
        {
            return "";
        }
        return IsDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        IsDeleted = isDeleted;
    }

    public String getImageUrl() {
        if(TextUtils.isEmpty(ImageUrl))
        {
            return "";
        }
        return ImageUtils.getBaseImageUrl(App.context)+ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getId() {
        if(TextUtils.isEmpty(Id))
        {
            return "";
        }
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        if(TextUtils.isEmpty(Title))
        {
            return "";
        }
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    protected OfferItem(Parcel in) {
        Name = in.readString();
        Status = in.readString();
        ImageDeletedOnEdit = in.readString();
        Description = in.readString();
        ValidFrom = in.readString();
        ValidUpto = in.readString();
        Store = in.readParcelable(StoreBO.class.getClassLoader());
        Store_Id = in.readString();
        IsDeleted = in.readString();
        ImageUrl = in.readString();
        Id = in.readString();
        Title = in.readString();
    }

    public static final Creator<OfferItem> CREATOR = new Creator<OfferItem>() {
        @Override
        public OfferItem createFromParcel(Parcel in) {
            return new OfferItem(in);
        }

        @Override
        public OfferItem[] newArray(int size) {
            return new OfferItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Status);
        dest.writeString(ImageDeletedOnEdit);
        dest.writeString(Description);
        dest.writeString(ValidFrom);
        dest.writeString(ValidUpto);
        dest.writeParcelable(Store, flags);
        dest.writeString(Store_Id);
        dest.writeString(IsDeleted);
        dest.writeString(ImageUrl);
        dest.writeString(Id);
        dest.writeString(Title);
    }
}
