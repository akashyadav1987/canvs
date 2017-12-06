package com.canvas.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.canvas.utils.Utility;

/**
 * Created by akashyadav on 12/2/17.
 */

public class RobotoCondensedBoldTextView extends AppCompatTextView {

    public RobotoCondensedBoldTextView(Context context) {
        super(context);
    }

    public RobotoCondensedBoldTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RobotoCondensedBoldTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setTypeface(Utility.getFontRobotoCondensedBold());
    }
}
