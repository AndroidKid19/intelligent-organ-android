package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.yway.scomponent.commonres.view.layout.NiceImageView;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;

import butterknife.BindView;

/**
 * ================================================
 * 通讯录组织机构选择
 * ================================================
 */
public class ChooseUserItemHolder extends BaseHolder<Object> {
    /**
     * 头像
     */
    @BindView(R2.id.niv_head)
    NiceImageView mNiceImageView;
    /**
     * 姓名
     */
    @BindView(R2.id.tv_user_name)
    AppCompatTextView mTvUserName;
    /**
     * 岗位
     */
    @BindView(R2.id.tv_user_office)
    AppCompatTextView mTvUserOffice;
    /**
     * 是否选中
     */
    @BindView(R2.id.checkbox)
    AppCompatCheckBox mCheckBox;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架

    public ChooseUserItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到 Context 的地方,拿到 AppComponent,从而得到用 Dagger 管理的单例对象
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(Object data, int position) {
        UserInfoBean userInfoBean = (UserInfoBean) data;
        //是否选中
        mCheckBox.setChecked(userInfoBean.isChecked());
        //加载姓名
        mTvUserName.setText(Utils.appendStr(userInfoBean.getName()));
        //加载头像
        if (!StringUtils.isEmpty(userInfoBean.getSysUserFilePath())) {
            //itemView 的 Context 就是 Activity, Glide 会自动处理并和该 Activity 的生命周期绑定
            mImageLoader.loadImage(itemView.getContext(),
                    CommonImageConfigImpl
                            .builder()
                            .url(userInfoBean.getSysUserFilePath())
                            .imageView(mNiceImageView)
                            .placeholder(R.mipmap.public_ic_default_head)
                            .build());
        }
        mTvUserOffice.setText("干部");
        itemView.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
