package com.zhongchuang.canting.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aliyun.vod.common.utils.StorageUtils;
import com.aliyun.vod.common.utils.ToastUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.zxing.client.android.activity.CaptureActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.EMLog;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhongchuang.canting.BuildConfig;
import com.zhongchuang.canting.R;
import com.zhongchuang.canting.activity.chat.AddFriendActivity;
import com.zhongchuang.canting.activity.chat.ChatSplashActivity;
import com.zhongchuang.canting.activity.chat.FaceCreatActivity;
import com.zhongchuang.canting.activity.mall.BanDetailActivity;
import com.zhongchuang.canting.activity.mall.SearchGoodActivity;
import com.zhongchuang.canting.activity.mall.ShopCompsiteMallActivity;
import com.zhongchuang.canting.activity.mall.ShopMallActivity;
import com.zhongchuang.canting.activity.mall.ShopMallDetailActivity;
import com.zhongchuang.canting.activity.mall.ShopSpecialActivity;
import com.zhongchuang.canting.activity.mine.NewPersonDetailActivity;
import com.zhongchuang.canting.activity.mine.ProfitChargeActivity;
import com.zhongchuang.canting.activity.shop.AppStoreActivity;
import com.zhongchuang.canting.adapter.AppBasedapter;
import com.zhongchuang.canting.adapter.BannerAdapters;
import com.zhongchuang.canting.adapter.BannerMineAdapter;
import com.zhongchuang.canting.adapter.Basedapter1;
import com.zhongchuang.canting.adapter.FragmentViewPagerAdapter;
import com.zhongchuang.canting.adapter.HomeBannerAdapter;
import com.zhongchuang.canting.adapter.HomeBasedapter;
import com.zhongchuang.canting.adapter.HomeItemdapter;
import com.zhongchuang.canting.adapter.HomeProductdapter;
import com.zhongchuang.canting.adapter.VideoItemItemdapter;
import com.zhongchuang.canting.adapter.recycle.ItemRecycleAdapter;
import com.zhongchuang.canting.allive.recorder.util.Common;
import com.zhongchuang.canting.allive.vodplayerview.activity.AliyunPlayerSkinActivity;
import com.zhongchuang.canting.allive.vodplayerview.activity.AliyunPlayerSkinActivityMin;
import com.zhongchuang.canting.app.CanTingAppLication;
import com.zhongchuang.canting.base.BaseTitle_Activity;
import com.zhongchuang.canting.been.Banner;
import com.zhongchuang.canting.been.BaseResponse;
import com.zhongchuang.canting.been.FriendSearchBean;
import com.zhongchuang.canting.been.GAME;
import com.zhongchuang.canting.been.HOMES;
import com.zhongchuang.canting.been.Home;
import com.zhongchuang.canting.been.Host;
import com.zhongchuang.canting.been.Ingegebean;
import com.zhongchuang.canting.been.Product;
import com.zhongchuang.canting.been.ProvinceModel;
import com.zhongchuang.canting.been.ShareBean;
import com.zhongchuang.canting.been.SubscriptionBean;
import com.zhongchuang.canting.been.Version;
import com.zhongchuang.canting.been.VideoData;
import com.zhongchuang.canting.been.ZhiBo_GuanZhongBean;
import com.zhongchuang.canting.been.apply;
import com.zhongchuang.canting.db.Constant;
import com.zhongchuang.canting.easeui.ui.MessageActivity;
import com.zhongchuang.canting.fragment.Fragment_more_app;
import com.zhongchuang.canting.fragment.Fragment_more_app1;
import com.zhongchuang.canting.fragment.message.QFriendCircleFragment;
import com.zhongchuang.canting.hud.ToastUtils;
import com.zhongchuang.canting.net.BaseCallBack;
import com.zhongchuang.canting.net.HttpUtil;
import com.zhongchuang.canting.net.netService;
import com.zhongchuang.canting.permission.PermissionConst;
import com.zhongchuang.canting.permission.PermissionFail;
import com.zhongchuang.canting.permission.PermissionGen;
import com.zhongchuang.canting.permission.PermissionSuccess;
import com.zhongchuang.canting.presenter.BaseContract;
import com.zhongchuang.canting.presenter.BasesPresenter;
import com.zhongchuang.canting.presenter.LiveStreamPresenter;
import com.zhongchuang.canting.utils.BadgeUtil;
import com.zhongchuang.canting.utils.HxMessageUtils;
import com.zhongchuang.canting.utils.ShareUtils;
import com.zhongchuang.canting.utils.SpUtil;
import com.zhongchuang.canting.utils.StringUtil;
import com.zhongchuang.canting.utils.TextUtil;
import com.zhongchuang.canting.utils.location.LocationUtil;
import com.zhongchuang.canting.widget.BaseDailogManager;
import com.zhongchuang.canting.widget.CircleImageView;
import com.zhongchuang.canting.widget.DivItemDecoration;
import com.zhongchuang.canting.widget.MCheckBox;
import com.zhongchuang.canting.widget.MarkaBaseDialog;
import com.zhongchuang.canting.widget.NoScrollGridView;
import com.zhongchuang.canting.widget.NoScrollViewPager;
import com.zhongchuang.canting.widget.RegularListView;
import com.zhongchuang.canting.widget.RxBus;
import com.zhongchuang.canting.widget.SharePopWindow;
import com.zhongchuang.canting.widget.banner.BannerBaseAdapter;
import com.zhongchuang.canting.widget.banner.BannerView;
import com.zhongchuang.canting.widget.waitLoading.ShapeLoadingDialog;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.valuesfeng.picker.tablayout.SlidingScaleTabLayout;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;



@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class HomeActivitys extends BaseTitle_Activity implements BaseContract.View {




    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.ll_searh)
    LinearLayout llSearh;
    @BindView(R.id.iv_seach)
    ImageView ivSeach;
    @BindView(R.id.iv_change)
    ImageView iv_change;

    @BindView(R.id.ll_bg)
    LinearLayout llBg;
    @BindView(R.id.super_recycle_view)
    SuperRecyclerView mSuperRecyclerView;
    private int pos = 1;
    private Subscription mSubscription;
    //    private ProfileInfoHelper infoHelper;
    private int type = 1;

    private ItemRecycleAdapter adapter;

    private LinearLayoutManager layoutManager;
    private LiveStreamPresenter presenters;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private final int TYPE_PULL_REFRESH = 888;
    private final int TYPE_PULL_MORE = 889;
    private int currpage = 1;//第几页
    private View view;
    private BasesPresenter presenter;


    @Override
    public View addContentView() {
        return getLayoutInflater().inflate(R.layout.home_activitys, null);
    }
    private boolean isFinsh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        presenter = new BasesPresenter(this);

        PermissionGen.with(HomeActivitys.this)
                .addRequestCode(PermissionConst.REQUECT_CODE_CAMERA)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                .request();
        showPress();
        presenter.getHomeBanner("3");

        layoutManager = new LinearLayoutManager(this);
        mSuperRecyclerView.setLayoutManager(layoutManager);
        mSuperRecyclerView.addItemDecoration(new DivItemDecoration(2, true));

        mSuperRecyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

        adapter = new ItemRecycleAdapter(this);
        mSuperRecyclerView.setAdapter(adapter);
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //  mSuperRecyclerView.showMoreProgress();
                if(isLoad){
                    presenter.getHotDirect();
                    presenter.getRecomdVideoList();
                }
                presenter.getProductList(TYPE_PULL_REFRESH, 1 + "", 12 + "", "", "3", "0", "1");

                if (mSuperRecyclerView != null) {
                    mSuperRecyclerView.hideMoreProgress();
                }


            }
        };

        mSuperRecyclerView.setRefreshListener(refreshListener);

        initView();
