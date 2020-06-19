package com.bw.movie.fragment.cinemafragment;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.activity.CinemaDetailActivity;
import com.bw.movie.adapter.RecommendCinemasListAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.DataListBean;
import com.bw.movie.bean.RecommendCinemasList;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.url.MyUrl;
import com.bw.movie.util.NetUtil;
import com.google.gson.reflect.TypeToken;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bw.movie.api.ApiService.GET;

/**
 * 影院页面附近影院Tab页
 * 李易泽
 * 20200602
 */
public class NearbyCinemaFragment extends BaseFragment {
    //定义
    private TwinklingRefreshLayout twinklingRl;
    private RecyclerView recommendRecy;
    private RecommendCinemasListAdapter recommendCinemasListAdapter;
    private Map<String, Object> map;
    private Type recommendCinemasType;
    private int page;
    private String longitude = "0";
    private String latitude = "0";
    //封装
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    //方法实现
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.cinema_fragment_recommend_cinemas;
    }
    @Override
    protected void initViews(View mContentView) {
        //初始化
        twinklingRl = mContentView.findViewById(R.id.twinkling_rl);
        recommendRecy = mContentView.findViewById(R.id.recommend_recy);
        recommendRecy.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recommendCinemasListAdapter = new RecommendCinemasListAdapter();
        recommendRecy.setAdapter(recommendCinemasListAdapter);
        map = new HashMap<>();
        //设置距离信息显示
        recommendCinemasListAdapter.setDistanceInfo(true);
        //点击回调
        recommendCinemasListAdapter.setDataCallBack(new RecommendCinemasListAdapter.DataCallBack() {
            @Override
            public void dataCall(long id) {
                //调用跳转方法
                jumpCinemaDetailActivity((int) id);
            }
        });
        //泛型类设置
        recommendCinemasType = new TypeToken<DataListBean<RecommendCinemasList>>() {
        }.getType();
        //参数设置
        map.put("longitude",longitude);
        map.put("latitude",latitude);
        map.put("count",5);
        //设置监听
        twinklingRl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                //判断网络
                if(NetUtil.getInstance().isConnected()){
                    //刷新请求
                    page = 1;
                    map.put("page",page);
                    mPresenter.startRequest(GET, MyUrl.FIND_NEAR_BY_CINEMAS,recommendCinemasType,map);
                } else {
                    twinklingRl.finishRefreshing();
                }
            }
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                //判断网络
                if(NetUtil.getInstance().isConnected()){
                    //加载更多请求
                    page++;
                    map.put("page",page);
                    mPresenter.startRequest(GET, MyUrl.FIND_NEAR_BY_CINEMAS,recommendCinemasType,map);
                } else {
                    twinklingRl.finishLoadmore();
                }
            }
        });
    }
    @Override
    protected BasePresenter initPresenter() {
        return new PresenterImpl();
    }
    @Override
    protected void lazyLoad() {
        //开始加载
        twinklingRl.startRefresh();
    }
    @Override
    protected void initDestroyView() {
    }
    @Override
    protected void initDetach() {
    }
    @Override
    public void onSuccess(Object o) {
        if(o instanceof DataListBean){
            List result = ((DataListBean) o).getResult();
            if(result != null){
                if(result.size() > 0){
                    //判断
                    if(page == 1){
                        //清空数据
                        recommendCinemasListAdapter.getList().clear();
                    }
                    if(result.get(0) instanceof RecommendCinemasList){
                        //添加数据并刷新
                        recommendCinemasListAdapter.getList().addAll(result);
                        recommendCinemasListAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getContext(), "没有更多数据了！", Toast.LENGTH_LONG).show();
                }
            }
        }
        //反馈
        twinklingRl.finishRefreshing();
        twinklingRl.finishLoadmore();
    }
    @Override
    public void onFail(String err) {
    }
    //封装统一跳转影院详情的方法
    private void jumpCinemaDetailActivity(int cinemaId){
        //跳转
        Intent intent = new Intent(getActivity(), CinemaDetailActivity.class);
        //传值
        intent.putExtra("cinemaId", cinemaId);
        //完成跳转
        startActivity(intent);
    }
}
