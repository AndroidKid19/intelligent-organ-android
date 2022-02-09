
package com.yway.scomponent.user.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.user.R;
import com.yway.scomponent.user.mvp.model.entity.MessageBean;
import com.yway.scomponent.user.mvp.ui.holder.MessageItemHolder;
import java.util.List;

/**
 * ================================================
 *  常用语
 * ================================================
 */
public class MessageAdapter extends DefaultAdapter<MessageBean> {

    public MessageAdapter(List<MessageBean> infos) {
        super(infos);
    }


    @Override
    public BaseHolder<MessageBean> getHolder(View v, int viewType) {
        return new MessageItemHolder(v);
    }


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.user_item_message;
    }
}
