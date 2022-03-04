
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceBean;
import com.yway.scomponent.organ.mvp.ui.holder.MeetingItemHolder;

import java.util.List;

/**
 * ================================================
 * 资讯
 * ================================================
 */
public class MeetingAdapter extends DefaultAdapter<ConferenceBean> {
    public static final int COF_CONTENT = 3;//会议内容
    public static final int COF_CONTENT_ING = 4;//会议内容 开会中

    public MeetingAdapter(List<ConferenceBean> infos) {
        super(infos);
    }

    /**
     * 刷新
     *
     * @return
     */
    public void refreshData(List<ConferenceBean> data) {
        //校验是否标签是否存在列表中，如果存在列表中则返回下标位置
        this.mInfos = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        //这个方法很重要，这里根据position取出items集合中的对象，用instanceof判断他是标题还是数据项，来返回对应的标识
        ConferenceBean conferenceBean = (ConferenceBean) mInfos.get(position);
        if (conferenceBean.isConfIng()) {
            return COF_CONTENT_ING;
        } else {
            return COF_CONTENT;
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {//根据viewtyupe来区分，是标题还是数据项
            case COF_CONTENT:
                return R.layout.organ_home_item_conference;
            case COF_CONTENT_ING:
                return R.layout.organ_home_item_conference_ing;
        }
        return -1;
    }

    @Override
    public BaseHolder<ConferenceBean> getHolder(View v, int viewType) {
        return new MeetingItemHolder(v);
    }

}
