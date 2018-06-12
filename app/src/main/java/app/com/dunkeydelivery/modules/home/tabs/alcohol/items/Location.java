package app.com.dunkeydelivery.modules.home.tabs.alcohol.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 1/16/2018.
 */

public class Location implements Parcelable{

    @SerializedName("Geography")
    Geography Geography;

    public Location(Geography geography) {
        Geography = geography;
    }

    public Geography getGeography() {
        return Geography;
    }

    public void setGeography(Geography geography) {
        Geography = geography;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.Geography, flags);
    }

    protected Location(Parcel in) {
        this.Geography = in.readParcelable(app.com.dunkeydelivery.modules.home.tabs.alcohol.items.Geography.class.getClassLoader());
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
