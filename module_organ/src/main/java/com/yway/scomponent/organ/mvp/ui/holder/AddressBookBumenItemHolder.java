package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;

import butterknife.BindView;

/**
 * ================================================
 * 通讯录组织机构选择
 * ================================================
 */
public class AddressBookBumenItemHolder extends BaseHolder<Object> {
    @BindView(R2.id.tv_organ_name)
    AppCompatTextView mTvOragnName;

    public AddressBookBumenItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data, int position) {
        AddressCompanyBean addressCompanyBean = (AddressCompanyBean) data;
        mTvOragnName.setText(Utils.appendStr(addressCompanyBean.getOrgTitle()));
        itemView.setOnClickListener(this);

    }

    @Override
    protected void onRelease() {
    }
}
