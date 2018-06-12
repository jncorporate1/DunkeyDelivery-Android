package app.com.dunkeydelivery.modules.account.Items;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Developer on 12/22/2017.
 */

public class Address implements Serializable {
    @SerializedName("Id")
    int id;
    @SerializedName("FullAddress")
    String address;
    @SerializedName("Address2")
    String address2;
    @SerializedName("Frequency")
    String frequency;
//    @SerializedName("FullAddress")
    @SerializedName("City")
    String city;
    @SerializedName("State")
    String stateCode;
    @SerializedName("PostalCode")
    String zipCode;
    @SerializedName("Telephone")
    String phone;
    String addressType;

    public Address(String address, String address2, String city, String stateCode, String zipCode, String phone, String addressType) {
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.stateCode = stateCode;
        this.zipCode = zipCode;
        this.phone = phone;
        this.addressType = addressType;
    }

    public String getAddress2() {
        if(address2==null)
        {
            return "";
        }
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getFrequency() {
        if(frequency==null)
        {
            return "";
        }
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    public String getAddress() {
        if(address==null)
        {
            return "";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getCity()
    {
        if(city==null)
        {
            return "";
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode()
    {
        if(stateCode==null)
        {
            return "";
        }
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getZipCode()
    {
        if(zipCode==null)
        {
            return "";
        }
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone()
    {
        if(phone==null)
        {
            return "";
        }
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressType()
    {
        if(addressType==null)
        {
            return "";
        }
        return addressType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
}
