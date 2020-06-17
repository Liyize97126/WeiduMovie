package com.bw.movie.activity;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.CinemaScheduleTabBean;
import com.bw.movie.fragment.cinemaschedulefragment.CinemaScheduleFragment;
import com.bw.movie.util.TimesFormatUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 影院排期
 * 李易泽
 * 20200615
 */
public class CinemaScheduleActivity extends BaseActivity {
    //定义
    private ImageView back;
    private TabLayout cinemaScheduleTabLay;
    private ViewPager cinemaScheduleViewPag;
    private List<CinemaScheduleTabBean> cinemaScheduleTabBeanList;
    private Date date1,date2,date3,date4,date5,date6,date7;
    private Calendar calendar;
    private int cinemaId;
    //方法实现
    @Override
    protected boolean isFullScreen() {
        return false;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_cinema_schedule;
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    @Override
    protected void initView() {
        //Id
        back = findViewById(R.id.back);
        cinemaScheduleTabLay = findViewById(R.id.cinema_schedule_tab_lay);
        cinemaScheduleViewPag = findViewById(R.id.cinema_schedule_view_pag);
        //初始化对象
        date1 = new Date();
        //获取Calendar类
        calendar = Calendar.getInstance();
        //初始化集合
        cinemaScheduleTabBeanList = new ArrayList<>();
        //设置当前时间
        calendar.setTime(date1);
        //日期累加
        calendar.add(Calendar.DATE,1);
        date2 = calendar.getTime();
        calendar.add(Calendar.DATE,1);
        date3 = calendar.getTime();
        calendar.add(Calendar.DATE,1);
        date4 = calendar.getTime();
        calendar.add(Calendar.DATE,1);
        date5 = calendar.getTime();
        calendar.add(Calendar.DATE,1);
        date6 = calendar.getTime();
        calendar.add(Calendar.DATE,1);
        date7 = calendar.getTime();
        //返回界面操作
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //获取数据
        cinemaId = getIntent().getIntExtra("cinemaId",-1);
        //判断
        if(cinemaId != -1){
            //添加页面
            cinemaScheduleTabBeanList.add(new CinemaScheduleTabBean(TimesFormatUtil.timeFormatThirdStyleToday(date1.getTime()),new CinemaScheduleFragment(cinemaId)));
            cinemaScheduleTabBeanList.add(new CinemaScheduleTabBean(TimesFormatUtil.timeFormatThirdStyleTomorrow(date2.getTime()),new CinemaScheduleFragment(cinemaId)));
            cinemaScheduleTabBeanList.add(new CinemaScheduleTabBean(TimesFormatUtil.timeFormatThirdStyleAfterTomorrow(date3.getTime()),new CinemaScheduleFragment(cinemaId)));
            cinemaScheduleTabBeanList.add(new CinemaScheduleTabBean(TimesFormatUtil.timeFormatThird(date4.getTime()),new CinemaScheduleFragment(cinemaId)));
            cinemaScheduleTabBeanList.add(new CinemaScheduleTabBean(TimesFormatUtil.timeFormatThird(date5.getTime()),new CinemaScheduleFragment(cinemaId)));
            cinemaScheduleTabBeanList.add(new CinemaScheduleTabBean(TimesFormatUtil.timeFormatThird(date6.getTime()),new CinemaScheduleFragment(cinemaId)));
            cinemaScheduleTabBeanList.add(new CinemaScheduleTabBean(TimesFormatUtil.timeFormatThird(date7.getTime()),new CinemaScheduleFragment(cinemaId)));
            //设置适配器
            cinemaScheduleViewPag.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Nullable
                @Override
                public CharSequence getPageTitle(int position) {
                    return cinemaScheduleTabBeanList.get(position).getTabTitle();
                }
                @NonNull
                @Override
                public Fragment getItem(int position) {
                    return cinemaScheduleTabBeanList.get(position).getBaseFragment();
                }
                @Override
                public int getCount() {
                    return cinemaScheduleTabBeanList.size();
                }
            });
            //关联
            cinemaScheduleTabLay.setupWithViewPager(cinemaScheduleViewPag);
        } else {
            throw new IllegalArgumentException("参数读取异常！");
        }
    }
    @Override
    protected void startCoding() {
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
    }
    @Override
    public void onFail(String err) {
    }
}
