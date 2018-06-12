package app.com.dunkeydelivery.modules.deals.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import app.com.dunkeydelivery.modules.home.items.ProductBO;

/**
 * Created by Developer on 2/12/2018.
 */

public class DealsItem implements Parcelable {


    @SerializedName("Id")
    String Id;
    @SerializedName("Offer_Id")
    String OfferId;
    @SerializedName("Product_Id")
    String ProductId;
    @SerializedName("OfferProductId")
    String OfferProductId;
    @SerializedName("Description")
    String Description;
    @SerializedName("Price")
    String Price;
    @SerializedName("DiscountPercentage")
    String DiscountPercentage;
    @SerializedName("SlashPrice")
    String SlashPrice;
    @SerializedName("ImageUrl")
    String ImageUrl;
    @SerializedName("IsDeleted")
    String IsDeleted;
    @SerializedName("Offer")
    OfferItem Offer;
    @SerializedName("Product")
    ProductBO Product;
    @SerializedName("Package")
    Package Package;


    public app.com.dunkeydelivery.modules.deals.items.Package getPackage() {
        return Package;
    }

    public void setPackage(app.com.dunkeydelivery.modules.deals.items.Package aPackage) {
        Package = aPackage;
    }

    int ItemType=-1;


    public int getItemType() {
        if (ItemType == -1) {
            return 0;
        }
        return ItemType;
    }

    public void setItemType(Integer itemType) {
        ItemType = itemType;
    }

    public String getId()
    {
        if(TextUtils.isEmpty(Id))
        {
            return "";
        }
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOfferId() {
        if(TextUtils.isEmpty(OfferId))
        {
            return "";
        }
        return OfferId;
    }

    public void setOfferId(String offerId) {
        OfferId = offerId;
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

    public String getOfferProductId() {
        if(TextUtils.isEmpty(OfferProductId))
        {
            return "";
        }
        return OfferProductId;
    }

    public void setOfferProductId(String offerProductId) {
        OfferProductId = offerProductId;
    }

    public String getDescription() {
        if( TextUtils.isEmpty(Description))
        {
            return "";
        }
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public String getDiscountPercentage() {
        if(TextUtils.isEmpty(DiscountPercentage))
        {
            return "";
        }
        return DiscountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        DiscountPercentage = discountPercentage;
    }

    public String getSlashPrice() {
        if(TextUtils.isEmpty(SlashPrice))
        {
            return "";
        }
        return SlashPrice;
    }

    public void setSlashPrice(String slashPrice) {
        SlashPrice = slashPrice;
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

    public OfferItem getOffer() {
        return Offer;
    }

    public void setOffer(OfferItem offer) {
        Offer = offer;
    }

    public ProductBO getProduct() {
        return Product;
    }

    public void setProduct(ProductBO product) {
        Product = product;
    }

    protected DealsItem(Parcel in) {
        Id = in.readString();
        OfferId = in.readString();
        ProductId = in.readString();
        OfferProductId = in.readString();
        Description = in.readString();
        Price = in.readString();
        DiscountPercentage = in.readString();
        SlashPrice = in.readString();
        ImageUrl = in.readString();
        IsDeleted = in.readString();
        Offer = in.readParcelable(OfferItem.class.getClassLoader());
        Product = in.readParcelable(ProductBO.class.getClassLoader());
        Package = in.readParcelable(app.com.dunkeydelivery.modules.deals.items.Package.class.getClassLoader());
        ItemType = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(OfferId);
        dest.writeString(ProductId);
        dest.writeString(OfferProductId);
        dest.writeString(Description);
        dest.writeString(Price);
        dest.writeString(DiscountPercentage);
        dest.writeString(SlashPrice);
        dest.writeString(ImageUrl);
        dest.writeString(IsDeleted);
        dest.writeParcelable(Offer, flags);
        dest.writeParcelable(Product, flags);
        dest.writeParcelable(Package, flags);
        dest.writeInt(ItemType);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DealsItem> CREATOR = new Creator<DealsItem>() {
        @Override
        public DealsItem createFromParcel(Parcel in) {
            return new DealsItem(in);
        }

        @Override
        public DealsItem[] newArray(int size) {
            return new DealsItem[size];
        }
    };
}
