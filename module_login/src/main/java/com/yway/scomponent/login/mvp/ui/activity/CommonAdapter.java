
package com.yway.scomponent.login.mvp.ui.activity;

import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.login.R;
import com.yway.scomponent.login.mvp.model.entity.TagInfo;

import java.util.List;

/**
 * ================================================
 *
 * ================================================
 */
public class CommonAdapter extends DefaultAdapter<TagInfo> {
    public CommonAdapter(List<TagInfo> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<TagInfo> getHolder(View v, int viewType) {
        return new CommonItemHolder(v);
    }

    /**
     * 刷新标签列表
     * @param tagInfo 标签信息
     * @return
     */
    public void refreshTagList(TagInfo tagInfo){
        //校验是否标签是否存在列表中，如果存在列表中则返回下标位置
        int index = checkIsExist(tagInfo.getTagNumber());
        if (index >= 0){
            //列表存在当前标签
            TagInfo oldTagInfo = this.mInfos.get(index);
            this.mInfos.get(index).setTagScanCount(oldTagInfo.getTagScanCount()+tagInfo.getTagScanCount());
            notifyItemChanged(index);
        }else{
            this.mInfos.add(tagInfo);
            notifyDataSetChanged();
        }
    }

    /**
     * 判断EPC是否在列表中
     * @param strEPC 索引
     * @return
     */
    public int checkIsExist(String strEPC) {
        int existFlag = -1;
        if (StringUtils.isEmpty(strEPC)) {
            return existFlag;
        }
        for (int i = 0; i < this.mInfos.size(); i++) {
            if (this.mInfos.get(i).getTagNumber().equals(strEPC)){
                //列表已存在
                existFlag =  i;
                break;
            }
        }
        return existFlag;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.login_item_common;
    }
}
