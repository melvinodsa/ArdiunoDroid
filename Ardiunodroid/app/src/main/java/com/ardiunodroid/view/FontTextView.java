package com.ardiunodroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ardiunodroid.R;

/**
 * Created by hacker on 12/3/16.
 */
public class FontTextView extends TextView {


    public FontTextView(final Context context) {
        this(context, null);
    }

    public FontTextView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontTextView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        // prevent exception in Android Studio
        if (this.isInEditMode()) {
            return;
        }

        TypedArray array = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FontTextView, defStyle, 0);

        int fontRef = array.getResourceId(R.styleable.FontTextView_font, 0);

        if (fontRef != 0) {
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), getContext().getString(fontRef));
            setTypeface(font);
        }
        array.recycle();
    }
}
