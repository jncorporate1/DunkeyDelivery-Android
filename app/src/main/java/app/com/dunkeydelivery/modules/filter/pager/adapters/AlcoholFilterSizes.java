package app.com.dunkeydelivery.modules.filter.pager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.interfaces.AlcoholFilterSize;
import app.com.dunkeydelivery.modules.filter.pager.items.FilterProductSizes;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomCheckBox;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Developer on 4/2/2018.
 */

public class AlcoholFilterSizes extends RecyclerView.Adapter {

    private ArrayList<FilterProductSizes> filterProductSizes;
    private AlcoholFilterSize alcoholFilterSize;
    private Context context;

    public AlcoholFilterSizes(ArrayList<FilterProductSizes> filterProductSizes, Context context, int filterCheckValue) {
        this.filterProductSizes = filterProductSizes;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.alcohol_filter_size,parent,false);
        return new AlcoholFilterSizesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AlcoholFilterSizesViewHolder alcoholFilterSizesViewHolder = (AlcoholFilterSizesViewHolder) holder;
        alcoholFilterSizesViewHolder.customCheckBox.setText(filterProductSizes.get(position).getNetWeight());
        if (filterProductSizes.get(position).isCheck()) {
            alcoholFilterSizesViewHolder.customCheckBox.setChecked(true);
        }
        else
        {
            alcoholFilterSizesViewHolder.customCheckBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return filterProductSizes.size();
    }

    class AlcoholFilterSizesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.customCheckBox)
        CustomCheckBox customCheckBox;

        public AlcoholFilterSizesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
            customCheckBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.customCheckBox)
            {
                alcoholFilterSize.onSizeClick(getAdapterPosition());
            }
        }
    }

    //initialize interface to implement click listener on radiobutton
    public void setAlcoholFilterSize(AlcoholFilterSize alcoholFilterSize)
    {
        this.alcoholFilterSize = alcoholFilterSize;
    }

    //set radio button checked or unchecked
    public void setRadioButtonStatus(int position)
    {
        for(FilterProductSizes filterProductSize:filterProductSizes) {
            try {
                if(filterProductSizes.indexOf(filterProductSize) == position) {
                    if (filterProductSize.isCheck()) {
                        filterProductSize.setCheck(false);
                    } else {
                        filterProductSize.setCheck(true);
                    }
                }
            }
            catch (Exception e)
            {
                LogUtils.i("mess",e.toString());
            }
        }
        notifyDataSetChanged();
    }

    public String getSelectedSize()
    {
        String sizes="";
        for (FilterProductSizes filterProductSize:filterProductSizes)
        {
            try {
                if (filterProductSize.isCheck()) {
                    sizes = sizes + filterProductSize.getNetWeight() + "#";
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return sizes;
    }

    public void resetSizes()
    {
        for (FilterProductSizes filterProductSize:filterProductSizes)
        {
            try {
                filterProductSizes.get(filterProductSizes.indexOf(filterProductSize)).setCheck(false);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        notifyDataSetChanged();
    }
}
