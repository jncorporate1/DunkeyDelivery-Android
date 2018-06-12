package app.com.dunkeydelivery.modules.cart.fragments.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Developer on 1/11/2018.
 */

public class CheckOutSummary implements Serializable {

    @SerializedName("SubTotal")
    @Expose
    Double subTotal;
    @SerializedName("Total")
    @Expose
    Double total;
    @SerializedName("Tax")
    @Expose
    Double tax;
    @SerializedName("SubTotalWDF")
    @Expose
    Double SubTotalWDF;

    @SerializedName("DeliveryFee")
    @Expose
    Double DeliveryFee;

    @SerializedName("Tip")
    @Expose
    Double tip;

    public CheckOutSummary() {
        subTotal = 0.0;
        total = 0.0;
        tax = 0.0;
        tip = 0.0;


    }

    public double getSubTotal() {
        if (subTotal == null)
            return 0;
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTotal() {
        if (total == null)
        {
            return 0;
        }
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTax() {
        if (tax == 0)
            return 0;
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTip() {
        if (tip == null)
            return 0;
        return tip;
    }

    public void setTip(double tip) {
        this.tip = tip;
    }

    public double getDeliveryFee() {
        if (DeliveryFee == null)
            return 0;
        return DeliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        DeliveryFee = deliveryFee;
    }

    public double getSubTotalWDF() {
        if (SubTotalWDF == null)
            return 0;
        return SubTotalWDF;
    }

    public void setSubTotalWDF(Double subTotalWDF) {
        SubTotalWDF = subTotalWDF;
    }


}
