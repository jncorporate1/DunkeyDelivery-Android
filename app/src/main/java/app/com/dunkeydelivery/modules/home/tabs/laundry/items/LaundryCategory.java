package app.com.dunkeydelivery.modules.home.tabs.laundry.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import app.com.dunkeydelivery.App;
import app.com.dunkeydelivery.utils.ImageUtils;

public class LaundryCategory implements Parcelable {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("Name")
    String Name;

    @SerializedName("Description")
    String Description;

    @SerializedName("Store_Id")
    Integer Store_Id;

    @SerializedName("ParentCategoryId")
    Integer ParentCategoryId;

    @SerializedName("ImageUrl")
    String ImageUrl;

    protected LaundryCategory(Parcel in) {
        if (in.readByte() == 0) {
            Id = null;
        } else {
            Id = in.readInt();
        }
        Name = in.readString();
        Description = in.readString();
        if (in.readByte() == 0) {
            Store_Id = null;
        } else {
            Store_Id = in.readInt();
        }
        if (in.readByte() == 0) {
            ParentCategoryId = null;
        } else {
            ParentCategoryId = in.readInt();
        }
        ImageUrl = in.readString();
    }

    public static final Creator<LaundryCategory> CREATOR = new Creator<LaundryCategory>() {
        @Override
        public LaundryCategory createFromParcel(Parcel in) {
            return new LaundryCategory(in);
        }

        @Override
        public LaundryCategory[] newArray(int size) {
            return new LaundryCategory[size];
        }
    };

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getStore_Id() {
        return Store_Id;
    }

    public void setStore_Id(Integer store_Id) {
        Store_Id = store_Id;
    }

    public Integer getParentCategoryId() {
        return ParentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        ParentCategoryId = parentCategoryId;
    }

    public String getImageUrl() {
        return ImageUtils.getBaseImageUrlDummy(App.context) + ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
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
        dest.writeString(Name);
        dest.writeString(Description);
        if (Store_Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Store_Id);
        }
        if (ParentCategoryId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ParentCategoryId);
        }
        dest.writeString(ImageUrl);
    }
}
