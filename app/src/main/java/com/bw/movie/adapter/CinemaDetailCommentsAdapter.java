package com.bw.movie.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.bean.CinemaDetailCommentsList;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 影院详情页影评列表适配器
 * 李易泽
 * 20200612
 */
public class CinemaDetailCommentsAdapter extends RecyclerView.Adapter<CinemaDetailCommentsAdapter.MyHolder> {
    //定义
    private List<CinemaDetailCommentsList> list = new ArrayList<>();
    //封装
    public List<CinemaDetailCommentsList> getList() {
        return list;
    }
    //方法实现
    //初始化视图
    @NonNull
    @Override
    public CinemaDetailCommentsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_list_contents, parent, false);
        return new MyHolder(view);
    }
    //设置数据
    @Override
    public void onBindViewHolder(@NonNull CinemaDetailCommentsAdapter.MyHolder holder, int position) {
        //获取数据
        CinemaDetailCommentsList cinemaDetailCommentsList = list.get(position);
        //Fresco图片渐进式加载
        ImageRequest build = ImageRequestBuilder.newBuilderWithSource(Uri.parse(cinemaDetailCommentsList.getCommentHeadPic()))
                .setProgressiveRenderingEnabled(true).build();
        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(build)
                .build();
        holder.commentHeadPic.setController(controller);
        //设置文本
        holder.commentUserName.setText(cinemaDetailCommentsList.getCommentUserName());
        holder.commentContent.setText(cinemaDetailCommentsList.getCommentContent());
    }
    //条目总数
    @Override
    public int getItemCount() {
        return list.size();
    }
    //寄存器
    public class MyHolder extends RecyclerView.ViewHolder {
        //定义
        protected SimpleDraweeView commentHeadPic;
        protected TextView commentUserName,commentContent;
        //初始化
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            commentHeadPic = itemView.findViewById(R.id.comment_head_pic);
            commentUserName = itemView.findViewById(R.id.comment_user_name);
            commentContent = itemView.findViewById(R.id.comment_content);
        }
    }
}
