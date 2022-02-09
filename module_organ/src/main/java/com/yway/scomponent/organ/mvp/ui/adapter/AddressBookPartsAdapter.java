
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.organ.mvp.ui.holder.AddressBookBumenItemHolder;
import com.yway.scomponent.organ.mvp.ui.holder.AddressBookPersonItemHolder;

import java.util.List;

/**
 * ================================================
 * 通讯录组织机构
 * ================================================
 */
public class AddressBookPartsAdapter extends DefaultAdapter<Object> {
    private final int TYPE_BUMEN = 0;//部门类型的
    private final int TYPE_PERSON = 1;//个人的

    public AddressBookPartsAdapter(List<Object> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<Object> getHolder(View v, int viewType) {
        switch (viewType) {//根据viewtyupe来区分，是标题还是数据项
            case TYPE_BUMEN://部门类型的
                return new AddressBookBumenItemHolder(v);
            case TYPE_PERSON://个人的
                return new AddressBookPersonItemHolder(v);
        }
        return null;
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
                return R.layout.organ_item_address_book_person;
        }
        return -1;
    }
}
