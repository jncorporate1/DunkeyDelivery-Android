package app.com.dunkeydelivery.items;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 2/1/2018.
 */

public class SettingBO {
    @SerializedName("DeliveryFee")
    Integer DeliveryFee;
    @SerializedName("Currency")
    String Currency;
    @SerializedName("Point")
    Integer Point;
    @SerializedName("Tip")
    Integer Tip;
    @SerializedName("ContactNo")
    String ContactNo;

    public String getContactNo() {
        if(ContactNo==null)
        {
            return "";
        }
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public Integer getTip() {
        if (Tip == null)
            return 10;
        return Tip;
    }

    public void setTip(Integer tip) {
        Tip = tip;
    }

    public int getDeliveryFee() {
        if (DeliveryFee == null)
            return 0;
        return DeliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        DeliveryFee = deliveryFee;
    }

    public String getCurrency() {
        if (Currency == null) {
            return "";
        }
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public int getPoint() {
        if (Point == null) {
            return 20;
        }
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }
}
