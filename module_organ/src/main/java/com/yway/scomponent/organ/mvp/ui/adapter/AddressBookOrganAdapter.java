
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.organ.mvp.ui.holder.AddressBookOrganItemHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 通讯录组织机构
 * ================================================
 */
public class AddressBookOrganAdapter extends DefaultAdapter<AddressCompanyBean> {
    public AddressBookOrganAdapter(List<AddressCompanyBean> infos) {
        super(infos);
    }

    /**
     * 刷新
     *
     * @return
     */
    public void refreshData(List<AddressCompanyBean> data) {
        //校验是否标签是否存在列表中，如果存在列表中则返回下标位置
        this.mInfos = data;
        notifyDataSetChanged();
    }

    public void addOrgan(AddressCompanyBean data){
        for (int i = 0; i < this.getInfos().size(); i++) {
            this.mInfos.get(i).setFlag(1);
        }
        this.mInfos.add(data);
        notifyDataSetChanged();
    }

    public void removeOrgan(int position){
        //创建即将删除的组织机构
        List<AddressCompanyBean> list = new ArrayList<>();

        for (int i = position+1; i < this.mInfos.size(); i++) {
            list.add(this.mInfos.get(i));
        }

        this.mInfos.removeAll(list);

        this.mInfos.get(this.mInfos.size()-1).setFlag(0);
        notifyDataSetChanged();
    }


    @Override
    public BaseHolder<AddressCompanyBean> getHolder(View v, int viewType) {
        return new AddressBookOrganItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.organ_item_address_book_organ;
    }
}
