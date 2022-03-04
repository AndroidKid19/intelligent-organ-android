package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.yway.scomponent.commonres.view.layout.NiceImageView;
import com.yway.scomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;
import com.yway.scomponent.organ.mvp.model.entity.MessageBean;

import butterknife.BindView;

/**
 * ================================================
 * 资讯
 * ================================================
 */
public class InformationItemHolder extends BaseHolder<MessageBean> {
    @BindView(R2.id.tv_msg_title)
    AppCompatTextView mTvMsgTitle;
    @BindView(R2.id.tv_msg_date)
    AppCompatTextView mTvMsgDate;
    @BindView(R2.id.tv_msg_cover)
    NiceImageView mTvMsgCover;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架

    public InformationItemHolder(View itemView) {
        super(itemView);
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(MessageBean data, int position) {
        mTvMsgTitle.setText(data.getTitle());
        mTvMsgDate.setText(data.getCreateTime());

        //加载封面
        if (!StringUtils.isEmpty(data.getCoverPictureUrl())) {
            //itemView 的 Context 就是 Activity, Glide 会自动处理并和该 Activity 的生命周期绑定
            mImageLoader.loadImage(itemView.getContext(),
                    CommonImageConfigImpl
                            .builder()
                            .url(data.getCoverPictureUrl())
                            .imageView(mTvMsgCover)
                            .build());
        }
        itemView.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
