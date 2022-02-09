
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceBean;
import com.yway.scomponent.organ.mvp.ui.holder.ConfirmMeetingItemHolder;

import java.util.List;

/**
 * ================================================
 * 已同意会议
 * ================================================
 */
public class ConfirmMeetingAdapter extends DefaultAdapter<Object> {
    public static final int COF_CONTENT = 3;//会议内容
    public static final int COF_CONTENT_ING = 4;//会议内容 开会中

    public ConfirmMeetingAdapter(List<Object> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<Object> getHolder(View v, int viewType) {
        return new ConfirmMeetingItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {//根据viewtyupe来区分，是标题还是数据项
            case COF_CONTENT:
                return R.layout.organ_home_item_conference_white;
            case COF_CONTENT_ING:
                return R.layout.organ_home_item_conference_ing;
        }
        return -1;
    }


    @Override
    public int getItemViewType(int position) {
        //这个方法很重要，这里根据position取出items集合中的对象，用instanceof判断他是标题还是数据项，来返回对应的标识
        if (mInfos.get(position) instanceof ConferenceBean) {
            ConferenceBean conferenceBean = (ConferenceBean) mInfos.get(position);
            if (conferenceBean.isConfIng()) {
                return COF_CONTENT_ING;
            } else {
                return COF_CONTENT;
            }
        } else {
            return -1;
        }
    }
}
