package com.yway.scomponent.user.mvp.ui.activity;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.KeyboardUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.base.BaseActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.utils.ArmsUtils;

import android.content.Intent;
import android.util.Log;

import com.jess.arms.utils.PermissionUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.broadcast.BroadcastAction;
import com.luck.picture.lib.broadcast.BroadcastManager;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.ScreenUtils;
import com.luck.picture.lib.tools.ToastUtils;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.user.R2;
import com.yway.scomponent.user.di.component.DaggerFeedbackComponent;
import com.yway.scomponent.user.mvp.contract.FeedbackContract;
import com.yway.scomponent.user.mvp.presenter.FeedbackPresenter;
import com.yway.scomponent.user.R;
import com.yway.scomponent.user.mvp.ui.adapter.GridImageAdapter;
import com.yway.scomponent.user.mvp.ui.manager.FullyGridLayoutManager;
import com.yway.scomponent.user.mvp.ui.manager.GlideEngine;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnTextChanged;

/**
 * 问题反馈
 */
@Route(path = RouterHub.USER_FEEDBACKACTIVITY)
public class FeedbackActivity extends BaseActivity<FeedbackPresenter> implements FeedbackContract.View {
    /**
     * 反馈内容
     **/
    @BindView(R2.id.et_content)
    AppCompatEditText mEtContent;
    /**
     * 反馈内容数量
     **/
    @BindView(R2.id.tv_content_count)
    AppCompatTextView mTvContentCount;
    /**
     * 反馈图片展示rview
     **/
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;
    /**
     * 图片展示适配器
     **/
    private GridImageAdapter mAdapter;
    /**
     * 图片选种列表
     **/
    private List<LocalMedia> selectList = new ArrayList<>();
    /**
     * 最大支持图片数量
     **/
    private int maxSelectNum = 4;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFeedbackComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.user_activity_feedback; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();

        initRecycleView();
        // 注册外部预览图片删除按钮回调
        BroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                BroadcastAction.ACTION_DELETE_PREVIEW_POSITION);
    }

    /**
     * @param text
     * @return void
     * @method onTextChanged
     * @description 监听问题说明
     * @date: 2020/12/8 11:23
     * @author: Yuan
     */
    @OnTextChanged(R2.id.et_content)
    void onTextChanged(CharSequence text) {
        //内容改变监听
        if (mEtContent.getText().length() > 0) {
            mTvContentCount.setText(new StringBuilder().append(mEtContent.getText().length()).append("/").append(100 - mEtContent.getText().length()));
        } else {
            mTvContentCount.setText("100/100");
        }

    }


    /**
     * @return
     * @method
     * @description 初始化rview
     * @date: 2020/12/4 13:57
     * @author: Yuan
     */
    private void initRecycleView() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 8), false));
        mAdapter = new GridImageAdapter(this, onAddPicClickListener);
        mAdapter.setList(selectList);
        mAdapter.setSelectMax(maxSelectNum);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((position, v) -> {
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String mimeType = media.getMimeType();
                int mediaType = PictureMimeType.getMimeType(mimeType);
                switch (mediaType) {
                    case PictureConfig.TYPE_VIDEO:
                        // 预览视频
                        PictureSelector.create(this).externalPictureVideo(media.getPath());
                        break;
                    case PictureConfig.TYPE_AUDIO:
                        // 预览音频
                        PictureSelector.create(this).externalPictureAudio(media.getPath());
                        break;
                    default:
                        // 预览图片 可自定长按保存路径
                        PictureSelector.create(this)
                                .themeStyle(R.style.public_picture_white_style) // xml设置主题
                                .isNotPreviewDownload(false)// 预览图片长按是否可以下载
                                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                .openExternalPreview(position, selectList);
                        break;
                }
            }
        });
    }


    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            KeyboardUtils.hideSoftInput(FeedbackActivity.this);
            PermissionUtil.launchCamera(mRequestPermission, mPresenter.getRxPermissions(FeedbackActivity.this), ArmsUtils.obtainAppComponentFromContext(FeedbackActivity.this).rxErrorHandler());
//            pictureSelector();
        }

    };

    private PermissionUtil.RequestPermission mRequestPermission = new PermissionUtil.RequestPermission() {
        @Override
        public void onRequestPermissionSuccess() {
            choicePhoto(FeedbackActivity.this);
        }

        @Override
        public void onRequestPermissionFailure(List<String> permissions) {
            PermissionUtil.launchCamera(mRequestPermission, mPresenter.getRxPermissions(FeedbackActivity.this), ArmsUtils.obtainAppComponentFromContext(FeedbackActivity.this).rxErrorHandler());
        }

        @Override
        public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
            ArmsUtils.snackbarText(getString(R.string.public_common_permission_fail));
        }
    };

    /**
     * 选择图片，从图库、相机
     *
     * @param activity 上下文
     */
    public void choicePhoto(Activity activity) {
        pictureSelector();
    }


    /**
     * 选择图片
     */
    private void pictureSelector() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.public_picture_white_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .isWeChatStyle(false)// 是否开启微信图片选择风格
                .isUseCustomCamera(false)// 是否使用自定义相机
                .isWithVideoImage(false)// 图片和视频是否可以同选
                .maxSelectNum(4)// 最大图片选择数量
                .maxVideoSelectNum(0) // 视频最大选择数量，如果没有单独设置的需求则可以不设置，同用maxSelectNum字段
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(false)// 是否裁剪
                .isCompress(true)// 是否压缩
                .compressQuality(25)// 图片压缩后输出质量 0~ 100
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isOpenClickSound(false)// 是否开启点击声音
                .selectionData(selectList)// 是否传入已选图片
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(onResultCallbackListener);
    }


    /**
     * 图片选择结果回调
     */
    private OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
        @Override
        public void onResult(List result) {
            // 图片选择结果回调
            selectList = result;
            // 例如 LocalMedia 里面返回五种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
            // 5.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
            // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
            for (LocalMedia media : selectList) {
                Log.i(TAG, "是否压缩:" + media.isCompressed());
                Log.i(TAG, "压缩:" + media.getCompressPath());
                Log.i(TAG, "原图:" + media.getPath());
                Log.i(TAG, "是否裁剪:" + media.isCut());
                Log.i(TAG, "裁剪:" + media.getCutPath());
                Log.i(TAG, "是否开启原图:" + media.isOriginal());
                Log.i(TAG, "原图路径:" + media.getOriginalPath());
                Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());

            }

            mAdapter.setList(selectList);
            mAdapter.notifyDataSetChanged();
            //初始化图片上传状态

        }

        @Override
        public void onCancel() {

        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle extras;
            switch (action) {
                case BroadcastAction.ACTION_DELETE_PREVIEW_POSITION:
                    // 外部预览删除按钮回调
                    extras = intent.getExtras();
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    ToastUtils.s(context, "delete image index:" + position);
                    if (position < mAdapter.getItemCount()) {
                        selectList.remove(position);
                        mAdapter.notifyItemRemoved(position);
                    }
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            BroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver,
                    BroadcastAction.ACTION_DELETE_PREVIEW_POSITION);
        }
    }

    @Override
    public void showLoading() {
        ProgresDialog.getInstance(this).show();
    }

    @Override
    public void hideLoading() {
        ProgresDialog.getInstance(this).dismissDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    public Activity getActivity() {
        return this;
    }
}