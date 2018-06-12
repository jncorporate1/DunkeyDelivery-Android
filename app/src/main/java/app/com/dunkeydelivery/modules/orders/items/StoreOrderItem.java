package app.com.dunkeydelivery.modules.orders.items;

import com.google.gson.annotations.SerializedName;

import app.com.dunkeydelivery.App;
import app.com.dunkeydelivery.utils.ImageUtils;

public class StoreOrderItem {

    @SerializedName("Id")
    Integer Id;

    @SerializedName("ItemId")
    Integer ItemId;

    @SerializedName("Qty")
    Integer Qty;

    @SerializedName("StoreOrder_Id")
    Integer StoreOrder_Id;

    @SerializedName("Name")
    String Name;

    @SerializedName("Price")
    String Price;

    @SerializedName("Description")
    String Description;

    @SerializedName("ImageUrl")
    String ImageUrl;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getItemId() {
        return ItemId;
    }

    public void setItemId(Integer itemId) {
        ItemId = itemId;
    }

    public Integer getQty() {
        return Qty;
    }

    public void setQty(Integer qty) {
        Qty = qty;
    }

    public Integer getStoreOrder_Id() {
        return StoreOrder_Id;
    }

    public void setStoreOrder_Id(Integer storeOrder_Id) {
        StoreOrder_Id = storeOrder_Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return ImageUtils.getBaseImageUrlDummy( App.context) + ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
