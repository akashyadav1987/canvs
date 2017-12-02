package com.canvas.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.canvas.utils.Utility;

/**
 * Created by akashyadav on 12/2/17.
 */

public class RobotoRegularTextView extends AppCompatTextView {
    public RobotoRegularTextView(Context context) {
        super(context);
    }

    public RobotoRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RobotoRegularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setTypeface(Utility.getFontRobotoRegular());
    }
}
