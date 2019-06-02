package io.defy.chicken.lover.view.custom;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

public class SquareLayout extends ConstraintLayout {

    public SquareLayout(Context context) {
        super(context);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int width, int height) {
        // note we are applying the width value as the height
        super.onMeasure(width, width);
    }

}