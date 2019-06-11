package com.zhongchuang.canting.presenter;


        import com.zhongchuang.canting.app.CanTingAppLication;
        import com.zhongchuang.canting.been.BaseResponse;
        import com.zhongchuang.canting.been.RedInfo;
        import com.zhongchuang.canting.been.aliLive;
        import com.zhongchuang.canting.been.videobean;
        import com.zhongchuang.canting.net.BaseCallBack;
        import com.zhongchuang.canting.net.HttpUtil;
        import com.zhongchuang.canting.net.netService;
        import com.zhongchuang.canting.utils.SpUtil;
        import com.zhongchuang.canting.utils.TextUtil;
        import com.zhongchuang.canting.utils.location.LocationUtil;

        import java.util.HashMap;
        import java.util.Map;
        import java.util.TreeMap;

        import rx.Subscription;


public class OtherPresenter implements OtherContract.Presenter {
    private Subscription subscription;

    private OtherContract.View mView;


    protected netService api;

    public OtherPresenter(OtherContract.View view) {
        mView = view;

        api = HttpUtil.getInstance().create(netService.class);
    }
    @Override
    public void alterPaymentPassword(String oldPassword, String paymentPassword,String confirmPassword) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        map.put("oldPassword", oldPassword);
        map.put("paymentPassword", paymentPassword);
        map.put("confirmPassword", confirmPassword);


        api.alterPaymentPassword(map).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 13);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    public void updateType(String id,String type) {


        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("type", type);

        api.updateType(map).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 5);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    public void setLiveNotifyUrl(int type) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        map.put("type", type+"");
        if(TextUtil.isNotEmpty(LocationUtil.city)){
            map.put("liveAddress", LocationUtil.city);
        }

        api.setLiveNotifyUrl(map).enqueue(new BaseCallBack<aliLive>() {

            @Override
            public void onSuccess(aliLive userLoginBean) {
                mView.toEntity(userLoginBean.data, 7);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void getVideoList(String id) {
        Map<String, String> params = new TreeMap<>();
        params.put("pageNum", "1");
        params.put("pageSize", "2000");

        params.put("userInfoId", id);

        api.getVideoList(params).enqueue(new BaseCallBack<videobean>() {

            @Override
            public void onSuccess(videobean userLoginBean) {
                mView.toEntity(userLoginBean.data, 111);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void setPaymentPassword(String paymentPassword, String confirmPassword) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        map.put("paymentPassword", paymentPassword);
        map.put("confirmPassword", confirmPassword);


        api.setPaymentPassword(map).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 11);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getPushUrl() {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        api.getPushUrl(map).enqueue(new BaseCallBack<aliLive>() {

            @Override
            public void onSuccess(aliLive userLoginBean) {
                mView.toEntity(userLoginBean.data, 19);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getLiveUrl(String id) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        map.put("anchorUserId", id);

        api.getLiveUrl(map).enqueue(new BaseCallBack<aliLive>() {

            @Override
            public void onSuccess(aliLive userLoginBean) {
                mView.toEntity(userLoginBean.data, 19);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void getLiveToken() {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        api.getLiveToken(map).enqueue(new BaseCallBack<aliLive>() {

            @Override
            public void onSuccess(aliLive userLoginBean) {
                mView.toEntity(userLoginBean.data, 19);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }


    @Override
    public void uploadVideo(String coverImage,String videoName,String videoUrl,int type) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        map.put("coverImage", coverImage);
        map.put("videoName", videoName);
        map.put("videoUrl", videoUrl);
        map.put("type", type+"");

        api.uploadVideo(map).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse BaseResponse) {
                mView.toEntity(BaseResponse, 12);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void addLiveRecordVod() {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        api.addLiveRecordVod(map).enqueue(new BaseCallBack<aliLive>() {

            @Override
            public void onSuccess(aliLive userLoginBean) {
                mView.toEntity(userLoginBean.data, 19);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void verifyPassword(String paymentPassword) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        if(TextUtil.isNotEmpty(paymentPassword)){
            map.put("paymentPassword", paymentPassword);
            map.put("type", "1");
        }else {
            map.put("type", "2");
        }



        api.verifyPassword(map).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 999);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }


    @Override
    public void payCheckCode(String mobileNumber,String code) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        map.put("mobileNumber", mobileNumber);
        map.put("code", code);

        api.payCheckCode(map).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 999);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }


    @Override
    public void sendRed(String integralCount,String number,String type,String groupId,String leavMessage,String sendType) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        map.put("integralCount", integralCount);
        map.put("number", number);
        map.put("type", type);
        map.put("groupId", groupId);
        map.put("leavMessage", leavMessage);
        map.put("sendType", sendType);


        api.sendRed(map).enqueue(new BaseCallBack<RedInfo>() {

            @Override
            public void onSuccess(RedInfo userLoginBean) {
                mView.toEntity(userLoginBean.data, 59);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getLuckInfo(String redEnvelopeId) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        map.put("redEnvelopeId", redEnvelopeId);


        api.getLuckInfo(map).enqueue(new BaseCallBack<RedInfo>() {

            @Override
            public void onSuccess(RedInfo userLoginBean) {
                mView.toEntity(userLoginBean.data, 59);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
}
