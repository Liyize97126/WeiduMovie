package com.bw.movie.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.LoginActivity;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.UserInfo;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 首页我的页面
 * 李易泽
 * 20200529
 */
public class MyFragment extends BaseFragment {
    //定义
    private ImageView allSysMsgDo;
    private SimpleDraweeView headPic;
    private TextView nickName;
    private RelativeLayout loginRegisterDo,myMovieTicketDo;
    //方法实现
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_my;
    }
    @Override
    protected void initViews(View mContentView) {
        //EventBus注册
        EventBus.getDefault().register(this);
        //初始化
        allSysMsgDo = mContentView.findViewById(R.id.all_sys_msg_do);
        headPic = mContentView.findViewById(R.id.head_pic);
        nickName = mContentView.findViewById(R.id.nick_name);
        loginRegisterDo = mContentView.findViewById(R.id.login_register_do);
        myMovieTicketDo = mContentView.findViewById(R.id.my_movie_ticket_do);
        //点击事件
        loginRegisterDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转
                Intent intent = new Intent(getActivity(), LoginActivity.class);
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
    }
    //接收订阅者消息
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void postText(UserInfo userInfo){
        headPic.setImageURI(Uri.parse(userInfo.getHeadPic()));
        nickName.setText(userInfo.getNickName());
    }
    @Override
    protected void initDestroyView() {
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
