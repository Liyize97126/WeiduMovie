package com.bw.movie.fragment.cinemafragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.adapter.CinemaByRegionListAdapter;
import com.bw.movie.adapter.RegionListAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.CinemaByRegionList;
import com.bw.movie.bean.DataListBean;
import com.bw.movie.bean.RegionList;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.url.MyUrl;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bw.movie.api.ApiService.GET;

/**
 * 影院页面地区选择Tab页
 * 李易泽
 * 20200602
 */
public class CinemaByRegionFragment extends BaseFragment {
    //定义
    private RecyclerView regionRecy,cinemaByRegionRecy;
    private RegionListAdapter regionListAdapter;
    private CinemaByRegionListAdapter cinemaByRegionListAdapter;
    private Type regionType, cinemaByRegionType;
    private Map<String, Object> cinemaByRegionMap;
    //方法实现
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.cinema_fragment_by_region;
    }
    @Override
    protected void initViews(View mContentView) {
        //初始化
        regionRecy = mContentView.findViewById(R.id.region_recy);
        regionRecy.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        cinemaByRegionRecy = mContentView.findViewById(R.id.cinema_by_region_recy);
        cinemaByRegionRecy.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        regionListAdapter = new RegionListAdapter();
        cinemaByRegionListAdapter = new CinemaByRegionListAdapter();
        regionRecy.setAdapter(regionListAdapter);
        cinemaByRegionRecy.setAdapter(cinemaByRegionListAdapter);
        //泛型类处理
        regionType = new TypeToken<DataListBean<RegionList>>() {
        }.getType();
        cinemaByRegionType = new TypeToken<DataListBean<CinemaByRegionList>>() {
        }.getType();
        //设置加载参数
        cinemaByRegionMap = new HashMap<>();
        cinemaByRegionMap.put("regionId",1);
        //回调事件
        regionListAdapter.setItemClick(new RegionListAdapter.ItemClick() {
            @Override
            public void setOnItemClick(int regionId) {
                //设置加载参数
                cinemaByRegionMap.put("regionId",regionId);
                //发起请求
                mPresenter.startRequest(GET,MyUrl.FIND_CINEMA_BY_REGION,cinemaByRegionType,cinemaByRegionMap);
            }
        });
        cinemaByRegionListAdapter.setItemClick(new CinemaByRegionListAdapter.ItemClick() {
            @Override
            public void setOnItemClick(int id) {
                //跳转页面
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
        mPresenter.startRequest(GET, MyUrl.FIND_REGION_LIST,regionType,null);
        mPresenter.startRequest(GET,MyUrl.FIND_CINEMA_BY_REGION,cinemaByRegionType,cinemaByRegionMap);
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
            final List result = ((DataListBean) o).getResult();
            //判断是否非空
            if(result.size() > 0){
                //instanceof判断
                if(result.get(0) instanceof RegionList){
                    //添加数据到集合
                    regionListAdapter.getList().addAll(result);
                    regionListAdapter.notifyDataSetChanged();
                    regionListAdapter.setIndex(0);
                }
                if(result.get(0) instanceof CinemaByRegionList){
                    //清空列表数据
                    if(cinemaByRegionListAdapter != null){
                        cinemaByRegionListAdapter.getList().clear();
                    }
                    //添加数据到集合
                    cinemaByRegionListAdapter.getList().addAll(result);
                    cinemaByRegionListAdapter.notifyDataSetChanged();
                    cinemaByRegionListAdapter.setIndex(0);
                }
            }
        }
    }
    @Override
    public void onFail(String err) {
    }
}
