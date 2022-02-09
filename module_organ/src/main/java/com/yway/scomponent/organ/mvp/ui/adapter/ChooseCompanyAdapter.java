
package com.yway.scomponent.organ.mvp.ui.adapter;
import android.view.View;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.organ.mvp.ui.holder.ChooseCompanyItemHolder;
import java.util.List;

/**
 * ================================================
 * 参会公司适配器
 * ================================================
 */
public class ChooseCompanyAdapter extends DefaultAdapter<AddressCompanyBean> {
    public ChooseCompanyAdapter(List<AddressCompanyBean> infos) {
        super(infos);
    }

    /**
     * 添加data
     */
    public void addItemData(AddressCompanyBean data) {
        //校验是否标签是否存在列表中，如果存在列表中则返回下标位置
        this.mInfos.add(data);
        notifyDataSetChanged();
    }


    /**
     * 添加data
     */
    public void removeItemData(int position) {
        //校验是否标签是否存在列表中，如果存在列表中则返回下标位置
        this.mInfos.remove(position);
        notifyDataSetChanged();
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

    @Override
    public BaseHolder<AddressCompanyBean> getHolder(View v, int viewType) {
        return new ChooseCompanyItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.organ_item_apply_choose_company;
    }
}
