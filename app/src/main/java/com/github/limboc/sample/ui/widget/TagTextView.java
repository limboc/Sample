package com.github.limboc.sample.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.github.limboc.sample.R;
import com.github.limboc.sample.ui.utils.DensityUtils;
import com.github.limboc.sample.utils.L;

/**
 * Created by Chen on 2016/6/27.
 */
public class TagTextView extends TextView{

    private Paint mPaint;
    private RectF mRectF;
    private int borderColor;
    private int borderWidth;
    private int backgroundColor;
    private int borderRadius;

    public TagTextView(Context context) {
        super(context);
        init(null);
    }

    public TagTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectF = new RectF();
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TagTextView, 0,
                0);
        borderColor = a.getColor(R.styleable.TagTextView_borderColor, 0);
        backgroundColor = a.getColor(R.styleable.TagTextView_backgroundColor, 0);
        borderWidth = DensityUtils.dp2px(getContext(), 1);
        borderRadius = DensityUtils.dp2px(getContext(), 10);
        setTextColor(borderColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.set(borderWidth, borderWidth, w - borderWidth, h - borderWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(borderColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(borderWidth);
        canvas.drawRoundRect(mRectF, borderRadius, borderRadius, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(backgroundColor);
        canvas.drawRoundRect(mRectF, borderRadius, borderRadius, mPaint);
    }
}
