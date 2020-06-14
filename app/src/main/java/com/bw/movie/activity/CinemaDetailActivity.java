package com.bw.movie.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.CinemasDetail;
import com.bw.movie.bean.CinemasDetailTabBean;
import com.bw.movie.bean.DataBean;
import com.bw.movie.fragment.cinemadetailfragment.CinemaCommentsFragment;
import com.bw.movie.fragment.cinemadetailfragment.CinemaDetailFragment;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.url.MyUrl;
import com.bw.movie.util.NetUtil;
import com.bw.movie.view.TabCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bw.movie.api.ApiService.GET;

/**
 * 影院详情页面
 * 李易泽
 * 20200612
 */
public class CinemaDetailActivity extends BaseActivity {
    //定义
    private Map<String, Object> map;
    private Type type;
    private boolean isFollowCinema;
    private int flag = -1;
    private ImageView back,followCinema;
    private TextView name;
    private TabCardView label;
    private TabLayout cinemaDetailTabLay;
    private ViewPager cinemaDetailViewPag;
    private List<CinemasDetailTabBean> cinemasDetailTabBeanList;
    private int cinemaId;
    //方法实现
    @Override
    protected boolean isFullScreen() {
        return false;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_cinema_detail;
    }
    @Override
    protected BasePresenter initPresenter() {
        return new PresenterImpl();
    }
    @Override
    protected void initView() {
        //初始化
        back = findViewById(R.id.back);
        followCinema = findViewById(R.id.follow_cinema);
        name = findViewById(R.id.name);
        label = findViewById(R.id.label);
        cinemaDetailTabLay = findViewById(R.id.cinema_detail_tab_lay);
        cinemaDetailViewPag = findViewById(R.id.cinema_detail_view_pag);
        //名称跑马显示效果
        name.setSelected(true);
        //初始化Tab页面区域
        cinemasDetailTabBeanList = new ArrayList<>();
        //添加页面
        cinemasDetailTabBeanList.add(new CinemasDetailTabBean("影院详情",new CinemaDetailFragment()));
        cinemasDetailTabBeanList.add(new CinemasDetailTabBean("影院评价",new CinemaCommentsFragment()));
        //设置适配器
        cinemaDetailViewPag.setOffscreenPageLimit(cinemasDetailTabBeanList.size()-1);
        cinemaDetailViewPag.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return cinemasDetailTabBeanList.get(position).getTabTitle();
            }
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return cinemasDetailTabBeanList.get(position).getBaseFragment();
            }
            @Override
            public int getCount() {
                return cinemasDetailTabBeanList.size();
            }
        });
        //关联布局
        cinemaDetailTabLay.setupWithViewPager(cinemaDetailViewPag);
        //获取值
        cinemaId = getIntent().getIntExtra("cinemaId", -1);
        //泛型类处理
        type = new TypeToken<DataBean<CinemasDetail>>() {
        }.getType();
        //设置加载参数
        map = new HashMap<>();
        map.put("cinemaId", cinemaId);
        //返回界面操作
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void startCoding() {
        //关注与取消关注电影
        followCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断网络
                if(NetUtil.getInstance().isConnected()){
                    //判断
                    if(!isFollowCinema){
                        //关注电影
                        flag = 1;
                        mPresenter.startRequest(GET,MyUrl.FOLLOW_CINEMA,type,map);
                    } else {
                        //取消关注电影
                        flag = 1;
                        mPresenter.startRequest(GET,MyUrl.CANCEL_FOLLOW_CINEMA,type,map);
                    }
                } else {
                    //提示
                    Toast.makeText(CinemaDetailActivity.this,"网络似乎开小差了，请检查下网络吧！",Toast.LENGTH_LONG).show();
                }
            }
        });
        //判断网络
        if(NetUtil.getInstance().isConnected()){
            //发起请求
            flag = 0;
            mPresenter.startRequest(GET, MyUrl.FIND_CINEMA_INFO,type,map);
        } else {
        }
    }
    @Override
    protected void initResume() {
    }
    @Override
    protected void initPause() {
    }
    @Override
    protected void initDestroy() {
    }
    @Override
    public void onSuccess(Object o) {
        //instanceof判断
        if(o instanceof DataBean){
            //选择
            switch (flag){
                case 0 : {
                    //获取对象
                    Object result = ((DataBean) o).getResult();
                    //判断是否非空
                    if(result != null){
                        //instanceof判断
                        if(result instanceof CinemasDetail){
                            //设置数据
                            name.setText(((CinemasDetail) result).getName());
                            //拆分类别
                            String lab = ((CinemasDetail) result).getLabel();
                            String[] split = lab.split(",");
                            for (String sli : split) {
                                //执行添加视图方法
                                label.addFlow(sli);
                            }
                            //设置关注状态
                            switch (((CinemasDetail) result).getFollowCinema()){
                                case 1: {
                                    isFollowCinema = true;
                                    followCinema.setImageResource(R.drawable.empty_heart_selected);
                                }break;
                                case 2: {
                                    isFollowCinema = false;
                                    followCinema.setImageResource(R.drawable.empty_heart_noselected);
                                }break;
                            }
                            //其它数据发送粘性事件
                            EventBus.getDefault().postSticky(cinemaId);
                            EventBus.getDefault().postSticky(result);
                        }
                    }
                }break;
                case 1 : {
                    //提示
                    Toast.makeText(this,((DataBean) o).getMessage(),Toast.LENGTH_LONG).show();
                    //判断是否登录
                    if(((DataBean) o).getStatus().equals("9999")){
                        //跳转到登录页面
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        return;
                    }
                    //判断关注/取消关注是否成功
                    if(((DataBean) o).getStatus().equals("0000")){
                        //修改状态
                        if(!isFollowCinema){
                            //调整成取消关注
                            isFollowCinema = true;
                            //修改已关注样式
                            followCinema.setImageResource(R.drawable.empty_heart_selected);
                        } else {
                            //调整成关注
                            isFollowCinema = false;
                            //修改成未关注样式
                            followCinema.setImageResource(R.drawable.empty_heart_noselected);
                        }
                    }
                }break;
            }
        }
    }
    @Override
    public void onFail(String err) {
    }
}
