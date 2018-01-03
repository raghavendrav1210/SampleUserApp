package com.tn.tnparty.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by raghav on 1/2/2018.
 */

public class FontAwesomeTextView extends AppCompatTextView {
    public FontAwesomeTextView(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        this.setTypeface(face);
    }

    public FontAwesomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        this.setTypeface(face);
    }

    public FontAwesomeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }

}
