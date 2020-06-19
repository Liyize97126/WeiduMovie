package com.bawei.xuanzuolibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 绘制圆点图
 * 李易泽
 * 20200614
 */
public class CirclePointView extends FrameLayout {
    //定义
    private int mColor;
    private Paint paint;
    //构造方法
    public CirclePointView(@NonNull Context context) {
        super(context);
        /* 设置可执行OnDraw方法
         * 默认情况下，出于性能考虑，会被设置成WILL_NOT_DROW，这样，ondraw就不会被执行了。
         * 如想重写ondraw方法，需要调用setWillNotDraw(false)*/
        setWillNotDraw(false);
    }
    public CirclePointView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public CirclePointView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //测量方法
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(90,90);
    }
    //绘图方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画笔
        paint = new Paint();
        //判断
        if(mColor != 0){
            //设置颜色
            paint.setColor(mColor);
        }
        //完成绘制
        canvas.drawCircle(getWidth()/2,getHeight()/2,30,paint);
    }
    //设置圆圈颜色方法
    public void setColor(int color){
        //颜色设置
        mColor = color;
        //刷新视图
        invalidate();
    }
}
