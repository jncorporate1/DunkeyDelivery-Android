package app.com.dunkeydelivery.utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.modules.home.items.RatingsItem;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.items.StoreTag;

/**
 * Created by Developer on 9/11/2017.
 */

public class StoreUtils {

    public static void addStoreTags(Context context, FlowLayout flowLayout, List<StoreTag> storeTags) {

        try {
            flowLayout.removeAllViews();
            if (storeTags != null && storeTags.size() > 0) {

                for (StoreTag storeTag : storeTags) {
                    try {
                        View view = LayoutInflater.from(context).inflate(R.layout.item_tag, null);

                        TextView tvTag = (TextView) view.findViewById(R.id.tv_tag);
                        tvTag.setText(storeTag.getTag());
                        GradientDrawable bgShape = (GradientDrawable) tvTag.getBackground();
                        bgShape.setColor(ContextCompat.getColor(context, ImageUtils.tagColors[storeTags.indexOf(storeTag)]));
                        flowLayout.addView(view);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void addStoreRating(Context context, RatingsItem rating, LinearLayout ll_ratings) {
        for (int position = 5; position > 0; position--) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_rating, null);
            RatingBar ratingBar = view.findViewById(R.id.ratingbar);
            TextView tv_value = view.findViewById(R.id.tv_value);
            ProgressBar progressBar1 = view.findViewById(R.id.progressBar1);

            ratingBar.setmClickable(false);
            ratingBar.setStar(position);
            tv_value.setText(rating.getRatings(position) + "");
            progressBar1.setProgress(rating.getRatings(position) * 10);
            ll_ratings.addView(view);
        }
    }
}
