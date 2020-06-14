package com.bw.movie.fragment.cinemadetailfragment;

import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.adapter.CinemaDetailCommentsAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.CinemaDetailCommentsList;
import com.bw.movie.bean.DataListBean;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.url.MyUrl;
import com.bw.movie.util.NetUtil;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static com.bw.movie.api.ApiService.GET;

/**
 * 影院评价Fragment
 * 李易泽
 * 20200612
 */
public class CinemaCommentsFragment extends BaseFragment {
    //定义
    private RecyclerView cinemaCommentsRecy;
    private Button addCinemaComment;
    private CinemaDetailCommentsAdapter cinemaDetailCommentsAdapter;
    private Type type;
    private Map<String, Object> map;
    //方法实现
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.cinema_detail_fragment_cinema_comments;
    }
    @Override
    protected void initViews(View mContentView) {
        //EventBus注册
        EventBus.getDefault().register(this);
        //初始化控件
        cinemaCommentsRecy = mContentView.findViewById(R.id.cinema_comments_recy);
        addCinemaComment = mContentView.findViewById(R.id.add_cinema_comment);
        //布局管理器与适配器
        cinemaCommentsRecy.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        cinemaDetailCommentsAdapter = new CinemaDetailCommentsAdapter();
        cinemaCommentsRecy.setAdapter(cinemaDetailCommentsAdapter);
        //泛型类处理
        type = new TypeToken<DataListBean<CinemaDetailCommentsList>>() {
        }.getType();
        //定义集合
        map = new HashMap<>();
        map.put("page",1);
        map.put("count",15);
    }
    @Override
    protected BasePresenter initPresenter() {
        return new PresenterImpl();
    }
    @Override
    protected void lazyLoad() {
        //判断网络
        if(NetUtil.getInstance().isConnected()){
            //请求
            mPresenter.startRequest(GET, MyUrl.FIND_ALL_CINEMA_COMMENT,type,map);
        } else {
        }
    }
    @Override
    protected void initDestroyView() {
    }
    //接收订阅者消息
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void postMovieID(Integer integer){
        //设置加载参数
        map.put("cinemaId", integer);
    }
    @Override
    protected void initDetach() {
        //EventBus取消注册
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onSuccess(Object o) {
        //instanceof判断
        if(o instanceof DataListBean){
            //获取并添加数据
            cinemaDetailCommentsAdapter.getList().addAll(((DataListBean) o).getResult());
            cinemaDetailCommentsAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onFail(String err) {
    }
}