//        presenter.getDirRoomClassify();
//        presenter.verifyPassword("");


        if(TextUtil.isNotEmpty(LocationUtil.city)){
            tvCity.setText(LocationUtil.city);
        }
        llSearh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivitys.this, SearchGoodActivity.class);
                intent.putExtra("type", 3);
                startActivity(intent);
            }
        });
        iv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindow();
            }
        });
        ivSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivitys.this,
                            Manifest.permission.CAMERA)) {

                        PermissionGen.with(HomeActivitys.this)
                                .addRequestCode(PermissionConst.REQUECT_CODE_CAMERA)
                                .permissions(Manifest.permission.CAMERA)
                                .request();
                        // 显示给用户的解释
                    } else {
                        // No explanation needed, we can request the permission.
                        Intent intent1 = new Intent(HomeActivitys.this, CaptureActivity.class);

                        intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivityForResult(intent1, 0);
                    }
                } else {
                    ToastUtil.showToast(HomeActivitys.this,"你还未登录");
                    startActivity(new Intent(HomeActivitys.this, LoginActivity.class));
                }
            }
        });
//        if (!TextUtils.isEmpty(CanTingAppLication.userId)) {
//            presenter.getChatGroupList();
//        }

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(this)
                .loadText("版本升级中...")
                .build();


//        ivLangue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopwindow();
//            }
//        });
    }
   private boolean isLoad;
  private ShapeLoadingDialog shapeLoadingDialog;
