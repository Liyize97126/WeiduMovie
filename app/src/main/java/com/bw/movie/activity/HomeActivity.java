package com.bw.movie.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.fragment.MovieListFragment;
import com.bw.movie.presenter.PresenterImpl;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页视图
 * 李易泽
 * 20200524
 */
public class HomeActivity extends BaseActivity {
    //定义
    private ViewPager viewPag;
    private TabLayout tabLay;
    private List<BaseFragment> fragments;
    //方法实现
    //是否全屏
    @Override
    protected boolean isFullScreen() {
        return false;
    }
    //设置布局
    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }
    //初始化Presenter
    @Override
    protected BasePresenter initPresenter() {
        return new PresenterImpl();
    }
    //初始化视图
    @Override
    protected void initView() {
        viewPag = findViewById(R.id.view_pag);
        tabLay = findViewById(R.id.tab_lay);
        fragments = new ArrayList<>();
        fragments.add(new MovieListFragment());
        fragments.add(new MovieListFragment());
        fragments.add(new MovieListFragment());
        //设置适配器
        viewPag.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        //关联
        tabLay.setupWithViewPager(viewPag);
        //设置自定义视图
        for (int i = 0; i < fragments.size(); i++) {
            tabLay.getTabAt(i).setCustomView(R.layout.view_per_tab);
            View customView = tabLay.getTabAt(i).getCustomView();
            TextView textView = customView.findViewById(R.id.z_tab_text);
            ImageView imageView = customView.findViewById(R.id.z_tab_image);
            LinearLayout linearLayout = customView.findViewById(R.id.z_tab_background);
            switch (i){
                case 0:{
                    linearLayout.setBackgroundResource(R.drawable.tab_selected);
                    imageView.setImageResource(R.drawable.movie_red);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("电影");
                }break;
                case 1:{
                    linearLayout.setBackgroundResource(R.drawable.tab_no_selected);
                    imageView.setImageResource(R.drawable.yingyuan_bai);
                    textView.setVisibility(View.GONE);
                    textView.setText("影院");
                }break;
                case 2:{
                    linearLayout.setBackgroundResource(R.drawable.tab_no_selected);
                    imageView.setImageResource(R.drawable.my_bai);
                    textView.setVisibility(View.GONE);
                    textView.setText("我的");
                }break;
            }
        }
        //切换监听
        tabLay.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                TextView textView = customView.findViewById(R.id.z_tab_text);
                ImageView imageView = customView.findViewById(R.id.z_tab_image);
                LinearLayout linearLayout = customView.findViewById(R.id.z_tab_background);
                linearLayout.setBackgroundResource(R.drawable.tab_selected);
                textView.setVisibility(View.VISIBLE);
                int position = tab.getPosition();
                switch (position){
                    case 0:{
                        imageView.setImageResource(R.drawable.movie_red);
                    }break;
                    case 1:{
                        imageView.setImageResource(R.drawable.yingyuan_red);
                    }break;
                    case 2:{
                        imageView.setImageResource(R.drawable.my_red);
                    }break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                TextView textView = customView.findViewById(R.id.z_tab_text);
                ImageView imageView = customView.findViewById(R.id.z_tab_image);
                LinearLayout linearLayout = customView.findViewById(R.id.z_tab_background);
                linearLayout.setBackgroundResource(R.drawable.tab_no_selected);
                textView.setVisibility(View.GONE);
                int position = tab.getPosition();
                switch (position){
                    case 0:{
                        imageView.setImageResource(R.drawable.movie_bai);
                    }break;
                    case 1:{
                        imageView.setImageResource(R.drawable.yingyuan_bai);
                    }break;
                    case 2:{
                        imageView.setImageResource(R.drawable.my_bai);
                    }break;
                }
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    //执行其他代码
    @Override
    protected void startCoding() {
    }
    //Resume
    @Override
    protected void initResume() {
    }
    //Pause
    @Override
    protected void initPause() {
    }
    //释放资源
    @Override
    protected void initDestroy() {
    }
    //请求成功
    @Override
    public void onSuccess(Object o) {
    }
    //请求失败
    @Override
    public void onFail(String err) {
    }
}
