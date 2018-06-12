package app.com.dunkeydelivery.items;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import app.com.dunkeydelivery.App;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.ImageUtils;

/**
 * Created by Developer on 5/30/2017.
 */

public class UserBO {

    @SerializedName("Id")
    @Expose
    public String id;
    @SerializedName("FirstName")
    @Expose
    public String firstName;
    @SerializedName("LastName")
    @Expose
    public String lastName;
    @SerializedName("FullName")
    @Expose
    public String fullName;
    @SerializedName("Email")
    @Expose
    public String email;
    @SerializedName("Phone")
    @Expose
    public String phone;
    @SerializedName("Address")
    @Expose
    public String address;
    @SerializedName("City")
    @Expose
    public String city;
    @SerializedName("State")
    @Expose
    public String state;
    @SerializedName("Country")
    @Expose
    public String country;
    @SerializedName("Dob")
    @Expose
    public String dob;
    @SerializedName("Role")
    @Expose
    public String role;
    @SerializedName("Username")
    @Expose
    public String username;
    @SerializedName("Token")
    @Expose
    public TokenBO token;
    @SerializedName("ProfilePictureUrl")
    @Expose
    public String ProfilePictureUrl;

    @SerializedName("TotalReviews")
    @Expose
    public String totalReviews;

    @SerializedName("TotalOrders")
    @Expose
    public String totalOrders;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        if (!TextUtils.isEmpty(firstName))
            return firstName;
        else
            return "";
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        if (!TextUtils.isEmpty(lastName))
            return lastName;
        else
            return "";
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        if (!TextUtils.isEmpty(fullName))
            return fullName;
        else if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName))
            return firstName + " " + lastName;
        else
            return "";
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        if (!TextUtils.isEmpty(email)) {
            return email;
        } else {
            return "";
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        if (TextUtils.isEmpty(phone)) {
            return "";
        }
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        if (TextUtils.isEmpty(address)) {
            return "";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        if (TextUtils.isEmpty(city)) {
            return "";
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        if (TextUtils.isEmpty(state)) {
            return "";
        }
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        if (TextUtils.isEmpty(country)) {
            return "";
        }
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDob() {
        if (TextUtils.isEmpty(dob)) {
            return "";
        }
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRole() {
        if (TextUtils.isEmpty(role)) {
            return "";
        }
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        if (TextUtils.isEmpty(username)) {
            return "";
        }
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TokenBO getToken() {
        return token;
    }

    public void setToken(TokenBO token) {
        this.token = token;
    }

    public String getTotalReviews() {
        if (totalReviews == null)
            return "0";
        return totalReviews;
    }

    public void setTotalReviews(String totalReviews) {
        this.totalReviews = totalReviews;
    }

    public String getTotalOrders() {
        if (TextUtils.isEmpty(totalOrders)) {
            return "";
        }
        return totalOrders;
    }

    public void setTotalOrders(String totalOrders) {
        this.totalOrders = totalOrders;
    }

    public String getProfilePictureUrl() {
        String url = "";
        if (!TextUtils.isEmpty(role) && (Integer.parseInt(role) == EnumUtils.SignInType.getSignInType(EnumUtils.SignInType.Facebook) || Integer.parseInt(role) == EnumUtils.SignInType.getSignInType(EnumUtils.SignInType.Gmail)))
            url = ProfilePictureUrl;
        else if (ProfilePictureUrl.trim().startsWith("https") || ProfilePictureUrl.trim().startsWith("http"))
            url = ProfilePictureUrl;
        else
            url = ImageUtils.getBaseImageUrlDummy(App.context) + ProfilePictureUrl.trim();
        return url.trim();
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        ProfilePictureUrl = profilePictureUrl;
    }
}
