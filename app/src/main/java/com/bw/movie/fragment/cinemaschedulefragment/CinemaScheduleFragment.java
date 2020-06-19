package com.bw.movie.fragment.cinemaschedulefragment;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.activity.ChooseSeatActivity;
import com.bw.movie.activity.MoviesDetailActivity;
import com.bw.movie.adapter.CinemaScheduleListAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.CinemaScheduleList;
import com.bw.movie.bean.DataListBean;
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
 * 影院排期数据展示Fragment
 * 李易泽
 * 20200615
 */
public class CinemaScheduleFragment extends BaseFragment {
    //定义
    private TwinklingRefreshLayout twinklingRl;
    private RecyclerView cinemaScheduleRecy;
    private CinemaScheduleListAdapter cinemaScheduleListAdapter;
    private int page = 1;
    private int cinemaId;
    private Type type;
    private Map<String, Object> map;
    //构造
    public CinemaScheduleFragment(int cinemaId) {
        this.cinemaId = cinemaId;
    }
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_cinema_schedule;
    }
    @Override
    protected void initViews(View mContentView) {
        //初始化
        twinklingRl = mContentView.findViewById(R.id.twinkling_rl);
        cinemaScheduleRecy = mContentView.findViewById(R.id.cinema_schedule_recy);
        //设置布局管理器
        cinemaScheduleRecy.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        //设置适配器
        cinemaScheduleListAdapter = new CinemaScheduleListAdapter();
        cinemaScheduleRecy.setAdapter(cinemaScheduleListAdapter);
        //设置监听
        cinemaScheduleListAdapter.setDataCallBack(new CinemaScheduleListAdapter.DataCallBack() {
            @Override
            public void jumpMovieDetail(long movieId) {
                //跳转
                Intent intent = new Intent(getContext(), MoviesDetailActivity.class);
                //传值
                intent.putExtra("movieId", (int) movieId);
                intent.putExtra("cinemaId", cinemaId);
                //完成跳转
                startActivity(intent);
            }
            @Override
            public void jumpChooseSeat(CinemaScheduleList cinemaScheduleList) {
                //跳转
                Intent intent = new Intent(getContext(), ChooseSeatActivity.class);
                //传值
                intent.putExtra("movieId", (int) cinemaScheduleList.getMovieId());
                intent.putExtra("cinemaId", cinemaId);
                intent.putExtra("name",cinemaScheduleList.getName());
                //完成跳转
                startActivity(intent);
            }
        });
        //设置上下拉刷新和加载
        twinklingRl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                //判断网络
                if(NetUtil.getInstance().isConnected()){
                    //请求数据
                    page = 1;
                    map.put("page",page);
                    mPresenter.startRequest(GET, MyUrl.FIND_CINEMA_SCHEDULE_LIST,type,map);
                } else {
                    //提示
                    Toast.makeText(getContext(),"网络似乎开小差了，请检查下网络吧！",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                //判断网络
                if(NetUtil.getInstance().isConnected()){
                    //请求数据
                    page++;
                    map.put("page",page);
                    mPresenter.startRequest(GET, MyUrl.FIND_CINEMA_SCHEDULE_LIST,type,map);
                } else {
                    //提示
                    Toast.makeText(getContext(),"网络似乎开小差了，请检查下网络吧！",Toast.LENGTH_LONG).show();
                }
            }
        });
        //泛型类处理
        type = new TypeToken<DataListBean<CinemaScheduleList>>() {
        }.getType();
        //设置加载参数
        map = new HashMap<>();
        map.put("cinemaId", cinemaId);
        map.put("page",page);
        map.put("count",8);
    }
    @Override
    protected BasePresenter initPresenter() {
        return new PresenterImpl();
    }
    @Override
    protected void lazyLoad() {
        //判断网络
        if(NetUtil.getInstance().isConnected()){
            //请求数据
            mPresenter.startRequest(GET, MyUrl.FIND_CINEMA_SCHEDULE_LIST,type,map);
        } else {
            //提示
            Toast.makeText(getContext(),"网络似乎开小差了，请检查下网络吧！",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void initDestroyView() {
    }
    @Override
    protected void initDetach() {
    }
    @Override
    public void onSuccess(Object o) {
        //instanceof判断
        if(o instanceof DataListBean){
            //获取集合
            List result = ((DataListBean) o).getResult();
            //判断集合是否有数据
            if(result != null){
                if(result.size() > 0){
                    //instanceof判断
                    if(result.get(0) instanceof CinemaScheduleList){
                        //清空数据
                        if(page == 1){
                            cinemaScheduleListAdapter.getList().clear();
                        }
                        //添加数据并刷新
                        cinemaScheduleListAdapter.getList().addAll(result);
                        cinemaScheduleListAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                //提示
                Toast.makeText(getContext(),"没有更多数据了！",Toast.LENGTH_LONG).show();
            }
        }
        //停止刷新和加载更多
        twinklingRl.finishRefreshing();
        twinklingRl.finishLoadmore();
    }
    @Override
    public void onFail(String err) {
    }
}
