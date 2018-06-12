package app.com.dunkeydelivery.modules.account.Items;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Developer on 12/22/2017.
 */
public class CardItem implements Serializable {
    @SerializedName("Id")
    int id;
    @SerializedName("CCNo")
    String cardNumber;
    @SerializedName("ExpiryDate")
    String expiryDate;
    @SerializedName("CCV")
    String cvv;
    @SerializedName("BillingCode")
    String zipCode;
    @SerializedName("Label")
    String label;
    @SerializedName("Is_Primary")
    int isPrimary;

    public CardItem(String cardNumber, String expiryDate, String cvv, String zipCode, String label, int isPrimary) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.zipCode = zipCode;
        this.label = label;
        this.isPrimary = isPrimary;
    }

    public String getCardNumber() {
        if (cardNumber == null) {
            return "";
        }
        return cardNumber;
    }

    public String getExpiryDate() {
        if (expiryDate == null) {
            return "";
        }
        return expiryDate;
    }

    public String getCvv() {
        if (cvv == null) {
            return "";
        }
        return cvv;
    }

    public String getZipCode() {
        if (zipCode == null) {
            return "";
        }
        return zipCode;
    }

    public String getLabel() {
        if (label == null) {
            return "";
        }
        return label;
    }

    public int isPrimary() {
        return isPrimary;
    }

    public void setPrimary(int isPrimary) {
        this.isPrimary = isPrimary;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
