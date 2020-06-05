package com.bw.movie.fragment.moviedetailfragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.adapter.MovieDetailCommentsAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.DataListBean;
import com.bw.movie.bean.MovieDetailCommentsList;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.util.NetUtil;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 影片详情评论卡片Fragment
 * 李易泽
 * 20200604
 */
public class MovieCommentsFragment extends BaseFragment {
    //定义
    private RecyclerView movieCommentsRecy;
    private MovieDetailCommentsAdapter movieDetailCommentsAdapter;
    private Type type;
    private Map<String, Object> map;
    //方法实现
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.movie_detail_fragment_movie_comments;
    }
    @Override
    protected void initViews(View mContentView) {
        //EventBus注册
        EventBus.getDefault().register(this);
        //初始化ID
        movieCommentsRecy = mContentView.findViewById(R.id.movie_comments_recy);
        //布局管理器与适配器
        movieCommentsRecy.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        movieDetailCommentsAdapter = new MovieDetailCommentsAdapter();
        movieCommentsRecy.setAdapter(movieDetailCommentsAdapter);
        //泛型类处理
        type = new TypeToken<DataListBean<MovieDetailCommentsList>>() {
        }.getType();
        //定义集合
        map = new HashMap<>();
        map.put("page",1);
        map.put("count",15);
    }
    @Override
    protected BasePresenter initPresenter() {
        return new PresenterImpl();
    }
    @Override
    protected void lazyLoad() {
        //判断网络
        if(NetUtil.getInstance().isConnected()){
            //请求
            //mPresenter.startRequest(GET, MyUrl.FIND_ALL_MOVIE_COMMENT,type,map);
        }
    }
    @Override
    protected void initDestroyView() {
    }
    //接收订阅者消息
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void postMovieID(Integer integer){
        //设置加载参数
        map.put("movieId", integer);
    }
    @Override
    protected void initDetach() {
        //EventBus取消注册
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onSuccess(Object o) {
        //instanceof判断
        if(o instanceof DataListBean){
            //获取并添加数据
            movieDetailCommentsAdapter.getList().addAll(((DataListBean) o).getResult());
            movieDetailCommentsAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onFail(String err) {
    }
}
