package com.bw.movie.fragment.moviedetailfragment;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.adapter.MovieDetailPosterAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.MoviesDetail;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 影片详情剧照卡片Fragment
 * 李易泽
 * 20200604
 */
public class StillFragment extends BaseFragment {
    //定义
    private RecyclerView posterRecy;
    private MovieDetailPosterAdapter movieDetailPosterAdapter;
    //方法实现
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.movie_detail_fragment_still;
    }
    @Override
    protected void initViews(View mContentView) {
        //EventBus注册
        EventBus.getDefault().register(this);
        //初始化ID
        posterRecy = mContentView.findViewById(R.id.poster_recy);
        //布局管理器与适配器
        posterRecy.setLayoutManager(new GridLayoutManager(getContext(),2));
        movieDetailPosterAdapter = new MovieDetailPosterAdapter();
        posterRecy.setAdapter(movieDetailPosterAdapter);
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
    public void postMovieDetailListBean(Object obj){
        //确认数据类型
        if(obj instanceof MoviesDetail) {
            //添加数据
            movieDetailPosterAdapter.getList().addAll(((MoviesDetail) obj).getPosterList());
            //刷新适配器
            movieDetailPosterAdapter.notifyDataSetChanged();
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
