package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;

import butterknife.BindView;

/**
 * ================================================
 * 通讯录组织机构选择
 * ================================================
 */
public class AddressBookOrganItemHolder extends BaseHolder<AddressCompanyBean> {
    @BindView(R2.id.tv_organ_name)
    AppCompatTextView mTvOragnName;
    @BindView(R2.id.iv_right)
    AppCompatImageView mIvRight;

    public AddressBookOrganItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(AddressCompanyBean data, int position) {
        if (position == 0) {
            mIvRight.setVisibility(View.GONE);
        }else{
            mIvRight.setVisibility(View.VISIBLE);
        }
        mTvOragnName.setText(data.getOrgTitle());
        if (data.getFlag() == 0) {
            mTvOragnName.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.public_color_text_gray));
        } else if (data.getFlag() == 1) {//蓝色
            mTvOragnName.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.public_colorConfirm));
        }
    }

    @Override
    protected void onRelease() {
    }
}
