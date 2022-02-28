package com.yway.scomponent.organ.mvp.ui.adapter;
import android.view.View;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.model.entity.RechargeRecordBean;
import com.yway.scomponent.organ.mvp.ui.holder.RechargeRecordItemHolder;
import java.util.List;

/**
 * ================================================
 * 充值记录
 * ================================================
 */
public class RechargeRecordAdapter extends DefaultAdapter<RechargeRecordBean> {
    private int optType;

    public int getOptType() {
        return optType;
    }

    public void setOptType(int optType) {
        this.optType = optType;
    }

    public RechargeRecordAdapter(List<RechargeRecordBean> infos) {
        super(infos);
    }

    /**
     * 刷新
     *
     * @return
     */
    public void refreshData(List<RechargeRecordBean> data) {
        //校验是否标签是否存在列表中，如果存在列表中则返回下标位置
        this.mInfos = data;
        notifyDataSetChanged();
    }

    @Override
    public BaseHolder<RechargeRecordBean> getHolder(View v, int viewType) {
        return new RechargeRecordItemHolder(v,optType);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.organ_item_bill_record;
    }
}
