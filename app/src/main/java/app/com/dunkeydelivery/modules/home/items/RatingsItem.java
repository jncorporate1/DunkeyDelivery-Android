package app.com.dunkeydelivery.modules.home.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 1/8/2018.
 */

public class RatingsItem implements Parcelable {
    @SerializedName("FiveStar")
    @Expose
    int fiveStar;
    @SerializedName("FourStar")
    @Expose
    int fourStar;
    @SerializedName("ThreeStar")
    @Expose
    int threeStar;
    @SerializedName("TwoStar")
    @Expose
    int twoStar;
    @SerializedName("OneStar")
    @Expose
    int oneStar;
    @SerializedName("TotalRatings")
    @Expose
    int totalStar;

    public RatingsItem() {
        oneStar = 0;
        twoStar = 0;
        threeStar = 0;
        fourStar = 0;
        fiveStar = 0;
        totalStar = 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeInt(this.fiveStar);
        parcel.writeInt(this.fourStar);
        parcel.writeInt(this.threeStar);
        parcel.writeInt(this.twoStar);
        parcel.writeInt(this.oneStar);
        parcel.writeInt(this.twoStar);
    }

    protected RatingsItem(Parcel in) {
        this.fiveStar = in.readInt();
        this.fourStar = in.readInt();
        this.threeStar = in.readInt();
        this.twoStar = in.readInt();
        this.oneStar = in.readInt();
        this.totalStar = in.readInt();
    }

    public static final Creator<RatingsItem> CREATOR = new Creator<RatingsItem>() {
        @Override
        public RatingsItem createFromParcel(Parcel source) {
            return new RatingsItem(source);
        }

        @Override
        public RatingsItem[] newArray(int size) {
            return new RatingsItem[size];
        }
    };

    public int getFiveStar() {
        return fiveStar;
    }

    public int getFourStar() {
        return fourStar;
    }

    public int getThreeStar() {
        return threeStar;
    }

    public int getTwoStar() {
        return twoStar;
    }

    public int getOneStar() {
        return oneStar;
    }

    public int getTotalStar() {
        return totalStar;
    }

    public int getRatings(int position) {
        switch (position) {
            case 1:
                return getOneStar();
            case 2:
                return getTwoStar();
            case 3:
                return getThreeStar();
            case 4:
                return getFourStar();
            case 5:
                return getFiveStar();
            default:
                return 0;
        }

    }
}
