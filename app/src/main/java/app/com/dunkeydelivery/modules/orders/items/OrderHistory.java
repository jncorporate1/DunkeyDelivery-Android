package app.com.dunkeydelivery.modules.orders.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import app.com.dunkeydelivery.utils.EnumUtils;

public class OrderHistory implements Parcelable {

    @SerializedName("Id")
    Integer Id;


    @SerializedName("OrderDateTime")
    String OrderDateTime;

    @SerializedName("DeliveryTime_From")
    String DeliveryTime_From;

    @SerializedName("DeliveryTime_To")
    String DeliveryTime_To;

    @SerializedName("MaxDeliveryTime")
    Integer MaxDeliveryTime;

    @SerializedName("PaymentMethod")
    Integer PaymentMethod;

    @SerializedName("Subtotal")
    Double Subtotal;

    @SerializedName("ServiceFee")
    Double ServiceFee;

    @SerializedName("DeliveryFee")
    Double DeliveryFee;

    @SerializedName("Total")
    Double Total;

    @SerializedName("TipAmount")
    Double TipAmount;

    @SerializedName("User_ID")
    Integer User_ID;

    @SerializedName("OrderPayment_Id")
    Integer OrderPayment_Id;

    @SerializedName("DeliveryMan_Id")
    Integer DeliveryMan_Id;

    @SerializedName("UserFullName")
    String UserFullName;

    @SerializedName("TotalTaxDeducted")
    Integer TotalTaxDeducted;

    @SerializedName("DeliveryDetails_FirstName")
    String DeliveryDetails_FirstName;

    @SerializedName("DeliveryDetails_LastName")
    String DeliveryDetails_LastName;

    @SerializedName("DeliveryDetails_Phone")
    String DeliveryDetails_Phone;

    @SerializedName("DeliveryDetails_ZipCode")
    String DeliveryDetails_ZipCode;

    @SerializedName("DeliveryDetails_Email")
    String DeliveryDetails_Email;

    @SerializedName("DeliveryDetails_City")
    String DeliveryDetails_City;

    @SerializedName("DeliveryDetails_Address")
    String DeliveryDetails_Address;

    @SerializedName("DeliveryDetails_AddtionalNote")
    String DeliveryDetails_AddtionalNote;

    @SerializedName("Frequency")
    Integer Frequency;

    @SerializedName("StoreOrders")
    List<StoreOrder> storeOrders;

    boolean isExpanded;

    protected OrderHistory(Parcel in) {
        if (in.readByte() == 0) {
            Id = null;
        } else {
            Id = in.readInt();
        }
        OrderDateTime = in.readString();
        DeliveryTime_From = in.readString();
        DeliveryTime_To = in.readString();
        if (in.readByte() == 0) {
            MaxDeliveryTime = null;
        } else {
            MaxDeliveryTime = in.readInt();
        }
        if (in.readByte() == 0) {
            PaymentMethod = null;
        } else {
            PaymentMethod = in.readInt();
        }
        if (in.readByte() == 0) {
            Subtotal = null;
        } else {
            Subtotal = in.readDouble();
        }
        if (in.readByte() == 0) {
            ServiceFee = null;
        } else {
            ServiceFee = in.readDouble();
        }
        if (in.readByte() == 0) {
            DeliveryFee = null;
        } else {
            DeliveryFee = in.readDouble();
        }
        if (in.readByte() == 0) {
            Total = null;
        } else {
            Total = in.readDouble();
        }
        if (in.readByte() == 0) {
            TipAmount = null;
        } else {
            TipAmount = in.readDouble();
        }
        if (in.readByte() == 0) {
            User_ID = null;
        } else {
            User_ID = in.readInt();
        }
        if (in.readByte() == 0) {
            OrderPayment_Id = null;
        } else {
            OrderPayment_Id = in.readInt();
        }
        if (in.readByte() == 0) {
            DeliveryMan_Id = null;
        } else {
            DeliveryMan_Id = in.readInt();
        }
        UserFullName = in.readString();
        if (in.readByte() == 0) {
            TotalTaxDeducted = null;
        } else {
            TotalTaxDeducted = in.readInt();
        }
        if (in.readByte() == 0) {
            Frequency = null;
        } else {
            Frequency = in.readInt();
        }
        DeliveryDetails_FirstName = in.readString();
        DeliveryDetails_LastName = in.readString();
        DeliveryDetails_Phone = in.readString();
        DeliveryDetails_ZipCode = in.readString();
        DeliveryDetails_Email = in.readString();
        DeliveryDetails_City = in.readString();
        DeliveryDetails_Address = in.readString();
        DeliveryDetails_AddtionalNote = in.readString();
        isExpanded = in.readByte() != 0;
    }

    public static final Creator<OrderHistory> CREATOR = new Creator<OrderHistory>() {
        @Override
        public OrderHistory createFromParcel(Parcel in) {
            return new OrderHistory(in);
        }

        @Override
        public OrderHistory[] newArray(int size) {
            return new OrderHistory[size];
        }
    };

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getOrderDateTime() {
        return OrderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        OrderDateTime = orderDateTime;
    }

    public String getDeliveryTime_From() {
        return DeliveryTime_From;
    }

    public void setDeliveryTime_From(String deliveryTime_From) {
        DeliveryTime_From = deliveryTime_From;
    }

    public String getDeliveryTime_To() {
        return DeliveryTime_To;
    }

    public void setDeliveryTime_To(String deliveryTime_To) {
        DeliveryTime_To = deliveryTime_To;
    }

