package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.yway.scomponent.commonres.view.layout.NiceImageView;
import com.yway.scomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.mvp.model.entity.VisitorRecordBean;

import butterknife.BindView;

/**
 * ================================================
 * approvalStatusStrs
 * ================================================
 */
public class VisitorItemHolder extends BaseHolder<VisitorRecordBean> {

    @BindView(R2.id.tv_time_name)
    AppCompatTextView mTvTime;

    @BindView(R2.id.tv_user_name)
    AppCompatTextView mTvUserName;

    @BindView(R2.id.tv_phone)
    AppCompatTextView mTvPhone;

    @BindView(R2.id.tv_organ_name)
    AppCompatTextView mTvOrganName;
    @BindView(R2.id.tv_status)
    AppCompatTextView mTvStatus;
    @BindView(R2.id.niv_head)
    NiceImageView mNiceImageView;
    @BindView(R2.id.tv_temperature)
    AppCompatTextView mTvTemperature;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架

    public VisitorItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到 Context 的地方,拿到 AppComponent,从而得到用 Dagger 管理的单例对象
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(VisitorRecordBean data, int position) {
        mTvTime.setText(data.getCreateTime());
        mTvUserName.setText(data.getName());
        mTvPhone.setText(data.getCellPhone());
        mTvOrganName.setText(data.getOrgTitle());
        mTvTemperature.setText(Utils.appendStr(data.getTemperature(),"℃"));
        if (!StringUtils.isEmpty(data.getVisitStatus()) && data.getVisitStatus().equals("1")){
            mTvStatus.setText("已到访");
            mTvTime.setText(data.getUpdateTime());
        }

        if (!StringUtils.isEmpty(data.getTemperature())){
            double d = Double.parseDouble(data.getTemperature());
            if (d >= 37){
                mTvTemperature.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.public_color_text_sign));
            }else{
                mTvTemperature.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.public_color_text_success));
            }
        }
        //加载头像
        if (!StringUtils.isEmpty(data.getPersonalPhotos())) {
            //itemView 的 Context 就是 Activity, Glide 会自动处理并和该 Activity 的生命周期绑定
            mImageLoader.loadImage(itemView.getContext(),
                    CommonImageConfigImpl
                            .builder()
                            .url(data.getPersonalPhotos())
                            .imageView(mNiceImageView)
                            .placeholder(R.mipmap.public_ic_default_head)
                            .build());
        }
        itemView.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
