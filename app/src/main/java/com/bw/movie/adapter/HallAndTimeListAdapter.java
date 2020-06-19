package com.bw.movie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.bean.HallAndTimeList;

import java.util.ArrayList;
import java.util.List;

/**
 * 影厅选择列表适配器
 * 李易泽
 * 20200618
 */
public class HallAndTimeListAdapter extends RecyclerView.Adapter<HallAndTimeListAdapter.MyHolder> {
    //定义
    private List<HallAndTimeList> list = new ArrayList<>();
    private int index = -1;
    private ItemClick itemClick;
    public List<HallAndTimeList> getList() {
        return list;
    }
    public void setIndex(int index) {
        this.index = index;
        notifyDataSetChanged();
    }
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }
    //方法实现
    @NonNull
    @Override
    public HallAndTimeListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hall_and_time_list_contents, parent, false);
        return new MyHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull HallAndTimeListAdapter.MyHolder holder, final int position) {
        //获取并设置数据
        HallAndTimeList hallAndTimeList = list.get(position);
        holder.screeningHall.setText(hallAndTimeList.getScreeningHall());
        //时间转换
        String beginTime = hallAndTimeList.getBeginTime().substring(0, hallAndTimeList.getBeginTime().length()-3);
        String endTime = hallAndTimeList.getEndTime().substring(0, hallAndTimeList.getEndTime().length() - 3);
        holder.beginTime.setText(beginTime);
        holder.endTime.setText(endTime);
        //效果选择
        if(position == index){
            //设置背景颜色
            holder.xuanCard.setBackgroundResource(R.drawable.hall_and_time_list_background_selected);
        } else {
            //设置背景颜色
            holder.xuanCard.setBackgroundResource(R.drawable.hall_and_time_list_background);
        }
        //回调
        holder.itemView.setTag(hallAndTimeList.getHallId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置选中状态
                setIndex(position);
                int hallId = (int) v.getTag();
                if(itemClick != null){
                    itemClick.setOnItemClick(hallId);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    //寄存器
    public class MyHolder extends RecyclerView.ViewHolder {
        protected LinearLayout xuanCard;
        protected TextView screeningHall,beginTime,endTime;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            xuanCard = itemView.findViewById(R.id.xuan_card);
            screeningHall = itemView.findViewById(R.id.screening_hall);
            beginTime = itemView.findViewById(R.id.begin_time);
            endTime = itemView.findViewById(R.id.end_time);
        }
    }
    //回调
    public interface ItemClick {
        void setOnItemClick(int hallId);
    }
}
