package app.com.dunkeydelivery.modules.home.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 3/30/2018.
 */

public class ProductSizes implements Parcelable {

    @SerializedName("Id")
    String Id;
    @SerializedName("Unit")
    String Unit;
    @SerializedName("Weight")
    String Weight;
    @SerializedName("Size")
    String Size;
    @SerializedName("IsDeleted")
    String IsDeleted;
    @SerializedName("Product_Id")
    String Product_Id;
    /*@SerializedName("Product")
    String Product;*/
    @SerializedName("Price")
    String Price;
    @SerializedName("NetWeight")
    String NetWeight;

    protected ProductSizes(Parcel in) {
        Id = in.readString();
        Unit = in.readString();
        Weight = in.readString();
        Size = in.readString();
        IsDeleted = in.readString();
        Product_Id = in.readString();
//        Product = in.readString();
        NetWeight = in.readString();
    }

    public static final Creator<ProductSizes> CREATOR = new Creator<ProductSizes>() {
        @Override
        public ProductSizes createFromParcel(Parcel in) {
            return new ProductSizes(in);
        }

        @Override
        public ProductSizes[] newArray(int size) {
            return new ProductSizes[size];
        }
    };

    public String getPrice() {
        if (TextUtils.isEmpty(Price)) {
            return "";
        }
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getId() {
        if (TextUtils.isEmpty(Id)) {
            return "";
        }
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUnit() {
        if (TextUtils.isEmpty(Unit)) {
            return "";
        }
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getWeight() {
        if (TextUtils.isEmpty(Weight)) {
            return "";
        }
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getSize() {
        if (TextUtils.isEmpty(Size)) {
            return "";
        }
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getIsDeleted() {
        if (TextUtils.isEmpty(IsDeleted)) {
            return "";
        }
        return IsDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        IsDeleted = isDeleted;
    }

    public String getProduct_Id() {
        if (TextUtils.isEmpty(Product_Id)) {
            return "";
        }
        return Product_Id;
    }

    public void setProduct_Id(String product_Id) {
        Product_Id = product_Id;
    }

    /*public String getProduct() {
        if(TextUtils.isEmpty(Product))
        {
            return "";
        }
        return Product;
    }*/

    public String getNetWeight() {
        if (TextUtils.isEmpty(NetWeight)) {
            return "";
        }
        return NetWeight;
    }

    public void setNetWeight(String netWeight) {
        NetWeight = netWeight;
    }

    /*public void setProduct(String product) {
        Product = product;
    }*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Unit);
        dest.writeString(Weight);
        dest.writeString(Size);
        dest.writeString(IsDeleted);
        dest.writeString(Product_Id);
//        dest.writeString(Product);
        dest.writeString(NetWeight);
    }
}
