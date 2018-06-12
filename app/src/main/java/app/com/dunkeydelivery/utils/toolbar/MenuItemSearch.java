package app.com.dunkeydelivery.utils.toolbar;

import android.text.TextWatcher;
import android.view.Menu;
import android.view.View.OnClickListener;

import app.com.dunkeydelivery.modules.search.items.AddressItem;

public class MenuItemSearch extends MenuItem{

    private TextWatcher textWatcher;
    private OnClickListener onETClickListener;
    private OnClickListener onIBClickListener;
    private String hintText;
    private AddressItem addressItem;
    private String text;
    private int resourceId = -1;

    public MenuItemSearch(String hint, String text, TextWatcher textWatcher,
                          OnClickListener onETClickListener, OnClickListener onIBClickListener) {
        hintText = hint;
        this.text = text;
        this.textWatcher = textWatcher;
        this.onETClickListener = onETClickListener;
        this.onIBClickListener = onIBClickListener;
    }

    public MenuItemSearch(String hint, AddressItem addressItem, TextWatcher textWatcher,
                          OnClickListener onETClickListener, OnClickListener onIBClickListener) {
        hintText = hint;
        this.addressItem = addressItem;
        this.textWatcher = textWatcher;
        this.onETClickListener = onETClickListener;
        this.onIBClickListener = onIBClickListener;
    }

    public MenuItemSearch(String str, int resourceId,  TextWatcher textWatcher,
                          OnClickListener onETClickListener, OnClickListener onIBClickListener) {
        hintText = str;
        this.textWatcher = textWatcher;
        this.resourceId = resourceId;
        this.onETClickListener = onETClickListener;
        this.onIBClickListener = onIBClickListener;
    }

    public AddressItem getAddressItem() {
        return addressItem;
    }

    public void setAddressItem(AddressItem addressItem) {
        this.addressItem = addressItem;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getHint() {
        return hintText;
    }

    public TextWatcher getTextWatcher() {
        return textWatcher;
    }

    public OnClickListener getOnETClickListener() {
        return onETClickListener;
    }

    public void setOnETClickListener(OnClickListener onETClickListener) {
        this.onETClickListener = onETClickListener;
    }

    public void setTitle(String title) {
        this.hintText = title;
    }

    public OnClickListener getOnIBClickListener() {
        return onIBClickListener;
    }

    public void setOnIBClickListener(OnClickListener onIBClickListener) {
        this.onIBClickListener = onIBClickListener;
    }
}
