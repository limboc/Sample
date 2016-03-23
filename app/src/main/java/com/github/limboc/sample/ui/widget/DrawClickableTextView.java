package com.github.limboc.sample.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.github.limboc.sample.R;

public class DrawClickableTextView extends TextView {

    private Drawable arrowDown;
    private Drawable arrowUp;
    private boolean isOpen = false;
    private Typeface typeface;
    private OnTextViewListener listener;

    public DrawClickableTextView(Context context) {
        super(context);
        init(null);
    }

    public DrawClickableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DrawClickableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) public DrawClickableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.DrawClickableTextView,
                    0, 0);
            try {
                isOpen = a.getBoolean(R.styleable.DrawClickableTextView_isOpen, false);
            } finally {
                a.recycle();
            }
        }

        // Make sure to mutate so that if there are multiple password fields, they can have
        // different visibilities.
        arrowDown = ContextCompat.getDrawable(getContext(), R.mipmap.arrow_down).mutate();
        arrowUp = ContextCompat.getDrawable(getContext(), R.mipmap.arrow_up).mutate();
        setup();
    }

    protected void setup() {
        Drawable drawable = isOpen ? arrowUp : arrowDown;
        Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawable, drawables[3]);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {

            isOpen = !isOpen;
            setup();
            invalidate();
            if(listener != null){
                listener.onClick(isOpen);
            }
        }

        return super.onTouchEvent(event);
    }

    @Override public void setInputType(int type) {
        this.typeface = getTypeface();
        super.setInputType(type);
        setTypeface(typeface);
    }

    public interface OnTextViewListener{
        void onClick(boolean isOpen);
    }

    public void setOnTextViewListener(OnTextViewListener listener){
        this.listener = listener;
    }

}
