
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.organ.mvp.ui.holder.AddressBookBumenItemHolder;
import com.yway.scomponent.organ.mvp.ui.holder.ChooseUserItemHolder;

import java.util.List;

/**
 * ================================================
 * 通讯录组织机构
 * ================================================
 */
public class ChooseUserAdapter extends DefaultAdapter<Object> {
    private final int TYPE_BUMEN = 0;//部门类型的
    private final int TYPE_PERSON = 1;//个人的

    public ChooseUserAdapter(List<Object> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<Object> getHolder(View v, int viewType) {
        switch (viewType) {//根据viewtyupe来区分，是标题还是数据项
            case TYPE_BUMEN://部门类型的
                return new AddressBookBumenItemHolder(v);
            case TYPE_PERSON://个人的
                return new ChooseUserItemHolder(v);
        }
        return null;
    }

    /**
     * 选中当前用户
     * */
    public void checkedUser(int position){
        UserInfoBean userInfoBean = (UserInfoBean) mInfos.get(position);
        userInfoBean.setChecked(!userInfoBean.isChecked());
        mInfos.set(position,userInfoBean);
        notifyItemChanged(position);
    }

    /**
     * 全选
     * */
    public void allChecked(boolean choose){
        for (int i = 0; i < mInfos.size(); i++) {
            if (mInfos.get(i) instanceof UserInfoBean){
                UserInfoBean userInfoBean = (UserInfoBean) mInfos.get(i);
                userInfoBean.setChecked(choose);
                mInfos.set(i,userInfoBean);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 删除当前选中人员
     * */
    public void removeChecked(UserInfoBean userInfoBean){
        for (int i = 0; i < mInfos.size(); i++) {
            if (mInfos.get(i) instanceof UserInfoBean){
                UserInfoBean userInfo = (UserInfoBean) mInfos.get(i);
                if (userInfoBean.getUserId().equals(userInfo.getUserId())){
                    userInfo.setChecked(false);
                    mInfos.set(i,userInfoBean);
                    notifyItemChanged(i);
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        //这个方法很重要，这里根据position取出items集合中的对象，用instanceof判断他是标题还是数据项，来返回对应的标识
        if (mInfos.get(position) instanceof AddressCompanyBean) {//根据items数据类型的不同来判断他是标题还是数据项
            return TYPE_BUMEN;
        } else if (mInfos.get(position) instanceof UserInfoBean) {
            return TYPE_PERSON;
        } else {
            return -1;
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {//根据viewtyupe来区分，是标题还是数据项
            case TYPE_BUMEN:
                return R.layout.organ_item_address_book_bumen;
            case TYPE_PERSON:
                return R.layout.organ_item_choose_user;
        }
        return -1;
    }
}
