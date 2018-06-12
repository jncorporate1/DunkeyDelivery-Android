package app.com.dunkeydelivery.modules.filter.pager.items;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import butterknife.BindView;

/**
 * Created by Developer on 1/31/2018.
 */

public class CuisineItem implements Serializable {
    @SerializedName("Id")
    Integer Id;
    @SerializedName("Tag")
    String Tag;
    @SerializedName("TotalCount")
    Integer TotalCount;
    private boolean isChecked;

    public Integer getId() {
        if (Id == null)
            return 0;
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTag() {
        if (Tag == null || Tag.equals(""))
            return "";
        return Tag;

    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public Integer getTotalCount() {
        if (TotalCount == null)
            return 0;

        return TotalCount;
    }

    public void setTotalCount(Integer totalCount) {
        TotalCount = totalCount;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return this.isChecked;
    }
}
