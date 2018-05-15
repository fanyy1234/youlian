package com.bby.youlianwallet.network;


import com.bby.youlianwallet.model.ResultDto;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiService {


    /**
     * 发送验证码
     * 验证码类型 0注册1修改密码
     */
    @FormUrlEncoded
    @POST("mobile/code/send")
    Call<ResultDto> sendCode(@Field("mobile") String mobile, @Field("type") int type);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("mobile/member/login")
    Call<ResultDto> login(@Field("mobile") String mobile, @Field("password") String password);

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("mobile/member/regist")
    Call<ResultDto> register(@Field("mobile") String mobile,
                             @Field("identifyingCode") String checkcode,
                             @Field("password") String password,
                             @Field("recCode") String recommendCode,
                             @Field("userName") String nickName);

    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("mobile/member/forgetPassword")
    Call<ResultDto> forgetPassword(@Field("mobile") String mobile,
                             @Field("msgCode") String checkcode,
                             @Field("password") String password);

    /**
     * 查询钱包地址
     */
    @POST("mobile/member/queryAddress")
    Call<ResultDto> queryAddress(@Header("Authorization") String authorization);

    /**
     * 修改钱包地址
     */
    @FormUrlEncoded
    @POST("mobile/member/purseAddress")
    Call<ResultDto> changeAddress(@Header("Authorization") String authorization,
                                   @Field("btcAddress") String bitadd,
                                   @Field("ethAddress") String ether,
                                   @Field("bitAddress") String littlebit);

    /**
     * 提币
     */
    @FormUrlEncoded
    @POST("mobile/member/order/withdrawals")
    Call<ResultDto> withdrawals(@Header("Authorization") String authorization,
                                   @Field("takeType") int takeType,
                                   @Field("takeNumber") long takeNumber,
                                   @Field("purseAddress") String purseAddress);

    /**
     * 买币
     */
    @FormUrlEncoded
    @POST("mobile/member/order/recharge")
    Call<ResultDto> recharge(@Header("Authorization") String authorization,
                                   @Field("payType") int payType,
                                   @Field("buyNumber") long buyNumber);

    /**
     * 总资产
     */
    @POST("totaAssets")
    Call<ResultDto> totalAssets(@Header("Authorization") String authorization);

    /**
     * 资产记录
     */
    @FormUrlEncoded
    @POST("capitalRecord")
    Call<ResultDto> capitalRecord(@Header("Authorization") String authorization,
                                  @Field("page") int pageNo,
                                  @Field("pageSize") int pageSize);
    /**
     * 买币记录
     */
    @FormUrlEncoded
    @POST("mobile/member/order/buyRecord")
    Call<ResultDto> buyRecord(@Header("Authorization") String authorization,
                                  @Field("page") int pageNo,
                                  @Field("pageSize") int pageSize);
    /**
     * 提币记录
     */
    @FormUrlEncoded
    @POST("mobile/member/order/takeRecord")
    Call<ResultDto> takeRecord(@Header("Authorization") String authorization,
                                  @Field("page") int pageNo,
                                  @Field("pageSize") int pageSize);

    /**
     * 充值数据
     */
    @FormUrlEncoded
    @POST("mobile/member/order/rechargeData")
    Call<ResultDto> rechargeData(@Header("Authorization") String authorization,
                                  @Field("payType") int payType,
                                  @Field("buyNumber") long buyNumber);

    /**
     * 公告
     */
    @GET("mobile/cms/notice")
    Call<ResultDto> notice(@Header("Authorization") String authorization);
    /**
     * 首页
     */
    @GET("mobile/cms/consultation")
    Call<ResultDto> consultation(@Header("Authorization") String authorization);

    /**
     * 公告详情
     */
    @GET("mobile/cms/noticeDetails")
    Call<ResultDto> noticeDetails(@Header("Authorization") String authorization,
                                  @Query("id") long id);

    /**
     * 推荐伽马
     */
    @POST("mobile/member/recommendBeiTa")
    Call<ResultDto> recommendBeiTa(@Header("Authorization") String authorization);

    /**
     * 关于我们
     */
    @POST("mobile/member/aboutUs")
    Call<ResultDto> aboutUs(@Header("Authorization") String authorization);

    /**
     * 设置支付密码
     */
    @FormUrlEncoded
    @POST("mobile/member/payPassword")
    Call<ResultDto> payPassword(@Header("Authorization") String authorization,
                                @Field("payPassword") String payPassword);

    /**
     * 转账
     */
    @FormUrlEncoded
    @POST("mobile/member/order/transferAccounts")
    Call<ResultDto> transferAccounts(@Header("Authorization") String authorization,
                                     @Field("mobile") String mobile,
                                     @Field("money") double money,
                                     @Field("password") String password);

    /**
     * 转账记录
     */
    @FormUrlEncoded
    @POST("mobile/member/order/turnRecord")
    Call<ResultDto> turnRecord(@Header("Authorization") String authorization,
                               @Field("page") int page,
                               @Field("pageSize") int pageSize);

    /**
     * 收款记录
     */
    @FormUrlEncoded
    @POST("mobile/member/order/collectRecord")
    Call<ResultDto> collectRecord(@Header("Authorization") String authorization,
                                  @Field("page") int page,
                                  @Field("pageSize") int pageSize);
    /**
     * 反馈记录
     */
    @FormUrlEncoded
    @POST("mobile/member/opinionReply")
    Call<ResultDto> opinionReply(@Header("Authorization") String authorization,
                                  @Field("page") int page,
                                  @Field("pageSize") int pageSize);
    /**
     * 反馈意见
     */
    @FormUrlEncoded
    @POST("mobile/member/opinion")
    Call<ResultDto> opinion(@Header("Authorization") String authorization,
                                  @Field("content") String content);
    /**
     * 我的业绩
     */
    @FormUrlEncoded
    @POST("mobile/member/order/myAchievement")
    Call<ResultDto> myAchievement(@Header("Authorization") String authorization,
                                  @Field("page") int page,
                                  @Field("pageSize") int pageSize);
    /**
     * usdt价格查询
     */
    @FormUrlEncoded
    @POST("mobile/member/order/buyUsdtData")
    Call<ResultDto> buyUsdtData(@Header("Authorization") String authorization,
                                  @Field("buyNumber") long buyNumber);
    /**
     * 取消订单
     */
    @FormUrlEncoded
    @POST("mobile/member/order/cancellationOrder")
    Call<ResultDto> cancellationOrder(@Header("Authorization") String authorization,
                                  @Field("id") long id);
    /**
     * 立即支付订单
     */
    @FormUrlEncoded
    @POST("mobile/member/order/immediatePay")
    Call<ResultDto> immediatePay(@Header("Authorization") String authorization,
                                  @Field("id") long id);
    /**
     * usdt买入
     */
    @FormUrlEncoded
    @POST("mobile/member/order/buyUsdt")
    Call<ResultDto> buyUsdt(@Header("Authorization") String authorization,
                                  @Field("buyNumber") long buyNumber);
    /**
     * usdt订单0未完成1已完成4已取消
     */
    @FormUrlEncoded
    @POST("mobile/member/order/orderList")
    Call<ResultDto> orderList(@Header("Authorization") String authorization,@Field("orderStatus") Integer orderStatus,@Field("page") Integer page,
                                  @Field("pageSize") Integer pageSize);







//    post传json
//    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
//    @POST("mobile/code/send")
//    Call<FlyRouteBean> postFlyRoute(@Body RequestBody route);//传入的参数为RequestBody
}
