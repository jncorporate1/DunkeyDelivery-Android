package app.com.dunkeydelivery.modules.home.tabs.alcohol.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 1/17/2018.
 */

public class StoreTags implements Parcelable {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("Tag")
    String Tag;

    @SerializedName("Store_Id")
    Integer Store_Id;

    public StoreTags(Integer id, String tag, Integer store_Id) {
        Id = id;
        Tag = tag;
        Store_Id = store_Id;
    }

    protected StoreTags(Parcel in) {
        if (in.readByte() == 0) {
            Id = null;
        } else {
            Id = in.readInt();
        }
        Tag = in.readString();
        if (in.readByte() == 0) {
            Store_Id = null;
        } else {
            Store_Id = in.readInt();
        }
    }

    public static final Creator<StoreTags> CREATOR = new Creator<StoreTags>() {
        @Override
        public StoreTags createFromParcel(Parcel in) {
            return new StoreTags(in);
        }

        @Override
        public StoreTags[] newArray(int size) {
            return new StoreTags[size];
        }
    };

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public Integer getStore_Id() {
        return Store_Id;
    }

    public void setStore_Id(Integer store_Id) {
        Store_Id = store_Id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Id);
        }
        dest.writeString(Tag);
        if (Store_Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Store_Id);
        }
    }
}
