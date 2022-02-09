package com.yway.scomponent.commonres.dialog;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yway.scomponent.commonres.R;
import com.yway.scomponent.commonres.view.layout.NumberProgressBar;

import java.io.File;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @ProjectName: Android-Project
 * @Package: com.fatalsignal.outsourcing.commonres.dialog
 * @ClassName: UpgradeDialog
 * @Description: 升级提示框
 * @Author: Yuanweiwei
 * @CreateDate: 2019/9/26 13:46
 * @UpdateUser:
 * @UpdateDate: 2019/9/26 13:46
 * @UpdateRemark:
 * @Version: 1.0
 */
public final class UpgradeDialog extends BasePopupWindow {

    private TextView mNameView;
    private TextView mSizeView;
    private TextView mContentView;
    private NumberProgressBar mProgressView;

    private TextView mUpdateView;
    private TextView mTvInSetting;
    private ViewGroup mCancelLayout;
    private View mCloseView;

    public UpgradeDialog(Context context) {
        super(context);
        bindEvent();
    }

    private void bindEvent() {
        mNameView = findViewById(R.id.tv_update_name);
        mSizeView = findViewById(R.id.tv_update_size);
        mContentView = findViewById(R.id.tv_update_content);
        mProgressView = findViewById(R.id.pb_update_progress);
        mTvInSetting = findViewById(R.id.tv_in_setting);
        mUpdateView = findViewById(R.id.tv_update_update);
        mCancelLayout = findViewById(R.id.ll_update_cancel);
        mCloseView = findViewById(R.id.iv_update_close);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.public_dialog_update);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultScaleAnimation(false);
    }

    @Override
    public void showPopupWindow() {
        setShowAnimation(getDefaultScaleAnimation());
        setDismissAnimation(getDefaultScaleAnimation(false));
        setOutSideTouchable(false);
        setOutSideDismiss(false);
        super.showPopupWindow();
    }

    @Override
    public void showPopupWindow(View anchorView) {
        initAnimate();
        super.showPopupWindow(anchorView);
    }

    private void initAnimate() {
        int gravity = getPopupGravity();
        float in_fromX = 0;
        float in_toX = 0;
        float in_fromY = 0;
        float in_toY = 0;
        float exit_fromX = 0;
        float exit_toX = 0;
        float exit_fromY = 0;
        float exit_toY = 0;
        switch (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.LEFT:
            case Gravity.START:
                in_fromX = 1f;
                exit_toX = 1f;
                break;
            case Gravity.RIGHT:
            case Gravity.END:
                in_fromX = -1f;
                exit_toX = -1f;
                break;
            case Gravity.CENTER_HORIZONTAL:
                break;
            default:
                break;
        }
        switch (gravity & Gravity.VERTICAL_GRAVITY_MASK) {
            case Gravity.TOP:
                in_fromY = 1f;
                exit_toY = 1f;
                break;
            case Gravity.BOTTOM:
                in_fromY = -1f;
                exit_toY = -1f;
                break;
            case Gravity.CENTER_VERTICAL:
                break;
            default:
                break;
        }
        setShowAnimation(createTranslateAnimate(in_fromX, in_toX, in_fromY, in_toY));
        setDismissAnimation(createTranslateAnimate(exit_fromX, exit_toX, exit_fromY, exit_toY));
    }
    public Animation createTranslateAnimate(float fromX, float toX, float fromY, float toY) {
        Animation result = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                fromX,
                Animation.RELATIVE_TO_PARENT,
                toX,
                Animation.RELATIVE_TO_PARENT,
                fromY,
                Animation.RELATIVE_TO_PARENT,
                toY);
        result.setDuration(500);
        return result;
    }


    public static final class Builder implements OnDownloadListener,View.OnClickListener {
        /** 下载地址 */
        private String mDownloadUrl;

        /** 当前下载状态 */
        private int mDownloadStatus = -1;

        /** 下载处理对象 */
        private DownloadHandler mDownloadHandler;

        private UpgradeDialog mUpgradeDialog;

        private RxPermissions mRxPermissions;
        /**是否取得安装权限*/
        private boolean isInstallPackagesPermission = false;

        public Builder(Context context, RxPermissions mRxPermissions) {
            this.mRxPermissions = mRxPermissions;
            mUpgradeDialog = new UpgradeDialog(context);
            mUpgradeDialog.setPopupFadeEnable(false);
            mUpgradeDialog.mUpdateView.setOnClickListener(this);
            mUpgradeDialog.mCloseView.setOnClickListener(this);
            mUpgradeDialog.mTvInSetting.setOnClickListener(this);
        }

        /**
         * 设置版本名
         */
        public Builder setVersionName(CharSequence name) {
            mUpgradeDialog.mNameView.setText(name);
            return this;
        }

        /**
         * 设置文件大小
         */
        public Builder setFileSize(long size) {
            return setFileSize(Formatter.formatFileSize(mUpgradeDialog.getContext(), size));
        }

        public Builder setFileSize(CharSequence text) {
            mUpgradeDialog.mSizeView.setText(text);
            return this;
        }

        /**
         * 设置更新日志
         */
        public Builder setUpdateLog(CharSequence text) {
            mUpgradeDialog.mContentView.setText(text);
            mUpgradeDialog.mContentView.setVisibility(text == null ? View.GONE : View.VISIBLE);
            return this;
        }

        /**
         * 设置强制更新
         */
        public Builder setForceUpdate(boolean force) {
            mUpgradeDialog.mCancelLayout.setVisibility(force ? View.GONE : View.VISIBLE);
            if (force) {
                mUpgradeDialog.setOutSideDismiss(false);
                mUpgradeDialog.setOutSideTouchable(false);
                mUpgradeDialog.setBackPressEnable(false);
            }
            return this;
        }

        /**
         * 设置下载 url
         */
        public Builder setDownloadUrl(String url) {
            mDownloadUrl = url;
            return this;
        }

        /**
         * 显示
         */
        public Builder showPopupWindow() {
            mUpgradeDialog.showPopupWindow();
            return this;
        }

        public boolean isShowing(){
           return mUpgradeDialog.isShowing();
        }

        /**
         * {@link OnDownloadListener}
         */
        @Override
        public void downloadProgressChange(int progress) {
            mUpgradeDialog.mProgressView.setProgress(progress);
        }

        @Override
        public void downloadStateChange(int state) {
            // 记录本次的下载状态
            mDownloadStatus = state;
            // 判断下载状态
            switch (state) {
                // 下载中
                case DownloadManager.STATUS_RUNNING:
                    mUpgradeDialog.mUpdateView.setText(R.string.public_update_status_running);
                    // 显示进度条
                    mUpgradeDialog.mProgressView.setVisibility(View.VISIBLE);
                    break;
                // 下载成功
                case DownloadManager.STATUS_SUCCESSFUL:
                    mUpgradeDialog.mUpdateView.setText(R.string.public_update_status_successful);
                    // 隐藏进度条
                    mUpgradeDialog.mProgressView.setVisibility(View.GONE);
                    // 安装 Apk
                    mDownloadHandler.openDownloadFile();
                    break;
                // 下载失败
                case DownloadManager.STATUS_FAILED:
                    mUpgradeDialog.mUpdateView.setText(R.string.public_update_status_failed);
                    // 删除下载的文件
                    mDownloadHandler.deleteDownloadFile();
                    break;
                // 下载暂停
                case DownloadManager.STATUS_PAUSED:
                    mUpgradeDialog.mUpdateView.setText(R.string.public_update_status_paused);
                    break;
                // 等待下载
                case DownloadManager.STATUS_PENDING:
                    mUpgradeDialog.mUpdateView.setText(R.string.public_update_status_pending);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            if (v == mUpgradeDialog.mCloseView) {
                mUpgradeDialog.dismiss();
            } else if (v == mUpgradeDialog.mUpdateView) {
                // 判断下载状态
                switch (mDownloadStatus) {
                    // 没有任何状态
                    case -1:
                        // 下载失败
                    case DownloadManager.STATUS_FAILED:
                        // 重新下载
                        requestPermission();
                        break;
                    // 下载成功
                    case DownloadManager.STATUS_SUCCESSFUL:
                        // 安装 Apk
                        mDownloadHandler.openDownloadFile();
                        break;
                    default:
                        break;
                }
            }else if(v == mUpgradeDialog.mTvInSetting){
                //进入设置
                //8.0以上要手动授权 兼容8.0
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    startInstallPermissionSettingActivity();
//                }
//                ArmsUtils.goToBrowser(BuildConfig.CLONEWINNER_PORTAL_HOST_ROOT);
//                ArmsUtils.copy(mUpgradeDialog.getContext(),BuildConfig.CLONEWINNER_PORTAL_HOST_ROOT);
            }
        }

        /**
         * 请求权限
         */
        private void requestPermission() {
            PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
                @Override
                public void onRequestPermissionSuccess() {
                    mDownloadHandler = new DownloadHandler(mUpgradeDialog.getContext());
                    mDownloadHandler.setDownloadListener(Builder.this);
                    if (!mDownloadHandler.createDownload(mDownloadUrl,  mUpgradeDialog.getContext().getString(R.string.public_app_name)+
                            " " + mUpgradeDialog.mNameView.getText().toString() + ".apk", null)) {
                        mUpgradeDialog.mUpdateView.setText(R.string.public_update_download_fail);
                    } else {
                        // 设置对话框不能被取消
                        mUpgradeDialog.setOutSideTouchable(false);
                        // 隐藏取消按钮
                        mUpgradeDialog.mCancelLayout.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onRequestPermissionFailure(List<String> permissions) {
                    ArmsUtils.snackbarText(ArmsUtils.getString(mUpgradeDialog.getContext(),R.string.public_update_permission_hint));
                }

                @Override
                public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                    ArmsUtils.snackbarText(ArmsUtils.getString(mUpgradeDialog.getContext(),R.string.public_update_permission_hint));
                }
            },mRxPermissions, ArmsUtils.obtainAppComponentFromContext(mUpgradeDialog.getContext()).rxErrorHandler());

        }


        private void installPackages(){
//            installPackages

            PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
                @Override
                public void onRequestPermissionSuccess() {
                    isInstallPackagesPermission = true;
                }

                @Override
                public void onRequestPermissionFailure(List<String> permissions) {
                    isInstallPackagesPermission = false;
                }

                @Override
                public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                    isInstallPackagesPermission = false;
                }
            },mRxPermissions, ArmsUtils.obtainAppComponentFromContext(mUpgradeDialog.getContext()).rxErrorHandler());
        }

        /**
         * 跳转到设置-允许安装未知来源-页面
         */
        @RequiresApi(api = Build.VERSION_CODES.O)
        private void startInstallPermissionSettingActivity() {
            //注意这个是8.0新API
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mUpgradeDialog.getContext().startActivity(intent);
        }
    }



    private static final class DownloadHandler extends Handler {

        private final Context mContext;

        /** 下载管理器对象 */
        private final DownloadManager mDownloadManager;
        /** 下载内容观察者 */
        private DownloadObserver mDownloadObserver;

        /** 下载文件 id */
        private long mDownloadId;

        /** 下载监听 */
        private OnDownloadListener mListener;

        /** 下载的文件 */
        private File mDownloadFile;

        private DownloadHandler(Context context) {
            super(Looper.getMainLooper());
            mContext = context;
            mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        }

        private void setDownloadListener(OnDownloadListener listener) {
            mListener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            if (mListener == null) {
                return;
            }

            // 判断下载状态
            switch (msg.what) {
                // 下载中
                case DownloadManager.STATUS_RUNNING:
                    // 计算下载百分比，这里踩了两个坑
                    // 当 apk 文件很大的时候：下载字节数 * 100 会超过 int 最大值，计算结果会变成负数
                    // 还有需要注意的是，int 除以 int 等于 int，这里的下载字节数除以总字节数应该要 double 类型的
                    int progress = (int) (((double) msg.arg2 / msg.arg1) * 100);
                    mListener.downloadProgressChange(progress);
                    break;
                // 下载成功
                case DownloadManager.STATUS_SUCCESSFUL:
                    // 下载失败
                case DownloadManager.STATUS_FAILED:
                    // 移除内容观察者
                    if (mDownloadObserver != null) {
                        mContext.getContentResolver().unregisterContentObserver(mDownloadObserver);
                    }
                    break;
                default:
                    break;
            }

            mListener.downloadStateChange(msg.what);
        }

        /**
         * 创建下载任务
         *
         * @param downloadUrl           下载地址
         * @param fileName              文件命名
         * @param notificationTitle     通知栏标题
         * @return                      下载 id
         */
        @SuppressWarnings("ResultOfMethodCallIgnored")
        private boolean createDownload(String downloadUrl, String fileName, String notificationTitle) {
            if (fileName == null) {
                throw new IllegalArgumentException("The filename cannot be empty");
            }

            // 记录下载的文件

            mDownloadFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
            // 如果这个文件已经下载过，就先删除这个文件
            if (mDownloadFile.exists()) {
                mDownloadFile.delete();
            }

            try {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
                request.allowScanningByMediaScanner();
                //设置WIFI下进行更新
                //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

                if (notificationTitle != null) {
                    request.setTitle(notificationTitle);
                }

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                mDownloadId = mDownloadManager.enqueue(request);

                mDownloadObserver = new DownloadObserver(this, mDownloadManager, new DownloadManager.Query().setFilterById(mDownloadId));
                // 添加内容观察者
                mContext.getContentResolver().registerContentObserver(Uri.parse("content://downloads/"), true, mDownloadObserver);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        /**
         * 打开下载的文件
         */
        private void openDownloadFile() {
            // 这里需要特别说明的是，这个 API 其实不是打开文件的，我也不知道干什么用的
            // 测试前必须要加权限，否则会崩溃：<uses-permission android:name="android.permission.ACCESS_ALL_DOWNLOADS" />
            // mDownloadManager.openDownloadedFile(mDownloadId);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
                //参数二:fileprovider绝对路径 com.fatalsignal.outsourcing.wheel：项目包名
                uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", mDownloadFile);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(mDownloadFile);
            }

//            try {
//                Runtime.getRuntime().exec("chmod 777 " + mDownloadFile.getCanonicalPath());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("return-data", false);
            mContext.startActivity(intent);
        }

        /**
         * 删除下载的文件
         */
        void deleteDownloadFile() {
            mDownloadManager.remove(mDownloadId);
        }
    }


    private static class DownloadObserver extends ContentObserver {

        private final Handler mHandler;
        private final DownloadManager mDownloadManager;
        private final DownloadManager.Query mQuery;

        DownloadObserver(Handler handler, DownloadManager manager, DownloadManager.Query query) {
            super(handler);
            mHandler = handler;
            mDownloadManager = manager;
            mQuery = query;
        }

        /**
         * 每当 /data/data/com.android.providers.download/database/database.db 变化后就会触发onChange方法
         *
         * @param selfChange        是否是当前应用自己操作了数据库
         */
        @Override
        public void onChange(boolean selfChange) {
            // 查询数据库
            Cursor cursor = mDownloadManager.query(mQuery);
            // 游标定位到第一个，因为 Cursor 总数只有一个
            cursor.moveToFirst();

            // 总需下载的字节数
            int totalBytes = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            // 已经下载的字节数
            int downloadBytes = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            // 下载状态
            int downloadStatus = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));

            // 关闭游标
            cursor.close();

            // 发送更新消息
            Message msg = mHandler.obtainMessage();
            msg.arg1 = totalBytes;
            msg.arg2 = downloadBytes;
            msg.what = downloadStatus;
            mHandler.sendMessage(msg);
        }
    }

    private interface OnDownloadListener {
        /**
         * 下载进度改变
         */
        void downloadProgressChange(int progress);

        /**
         * 下载状态改变
         */
        void downloadStateChange(int state);
    }

}
