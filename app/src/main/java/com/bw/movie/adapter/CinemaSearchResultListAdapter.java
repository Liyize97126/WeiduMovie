package com.bw.movie.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.bean.CinemaSearchResultList;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索页影院查询结果列表适配器
 * 李易泽
 * 20200613
 */
public class CinemaSearchResultListAdapter extends RecyclerView.Adapter<CinemaSearchResultListAdapter.MyHolder> {
    //定义
    private List<CinemaSearchResultList> list = new ArrayList<>();
    private DataCallBack dataCallBack;
    //封装
    public List<CinemaSearchResultList> getList() {
        return list;
    }
    public void setDataCallBack(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }
    //方法实现
    //初始化视图
    @NonNull
    @Override
    public CinemaSearchResultListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cinema_search_result_list_contents, parent, false);
        return new MyHolder(view);
    }
    //设置数据
    @Override
    public void onBindViewHolder(@NonNull CinemaSearchResultListAdapter.MyHolder holder, int position) {
        //获取数据
        CinemaSearchResultList cinemaSearchResultList = list.get(position);
        //设置图片渐进式加载
        ImageRequest build = ImageRequestBuilder.newBuilderWithSource(Uri.parse(cinemaSearchResultList.getLogo()))
                .setProgressiveRenderingEnabled(true).build();
        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(build)
                .build();
        holder.logo.setController(controller);
        //设置其他数据
        holder.name.setText(cinemaSearchResultList.getName());
        holder.address.setText(cinemaSearchResultList.getAddress());
        //点击监听
        holder.itemView.setTag(cinemaSearchResultList.getId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long cinemaId = (long) v.getTag();
                //判断
                if(dataCallBack != null){
                    //完成回调
                    dataCallBack.jumpMovieDetail(cinemaId);
                }
            }
        });
    }
    //条目总数
    @Override
    public int getItemCount() {
        return list.size();
    }
    //寄存器
    public class MyHolder extends RecyclerView.ViewHolder {
        //定义
        protected SimpleDraweeView logo;
        protected TextView name,address;
        protected MyHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
        }
    }
    //声明回调
    public interface DataCallBack {
        void jumpMovieDetail(long cinemaId);
    }
}
