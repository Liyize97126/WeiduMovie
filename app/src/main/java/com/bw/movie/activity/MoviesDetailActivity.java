package com.bw.movie.activity;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.DataBean;
import com.bw.movie.bean.MoviesDetail;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.url.MyUrl;
import com.bw.movie.util.NetUtil;
import com.bw.movie.util.TimesFormatUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static com.bw.movie.api.ApiService.GET;

/**
 * 电影详情页面
 * 李易泽
 * 20200528
 */
public class MoviesDetailActivity extends BaseActivity {
    //定义
    private Map<String, Object> map;
    private Type type;
    private SimpleDraweeView imageUrl;
    private ImageView back,whetherFollowPic;
    private TextView score,commentNum,whetherFollowText,name,movieType,duration,releaseTime,placeOrigin;
    //方法实现
    @Override
    protected boolean isFullScreen() {
        return false;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_movies_detail;
    }
    @Override
    protected BasePresenter initPresenter() {
        return new PresenterImpl();
    }
    @Override
    protected void initView() {
        //获取ID
        imageUrl = findViewById(R.id.image_url);
        back = findViewById(R.id.back);
        whetherFollowPic = findViewById(R.id.whether_follow_pic);
        score = findViewById(R.id.score);
        commentNum = findViewById(R.id.comment_num);
        whetherFollowText = findViewById(R.id.whether_follow_text);
        name = findViewById(R.id.name);
        movieType = findViewById(R.id.movie_type);
        duration = findViewById(R.id.duration);
        releaseTime = findViewById(R.id.release_time);
        placeOrigin = findViewById(R.id.place_origin);
        //获取值
        int movieId = getIntent().getIntExtra("movieId", -1);
        //泛型类处理
        type = new TypeToken<DataBean<MoviesDetail>>() {
        }.getType();
        //设置加载参数
        map = new HashMap<>();
        map.put("movieId", movieId);
        //返回界面操作
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void startCoding() {
        //判断网络
        if(NetUtil.getInstance().isConnected()){
            //发送请求
            mPresenter.startRequest(GET, MyUrl.FIND_MOVIES_DETAIL,type,map);
        } else {
        }
    }
    @Override
    protected void initResume() {
    }
    @Override
    protected void initPause() {
    }
    @Override
    protected void initDestroy() {
    }
    @Override
    public void onSuccess(Object o) {
        //instanceof判断
        if(o instanceof DataBean){
            Object result = ((DataBean) o).getResult();
            //判断是否非空
            if(result != null){
                //instanceof判断
                if(result instanceof MoviesDetail){
                    //设置数据
                    score.setText("评分   " + ((MoviesDetail) result).getScore() + "分");
                    commentNum.setText("评论   " + ((MoviesDetail) result).getCommentNum() + "条");
                    name.setText(((MoviesDetail) result).getName());
                    if(((MoviesDetail) result).getMovieType().equals("")){
                        //去除外边距
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) duration.getLayoutParams();
                        layoutParams.setMargins(0,0,0,0);
                        duration.setLayoutParams(layoutParams);
                    } else {
                        movieType.setText(((MoviesDetail) result).getMovieType());
                    }
                    duration.setText(((MoviesDetail) result).getDuration());
                    releaseTime.setText(TimesFormatUtil.timeFormatFirst(((MoviesDetail) result).getReleaseTime()));
                    placeOrigin.setText(((MoviesDetail) result).getPlaceOrigin() + "上映");
                    //设置图片
                    ImageRequest build = ImageRequestBuilder.newBuilderWithSource(Uri.parse(((MoviesDetail) result).getImageUrl()))
                            .setProgressiveRenderingEnabled(true).build();
                    AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(build)
                            .build();
                    imageUrl.setController(controller);
                    //设置关注状态
                    switch (((MoviesDetail) result).getWhetherFollow()){
                        case 2: {
                            whetherFollowPic.setImageResource(R.drawable.empty_heart_noselected);
                            whetherFollowText.setText("关注");
                        }break;
                    }
                }
            }
        }
    }
    @Override
    public void onFail(String err) {
    }
}