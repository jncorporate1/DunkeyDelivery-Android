package app.com.dunkeydelivery.modules.points.items;

import com.google.gson.annotations.SerializedName;

import app.com.dunkeydelivery.App;
import app.com.dunkeydelivery.utils.ImageUtils;

/**
 * Created by Developer on 1/15/2018.
 */

public class RewardPrize {

    @SerializedName("Id")
    Integer Id;
    @SerializedName("Name")
    String Name;
    @SerializedName("ImageUrl")
    String ImageUrl;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        if(Name==null)
        {
            return "";
        }
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getImageUrlDummy() {
        return ImageUtils.getBaseImageUrlDummy(App.context)+ ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
