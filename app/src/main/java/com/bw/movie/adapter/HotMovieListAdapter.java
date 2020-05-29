package com.bw.movie.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.bean.HotMovieList;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页热门电影列表适配器
 * 李易泽
 * 20200525
 */
public class HotMovieListAdapter extends RecyclerView.Adapter<HotMovieListAdapter.MyHolder> {
    //定义
    private List<HotMovieList> list = new ArrayList<>();
    private DataCallBack dataCallBack;
    //封装
    public List<HotMovieList> getList() {
        return list;
    }
    public void setDataCallBack(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }
    //方法实现
    //初始化视图
    @NonNull
    @Override
    public HotMovieListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rndy_list_contents, parent, false);
        return new MyHolder(view);
    }
    //设置数据
    @Override
    public void onBindViewHolder(@NonNull HotMovieListAdapter.MyHolder holder, int position) {
        //获取数据
        HotMovieList hotMovieList = list.get(position);
        //Fresco图片渐进式加载
        ImageRequest build = ImageRequestBuilder.newBuilderWithSource(Uri.parse(hotMovieList.getImageUrl()))
                .setProgressiveRenderingEnabled(true).build();
        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(build)
                .build();
        holder.imageUrl.setController(controller);
        //设置文本
        holder.name.setText(hotMovieList.getName());
        holder.score.setText(hotMovieList.getScore() + "分");
        //点击监听
        holder.itemView.setTag(hotMovieList.getMovieId());
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
        protected TextView score,name;
        //初始化
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageUrl = itemView.findViewById(R.id.image_url);
            score = itemView.findViewById(R.id.score);
            name = itemView.findViewById(R.id.name);
        }
    }
    //声明回调
    public interface DataCallBack {
        void jumpMovieDetail(long movieId);
    }
}
