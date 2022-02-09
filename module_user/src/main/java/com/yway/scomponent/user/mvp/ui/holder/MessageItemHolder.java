package com.yway.scomponent.user.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yway.scomponent.user.R2;
import com.yway.scomponent.user.mvp.model.entity.MessageBean;

import butterknife.BindView;

/**
 * ================================================
 * 系统消息
 * ================================================
 */
public class MessageItemHolder extends BaseHolder<MessageBean> {

    private AppComponent mAppComponent;

    @BindView(R2.id.tv_date)
    AppCompatTextView mTvDate;
    @BindView(R2.id.tv_content)
    AppCompatTextView mTvContent;

    public MessageItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到 Context 的地方, 拿到 AppComponent, 从而得到用 Dagger 管理的单例对象
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
    }

    @Override
    public void setData(MessageBean data, int position) {
        //itemView 的 Context 就是 Activity, Glide 会自动处理并和该 Activity 的生命周期绑定
        mTvDate.setText(data.getCreate_time());
        mTvContent.setText(data.getTitle());
        itemView.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
        //只要传入的 Context 为 Activity, Glide 就会自己做好生命周期的管理, 其实在上面的代码中传入的 Context 就是 Activity
        //所以在 onRelease 方法中不做 clear 也是可以的, 但是在这里想展示一下 clear 的用法

    }
}
