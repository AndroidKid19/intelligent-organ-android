
package com.yway.scomponent.user.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.user.R;
import com.yway.scomponent.user.mvp.ui.holder.CommonItemHolder;

import java.util.List;

/**
 * ================================================
 *
 * ================================================
 */
public class CommonAdapter extends DefaultAdapter<Integer> {
    public CommonAdapter(List<Integer> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<Integer> getHolder(View v, int viewType) {
        return new CommonItemHolder(v);
    }


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.user_item_common;
    }
}
