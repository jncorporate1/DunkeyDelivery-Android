package app.com.dunkeydelivery.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class PixelsOp {

	public static float convertSpToPixels(float sp, Context context) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
	}

	public static int convertToDensity(Context context, int pixels) {
		float scale = context.getResources().getDisplayMetrics().density;
		return ((int) (pixels * scale + 0.5f));
	}

	public static float dpFromPx(final Context context, final float px) {
		return px / context.getResources().getDisplayMetrics().density;
	}

	public static float pxFromDp(final Context context, final float dp) {
		return dp * context.getResources().getDisplayMetrics().density;
	}

	public static int getWidthOfScreen(Context context){
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int width = displayMetrics.widthPixels;
		return width;
	}


	
}
