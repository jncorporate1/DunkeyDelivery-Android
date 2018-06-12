package app.com.dunkeydelivery.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;

/**
 * Created by Developer on 7/3/2017.
 */

public class ImageUtils {

    public static String getBaseImageUrl(Context context) {
        if (Constants.appDomain == EnumUtils.AppDomain.QA) {
            return context.getString(R.string.base_url_qa_image);
        } else if (Constants.appDomain == EnumUtils.AppDomain.DEV) {
            return context.getString(R.string.base_url_dev_image);
        } else if (Constants.appDomain == EnumUtils.AppDomain.LIVE) {
            return context.getString(R.string.base_url_live_image);
        }
        return context.getString(R.string.base_url_qa_image);
    }

    public static String getBaseImageUrlDummy(Context context) {
        if (Constants.appDomain == EnumUtils.AppDomain.QA) {
            return context.getString(R.string.base_url_qa_image);
        } else if (Constants.appDomain == EnumUtils.AppDomain.DEV) {
            return context.getString(R.string.base_url_dev_image);
        } else if (Constants.appDomain == EnumUtils.AppDomain.LIVE) {
            return context.getString(R.string.base_url_live_image);
        }
//        return context.getString(R.string.base_url_qa_image);
        return context.getString(R.string.base_url_dev_image);
    }

    public static int[] tagColors = {R.color.alcohol_selected_color,
            R.color.food_selected_color,
            R.color.grocery_selected_color,
            R.color.laundry_selected_color,
            R.color.retail_selected_color
    };

    public static void setRoundedImage(final Context context, String url, final ImageView imageView) {
        // url = getBaseImageUrl(context) + url;
        try {

            RequestOptions requestOptions = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

            Glide.with(context).asBitmap().load(url).apply(requestOptions).into(new BitmapImageViewTarget(imageView) {

                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    super.onResourceReady(resource, transition);

                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imageView.setImageDrawable(circularBitmapDrawable);
                }
            });
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
    }

    public static void setRoundedImage(final Context context, Uri url, final ImageView imageView) {
        try {

            RequestOptions requestOptions = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

            Glide.with(context).asBitmap().load(url).apply(requestOptions).into(new BitmapImageViewTarget(imageView) {

                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    super.onResourceReady(resource, transition);

                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imageView.setImageDrawable(circularBitmapDrawable);
                }
            });
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
    }


    public static void setImageCentered(final Context context, String url, final ImageView imageView) {
        try {
            Glide.with(context).load(url).into(imageView);
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
    }

    public static void setImageCentered(final Context context, String url, final ImageView imageView, int placeHolder) {
//        url = getBaseImageUrl(context) + url;
        try {

            RequestOptions requestOptions = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);
            Glide.with(context).load(url).apply(requestOptions).into(imageView);
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
    }

    /*helping method*/
    public static void setCenterImage(String imageUrl, final ImageView imv, final Context context) {
//        imageUrl = getBaseImageUrlDummy(context) + imageUrl;
        try {
            if (imageUrl != null) {

                final String finalImageUrl = imageUrl;

                RequestOptions requestOptions = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

                Glide.with(context).asBitmap().load(imageUrl).apply(requestOptions).into(new BitmapImageViewTarget(imv) {

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        super.onResourceReady(resource, transition);

                        final Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                RequestOptions requestOptions = new RequestOptions().fitCenter();
                                Glide.with(context).load(finalImageUrl).apply(requestOptions).into(imv);
                                handler.removeCallbacksAndMessages(null);
                            }
                        });
                    }
                });
            } else {
                imv.setImageResource(R.drawable.icon_user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /*helping method*/
    public static void setCenterImage(String imageUrl, final ImageView imv, final Context context, int placeHolder) {
//        imageUrl = getBaseImageUrl(context) + imageUrl;
        try {
            if (imageUrl != null) {
                final String finalImageUrl = imageUrl;

                LogUtils.i("mess",finalImageUrl);

                RequestOptions requestOptions = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

                Glide.with(context).asBitmap().load(imageUrl).apply(requestOptions)
                        .into(new BitmapImageViewTarget(imv) {

                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                super.onResourceReady(resource, transition);

                                final Handler handler = new Handler();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        RequestOptions requestOptions = new RequestOptions().fitCenter();
                                        Glide.with(context).load(finalImageUrl).apply(requestOptions).into(imv);
                                        handler.removeCallbacksAndMessages(null);
                                    }
                                });
                            }
                        });
            } else {
                imv.setImageResource(placeHolder);
            }
        } catch (Exception ex) {
            LogUtils.i("mess", "image set not    " + ex.toString());
        }
    }

    public static void setFitXYImage(String imageUrl, final ImageView imv, final Context context, int placeHolder) {
//        imageUrl = getBaseImageUrl(context) + imageUrl;
        try {
            if (imageUrl != null) {
                final String finalImageUrl = imageUrl;

                RequestOptions requestOptions = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

                Glide.with(context).asBitmap().load(imageUrl).apply(requestOptions).into(new BitmapImageViewTarget(imv) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        super.onResourceReady(resource, transition);
                    }
                });
            } else {
                imv.setImageResource(placeHolder);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void setImageCentered(final Context context, final Uri uri, final ImageView imageView, int placeHolder) {
        try {
            RequestOptions requestOptions = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

            Glide.with(context)
                    .asBitmap()

                    .load(uri)
                    .apply(requestOptions).
                    into(new BitmapImageViewTarget(imageView) {

                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            super.onResourceReady(resource, transition);

                            final Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    RequestOptions requestOptions = new RequestOptions().centerCrop();
                                    Glide.with(context).load(uri).
                                            apply(requestOptions).into(imageView);
                                    handler.removeCallbacksAndMessages(null);
                                }
                            });
                        }
                    });
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
    }
}
