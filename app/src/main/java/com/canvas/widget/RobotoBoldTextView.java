package com.canvas.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.canvas.utils.Utility;

/**
 * Created by akashyadav on 12/2/17.
 */

public class RobotoBoldTextView extends AppCompatTextView {

    public RobotoBoldTextView(Context context) {
        super(context);
    }

    public RobotoBoldTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RobotoBoldTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setTypeface(Utility.getFontRobotoBoldr());
    }
}
