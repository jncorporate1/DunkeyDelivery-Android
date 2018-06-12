package app.com.dunkeydelivery.modules.search.items;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Developer on 3/29/2016.
 */
public class AddressItem implements Parcelable {

    String formattedAddress; //formatted_address is the complete address which contains city state and zip and country used in Google Api
    String addressLine;
    String countryCode;
    String country;
    String city;
    String state;
    String stateCode;
    String zipCode;
    String locale;
    String featuredArea;
    String streetAddress;
    double latitude;
    double longitude;
    int addressType;
    String addressTypeName;

    public String getFormattedAddress() {
        if(formattedAddress != null)
            return formattedAddress;
        else
            return "";
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStateCode() {
        if(stateCode != null)
            return stateCode;
        else
            return "";
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public int getAddressType() {
        return addressType;
    }

    public void setAddressType(int addressType) {
        this.addressType = addressType;
    }

    public String getAddressTypeName() {
        return addressTypeName;
    }

    public void setAddressTypeName(String addressTypeName) {
        this.addressTypeName = addressTypeName;
    }

    public String getStreetAddress() {
        if(streetAddress != null)
            return streetAddress;
        else
            return "";
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getAddressLine() {
        if(addressLine != null)
            return addressLine;
        else
            return "";
    }

    public String getAddressLine1(){
        String addressLine1 = "";
        if (streetAddress != null) {
            addressLine1 = streetAddress;
        }

        if (addressLine != null && !addressLine.isEmpty()) {
            if(addressLine1.isEmpty()){
                addressLine1 = addressLine;
            } else{
                addressLine1 = addressLine1 + " " + addressLine;
            }
        }

        if(addressLine1.isEmpty()){
            addressLine1 = getFormattedAddress();
        }

        return addressLine1;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getZipCode() {
        if(zipCode != null)
            return zipCode;
        else
            return "";
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getFeaturedArea() {
        return featuredArea;
    }

    public void setFeaturedArea(String featuredArea) {
        this.featuredArea = featuredArea;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }


    public String getState() {
        if(state!=null)
        return state;
        else
            return "";
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        if(city!=null)
            return city;
        else
            return "";
    }


    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public AddressItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.formattedAddress);
        dest.writeString(this.addressLine);
        dest.writeString(this.countryCode);
        dest.writeString(this.country);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.stateCode);
        dest.writeString(this.zipCode);
        dest.writeString(this.locale);
        dest.writeString(this.featuredArea);
        dest.writeString(this.streetAddress);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeInt(this.addressType);
        dest.writeString(this.addressTypeName);
    }

    protected AddressItem(Parcel in) {
        this.formattedAddress = in.readString();
        this.addressLine = in.readString();
        this.countryCode = in.readString();
        this.country = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.stateCode = in.readString();
        this.zipCode = in.readString();
        this.locale = in.readString();
        this.featuredArea = in.readString();
        this.streetAddress = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.addressType = in.readInt();
        this.addressTypeName = in.readString();
    }



    public static final Creator<AddressItem> CREATOR = new Creator<AddressItem>() {
        @Override
        public AddressItem createFromParcel(Parcel source) {
            return new AddressItem(source);
        }

        @Override
        public AddressItem[] newArray(int size) {
            return new AddressItem[size];
        }
    };
}
