package app.com.dunkeydelivery.modules.orders.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import app.com.dunkeydelivery.App;
import app.com.dunkeydelivery.utils.ImageUtils;
    
public class StoreOrder implements Parcelable {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("OrderNo")
    String OrderNo;

    @SerializedName("OrderDeliveryTime")
    String OrderDeliveryTime;

    @SerializedName("Status")
    Integer Status;

    @SerializedName("Store_Id")
    Integer Store_Id;

    @SerializedName("Subtotal")
    Integer Subtotal;

    @SerializedName("Total")
    Integer Total;

    @SerializedName("Order_Id")
    Integer Order_Id;

    @SerializedName("StoreName")
    String StoreName;

    @SerializedName("ImageUrl")
    String ImageUrl;

    @SerializedName("ServiceFee")
    Integer ServiceFee;

    @SerializedName("DeliveryFee")
    Integer DeliveryFee;

    @SerializedName("OrderItems")
    List<StoreOrderItem> OrderItems;

    protected StoreOrder(Parcel in) {
        if (in.readByte() == 0) {
            Id = null;
        } else {
            Id = in.readInt();
        }
        OrderNo = in.readString();
        OrderDeliveryTime=in.readString();
        if (in.readByte() == 0) {
            Status = null;
        } else {
            Status = in.readInt();
        }
        if (in.readByte() == 0) {
            Store_Id = null;
        } else {
            Store_Id = in.readInt();
        }
        if (in.readByte() == 0) {
            Subtotal = null;
        } else {
            Subtotal = in.readInt();
        }
        if (in.readByte() == 0) {
            Total = null;
        } else {
            Total = in.readInt();
        }
        if (in.readByte() == 0) {
            Order_Id = null;
        } else {
            Order_Id = in.readInt();
        }
        if (in.readByte() == 0) {
            DeliveryFee = null;
        } else {
            DeliveryFee = in.readInt();
        }
        if (in.readByte() == 0) {
            ServiceFee = null;
        } else {
            ServiceFee = in.readInt();
        }
        StoreName = in.readString();
        ImageUrl = in.readString();
    }

    public static final Creator<StoreOrder> CREATOR = new Creator<StoreOrder>() {
        @Override
        public StoreOrder createFromParcel(Parcel in) {
            return new StoreOrder(in);
        }

        @Override
        public StoreOrder[] newArray(int size) {
            return new StoreOrder[size];
        }
    };

    public String getOrderDeliveryTime() {
        if(TextUtils.isEmpty(OrderDeliveryTime))
        {
            return "";
        }
        return OrderDeliveryTime;
    }

    public void setOrderDeliveryTime(String orderDeliveryTime) {
        OrderDeliveryTime = orderDeliveryTime;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Integer getStore_Id() {
        return Store_Id;
    }

    public void setStore_Id(Integer store_Id) {
        Store_Id = store_Id;
    }

    public Integer getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        Subtotal = subtotal;
    }

    public Integer getTotal() {
        return Total;
    }

    public void setTotal(Integer total) {
        Total = total;
    }

    public Integer getOrder_Id() {
        return Order_Id;
    }

    public void setOrder_Id(Integer order_Id) {
        Order_Id = order_Id;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getImageUrl() {
        return ImageUtils.getBaseImageUrlDummy(App.context) + ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public List<StoreOrderItem> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(List<StoreOrderItem> orderItems) {
        OrderItems = orderItems;
    }

    public Integer getServiceFee() {
        if (ServiceFee == null)
            return 0;
        return ServiceFee;
    }

    public void setServiceFee(Integer serviceFee) {
        ServiceFee = serviceFee;
    }

    public Integer getDeliveryFee() {
        if (DeliveryFee == null)
            return 0;
        return DeliveryFee;
    }

    public void setDeliveryFee(Integer deliveryFee) {
        DeliveryFee = deliveryFee;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Id);
        }
        dest.writeString(OrderNo);
        dest.writeString(OrderDeliveryTime);
        if (Status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Status);
        }
        if (Store_Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Store_Id);
        }
        if (Subtotal == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Subtotal);
        }
        if (Total == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Total);
        }
        if (Order_Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Order_Id);
        }
        if (DeliveryFee == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(DeliveryFee);
        }
        if (ServiceFee == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ServiceFee);
        }
        dest.writeString(StoreName);
        dest.writeString(ImageUrl);
    }
}
