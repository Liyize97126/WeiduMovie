package com.bw.movie.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.bean.MoviesDetail;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 电影详情页演员列表适配器
 * 李易泽
 * 20200604
 */
public class MovieDetailActorAdapter extends RecyclerView.Adapter<MovieDetailActorAdapter.MyHolder> {
    //定义
    private List<MoviesDetail.MovieActorBean> list = new ArrayList<>();
    //封装
    public List<MoviesDetail.MovieActorBean> getList() {
        return list;
    }
    //方法实现
    //初始化视图
    @NonNull
    @Override
    public MovieDetailActorAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_detail_actor_list_contents, parent, false);
        return new MyHolder(view);
    }
    //设置数据
    @Override
    public void onBindViewHolder(@NonNull MovieDetailActorAdapter.MyHolder holder, int position) {
        //获取数据
        MoviesDetail.MovieActorBean movieActorBean = list.get(position);
        //Fresco图片渐进式加载
        ImageRequest build = ImageRequestBuilder.newBuilderWithSource(Uri.parse(movieActorBean.getPhoto()))
                .setProgressiveRenderingEnabled(true).build();
        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(build)
                .build();
        holder.photo.setController(controller);
        //设置文本
        holder.name.setText(movieActorBean.getName());
        holder.role.setText("饰：" + movieActorBean.getRole());
    }
    //条目总数
    @Override
    public int getItemCount() {
        return list.size();
    }
    //寄存器
    public class MyHolder extends RecyclerView.ViewHolder {
        //定义
        protected SimpleDraweeView photo;
        protected TextView name,role;
        //初始化
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            name = itemView.findViewById(R.id.name);
            role = itemView.findViewById(R.id.role);
        }
    }
}
