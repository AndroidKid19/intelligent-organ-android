
package com.yway.scomponent.user.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.user.R;
import com.yway.scomponent.user.mvp.ui.holder.CollectItemHolder;

import java.util.List;

/**
 * ================================================
 * 收藏
 * ================================================
 */
public class CollectAdapter extends DefaultAdapter<Object> {
    public CollectAdapter(List<Object> infos) {
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

    public void removePosition(int removePosition) {
        this.mInfos.remove(removePosition);
        notifyItemRemoved(removePosition);
    }

    @Override
    public BaseHolder<Object> getHolder(View v, int viewType) {
        return new CollectItemHolder(v);
    }


    @Override
    public int getLayoutId(int viewType) {

        return R.layout.user_home_item_meg;
    }

}
