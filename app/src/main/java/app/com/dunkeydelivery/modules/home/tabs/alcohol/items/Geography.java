package app.com.dunkeydelivery.modules.home.tabs.alcohol.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 1/16/2018.
 */

public class Geography implements Parcelable{
    @SerializedName("CoordinateSystemId")
    Long coordinateSystemId;

    @SerializedName("WellKnownText")
    String wellKnownText;

    public Geography(Long coordinateSystemId, String wellKnownText) {
        this.coordinateSystemId = coordinateSystemId;
        this.wellKnownText = wellKnownText;
    }

    public Long getCoordinateSystemId() {
        return coordinateSystemId;
    }

    public void setCoordinateSystemId(Long coordinateSystemId) {
        this.coordinateSystemId = coordinateSystemId;
    }

    public String getWellKnownText() {
        return wellKnownText;
    }

    public void setWellKnownText(String wellKnownText) {
        this.wellKnownText = wellKnownText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.coordinateSystemId);
        dest.writeString(this.wellKnownText);
    }

    protected Geography(Parcel in) {
        this.coordinateSystemId = (Long) in.readValue(Long.class.getClassLoader());
        this.wellKnownText = in.readString();
    }

    public static final Creator<Geography> CREATOR = new Creator<Geography>() {
        @Override
        public Geography createFromParcel(Parcel source) {
            return new Geography(source);
        }

        @Override
        public Geography[] newArray(int size) {
            return new Geography[size];
        }
    };
}
