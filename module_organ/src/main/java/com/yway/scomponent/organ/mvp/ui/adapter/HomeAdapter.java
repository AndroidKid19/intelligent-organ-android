
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceBean;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceTitleBean;
import com.yway.scomponent.organ.mvp.model.entity.MessageBean;
import com.yway.scomponent.organ.mvp.model.entity.MessageTitleBean;
import com.yway.scomponent.organ.mvp.ui.holder.CofItemHolder;
import com.yway.scomponent.organ.mvp.ui.holder.CofTitleItemHolder;
import com.yway.scomponent.organ.mvp.ui.holder.MsgItemHolder;
import com.yway.scomponent.organ.mvp.ui.holder.MsgTitleItemHolder;

import java.util.List;

/**
 * ================================================
 * 配料占比适配器
 * ================================================
 */
public class HomeAdapter extends DefaultAdapter<Object> {
    public static final int MSG_TITLE = 0;//消息标题
    public static final int MSG_CONTENT = 1;//消息内容
    public static final int COF_TITLE = 2;//会议标题
    public static final int COF_CONTENT = 3;//会议内容
    public static final int COF_CONTENT_ING = 4;//会议内容 开会中

    public HomeAdapter(List<Object> infos) {
        super(infos);
    }

    /**
     * 刷新首页消息及会议内容
     *
     * @param objects 消息及会议内容
     * @return
     */
    public void refreshData(List<Object> objects) {
        //校验是否标签是否存在列表中，如果存在列表中则返回下标位置
        this.mInfos = objects;
        notifyDataSetChanged();
    }

    @Override
    public BaseHolder<Object> getHolder(View v, int viewType) {
        switch (viewType) {//根据viewtyupe来区分，是标题还是数据项
            case MSG_TITLE://标题
                return new MsgTitleItemHolder(v);
            case MSG_CONTENT://数据项
                return new MsgItemHolder(v);
            case COF_TITLE://标题
                return new CofTitleItemHolder(v);
            case COF_CONTENT://数据项
            case COF_CONTENT_ING:
                return new CofItemHolder(v);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        //这个方法很重要，这里根据position取出items集合中的对象，用instanceof判断他是标题还是数据项，来返回对应的标识
        if (mInfos.get(position) instanceof MessageTitleBean) {//根据items数据类型的不同来判断他是标题还是数据项
            return MSG_TITLE;
        } else if (mInfos.get(position) instanceof MessageBean) {
            return MSG_CONTENT;
        } else if (mInfos.get(position) instanceof ConferenceTitleBean) {
            return COF_TITLE;
        } else if (mInfos.get(position) instanceof ConferenceBean) {
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

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {//根据viewtyupe来区分，是标题还是数据项
            case MSG_TITLE:
                return R.layout.organ_home_item_meg_title;
            case MSG_CONTENT:
                return R.layout.organ_home_item_meg;
            case COF_TITLE:
                return R.layout.organ_home_item_conference_title;
            case COF_CONTENT:
                return R.layout.organ_home_item_conference;
            case COF_CONTENT_ING:
                return R.layout.organ_home_item_conference_ing;
        }
        return -1;
    }

}
