
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.model.entity.CheckBoxBean;
import com.yway.scomponent.organ.mvp.ui.holder.CheckBoxItemHolder;

import java.util.List;

/**
 * ================================================
 * 设备选择适配器
 * ================================================
 */
public class CheckBoxAdapter extends DefaultAdapter<CheckBoxBean> {
    public CheckBoxAdapter(List<CheckBoxBean> infos) {
        super(infos);
    }

    /**
     * 刷新
     * @return
     */
    public void refreshData(List<CheckBoxBean> data) {
        //校验是否标签是否存在列表中，如果存在列表中则返回下标位置
        this.mInfos = data;
        notifyDataSetChanged();
    }

    public void multipleChoose(int position) {
        CheckBoxBean listItemBean = mInfos.get(position);
        if (listItemBean.isChecked()) {
            listItemBean.setChecked(false);
        } else {
            listItemBean.setChecked(true);
        }
        notifyDataSetChanged();
    }

    public String getCheckedDevice(){
        String codes = "";
        for (CheckBoxBean checkBoxBean:this.mInfos) {
            if (checkBoxBean.isChecked()){
               codes = Utils.appendStr(checkBoxBean.getSysDictCode(),",",codes);
            }
        }
        if (!StringUtils.isEmpty(codes)){
            codes = codes.substring(0,codes.length()-1);
        }
        return codes;
    }

    @Override
    public BaseHolder<CheckBoxBean> getHolder(View v, int viewType) {
        return new CheckBoxItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.organ_item_check_box;
    }
}
