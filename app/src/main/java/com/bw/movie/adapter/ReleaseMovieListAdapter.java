package com.bw.movie.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.bean.ReleaseMovieList;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页正在上映列表适配器
 * 李易泽
 * 20200525
 */
public class ReleaseMovieListAdapter extends RecyclerView.Adapter<ReleaseMovieListAdapter.MyHolder> {
    //定义
    private List<ReleaseMovieList> list = new ArrayList<>();
    //封装
    public List<ReleaseMovieList> getList() {
        return list;
    }
    //方法实现
    //初始化视图
    @NonNull
    @Override
    public ReleaseMovieListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zzry_list_contents, parent, false);
        return new MyHolder(view);
    }
    //设置数据
    @Override
    public void onBindViewHolder(@NonNull ReleaseMovieListAdapter.MyHolder holder, int position) {
        //获取数据
        ReleaseMovieList releaseMovieList = list.get(position);
        //Fresco图片渐进式加载
        ImageRequest build = ImageRequestBuilder.newBuilderWithSource(Uri.parse(releaseMovieList.getImageUrl()))
                .setProgressiveRenderingEnabled(true).build();
        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(build)
                .build();
        holder.imageUrl.setController(controller);
        //设置文本
        holder.name.setText(releaseMovieList.getName());
        holder.score.setText(releaseMovieList.getScore() + "分");
    }
    //条目总数
    @Override
    public int getItemCount() {
        return list.size();
    }
    //寄存器
    public class MyHolder extends RecyclerView.ViewHolder {
        //定义
        protected SimpleDraweeView imageUrl;
        protected TextView score,name;
        //初始化
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageUrl = itemView.findViewById(R.id.image_url);
            score = itemView.findViewById(R.id.score);
            name = itemView.findViewById(R.id.name);
        }
    }
}
