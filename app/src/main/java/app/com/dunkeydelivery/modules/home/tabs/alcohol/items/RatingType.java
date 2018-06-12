package app.com.dunkeydelivery.modules.home.tabs.alcohol.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 1/16/2018.
 */

public class RatingType implements Parcelable{

    @SerializedName("FiveStar")
    Integer FiveStar;

    @SerializedName("FourStar")
    Integer FourStar;

    @SerializedName("ThreeStar")
    Integer ThreeStar;

    @SerializedName("TwoStar")
    Integer TwoStar;

    @SerializedName("OneStar")
    Integer OneStar;

    public RatingType(Integer fiveStar, Integer fourStar, Integer threeStar, Integer twoStar, Integer oneStar) {
        FiveStar = fiveStar;
        FourStar = fourStar;
        ThreeStar = threeStar;
        TwoStar = twoStar;
        OneStar = oneStar;
    }

    public Integer getFiveStar() {
        return FiveStar;
    }

    public void setFiveStar(Integer fiveStar) {
        FiveStar = fiveStar;
    }

    public Integer getFourStar() {
        return FourStar;
    }

    public void setFourStar(Integer fourStar) {
        FourStar = fourStar;
    }

    public Integer getThreeStar() {
        return ThreeStar;
    }

    public void setThreeStar(Integer threeStar) {
        ThreeStar = threeStar;
    }

    public Integer getTwoStar() {
        return TwoStar;
    }

    public void setTwoStar(Integer twoStar) {
        TwoStar = twoStar;
    }

    public Integer getOneStar() {
        return OneStar;
    }

    public void setOneStar(Integer oneStar) {
        OneStar = oneStar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.FiveStar);
        dest.writeValue(this.FourStar);
        dest.writeValue(this.ThreeStar);
        dest.writeValue(this.TwoStar);
        dest.writeValue(this.OneStar);
    }

    protected RatingType(Parcel in) {
        this.FiveStar = (Integer) in.readValue(Integer.class.getClassLoader());
        this.FourStar = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ThreeStar = (Integer) in.readValue(Integer.class.getClassLoader());
        this.TwoStar = (Integer) in.readValue(Integer.class.getClassLoader());
        this.OneStar = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<RatingType> CREATOR = new Creator<RatingType>() {
        @Override
        public RatingType createFromParcel(Parcel source) {
            return new RatingType(source);
        }

        @Override
        public RatingType[] newArray(int size) {
            return new RatingType[size];
        }
    };
}
