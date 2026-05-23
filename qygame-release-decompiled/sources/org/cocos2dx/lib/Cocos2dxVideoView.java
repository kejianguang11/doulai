package org.cocos2dx.lib;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import com.igexin.push.core.b;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Cocos2dxVideoView extends SurfaceView {
    private static final String AssetResourceRoot = "@assets/";
    private static final int EVENT_CLICKED = 5;
    private static final int EVENT_COMPLETED = 3;
    private static final int EVENT_META_LOADED = 4;
    private static final int EVENT_PAUSED = 1;
    private static final int EVENT_PLAYING = 0;
    private static final int EVENT_READY_TO_PLAY = 6;
    private static final int EVENT_STOPPED = 2;
    private String TAG;
    protected Cocos2dxActivity mCocos2dxActivity;
    private MediaPlayer.OnCompletionListener mCompletionListener;
    private State mCurrentState;
    private int mDuration;
    private MediaPlayer.OnErrorListener mErrorListener;
    protected boolean mFullScreenEnabled;
    protected int mFullScreenHeight;
    protected int mFullScreenWidth;
    private boolean mIsAssetRouse;
    private boolean mKeepRatio;
    private MediaPlayer mMediaPlayer;
    private boolean mMetaUpdated;
    private OnVideoEventListener mOnVideoEventListener;
    private int mPositionBeforeRelease;
    MediaPlayer.OnPreparedListener mPreparedListener;
    SurfaceHolder.Callback mSHCallback;
    private int mSeekWhenPrepared;
    private SurfaceHolder mSurfaceHolder;
    private String mVideoFilePath;
    private int mVideoHeight;
    private Uri mVideoUri;
    private int mVideoWidth;
    protected int mViewHeight;
    protected int mViewLeft;
    private int mViewTag;
    protected int mViewTop;
    protected int mViewWidth;
    protected int mVisibleHeight;
    protected int mVisibleLeft;
    protected int mVisibleTop;
    protected int mVisibleWidth;

    public interface OnVideoEventListener {
        void onVideoEvent(int i, int i2);
    }

    private enum State {
        IDLE,
        ERROR,
        INITIALIZED,
        PREPARING,
        PREPARED,
        STARTED,
        PAUSED,
        STOPPED,
        PLAYBACK_COMPLETED
    }

    public Cocos2dxVideoView(Cocos2dxActivity cocos2dxActivity, int i) {
        super(cocos2dxActivity);
        this.TAG = "Cocos2dxVideoView";
        this.mCurrentState = State.IDLE;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        this.mSeekWhenPrepared = 0;
        this.mCocos2dxActivity = null;
        this.mViewLeft = 0;
        this.mViewTop = 0;
        this.mViewWidth = 0;
        this.mViewHeight = 0;
        this.mVisibleLeft = 0;
        this.mVisibleTop = 0;
        this.mVisibleWidth = 0;
        this.mVisibleHeight = 0;
        this.mFullScreenEnabled = false;
        this.mFullScreenWidth = 0;
        this.mFullScreenHeight = 0;
        this.mIsAssetRouse = false;
        this.mVideoFilePath = null;
        this.mViewTag = 0;
        this.mKeepRatio = false;
        this.mMetaUpdated = false;
        this.mPositionBeforeRelease = 0;
        this.mPreparedListener = new MediaPlayer.OnPreparedListener() { // from class: org.cocos2dx.lib.Cocos2dxVideoView.1
            @Override // android.media.MediaPlayer.OnPreparedListener
            public void onPrepared(MediaPlayer mediaPlayer) {
                Cocos2dxVideoView.this.mVideoWidth = mediaPlayer.getVideoWidth();
                Cocos2dxVideoView.this.mVideoHeight = mediaPlayer.getVideoHeight();
                if (Cocos2dxVideoView.this.mVideoWidth != 0 && Cocos2dxVideoView.this.mVideoHeight != 0) {
                    Cocos2dxVideoView.this.fixSize();
                }
                if (!Cocos2dxVideoView.this.mMetaUpdated) {
                    Cocos2dxVideoView.this.sendEvent(4);
                    Cocos2dxVideoView.this.sendEvent(6);
                    Cocos2dxVideoView.this.mMetaUpdated = true;
                }
                Cocos2dxVideoView.this.mCurrentState = State.PREPARED;
            }
        };
        this.mCompletionListener = new MediaPlayer.OnCompletionListener() { // from class: org.cocos2dx.lib.Cocos2dxVideoView.2
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer) {
                Cocos2dxVideoView.this.mCurrentState = State.PLAYBACK_COMPLETED;
                Cocos2dxVideoView.this.sendEvent(3);
            }
        };
        this.mErrorListener = new MediaPlayer.OnErrorListener() { // from class: org.cocos2dx.lib.Cocos2dxVideoView.3
            @Override // android.media.MediaPlayer.OnErrorListener
            public boolean onError(MediaPlayer mediaPlayer, int i2, int i3) {
                Log.d(Cocos2dxVideoView.this.TAG, "Error: " + i2 + b.an + i3);
                Cocos2dxVideoView.this.mCurrentState = State.ERROR;
                if (Cocos2dxVideoView.this.getWindowToken() == null) {
                    return true;
                }
                Resources resources = Cocos2dxVideoView.this.mCocos2dxActivity.getResources();
                new AlertDialog.Builder(Cocos2dxVideoView.this.mCocos2dxActivity).setTitle(resources.getString(resources.getIdentifier("VideoView_error_title", "string", "android"))).setMessage(resources.getIdentifier(i2 == 200 ? "VideoView_error_text_invalid_progressive_playback" : "VideoView_error_text_unknown", "string", "android")).setPositiveButton(resources.getString(resources.getIdentifier("VideoView_error_button", "string", "android")), new DialogInterface.OnClickListener() { // from class: org.cocos2dx.lib.Cocos2dxVideoView.3.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i4) {
                        Cocos2dxVideoView.this.sendEvent(3);
                    }
                }).setCancelable(false).show();
                return true;
            }
        };
        this.mSHCallback = new SurfaceHolder.Callback() { // from class: org.cocos2dx.lib.Cocos2dxVideoView.4
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Cocos2dxVideoView.this.mSurfaceHolder = surfaceHolder;
                Cocos2dxVideoView.this.openVideo();
                if (Cocos2dxVideoView.this.mPositionBeforeRelease > 0) {
                    Cocos2dxVideoView.this.mMediaPlayer.seekTo(Cocos2dxVideoView.this.mPositionBeforeRelease);
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                Cocos2dxVideoView.this.mSurfaceHolder = null;
                Cocos2dxVideoView.this.mPositionBeforeRelease = Cocos2dxVideoView.this.getCurrentPosition();
                Cocos2dxVideoView.this.release();
            }
        };
        this.mViewTag = i;
        this.mCocos2dxActivity = cocos2dxActivity;
        initVideoView();
    }

    private void initVideoView() {
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        getHolder().addCallback(this.mSHCallback);
        getHolder().setType(3);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.mCurrentState = State.IDLE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openVideo() {
        String str;
        StringBuilder sb;
        if (this.mSurfaceHolder == null) {
            return;
        }
        if (this.mIsAssetRouse) {
            if (this.mVideoFilePath == null) {
                return;
            }
        } else if (this.mVideoUri == null) {
            return;
        }
        pausePlaybackService();
        try {
            this.mMediaPlayer = new MediaPlayer();
            this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
            this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
            this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
            this.mMediaPlayer.setDisplay(this.mSurfaceHolder);
            this.mMediaPlayer.setAudioStreamType(3);
            this.mMediaPlayer.setScreenOnWhilePlaying(true);
            if (this.mIsAssetRouse) {
                AssetFileDescriptor assetFileDescriptorOpenFd = this.mCocos2dxActivity.getAssets().openFd(this.mVideoFilePath);
                this.mMediaPlayer.setDataSource(assetFileDescriptorOpenFd.getFileDescriptor(), assetFileDescriptorOpenFd.getStartOffset(), assetFileDescriptorOpenFd.getLength());
            } else {
                this.mMediaPlayer.setDataSource(this.mVideoUri.toString());
            }
            this.mCurrentState = State.INITIALIZED;
            this.mMediaPlayer.prepare();
            showFirstFrame();
        } catch (IOException e) {
            e = e;
            str = this.TAG;
            sb = new StringBuilder();
            sb.append("Unable to open content: ");
            sb.append(this.mVideoUri);
            Log.w(str, sb.toString(), e);
            this.mCurrentState = State.ERROR;
            this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
        } catch (IllegalArgumentException e2) {
            e = e2;
            str = this.TAG;
            sb = new StringBuilder();
            sb.append("Unable to open content: ");
            sb.append(this.mVideoUri);
            Log.w(str, sb.toString(), e);
            this.mCurrentState = State.ERROR;
            this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
        }
    }

    private void pausePlaybackService() {
        Intent intent = new Intent("com.android.music.musicservicecommand");
        intent.putExtra("command", "pause");
        this.mCocos2dxActivity.sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void release() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEvent(int i) {
        if (this.mOnVideoEventListener != null) {
            this.mOnVideoEventListener.onVideoEvent(this.mViewTag, i);
        }
    }

    private void setVideoURI(Uri uri, Map<String, String> map) {
        this.mVideoUri = uri;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
    }

    private void showFirstFrame() {
        this.mMediaPlayer.seekTo(1);
    }

    public void fixSize() {
        if (!this.mFullScreenEnabled) {
            fixSize(this.mViewLeft, this.mViewTop, this.mViewWidth, this.mViewHeight);
            return;
        }
        this.mFullScreenWidth = this.mCocos2dxActivity.getGLSurfaceView().getWidth();
        this.mFullScreenHeight = this.mCocos2dxActivity.getGLSurfaceView().getHeight();
        fixSize(0, 0, this.mFullScreenWidth, this.mFullScreenHeight);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void fixSize(int i, int i2, int i3, int i4) {
        if (this.mVideoWidth == 0 || this.mVideoHeight == 0) {
            this.mVisibleLeft = i;
            this.mVisibleTop = i2;
            this.mVisibleWidth = i3;
            this.mVisibleHeight = i4;
        } else if (i3 == 0 || i4 == 0) {
            this.mVisibleLeft = i;
            this.mVisibleTop = i2;
            this.mVisibleWidth = this.mVideoWidth;
            this.mVisibleHeight = this.mVideoHeight;
        } else if (this.mKeepRatio && !this.mFullScreenEnabled) {
            if (this.mVideoWidth * i4 > this.mVideoHeight * i3) {
                this.mVisibleWidth = i3;
                this.mVisibleHeight = (this.mVideoHeight * i3) / this.mVideoWidth;
            } else if (this.mVideoWidth * i4 < this.mVideoHeight * i3) {
                this.mVisibleWidth = (this.mVideoWidth * i4) / this.mVideoHeight;
                this.mVisibleHeight = i4;
            }
            this.mVisibleLeft = i + ((i3 - this.mVisibleWidth) / 2);
            this.mVisibleTop = i2 + ((i4 - this.mVisibleHeight) / 2);
        }
        getHolder().setFixedSize(this.mVisibleWidth, this.mVisibleHeight);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.leftMargin = this.mVisibleLeft;
        layoutParams.topMargin = this.mVisibleTop;
        setLayoutParams(layoutParams);
    }

    public int getCurrentPosition() {
        if (!(this.mCurrentState == State.ERROR) && !(this.mMediaPlayer == null)) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        return -1;
    }

    public int getDuration() {
        if (this.mCurrentState != State.IDLE && this.mCurrentState != State.ERROR && this.mCurrentState != State.INITIALIZED && this.mMediaPlayer != null) {
            this.mDuration = this.mMediaPlayer.getDuration();
        }
        return this.mDuration;
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(this.mVisibleWidth, this.mVisibleHeight);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if ((motionEvent.getAction() & 255) == 1) {
            sendEvent(5);
        }
        return true;
    }

    public void pause() {
        if ((this.mCurrentState == State.STARTED || this.mCurrentState == State.PLAYBACK_COMPLETED) && this.mMediaPlayer != null) {
            this.mCurrentState = State.PAUSED;
            this.mMediaPlayer.pause();
            sendEvent(1);
        }
    }

    public int resolveAdjustedSize(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        return mode != Integer.MIN_VALUE ? (mode == 0 || mode != 1073741824) ? i : size : Math.min(i, size);
    }

    public void seekTo(int i) {
        if (this.mCurrentState == State.IDLE || this.mCurrentState == State.INITIALIZED || this.mCurrentState == State.STOPPED || this.mCurrentState == State.ERROR || this.mMediaPlayer == null) {
            return;
        }
        this.mMediaPlayer.seekTo(i);
    }

    public void setFullScreenEnabled(boolean z) {
        if (this.mFullScreenEnabled != z) {
            this.mFullScreenEnabled = z;
            fixSize();
        }
    }

    public void setKeepRatio(boolean z) {
        this.mKeepRatio = z;
        fixSize();
    }

    public void setVideoFileName(String str) {
        boolean z;
        if (str.startsWith(AssetResourceRoot)) {
            str = str.substring(AssetResourceRoot.length());
        }
        if (str.startsWith("/")) {
            z = false;
        } else {
            this.mVideoFilePath = str;
            z = true;
        }
        this.mIsAssetRouse = z;
        setVideoURI(Uri.parse(str), null);
    }

    public void setVideoRect(int i, int i2, int i3, int i4) {
        if (this.mViewLeft == i && this.mViewTop == i2 && this.mViewWidth == i3 && this.mViewHeight == i4) {
            return;
        }
        this.mViewLeft = i;
        this.mViewTop = i2;
        this.mViewWidth = i3;
        this.mViewHeight = i4;
        fixSize(this.mViewLeft, this.mViewTop, this.mViewWidth, this.mViewHeight);
    }

    public void setVideoURL(String str) {
        this.mIsAssetRouse = false;
        setVideoURI(Uri.parse(str), null);
    }

    public void setVideoViewEventListener(OnVideoEventListener onVideoEventListener) {
        this.mOnVideoEventListener = onVideoEventListener;
    }

    @Override // android.view.SurfaceView, android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
    }

    public void setVolume(float f) {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setVolume(f, f);
        }
    }

    public void start() {
        if ((this.mCurrentState == State.PREPARED || this.mCurrentState == State.PAUSED || this.mCurrentState == State.PLAYBACK_COMPLETED) && this.mMediaPlayer != null) {
            this.mCurrentState = State.STARTED;
            this.mMediaPlayer.start();
            sendEvent(0);
        }
    }

    public void stop() {
        if (this.mCurrentState == State.IDLE || this.mCurrentState == State.INITIALIZED || this.mCurrentState == State.ERROR || this.mCurrentState == State.STOPPED || this.mMediaPlayer == null) {
            return;
        }
        this.mCurrentState = State.STOPPED;
        this.mMediaPlayer.stop();
        sendEvent(2);
        try {
            this.mMediaPlayer.prepare();
            showFirstFrame();
        } catch (Exception unused) {
        }
    }

    public void stopPlayback() {
        release();
    }
}
