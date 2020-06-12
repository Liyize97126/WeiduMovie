package com.bw.movie.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.adapter.MovieSearchResultListAdapter;
import com.bw.movie.adapter.ReleaseMovieListAdapter;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.DataListBean;
import com.bw.movie.bean.MovieSearchResultList;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.url.MyUrl;
import com.bw.movie.util.NetUtil;
import com.google.gson.reflect.TypeToken;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bw.movie.api.ApiService.GET;

/**
 * 搜索界面
 * 李易泽
 * 20200611
 */
public class SearchActivity extends BaseActivity {
    //定义
    public static final int SEARCH_MOVIE = 0;
    public static final int SEARCH_CINEMA = 1;
    public static final int SEARCH_ERROR = -1;
    private ImageView back;
    private EditText keyword;
    private TwinklingRefreshLayout twinklingRl;
    private RecyclerView searchResultRecy;
    private int flag;
    private int page = 1;
    private String url;
    private Type type;
    private Map<String, Object> map;
    private MovieSearchResultListAdapter movieSearchResultListAdapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //请求查询
            if(msg.what == 0){
                mPresenter.startRequest(GET,url,type,map);
            }
        }
    };
    //方法实现
    @Override
    protected boolean isFullScreen() {
        return false;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }
    @Override
    protected BasePresenter initPresenter() {
        return new PresenterImpl();
    }
    @Override
    protected void initView() {
        //初始化
        back = findViewById(R.id.back);
        keyword = findViewById(R.id.keyword);
        twinklingRl = findViewById(R.id.twinkling_rl);
        searchResultRecy = findViewById(R.id.search_result_recy);
        map = new HashMap<>();
        //TwinklingRefreshLayout设置
        //禁用下拉刷新
        twinklingRl.setEnableRefresh(false);
        //加载更多监听
        twinklingRl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                //判断网络
                if(NetUtil.getInstance().isConnected()){
                    //请求数据
                    page++;
                    map.put("page",page);
                    mPresenter.startRequest(GET,url,type,map);
                } else {
                    twinklingRl.finishLoadmore();
                }
            }
        });
        //设置布局管理器
        searchResultRecy.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //接收值
        flag = getIntent().getIntExtra("flag", SEARCH_ERROR);
        //选择
        switch (flag){
            case SEARCH_MOVIE:{
                //代表搜索电影，设置文本框提示文本
                keyword.setHint("请输入电影名称");
                //设置电影列表适配器
                movieSearchResultListAdapter = new MovieSearchResultListAdapter();
                searchResultRecy.setAdapter(movieSearchResultListAdapter);
                movieSearchResultListAdapter.setDataCallBack(new ReleaseMovieListAdapter.DataCallBack() {
                    @Override
                    public void jumpMovieDetail(long movieId) {
                        //跳转
                        Intent intent = new Intent(SearchActivity.this, MoviesDetailActivity.class);
                        //传值
                        intent.putExtra("movieId", (int) movieId);
                        //完成跳转
                        startActivity(intent);
                    }
                });
                //请求的链接
                url = MyUrl.FIND_MOVIE_BY_KEYWORD;
                //泛型类处理
                type = new TypeToken<DataListBean<MovieSearchResultList>>() {
                }.getType();
                //设置加载参数
                map.put("keyword",keyword.getText().toString());
                map.put("page",page);
                map.put("count",10);
            }break;
            case SEARCH_CINEMA:{}break;
            case SEARCH_ERROR:{
                throw new IllegalArgumentException("参数读取异常！");
            }
        }
    }
    @Override
    protected void startCoding() {
        //点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //准备请求
        //判断网络
        if(NetUtil.getInstance().isConnected()){
            //请求数据
            mPresenter.startRequest(GET,url,type,map);
        } else {
        }
        //文本框监听
        keyword.addTextChangedListener(new TextWatcher() {
            //文本变化前执行
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            //文本发生变化时执行
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //清除旧消息
                handler.removeCallbacksAndMessages(null);
                //判断网络
                if(NetUtil.getInstance().isConnected()){
                    //设置参数
                    switch (flag){
                        case SEARCH_MOVIE:{
                            map.put("keyword",keyword.getText().toString());
                        }break;
                        case SEARCH_CINEMA:{}break;
                    }
                    //修改页数
                    page = 1;
                    map.put("page",page);
                    //发送消息
                    handler.sendEmptyMessageDelayed(0,1000);
                } else {
                }
            }
            //文本变化后执行
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    @Override
    protected void initResume() {
    }
    @Override
    protected void initPause() {
    }
    @Override
    protected void initDestroy() {
        //清除消息
        handler.removeCallbacksAndMessages(null);
    }
    @Override
    public void onSuccess(Object o) {
        //instanceof判断
        if(o instanceof DataListBean){
            //获取集合
            List result = ((DataListBean) o).getResult();
            //判断集合是否有数据
            if(result != null){
                //instanceof判断
                if(result.get(0) instanceof MovieSearchResultList){
                    //清空数据
                    if(page == 1){
                        movieSearchResultListAdapter.getList().clear();
                    }
                    //添加数据并刷新
                    movieSearchResultListAdapter.getList().addAll(result);
                    movieSearchResultListAdapter.notifyDataSetChanged();
                }
            } else {
                //提示
                Toast.makeText(this,((DataListBean) o).getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        //停止加载更多
        twinklingRl.finishLoadmore();
    }
    @Override
    public void onFail(String err) {
    }
}
