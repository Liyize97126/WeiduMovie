package com.bw.movie.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.DataBean;
import com.bw.movie.bean.MoviesDetail;
import com.bw.movie.bean.MoviesDetailTabBean;
import com.bw.movie.fragment.moviedetailfragment.IntroducedFragment;
import com.bw.movie.fragment.moviedetailfragment.MovieCommentsFragment;
import com.bw.movie.fragment.moviedetailfragment.NoticeFragment;
import com.bw.movie.fragment.moviedetailfragment.StillFragment;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.url.MyUrl;
import com.bw.movie.util.NetUtil;
import com.bw.movie.util.TimesFormatUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private boolean isFollowMovie;
    private int flag = -1;
    private SimpleDraweeView imageUrl;
    private LinearLayout followMovieDo;
    private ImageView back,whetherFollowPic;
    private TextView score,commentNum,whetherFollowText,name,movieType,duration,releaseTime,placeOrigin;
    private TabLayout movieDetailTabLay;
    private ViewPager movieDetailViewPag;
    private Button addMovieComment, buyMovieTicketDo;
    private List<MoviesDetailTabBean> moviesDetailTabBeanList;
    private int movieId;
    private String movieName;
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
        followMovieDo = findViewById(R.id.follow_movie_do);
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
        movieDetailTabLay = findViewById(R.id.movie_detail_tab_lay);
        movieDetailViewPag = findViewById(R.id.movie_detail_view_pag);
        addMovieComment = findViewById(R.id.add_movie_comment);
        buyMovieTicketDo = findViewById(R.id.buy_movie_ticket_do);
        moviesDetailTabBeanList = new ArrayList<>();
        //添加页面
        moviesDetailTabBeanList.add(new MoviesDetailTabBean("介绍",new IntroducedFragment()));
        moviesDetailTabBeanList.add(new MoviesDetailTabBean("预告",new NoticeFragment()));
        moviesDetailTabBeanList.add(new MoviesDetailTabBean("剧照",new StillFragment()));
        moviesDetailTabBeanList.add(new MoviesDetailTabBean("影评",new MovieCommentsFragment()));
        //设置适配器
        movieDetailViewPag.setOffscreenPageLimit(moviesDetailTabBeanList.size()-1);
        movieDetailViewPag.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return moviesDetailTabBeanList.get(position).getTabTitle();
            }
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return moviesDetailTabBeanList.get(position).getBaseFragment();
            }
            @Override
            public int getCount() {
                return moviesDetailTabBeanList.size();
            }
        });
        //关联布局
        movieDetailTabLay.setupWithViewPager(movieDetailViewPag);
        //获取值
        movieId = getIntent().getIntExtra("movieId", -1);
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
        //点击事件
        addMovieComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至评价页面
                Intent intent = new Intent(MoviesDetailActivity.this, WriteEvaluationActivity.class);
                intent.putExtra("movieName",movieName);
                startActivity(intent);
            }
        });
        buyMovieTicketDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提示
                Toast.makeText(MoviesDetailActivity.this,"正在开发中，敬请期待！",Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void startCoding() {
        //关注与取消关注电影
        followMovieDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断网络
                if(NetUtil.getInstance().isConnected()){
                    //判断
                    if(!isFollowMovie){
                        //关注电影
                        flag = 1;
                        mPresenter.startRequest(GET,MyUrl.FOLLOW_MOVIE,type,map);
                    } else {
                        //取消关注电影
                        flag = 1;
                        mPresenter.startRequest(GET,MyUrl.CANCEL_FOLLOW_MOVIE,type,map);
                    }
                } else {
                    //提示
                    Toast.makeText(MoviesDetailActivity.this,"网络似乎开小差了，请检查下网络吧！",Toast.LENGTH_LONG).show();
                }
            }
        });
        //判断网络
        if(NetUtil.getInstance().isConnected()){
            //发送请求
            flag = 0;
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
            //选择
            switch (flag){
                case 0 : {
                    //获取对象
                    Object result = ((DataBean) o).getResult();
                    //判断是否非空
                    if(result != null){
                        //instanceof判断
                        if(result instanceof MoviesDetail){
                            //设置数据
                            score.setText("评分   " + ((MoviesDetail) result).getScore() + "分");
                            commentNum.setText("评论   " + ((MoviesDetail) result).getCommentNum() + "条");
                            movieName = ((MoviesDetail) result).getName();
                            name.setText(movieName);
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
                                case 1: {
                                    isFollowMovie = true;
                                    whetherFollowPic.setImageResource(R.drawable.empty_heart_selected);
                                    whetherFollowText.setText("已关注");
                                }break;
                                case 2: {
                                    isFollowMovie = false;
                                    whetherFollowPic.setImageResource(R.drawable.empty_heart_noselected);
                                    whetherFollowText.setText("关注");
                                }break;
                            }
                            //其它数据发送粘性事件
                            EventBus.getDefault().postSticky(movieId);
                            EventBus.getDefault().postSticky(result);
                        }
                    }
                }break;
                case 1 : {
                    //提示
                    Toast.makeText(this,((DataBean) o).getMessage(),Toast.LENGTH_LONG).show();
                    //判断是否登录
                    if(((DataBean) o).getStatus().equals("9999")){
                        //跳转到登录页面
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        return;
                    }
                    //判断关注/取消关注是否成功
                    if(((DataBean) o).getStatus().equals("0000")){
                        //修改状态
                        if(!isFollowMovie){
                            //调整成取消关注
                            isFollowMovie = true;
                            //修改已关注样式
                            whetherFollowPic.setImageResource(R.drawable.empty_heart_selected);
                            whetherFollowText.setText("已关注");
                        } else {
                            //调整成关注
                            isFollowMovie = false;
                            //修改成未关注样式
                            whetherFollowPic.setImageResource(R.drawable.empty_heart_noselected);
                            whetherFollowText.setText("关注");
                        }
                    }
                }break;
            }
        }
    }
    @Override
    public void onFail(String err) {
    }
}
