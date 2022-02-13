package com.yway.scomponent.user.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.yway.scomponent.commonres.view.layout.NiceImageView;
import com.yway.scomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import com.yway.scomponent.user.R2;
import com.yway.scomponent.user.mvp.model.entity.ArticleBean;

import butterknife.BindView;

/**
 * ================================================
 * 收藏
 * ================================================
 */
public class CollectItemHolder extends BaseHolder<Object> {

    @BindView(R2.id.tv_msg_title)
    AppCompatTextView mTvMsgTitle;
    @BindView(R2.id.tv_msg_date)
    AppCompatTextView mTvMsgDate;
    @BindView(R2.id.tv_msg_cover)
    NiceImageView mTvMsgCover;
    @BindView(R2.id.iv_collect)
    AppCompatImageView mIvCollect;
    @BindView(R2.id.view_collect)
    View mViewCollect;


    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架

    public CollectItemHolder(View itemView) {
        super(itemView);      //可以在任何可以拿到 Context 的地方,拿到 AppComponent,从而得到用 Dagger 管理的单例对象
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(Object data, int position) {
        ArticleBean messageBean = (ArticleBean) data;
        mTvMsgTitle.setText(messageBean.getTitle());
        mTvMsgDate.setText(messageBean.getCreateTime());
        //加载封面
        if (!StringUtils.isEmpty(messageBean.getCoverPictureUrl())) {
            //itemView 的 Context 就是 Activity, Glide 会自动处理并和该 Activity 的生命周期绑定
            mImageLoader.loadImage(itemView.getContext(),
                    CommonImageConfigImpl
                            .builder()
                            .url(messageBean.getCoverPictureUrl())
                            .imageView(mTvMsgCover)
                            .build());
        }
        mViewCollect.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
