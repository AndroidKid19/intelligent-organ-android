package com.yway.scomponent.commonsdk.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;

import com.blankj.utilcode.util.ToastUtils;

import java.io.IOException;
/**
 * @ProjectName: miemiemie-android
 * @Package: com.yway.scomponent.commonsdk.utils
 * @ClassName: MusicPlayerUtils
 * @Description: 音频播放管理工具
 * @Author: Yuan
 * @CreateDate: 2020/10/29 14:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/29 14:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MusicPlayerUtils {

    private static boolean progressFlag = true;
    /**
     * @author : yuanweiwei
     * @description : TODO 定义进度回调
     * @date : 2018/12/28 15:51
     * @return : null
     */
    public interface OnProgressListener {
        void onProgress(int progress);
    }

    private static OnProgressListener mProgressListener;

    public static void setProgressListener(OnProgressListener onProgressListener) {
        mProgressListener = onProgressListener;
    }

    public interface OnPlayMusicListener{
        void onCompletion();
    }

    private static OnPlayMusicListener mOnPlayMusicListener;

    public static void setOnPlayMusicListener(OnPlayMusicListener onPlayMusicListener) {
        mOnPlayMusicListener = onPlayMusicListener;
    }

    private static boolean isProgress = false;

    private static MediaPlayer mediaPlayer;

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static void setMediaPlayer(MediaPlayer mediaPlayer) {
        MusicPlayerUtils.mediaPlayer = mediaPlayer;
    }


    public static long endProgress = 0;
    public static Handler handler = new Handler();
    public static Runnable run = new Runnable() {
        public void run() {
            if (endProgress != 0) {
                if (MusicPlayerUtils.getMediaPlayer().isPlaying() && MusicPlayerUtils.getMediaPlayer().getCurrentPosition() >= endProgress) {
                    MusicPlayerUtils.reset();
                    handler.removeCallbacks(run);
                }
                handler.postDelayed(run, 1000);
            }

        }
    };


    /**
     * @description : TODO  播放音频没有精度条
     * @author : yuanweiwei
     * @date : 2019/1/29 14:10
     * @param context :
     * @param path :
     * @param progress :
     * @return : void
     */
    public static void playMusic(Context context, String path, final int progress) {

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (progress != 0) {
                        mediaPlayer.seekTo(progress);
                    }
                    mediaPlayer.start();
//                    mediaPlayer.setLooping(false);
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    ToastUtils.showShort("音频格式错误~");
                    return false;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stop();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @description : TODO  播放音频 播放完成回调
     * @author : yuanweiwei
     * @date : 2019/1/29 14:10
     * @param context :
     * @param path :
     * @param progress :
     * @return : void
     */
    public static void playMusicCompletion(Context context, String path, final int progress) {

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (progress != 0) {
                        mediaPlayer.seekTo(progress);
                    }
                    mediaPlayer.start();
//                    mediaPlayer.setLooping(false);
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    ToastUtils.showShort("音频格式错误~");
                    return false;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stop();
                    mOnPlayMusicListener.onCompletion();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static MediaPlayer mediaPlayerRes;

    public static void playAudio(Context context,int resId){
        if (mediaPlayerRes == null){
            mediaPlayerRes = MediaPlayer.create(context, resId);
        }
        mediaPlayerRes.start();
        mediaPlayerRes.setOnCompletionListener(mediaPlayer1 -> {
            if (mediaPlayer1.isPlaying()) {
                mediaPlayer1.stop();
            }
            mediaPlayer1.release();//释放资源
        });
    }

    /**
     * @description : TODO  播放音频待精度条回调
     * @author : yuanweiwei
     * @date : 2019/1/29 14:10
     * @param context :
     * @param path :
     * @param progress :
     * @return : void
     */
    public static void play(Context context, String path, final int progress) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (progress != 0) {
                        mediaPlayer.seekTo(progress);
                    }
                    mediaPlayer.start();
                    mediaPlayer.setLooping(false);
                    new Thread() {
                        @Override
                        public void run() {
                            while (progressFlag) {
                                mProgressListener.onProgress(MusicPlayerUtils.getMediaPlayer().getCurrentPosition());
                            }
                        }
                    }.start();
                }
            });


            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    ToastUtils.showShort("音频格式错误~");
                    return false;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stop();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reStart() {
        progressFlag = true;
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public static void pause() {
        progressFlag = false;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public static void stop() {
        progressFlag = false;
        if (handler != null) {
            handler.removeCallbacks(run);
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if(mProgressListener!=null){
            mProgressListener.onProgress(0);
            progressFlag = true;
            mProgressListener = null;
        }
    }

    public static void reset() {
        progressFlag = true;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }
    }


    public static int getDuration(String musicUrl) {
        int duration = 0;
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(musicUrl);
            mediaPlayer.prepare();
            duration = mediaPlayer.getDuration();
            if (mediaPlayer != null) {
                mediaPlayer = null;
            }
            return duration;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return duration;
    }
}
