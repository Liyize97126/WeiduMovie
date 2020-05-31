package com.bw.movie.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.CinemaTabBean;
import com.bw.movie.fragment.cinemafragment.RecommendCinemaFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页影院列表
 * 李易泽
 * 20200530
 */
public class CinemaListFragment extends BaseFragment {
    //定义
    private TextView locationName;
    private ImageView searchDo;
    private TabLayout cinemaTabLay;
    private ViewPager cinemaViewPag;
    private List<CinemaTabBean> list;
    //方法实现
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_cinema_list;
    }
    @Override
    protected void initViews(View mContentView) {
        //初始化
        locationName = mContentView.findViewById(R.id.location_name);
        searchDo = mContentView.findViewById(R.id.search_do);
        cinemaTabLay = mContentView.findViewById(R.id.cinema_tab_lay);
        cinemaViewPag = mContentView.findViewById(R.id.cinema_view_pag);
        list = new ArrayList<>();
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    @Override
    protected void lazyLoad() {
        //加载Tab页
        list.add(new CinemaTabBean("推荐影院",new RecommendCinemaFragment()));
        list.add(new CinemaTabBean("附近影院",new RecommendCinemaFragment()));
        list.add(new CinemaTabBean("海淀区 ▼",new RecommendCinemaFragment()));
        //设置适配器
        cinemaViewPag.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position).getTabTitle();
            }
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return list.get(position).getBaseFragment();
            }
            @Override
            public int getCount() {
                return list.size();
            }
        });
        //关联
        cinemaTabLay.setupWithViewPager(cinemaViewPag);
    }
    @Override
    protected void initDestroyView() {
    }
    @Override
    public void onSuccess(Object o) {
    }
    @Override
    public void onFail(String err) {
    }
}
