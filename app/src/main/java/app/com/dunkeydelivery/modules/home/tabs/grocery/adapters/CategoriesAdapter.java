package app.com.dunkeydelivery.modules.home.tabs.grocery.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andexert.expandablelayout.library.ExpandableLayout;

import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.modules.home.items.SubCategoryBO;

/**
 * Created by Developer on 3/22/2016.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<SubCategoryBO> subCategoryBOs;
    private int mLastExpandedViewPosition = -1;
    public final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int visibleThreshold = 2;
    private RecyclerView recyclerView;
    private boolean isLoading;

    public CategoriesAdapter(Context context, List<SubCategoryBO> subCategoryBOs, RecyclerView recyclerView) {
        this.subCategoryBOs = subCategoryBOs;
        this.context = context;
        this.recyclerView = recyclerView;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItem, totalItemCount;
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategoryBOs.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vHolder, final int i) {

        if (vHolder instanceof MessagesExpandViewHolder) {

            final MessagesExpandViewHolder holder = (MessagesExpandViewHolder) vHolder;

//            if (subCategoryBOs.get(i).isExpanded()) {
//                holder.el.show();
//            } else {
//                holder.el.hide();
//            }

            holder.tvCategoryTitle.setText(subCategoryBOs.get(i).getName());
            holder.el.setOnExpandChangeListener(new ExpandableLayout.OnExpandChangeListener() {
                @Override
                public void elExpandChanged(boolean isExpanded) {
                    try {
                        if (isExpanded) {
                            if (recyclerView != null) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        recyclerView.smoothScrollToPosition(i);
                                    }
                                }, 200);
                            }
                        }
//                        ((ExpandableLayoutListener) myClickListener).onLayoutExpanded(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.rvSubItem.setHasFixedSize(false);
            LinearLayoutManager llm = new LinearLayoutManager(context);

            llm.setOrientation(LinearLayoutManager.VERTICAL);
            holder.rvSubItem.setLayoutManager(llm);
            if (subCategoryBOs.get(i).getSubCategories() != null && subCategoryBOs.get(i).getSubCategories().size() > 0) {
                holder.rvSubItem.setAdapter(new SubCategoryItemAdapter(subCategoryBOs.get(i).getSubCategories(),
                        clickListener));
            } else {
                holder.rvSubItem.setAdapter(null);
            }


        } else if (vHolder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) vHolder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_expandable_category, parent, false);
                return new MessagesExpandViewHolder(view);
            case VIEW_TYPE_LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item,
                        parent, false);
                return new LoadingViewHolder(view);

        }
        return null;
    }


    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }


    public void setLoaded() {
        isLoading = false;
    }


    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public void addItems(List<SubCategoryBO> newSubCategoryBOs) {
        subCategoryBOs.addAll(newSubCategoryBOs);
        notifyDataSetChanged();
    }

    public void setItems(List<SubCategoryBO> newSubCategoryBOs) {
        subCategoryBOs = newSubCategoryBOs;
        notifyDataSetChanged();
    }

    public List<SubCategoryBO> getItems() {
        return subCategoryBOs;
    }

    public void removeLoadingItem() {
        subCategoryBOs.remove(subCategoryBOs.size() - 1);
        notifyItemRemoved(subCategoryBOs.size());
    }

    public void addLoadingItem() {
        subCategoryBOs.add(null);
        notifyItemInserted(subCategoryBOs.size() - 1);
    }

    public void clearItems() {
        subCategoryBOs.clear();
    }

    public SubCategoryBO getItem(int position) {
        return subCategoryBOs.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return subCategoryBOs.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public int getLastExpandedViewPosition() {
        return mLastExpandedViewPosition;
    }

    public void setLastExpandedViewPosition(int lastPosition) {
        mLastExpandedViewPosition = lastPosition;
    }

    public class MessagesExpandViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout llroot;
        private TextView tvCategoryTitle;
        private ExpandableLayout el;

        //subitem views...
        private RecyclerView rvSubItem;

        public MessagesExpandViewHolder(View v) {
            super(v);
            llroot = (LinearLayout) v.findViewById(R.id.ll_root);
            tvCategoryTitle = (TextView) v.findViewById(R.id.tv_order_id);
            el = (ExpandableLayout) v.findViewById(R.id.el);
            el.setArrow((ImageView) v.findViewById(R.id.iv_arrow_up),
                    (ImageView) v.findViewById(R.id.iv_arrow_dn));
            llroot.setOnClickListener(this);

            //init subitem recycler view...
            rvSubItem = (RecyclerView) v.findViewById(R.id.rv_subitem);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.ll_root:
                    SubCategoryBO subCategoryBO = subCategoryBOs.get(getAdapterPosition());
                    if (subCategoryBO.getSubCategories() != null && subCategoryBO.getSubCategories().size() > 0) {
                        if (getLastExpandedViewPosition() != -1 && getLastExpandedViewPosition() != getLayoutPosition()
                                && subCategoryBOs.get(getLastExpandedViewPosition()).isExpanded()) {
                            subCategoryBOs.get(getLastExpandedViewPosition()).setExpanded(false);
                            notifyItemChanged(getLastExpandedViewPosition());
                        }

                        if (el.isOpened()) {
                            subCategoryBOs.get(getLayoutPosition()).setExpanded(false);
                            el.hide();
                        } else {
                            setLastExpandedViewPosition(getLayoutPosition());
                            subCategoryBOs.get(getLayoutPosition()).setExpanded(true);
                            el.show();
                        }
                    } else {
                        clickListener.onRowClick(subCategoryBOs.get(getAdapterPosition()));
                    }
                    break;
            }

        }
    }

    /*Listeners*/
    ClickListeners clickListener;

    public void setClickListener(ClickListeners clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListeners {
        void onRowClick(SubCategoryBO subCategoryBO);

        void onSubItem(SubCategoryBO subCategoryBO);
    }

    public interface ExpandableLayoutListener {
        public void onLayoutExpanded(int position);
    }
}

