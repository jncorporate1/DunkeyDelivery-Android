package app.com.dunkeydelivery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.interfaces.ProductSizeSelection;
import app.com.dunkeydelivery.modules.home.items.ProductSizes;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomRadioButton;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Developer on 4/2/2018.
 */

public class AlcoholSizes extends RecyclerView.Adapter {

    private ArrayList<ProductSizes> list;
    private Context context;
    private int defaultPosition=0;
    private ArrayList<ProductSizes> productSizes;
    private ProductSizeSelection productSizeSelection;
    int lastSelectedPosition = -1;

    public AlcoholSizes(Context context, ArrayList<ProductSizes> productSizes) {
        this.context = context;
        this.productSizes = productSizes;
        this.list = productSizes;
    }

    public void setProductSizeSelection(ProductSizeSelection selection) {
        this.productSizeSelection = selection;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_price, parent, false);
        return new ProductSizesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        ProductSizesViewHolder productSizesViewHolder = (ProductSizesViewHolder) holder;
        if (lastSelectedPosition == position) {
            productSizesViewHolder.customRadioButton.setChecked(true);
        } else {
            productSizesViewHolder.customRadioButton.setChecked(false);
        }

        if(defaultPosition==0)
        {
            productSizesViewHolder.customRadioButton.setChecked(true);
            defaultPosition=-1;
        }
        productSizesViewHolder.customRadioButton.setText(list.get(position).getNetWeight());
        productSizesViewHolder.tvPrice.setText(Constants.CURRENCY_SYMBOL+list.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void seeMore(boolean showAll) {

        if (!showAll) {
            ArrayList<ProductSizes> temp = new ArrayList<>();
            for (ProductSizes productSize : productSizes) {
                temp.add(productSize);
                if (productSizes.indexOf(productSize) == 3) {
                    break;
                }
            }
            list = temp;
            notifyDataSetChanged();
        } else {
            list = productSizes;
            notifyDataSetChanged();

        }
    }

    class ProductSizesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.customRadioButton)
        CustomRadioButton customRadioButton;

        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ProductSizesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            customRadioButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.customRadioButton) {
                if (lastSelectedPosition != getAdapterPosition()) {
                    lastSelectedPosition = getAdapterPosition();
                    productSizeSelection.onRowClick(lastSelectedPosition);
                    notifyDataSetChanged();
                }
            }
        }
    }
}
