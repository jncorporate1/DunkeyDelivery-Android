package app.com.dunkeydelivery.modules.home.tabs.pharmacy.items;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 9/15/2017.
 */

public class Medication {

    @SerializedName("Id")
    @Expose
    public Integer id;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Store_Id")
    @Expose
    public Integer storeId;
    @SerializedName("Size")
    @Expose
    public String size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
