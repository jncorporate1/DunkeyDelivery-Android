package app.com.dunkeydelivery.modules.home.tabs.laundry.items;

/**
 * Created by Developer on 7/14/2017.
 */

public class LaundryItem {
    String title;
    int quantity = 0;
    int imResourceId = -1;
    int imResourceIdSelected = -1;

    public LaundryItem(String title, int quantity, int imResourceId, int imResourceIdSelected) {
        this.title = title;
        this.quantity = quantity;
        this.imResourceId = imResourceId;
        this.imResourceIdSelected = imResourceIdSelected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImResourceId() {
        return imResourceId;
    }

    public void setImResourceId(int imResourceId) {
        this.imResourceId = imResourceId;
    }

    public int getImResourceIdSelected() {
        return imResourceIdSelected;
    }

    public void setImResourceIdSelected(int imResourceIdSelected) {
        this.imResourceIdSelected = imResourceIdSelected;
    }
}
