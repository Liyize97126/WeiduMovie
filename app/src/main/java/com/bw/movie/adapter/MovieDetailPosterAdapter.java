package com.bw.movie.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 电影详情页剧照列表适配器
 * 李易泽
 * 20200604
 */
public class MovieDetailPosterAdapter extends RecyclerView.Adapter<MovieDetailPosterAdapter.MyHolder> {
    //定义
    private List<String> list = new ArrayList<>();
    //封装
    public List<String> getList() {
        return list;
    }
    //方法实现
    //初始化视图
    @NonNull
    @Override
    public MovieDetailPosterAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_detail_poster_list_contents, parent, false);
        return new MyHolder(view);
    }
    //设置数据
    @Override
    public void onBindViewHolder(@NonNull MovieDetailPosterAdapter.MyHolder holder, int position) {
        //获取数据
        String photoUrl = list.get(position);
        //Fresco图片渐进式加载
        ImageRequest build = ImageRequestBuilder.newBuilderWithSource(Uri.parse(photoUrl))
                .setProgressiveRenderingEnabled(true).build();
        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(build)
                .build();
        holder.posterPhoto.setController(controller);
    }
    //条目总数
    @Override
    public int getItemCount() {
        return list.size();
    }
    //寄存器
    public class MyHolder extends RecyclerView.ViewHolder {
        //定义
        protected SimpleDraweeView posterPhoto;
        //初始化
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            posterPhoto = itemView.findViewById(R.id.poster_photo);
        }
    }
}
