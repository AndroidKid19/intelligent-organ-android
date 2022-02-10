package com.yway.scomponent.organ.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yway.scomponent.commonres.calendar.Calendar;
import com.yway.scomponent.commonres.calendar.CalendarLayout;
import com.yway.scomponent.commonres.calendar.CalendarView;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerOnlineSubscribeRoomComponent;
import com.yway.scomponent.organ.mvp.contract.OnlineSubscribeRoomContract;
import com.yway.scomponent.organ.mvp.model.entity.RoomDetailsBean;
import com.yway.scomponent.organ.mvp.model.entity.SubscribeTimeBean;
import com.yway.scomponent.organ.mvp.presenter.OnlineSubscribeRoomPresenter;
import com.yway.scomponent.organ.mvp.ui.adapter.ConferenceRoomAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import butterknife.BindView;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 会议室预约
 */
@Route(path = RouterHub.HOME_ONLINESUBSCRIBEROOMACTIVITY)
public class OnlineSubscribeRoomActivity extends BaseActivity<OnlineSubscribeRoomPresenter> implements OnlineSubscribeRoomContract.View,
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener{
    /**
     * View 月
     */
    @BindView(R2.id.tv_month_day)
    AppCompatTextView mTextMonthDay;
    /**
     * View 年
     */
    @BindView(R2.id.tv_year)
    AppCompatTextView mTextYear;
    /**
     * View 当天
     */
    @BindView(R2.id.tv_lunar)
    AppCompatTextView mTextLunar;
    /**
     * View 日
     */
    @BindView(R2.id.tv_current_day)
    AppCompatTextView mTextCurrentDay;

    /**
     * View 会议室列表
     */
    @BindView(R2.id.recycle_view)
    RecyclerView mRecyclerView;

    /**
     * View 日历
     */
    @BindView(R2.id.calendar_view)
    CalendarView mCalendarView;
    /**
     * ViewGroup 日历
     */
    @BindView(R2.id.calendarLayout)
    CalendarLayout mCalendarLayout;
    /**
     * 注入列表管理器
     */
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    ConferenceRoomAdapter mAdapter;
    /**
     * 注入列表数据源
     */
    @Inject
    List<RoomDetailsBean> mDataLs;
    /**
     * 记录当前年
     * */
    private int mYear;
    /**
     * 参数
     * */
    private Map<String,Object> paramMap = new HashMap<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOnlineSubscribeRoomComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.organ_activity_online_subscribe_room; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();
        initRecyclerView();

        initCalendar();
        //初始化查询会议室
        paramMap.put("meetingDate", TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd")));
        mPresenter.queryMeetingRoomPageList(paramMap,true);
    }

    /**
     * 初始化日历
     * */
    private void initCalendar() {
        //日历监听
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        //设置年
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        //设置月
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月");
        //标记今日
        mTextLunar.setText("今日");
        //设置当前几号
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
        //记录当前年
        mYear = mCalendarView.getCurYear();
        mTextCurrentDay.setOnClickListener(v -> {
            mCalendarView.scrollToCurrent();
        });
        mCalendarView.setOnCalendarSelectListener(this);
    }


    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        //设置下拉刷新监听
//        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        //设置上拉加载
//        mRefreshLayout.setOnLoadMoreListener(mOnLoadMoreListener);
//        RoomDetailsBean roomDetailsBean = new RoomDetailsBean();
//        roomDetailsBean.setId("1");
//        roomDetailsBean.setName("区委机关1号楼(北楼)一楼会议室");
//        roomDetailsBean.setLocation("1号楼(北楼)");
//        roomDetailsBean.setSeatsNumber("30");
//        List<SubscribeTimeBean> list = new ArrayList<>();
//        SubscribeTimeBean subscribeTimeBean = new SubscribeTimeBean();
//        subscribeTimeBean.setKey("上午");
//        subscribeTimeBean.setTime("8:00");
//        subscribeTimeBean.setWhetherAppointment("0");
//        list.add(subscribeTimeBean);
//        subscribeTimeBean = new SubscribeTimeBean();
//        subscribeTimeBean.setKey("上午");
//        subscribeTimeBean.setTime("9:00");
//        subscribeTimeBean.setWhetherAppointment("0");
//        list.add(subscribeTimeBean);
//        subscribeTimeBean = new SubscribeTimeBean();
//        subscribeTimeBean.setKey("上午");
//        subscribeTimeBean.setTime("10:00");
//        subscribeTimeBean.setWhetherAppointment("1");
//        list.add(subscribeTimeBean);
//        subscribeTimeBean = new SubscribeTimeBean();
//        subscribeTimeBean.setKey("上午");
//        subscribeTimeBean.setTime("11:00");
//        subscribeTimeBean.setWhetherAppointment("1");
//        list.add(subscribeTimeBean);
//        subscribeTimeBean = new SubscribeTimeBean();
//        subscribeTimeBean.setKey("上午");
//        subscribeTimeBean.setTime("12:00");
//        subscribeTimeBean.setWhetherAppointment("1");
//        list.add(subscribeTimeBean);subscribeTimeBean = new SubscribeTimeBean();
//        subscribeTimeBean.setKey("上午");
//        subscribeTimeBean.setTime("13:00");
//        subscribeTimeBean.setWhetherAppointment("1");
//        list.add(subscribeTimeBean);
//
//        roomDetailsBean.setMeetingTimeBatRspBOList(list);
//        mDataLs.add(roomDetailsBean);
//        mDataLs.add(roomDetailsBean);


        mAdapter.setOnItemClickListener((view, viewType, data, position) -> {
            RoomDetailsBean roomDetailsBean1 = (RoomDetailsBean)data;
            String month = mCalendarView.getCurMonth() < 10 ? Utils.appendStr("0",mCalendarView.getCurMonth()) : mCalendarView.getCurMonth()+"";
            String day = mCalendarView.getCurDay() < 10 ? Utils.appendStr("0",mCalendarView.getCurDay()) : mCalendarView.getCurDay()+"";
            String strDate = Utils.appendStr(mCalendarView.getCurYear(),"-",month,"-",day);
            roomDetailsBean1.setMeetingDate(strDate);
            if (ObjectUtils.isEmpty(roomDetailsBean1.getSubscribeTimeBean()) || StringUtils.isEmpty(roomDetailsBean1.getSubscribeTimeBean().getTime())){
                ArmsUtils.snackbarText("请选择会议时间");
                return;
            }
            Utils.postcard(RouterHub.HOME_APPLYROOMACTIVITY)
                    .withParcelable("roomDetailsBean",roomDetailsBean1)
                    .navigation(getActivity());
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextMonthDay.setText(calendar.getMonth() + "月");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());

        paramMap.put("meetingDate", Utils.appendStr(calendar.getYear(),"-",calendar.getMonth(),"-",calendar.getDay()));
        mPresenter.queryMeetingRoomPageList(paramMap,true);

    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }
}