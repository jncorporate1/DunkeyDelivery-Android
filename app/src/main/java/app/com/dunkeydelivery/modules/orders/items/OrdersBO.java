package app.com.dunkeydelivery.modules.orders.items;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Developer on 7/5/2017.
 */

public class OrdersBO implements Parcelable {

    String orderID;
    boolean isExpanded;

    public OrdersBO(String orderID, boolean isExpanded) {
        this.orderID = orderID;
        this.isExpanded = isExpanded;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expand) {
        isExpanded = expand;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderID);
        dest.writeByte(this.isExpanded ? (byte) 1 : (byte) 0);
    }

    protected OrdersBO(Parcel in) {
        this.orderID = in.readString();
        this.isExpanded = in.readByte() != 0;
    }

    public static final Parcelable.Creator<OrdersBO> CREATOR = new Parcelable.Creator<OrdersBO>() {
        @Override
        public OrdersBO createFromParcel(Parcel source) {
            return new OrdersBO(source);
        }

        @Override
        public OrdersBO[] newArray(int size) {
            return new OrdersBO[size];
        }
    };
}
