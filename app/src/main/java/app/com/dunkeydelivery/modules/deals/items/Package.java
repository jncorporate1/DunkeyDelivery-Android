package app.com.dunkeydelivery.modules.deals.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Developer on 3/6/2018.
 */

public class Package implements Parcelable {

    @SerializedName("Id")
    String Id;
    @SerializedName("Name")
    String Name;
    @SerializedName("Status")
    String Status;
    @SerializedName("Price")
    String Price;
    @SerializedName("Description")
    String Description;
    @SerializedName("IsDeleted")
    String IsDeleted;
    @SerializedName("Store_Id")
    String Store_Id;
    @SerializedName("ImageUrl")
    String ImageUrl;
    @SerializedName("ImageDeletedOnEdit")
    String ImageDeletedOnEdit;
    @SerializedName("Package_Products")
    ArrayList<PackageProducts> PackageProducts;


    public ArrayList<app.com.dunkeydelivery.modules.deals.items.PackageProducts> getPackageProducts() {
        return PackageProducts;
    }

    public void setPackageProducts(ArrayList<app.com.dunkeydelivery.modules.deals.items.PackageProducts> packageProducts) {
        PackageProducts = packageProducts;
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

    public String getImageUrl() {
        if(TextUtils.isEmpty(ImageUrl))
        {
            return "";
        }
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
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

    protected Package(Parcel in) {
        Id = in.readString();
        Name = in.readString();
        Status = in.readString();
        Price = in.readString();
        Description = in.readString();
        IsDeleted = in.readString();
        Store_Id = in.readString();
        ImageUrl = in.readString();
        ImageDeletedOnEdit = in.readString();
        PackageProducts = in.createTypedArrayList(app.com.dunkeydelivery.modules.deals.items.PackageProducts.CREATOR);
    }

    public static final Creator<Package> CREATOR = new Creator<Package>() {
        @Override
        public Package createFromParcel(Parcel in) {
            return new Package(in);
        }

        @Override
        public Package[] newArray(int size) {
            return new Package[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Name);
        dest.writeString(Status);
        dest.writeString(Price);
        dest.writeString(Description);
        dest.writeString(IsDeleted);
        dest.writeString(Store_Id);
        dest.writeString(ImageUrl);
        dest.writeString(ImageDeletedOnEdit);
        dest.writeTypedList(PackageProducts);
    }
}
