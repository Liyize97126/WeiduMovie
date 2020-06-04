package com.bw.movie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.bean.RegionList;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域列表数据适配器
 * 李易泽
 * 20200602
 */
public class RegionListAdapter extends RecyclerView.Adapter<RegionListAdapter.MyHolder> {
    //定义
    private List<RegionList> list = new ArrayList<>();
    private int index = -1;
    private ItemClick itemClick;
    public List<RegionList> getList() {
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
    public RegionListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.region_list_contents, parent, false);
        return new MyHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RegionListAdapter.MyHolder holder, final int position) {
        //获取并设置数据
        RegionList regionList = list.get(position);
        holder.regionName.setText(regionList.getRegionName());
        //效果选择
        if(position == index){
            //设置背景颜色
            holder.xuanCard.setBackgroundResource(R.drawable.region_list_selected_background);
            //设置字体粗体
            holder.regionName.getPaint().setFakeBoldText(true);
        } else {
            //设置背景颜色
            holder.xuanCard.setBackgroundResource(R.drawable.region_list_noselected_background);
            //取消字体粗体
            holder.regionName.getPaint().setFakeBoldText(false);
        }
        //回调
        holder.itemView.setTag(regionList.getRegionId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置选中状态
                setIndex(position);
                int regionId = (int) v.getTag();
                if(itemClick != null){
                    itemClick.setOnItemClick(regionId);
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
        protected TextView regionName;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            xuanCard = itemView.findViewById(R.id.xuan_card);
            regionName = itemView.findViewById(R.id.region_name);
        }
    }
    //回调
    public interface ItemClick {
        void setOnItemClick(int regionId);
    }
}
