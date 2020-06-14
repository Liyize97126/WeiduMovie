package com.bw.movie.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.bean.RecommendCinemasList;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 影院页推荐影院列表适配器
 * 李易泽
 * 20200530
 */
public class RecommendCinemasListAdapter extends RecyclerView.Adapter<RecommendCinemasListAdapter.MyViewHolder> {
    //定义
    private List<RecommendCinemasList> list = new ArrayList<>();
    private DataCallBack dataCallBack;
    public List<RecommendCinemasList> getList() {
        return list;
    }
    public void setDataCallBack(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }
    //方法实现
    @NonNull
    @Override
    public RecommendCinemasListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cinema_tjyy_list_contents, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecommendCinemasListAdapter.MyViewHolder holder, int position) {
        //获取并设置数据
        RecommendCinemasList recommendCinemasList = list.get(position);
        holder.name.setText(recommendCinemasList.getName());
        holder.address.setText(recommendCinemasList.getAddress());
        //Fresco图片渐进式加载
        ImageRequest build = ImageRequestBuilder.newBuilderWithSource(Uri.parse(recommendCinemasList.getLogo()))
                .setProgressiveRenderingEnabled(true).build();
        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(build)
                .build();
        holder.logo.setController(controller);
        //设置点击事件
        holder.itemView.setTag(recommendCinemasList.getId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断
                if(dataCallBack != null){
                    dataCallBack.dataCall((long) v.getTag());
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    //寄存器
    public class MyViewHolder extends RecyclerView.ViewHolder {
        //定义
        protected SimpleDraweeView logo;
        protected TextView name,address;
        protected MyViewHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
        }
    }
    //点击事件回调
    public interface DataCallBack{
        void dataCall(long id);
    }
}
