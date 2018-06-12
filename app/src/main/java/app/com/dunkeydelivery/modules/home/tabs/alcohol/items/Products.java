package app.com.dunkeydelivery.modules.home.tabs.alcohol.items;

import com.google.gson.annotations.SerializedName;

import app.com.dunkeydelivery.App;
import app.com.dunkeydelivery.utils.ImageUtils;

/**
 * Created by Developer on 1/16/2018.
 */

public class Products {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("Name")
    String Name;

    @SerializedName("Price")
    Double Price;

    @SerializedName("Description")
    String Description;

    @SerializedName("Image")
    String Image;

    @SerializedName("Status")
    Integer Status;

    @SerializedName("IsDeleted")
    Boolean IsDeleted;

    @SerializedName("Category_Id")
    Integer Category_Id;

    @SerializedName("Store_Id")
    Integer Store_Id;

    @SerializedName("Size")
    String Size;

    @SerializedName("ImageDeletedOnEdit")
    Boolean ImageDeletedOnEdit;

    public Products(Integer id, String name, Double price, String description, String image, Integer status, Boolean isDeleted, Integer category_Id, Integer store_Id, String size, Boolean imageDeletedOnEdit) {
        Id = id;
        Name = name;
        Price = price;
        Description = description;
        Image = image;
        Status = status;
        IsDeleted = isDeleted;
        Category_Id = category_Id;
        Store_Id = store_Id;
        Size = size;
        ImageDeletedOnEdit = imageDeletedOnEdit;
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

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return ImageUtils.getBaseImageUrlDummy(App.context) + Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Boolean getDeleted() {
        return IsDeleted;
    }

    public void setDeleted(Boolean deleted) {
        IsDeleted = deleted;
    }

    public Integer getCategory_Id() {
        return Category_Id;
    }

    public void setCategory_Id(Integer category_Id) {
        Category_Id = category_Id;
    }

    public Integer getStore_Id() {
        return Store_Id;
    }

    public void setStore_Id(Integer store_Id) {
        Store_Id = store_Id;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public Boolean getImageDeletedOnEdit() {
        return ImageDeletedOnEdit;
    }

    public void setImageDeletedOnEdit(Boolean imageDeletedOnEdit) {
        ImageDeletedOnEdit = imageDeletedOnEdit;
    }
}