//   private HomeBasedapter adapter;
    @PermissionFail(requestCode = PermissionConst.REQUECT_CODE_CAMERA)
    public void requestSdcardFailed() {

    }

    @PermissionSuccess(requestCode = PermissionConst.REQUECT_CODE_CAMERA)
    public void requestSdcardSuccess() {
        File file = new File(StorageUtils.getCacheDirectory(CanTingAppLication.getInstance()).getAbsolutePath() + File.separator + "live.zip");
        if (file != null && file.length() < 20755920) {
            CanTingAppLication.isComplete = false;
            new Thread(new DownloadApk("https://video-zx.oss-cn-shenzhen.aliyuncs.com/app/live.zip", 2)).start();
        } else if (file == null || !file.exists()) {
            CanTingAppLication.isComplete = false;
            new Thread(new DownloadApk("https://video-zx.oss-cn-shenzhen.aliyuncs.com/app/live.zip", 2)).start();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Common.unZip();
                }
            }).start();

            CanTingAppLication.isComplete = true;
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent datas) {
        super.onActivityResult(requestCode, resultCode, datas);
        if (resultCode == RESULT_OK) {
            String result = datas.getStringExtra("result");
            if (TextUtils.isEmpty(result)) {
                return;
            }
            String[] userids = result.split(",");
            if (result.contains("@@!!##$$%%")) {
                if (userids == null || userids.length != 3) {
                    return;
                }
                showPopwindows(userids[0], userids[1]);
            } else {
                if (userids == null || userids.length != 3) {
                    return;
                }
                FriendSearchBean.DataBean dataBean = new FriendSearchBean.DataBean();
                dataBean.setNickname(userids[0]);
                dataBean.setRingLetterName(userids[1]);
                Intent intent = new Intent(HomeActivitys.this, AddFriendActivity.class);
                data.data.remove(0);
                intent.putExtra("data", data);
                intent.putExtra("datas", dataBean);
                startActivityForResult(intent, 2);
//                showPopwindow(, userids[1]);
            }


        }
    }

    private MarkaBaseDialog dialog;

    public void showPopwindows(final String name, final String id) {
        TextView sure = null;
        TextView cancel = null;
        TextView title = null;
        EditText reson = null;
        View views = View.inflate(this, R.layout.base_dailog_view, null);
        sure = views.findViewById(R.id.txt_sure);
        cancel = views.findViewById(R.id.txt_cancel);
        title = views.findViewById(R.id.txt_title);

        title.setText(getString(R.string.jrs) + name + getString(R.string.q));
        dialog = BaseDailogManager.getInstance().getBuilder(this).setMessageView(views).create();
        dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addFriendList(id, name);
                dialog.dismiss();

            }
        });
    }

    public void addFriendList(String id, final String groupsName) {

        Map<String, String> map = new HashMap<>();
//        map.put("userInfoId", CanTingAppLication.userId);
        map.put("addusers", SpUtil.getUserInfoId(HomeActivitys.this));
        map.put("groupId", id);
        map.put("groupsName", groupsName);

        netService api = HttpUtil.getInstance().create(netService.class);
        api.addFriendList(map).enqueue(new BaseCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse group) {
                RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.REFRESSH, ""));
                ToastUtils.showNormalToast(getString(R.string.nyjs) + groupsName + getString(R.string.qcy));
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                ToastUtils.showNormalToast(t);
            }
        });
    }
    private View views = null;
    private String langueType = "zh";
    private MCheckBox ivType1;
    private MCheckBox ivType2;
    private MCheckBox ivType3;
    private MCheckBox ivType4;
    private MCheckBox ivType5;
    private MCheckBox ivType6;
    private MCheckBox ivType7;
    private LinearLayout ll_langue1;
    private LinearLayout ll_langue2;
    private LinearLayout ll_langue3;
    private LinearLayout ll_langue4;
    private LinearLayout ll_langue5;
    private LinearLayout ll_langue6;
    private LinearLayout ll_langue7;

    public void showPopwindow() {

        views = View.inflate(this, R.layout.langue_item_choose, null);


        ivType1 = views.findViewById(R.id.iv_type1);
        ivType2 = views.findViewById(R.id.iv_type2);
        ivType3 = views.findViewById(R.id.iv_type3);
        ivType4 = views.findViewById(R.id.iv_type4);
        ivType5 = views.findViewById(R.id.iv_type5);
        ivType6 = views.findViewById(R.id.iv_type6);
        ivType7 = views.findViewById(R.id.iv_type7);

        ll_langue1 = views.findViewById(R.id.ll_langue1);
        ll_langue2 = views.findViewById(R.id.ll_langue2);
        ll_langue3 = views.findViewById(R.id.ll_langue3);
        ll_langue4 = views.findViewById(R.id.ll_langue4);
        ll_langue5 = views.findViewById(R.id.ll_langue5);
        ll_langue6 = views.findViewById(R.id.ll_langue6);
        ll_langue7 = views.findViewById(R.id.ll_langue7);


        dialogs = BaseDailogManager.getInstance().getBuilder(this).setMessageView(views).create();
        selectType(SpUtil.getLangueType(this));
        dialogs.show();

        ll_langue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType("zh-rCN");
                updateActivity(langueType);
            }
        });
        ll_langue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType("en");
                updateActivity(langueType);
            }
        });
        ll_langue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType("fan");
                updateActivity(langueType);
            }
        });
        ll_langue4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType("ja");
                updateActivity(langueType);
            }
        });
        ll_langue5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType("ko");
                updateActivity(langueType);
            }
        });
        ll_langue6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType("ms");
                updateActivity(langueType);
            }
        });
        ll_langue7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType("ru");
                updateActivity(langueType);
            }
        });


    }

    /**
     * 刷新语言
     */
    public void updateActivity(String sta) {
        SpUtil.putString(this, "LangueType", sta);
        if (isLogin) {
            presenter.setLanguge(getLangue(sta));
        }
        // 本地语言设置
        Locale myLocale = new Locale(sta);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        startActivity((new Intent(this, HomeActivitys.class)));
        cots = 0;
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        for (EMConversation conversation : conversations.values()) {
            if (conversation.getAllMessages().size() != 0) {
                EMConversation emConversation = EMClient.getInstance().chatManager().getConversation(conversation.conversationId());
                if (emConversation != null) {
                    int unreadMsgCount = emConversation.getUnreadMsgCount();
                    cots = cots + unreadMsgCount;
                }

            }
        }
       setData(cots);
    }

    public String getLangue(String lan) {
        String langue = "";
        if (lan.equals("zh-rCN")) {
            langue = "zh";
        } else if (lan.equals("fan")) {
            langue = "tw";
        } else {
            langue = lan;
        }
        return langue;
    }

    private MarkaBaseDialog dialogs;

    public void selectType(String type) {

        if (type.equals("zh-rCN")) {
            langueType = "zh-rCN";
            ivType1.setChecked(true);
            ivType2.setChecked(false);
            ivType3.setChecked(false);
            ivType4.setChecked(false);
            ivType5.setChecked(false);
            ivType6.setChecked(false);
            ivType7.setChecked(false);
        } else if (type.equals("en")) {
            langueType = "en";
            ivType1.setChecked(false);
            ivType2.setChecked(true);
            ivType3.setChecked(false);
            ivType4.setChecked(false);
            ivType5.setChecked(false);
            ivType6.setChecked(false);
            ivType7.setChecked(false);
        } else if (type.equals("fan")) {
            langueType = "fan";
            ivType1.setChecked(false);
            ivType2.setChecked(false);
            ivType3.setChecked(true);
            ivType4.setChecked(false);
            ivType5.setChecked(false);
            ivType6.setChecked(false);
            ivType7.setChecked(false);
        } else if (type.equals("ja")) {
            langueType = "ja";
            ivType1.setChecked(false);
            ivType2.setChecked(false);
            ivType3.setChecked(false);
            ivType4.setChecked(true);
            ivType5.setChecked(false);
            ivType6.setChecked(false);
            ivType7.setChecked(false);
        } else if (type.equals("ko")) {
            langueType = "ko";
            ivType1.setChecked(false);
            ivType2.setChecked(false);
            ivType3.setChecked(false);
            ivType4.setChecked(false);
            ivType5.setChecked(true);
            ivType6.setChecked(false);
            ivType7.setChecked(false);
        } else if (type.equals("ms")) {
            langueType = "ms";
            ivType1.setChecked(false);
            ivType2.setChecked(false);
            ivType3.setChecked(false);
            ivType4.setChecked(false);
            ivType5.setChecked(false);
            ivType6.setChecked(true);
            ivType7.setChecked(false);
        } else if (type.equals("ru")) {
            langueType = "ru";
            ivType1.setChecked(false);
            ivType2.setChecked(false);
            ivType3.setChecked(false);
            ivType4.setChecked(false);
            ivType5.setChecked(false);
            ivType6.setChecked(false);
            ivType7.setChecked(true);
        }
        dialogs.dismiss();
        CanTingAppLication.LangueType = langueType;

    }


    public static boolean isLogin;


   public Handler handler=new Handler(new Handler.Callback() {
       @Override
       public boolean handleMessage(Message msg) {
           setData(cots);
           if (presenter != null) {

               presenter.getProductList(TYPE_PULL_REFRESH, 1 + "", 12 + "", "", "3", "0", "1");
               if (!TextUtils.isEmpty(CanTingAppLication.userId)) {
                   presenter.getChatGroupList();
                   presenter.hostInfo();
                   presenter.getUserIntegral();
                   presenter.verifyPassword("");
               }

           }
           return false;
       }
   });
    private int cont;
    public interface  MessageNotifyListener{
        void messageNotify(int cout);
    }
    public MessageNotifyListener listener;

    public void setListener(MessageNotifyListener listener){
        this.listener=listener;
    }
    public void setData(int cout) {
           listener.messageNotify(cout);




    }

    private List<HOMES> datas = new ArrayList<>();

    private void setLoginMessage() {
        String phone = SpUtil.getString(this, "mobileNumber", "");
        String token = SpUtil.getString(this, "token", "");
        String avar = SpUtil.getString(this, "avar", "");
        isLogin = !TextUtils.isEmpty(token) && !token.equals("") && !TextUtils.isEmpty(token) && !token.equals("");


    }



    private CountDownTimer countDownTimer1;
    private int states;

    private List<Fragment> mFragments;
    private com.youth.banner.Banner banner;
    private NoScrollGridView gridContent;
    private RegularListView gridContentLb;
    private RegularListView gridContentLbs;
    private RegularListView gridContentLive;
    private CardView card_lb;
    private CardView card_lbs;
    private CardView card_live;

    private LinearLayout ll_special;
    private HomeItemdapter homedapter;
    private HomeProductdapter homeProductdapter;
    private VideoItemItemdapter lbapter;
    private VideoItemItemdapter lbapters;
    private VideoItemItemdapter liveapter;
    private NoScrollViewPager viewpagerMain;
    private FragmentViewPagerAdapter mainViewPagerAdapter;

    private void addFragment() {
        mFragments = new ArrayList<>();

        fragment_more_app = new Fragment_more_app();
        fragment_more_app1 = new Fragment_more_app1();

        mFragments.add(fragment_more_app);
        mFragments.add(fragment_more_app1);

    }
    public void afterLoad(){
        presenter.getHotDirect();
        presenter.getRecomdVideoList();
        presenter.getActivityProductList(222, 1 + "", 12 + "", "", "" + "1", "" + "2");

    }

    public void choosePoition(int position){
        if(position==0){
            line1.setImageDrawable(getResources().getDrawable(R.drawable.blue_lines_choose));
            line1.setVisibility(View.VISIBLE);
            line2.setVisibility(View.INVISIBLE);
        }else {
            line2.setImageDrawable(getResources().getDrawable(R.drawable.blue_lines_right));
            line2.setVisibility(View.VISIBLE);
            line1.setVisibility(View.INVISIBLE);
        }
    }
    private ImageView line1;
    private ImageView line2;
    private void initView() {

        View view = View.inflate(this, R.layout.home_head_view, null);
        banner = view.findViewById(R.id.banner);

        gridContent = view.findViewById(R.id.grid_content);
        gridContentLb = view.findViewById(R.id.grid_content_lb);
        gridContentLbs = view.findViewById(R.id.grid_content_lbs);
        gridContentLive = view.findViewById(R.id.grid_content_live);
        card_lb = view.findViewById(R.id.card_lb);
        card_lbs = view.findViewById(R.id.card_lbs);
        card_live = view.findViewById(R.id.cards_live);
        line1 = view.findViewById(R.id.iv_line1);
        line2 = view.findViewById(R.id.iv_line2);

        viewpagerMain = view.findViewById(R.id.viewpager_main);

        ll_special = view.findViewById(R.id.ll_special);

        homeProductdapter = new HomeProductdapter(this);
        lbapter = new VideoItemItemdapter(this);
        lbapters = new VideoItemItemdapter(this);
        liveapter = new VideoItemItemdapter(this);
        lbapter.setOnItemClickListener(new VideoItemItemdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, VideoData dataBean) {
                String token = SpUtil.getString(HomeActivitys.this, "token", "");
                if (token == null || token.equals("")) {
                    ToastUtils.showNormalToast("你还没有登录，快去登录吧!");
                    Intent gotoLogin = new Intent(HomeActivitys.this, LoginActivity.class);
                    startActivity(gotoLogin);
                    return;
                }
                if (!dataBean.new_type.equals("0")) {
                    CanTingAppLication.landType = 6;
                    Intent intent = new Intent(HomeActivitys.this, AliyunPlayerSkinActivity.class);
                    intent.putExtra("type", 3);
                    intent.putExtra("url", dataBean.video_url);
                    intent.putExtra("name", dataBean.video_name);
                    intent.putExtra("room_info_id", dataBean.room_info_id);
                    intent.putExtra("id", dataBean.user_info_id);
                    startActivity(intent);
                } else {
                    if (dataBean.video_type.equals("2")) {
                        CanTingAppLication.landType = 6;
                        Intent intent = new Intent(HomeActivitys.this, AliyunPlayerSkinActivity.class);
                        intent.putExtra("url", dataBean.video_url);
                        intent.putExtra("name", dataBean.video_name);
                        intent.putExtra("room_info_id", dataBean.room_info_id);
                        intent.putExtra("id", dataBean.user_info_id);
                        startActivity(intent);
                    } else if (dataBean.video_type.equals("3")) {
                        CanTingAppLication.landType = 8;
                        Intent intent = new Intent(HomeActivitys.this, AliyunPlayerSkinActivity.class);
                        intent.putExtra("url", dataBean.video_url);
                        intent.putExtra("type", 3);
                        intent.putExtra("name", dataBean.video_name);
                        intent.putExtra("room_info_id", dataBean.room_info_id);
                        intent.putExtra("id", dataBean.user_info_id);
                        startActivity(intent);

                    } else {
                        CanTingAppLication.landType = 8;
                        Intent intent = new Intent(HomeActivitys.this, AliyunPlayerSkinActivityMin.class);
                        intent.putExtra("url", dataBean.video_url);
                        intent.putExtra("name", dataBean.video_name);
                        intent.putExtra("room_info_id", dataBean.room_info_id);
                        intent.putExtra("id", dataBean.user_info_id);
                        startActivity(intent);
                    }
                }
            }
        });
        lbapters.setOnItemClickListener(new VideoItemItemdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, VideoData dataBean) {
                String token = SpUtil.getString(HomeActivitys.this, "token", "");
                if (token == null || token.equals("")) {
                    ToastUtils.showNormalToast("你还没有登录，快去登录吧!");
                    Intent gotoLogin = new Intent(HomeActivitys.this, LoginActivity.class);
                    startActivity(gotoLogin);
                    return;
                }
                if (!dataBean.new_type.equals("0")) {
                    CanTingAppLication.landType = 6;
                    Intent intent = new Intent(HomeActivitys.this, AliyunPlayerSkinActivity.class);
                    intent.putExtra("type", 3);
                    intent.putExtra("url", dataBean.video_url);
                    intent.putExtra("name", dataBean.video_name);
                    intent.putExtra("room_info_id", dataBean.room_info_id);
                    intent.putExtra("id", dataBean.user_info_id);
                    startActivity(intent);
                } else {
                    if (dataBean.video_type.equals("2")) {
                        CanTingAppLication.landType = 6;
                        Intent intent = new Intent(HomeActivitys.this, AliyunPlayerSkinActivity.class);
                        intent.putExtra("url", dataBean.video_url);
                        intent.putExtra("name", dataBean.video_name);
                        intent.putExtra("room_info_id", dataBean.room_info_id);
                        intent.putExtra("id", dataBean.user_info_id);
                        startActivity(intent);
                    } else if (dataBean.video_type.equals("3")) {
                        CanTingAppLication.landType = 8;
                        Intent intent = new Intent(HomeActivitys.this, AliyunPlayerSkinActivity.class);
                        intent.putExtra("url", dataBean.video_url);
                        intent.putExtra("type", 3);
                        intent.putExtra("name", dataBean.video_name);
                        intent.putExtra("room_info_id", dataBean.room_info_id);
                        intent.putExtra("id", dataBean.user_info_id);
                        startActivity(intent);

                    } else {
                        CanTingAppLication.landType = 8;
                        Intent intent = new Intent(HomeActivitys.this, AliyunPlayerSkinActivityMin.class);
                        intent.putExtra("url", dataBean.video_url);
                        intent.putExtra("name", dataBean.video_name);
                        intent.putExtra("room_info_id", dataBean.room_info_id);
                        intent.putExtra("id", dataBean.user_info_id);
                        startActivity(intent);
                    }
                }
            }
        });
        liveapter.setOnItemClickListener(new VideoItemItemdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, VideoData dataBean) {
                if(dataBean.is_live){
                    ToastUtils.showNormalToast("主播还未上线，请稍后观看直播!");
                    return;
                }
                String token = SpUtil.getString(HomeActivitys.this, "token", "");
                if (token == null || token.equals("")) {
                    ToastUtils.showNormalToast("你还没有登录，快去登录吧!");
                    Intent gotoLogin = new Intent(HomeActivitys.this, LoginActivity.class);
                    startActivity(gotoLogin);
                    return;
                }
                if (dataBean.type.equals("2")) {
                    CanTingAppLication.landType = 0;
                    Intent intent = new Intent(HomeActivitys.this, AliyunPlayerSkinActivity.class);
                    intent.putExtra("id", dataBean.user_info_id);
                    intent.putExtra("room_info_id", dataBean.room_info_id);
                    startActivity(intent);
                } else {
                    CanTingAppLication.landType = 1;
                    Intent intent = new Intent(HomeActivitys.this, AliyunPlayerSkinActivityMin.class);
                    intent.putExtra("id", dataBean.user_info_id);
                    intent.putExtra("room_info_id", dataBean.room_info_id);
                    startActivity(intent);
                }
            }
        });
        addFragment();
        mainViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), mFragments);
        viewpagerMain.setAdapter(mainViewPagerAdapter);
        viewpagerMain.setOffscreenPageLimit(1);//设置缓存view 的个数
        viewpagerMain.setCurrentItem(0);
        choosePoition(0);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if(banners!=null&&TextUtil.isNotEmpty( banners.get(position).linkName)&&(banners.get(position).linkName.contains("156")||banners.get(position).linkName.contains("jpg")||banners.get(position).linkName.contains("png")) ){
                    Intent intent = new Intent(HomeActivitys.this, BanDetailActivity.class);
                    intent.putExtra("url", banners.get(position).linkName);
                    startActivity(intent);
                }

            }
        });
        viewpagerMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                choosePoition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ll_special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivitys.this, ShopSpecialActivity.class);
                intent.putExtra("state", 1);
                startActivity(intent);

            }
        });


        gridContent.setAdapter(homeProductdapter);
        gridContentLive.setAdapter(liveapter);
        gridContentLb.setAdapter(lbapter);
        gridContentLbs.setAdapter(lbapters);



        homeProductdapter.setOnItemClickListener(new HomeProductdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product data) {


                    Intent intent = new Intent(HomeActivitys.this, ShopMallDetailActivity.class);
                    intent.putExtra("id", data.product_sku_id);
                    intent.putExtra("type", 1);
                    startActivity(intent);



            }
        });


