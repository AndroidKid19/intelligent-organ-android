
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.ui.holder.CheckedUserItemHolder;
import java.util.List;

/**
 * ================================================
 * 选中人员
 * ================================================
 */
public class CheckedUserAdapter extends DefaultAdapter<UserInfoBean> {
    public CheckedUserAdapter(List<UserInfoBean> infos) {
        super(infos);
    }


    /**
     * 批量添加选中人员
     * */
    public void addAllCheckedUser(List<UserInfoBean> userInfoBean){
        this.mInfos.addAll(userInfoBean);
        notifyDataSetChanged();
    }

    /**
     * 添加选中人员
     * */
    public void addCheckedUser(UserInfoBean userInfoBean){
        this.mInfos.add(userInfoBean);
        notifyDataSetChanged();
    }

    /**
     * 删除选中人员
     * */
    public void removeCheckedUser(UserInfoBean userInfoBean){
        this.mInfos.remove(userInfoBean);
        notifyDataSetChanged();
    }

    @Override
    public BaseHolder<UserInfoBean> getHolder(View v, int viewType) {
        return new CheckedUserItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.organ_item_checked_user;
    }
}
