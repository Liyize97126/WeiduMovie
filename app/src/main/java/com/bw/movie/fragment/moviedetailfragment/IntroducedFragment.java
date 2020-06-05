package com.bw.movie.fragment.moviedetailfragment;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.adapter.MovieDetailActorAdapter;
import com.bw.movie.adapter.MovieDetailDirectorAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.MoviesDetail;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 影片详情介绍卡片Fragment
 * 李易泽
 * 20200604
 */
public class IntroducedFragment extends BaseFragment {
    //定义
    private TextView summary,movieDirectorText,movieActorText;
    private RecyclerView movieDirectorRecy,movieActorRecy;
    private MovieDetailDirectorAdapter movieDetailDirectorAdapter;
    private MovieDetailActorAdapter movieDetailActorAdapter;
    //方法实现
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.movie_detail_fragment_introduced;
    }
    @Override
    protected void initViews(View mContentView) {
        //EventBus注册
        EventBus.getDefault().register(this);
        //初始化ID
        summary = mContentView.findViewById(R.id.summary);
        movieDirectorText = mContentView.findViewById(R.id.movie_director_text);
        movieActorText = mContentView.findViewById(R.id.movie_actor_text);
        movieDirectorRecy = mContentView.findViewById(R.id.movie_director_recy);
        movieActorRecy = mContentView.findViewById(R.id.movie_actor_recy);
        //布局管理器与适配器
        movieDirectorRecy.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));
        movieActorRecy.setLayoutManager(new GridLayoutManager(getContext(),3));
        movieDetailDirectorAdapter = new MovieDetailDirectorAdapter();
        movieDetailActorAdapter = new MovieDetailActorAdapter();
        movieDirectorRecy.setAdapter(movieDetailDirectorAdapter);
        movieActorRecy.setAdapter(movieDetailActorAdapter);
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    @Override
    protected void lazyLoad() {
    }
    @Override
    protected void initDestroyView() {
    }
    //接收订阅者消息
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void postMovieActorBean(Object obj){
        //确认数据类型
        if(obj instanceof MoviesDetail) {
            //添加数据
            summary.setText(((MoviesDetail) obj).getSummary());
            movieDirectorText.setText("导演（" + ((MoviesDetail) obj).getMovieDirector().size() + "）");
            movieDetailDirectorAdapter.getList().addAll(((MoviesDetail) obj).getMovieDirector());
            movieActorText.setText("演员（" + ((MoviesDetail) obj).getMovieActor().size() + "）");
            movieDetailActorAdapter.getList().addAll(((MoviesDetail) obj).getMovieActor());
            //刷新适配器
            movieDetailDirectorAdapter.notifyDataSetChanged();
            movieDetailActorAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void initDetach() {
        //EventBus取消注册
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onSuccess(Object o) {
    }
    @Override
    public void onFail(String err) {
    }
}
