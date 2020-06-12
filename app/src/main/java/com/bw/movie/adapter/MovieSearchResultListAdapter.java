package com.bw.movie.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.bean.MovieSearchResultList;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索页电影查询结果列表适配器
 * 李易泽
 * 20200611
 */
public class MovieSearchResultListAdapter extends RecyclerView.Adapter<MovieSearchResultListAdapter.MyHolder> {
    //定义
    private List<MovieSearchResultList> list = new ArrayList<>();
    private ReleaseMovieListAdapter.DataCallBack dataCallBack;
    //封装
    public List<MovieSearchResultList> getList() {
        return list;
    }
    public void setDataCallBack(ReleaseMovieListAdapter.DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }
    //方法实现
    //初始化视图
    @NonNull
    @Override
    public MovieSearchResultListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_search_result_list_contents, parent, false);
        return new MyHolder(view);
    }
    //设置数据
    @Override
    public void onBindViewHolder(@NonNull MovieSearchResultListAdapter.MyHolder holder, int position) {
        //获取数据
        MovieSearchResultList movieSearchResultList = list.get(position);
        //设置图片渐进式加载
        ImageRequest build = ImageRequestBuilder.newBuilderWithSource(Uri.parse(movieSearchResultList.getImageUrl()))
                .setProgressiveRenderingEnabled(true).build();
        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(build)
                .build();
        holder.imageUrl.setController(controller);
        //设置其他数据
        holder.name.setText(movieSearchResultList.getName());
        holder.director.setText("导演：" + movieSearchResultList.getDirector());
        holder.starring.setText("主演：" + movieSearchResultList.getStarring());
        holder.score.setText("评分：" + movieSearchResultList.getScore() + "分");
        //点击监听
        holder.itemView.setTag(movieSearchResultList.getMovieId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long movieId = (long) v.getTag();
                //判断
                if(dataCallBack != null){
                    //完成回调
                    dataCallBack.jumpMovieDetail(movieId);
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
        protected SimpleDraweeView imageUrl;
        protected TextView name,director,starring,score;
        //初始化
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageUrl = itemView.findViewById(R.id.image_url);
            name = itemView.findViewById(R.id.name);
            director = itemView.findViewById(R.id.director);
            starring = itemView.findViewById(R.id.starring);
            score = itemView.findViewById(R.id.score);
        }
    }
    //声明回调
    public interface DataCallBack {
        void jumpMovieDetail(long movieId);
    }
}
