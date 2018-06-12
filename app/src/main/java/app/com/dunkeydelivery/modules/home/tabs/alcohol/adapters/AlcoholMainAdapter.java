package app.com.dunkeydelivery.modules.home.tabs.alcohol.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.modules.home.items.ProductBO;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.items.Products;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.PixelsOp;
import butterknife.ButterKnife;

/**
 * Created by Developer on 1/16/2018.
 */

public class AlcoholMainAdapter extends RecyclerView.Adapter {

    private List<ProductBO> dataSet;
    Context mContext;
    private boolean isGrid;

    //constructor...
    public AlcoholMainAdapter(List<ProductBO> data, Context context, boolean isGrid) {
        this.dataSet = data;
        this.mContext = context;
        this.isGrid = isGrid;
    }

    class ItemTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvPrice;
        ImageView ivImage;
        View mParentFrame;

        public ItemTypeViewHolder(View itemView) {
            super(itemView);
            this.mParentFrame = itemView.findViewById(R.id.ll_root);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            this.ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
            this.mParentFrame.setOnClickListener(this);

            if(isGrid){
                float width = (float) (PixelsOp.getWidthOfScreen(mContext) / 3.5);
                mParentFrame.setLayoutParams(new  LinearLayout.LayoutParams((int) width,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_root:
                    if(clickListener != null)
                        clickListener.onRowClick(getAdapterPosition());
                    break;
            }
        }
    }


    public void addItems(List<ProductBO> streamBOs) {
        dataSet.addAll(streamBOs);
        notifyDataSetChanged();
    }

    public List<ProductBO> getItems() {
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

    public ProductBO getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alcohol, parent, false);
        return new ItemTypeViewHolder(view);


    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        ProductBO itemBO = dataSet.get(listPosition);
        ItemTypeViewHolder itemTypeViewHolder = (ItemTypeViewHolder) holder;
        itemTypeViewHolder.tvTitle.setText(itemBO.getName());
        itemTypeViewHolder.tvPrice.setText("Price "+ Constants.CURRENCY_SYMBOL+Double.parseDouble(itemBO.getPrice().substring(1)));
        ImageUtils.setCenterImage(itemBO.getImage(), itemTypeViewHolder.ivImage, mContext);
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
