package app.com.dunkeydelivery.modules.cart.fragments.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import app.com.dunkeydelivery.modules.account.Items.Address;
import app.com.dunkeydelivery.modules.account.Items.CardItem;

/**
 * Created by Developer on 1/9/2018.
 */

public class CheckoutItem implements Parcelable {
    @SerializedName("OrderSummary")
    @Expose
    CheckOutSummary summary;

    @SerializedName("points")
    @Expose
    public Integer points;
    @SerializedName("Address")
    @Expose
    public Address address;
    @SerializedName("CreditCard")
    @Expose
    CardItem cardItem;

    public CardItem getCardItem() {
        return cardItem;
    }

    public void setCardItem(CardItem cardItem) {
        this.cardItem = cardItem;
    }

    @SerializedName("Store")

    @Expose
    ArrayList<CartStore> storeArray;

    protected CheckoutItem(Parcel in) {
    }

    public static final Creator<CheckoutItem> CREATOR = new Creator<CheckoutItem>() {
        @Override
        public CheckoutItem createFromParcel(Parcel in) {
            return new CheckoutItem(in);
        }

        @Override
        public CheckoutItem[] newArray(int size) {
            return new CheckoutItem[size];
        }
    };

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<CartStore> getStoreArray() {
        return storeArray;
    }

    public void setStoreArray(ArrayList<CartStore> storeArray) {
        this.storeArray = storeArray;
    }

    public CheckOutSummary getSummary() {
        return summary == null ? new CheckOutSummary() : summary;
    }

    public void setSumamry(CheckOutSummary sumamry) {
        this.summary = sumamry;
    }

    public Integer getPoints() {
        if (points == null)
            return 0;
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
