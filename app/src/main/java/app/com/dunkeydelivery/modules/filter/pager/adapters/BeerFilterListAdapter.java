package app.com.dunkeydelivery.modules.filter.pager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.items.ItemBO;

/**
 * Created by Developer on 6/2/2017.
 */

public class BeerFilterListAdapter extends RecyclerView.Adapter {
    private List<ItemBO> dataSet;
    Context mContext;
    int total_types;
    public final int VIEW_TYPE_ITEM = 0;

    //constructor...
    public BeerFilterListAdapter(List<ItemBO> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();

    }

    //this method is used to remove last 6 items from the list...
    public void removeLastItems() {
        if(dataSet.size() > 6){
            for(int i = dataSet.size()-1; i>=6; i--){
                dataSet.remove(i);
            }
            notifyDataSetChanged();
        }
    }

    class ItemTypeViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        CheckBox tvTitle;

        public ItemTypeViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = (CheckBox) itemView.findViewById(R.id.tv_title);
            this.tvTitle.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        }
    }



    public void addItems(List<ItemBO> streamBOs) {
        dataSet.addAll(streamBOs);
        notifyDataSetChanged();
    }

    public List<ItemBO> getItems() {
        return dataSet;
    }

    public void removeLoadingItem() {
        dataSet.remove(dataSet.size() - 1);
        notifyItemRemoved(dataSet.size());
    }

    public void addLoadingItem() {
        dataSet.add(null);
        notifyItemInserted(dataSet.size() - 1);
    }

    public void clearItems() {
        dataSet.clear();
    }

    public ItemBO getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        return new ItemTypeViewHolder(view);

    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        ItemBO itemBO = dataSet.get(listPosition);
        ItemTypeViewHolder itemTypeViewHolder = (ItemTypeViewHolder) holder;
        itemTypeViewHolder.tvTitle.setText(itemBO.getTitle());
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }



    /*Listeners*/
    ClickListeners clickListener;

    public void setClickListener(ClickListeners clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListeners {
        void onRowClick(int position);

    }

}
