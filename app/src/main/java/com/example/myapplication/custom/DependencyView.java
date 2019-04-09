package com.example.myapplication.custom;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class DependencyView extends AppCompatTextView {

    private float mSlop;//这个值越大，拖动效果越差
    private float mLastX;
    private float mLastY;

    public DependencyView(Context context) {
        this(context, null);
    }

    public DependencyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DependencyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //重写OnTouchEvent()方法必须要设置setClickable(true)
        setClickable(true);

        mSlop = ViewConfiguration.get(context).getScaledTouchSlop();

//        Log.e("mSlop", "slop=" + mSlop);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.e("ACTION_DOWN", "downX=" + event.getX());
                mLastX = event.getX();
                mLastY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
//                Log.e("ACTION_MOVE", "moveX=" + event.getX());
                int deltaX = (int) (event.getX() - mLastX);
                int deltaY = (int) (event.getY() - mLastY);
                if (Math.abs(deltaX) > mSlop || Math.abs(deltaY) > mSlop) {
                    ViewCompat.offsetLeftAndRight(this, deltaX);
                    ViewCompat.offsetTopAndBottom(this, deltaY);

                    mLastX = event.getX();
                    mLastY = event.getY();
                }

                break;

            case MotionEvent.ACTION_UP:
                mLastX = event.getX();
                mLastY = event.getY();

                break;

                default:
                    break;
        }
        return true;
    }
}
