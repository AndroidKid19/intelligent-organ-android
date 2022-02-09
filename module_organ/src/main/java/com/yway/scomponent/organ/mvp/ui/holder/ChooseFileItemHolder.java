package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.commonsdk.core.UploadFileBean;
import com.yway.scomponent.organ.R2;

import butterknife.BindView;

/**
 * ================================================
 * 参会单位
 * ================================================
 */
public class ChooseFileItemHolder extends BaseHolder<UploadFileBean> {
    @BindView(R2.id.tv_company_name)
    AppCompatTextView mTvCompanyName;
    @BindView(R2.id.iv_close)
    AppCompatImageView mIvClose;

    public ChooseFileItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(UploadFileBean data, int position) {
        mTvCompanyName.setText(data.getName());
        mIvClose.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