//        showPress();
        adapter.setHeaderView(view);

        adapter.setItemCikcListener(new ItemRecycleAdapter.ItemClikcListener() {
            @Override
            public void itemClick(String data, int poition) {
                Intent intent = new Intent(HomeActivitys.this, ShopMallDetailActivity.class);
                intent.putExtra("id", data);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });


        if (CanTingAppLication.data == null) {

            Observable.create(new Observable.OnSubscribe<JSONObject>() {

                @Override
                public void call(Subscriber<? super JSONObject> subscriber) {
                    JSONObject json = TextUtil.getJson("phone.json", HomeActivitys.this);
                    subscriber.onNext(json);
                    subscriber.onCompleted();//结束异步任务

                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<JSONObject>() {
                        @Override
                        public void call(JSONObject json) {
                            JSONObject dataJson = json.optJSONObject("data");
//                            PREFIX  data = new Gson().fromJson(dataJson.toString(), PREFIX.class);

                        }
                    });
        }
        if (CanTingAppLication.province == null) {
            Observable.create(new Observable.OnSubscribe<JSONObject>() {

                @Override
                public void call(Subscriber<? super JSONObject> subscriber) {
                    JSONObject json = TextUtil.getJson("city.json", HomeActivitys.this);
                    subscriber.onNext(json);
                    subscriber.onCompleted();//结束异步任务

                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<JSONObject>() {
                        @Override
                        public void call(JSONObject json) {
                            JSONObject dataJson = json.optJSONObject("data");
                            CanTingAppLication.province = new Gson().fromJson(dataJson.toString(), ProvinceModel.class);

                        }
                    });

        }
        mSubscription = RxBus.getInstance().toObserverable(SubscriptionBean.RxBusSendBean.class).subscribe(new Action1<SubscriptionBean.RxBusSendBean>() {
            @Override
            public void call(SubscriptionBean.RxBusSendBean bean) {
                if (bean == null) return;
                if (bean.type == SubscriptionBean.SIGN) {
                    if (states == 0) {

                    }
                    if (countDownTimer1 == null) {
                        states = 1;
                        countDownTimer1 = new CountDownTimer(5000, 5000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                states = 0;
                                countDownTimer1.cancel();
                            }
                        }.start();
                    }

                } else if (bean.type == SubscriptionBean.OUTLOGIN) {
                    exitApp();

                } else if (bean.type == SubscriptionBean.LOGIN_FINISH) {
                    exitApp();

                }else if(bean.type == SubscriptionBean.MESSAGENOTIFI){
                    List<EMMessage> messages = (List<EMMessage>) bean.content;
                    notifyMessage(messages);
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        RxBus.getInstance().addSubscription(mSubscription);

        setLoginMessage();

        presenter.getVersionAndUrl();


           shareBean=new ShareBean();
            shareBean.title_ = SpUtil.getName(this) + "邀请你下载数字时代APP";
            shareBean.content_ = "让你有不一样的购物体验不一样的直播平台不一样的社交！";
            shareBean.url_ = Constant.APP_SHARE;
            shareBean.img_ = "img";

        CanTingAppLication.appbean = shareBean;


//
//        tvLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isLogin) {
//                    Intent intent3 = new Intent(HomeActivitys.this, MainActivity.class);
//                    intent3.putExtra("type", 3);
//                    startActivity(intent3);
//                } else {
//                    startActivity(new Intent(HomeActivitys.this, LoginActivity.class));
//                }
//
//            }
//        });
    }
   private  ShareBean shareBean;
    public void refresh() {
        if (mSuperRecyclerView != null) {
            //实现自动下拉刷新功能
            mSuperRecyclerView.getSwipeToRefresh().post(new Runnable() {
                @Override
                public void run() {
                    mSuperRecyclerView.setRefreshing(true);//执行下拉刷新的动画
                    refreshListener.onRefresh();//执行数据加载操作
                }
            });
        }
    }
    private List<Product> dats = new ArrayList<>();
    private int cout = 0;
    public void onDataLoaded(int loadType, final boolean haveNext, List<Product> list) {

        if (loadType == TYPE_PULL_REFRESH) {
            currpage = 1;
            dats.clear();
            for (Product info : list) {
                dats.add(info);
            }
        } else {
            for (Product info : list) {
                dats.add(info);
            }
        }

        adapter.setDatas(dats);

        adapter.notifyDataSetChanged();
        if (mSuperRecyclerView != null) {
            mSuperRecyclerView.hideMoreProgress();
        }


        if (haveNext) {
            mSuperRecyclerView.setupMoreListener(new OnMoreListener() {
                @Override
                public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                    currpage++;
                    mSuperRecyclerView.showMoreProgress();

                    if (haveNext)
                        presenter.getProductList(TYPE_PULL_MORE, currpage + "", 12 + "", "", "3", "0", "1");
                    mSuperRecyclerView.hideMoreProgress();

                }
            }, 1);
        } else {
            if (mSuperRecyclerView != null) {
                mSuperRecyclerView.removeMoreListener();
                mSuperRecyclerView.hideMoreProgress();
            }


        }


    }
    private SharePopWindow shopBuyWindow;
    private int state = 1;
     private Fragment_more_app fragment_more_app;
     private Fragment_more_app1 fragment_more_app1;
    private MarkaBaseDialog dialg;

    public void showPopwindow(final String url) {
        TextView sure = null;
        TextView cancel = null;
        TextView title = null;
        EditText reson = null;
        View views = View.inflate(this, R.layout.down_dialog, null);
        sure = views.findViewById(R.id.txt_sure);
        cancel = views.findViewById(R.id.txt_cancel);
        title = views.findViewById(R.id.txt_title);
        if (TextUtil.isNotEmpty(description)) {
            title.setText(description);

        } else {
            title.setText(R.string.yxdbbo);

        }
        dialg = BaseDailogManager.getInstance().getBuilder(this).setMessageView(views).create();
        dialg.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialg.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                initNotification();
//                llBgs.setVisibility(View.VISIBLE);
//                if (!isLogin) {
//                    llBg.setVisibility(View.GONE);
//                }

                if(!isInstall){
                    isInstall=true;
                    shapeLoadingDialog.show();
                    shapeLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if (!isFinsh) {
                                shapeLoadingDialog.show();
                            }
                        }
                    });
                    isStar = true;
                    new Thread(new DownloadApk(url, 1)).start();
                    dialg.dismiss();

                }


            }
        });
    }
   private boolean isInstall;

    private String userInfoId;

    private void exitApp() {
        String olderToken = SpUtil.getString(this, "token", "");//token值
        String code = SpUtil.getString(this, "code", "");
        if (TextUtils.isEmpty(olderToken)) {
            return;
        } else {
            userInfoId = SpUtil.getString(HomeActivitys.this, "userInfoId", "");
            if (userInfoId != null) {
                SpUtil.remove(HomeActivitys.this, "userInfoId");//token值
            }
            //误操作  清理   不用处理
            String userId = SpUtil.getString(this, "userId", "");
            String userloid = SpUtil.getString(this, "userloid", "");

            if (olderToken != null) {
                SpUtil.remove(this, "token");//token值
            }

            if (code != null) {
                SpUtil.remove(this, "code");//userId值
            }

            //误操作  清理   不用处理
            if (userId != null) {
                SpUtil.remove(this, "userId");//userId值
            }

            if (userloid != null) {
                SpUtil.remove(this, "userloid");//userId值
            }
            state = 0;
            startActivity(new Intent(HomeActivitys.this, LoginActivity.class));
            logOut();

        }

    }

    private void logOut() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().logout(true, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
//                        tvLogin.setText("登录");

                        Log.d(TAG, "main+12: " + "登出成功");
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onError(int code, String message) {
                        // TODO Auto-generated method stub

                    }
                });
            }
        }).start();

    }

    private int cots;
    public boolean isShow = true;
    public boolean isStar = false;

    @Override
    public void onResume() {
        super.onResume();
        isShow = true;
        cots = 0;
       CanTingAppLication.CompanyType=Constant.CompanyType;
//        Constant.APP_LIVE_DOWN = Constant.URL_TYPE1[Integer.valueOf(Constant.CompanyType)];
//        Constant.APP_PRODUCT = Constant.URL_TYPE2[Integer.valueOf(Constant.CompanyType)];
//        Constant.APP_FILE_NAME = Constant.URL_TYPE3[Integer.valueOf(Constant.CompanyType)];
//        Constant.FILE_NAME = Constant.URL_TYPE4[Integer.valueOf(Constant.CompanyType)];
//        Constant.APP_SHARE = Constant.URL_TYPE5[Integer.valueOf(Constant.CompanyType)];
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        for (EMConversation conversation : conversations.values()) {
            if (conversation.getAllMessages().size() != 0) {
                EMConversation emConversation = EMClient.getInstance().chatManager().getConversation(conversation.conversationId());
                if (emConversation != null) {
                    int unreadMsgCount = emConversation.getUnreadMsgCount();
                    cots = cots + unreadMsgCount;
                }

            }
        }

        handler.sendEmptyMessageDelayed(1,350);




        if (state == 0) {

        }
        setLoginMessage();
        if (!TextUtils.isEmpty(CanTingAppLication.userId)) {
            state = 1;
        }
    }

    public void showCount(int allCount) {
//        madMode(99);
//        BadgeUtil.setBadgeCount( this,allCount, R.drawable.red_point);


    }

    /**
     * 获取所有App的包名和启动类名
     *
     * @param count count
     */
    private void madMode(int count) {

        BadgeUtil.setBadgeOfMadMode(getApplicationContext(), count, "com.zhongchuang.canting", "com.zhongchuang.canting.activity.AliveSplashActivity");

    }

    @Override
    public void onStop() {
        super.onStop();
        hidePress();
        isShow = false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isShow = false;

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public boolean isTitleShow() {
        return false;
    }


    private List<ZhiBo_GuanZhongBean.DataBean> cooks = new ArrayList<>();


    public void onResultSuccess(ZhiBo_GuanZhongBean zhiBo_guanZhongBean) {

    }
    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Banner url= (Banner) path;
            if(HomeActivitys.this!=null){
                Glide.with(context).load(StringUtil.changeUrl(url.image_url)).thumbnail(0.1f).placeholder(R.drawable.moren).into(imageView);
            }
            //Glide 加载图片简单用法



        }
    }
    private void startBanner() {
        //设置banner样式(显示圆形指示器)
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置（指示器居右）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(banners);
        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
//        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }
    private GAME data;
    public static GAME messageGroup;
    private List<GAME> dat = new ArrayList<>();
    List<Banner> category;
    List<Banner> banners;
    private List<Product> productData;
    @Override
    public <T> void toEntity(T entity, int type) {
        hidePress();
        if (type == 12) {
            data = new GAME();
            GAME gas = new GAME();
            gas.directTypeName = "录播视频";
            gas.id = "-1";
            dat.clear();
            dat.add(gas);
            GAME ga = new GAME();
            ga.directTypeName = "热门";
            ga.id = "0";
            dat.add(ga);
            List<GAME> games = (List<GAME>) entity;
            for (GAME game : games) {
                dat.add(game);
            }
            data.data = dat;
        } else if (type == 14) {
            List<Version> datas = (  List<Version>) entity;

            for(Version version:datas){
                if(version.url.contains("szds.apk")){
                    String oldVersion = StringUtil.getVersion(CanTingAppLication.getInstance());//"0.17"
                    description = version.description;
                    if (TextUtil.isNotEmpty(version.name)) {
                        CanTingAppLication.url = version.name;
                    } else {
                        CanTingAppLication.url = "http://vip1.runchunqiu.com/";
                    }
                    if (version.version.compareTo(oldVersion) > 0) {
                        showPopwindow(version.url);

                    }
                }
            }


           if(datas==null){
               CanTingAppLication.url = "http://vip1.runchunqiu.com/";
               return;
           }

        } else if (type == 22) {

            messageGroup = (GAME) entity;
        }else if (type == 444) {
            isLoad=true;
            List<VideoData>  lists= (List<VideoData>) entity;
            if(lists==null||lists.size()==0){
                List<VideoData> datas=new ArrayList<>();
                VideoData videoData = new VideoData();
                videoData.cover_image="http://xjxlsy.cn/1576585590225chatbg";
                videoData.user_info_nickname="直播间1";
                videoData.third_category_name="广东赛区";
                videoData.direct_overview="数字时代直播专区";
                videoData.is_live=true;
                VideoData videoData1 = new VideoData();
                videoData1.cover_image="http://xjxlsy.cn/1576586712269chatbg";
                videoData1.user_info_nickname="直播间2";
                videoData1.third_category_name="禅舞";
                videoData1.direct_overview="大道至简!返璞归真!";
                videoData1.is_live=true;
                VideoData videoData2 = new VideoData();
                videoData2.cover_image="http://xjxlsy.cn/1576587045886chatbg";
                videoData2.user_info_nickname="直播间3";
                videoData2.third_category_name="车载冰箱";
                videoData2.direct_overview="颤覆传统销售   让消费回归理性";
                videoData2.is_live=true;
                VideoData videoData3 = new VideoData();
                videoData3.cover_image="http://xjxlsy.cn/1576588233735chatbg";
                videoData3.user_info_nickname="直播间4";
                videoData3.third_category_name="政府公共管理服务";
                videoData3.direct_overview="益业联盟，现场展示盟友实力!";
                videoData3.is_live=true;
                datas.add(videoData);
                datas.add(videoData1);
                datas.add(videoData2);
                datas.add(videoData3);
                liveapter.setData(datas);
//                card_live.setVisibility(View.GONE);
            }else {
                liveapter.setData(lists);
            }


        }else if (type == 443) {
            isLoad=true;
            List<VideoData>  lists= (List<VideoData>) entity;
            if(lists==null||lists.size()==0){
                lbapter.setData(lists);
                lbapters.setData(lists);
            }else {
                lbapter.setData(lists);
//                intent.putExtra("type", 3);
//                intent.putExtra("url", dataBean.video_url);
//                intent.putExtra("name", dataBean.video_name);
//                intent.putExtra("room_info_id", dataBean.room_info_id);
//                intent.putExtra("id", dataBean.user_info_id);
//                List<VideoData> datas=new ArrayList<>();
//                VideoData videoData = new VideoData();
//                videoData.video_url="";
//                videoData.video_name="";
//                videoData.room_info_id="";
//                videoData.user_info_id="";
//                videoData.cover_image="";
//                videoData.user_info_nickname="直播间1";
//                videoData.third_category_name="广东赛区";
//                videoData.direct_overview="数字时代直播专区";
//                videoData.is_live=true;
//                VideoData videoData1 = new VideoData();
//                videoData1.cover_image="http://xjxlsy.cn/1576586712269chatbg";
//                videoData1.user_info_nickname="直播间2";
//                videoData1.third_category_name="禅舞";
//                videoData1.direct_overview="大道至简!返璞归真!";
//                videoData1.is_live=true;
//                VideoData videoData2 = new VideoData();
//                videoData2.cover_image="http://xjxlsy.cn/1576587045886chatbg";
//                videoData2.user_info_nickname="直播间3";
//                videoData2.third_category_name="车载冰箱";
//                videoData2.direct_overview="颤覆传统销售   让消费回归理性";
//                videoData2.is_live=true;
//                VideoData videoData3 = new VideoData();
//                videoData3.cover_image="http://xjxlsy.cn/1576588233735chatbg";
//                videoData3.user_info_nickname="直播间4";
//                videoData3.third_category_name="政府公共管理服务";
//                videoData3.direct_overview="益业联盟，现场展示盟友实力!";
//                videoData3.is_live=true;
//                datas.add(videoData);
//                datas.add(videoData1);
//                datas.add(videoData2);
//                datas.add(videoData3);
                lbapters.setData(lists);
            }


        }else if (type == 888|| type==889) {
            productData = (List<Product>) entity;
            if(productData!=null){
                onDataLoaded(type, 12 == productData.size(), productData);
            }else {
                adapter.notifyDataSetChanged();
            }


        }
//        else if (type == 6) {
//            Home home = (Home) entity;
//            banners = home.banner;
//            bannerAdapter.setData(banners);
//            bannerAdapter.notifyDataSetChanged();
//
//        }
        else if (type == 6) {
            afterLoad();
            Home home = (Home) entity;
            category = home.category;
            banners = home.banner;
            startBanner();

        } else if (type == 989) {

        } else if(type==222){
            List<Product> data = (List<Product>) entity;

            if(data!=null&&data.size()>0){
                homeProductdapter.setData(data);
                homeProductdapter.notifyDataSetChanged();

            }else {
                if(productData!=null){
                    homeProductdapter.setData(productData);
                    homeProductdapter.notifyDataSetChanged();
                }

            }

        }else if (type == 111) {

        } else    if (type == 19) {
            Ingegebean    bean = (Ingegebean) entity;
            if(bean==null){
                return;
            }

            if(bean.pay_type==1){
                CanTingAppLication.isPay=true;
            }else {
                CanTingAppLication.isPay=false;
            }
            if (TextUtil.isNotEmpty(bean.money_buy_integral)) {
                CanTingAppLication.totalintegral =  CanTingAppLication.totalintegral +Double.valueOf(bean.money_buy_integral);
                CanTingAppLication.Chargeintegral =  Double.valueOf(bean.money_buy_integral);
            }
            if (TextUtil.isNotEmpty(bean.chat_integral)) {
                CanTingAppLication.totalintegral =  CanTingAppLication.totalintegral +Double.valueOf(bean.chat_integral);
            }
            if (TextUtil.isNotEmpty(bean.jewel_integral)) {
                CanTingAppLication.totalintegral =  CanTingAppLication.totalintegral +Double.valueOf(bean.jewel_integral);
            }
            if (TextUtil.isNotEmpty(bean.direct_integral)) {
                CanTingAppLication.totalintegral =  CanTingAppLication.totalintegral +Double.valueOf(bean.direct_integral);
            }
            if (TextUtil.isNotEmpty(bean.invitation_code)) {
                CanTingAppLication.invitation_code=bean.invitation_code;
                shareBean.title_ = SpUtil.getName(this) + "邀请你下载数字时代APP";
                shareBean.content_ = "让你有不一样的购物体验不一样的直播平台不一样的社交！";
                shareBean.url_ = Constant.APP_SHARE + SpUtil.getName(this)+","+bean.invitation_code;
                shareBean.img_ = "img";
                CanTingAppLication.appbean = shareBean;
            }

        }else {
            Host data = (Host) entity;
            if (data != null && TextUtil.isNotEmpty(data.is_direct)) {


                if (data.is_direct.equals("1")) {
//                    if (TextUtil.isNotEmpty(data.user_integral)) {
//                        CanTingAppLication.totalintegral = Double.valueOf(data.user_integral);
//                    } else {
//                        if (TextUtil.isNotEmpty(data.userIntegral)) {
//                            CanTingAppLication.totalintegral = Double.valueOf(data.userIntegral);
//                        }
//                    }
                    SpUtil.putString(HomeActivitys.this, "isAnchor", 1 + "");
                } else {
//                    if (TextUtil.isNotEmpty(data.userIntegral)) {
//                        CanTingAppLication.totalintegral = Double.valueOf(data.userIntegral);
//                    } else {
//                        if (TextUtil.isNotEmpty(data.user_integral)) {
//                            CanTingAppLication.totalintegral = Double.valueOf(data.user_integral);
//                        }
//                    }
                    SpUtil.putString(HomeActivitys.this, "isAnchor", 0 + "");
                }
            }
        }

    }

    private String description;


    @Override
    public void toNextStep(int type) {

    }
   public void notifyMessage(List<EMMessage> messages){

       for (EMMessage message : messages) {
           EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
           // in background, do not refresh UI, notify it in notification bar
           String name = HxMessageUtils.getFName(message);
           if (name.contains("!@#$$#@!")) {
               return;
           }
       }
       cots = 0;
       Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
       for (EMConversation conversation : conversations.values()) {
           if (conversation.getAllMessages().size() != 0) {
               EMConversation emConversation = EMClient.getInstance().chatManager().getConversation(conversation.conversationId());
               if (emConversation != null) {
                   int unreadMsgCount = emConversation.getUnreadMsgCount();
                   cots = cots + unreadMsgCount;
               }

           }
       }
       RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.MESSAGENOTIFIS, cots));
       setData(cots);
   }
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {

//            showCount(cout);
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageRecalled(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };

    @Override
    public void showTomast(String msg) {
        hidePress();
    }

    private long max;
    private long current;

    //循环模拟下载过程
    public void start(final long current) {
        if (isShow) {
            if (current <= max) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(shapeLoadingDialog!=null){
                            shapeLoadingDialog.setText("已下载  "+(current*100)/max+"%");
                        }


//                        mProgress.setCurrentValue(current);
                    }
                });

            }
        }


    }

    /**
     * Created by mykar on 17/10/25.
     */
    public class DownloadApk implements Runnable {
        private ProgressDialog dialog;
        InputStream is;
        FileOutputStream fos;
        private Context context;

        public DownloadApk(String url, int type) {
            this.url = url;
            this.type = type;
        }

        private String url;
        private int type = 2;//1 下载apk  2 下载 live文件

        /**
         * 下载完成,提示用户安装
         */
        private void installApk(File file) {
            isStar = false;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    if (!isLogin) {
//                        llBg.setVisibility(View.VISIBLE);
//                    }
//                    llBgs.setVisibility(View.GONE);
                }
            });


            Intent intent = new Intent(Intent.ACTION_VIEW);
            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }


            HomeActivitys.this.startActivityForResult(intent, 0);
        }

        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder().get().url(url).build();
            try {

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {

                    //获取内容总长度
                    long contentLength = response.body().contentLength();
                    max = contentLength;


                    String apkName = null;
                    File apkFile = null;
                    //设置最大值
                    //保存到sd卡
                    if (type == 1) {
                        apkName = url.substring(url.lastIndexOf("/") + 1, url.length());
                        apkFile = new File(Environment.getExternalStorageDirectory(), apkName);
                    } else {
                        apkFile = new File(StorageUtils.getCacheDirectory(HomeActivitys.this).getAbsolutePath() + File.separator, "live.zip");

                    }
                    fos = new FileOutputStream(apkFile);
                    //获得输入流
                    is = response.body().byteStream();
                    //定义缓冲区大小
                    byte[] bys = new byte[1024];
                    int progress = 0;
                    int len = -1;
                    while ((len = is.read(bys)) != -1) {

                            fos.write(bys, 0, len);
                            fos.flush();
                            progress += len;
                            notify += len;
                            if (notify / 1024 == 1024) {
                                notify = 0;
                                current = progress;
                                start(progress);
                            }



                    }


                    //下载完成,提示用户安装
                    if (type == 1) {
                        isFinsh=true;
                        if(shapeLoadingDialog!=null){
                            shapeLoadingDialog.dismiss();
                        }

                        installApk(apkFile);
                    } else {
                        String SD_DIR = StorageUtils.getCacheDirectory(HomeActivitys.this).getAbsolutePath() + File.separator;
                        upZipFile(new File(apkFile.getAbsolutePath()), SD_DIR);
                        Common.unZip();
                        CanTingAppLication.isComplete = true;
                        RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.DOWN_COMPELTE, ""));
                    }

                }
            } catch (IOException e) {
                return;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //关闭io流
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    is = null;
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fos = null;
                }
            }

        }
    }

    /**
     * 解压缩
     * 将zipFile文件解压到folderPath目录下.
     *
     * @param zipFile    zip文件
     * @param folderPath 解压到的地址
     * @throws IOException
     */
    private void upZipFile(File zipFile, String folderPath) throws IOException {
        ZipFile zfile = new ZipFile(zipFile);
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            ze = (ZipEntry) zList.nextElement();
            if (ze.isDirectory()) {
                Log.d(TAG, "ze.getName() = " + ze.getName());
                String dirstr = folderPath + ze.getName();
                dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                Log.d(TAG, "str = " + dirstr);
                File f = new File(dirstr);
                f.mkdir();
                continue;
            }
            Log.d(TAG, "ze.getName() = " + ze.getName());
            OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
            InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
            int readLen = 0;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
        }
        zfile.close();
    }

    private long notify = 0;
