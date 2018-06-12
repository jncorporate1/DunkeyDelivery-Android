package app.com.dunkeydelivery.modules.cart.fragments.items;

import com.google.gson.annotations.SerializedName;

public class CartItem {

    @SerializedName("ItemId")
    Integer ItemId;
    @SerializedName("ItemType")
    Integer ItemType;
    @SerializedName("Qty")
    Integer Qty;
    @SerializedName("StoreId")
    Integer StoreId;

    public Integer getItemId() {
        return ItemId;
    }

    public void setItemId(Integer itemId) {
        ItemId = itemId;
    }

    public Integer getItemType() {
        return ItemType;
    }

    public void setItemType(Integer itemType) {
        ItemType = itemType;
    }

    public Integer getQty() {
        return Qty;
    }

    public void setQty(Integer qty) {
        Qty = qty;
    }

    public Integer getStoreId() {
        return StoreId;
    }

    public void setStoreId(Integer storeId) {
        StoreId = storeId;
    }
}
