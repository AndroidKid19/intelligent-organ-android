package com.yway.scomponent.commonres.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.jess.arms.integration.AppManager;
import com.yway.scomponent.commonres.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import razerdp.basepopup.BasePopupWindow;

import static com.blankj.utilcode.util.StringUtils.getString;

/**
 * @ProjectName: Android-Project
 * @Package: com.fatalsignal.outsourcing.commonres.dialog
 * @ClassName: UpgradeDialog
 * @Description: 消息提示框
 * @Author: Yuanweiwei
 * @CreateDate: 2019/9/26 13:46
 * @UpdateUser:
 * @UpdateDate: 2019/9/26 13:46
 * @UpdateRemark:
 * @Version: 1.0
 */
public final class ToastDialog extends BasePopupWindow {

    private TextView mMessageView;
    private ImageView mIconView;

    public ToastDialog(Context context) {
        super(context);
        setBackground(0);
        bindEvent();
    }

    private void bindEvent() {

        mMessageView = findViewById(R.id.tv_toast_message);
        mIconView = findViewById(R.id.iv_toast_icon);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.public_dialog_toast);
    }


    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
    }


    public static final class Builder{
        private Type mType = Type.WARN;
        private int mDuration = 1500;
        private ToastDialog mToastDialog;
        public Builder() {
            mToastDialog = new ToastDialog(AppManager.getAppManager().getCurrentActivity() == null ? AppManager.getAppManager().getTopActivity() : AppManager.getAppManager().getCurrentActivity());
            mToastDialog.setPopupFadeEnable(false);
        }

        public Builder setType(Type type) {
            mType = type;
            switch (type) {
                case FINISH:
                    mToastDialog.mIconView.setImageResource(R.drawable.public_ic_dialog_finish);
                    break;
                case ERROR:
                    mToastDialog.mIconView.setImageResource(R.drawable.public_ic_dialog_error);
                    break;
                case WARN:
                    mToastDialog.mIconView.setImageResource(R.drawable.public_ic_dialog_warning);
                    break;
                default:
                    break;
            }
            return this;
        }

        public Builder setDuration(int duration) {
            mDuration = duration;
            return this;
        }

        public Builder setMessage(@StringRes int id) {
            return setMessage(getString(id));
        }
        public Builder setMessage(CharSequence text) {
            mToastDialog.mMessageView.setText(text);
            return this;
        }

        /**
         * 显示
         */
        public ToastDialog.Builder showPopupWindow() {
            mToastDialog.showPopupWindow();

            /**
             * 延迟一段时间执行
             */
            Observable.timer(mDuration, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong){
                           mToastDialog.dismiss();
                        }
                    });
            return this;
        }



    }
    /**
     * 显示的类型
     */
    public enum Type {
        // 完成，错误，警告
        FINISH, ERROR, WARN
    }
}
