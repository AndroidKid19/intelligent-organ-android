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
 * ????????????
 */
@Route(path = RouterHub.USER_FEEDBACKACTIVITY)
public class FeedbackActivity extends BaseActivity<FeedbackPresenter> implements FeedbackContract.View {
    /**
     * ????????????
     **/
    @BindView(R2.id.et_content)
    AppCompatEditText mEtContent;
    /**
     * ??????????????????
     **/
    @BindView(R2.id.tv_content_count)
    AppCompatTextView mTvContentCount;
    /**
     * ??????????????????rview
     **/
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;
    /**
     * ?????????????????????
     **/
    private GridImageAdapter mAdapter;
    /**
     * ??????????????????
     **/
    private List<LocalMedia> selectList = new ArrayList<>();
    /**
     * ????????????????????????
     **/
    private int maxSelectNum = 4;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFeedbackComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.user_activity_feedback; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();

        initRecycleView();
        // ??????????????????????????????????????????
        BroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                BroadcastAction.ACTION_DELETE_PREVIEW_POSITION);
    }

    /**
     * @param text
     * @return void
     * @method onTextChanged
     * @description ??????????????????
     * @date: 2020/12/8 11:23
     * @author: Yuan
     */
    @OnTextChanged(R2.id.et_content)
    void onTextChanged(CharSequence text) {
        //??????????????????
        if (mEtContent.getText().length() > 0) {
            mTvContentCount.setText(new StringBuilder().append(mEtContent.getText().length()).append("/").append(100 - mEtContent.getText().length()));
        } else {
            mTvContentCount.setText("100/100");
        }

    }


    /**
     * @return
     * @method
     * @description ?????????rview
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
                        // ????????????
                        PictureSelector.create(this).externalPictureVideo(media.getPath());
                        break;
                    case PictureConfig.TYPE_AUDIO:
                        // ????????????
                        PictureSelector.create(this).externalPictureAudio(media.getPath());
                        break;
                    default:
                        // ???????????? ???????????????????????????
                        PictureSelector.create(this)
                                .themeStyle(R.style.public_picture_white_style) // xml????????????
                                .isNotPreviewDownload(false)// ????????????????????????????????????
                                .imageEngine(GlideEngine.createGlideEngine())// ??????????????????????????????????????????
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
     * ?????????????????????????????????
     *
     * @param activity ?????????
     */
    public void choicePhoto(Activity activity) {
        pictureSelector();
    }


    /**
     * ????????????
     */
    private void pictureSelector() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())// ??????.PictureMimeType.ofAll()?????????.ofImage()?????????.ofVideo()?????????.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// ??????????????????????????????????????????
                .theme(R.style.public_picture_white_style)// ?????????????????? ???????????? values/styles   ?????????R.style.picture.white.style v2.3.3??? ????????????setPictureStyle()????????????
                .isWeChatStyle(false)// ????????????????????????????????????
                .isUseCustomCamera(false)// ???????????????????????????
                .isWithVideoImage(false)// ?????????????????????????????????
                .maxSelectNum(4)// ????????????????????????
                .maxVideoSelectNum(0) // ???????????????????????????????????????????????????????????????????????????????????????maxSelectNum??????
                .imageSpanCount(4)// ??????????????????
                .isReturnEmpty(false)// ????????????????????????????????????????????????
                .isOriginalImageControl(false)// ????????????????????????????????????????????????true?????????????????????????????????????????????????????????????????????????????????
                .selectionMode(PictureConfig.MULTIPLE)// ?????? or ??????
                .isSingleDirectReturn(true)// ????????????????????????????????????PictureConfig.SINGLE???????????????
                .isPreviewImage(true)// ?????????????????????
                .isCamera(true)// ????????????????????????
                .isZoomAnim(true)// ?????????????????? ???????????? ??????true
                .isEnableCrop(false)// ????????????
                .isCompress(true)// ????????????
                .compressQuality(25)// ??????????????????????????? 0~ 100
                .hideBottomControls(false)// ????????????uCrop???????????????????????????
                .isOpenClickSound(false)// ????????????????????????
                .selectionData(selectList)// ????????????????????????
                .minimumCompressSize(100)// ??????100kb??????????????????
                .forResult(onResultCallbackListener);
    }


    /**
     * ????????????????????????
     */
    private OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
        @Override
        public void onResult(List result) {
            // ????????????????????????
            selectList = result;
            // ?????? LocalMedia ??????????????????path
            // 1.media.getPath(); ?????????path
            // 2.media.getCutPath();????????????path????????????media.isCut();?????????true
            // 3.media.getCompressPath();????????????path????????????media.isCompressed();?????????true
            // 4.media.getOriginalPath()); media.isOriginal());???true?????????????????????
            // 5.media.getAndroidQToPath();???Android Q?????????????????????????????????????????????????????????????????????
            // ???????????????????????????????????????????????????????????????????????????????????????
            for (LocalMedia media : selectList) {
                Log.i(TAG, "????????????:" + media.isCompressed());
                Log.i(TAG, "??????:" + media.getCompressPath());
                Log.i(TAG, "??????:" + media.getPath());
                Log.i(TAG, "????????????:" + media.isCut());
                Log.i(TAG, "??????:" + media.getCutPath());
                Log.i(TAG, "??????????????????:" + media.isOriginal());
                Log.i(TAG, "????????????:" + media.getOriginalPath());
                Log.i(TAG, "Android Q ??????Path:" + media.getAndroidQToPath());

            }

            mAdapter.setList(selectList);
            mAdapter.notifyDataSetChanged();
            //???????????????????????????

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
                    // ??????????????????????????????
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