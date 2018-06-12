package app.com.dunkeydelivery.utils.customviews.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Selection;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CheckBox;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;

/**
 * Created by Developer on 4/22/2016.
 */
public class CustomCheckBox extends CheckBox {
    private static final String TAG = "EditText";

    public CustomCheckBox(Context context) {
        super(context);
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomCheckBoxButton);
        String customFont = a.getString(R.styleable.CustomCheckBoxButton_customFont4);
        setCustomFont(ctx, customFont);
        a.recycle();
    }


    public boolean setCustomFont(Context ctx, String fontName) {
        Typeface typeface = null;
        try {
            if(fontName == null){
                fontName = Constants.DEFAULT_FONT_NAME_FOR_ET;
            }
            typeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/" + fontName);
        } catch (Exception e) {
            Log.e(TAG, "Unable to load typeface: "+e.getMessage());
            return false;
        }

        setTypeface(typeface);

        return true;
    }

    protected void setSpan_internal(Object span, int start, int end, int flags) {
        final int textLength = getText().length();
        ((Editable) getText()).setSpan(span, start, Math.min(end, textLength), flags);
    }

    protected void setCursorPosition_internal(int start, int end) {
        final int textLength = getText().length();
        Selection.setSelection(((Editable) getText()), Math.min(start, textLength), Math.min(end, textLength));
    }

}
