package com.bw.movie.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bawei.xtoastlibrary.XToast;
import com.bawei.xtoastlibrary.utils.AnimationUtils;
import com.bw.movie.R;
import com.bw.movie.api.ApiService;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.DataBean;
import com.bw.movie.bean.UserInfo;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.url.MyUrl;
import com.bw.movie.util.MyApplication;
import com.bw.movie.util.NetUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Type;

import static com.bw.movie.api.ApiService.GET;

/**
 * 用户信息页面
 * 李易泽
 * 20200619
 */
public class UserInfoActivity extends BaseActivity {
    //定义
    private static final int REQUEST_CODE = 150;
    private ImageView back;
    private SimpleDraweeView headPic;
    private TextView nickName,sex,email;
    private Type type;
    private int flag = -1;
    private RelativeLayout listFirst;
    private PopupWindow popupWindow;
    //方法实现
    @Override
    protected boolean isFullScreen() {
        return false;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }
    @Override
    protected BasePresenter initPresenter() {
        return new PresenterImpl();
    }
    @Override
    protected void initView() {
        //获取ID
        back = findViewById(R.id.back);
        headPic = findViewById(R.id.head_pic);
        nickName = findViewById(R.id.nick_name);
        sex = findViewById(R.id.sex);
        email = findViewById(R.id.email);
        listFirst = findViewById(R.id.list_first);
        //泛型类处理
        type = new TypeToken<DataBean<UserInfo>>() {
        }.getType();
        //返回界面操作
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //加载子布局
        View view = View.inflate(this, R.layout.view_popup_window_image_upload, null);
        //点击事件
        view.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭PopupWindow
                popupWindow.dismiss();
                //打开系统相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        view.findViewById(R.id.choose_from_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭PopupWindow
                popupWindow.dismiss();
                //打开系统图库
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, ApiService.REQUEST_CODE);
            }
        });
        //创建PopupWindow
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置焦点
        popupWindow.setFocusable(true);
        //设置可以触摸弹出框以外的区域
        popupWindow.setOutsideTouchable(true);
        //点击事件
        listFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示PopupWindow
                popupWindow.showAtLocation(v, Gravity.BOTTOM,0,0);
            }
        });
    }
    @Override
    protected void startCoding() {
        //判断网络
        if(NetUtil.getInstance().isConnected()){
            //发起请求
            flag = 0;
            mPresenter.startRequest(GET, MyUrl.GET_USER_INFO_BY_USER_ID,type,null);
        } else {
            //提示
            Toast.makeText(UserInfoActivity.this,"设备未联网，请联网后重试！",Toast.LENGTH_LONG).show();
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
    //接收结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ApiService.REQUEST_CODE && data != null) {
            //判断网络
            if(NetUtil.getInstance().isConnected()){
                try {
                    //获取Uri
                    Uri uri = data.getData();
                    //转换成Bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    //判断
                    if (bitmap != null) {
                        //创建文件夹
                        File file = new File(getExternalCacheDir().getAbsoluteFile() + "/upload");
                        file.mkdirs();
                        //输出流
                        FileOutputStream outputStream = new FileOutputStream(getExternalCacheDir().getAbsoluteFile() + "/upload/after.jpg");
                        //质量压缩
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //读取文件
                File file = new File(getExternalCacheDir().getAbsoluteFile() + "/upload/after.jpg");
                //上传请求
                flag = 1;
                mPresenter.startRequest(MyUrl.UPLOAD_HEAD_PIC,type,file);
            } else {
                //提示
                Toast.makeText(UserInfoActivity.this,"设备未联网，请联网后重试！",Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_CODE && data != null){
            //判断网络
            if(NetUtil.getInstance().isConnected()){
                //格式转换
                try {
                    //获取Bitmap
                    Bitmap bitmap = data.getParcelableExtra("data");
                    //判断
                    if (bitmap != null) {
                        //创建文件夹
                        File file = new File(getExternalCacheDir().getAbsoluteFile() + "/upload");
                        file.mkdirs();
                        //输出流
                        FileOutputStream outputStream = new FileOutputStream(getExternalCacheDir().getAbsoluteFile() + "/upload/after.jpg");
                        //质量压缩
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //读取文件
                File file = new File(getExternalCacheDir().getAbsoluteFile() + "/upload/after.jpg");
                //上传请求
                flag = 1;
                mPresenter.startRequest(MyUrl.UPLOAD_HEAD_PIC,type,file);
            } else {
                //提示
                Toast.makeText(UserInfoActivity.this,"设备未联网，请联网后重试！",Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onSuccess(Object o) {
        //instanceof判断
        if(o instanceof DataBean){
            //判断是否登录
            if(((DataBean) o).getStatus().equals("9999")){
                //跳转到登录页面
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return;
            }
            //选择
            switch (flag){
                case 0 : {
                    Object result = ((DataBean) o).getResult();
                    //判断是否非空
                    if(result != null){
                        //instanceof判断
                        if(result instanceof UserInfo){
                            //设置数据
                            nickName.setText(((UserInfo) result).getNickName());
                            switch (((UserInfo) result).getSex()){
                                case 1:{sex.setText("男");}break;
                                case 2:{sex.setText("女");}break;
                            }
                            email.setText(((UserInfo) result).getEmail());
                            headPic.setImageURI(Uri.parse(((UserInfo) result).getHeadPic()));
                        }
                    }
                }break;
                case 1 : {
                    //提示
                    XToast.create(this,((DataBean) o).getMessage())
                            .setBackgroundColor(Color.parseColor("#AE15D3"))
                            .setTextSize(16)
                            .setTextColor(Color.parseColor("#FFFFFF"))
                            .setDuration(XToast.XTOAST_DURATION_LONG)
                            .setAnimation(AnimationUtils.ANIMATION_DRAWER)
                            .show();
                    //写入图片数据
                    SharedPreferences.Editor edit = MyApplication.getSharedPreferences().edit();
                    edit.putString("headPic",((DataBean) o).getHeadPath());
                    edit.commit();
                    //发送粘性事件
                    EventBus.getDefault().postSticky(((DataBean) o).getHeadPath());
                    //刷新界面
                    flag = 0;
                    mPresenter.startRequest(GET, MyUrl.GET_USER_INFO_BY_USER_ID,type,null);
                }break;
            }
        }
    }
    @Override
    public void onFail(String err) {
    }
}