//   downurl1.add("http://119.23.212.8:8080/live/aliyun_svideo_animation_filter.zip");
//        downurl1.add("http://119.23.212.8:8080/live/aliyun_svideo_caption.zip");
//        downurl1.add("http://119.23.212.8:8080/live/aliyun_svideo_filter.zip");
//        downurl1.add("http://119.23.212.8:8080/live/aliyun_svideo_mv.zip");
//        downurl1.add("http://119.23.212.8:8080/live/aliyun_svideo_overlay.zip");
//        downurl1.add("http://119.23.212.8:8080/live/tail.zip");
//        downurl2.add("http://119.23.212.8:8080/live/filter.zip");
//        downurl2.add("http://119.23.212.8:8080/live/maohuzi.zip");
//        downurl2.add("http://119.23.212.8:8080/livemodel.zip");
//        downurl2.add("http://119.23.212.8:8080/live/mp3.zip");
//        downurl3.add("http://119.23.212.8:8080/live/encrypt.zip");
//
//        new Thread(new HomeActivity.DownloadApk("http://119.23.212.8:8080/live.zip",2)).start();
//}
//    private List<String> downurl1=new ArrayList<>();
//    private List<String> downurl2=new ArrayList<>();
//    private List<String> downurl3=new ArrayList<>();

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     *
     * @param baseDir     指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
    public File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr = null;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                try {
                    substr = new String(substr.getBytes("8859_1"), "GB2312");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ret = new File(ret, substr);

            }
            Log.d(TAG, "1ret = " + ret);
            if (!ret.exists())
                ret.mkdirs();
            substr = dirs[dirs.length - 1];
            try {
                substr = new String(substr.getBytes("8859_1"), "GB2312");
                Log.d(TAG, "substr = " + substr);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ret = new File(ret, substr);
            Log.d(TAG, "2ret = " + ret);
            return ret;
        }
        return ret;
    }

}

