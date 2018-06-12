package app.com.dunkeydelivery.modules.home.tabs.grocery.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.modules.home.items.SubCategoryBO;

/**
 * Created by Developer on 3/24/2016.
 */
public class SubCategoryItemAdapter extends
        RecyclerView.Adapter<SubCategoryItemAdapter.CategoryItemViewHolder> {

    private List<SubCategoryBO> subCategoryItems;
    CategoriesAdapter.ClickListeners clickListener;

    public SubCategoryItemAdapter(List<SubCategoryBO> subCategoryItems, CategoriesAdapter.ClickListeners clickListeners) {
        this.subCategoryItems = subCategoryItems;
        this.clickListener = clickListeners;
    }


    @Override
    public int getItemCount() {
        return subCategoryItems.size();
    }

    @Override
    public void onBindViewHolder(CategoryItemViewHolder itemViewHolder, int i) {
        itemViewHolder.getSubCategoryItemLabelView().setText(subCategoryItems.get(i).getName());
    }

    @Override
    public CategoryItemViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_subcategory_row, viewGroup, false);


        return new CategoryItemViewHolder(itemView);
    }


    public class CategoryItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSubCategoryItemLabel;

        public CategoryItemViewHolder(View itemView) {
            super(itemView);
            tvSubCategoryItemLabel = (TextView) itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null)
                        clickListener.onSubItem(subCategoryItems.get(getAdapterPosition()));
                }
            });
        }

        public TextView getSubCategoryItemLabelView() {
            return tvSubCategoryItemLabel;
        }
    }


}