package org.cocos2dx.lib;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.alipay.sdk.util.h;
import com.igexin.push.core.b;

/* JADX INFO: loaded from: classes.dex */
public class Cocos2dxGLSurfaceView extends GLSurfaceView {
    private static final int HANDLER_CLOSE_IME_KEYBOARD = 3;
    private static final int HANDLER_OPEN_IME_KEYBOARD = 2;
    private static final String TAG = "Cocos2dxGLSurfaceView";
    private static Cocos2dxGLSurfaceView mCocos2dxGLSurfaceView;
    private static Handler sHandler;
    private Cocos2dxRenderer mCocos2dxRenderer;
    private boolean mStopHandleTouchAndKeyEvents;

    public Cocos2dxGLSurfaceView(Context context) {
        super(context);
        this.mStopHandleTouchAndKeyEvents = false;
        initView();
    }

    public Cocos2dxGLSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStopHandleTouchAndKeyEvents = false;
        initView();
    }

    private static void dumpMotionEvent(MotionEvent motionEvent) {
        StringBuilder sb = new StringBuilder();
        int action = motionEvent.getAction();
        int i = action & 255;
        sb.append("event ACTION_");
        sb.append(new String[]{"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"}[i]);
        if (i == 5 || i == 6) {
            sb.append("(pid ");
            sb.append(action >> 8);
            sb.append(")");
        }
        sb.append("[");
        int i2 = 0;
        while (i2 < motionEvent.getPointerCount()) {
            sb.append("#");
            sb.append(i2);
            sb.append("(pid ");
            sb.append(motionEvent.getPointerId(i2));
            sb.append(")=");
            sb.append((int) motionEvent.getX(i2));
            sb.append(b.an);
            sb.append((int) motionEvent.getY(i2));
            i2++;
            if (i2 < motionEvent.getPointerCount()) {
                sb.append(h.b);
            }
        }
        sb.append("]");
        Log.d(TAG, sb.toString());
    }

    private String getContentText() {
        return this.mCocos2dxRenderer.getContentText();
    }

    public static Cocos2dxGLSurfaceView getInstance() {
        return mCocos2dxGLSurfaceView;
    }

    public static native void nativeOnSizeChanged(int i, int i2);

    public static void queueAccelerometer(final float f, final float f2, final float f3, final long j) {
        mCocos2dxGLSurfaceView.queueEvent(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxGLSurfaceView.1
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxAccelerometer.onSensorChanged(f, f2, f3, j);
            }
        });
    }

    protected void initView() {
        setEGLContextClientVersion(2);
        setFocusableInTouchMode(true);
        mCocos2dxGLSurfaceView = this;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(final int i, KeyEvent keyEvent) {
        if (i == 4) {
            Cocos2dxVideoHelper.mVideoHandler.sendEmptyMessage(1000);
        } else if (i != 66 && i != 82 && i != 85) {
            switch (i) {
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    break;
                default:
                    return super.onKeyDown(i, keyEvent);
            }
        }
        queueEvent(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxGLSurfaceView.10
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxGLSurfaceView.this.mCocos2dxRenderer.handleKeyDown(i);
            }
        });
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(final int i, KeyEvent keyEvent) {
        if (i != 4 && i != 66 && i != 82 && i != 85) {
            switch (i) {
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    break;
                default:
                    return super.onKeyUp(i, keyEvent);
            }
        }
        queueEvent(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxGLSurfaceView.11
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxGLSurfaceView.this.mCocos2dxRenderer.handleKeyUp(i);
            }
        });
        return true;
    }

    @Override // android.opengl.GLSurfaceView
    public void onPause() {
        queueEvent(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxGLSurfaceView.3
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxGLSurfaceView.this.mCocos2dxRenderer.handleOnPause();
            }
        });
        setRenderMode(0);
        this.mCocos2dxRenderer.setPauseInMainThread(true);
    }

    @Override // android.opengl.GLSurfaceView
    public void onResume() {
        super.onResume();
        setRenderMode(1);
        queueEvent(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxGLSurfaceView.2
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxGLSurfaceView.this.mCocos2dxRenderer.handleOnResume();
            }
        });
        this.mCocos2dxRenderer.setPauseInMainThread(false);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        if (isInEditMode()) {
            return;
        }
        this.mCocos2dxRenderer.setScreenWidthAndHeight(i, i2);
        nativeOnSizeChanged(i, i2);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Runnable runnable;
        Runnable runnable2;
        Runnable runnable3;
        int pointerCount = motionEvent.getPointerCount();
        final int[] iArr = new int[pointerCount];
        final float[] fArr = new float[pointerCount];
        final float[] fArr2 = new float[pointerCount];
        for (int i = 0; i < pointerCount; i++) {
            iArr[i] = motionEvent.getPointerId(i);
            fArr[i] = motionEvent.getX(i);
            fArr2[i] = motionEvent.getY(i);
        }
        switch (motionEvent.getAction() & 255) {
            case 0:
                if (this.mStopHandleTouchAndKeyEvents) {
                    Cocos2dxEditBox.complete();
                } else {
                    final int pointerId = motionEvent.getPointerId(0);
                    final float f = fArr[0];
                    final float f2 = fArr2[0];
                    runnable = new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxGLSurfaceView.5
                        @Override // java.lang.Runnable
                        public void run() {
                            Cocos2dxGLSurfaceView.this.mCocos2dxRenderer.handleActionDown(pointerId, f, f2);
                        }
                    };
                    queueEvent(runnable);
                }
                break;
            case 1:
                final int pointerId2 = motionEvent.getPointerId(0);
                final float f3 = fArr[0];
                final float f4 = fArr2[0];
                runnable = new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxGLSurfaceView.8
                    @Override // java.lang.Runnable
                    public void run() {
                        Cocos2dxGLSurfaceView.this.mCocos2dxRenderer.handleActionUp(pointerId2, f3, f4);
                    }
                };
                queueEvent(runnable);
                break;
            case 2:
                runnable2 = new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxGLSurfaceView.6
                    @Override // java.lang.Runnable
                    public void run() {
                        Cocos2dxGLSurfaceView.this.mCocos2dxRenderer.handleActionMove(iArr, fArr, fArr2);
                    }
                };
                queueEvent(runnable2);
                break;
            case 3:
                runnable2 = new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxGLSurfaceView.9
                    @Override // java.lang.Runnable
                    public void run() {
                        Cocos2dxGLSurfaceView.this.mCocos2dxRenderer.handleActionCancel(iArr, fArr, fArr2);
                    }
                };
                queueEvent(runnable2);
                break;
            case 5:
                if (this.mStopHandleTouchAndKeyEvents) {
                    Cocos2dxEditBox.complete();
                } else {
                    int action = motionEvent.getAction() >> 8;
                    final int pointerId3 = motionEvent.getPointerId(action);
                    final float x = motionEvent.getX(action);
                    final float y = motionEvent.getY(action);
                    runnable3 = new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxGLSurfaceView.4
                        @Override // java.lang.Runnable
                        public void run() {
                            Cocos2dxGLSurfaceView.this.mCocos2dxRenderer.handleActionDown(pointerId3, x, y);
                        }
                    };
                    queueEvent(runnable3);
                }
                break;
            case 6:
                int action2 = motionEvent.getAction() >> 8;
                final int pointerId4 = motionEvent.getPointerId(action2);
                final float x2 = motionEvent.getX(action2);
                final float y2 = motionEvent.getY(action2);
                runnable3 = new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxGLSurfaceView.7
                    @Override // java.lang.Runnable
                    public void run() {
                        Cocos2dxGLSurfaceView.this.mCocos2dxRenderer.handleActionUp(pointerId4, x2, y2);
                    }
                };
                queueEvent(runnable3);
                break;
        }
        return true;
    }

    public void setCocos2dxRenderer(Cocos2dxRenderer cocos2dxRenderer) {
        this.mCocos2dxRenderer = cocos2dxRenderer;
        setRenderer(this.mCocos2dxRenderer);
    }

    public void setStopHandleTouchAndKeyEvents(boolean z) {
        this.mStopHandleTouchAndKeyEvents = z;
    }
}
