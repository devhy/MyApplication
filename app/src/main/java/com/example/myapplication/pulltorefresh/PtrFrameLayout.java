package com.example.myapplication.pulltorefresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class PtrFrameLayout extends ViewGroup {

    private static final String TAG = PtrFrameLayout.class.getSimpleName();

    private static final int REFRESH_WIDTH = 50;
    private int mRefreshWidth;

    private View mRefresh;
    private View mTarget;

    private Scroller mScroller;

    private int mCurDraggerDimen;//当前拖动距离
    private int eventX;
    private int eventY;


    public PtrFrameLayout(Context context) {
        this(context, null);
    }

    public PtrFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScroller = new Scroller(context);

        mRefreshWidth = (int) (REFRESH_WIDTH * getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e(TAG, "onFinishInflate");

        mRefresh = getChildAt(0);

        mTarget = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mRefresh.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(mRefreshWidth, MeasureSpec.EXACTLY));

        mTarget.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //布局mRefresh mTarget
        if (mRefresh == null || mTarget == null) {
            return;
        }

        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();

        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();

        mRefresh.layout(childLeft, -mRefreshWidth - childTop, childWidth + childLeft, 0 - getPaddingBottom());

        mTarget.layout(childLeft, childTop, childWidth + childLeft, childHeight + childTop);
    }

    //这里负责拦截事件，拦截需要自己处理的事件，非自己处理的事件全部不拦截，传递给子View
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                eventX = (int) event.getX();
                eventY = (int) event.getY();

                break;
            case MotionEvent.ACTION_MOVE:

                int moveX = (int) (event.getX() + 0.5f);
                int moveY = (int) (event.getY() + 0.5f);

                int deltaX = moveX - eventX;
                int deltaY = moveY - eventY;

                if (Math.abs(deltaY) > Math.abs(deltaX) && Math.abs(getScrollY()) < (mRefreshWidth + 20)) {//加20是可以下拉超过刷新UI的高度
                    scrollBy(0, -deltaY);
                }

                eventX = moveX;
                eventY = moveY;


                break;
            case MotionEvent.ACTION_UP:

                if (Math.abs(getScrollY()) < mRefreshWidth) {
                    //如果滑动的距离小于这个值，则弹回初始布局
                    smoothScroll(getScrollY());
                } else {
                    //否则显示 加载中动画
                }

                break;
        }

        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        //回收资源
        super.onDetachedFromWindow();
    }

    private void smoothScroll(int dy) {
        mScroller.startScroll(0, dy, 0, -dy);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
