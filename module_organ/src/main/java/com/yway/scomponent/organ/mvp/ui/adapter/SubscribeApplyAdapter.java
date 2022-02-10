
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.model.entity.CheckBoxBean;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;
import com.yway.scomponent.organ.mvp.ui.holder.CheckBoxItemHolder;
import com.yway.scomponent.organ.mvp.ui.holder.SubscribeApplyItemHolder;

import java.util.List;

/**
 * ================================================
 * 预约审核
 * ================================================
 */
public class SubscribeApplyAdapter extends DefaultAdapter<MeetingRecordBean> {
    public SubscribeApplyAdapter(List<MeetingRecordBean> infos) {
        super(infos);
    }

    /**
     * 刷新
     * @return
     */
    public void refreshData(List<MeetingRecordBean> data) {
        //校验是否标签是否存在列表中，如果存在列表中则返回下标位置
        this.mInfos = data;
        notifyDataSetChanged();
    }

    @Override
    public BaseHolder<MeetingRecordBean> getHolder(View v, int viewType) {
        return new SubscribeApplyItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.organ_item_subscribe_apply;
    }
}
