
package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
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
 * 选中人员
 * ================================================
 */
public class CheckedUserItemHolder extends BaseHolder<UserInfoBean> {
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
     * 删除人员
     */
    @BindView(R2.id.iv_del)
    AppCompatImageView mIvDel;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架

    public CheckedUserItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到 Context 的地方,拿到 AppComponent,从而得到用 Dagger 管理的单例对象
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(UserInfoBean data, int position) {
        //加载姓名
        mTvUserName.setText(Utils.appendStr(data.getName()));
        //加载头像
        if (StringUtils.isEmpty(data.getSysUserFilePath())) {
            //itemView 的 Context 就是 Activity, Glide 会自动处理并和该 Activity 的生命周期绑定
            mImageLoader.loadImage(itemView.getContext(),
                    CommonImageConfigImpl
                            .builder()
                            .url(data.getSysUserFilePath())
                            .imageView(mNiceImageView)
                            .placeholder(R.mipmap.public_ic_default_head)
                            .build());
        }
        mIvDel.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
