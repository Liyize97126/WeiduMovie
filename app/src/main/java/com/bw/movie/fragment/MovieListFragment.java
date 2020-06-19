package com.bw.movie.fragment;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.activity.MoviesDetailActivity;
import com.bw.movie.activity.SearchActivity;
import com.bw.movie.adapter.ComingSoonMovieListAdapter;
import com.bw.movie.adapter.HotMovieListAdapter;
import com.bw.movie.adapter.ReleaseMovieListAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.BannerData;
import com.bw.movie.bean.ComingSoonMovieList;
import com.bw.movie.bean.DataListBean;
import com.bw.movie.bean.HotMovieList;
import com.bw.movie.bean.ReleaseMovieList;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.url.MyUrl;
import com.bw.movie.util.NetUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.reflect.TypeToken;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.stx.xhb.androidx.XBanner;

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
    private XBanner xBan;
    private ImageView searchDo;
    private TextView xBanNum,name,score,locationName;
    private SimpleDraweeView horizontalImage;
    private RelativeLayout jumpMovieDetail;
    private RecyclerView mZZRYRecy,mJJSYRecy,mRNDYRecy;
    private boolean listData1,listData2,listData3;
    private ReleaseMovieListAdapter releaseMovieListAdapter;
    private ComingSoonMovieListAdapter comingSoonMovieListAdapter;
    private HotMovieListAdapter hotMovieListAdapter;
    private Map<String, Object> releaseMovieMap,comingSoonMovieMap,hotMovieMap;
    private Type releaseMovieType,comingSoonMovieType,hotMovieType,bannerType;
    private int bannerNumber;
    private boolean isPositioning = false;
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient;
    //构造
    public MovieListFragment(AMapLocationClient mLocationClient) {
        this.mLocationClient = mLocationClient;
    }
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
        xBan = mContentView.findViewById(R.id.x_ban);
        searchDo = mContentView.findViewById(R.id.search_do);
        xBanNum = mContentView.findViewById(R.id.x_ban_num);
        name = mContentView.findViewById(R.id.name);
        score = mContentView.findViewById(R.id.score);
        locationName = mContentView.findViewById(R.id.location_name);
        horizontalImage = mContentView.findViewById(R.id.horizontal_image);
        twinklingRl = mContentView.findViewById(R.id.twinkling_rl);
        jumpMovieDetail = mContentView.findViewById(R.id.jump_movie_detail);
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
        bannerType = new TypeToken<DataListBean<BannerData>>() {
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
        hotMovieMap.put("count",6);
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
                intent.putExtra("flag",SearchActivity.SEARCH_MOVIE);
                startActivity(intent);
            }
        });
        //TwinklingRefreshLayout设置
        //禁用上拉加载
        twinklingRl.setEnableLoadmore(false);
        //刷新监听
        twinklingRl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                //判断网络
                if(NetUtil.getInstance().isConnected()){
                    //启动定位
                    locationName.setText("定位中...");
                    isPositioning = true;
                    mLocationClient.startLocation();
                    //更改flag值
                    listData1 = false;
                    listData2 = false;
                    listData3 = false;
                    //发起请求
                    mPresenter.startRequest(GET, MyUrl.FIND_RELEASE_MOVIE_LIST, releaseMovieType ,releaseMovieMap);
                    mPresenter.startRequest(GET,MyUrl.FIND_COMING_SOON_MOVIE_LIST, comingSoonMovieType, comingSoonMovieMap);
                    mPresenter.startRequest(GET,MyUrl.FIND_HOT_MOVIE_LIST,hotMovieType,hotMovieMap);
                    mPresenter.startRequest(GET,MyUrl.BANNER,bannerType,null);
                } else {
                    locationName.setText("请连接网络");
                    twinklingRl.finishRefreshing();
                }
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
        //设置Banner页面变化监听
        xBan.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                //设置数据
                xBanNum.setText((position + 1) + "/" + bannerNumber);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
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
    @Override
    protected void initDetach() {
    }
    //成功回调
    @Override
    public void onSuccess(Object o) {
        //instanceof判断
        if(o instanceof DataListBean){
            //获取集合
            final List result = ((DataListBean) o).getResult();
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
                        jumpMovieDetail.setVisibility(View.GONE);
                    }
                    //显示大图框架
                    jumpMovieDetail.setVisibility(View.VISIBLE);
                    //获取电影ID
                    int movieId = (int) ((HotMovieList) result.get(0)).getMovieId();
                    //传值
                    jumpMovieDetail.setTag(movieId);
                    //设置数据
                    name.setText(((HotMovieList) result.get(0)).getName());
                    score.setText(((HotMovieList) result.get(0)).getScore() + "分");
                    //设置图片
                    ImageRequest build = ImageRequestBuilder.newBuilderWithSource(Uri.parse(((HotMovieList) result.get(0)).getHorizontalImage()))
                            .setProgressiveRenderingEnabled(true).build();
                    AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(build)
                            .build();
                    horizontalImage.setController(controller);
                    //设置点击事件
                    jumpMovieDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //调用跳转方法
                            jumpMovieDetailActivity((Integer) v.getTag());
                        }
                    });
                    //删除第一条数据
                    result.remove(0);
                    //添加数据到集合
                    hotMovieListAdapter.getList().addAll(result);
                    hotMovieListAdapter.notifyDataSetChanged();
                    listData3 = true;
                }
                if(result.get(0) instanceof BannerData){
                    //设置Banner总数
                    bannerNumber = result.size();
                    //设置XBanner
                    xBan.setBannerData(result);
                    //开始加载图片
                    xBan.loadImage(new XBanner.XBannerAdapter() {
                        @Override
                        public void loadBanner(XBanner banner, Object model, View view, int position) {
                            //展示图片
                            Glide.with(view.getContext())
                                    .load(((BannerData) result.get(position)).getImageUrl())
                                    .into((ImageView) view);
                        }
                    });
                }
                //判断是否刷新完成
                if(listData1 || listData2 || listData3){
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
