package com.example.myapplication.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.custom.DependencyView;

/**
 * 自定义一个 行为
 *
 * 作用：Behavior 本身是一个抽象类，它的实现类都是为了能够让用户作用在一个 View 上进行拖拽、滑动、快速滑动等手势
 *
 * Behavior有3种方式设置给 child View
 * 1、xml设置 对应属性是 app:layout_behavior  值为具体Behavior实现类的完整类名 com.example.myapplication.behavior.MyBehavior
 *
 * 2、通过代码设置 lp = child.getLayoutParams()强制转换为CoordinatorLayout.LayoutParams  lp.setBehavior(new MyBehavior())
 *
 * 3、通过注解， 在自定义View上 注解@CoordinatorLayout.DefaultBehavior(MyBehavior.class)，如AppBarLayout的注解
 *
 *
 * child依赖dependency 并非是一对一的关系，可能是一对多，也可能是多对多
 */
public class MyBehavior extends CoordinatorLayout.Behavior<View> {

    private static final String TAG = "MyBehavior";

    public MyBehavior() {
    }

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 重写该方法 如果 dependency是 DependencyView 则返回true
     *
     * @param parent
     * @param child
     * @param dependency
     * @return true 表示child 依赖 dependency 来布局
     */
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
//        return dependency instanceof DependencyView;

        //behavior的滑动响应不需要依赖绑定
        return false;
    }

    /**
     * 该方法 决定了 child 将以怎样的 改变 依赖 dependency
     * @param parent
     * @param child
     * @param dependency
     * @return  有变化返回true  无变化返回false
     */
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        float x;
        float y;

        x = dependency.getX();

        //一对多关系， 如果是TextView则在dependency的上面， 否则在下面
        if (child instanceof TextView) {
            y = dependency.getTop() - child.getHeight() - 5;
        } else {
            y = dependency.getBottom() + 50;
        }


        child.setX(x);
        child.setY(y);

        return true;
    }



    //-----------------  Behavior 对滑动事件的响应  ---------------------


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        Log.e(TAG, "onStartNestedScroll: axes=" + axes + "--type=" + type);

        if (child instanceof ImageView && axes == View.SCROLL_AXIS_VERTICAL) {
            return true;
        }
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.e(TAG, "onNestedPreScroll: dx=" + dx + "--dy=" + dy + "--type=" + type);

        ViewCompat.offsetTopAndBottom(child, dy);

        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.e(TAG, "onNestedScroll");
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int type) {
        Log.e(TAG, "onStopNestedScroll");
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
    }

    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY) {
        Log.e(TAG, "onNestedPreFling");
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        Log.e(TAG, "onNestedFling");
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }


}
