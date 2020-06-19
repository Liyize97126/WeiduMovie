package com.bw.movie.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.bw.movie.R;
import com.bw.movie.activity.SearchActivity;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.CinemaTabBean;
import com.bw.movie.fragment.cinemafragment.CinemaByRegionFragment;
import com.bw.movie.fragment.cinemafragment.NearbyCinemaFragment;
import com.bw.movie.fragment.cinemafragment.RecommendCinemaFragment;
import com.bw.movie.util.NetUtil;
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
    private boolean isPositioning = false;
    private NearbyCinemaFragment nearbyCinemaFragment;
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient;
    //构造
    public CinemaListFragment(AMapLocationClient mLocationClient) {
        this.mLocationClient = mLocationClient;
    }
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
        nearbyCinemaFragment = new NearbyCinemaFragment();
        //定位功能
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //解析定位结果
                        String province = aMapLocation.getProvince();//省信息
                        String city = aMapLocation.getCity();//城市信息
                        nearbyCinemaFragment.setLongitude(String.valueOf(aMapLocation.getLongitude()));
                        nearbyCinemaFragment.setLatitude(String.valueOf(aMapLocation.getLatitude()));
                        //提示
                        locationName.setText(province + city);
                    } else {
                        //错误提示
                        locationName.setText("定位出错");
                        Toast.makeText(getContext(),"无法获取位置，请重试！",Toast.LENGTH_LONG).show();
                        Log.i("Location",aMapLocation.getErrorCode() + ":" + aMapLocation.getErrorInfo());
                    }
                }
                //结束定位
                isPositioning = false;
                mLocationClient.stopLocation();
            }
        });
        //设置点击事件
        locationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断定位状态
                if(isPositioning){
                    Toast.makeText(getContext(),"正在定位中，请稍等……",Toast.LENGTH_LONG).show();
                } else {
                    //判断网络
                    if(NetUtil.getInstance().isConnected()){
                        //重新定位
                        isPositioning = true;
                        locationName.setText("定位中...");
                        mLocationClient.startLocation();
                    } else {
                        locationName.setText("请连接网络");
                    }
                }
            }
        });
        searchDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行跳转
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("flag",SearchActivity.SEARCH_CINEMA);
                startActivity(intent);
            }
        });
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    @Override
    protected void lazyLoad() {
        //加载Tab页
        list.add(new CinemaTabBean("推荐影院",new RecommendCinemaFragment()));
        list.add(new CinemaTabBean("附近影院",nearbyCinemaFragment));
        list.add(new CinemaTabBean("海淀区 ▼",new CinemaByRegionFragment()));
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
        //判断网络
        if(NetUtil.getInstance().isConnected()) {
            //启动定位
            locationName.setText("定位中...");
            isPositioning = true;
            mLocationClient.startLocation();
        } else {
            locationName.setText("请连接网络");
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
    }
    @Override
    public void onFail(String err) {
    }
}
