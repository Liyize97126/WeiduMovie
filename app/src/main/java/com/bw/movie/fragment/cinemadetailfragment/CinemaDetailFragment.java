package com.bw.movie.fragment.cinemadetailfragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.CinemasDetail;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 影院详情Fragment
 * 李易泽
 * 20200612
 */
public class CinemaDetailFragment extends BaseFragment {
    //定义
    private TextView address,phone,vehicleRoute;
    private Button cinemaScheduleListDo;
    //方法实现
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.cinema_detail_fragment_cinema_detail;
    }
    @Override
    protected void initViews(View mContentView) {
        //EventBus注册
        EventBus.getDefault().register(this);
        //初始化控件
        address = mContentView.findViewById(R.id.address);
        phone = mContentView.findViewById(R.id.phone);
        vehicleRoute = mContentView.findViewById(R.id.vehicle_route);
        cinemaScheduleListDo = mContentView.findViewById(R.id.cinema_schedule_list_do);
        //点击事件
        cinemaScheduleListDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        //地址跑马显示效果
        address.setSelected(true);
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    @Override
    protected void lazyLoad() {
    }
    @Override
    protected void initDestroyView() {
    }
    //接收订阅者消息
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void postCinemaDetailListBean(Object obj){
        //确认数据类型
        if(obj instanceof CinemasDetail) {
            //添加数据
            address.setText(((CinemasDetail) obj).getAddress());
            phone.setText(((CinemasDetail) obj).getPhone());
            vehicleRoute.setText(((CinemasDetail) obj).getVehicleRoute());
        }
    }
    @Override
    protected void initDetach() {
        //EventBus取消注册
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onSuccess(Object o) {
    }
    @Override
    public void onFail(String err) {
    }
}
