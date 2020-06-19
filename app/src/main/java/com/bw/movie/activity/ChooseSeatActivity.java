package com.bw.movie.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.xuanzuolibrary.XZView;
import com.bawei.xuanzuolibrary.bean.SeatBean;
import com.bawei.xuanzuolibrary.bean.SeatInfo;
import com.bw.movie.R;
import com.bw.movie.adapter.HallAndTimeListAdapter;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.DataListBean;
import com.bw.movie.bean.HallAndTimeList;
import com.bw.movie.bean.SeatList;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.url.MyUrl;
import com.bw.movie.util.NetUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bw.movie.api.ApiService.GET;

/**
 * 选座页面
 * 李易泽
 * 20200618
 */
public class ChooseSeatActivity extends BaseActivity {
    //定义
    private ImageView back;
    private TextView movieName,hallNum;
    private XZView xzV;
    private RecyclerView hallAndTimeRecy;
    private Button buyMovieTicketsDo;
    private HallAndTimeListAdapter hallAndTimeListAdapter;
    private Map<String, Object> movieScheduleMap,seatInfoMap;
    private Type movieScheduleType,seatInfoType;
    private int movieId,cinemaId,hallId;
    private int flag = -1;
    private List<SeatBean> list;
    private String name;
    private String chooseSeat = "";
    private StringBuffer stringBuffer;
    private double fare = 0.00;
    //方法实现
    @Override
    protected boolean isFullScreen() {
        return false;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_seat;
    }
    @Override
    protected BasePresenter initPresenter() {
        return new PresenterImpl();
    }
    @Override
    protected void initView() {
        //获取ID
        back = findViewById(R.id.back);
        movieName = findViewById(R.id.movie_name);
        hallNum = findViewById(R.id.hall_num);
        xzV = findViewById(R.id.xz_v);
        hallAndTimeRecy = findViewById(R.id.hall_and_time_recy);
        buyMovieTicketsDo = findViewById(R.id.buy_movie_tickets_do);
        //设置按钮状态为禁用
        buyMovieTicketsDo.setEnabled(false);
        //设置布局管理器
        hallAndTimeRecy.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false));
        //设置适配器
        hallAndTimeListAdapter = new HallAndTimeListAdapter();
        hallAndTimeRecy.setAdapter(hallAndTimeListAdapter);
        //设置回调事件
        xzV.setDataCallBack(new XZView.DataCallBack() {
            @Override
            public void dataCall(List<SeatInfo> usedSeatInfoList) {
                stringBuffer = new StringBuffer();
                for (SeatInfo sa : usedSeatInfoList) {
                    //添加数据
                    stringBuffer.append(sa.getRow() + "-" + sa.getSeat() + ",");
                }
                //汇总数据
                chooseSeat = stringBuffer.toString();
                //判断
                if(!TextUtils.isEmpty(chooseSeat)){
                    //切割字符串
                    chooseSeat = chooseSeat.substring(0,chooseSeat.length()-1);
                }
                //判断座位数
                if(usedSeatInfoList.size() > 0){
                    //设置按钮状态为启用
                    buyMovieTicketsDo.setEnabled(true);
                    //设置背景颜色
                    buyMovieTicketsDo.setBackgroundResource(R.drawable.detail_button_second);
                    //设置价格
                    buyMovieTicketsDo.setText("￥" + (fare * usedSeatInfoList.size()));
                } else {
                    //设置按钮状态为禁用
                    buyMovieTicketsDo.setEnabled(false);
                    //设置背景颜色
                    buyMovieTicketsDo.setBackgroundResource(R.drawable.button_style_pink_no_corners);
                    //提示文本
                    buyMovieTicketsDo.setText("请先选座");
                }
            }
        });
        hallAndTimeListAdapter.setItemClick(new HallAndTimeListAdapter.ItemClick() {
            @Override
            public void setOnItemClick(int hallId) {
                //判断值是否一致
                if(ChooseSeatActivity.this.hallId != hallId){
                    //设置按钮状态为禁用
                    buyMovieTicketsDo.setEnabled(false);
                    //设置背景颜色
                    buyMovieTicketsDo.setBackgroundResource(R.drawable.button_style_pink_no_corners);
                    //提示文本
                    buyMovieTicketsDo.setText("请先选座");
                    //判断网络
                    if(NetUtil.getInstance().isConnected()){
                        //发起请求
                        flag = 1;
                        ChooseSeatActivity.this.hallId = hallId;
                        seatInfoMap.put("hallId",hallId);
                        mPresenter.startRequest(GET, MyUrl.FIND_SEAT_INFO,seatInfoType,seatInfoMap);
                    } else {
                        Toast.makeText(ChooseSeatActivity.this,"网络连接异常，无法获取座位信息！",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        //泛型类处理
        movieScheduleType = new TypeToken<DataListBean<HallAndTimeList>>() {
        }.getType();
        seatInfoType = new TypeToken<DataListBean<SeatList>>(){
        }.getType();
        //设置List
        list = new ArrayList<>();
        //设置Map
        movieScheduleMap = new HashMap<>();
        seatInfoMap = new HashMap<>();
        //返回界面操作
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //点击事件
        buyMovieTicketsDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提示
                Toast.makeText(ChooseSeatActivity.this,"您选择了：" + chooseSeat,Toast.LENGTH_LONG).show();
            }
        });
        //获取值
        Intent intent = getIntent();
        movieId = intent.getIntExtra("movieId",-1);
        cinemaId = intent.getIntExtra("cinemaId", -1);
        name = intent.getStringExtra("name");
        //设置标题
        movieName.setText(name);
        //判断
        if(movieId != -1 && cinemaId != -1){
            //设置加载参数
            movieScheduleMap.put("movieId", movieId);
            movieScheduleMap.put("cinemaId", cinemaId);
        } else {
            throw new IllegalArgumentException("参数异常！");
        }
    }
    @Override
    protected void startCoding() {
        //判断网络
        if(NetUtil.getInstance().isConnected()){
            //发起请求
            flag = 0;
            mPresenter.startRequest(GET, MyUrl.FIND_MOVIE_SCHEDULE,movieScheduleType,movieScheduleMap);
        } else {
            Toast.makeText(ChooseSeatActivity.this,"网络连接异常，无法获取影厅信息！",Toast.LENGTH_LONG).show();
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
        if(o instanceof DataListBean){
            //选择
            switch (flag){
                case 0:{
                    //获取对象
                    List result = ((DataListBean) o).getResult();
                    //判断是否非空
                    if(result != null){
                        if(result.size() > 0){
                            //instanceof判断
                            Object o1 = result.get(0);
                            if(o1 instanceof HallAndTimeList){
                                //添加数据
                                hallAndTimeListAdapter.getList().addAll(result);
                                hallAndTimeListAdapter.notifyDataSetChanged();
                                //设置影厅总数
                                hallNum.setText("（" + result.size() + "）");
                                //设置选中状态
                                hallAndTimeListAdapter.setIndex(0);
                                //设置单价
                                fare = ((HallAndTimeList) o1).getFare();
                                //发起选座请求
                                flag = 1;
                                int hallId = ((HallAndTimeList) o1).getHallId();
                                seatInfoMap.put("hallId",hallId);
                                ChooseSeatActivity.this.hallId = hallId;
                                mPresenter.startRequest(GET, MyUrl.FIND_SEAT_INFO,seatInfoType,seatInfoMap);
                            }
                        }
                    }
                }break;
                case 1:{
                    //获取对象
                    List result = ((DataListBean) o).getResult();
                    //判断是否非空
                    if(result != null) {
                        if (result.size() > 0) {
                            //instanceof判断
                            if(result.get(0) instanceof SeatList){
                                //清空原有数据
                                chooseSeat = "";
                                xzV.removeAllViews();
                                list.clear();
                                //遍历集合
                                for (int i = 0; i < result.size(); i++) {
                                    //添加数据
                                    list.add(new SeatBean(((SeatList) result.get(i)).getRow(),((SeatList) result.get(i)).getSeat(),((SeatList) result.get(i)).getStatus()));
                                }
                                //添加数据至集合
                                xzV.addSeat(this,list);
                            }
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
