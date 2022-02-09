package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.mvp.model.entity.CheckBoxBean;

import butterknife.BindView;

/**
 * ================================================
 * 配方原材料
 * ================================================
 */
public class CheckBoxItemHolder extends BaseHolder<CheckBoxBean> {
    @BindView(R2.id.checkbox)
    AppCompatCheckBox mCheckBox;

    public CheckBoxItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(CheckBoxBean data, int position) {
        mCheckBox.setText(data.getCheckBoxName());
        mCheckBox.setChecked(data.isChecked());
        itemView.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
