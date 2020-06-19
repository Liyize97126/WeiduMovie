package com.bawei.xuanzuolibrary;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bawei.xuanzuolibrary.bean.SeatBean;
import com.bawei.xuanzuolibrary.bean.SeatInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 选座视图框架
 * 李易泽
 * 20200614
 */
public class XZView extends FrameLayout {
    //定义成员变量
    private int mRow = 1;
    private List<SeatInfo> seatInfoList = new ArrayList<>();
    private List<SeatInfo> usedSeatInfoList = new ArrayList<>();
    private DataCallBack dataCallBack;
    //封装
    public void setDataCallBack(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }
    //构造函数
    public XZView(@NonNull Context context) {
        super(context);
    }
    public XZView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public XZView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //测量方法
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
    }
    //布局方法
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //定义
        int vTop = 0;//上边距
        int vLeft = 0;//左边距
        int vRight;//右边距
        int vBottom = 0;//下边距
        //获取子控件的数量
        int childCount = getChildCount();
        //获取屏幕宽度
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        //循环定位
        for (int i = 0; i < childCount; i++) {
            //获取当前子控件
            View view = getChildAt(i);
            //测量当前子控件
            view.measure(0,0);
            //获取控件宽度
            int measuredWidth = view.getMeasuredWidth();
            //获取控件高度
            int measuredHeight = view.getMeasuredHeight();
            //设置右边值
            vRight = vLeft + measuredWidth;
            //获取行数
            int row = seatInfoList.get(i).getRow();
            //判断
            if(!(row == mRow)){
                //行数切换
                mRow = row;
                vLeft = 0;
                vRight = vLeft + measuredWidth;
                vTop = vBottom;
            }
            //设置下边值
            vBottom = vTop + measuredHeight;
            //完成定位
            view.layout(vLeft,vTop,vRight,vBottom);
            //设置左边值
            vLeft = vRight;
        }
    }
    //添加座位方法
    public void addSeat(final Context context, List<SeatBean> list){
        //清空集合数据
        usedSeatInfoList.clear();
        //循环添加座位
        for (int i = 0; i < list.size(); i++) {
            //获取子视图
            final CirclePointView circlePointView = new CirclePointView(context);
            //获取状态
            final int status = list.get(i).getStatus();
            //获取行数
            String row = list.get(i).getRow();
            //获取座位号
            String seat = list.get(i).getSeat();
            //填写座位信息
            SeatInfo seatInfo = new SeatInfo(Integer.parseInt(row), Integer.parseInt(seat));
            //向集合添加数据
            seatInfoList.add(seatInfo);
            //判断状态
            switch (status){
                case 1:{
                    circlePointView.setColor(Color.parseColor("#FFFFFF"));
                }break;
                case 2:{
                    circlePointView.setColor(Color.parseColor("#E8BE1A"));
                }break;
            }
            //添加视图
            addView(circlePointView);
            //点击事件
            circlePointView.setTag(list.get(i));
            circlePointView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //判断
                    if(status == 1){
                        //获取座位选中状态
                        SeatBean seatBean = (SeatBean) v.getTag();
                        //判断
                        if(!seatBean.isChoose()){
                            //修改选中状态
                            circlePointView.setColor(Color.parseColor("#E81A5F"));
                            seatBean.setChoose(true);
                            //设置回调
                            if(dataCallBack != null){
                                //添加记录
                                usedSeatInfoList.add(new SeatInfo(Integer.parseInt(seatBean.getRow()),Integer.parseInt(seatBean.getSeat())));
                                dataCallBack.dataCall(usedSeatInfoList);
                            }
                        } else {
                            //修改选中状态
                            circlePointView.setColor(Color.parseColor("#FFFFFF"));
                            seatBean.setChoose(false);
                            //设置回调
                            if(dataCallBack != null){
                                //删除记录
                                for (int j = 0; j < usedSeatInfoList.size(); j++) {
                                    //判断
                                    if((usedSeatInfoList.get(j).getRow() == Integer.parseInt(seatBean.getRow()))
                                            && (usedSeatInfoList.get(j).getSeat() == Integer.parseInt(seatBean.getSeat()))){
                                        //执行删除
                                        usedSeatInfoList.remove(j);
                                        j--;
                                    }
                                }
                                dataCallBack.dataCall(usedSeatInfoList);
                            }
                        }
                    }
                }
            });
        }
    }
    //定义回调接口
    public interface DataCallBack{
        void dataCall(List<SeatInfo> usedSeatInfoList);
    }
}
