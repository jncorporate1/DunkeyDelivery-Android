package app.com.dunkeydelivery.modules.home.tabs.alcohol.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.modules.home.items.ProductBO;

/**
 * Created by Developer on 1/16/2018.
 */

public class Categories implements Parcelable{

    @SerializedName("Id")
    Integer Id;

    @SerializedName("Name")
    String Name;

    @SerializedName("Description")
    String Description;

    @SerializedName("Status")
    String Status;

    @SerializedName("Store_Id")
    Long Store_Id;

    @SerializedName("ParentCategoryId")
    Integer ParentCategoryId;

    @SerializedName("IsDeleted")
    Boolean IsDeleted;

    @SerializedName("ImageUrl")
    String ImageUrl;

    @SerializedName("ImageDeletedOnEdit")
    Boolean ImageDeletedOnEdit;

    @SerializedName("Products")
    List<ProductBO> Products;

    public Categories(Integer id, String name, String description, String status, Long store_Id, Integer parentCategoryId, Boolean isDeleted, String imageUrl, Boolean imageDeletedOnEdit, List<ProductBO> products) {
        Id = id;
        Name = name;
        Description = description;
        Status = status;
        Store_Id = store_Id;
        ParentCategoryId = parentCategoryId;
        IsDeleted = isDeleted;
        ImageUrl = imageUrl;
        ImageDeletedOnEdit = imageDeletedOnEdit;
        Products = products;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Long getStore_Id() {
        return Store_Id;
    }

    public void setStore_Id(Long store_Id) {
        Store_Id = store_Id;
    }

    public Integer getParentCategoryId() {
        return ParentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        ParentCategoryId = parentCategoryId;
    }

    public Boolean getDeleted() {
        return IsDeleted;
    }

    public void setDeleted(Boolean deleted) {
        IsDeleted = deleted;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public Boolean getImageDeletedOnEdit() {
        return ImageDeletedOnEdit;
    }

    public void setImageDeletedOnEdit(Boolean imageDeletedOnEdit) {
        ImageDeletedOnEdit = imageDeletedOnEdit;
    }

    public List<ProductBO> getProducts() {
        return Products;
    }

    public void setProducts(List<ProductBO> products) {
        Products = products;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.Id);
        dest.writeString(this.Name);
        dest.writeString(this.Description);
        dest.writeString(this.Status);
        dest.writeValue(this.Store_Id);
        dest.writeValue(this.ParentCategoryId);
        dest.writeValue(this.IsDeleted);
        dest.writeString(this.ImageUrl);
        dest.writeValue(this.ImageDeletedOnEdit);
        dest.writeList(this.Products);
    }

    protected Categories(Parcel in) {
        this.Id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.Name = in.readString();
        this.Description = in.readString();
        this.Status = in.readString();
        this.Store_Id = (Long) in.readValue(Long.class.getClassLoader());
        this.ParentCategoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.IsDeleted = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.ImageUrl = in.readString();
        this.ImageDeletedOnEdit = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.Products = new ArrayList<ProductBO>();
        in.readList(this.Products, app.com.dunkeydelivery.modules.home.tabs.alcohol.items.Products.class.getClassLoader());
    }

    public static final Creator<Categories> CREATOR = new Creator<Categories>() {
        @Override
        public Categories createFromParcel(Parcel source) {
            return new Categories(source);
        }

        @Override
        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };
}
