package app.com.dunkeydelivery.modules.home.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 8/28/2017.
 */

public class StoreHourBO implements Parcelable {

    @SerializedName("Id")
    @Expose
    public Integer id;
    @SerializedName("Monday_From")
    @Expose
    public String mondayFrom;
    @SerializedName("Monday_To")
    @Expose
    public String mondayTo;
    @SerializedName("Tuesday_From")
    @Expose
    public String tuesdayFrom;
    @SerializedName("Tuesday_To")
    @Expose
    public String tuesdayTo;
    @SerializedName("Wednesday_From")
    @Expose
    public String wednesdayFrom;
    @SerializedName("Wednesday_To")
    @Expose
    public String wednesdayTo;
    @SerializedName("Thursday_From")
    @Expose
    public String thursdayFrom;
    @SerializedName("Thursday_To")
    @Expose
    public String thursdayTo;
    @SerializedName("Friday_From")
    @Expose
    public String fridayFrom;
    @SerializedName("Friday_To")
    @Expose
    public String fridayTo;
    @SerializedName("Saturday_From")
    @Expose
    public String saturdayFrom;
    @SerializedName("Saturday_To")
    @Expose
    public String saturdayTo;
    @SerializedName("Sunday_From")
    @Expose
    public String sundayFrom;
    @SerializedName("Sunday_To")
    @Expose
    public String sundayTo;
    @SerializedName("Store_Id")
    @Expose
    public Integer storeId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getMondayTime() {
        if(!TextUtils.isEmpty(mondayFrom) && !TextUtils.isEmpty(mondayTo))
            return mondayFrom + " to " + mondayTo;
        else
            return "Closed";
    }

    public String getTuesdayTime() {
        if(!TextUtils.isEmpty(tuesdayFrom) && !TextUtils.isEmpty(tuesdayTo))
            return tuesdayFrom + " to " + tuesdayTo;
        else
            return "Closed";
    }

    public String getWedTime() {
        if(!TextUtils.isEmpty(wednesdayFrom) && !TextUtils.isEmpty(wednesdayTo))
            return wednesdayFrom + " to " + wednesdayTo;
        else
            return "Closed";
    }

    public String getThursdayTime() {
        if(!TextUtils.isEmpty(thursdayFrom) && !TextUtils.isEmpty(thursdayTo))
            return thursdayFrom + " to " + thursdayTo;
        else
            return "Closed";
    }

    public String getFridayTime() {
        if(!TextUtils.isEmpty(fridayFrom) && !TextUtils.isEmpty(fridayTo))
            return fridayFrom + " to " + fridayTo;
        else
            return "Closed";
    }

    public String getSatTime() {
        if(!TextUtils.isEmpty(saturdayFrom) && !TextUtils.isEmpty(saturdayTo))
            return saturdayFrom + " to " + saturdayTo;
        else
            return "Closed";
    }

    public String getSunTime() {
        if(!TextUtils.isEmpty(sundayFrom) && !TextUtils.isEmpty(sundayTo))
            return sundayFrom + " to " + sundayTo;
        else
            return "Closed";
    }

    public String getMondayFrom() {
        return mondayFrom;
    }


    public void setMondayFrom(String mondayFrom) {
        this.mondayFrom = mondayFrom;
    }

    public String getMondayTo() {
        return mondayTo;
    }

    public void setMondayTo(String mondayTo) {
        this.mondayTo = mondayTo;
    }

    public String getTuesdayFrom() {
        return tuesdayFrom;
    }

    public void setTuesdayFrom(String tuesdayFrom) {
        this.tuesdayFrom = tuesdayFrom;
    }

    public String getTuesdayTo() {
        return tuesdayTo;
    }

    public void setTuesdayTo(String tuesdayTo) {
        this.tuesdayTo = tuesdayTo;
    }

    public String getWednesdayFrom() {
        return wednesdayFrom;
    }

    public void setWednesdayFrom(String wednesdayFrom) {
        this.wednesdayFrom = wednesdayFrom;
    }

    public String getWednesdayTo() {
        return wednesdayTo;
    }

    public void setWednesdayTo(String wednesdayTo) {
        this.wednesdayTo = wednesdayTo;
    }

    public String getThursdayFrom() {
        return thursdayFrom;
    }

    public void setThursdayFrom(String thursdayFrom) {
        this.thursdayFrom = thursdayFrom;
    }

    public String getThursdayTo() {
        return thursdayTo;
    }

    public void setThursdayTo(String thursdayTo) {
        this.thursdayTo = thursdayTo;
    }

    public String getFridayFrom() {
        return fridayFrom;
    }

    public void setFridayFrom(String fridayFrom) {
        this.fridayFrom = fridayFrom;
    }

    public String getFridayTo() {
        return fridayTo;
    }

    public void setFridayTo(String fridayTo) {
        this.fridayTo = fridayTo;
    }

    public String getSaturdayFrom() {
        return saturdayFrom;
    }

    public void setSaturdayFrom(String saturdayFrom) {
        this.saturdayFrom = saturdayFrom;
    }

    public String getSaturdayTo() {
        return saturdayTo;
    }

    public void setSaturdayTo(String saturdayTo) {
        this.saturdayTo = saturdayTo;
    }

    public String getSundayFrom() {
        return sundayFrom;
    }

    public void setSundayFrom(String sundayFrom) {
        this.sundayFrom = sundayFrom;
    }

    public String getSundayTo() {
        return sundayTo;
    }

    public void setSundayTo(String sundayTo) {
        this.sundayTo = sundayTo;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.mondayFrom);
        dest.writeString(this.mondayTo);
        dest.writeString(this.tuesdayFrom);
        dest.writeString(this.tuesdayTo);
        dest.writeString(this.wednesdayFrom);
        dest.writeString(this.wednesdayTo);
        dest.writeString(this.thursdayFrom);
        dest.writeString(this.thursdayTo);
        dest.writeString(this.fridayFrom);
        dest.writeString(this.fridayTo);
        dest.writeString(this.saturdayFrom);
        dest.writeString(this.saturdayTo);
        dest.writeString(this.sundayFrom);
        dest.writeString(this.sundayTo);
        dest.writeValue(this.storeId);
    }

    public StoreHourBO() {
    }

    protected StoreHourBO(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mondayFrom = in.readString();
        this.mondayTo = in.readString();
        this.tuesdayFrom = in.readString();
        this.tuesdayTo = in.readString();
        this.wednesdayFrom = in.readString();
        this.wednesdayTo = in.readString();
        this.thursdayFrom = in.readString();
        this.thursdayTo = in.readString();
        this.fridayFrom = in.readString();
        this.fridayTo = in.readString();
        this.saturdayFrom = in.readString();
        this.saturdayTo = in.readString();
        this.sundayFrom = in.readString();
        this.sundayTo = in.readString();
        this.storeId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<StoreHourBO> CREATOR = new Parcelable.Creator<StoreHourBO>() {
        @Override
        public StoreHourBO createFromParcel(Parcel source) {
            return new StoreHourBO(source);
        }

        @Override
        public StoreHourBO[] newArray(int size) {
            return new StoreHourBO[size];
        }
    };
}
