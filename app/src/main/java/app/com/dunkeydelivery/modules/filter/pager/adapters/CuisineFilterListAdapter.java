package app.com.dunkeydelivery.modules.filter.pager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.modules.filter.pager.items.CuisineItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by Developer on 6/2/2017.
 */

public class CuisineFilterListAdapter extends RecyclerView.Adapter {
    private List<CuisineItem> dataSet;
    Context mContext;
    public int lastSelectedPosition = -1;

    //constructor...
    public CuisineFilterListAdapter(List<CuisineItem> data, Context context) {
        this.dataSet = data;
        this.mContext = context;

    }

    class ItemTypeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rb_cuisine)
        RadioButton rb_cuisine;

        public ItemTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

//    private void updateCheckChanged(int adapterPosition) {
//        for (int i = 0; i < dataSet.size(); i++) {
//            if (i == adapterPosition)
//                dataSet.get(adapterPosition).setChecked(true);
//            else
//                dataSet.get(adapterPosition).setChecked(false);
//        }
//        notifyDataSetChanged();
//    }


    public void addItems(List<CuisineItem> streamBOs) {
        dataSet.addAll(streamBOs);
        notifyDataSetChanged();
    }

    public List<CuisineItem> getItems() {
        return dataSet;
    }

    public void addLoadingItem() {
        dataSet.add(null);
        notifyItemInserted(dataSet.size() - 1);
    }

    public void clearItems() {
        dataSet.clear();
    }

    public CuisineItem getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_cuisine, parent, false);
        return new ItemTypeViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        final CuisineItem itemBO = dataSet.get(listPosition);
        ItemTypeViewHolder itemTypeViewHolder = (ItemTypeViewHolder) holder;
        itemTypeViewHolder.rb_cuisine.setText(itemBO.getTag());
        itemTypeViewHolder.rb_cuisine.setChecked(dataSet.get(listPosition).isChecked());

        itemTypeViewHolder.rb_cuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataSet.get(listPosition).isChecked())
                    dataSet.get(listPosition).setChecked(false);
                else
                    dataSet.get(listPosition).setChecked(true);
                notifyDataSetChanged();
            }
        });

//        if (itemBO.isChecked()) {
//            itemTypeViewHolder.rb_cuisine.setBackgroundResource(R.drawable.rounded_btn_green);
//        } else {
//            itemTypeViewHolder.rb_cuisine.setBackgroundResource(R.drawable.rounded_btn_white_border);
//        }
    }

    public String getSelectedCusines() {
        String cuisines = "";
        for (CuisineItem item : dataSet) {
            if (item.isChecked()) {
                cuisines += item.getTag() + ",";
            }
        }
        if (cuisines.equals(""))
            return "";
        return cuisines.substring(0, cuisines.length() - 1);
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
        void onRowClick(CuisineItem item);

    }
}
