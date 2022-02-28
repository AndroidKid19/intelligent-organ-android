package com.yway.scomponent.organ.mvp.ui.activity;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.CollectionUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.base.BaseActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.utils.ArmsUtils;

import android.content.Intent;
import android.view.View;

import com.yway.scomponent.commonres.view.layout.ClearEditText;
import com.yway.scomponent.commonres.view.layout.MultipleStatusView;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.commonsdk.core.Constants;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerUserSearchComponent;
import com.yway.scomponent.organ.mvp.contract.UserSearchContract;
import com.yway.scomponent.organ.mvp.presenter.UserSearchPresenter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.ui.adapter.AddressBookPartsAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 用户搜索
 */
@Route(path = RouterHub.HOME_USERSEARCHACTIVITY)
public class UserSearchActivity extends BaseActivity<UserSearchPresenter> implements UserSearchContract.View {

    /**
     * 多状态view
     */
    @BindView(R2.id.multiple_layout)
    MultipleStatusView mMultipleStatusView;
    /**
     * RecycleView
     **/
    @BindView(R2.id.recycle_view)
    RecyclerView mRecyclerView;

    /**
     * 搜索内容
     **/
    @BindView(R2.id.et_search_text)
    ClearEditText mTvContentCount;
    /**
     * 注入列表管理器
     */
    @Inject
    RecyclerView.LayoutManager mLayoutManager;

    @Inject
    AddressBookPartsAdapter mAddressBookPartsAdapter;

    /**
     * 注入列表数据源
     */
    @Inject
    List<Object> mObjectDataLs;

    @Autowired(name = "addressCompanyBean")
    AddressCompanyBean mAddressCompanyBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.organ_activity_user_search; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.layout_search).init();
        initRecyclerView();
        if (CollectionUtils.isEmpty(mAddressCompanyBean.getSysUserRspBOList())){
            mMultipleStatusView.showEmpty();
        }else{
            mMultipleStatusView.showContent();
            mObjectDataLs.addAll(mAddressCompanyBean.getSysUserRspBOList());

        }
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        //初始化部门及人员
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);

        mRecyclerView.setAdapter(mAddressBookPartsAdapter);
        //设置部门及人员事件监听
        mAddressBookPartsAdapter.setOnItemClickListener(mOnAddressBookPartsItemClickListener);
    }


    /**
     * 组织机构及人员事件回调监听
     */
    private DefaultAdapter.OnRecyclerViewItemClickListener mOnAddressBookPartsItemClickListener = (view, viewType, data, position) -> {
        if (data instanceof AddressCompanyBean) {//根据items数据类型的不同来判断他是标题还是数据项

        } else if (data instanceof UserInfoBean) {
            //人员
            Utils.postcard(RouterHub.USER_USERDETAILSACTIVITY)
                    .withParcelable(Constants.APP_USER_INFO, (UserInfoBean) data)
                    .withString(Constants.APP_USER_ORGAN, "")
                    .navigation(getActivity());
        }
    };

    @OnClick(R2.id.tv_close)
    void  onCloseClick(View view){
        finish();
    }

    @OnTextChanged(R2.id.et_search_text)
    void onTextChanged(CharSequence text){
        //内容改变监听
        if (mTvContentCount.getText().length() > 0) {
            List<UserInfoBean> userInfoBeans = mAddressCompanyBean.getSysUserRspBOList();
            List<UserInfoBean> newUsers = new ArrayList<>();
            for (int i = 0; i < userInfoBeans.size(); i++) {
                if (userInfoBeans.get(i).getName().indexOf(mTvContentCount.getText().toString()) != -1){
                    newUsers.add(userInfoBeans.get(i));
                }
            }

            mObjectDataLs.clear();
            mObjectDataLs.addAll(newUsers);
            mAddressBookPartsAdapter.notifyDataSetChanged();
        }else{
            mObjectDataLs.clear();
            mObjectDataLs.addAll(mAddressCompanyBean.getSysUserRspBOList());
            mAddressBookPartsAdapter.notifyDataSetChanged();
        }
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
}