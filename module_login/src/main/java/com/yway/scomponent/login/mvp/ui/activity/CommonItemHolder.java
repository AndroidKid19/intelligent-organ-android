
package com.yway.scomponent.login.mvp.ui.activity;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.commonres.view.layout.SettingBar;
import com.yway.scomponent.login.R2;
import com.yway.scomponent.login.mvp.model.entity.TagInfo;

import butterknife.BindView;

/**
 * ================================================
 * 耳标显示
 * ================================================
 */
public class CommonItemHolder extends BaseHolder<TagInfo> {
    @BindView(R2.id.bar_title)
    SettingBar mBarTitle;
    public CommonItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(TagInfo data, int position) {
        mBarTitle.setLeftText(data.toString());
    }

    @Override
    protected void onRelease() {
        mBarTitle = null;
    }
}
