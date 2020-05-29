package com.bw.movie.url;

/**
 * 接口地址
 * 李易泽
 * 20200523
 */
public interface MyUrl {
    //统一入口地址
    //String BASE_URL = "http://172.17.8.100/";
    String BASE_URL = "http://mobile.bwstudent.com/";
    //发送邮箱验证码
    String SEND_OUT_EMAIL_CODE = "movieApi/user/v2/sendOutEmailCode";
    //注册
    String REGISTER = "movieApi/user/v2/register";
    //登录
    String LOGIN = "movieApi/user/v2/login";
    //根据用户ID查询用户信息
    String GET_USER_INFO_BY_USER_ID = "movieApi/user/v1/verify/getUserInfoByUserId";
    //上传用户头像
    String UPLOAD_HEAD_PIC = "movieApi/user/v1/verify/uploadHeadPic";
    //修改密码
    String MODIFY_USER_PWD = "movieApi/user/v1/verify/modifyUserPwd";
    //查询用户预约电影信息
    String FIND_USER_RESERVE = "movieApi/user/v2/verify/findUserReserve";
    //购票记录
    String FIND_USER_BUY_TICKET_RECORD = "movieApi/user/v2/verify/findUserBuyTicketRecord";
    //查询看过的电影
    String FIND_SEEN_MOVIE = "movieApi/user/v2/verify/findSeenMovie";
    //我的电影票
    String FIND_MY_MOVIE_TICKET = "movieApi/user/v2/verify/findMyMovieTicket";
    //查询用户关注电影列表
    String FIND_USER_FOLLOW_MOVIE_LIST = "movieApi/user/v2/verify/findUserFollowMovieList";
    //查询用户关注影院列表
    String FIND_USER_FOLLOW_CINEMA_LIST = "movieApi/user/v2/verify/findUserFollowCinemaList";
    //修改用户手机号
    String UPDATE_USER_PHONE = "movieApi/user/v2/verify/updateUserPhone";
    //修改用户生日
    String UPDATE_USER_BIRTHDAY = "movieApi/user/v2/verify/updateUserBirthday";
    //微信登陆
    String WE_CHAT_BINDING_LOGIN = "movieApi/user/v1/weChatBindingLogin";
    //2-16~2-22
    //查询热门电影列表
    String FIND_HOT_MOVIE_LIST = "movieApi/movie/v2/findHotMovieList";
    //查询正在上映电影列表
    String FIND_RELEASE_MOVIE_LIST ="movieApi/movie/v2/findReleaseMovieList";
    //查询即将上映电影列表
    String FIND_COMING_SOON_MOVIE_LIST = "movieApi/movie/v2/findComingSoonMovieList";
    //查询电影详情
    String FIND_MOVIES_DETAIL = "movieApi/movie/v2/findMoviesDetail";
}
