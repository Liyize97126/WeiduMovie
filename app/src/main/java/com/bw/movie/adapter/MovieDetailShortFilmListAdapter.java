package com.bw.movie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.MoviesDetail;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 电影详情页预告视频列表适配器
 * 李易泽
 * 20200604
 */
public class MovieDetailShortFilmListAdapter extends RecyclerView.Adapter<MovieDetailShortFilmListAdapter.MyHolder> {
    //定义
    private List<MoviesDetail.ShortFilmListBean> list = new ArrayList<>();
    //封装
    public List<MoviesDetail.ShortFilmListBean> getList() {
        return list;
    }
    //方法实现
    //初始化视图
    @NonNull
    @Override
    public MovieDetailShortFilmListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_detail_short_film_list_contents, parent, false);
        return new MyHolder(view);
    }
    //设置数据
    @Override
    public void onBindViewHolder(@NonNull MovieDetailShortFilmListAdapter.MyHolder holder, int position) {
        //获取数据
        MoviesDetail.ShortFilmListBean shortFilmListBean = list.get(position);
        //设置视频播放器
        boolean setUp = holder.jcVideoPly.setUp(shortFilmListBean.getVideoUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST, "精彩预告视频");
        //设置初次加载图片
        if(setUp){
            Glide.with(holder.jcVideoPly.getContext())
                    .load(shortFilmListBean.getImageUrl())
                    .into(holder.jcVideoPly.thumbImageView);
        }
    }
    //条目总数
    @Override
    public int getItemCount() {
        return list.size();
    }
    //寄存器
    public class MyHolder extends RecyclerView.ViewHolder {
        //定义
        protected JCVideoPlayerStandard jcVideoPly;
        //初始化
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            jcVideoPly = itemView.findViewById(R.id.jc_video_ply);
        }
    }
}
