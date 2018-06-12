package app.com.dunkeydelivery.modules.home.items;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import app.com.dunkeydelivery.App;
import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.utils.ImageUtils;


/**
 * Created by Developer on 9/16/2017.
 */

public class ProductBO implements Parcelable {

    String address;
    Integer check = 0;

    String selectedTime;
    String selectedDate;

    @SerializedName("ItemId")
    @Expose
    Integer ItemId;
    @SerializedName("Id")
    int id;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Price")
    @Expose
    public String price;
    @SerializedName("Description")
    @Expose
    public String description;

    @SerializedName("Image")
    @Expose
    public String image;

    @SerializedName("Image_Selected")
    @Expose
    public String Image_Selected;

    @SerializedName("Status")
    @Expose
    Integer Status;
    @SerializedName("IsDeleted")
    Boolean IsDeleted;
    @SerializedName(value = "Category_id", alternate = {"Category_Id"})
    @Expose
    public Integer categoryId;
    @SerializedName(value = "Store_id", alternate = {"Store_Id"})
    @Expose
    public Integer storeId;
    @SerializedName("Size")
    @Expose
    String Size;
    @SerializedName("ImageDeletedOnEdit")
    @Expose
    Boolean ImageDeletedOnEdit;
    @SerializedName("BusinessName")
    @Expose
    public String storeName;
    @SerializedName("BusinessType")
    @Expose
    String businessType;
    @SerializedName("MinDeliveryTime")
    @Expose
    String minDeliveryTime;
    @SerializedName("MinDeliveryCharges")
    @Expose
    Integer minDeliveryCharges;
    @SerializedName("MinOrderPrice")
    @Expose
    String minOrderPrice;

    @SerializedName("AdditionNote")
    @Expose
    String additionNote;

    @SerializedName("ProductSizes")
    @Expose
    ArrayList<ProductSizes> productSizes;

    public ArrayList<ProductSizes> getProductSizes() {
        return productSizes;
    }

    public void setProductSizes(ArrayList<ProductSizes> productSizes) {
        this.productSizes = productSizes;
    }

    public Integer quantity;

    public Integer getItemType() {
        if (ItemId == null) {
            return 0;
        }
        return ItemId;
    }

    public String getSelectedTime() {
        if(TextUtils.isEmpty(selectedTime))
            return "";
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public String getSelectedDate() {
        if(TextUtils.isEmpty(selectedDate))
            return "";
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public Integer getCheck() {
        return check;
    }

    public void setCheck(Integer check) {
        this.check = check;
    }

    public void setItemType(Integer itemId) {
        ItemId = itemId;
    }

    public String getAddress() {
        if(TextUtils.isEmpty(address))
            return "";
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getSize() {
        if(TextUtils.isEmpty(Size))
            return "";
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

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStoreName() {
        if (TextUtils.isEmpty(storeName))
            return "";
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getQuantity() {
        if (quantity == null)
            return 0;
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductBO() {
    }

    public ProductBO(String name) {
        this.name = name;
    }

    public String getName() {
        if (!TextUtils.isEmpty(name))
            return name;
        else
            return "";
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPrice() {
        if (!TextUtils.isEmpty(price)) {
            return Constants.CURRENCY_SYMBOL + price;
        } else {
            return "";
        }
    }

    public String getPriceWithoutCurrenySign() {
        if (!TextUtils.isEmpty(price)) {
            return price;
        } else {
            return "";
        }
    }

    public String getPriceInDouble() {
        if (!TextUtils.isEmpty(price)) {
            return Constants.CURRENCY_SYMBOL + Double.parseDouble(price);
        } else {
            return "";
        }
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        if (!TextUtils.isEmpty(description))
            return description;
        else
            return "";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        if (!TextUtils.isEmpty(image)) {
            if (image.startsWith("http") || image.startsWith("Http")) {
                return image.trim();
            }
        }
        return (ImageUtils.getBaseImageUrlDummy(App.context) + image).trim();
    }

    public String getCartImage() {
        if (TextUtils.isEmpty(Image_Selected))
            return getImage();
        return getImage_Selected();
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_Selected() {
        return (ImageUtils.getBaseImageUrlDummy(App.context) + Image_Selected).trim();
    }

    public void setImage_Selected(String image_Selected) {
        Image_Selected = image_Selected;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public int getId() {
        return id;
    }

    public String getBusinessType() {
        if (TextUtils.isEmpty(businessType))
            return "";
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getMinDeliveryTime() {
        if (TextUtils.isEmpty(minDeliveryTime))
            return "";
        return minDeliveryTime;
    }

    public void setMinDeliveryTime(String minDeliveryTime) {
        this.minDeliveryTime = minDeliveryTime;
    }

    public Integer getMinDeliveryCharges() {
        if (minDeliveryCharges == null)
            return 0;
        return minDeliveryCharges;
    }

    public void setMinDeliveryCharges(Integer minDeliveryCharges) {
        this.minDeliveryCharges = minDeliveryCharges;
    }

    public String getMinOrderPrice() {
        if (TextUtils.isEmpty(minOrderPrice))
            return "";
        return minOrderPrice;
    }

    public void setMinOrderPrice(String minOrderPrice) {
        this.minOrderPrice = minOrderPrice;
    }

    public String getAdditionNote() {
        if (!TextUtils.isEmpty(additionNote))
            return additionNote;
        return "";
    }

    public void setAdditionNote(String additionNote) {
        this.additionNote = additionNote;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.description);
        dest.writeString(this.image);
        dest.writeString(this.Image_Selected);
        dest.writeValue(this.categoryId);
        dest.writeValue(this.id);
        dest.writeValue(this.storeId);
        dest.writeString(this.storeName);
        dest.writeString(this.businessType);
        dest.writeValue(this.minDeliveryTime);
        dest.writeValue(this.minDeliveryCharges);
        dest.writeValue(this.minOrderPrice);
        dest.writeTypedList(this.productSizes);
        if (quantity == null) {
            dest.writeInt(0);
        } else {
            dest.writeInt(this.quantity);
        }
        dest.writeString(this.additionNote);
        dest.writeString(this.address);
        if (ItemId == null) {
            dest.writeInt(0);
        } else {
            dest.writeInt(ItemId);
        }
    }

    protected ProductBO(Parcel in) {
        this.name = in.readString();
        this.price = in.readString();
        this.description = in.readString();
        this.image = in.readString();
        this.Image_Selected = in.readString();
        this.categoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.storeId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.storeName = in.readString();
        this.businessType = in.readString();
        this.minDeliveryTime = (String) in.readValue(Integer.class.getClassLoader());
        this.minDeliveryCharges = (Integer) in.readValue(Integer.class.getClassLoader());
        this.minOrderPrice = (String) in.readValue(Integer.class.getClassLoader());
        this.quantity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.additionNote = in.readString();
        this.address = in.readString();
        this.ItemId = in.readInt();
        this.productSizes = in.createTypedArrayList(ProductSizes.CREATOR);
    }

    public static final Parcelable.Creator<ProductBO> CREATOR = new Parcelable.Creator<ProductBO>() {
        @Override
        public ProductBO createFromParcel(Parcel source) {
            return new ProductBO(source);
        }

        @Override
        public ProductBO[] newArray(int size) {
            return new ProductBO[size];
        }
    };

}
