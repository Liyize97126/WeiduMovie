package com.bw.movie.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bw.movie.R;

/**
 * 电影详情标签View
 * 李易泽
 * 20200612
 */
public class TabCardView extends FrameLayout {
    //构造方法实现
    public TabCardView(@NonNull Context context) {
        super(context);
    }
    public TabCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public TabCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //布局方法
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //获取父控件Width
        int width = getWidth();
        //设置控件上下左右间隔
        int viewMargin = 20;
        //子控件的宽度求和
        int sumChildWidth = viewMargin;
        //行数
        int rows  = 1;
        //循环
        for (int i = 0; i < getChildCount(); i++) {
            //获取当前控件
            View view = getChildAt(i);
            //判断：当每行总体的宽度小于父控件的宽度的时候，则把控件布局在本行，否则，行数加1
            if(sumChildWidth + view.getWidth() > width){
                rows ++;
                sumChildWidth = viewMargin;
            }
            //对控件重新进行布局
            view.layout(sumChildWidth,
                    view.getHeight() * (rows-1) + viewMargin * rows,
                    sumChildWidth + view.getWidth(),
                    view.getHeight() * rows + viewMargin * rows);
            //累计单行控件所有的宽度求和
            sumChildWidth = sumChildWidth + view.getWidth() + viewMargin;
        }
    }
    //生成动态添加控件方法
    public void addFlow(String wenben) {
        //根据我们的item，动态加载添加到流式布局中
        TextView textView = (TextView) View.inflate(getContext(), R.layout.view_tab_card, null);
        //设置文本
        textView.setText(wenben);
        //LayoutParams 动态布局
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //布局重绘
        textView.setLayoutParams(layoutParams);
        //把控件添加到父控件中
        addView(textView);
    }
}
