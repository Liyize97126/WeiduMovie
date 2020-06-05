package com.bw.movie.fragment.moviedetailfragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.adapter.MovieDetailShortFilmListAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.MoviesDetail;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * 影片详情预告卡片Fragment
 * 李易泽
 * 20200604
 */
public class NoticeFragment extends BaseFragment {
    //定义
    private RecyclerView shortFilmRecy;
    private MovieDetailShortFilmListAdapter movieDetailShortFilmListAdapter;
    //方法实现
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.movie_detail_fragment_notice;
    }
    @Override
    protected void initViews(View mContentView) {
        //EventBus注册
        EventBus.getDefault().register(this);
        //初始化ID
        shortFilmRecy = mContentView.findViewById(R.id.short_film_recy);
        //布局管理器与适配器
        shortFilmRecy.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        movieDetailShortFilmListAdapter = new MovieDetailShortFilmListAdapter();
        shortFilmRecy.setAdapter(movieDetailShortFilmListAdapter);
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    @Override
    protected void lazyLoad() {
    }
    @Override
    public void onPause() {
        super.onPause();
        //状态调整
        JCVideoPlayer.releaseAllVideos();
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
            movieDetailShortFilmListAdapter.getList().addAll(((MoviesDetail) obj).getShortFilmList());
            //刷新适配器
            movieDetailShortFilmListAdapter.notifyDataSetChanged();
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
