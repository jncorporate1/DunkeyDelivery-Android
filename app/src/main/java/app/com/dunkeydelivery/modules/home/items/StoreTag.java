package app.com.dunkeydelivery.modules.home.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 9/5/2017.
 */

public class StoreTag implements Parcelable {

    @SerializedName("Id")
    @Expose
    public Integer id;
    @SerializedName("Tag")
    @Expose
    public String tag;
    @SerializedName("Store_Id")
    @Expose
    public Integer storeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        if(!TextUtils.isEmpty(tag))
            return tag;
        else
            return "";
    }

    public void setTag(String tag) {
        this.tag = tag;
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
        dest.writeString(this.tag);
        dest.writeValue(this.storeId);
    }

    public StoreTag() {
    }

    protected StoreTag(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.tag = in.readString();
        this.storeId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<StoreTag> CREATOR = new Parcelable.Creator<StoreTag>() {
        @Override
        public StoreTag createFromParcel(Parcel source) {
            return new StoreTag(source);
        }

        @Override
        public StoreTag[] newArray(int size) {
            return new StoreTag[size];
        }
    };
}
