package app.com.dunkeydelivery.modules.deals.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import app.com.dunkeydelivery.modules.home.items.ProductBO;

/**
 * Created by Developer on 3/6/2018.
 */

public class PackageProducts implements Parcelable {

    @SerializedName("Id")
    String Id;
    @SerializedName("Qty")
    String Qty;
    @SerializedName("Product_Id")
    String ProductId;
    @SerializedName("Package_Id")
    String PackageId;
    @SerializedName("PackageProductId")
    String PackageProductId;
    @SerializedName("Product")
    ProductBO Product;

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

    public String getQty() {
        if(TextUtils.isEmpty(Qty))
        {
            return "";
        }
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getProductId() {
        if(TextUtils.isEmpty(ProductId))
        {
            return "";
        }
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getPackageId() {
        if(TextUtils.isEmpty(PackageId))
        {
            return "";
        }
        return PackageId;
    }

    public void setPackageId(String packageId) {
        PackageId = packageId;
    }

    public String getPackageProductId() {
        if(TextUtils.isEmpty(PackageProductId))
        {
            return "";
        }
        return PackageProductId;
    }

    public void setPackageProductId(String packageProductId) {
        PackageProductId = packageProductId;
    }

    public ProductBO getProduct() {
        return Product;
    }

    public void setProduct(ProductBO product) {
        Product = product;
    }

    protected PackageProducts(Parcel in) {
        Id = in.readString();
        Qty = in.readString();
        ProductId = in.readString();
        PackageId = in.readString();
        PackageProductId = in.readString();
        Product = in.readParcelable(ProductBO.class.getClassLoader());
    }

    public static final Creator<PackageProducts> CREATOR = new Creator<PackageProducts>() {
        @Override
        public PackageProducts createFromParcel(Parcel in) {
            return new PackageProducts(in);
        }

        @Override
        public PackageProducts[] newArray(int size) {
            return new PackageProducts[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Qty);
        dest.writeString(ProductId);
        dest.writeString(PackageId);
        dest.writeString(PackageProductId);
        dest.writeParcelable(Product, flags);
    }
}
