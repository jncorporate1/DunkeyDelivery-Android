package app.com.dunkeydelivery.modules.home.tabs.alcohol.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import app.com.dunkeydelivery.modules.home.items.ProductBO;

/**
 * Created by Developer on 1/31/2018.
 */

public class AlcoholSeeAllBO implements Parcelable {

    @SerializedName("Wine")
    ArrayList<Categories> wineSeeAll;

    @SerializedName("Liquor")
    ArrayList<Categories> liquorSeeAll;

    @SerializedName("Beer")
    ArrayList<Categories> beerSeeAll;


    public ArrayList<Categories> getWineSeeAll() {
        return wineSeeAll;
    }

    public void setWineSeeAll(ArrayList<Categories> wineSeeAll) {
        this.wineSeeAll = wineSeeAll;
    }

    public ArrayList<Categories> getLiquorSeeAll() {
        return liquorSeeAll;
    }

    public void setLiquorSeeAll(ArrayList<Categories> liquorSeeAll) {
        this.liquorSeeAll = liquorSeeAll;
    }

    public ArrayList<Categories> getBeerSeeAll() {
        return beerSeeAll;
    }

    public void setBeerSeeAll(ArrayList<Categories> beerSeeAll) {
        this.beerSeeAll = beerSeeAll;
    }

    protected AlcoholSeeAllBO(Parcel in) {
        wineSeeAll = in.createTypedArrayList(Categories.CREATOR);
        liquorSeeAll = in.createTypedArrayList(Categories.CREATOR);
        beerSeeAll = in.createTypedArrayList(Categories.CREATOR);
    }

    public static final Creator<AlcoholSeeAllBO> CREATOR = new Creator<AlcoholSeeAllBO>() {
        @Override
        public AlcoholSeeAllBO createFromParcel(Parcel in) {
            return new AlcoholSeeAllBO(in);
        }

        @Override
        public AlcoholSeeAllBO[] newArray(int size) {
            return new AlcoholSeeAllBO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(wineSeeAll);
        dest.writeTypedList(liquorSeeAll);
        dest.writeTypedList(beerSeeAll);
    }
}
