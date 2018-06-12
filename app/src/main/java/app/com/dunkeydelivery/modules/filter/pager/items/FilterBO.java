package app.com.dunkeydelivery.modules.filter.pager.items;

import android.text.TextUtils;

import java.util.List;

import app.com.dunkeydelivery.items.ItemBO;

/**
 * Created by Developer on 7/17/2017.
 */

public class FilterBO {

    String title;
    List<ItemBO> data;
    boolean isShowMore;

    public FilterBO(String title, List<ItemBO> data, boolean isShowMore) {
        this.title = title;
        this.data = data;
        this.isShowMore = isShowMore;
    }

    public boolean isShowMore() {
        return isShowMore;
    }

    public void setShowMore(boolean showMore) {
        isShowMore = showMore;
    }

    public String getTitle() {
        if(TextUtils.isEmpty(title))
        {
            return "";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ItemBO> getData() {
        return data;
    }

    public void setData(List<ItemBO> data) {
        this.data = data;
    }
}
