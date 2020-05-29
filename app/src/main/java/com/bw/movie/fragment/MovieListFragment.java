package com.bw.movie.fragment;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.activity.MoviesDetailActivity;
import com.bw.movie.adapter.ComingSoonMovieListAdapter;
import com.bw.movie.adapter.HotMovieListAdapter;
import com.bw.movie.adapter.ReleaseMovieListAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.ComingSoonMovieList;
import com.bw.movie.bean.DataListBean;
import com.bw.movie.bean.HotMovieList;
import com.bw.movie.bean.ReleaseMovieList;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.url.MyUrl;
import com.google.gson.reflect.TypeToken;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bw.movie.api.ApiService.GET;

/**
 * 首页电影列表
 * 李易泽
 * 20200524
 */
public class MovieListFragment extends BaseFragment {
    //定义
    private TwinklingRefreshLayout twinklingRl;
    private boolean listData1,listData2,listData3;
    private RecyclerView mZZRYRecy,mJJSYRecy,mRNDYRecy;
    private ReleaseMovieListAdapter releaseMovieListAdapter;
    private ComingSoonMovieListAdapter comingSoonMovieListAdapter;
    private HotMovieListAdapter hotMovieListAdapter;
    private Map<String, Object> releaseMovieMap,comingSoonMovieMap,hotMovieMap;
    private Type releaseMovieType,comingSoonMovieType,hotMovieType;
    //方法实现
    //布局
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_movie_list;
    }
    //初始化视图
    @Override
    protected void initViews(View mContentView) {
        //获取ID
        twinklingRl = mContentView.findViewById(R.id.twinkling_rl);
        mZZRYRecy = mContentView.findViewById(R.id.zzry_recy);
        mJJSYRecy = mContentView.findViewById(R.id.jjsy_recy);
        mRNDYRecy = mContentView.findViewById(R.id.rndy_recy);
        //泛型类处理
        releaseMovieType = new TypeToken<DataListBean<ReleaseMovieList>>() {
        }.getType();
        comingSoonMovieType = new TypeToken<DataListBean<ComingSoonMovieList>>() {
        }.getType();
        hotMovieType = new TypeToken<DataListBean<HotMovieList>>() {
        }.getType();
        //设置加载参数
        releaseMovieMap = new HashMap<>();
        releaseMovieMap.put("page",1);
        releaseMovieMap.put("count",6);
        comingSoonMovieMap = new HashMap<>();
        comingSoonMovieMap.put("page",1);
        comingSoonMovieMap.put("count",3);
        hotMovieMap = new HashMap<>();
        hotMovieMap.put("page",1);
        hotMovieMap.put("count",4);
        //TwinklingRefreshLayout设置
        //禁用下拉加载
        twinklingRl.setEnableLoadmore(false);
        //刷新监听
        twinklingRl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                //更改flag值
                listData1 = false;
                listData2 = false;
                listData3 = false;
                //发起请求
                mPresenter.startRequest(GET, MyUrl.FIND_RELEASE_MOVIE_LIST, releaseMovieType ,releaseMovieMap);
                mPresenter.startRequest(GET,MyUrl.FIND_COMING_SOON_MOVIE_LIST, comingSoonMovieType, comingSoonMovieMap);
                mPresenter.startRequest(GET,MyUrl.FIND_HOT_MOVIE_LIST,hotMovieType,hotMovieMap);
            }
        });
        //设置布局管理器
        mZZRYRecy.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        mJJSYRecy.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mRNDYRecy.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        //设置适配器
        releaseMovieListAdapter = new ReleaseMovieListAdapter();
        comingSoonMovieListAdapter = new ComingSoonMovieListAdapter();
        hotMovieListAdapter = new HotMovieListAdapter();
        mZZRYRecy.setAdapter(releaseMovieListAdapter);
        mJJSYRecy.setAdapter(comingSoonMovieListAdapter);
        mRNDYRecy.setAdapter(hotMovieListAdapter);
        //设置回调监听
        releaseMovieListAdapter.setDataCallBack(new ReleaseMovieListAdapter.DataCallBack() {
            @Override
            public void jumpMovieDetail(long movieId) {
                //调用跳转方法
                jumpMovieDetailActivity((int) movieId);
            }
        });
        comingSoonMovieListAdapter.setDataCallBack(new ComingSoonMovieListAdapter.DataCallBack() {
            @Override
            public void jumpMovieDetail(long movieId) {
                //调用跳转方法
                jumpMovieDetailActivity((int) movieId);
            }
        });
        hotMovieListAdapter.setDataCallBack(new HotMovieListAdapter.DataCallBack() {
            @Override
            public void jumpMovieDetail(long movieId) {
                //调用跳转方法
                jumpMovieDetailActivity((int) movieId);
            }
        });
    }
    //初始化Presenter
    @Override
    protected BasePresenter initPresenter() {
        return new PresenterImpl();
    }
    //懒加载
    @Override
    protected void lazyLoad() {
        //发起请求
        twinklingRl.startRefresh();
    }
    //销毁视图
    @Override
    protected void initDestroyView() {
    }
    //成功回调
    @Override
    public void onSuccess(Object o) {
        //instanceof判断
        if(o instanceof DataListBean){
            //获取集合
            List result = ((DataListBean) o).getResult();
            //判断是否非空
            if(result.size() > 0){
                //instanceof判断
                if(result.get(0) instanceof ReleaseMovieList){
                    //清空列表数据
                    if(releaseMovieListAdapter != null){
                        releaseMovieListAdapter.getList().clear();
                    }
                    //添加数据到集合
                    releaseMovieListAdapter.getList().addAll(result);
                    releaseMovieListAdapter.notifyDataSetChanged();
                    listData1 = true;
                }
                if(result.get(0) instanceof ComingSoonMovieList){
                    //清空列表数据
                    if(comingSoonMovieListAdapter != null){
                        comingSoonMovieListAdapter.getList().clear();
                    }
                    //添加数据到集合
                    comingSoonMovieListAdapter.getList().addAll(result);
                    comingSoonMovieListAdapter.notifyDataSetChanged();
                    listData2 = true;
                }
                if(result.get(0) instanceof HotMovieList){
                    //清空列表数据
                    if(hotMovieListAdapter != null){
                        hotMovieListAdapter.getList().clear();
                    }
                    //添加数据到集合
                    hotMovieListAdapter.getList().addAll(result);
                    hotMovieListAdapter.notifyDataSetChanged();
                    listData3 = true;
                }
                //判断是否刷新完成
                if(listData1 && listData2 && listData3){
                    //提示刷新完成
                    twinklingRl.finishRefreshing();
                }
            }
        }
    }
    //失败回调
    @Override
    public void onFail(String err) {
    }
    //封装统一跳转电影详情的方法
    private void jumpMovieDetailActivity(int movieId){
        //跳转
        Intent intent = new Intent(getActivity(), MoviesDetailActivity.class);
        //传值
        intent.putExtra("movieId", movieId);
        //完成跳转
        startActivity(intent);
    }
}
