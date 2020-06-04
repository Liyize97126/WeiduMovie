package com.bw.movie.fragment.cinemafragment;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

/**
 * 影院页面附近影院Tab页
 * 李易泽
 * 20200602
 */
public class NearbyCinemaFragment extends BaseFragment {
    //定义
    private TwinklingRefreshLayout twinkling_rl;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                handler.removeCallbacksAndMessages(null);
                Toast.makeText(getContext(),"无法定位到位置！",Toast.LENGTH_LONG).show();
                twinkling_rl.finishRefreshing();
            }
        }
    };
    //方法实现
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.cinema_fragment_nearby_cinemas;
    }
    @Override
    protected void initViews(View mContentView) {
        //初始化
        twinkling_rl = mContentView.findViewById(R.id.twinkling_rl);
        twinkling_rl.setEnableLoadmore(false);
        twinkling_rl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                handler.sendEmptyMessageDelayed(0,5000);
            }
        });
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    @Override
    protected void lazyLoad() {
        //启动刷新
        twinkling_rl.startRefresh();
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
