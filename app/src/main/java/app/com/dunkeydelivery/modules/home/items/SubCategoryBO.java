package app.com.dunkeydelivery.modules.home.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import app.com.dunkeydelivery.items.DeliveryTypes;

/**
 * Created by Developer on 7/13/2017.
 */

public class SubCategoryBO implements Parcelable {

    boolean isExpanded;
    String title;

    @SerializedName("Id")
    @Expose
    public Integer id;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("Status")
    @Expose
    public Integer status;
    @SerializedName("Store_Id")
    @Expose
    public Integer storeId;
    @SerializedName("ParentCategoryId")
    @Expose
    public Integer parentCategoryId;
    @SerializedName("SubCategories")
    @Expose
    public List<SubCategoryBO> SubCategories;

    List<String> subCatgories;

    public SubCategoryBO(boolean isExpanded, String title, List<String> subCatgories) {
        this.isExpanded = isExpanded;
        this.title = title;
        this.subCatgories = subCatgories;
    }

    public List<SubCategoryBO> getSubCategories() {
        return SubCategories;
    }

    public void setSubCategories(List<SubCategoryBO> subCategories) {
        SubCategories = subCategories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSubCatgories() {
        return subCatgories;
    }

    public void setSubCatgories(List<String> subCatgories) {
        this.subCatgories = subCatgories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isExpanded ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeValue(this.status);
        dest.writeValue(this.storeId);
        dest.writeValue(this.parentCategoryId);
        dest.writeTypedList(this.SubCategories);
        dest.writeStringList(this.subCatgories);
    }

    protected SubCategoryBO(Parcel in) {
        this.isExpanded = in.readByte() != 0;
        this.title = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.storeId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.parentCategoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.SubCategories = in.createTypedArrayList(SubCategoryBO.CREATOR);
        this.subCatgories = in.createStringArrayList();
    }

    public static final Creator<SubCategoryBO> CREATOR = new Creator<SubCategoryBO>() {
        @Override
        public SubCategoryBO createFromParcel(Parcel source) {
            return new SubCategoryBO(source);
        }

        @Override
        public SubCategoryBO[] newArray(int size) {
            return new SubCategoryBO[size];
        }
    };
}
