package com.bw.movie.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.bean.ComingSoonMovieList;
import com.bw.movie.util.TimesFormatUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页即将上映列表适配器
 * 李易泽
 * 20200525
 */
public class ComingSoonMovieListAdapter extends RecyclerView.Adapter<ComingSoonMovieListAdapter.MyHolder> {
    //定义
    private List<ComingSoonMovieList> list = new ArrayList<>();
    private DataCallBack dataCallBack;
    //封装
    public List<ComingSoonMovieList> getList() {
        return list;
    }
    public void setDataCallBack(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }
    //方法实现
    //初始化视图
    @NonNull
    @Override
    public ComingSoonMovieListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jjsy_list_contents, parent, false);
        return new MyHolder(view);
    }
    //设置数据
    @Override
    public void onBindViewHolder(@NonNull ComingSoonMovieListAdapter.MyHolder holder, int position) {
        //获取数据
        ComingSoonMovieList comingSoonMovieList = list.get(position);
        //Fresco图片渐进式加载
        ImageRequest build = ImageRequestBuilder.newBuilderWithSource(Uri.parse(comingSoonMovieList.getImageUrl()))
                .setProgressiveRenderingEnabled(true).build();
        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(build)
                .build();
        holder.imageUrl.setController(controller);
        //设置文本
        holder.name.setText(comingSoonMovieList.getName());
        holder.releaseTime.setText(TimesFormatUtil.timeFormatSecond(comingSoonMovieList.getReleaseTime()));
        holder.wantSeeNum.setText(comingSoonMovieList.getWantSeeNum() + "人想看");
        //点击监听
        holder.itemView.setTag(comingSoonMovieList.getMovieId());
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
        protected TextView name,releaseTime,wantSeeNum;
        //初始化
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageUrl = itemView.findViewById(R.id.image_url);
            name = itemView.findViewById(R.id.name);
            releaseTime = itemView.findViewById(R.id.release_time);
            wantSeeNum = itemView.findViewById(R.id.want_see_num);
        }
    }
    //声明回调
    public interface DataCallBack {
        void jumpMovieDetail(long movieId);
    }
}
