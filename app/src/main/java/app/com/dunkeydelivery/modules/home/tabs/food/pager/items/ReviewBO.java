package app.com.dunkeydelivery.modules.home.tabs.food.pager.items;

import com.google.gson.annotations.SerializedName;

import app.com.dunkeydelivery.items.UserBO;

/**
 * Created by Developer on 7/10/2017.
 */

public class ReviewBO {
    //    String title;
//    String orders;
    @SerializedName("Id")
    public int Id;
    @SerializedName("User_Id")
    public int user_id;
    @SerializedName("Store_Id")
    public int store_id;
    @SerializedName("Rating")
    public int rating;
    @SerializedName("Feedback")
    public String feedback;
    @SerializedName("DateOfRating")
    public String dateOfRating;

    @SerializedName("User")
    public UserBO userBO;

//    public ReviewBO(String title, String orders) {

    //    }
//        this.title = title;
//    public void setTitle(String title) {
//
//    }
//        return title;
//    public String getTitle() {
//
//    }
//        this.orders = orders;
//    public void setOrders(String orders) {
//
//    }
//        return orders;
//    public String getOrders() {
//
//    }
//        this.orders = orders;
//        this.title = title;
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDateOfRating() {
        if (dateOfRating == null)
            return "";
        return dateOfRating;
    }

    public void setDateOfRating(String dateOfRating) {
        this.dateOfRating = dateOfRating;
    }

    public String getFeedback() {
        if (feedback == null) {
            return "";
        }
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public UserBO getUserBO() {
        return userBO;
    }

    public void setUserBO(UserBO userBO) {
        this.userBO = userBO;
    }
}
