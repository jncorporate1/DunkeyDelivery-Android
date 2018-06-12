package app.com.dunkeydelivery.modules.filter.pager.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 4/2/2018.
 */

public class FilterProductSizes implements Parcelable {

    @SerializedName("Unit")
    String Unit;

    @SerializedName("Size")
    String Size;

    @SerializedName("NetWeight")
    String NetWeight;

    @SerializedName("TypeID")
    String TypeID;

    @SerializedName("MainType")
    String MainType;

    boolean check;

    protected FilterProductSizes(Parcel in) {
        Unit = in.readString();
        Size = in.readString();
        check = in.readByte() != 0;
        NetWeight = in.readString();
        TypeID = in.readString();
        MainType = in.readString();
    }

    public static final Creator<FilterProductSizes> CREATOR = new Creator<FilterProductSizes>() {
        @Override
        public FilterProductSizes createFromParcel(Parcel in) {
            return new FilterProductSizes(in);
        }

        @Override
        public FilterProductSizes[] newArray(int size) {
            return new FilterProductSizes[size];
        }
    };

    public String getNetWeight() {
        if(TextUtils.isEmpty(NetWeight))
        {
            return "";
        }
        return NetWeight;
    }

    public void setNetWeight(String netWeight) {
        NetWeight = netWeight;
    }

    public String getType() {
        if(TextUtils.isEmpty(TypeID))
        {
            return "";
        }
        return TypeID;
    }

    public void setType(String type) {
        TypeID = type;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getUnit() {
        if(TextUtils.isEmpty(Unit))
        {
            return "";
        }
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getSize() {
        if(TextUtils.isEmpty(Size))
        {
            return "";
        }
        return Size;
    }

    public String getMainType() {
        if(TextUtils.isEmpty(MainType))
        {
            return "";
        }
        return MainType;
    }

    public void setMainType(String mainType) {
        MainType = mainType;
    }

    public void setSize(String size) {
        Size = size;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Unit);
        dest.writeString(Size);
        dest.writeString(NetWeight);
        dest.writeString(TypeID);
        dest.writeString(MainType);
        dest.writeByte((byte) (check ? 1 : 0));
    }
}
