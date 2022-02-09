
package com.yway.scomponent.user.mvp.ui.holder;
import android.view.View;
import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.commonres.view.layout.SettingBar;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.user.R2;
import butterknife.BindView;

/**
 * ================================================
 * 筛选
 * ================================================
 */
public class CommonItemHolder extends BaseHolder<Integer> {
    @BindView(R2.id.bar_title)
    SettingBar mBarTitle;
    public CommonItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Integer data, int position) {
//        mBarTitle.setLeftText(Utils.convertToStr(data));
    }

    @Override
    protected void onRelease() {
        mBarTitle = null;
    }
}
