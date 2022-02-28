package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.commonsdk.utils.ArithUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.mvp.model.entity.RechargeRecordBean;

import butterknife.BindView;

/**
 * ================================================
 * 充值记录
 * ================================================
 */
public class RechargeRecordItemHolder extends BaseHolder<RechargeRecordBean> {

    private int optType;
    @BindView(R2.id.tv_time_name)
    AppCompatTextView mTvTime;

    @BindView(R2.id.tv_money)
    AppCompatTextView mTvMoney;

    @BindView(R2.id.tv_type)
    AppCompatTextView mTvType;

    public RechargeRecordItemHolder(View itemView,int optType) {
        super(itemView);
        this.optType = optType;
    }

    @Override
    public void setData(RechargeRecordBean data, int position) {
        if (data.getTransactionType() == 0){
            //消费记录
            mTvType.setText("消费");
            mTvMoney.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.public_color_text_sign));
            mTvMoney.setText(Utils.appendStr("-",ArithUtils.div(Double.parseDouble(data.getTransactionAmount()),10000)));
        }else{
            //充值记录
            mTvType.setText("充值");
            mTvMoney.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.public_color_text_success));
            mTvMoney.setText(Utils.appendStr("+",ArithUtils.div(Double.parseDouble(data.getTransactionAmount()),10000)));
        }
        mTvTime.setText(data.getCreateTime());
    }

    @Override
    protected void onRelease() {
    }
}
