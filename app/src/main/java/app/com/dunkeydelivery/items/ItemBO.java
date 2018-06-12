package app.com.dunkeydelivery.items;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Developer on 6/9/2017.
 */

public class ItemBO implements Parcelable {
    String title;
    String subTitle;

    public ItemBO(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.subTitle);
    }

    protected ItemBO(Parcel in) {
        this.title = in.readString();
        this.subTitle = in.readString();
    }

    public static final Parcelable.Creator<ItemBO> CREATOR = new Parcelable.Creator<ItemBO>() {
        @Override
        public ItemBO createFromParcel(Parcel source) {
            return new ItemBO(source);
        }

        @Override
        public ItemBO[] newArray(int size) {
            return new ItemBO[size];
        }
    };
}
