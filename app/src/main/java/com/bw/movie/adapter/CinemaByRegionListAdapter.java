package com.bw.movie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.bean.CinemaByRegionList;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域查询影院列表数据适配器
 * 李易泽
 * 20200602
 */
public class CinemaByRegionListAdapter extends RecyclerView.Adapter<CinemaByRegionListAdapter.MyHolder> {
    //定义
    private List<CinemaByRegionList> list = new ArrayList<>();
    private int index = -1;
    private ItemClick itemClick;
    public List<CinemaByRegionList> getList() {
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
    public CinemaByRegionListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cinema_by_region_list_contents, parent, false);
        return new MyHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CinemaByRegionListAdapter.MyHolder holder, final int position) {
        //获取并设置数据
        CinemaByRegionList cinemaByRegionList = list.get(position);
        holder.name.setText(cinemaByRegionList.getName());
        //效果选择
        if(position == index){
            //设置背景颜色
            holder.xuanCard.setBackgroundResource(R.drawable.cinema_by_region_list_selected_background);
            //设置字体粗体
            holder.name.getPaint().setFakeBoldText(true);
        } else {
            //设置背景颜色
            holder.xuanCard.setBackgroundResource(R.drawable.cinema_by_region_list_noselected_background);
            //取消字体粗体
            holder.name.getPaint().setFakeBoldText(false);
        }
        //回调
        holder.itemView.setTag(cinemaByRegionList.getId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置选中状态
                setIndex(position);
                int id = (int) v.getTag();
                if(itemClick != null){
                    itemClick.setOnItemClick(id);
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
        protected TextView name;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            xuanCard = itemView.findViewById(R.id.xuan_card);
            name = itemView.findViewById(R.id.name);
        }
    }
    //回调
    public interface ItemClick {
        void setOnItemClick(int id);
    }
}