    public Integer getPaymentMethod() {
        if (PaymentMethod == null)
            return 0;
        return PaymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public Double getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(Double subtotal) {
        Subtotal = subtotal;
    }

    public Double getServiceFee() {
        return ServiceFee;
    }

    public void setServiceFee(Double serviceFee) {
        ServiceFee = serviceFee;
    }

    public Double getDeliveryFee() {
        return DeliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        DeliveryFee = deliveryFee;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public Double getTipAmount() {
        return TipAmount;
    }

    public void setTipAmount(Double tipAmount) {
        TipAmount = tipAmount;
    }

    public Integer getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(Integer user_ID) {
        User_ID = user_ID;
    }

    public Integer getOrderPayment_Id() {
        if (OrderPayment_Id == null)
            return 0;
        return OrderPayment_Id;
    }

    public void setOrderPayment_Id(Integer orderPayment_Id) {
        OrderPayment_Id = orderPayment_Id;
    }

    public Integer getDeliveryMan_Id() {
        return DeliveryMan_Id;
    }

    public void setDeliveryMan_Id(Integer deliveryMan_Id) {
        DeliveryMan_Id = deliveryMan_Id;
    }

    public String getUserFullName() {
        return UserFullName;
    }

    public void setUserFullName(String userFullName) {
        UserFullName = userFullName;
    }

    public Integer getTotalTaxDeducted() {
        return TotalTaxDeducted;
    }

    public void setTotalTaxDeducted(Integer totalTaxDeducted) {
        TotalTaxDeducted = totalTaxDeducted;
    }

    public String getDeliveryDetails_FirstName() {
        return DeliveryDetails_FirstName;
    }

    public void setDeliveryDetails_FirstName(String deliveryDetails_FirstName) {
        DeliveryDetails_FirstName = deliveryDetails_FirstName;
    }

    public String getDeliveryDetails_LastName() {
        return DeliveryDetails_LastName;
    }

    public void setDeliveryDetails_LastName(String deliveryDetails_LastName) {
        DeliveryDetails_LastName = deliveryDetails_LastName;
    }

    public String getDeliveryDetails_Phone() {
        return DeliveryDetails_Phone;
    }

    public void setDeliveryDetails_Phone(String deliveryDetails_Phone) {
        DeliveryDetails_Phone = deliveryDetails_Phone;
    }

    public String getDeliveryDetails_ZipCode() {
        return DeliveryDetails_ZipCode;
    }

    public void setDeliveryDetails_ZipCode(String deliveryDetails_ZipCode) {
        DeliveryDetails_ZipCode = deliveryDetails_ZipCode;
    }

    public String getDeliveryDetails_Email() {
        return DeliveryDetails_Email;
    }

    public void setDeliveryDetails_Email(String deliveryDetails_Email) {
        DeliveryDetails_Email = deliveryDetails_Email;
    }

    public String getDeliveryDetails_City() {
        return DeliveryDetails_City;
    }

    public void setDeliveryDetails_City(String deliveryDetails_City) {
        DeliveryDetails_City = deliveryDetails_City;
    }

    public String getDeliveryDetails_Address() {
        return DeliveryDetails_Address;
    }

    public void setDeliveryDetails_Address(String deliveryDetails_Address) {
        DeliveryDetails_Address = deliveryDetails_Address;
    }

    public String getDeliveryDetails_AddtionalNote() {
        if (DeliveryDetails_AddtionalNote != null)
            return DeliveryDetails_AddtionalNote;
        return "None";
    }

    public void setDeliveryDetails_AddtionalNote(String deliveryDetails_AddtionalNote) {
        DeliveryDetails_AddtionalNote = deliveryDetails_AddtionalNote;
    }

    public List<StoreOrder> getStoreOrders() {
        return storeOrders;
    }

    public void setStoreOrders(List<StoreOrder> storeOrders) {
        this.storeOrders = storeOrders;
    }


    public Integer getFrequency() {
        if (Frequency == null)
            return 0;
        return Frequency;
    }

    public String getFrequencyString() {
        return EnumUtils.CheckoutFrequency.getStringName(getFrequency());

    }

    public void setFrequency(Integer frequency) {
        Frequency = frequency;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expand) {
        isExpanded = expand;
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
        dest.writeString(OrderDateTime);
        dest.writeString(DeliveryTime_From);
        dest.writeString(DeliveryTime_To);
        if (PaymentMethod == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(PaymentMethod);
        }
        if (MaxDeliveryTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(MaxDeliveryTime);
        }
        if (Subtotal == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Subtotal);
        }
        if (ServiceFee == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(ServiceFee);
        }
        if (DeliveryFee == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(DeliveryFee);
        }
        if (Total == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Total);
        }
        if (TipAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(TipAmount);
        }
        if (User_ID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(User_ID);
        }
        if (OrderPayment_Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(OrderPayment_Id);
        }
        if (DeliveryMan_Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(DeliveryMan_Id);
        }
        dest.writeString(UserFullName);
        if (TotalTaxDeducted == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(TotalTaxDeducted);
        }
        if (Frequency == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Frequency);
        }
        dest.writeString(DeliveryDetails_FirstName);
        dest.writeString(DeliveryDetails_LastName);
        dest.writeString(DeliveryDetails_Phone);
        dest.writeString(DeliveryDetails_ZipCode);
        dest.writeString(DeliveryDetails_Email);
        dest.writeString(DeliveryDetails_City);
        dest.writeString(DeliveryDetails_Address);
        dest.writeString(DeliveryDetails_AddtionalNote);
        dest.writeByte((byte) (isExpanded ? 1 : 0));
    }

    public String getPaymentMethodString() {
        if (PaymentMethod == null || PaymentMethod == 0) {
            return "Credit Card";
        }
        return "PayPal";
    }

    public Integer getMaxDeliveryTime() {
        return MaxDeliveryTime;
    }

    public void setMaxDeliveryTime(Integer maxDeliveryTime) {
        MaxDeliveryTime = maxDeliveryTime;
    }

}
