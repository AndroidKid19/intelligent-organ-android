package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;

import butterknife.BindView;

/**
 * ================================================
 * 参会单位
 * ================================================
 */
public class ChooseCompanyItemHolder extends BaseHolder<AddressCompanyBean> {
    @BindView(R2.id.tv_company_name)
    AppCompatTextView mTvCompanyName;
    @BindView(R2.id.iv_close)
    AppCompatImageView mIvClose;

    public ChooseCompanyItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(AddressCompanyBean data, int position) {
        mTvCompanyName.setText(data.getOrgTitle());
        mIvClose.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
