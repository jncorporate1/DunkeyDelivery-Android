package app.com.dunkeydelivery.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 3/16/2018.
 */

public class DeliveryTypes implements Parcelable{

    @SerializedName("Id")
    String Id;
    @SerializedName("Type_Id")
    String Type_Id;
    @SerializedName("Type_Name")
    String Type_Name;
    @SerializedName("Store_Id")
    String Store_Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getType_Id() {
        if(TextUtils.isEmpty(Type_Id))
        {
            return "";
        }
        return Type_Id;
    }

    public void setType_Id(String type_Id) {
        Type_Id = type_Id;
    }

    public String getType_Name() {
        if(TextUtils.isEmpty(Type_Name))
        {
            return "";
        }
        return Type_Name;
    }

    public void setType_Name(String type_Name) {
        Type_Name = type_Name;
    }

    public String getStore_Id() {
        if(TextUtils.isEmpty(Store_Id))
        {
            return "";
        }
        return Store_Id;
    }

    public void setStore_Id(String store_Id) {
        Store_Id = store_Id;
    }

    protected DeliveryTypes(Parcel in) {
        Id = in.readString();
        Type_Id = in.readString();
        Type_Name = in.readString();
        Store_Id = in.readString();
    }

    public static final Creator<DeliveryTypes> CREATOR = new Creator<DeliveryTypes>() {
        @Override
        public DeliveryTypes createFromParcel(Parcel in) {
            return new DeliveryTypes(in);
        }

        @Override
        public DeliveryTypes[] newArray(int size) {
            return new DeliveryTypes[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Type_Id);
        dest.writeString(Type_Name);
        dest.writeString(Store_Id);
    }
}
