package org.cocos2dx.lib;

import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import android.widget.FrameLayout;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/* JADX INFO: loaded from: classes.dex */
public class Cocos2dxWebViewHelper {
    private static final String TAG = "Cocos2dxWebViewHelper";
    private static Cocos2dxActivity sCocos2dxActivity;
    private static Handler sHandler;
    private static FrameLayout sLayout;
    private static int viewTag;
    private static SparseArray<Cocos2dxWebView> webViews;

    public Cocos2dxWebViewHelper(FrameLayout frameLayout) {
        sLayout = frameLayout;
        sHandler = new Handler(Looper.myLooper());
        sCocos2dxActivity = (Cocos2dxActivity) Cocos2dxActivity.getContext();
        webViews = new SparseArray<>();
    }

    public static void _didFailLoading(int i, String str) {
        didFailLoading(i, str);
    }

    public static void _didFinishLoading(int i, String str) {
        didFinishLoading(i, str);
    }

    public static void _onJsCallback(int i, String str) {
        onJsCallback(i, str);
    }

    public static boolean _shouldStartLoading(int i, String str) {
        return !shouldStartLoading(i, str);
    }

    public static <T> T callInMainThread(Callable<T> callable) throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(callable);
        sHandler.post(futureTask);
        return (T) futureTask.get();
    }

    public static boolean canGoBack(final int i) {
        try {
            return ((Boolean) callInMainThread(new Callable<Boolean>() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.13
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public Boolean call() throws Exception {
                    Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                    return Boolean.valueOf(cocos2dxWebView != null && cocos2dxWebView.canGoBack());
                }
            })).booleanValue();
        } catch (InterruptedException | ExecutionException unused) {
            return false;
        }
    }

    public static boolean canGoForward(final int i) {
        try {
            return ((Boolean) callInMainThread(new Callable<Boolean>() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.14
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public Boolean call() throws Exception {
                    Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                    return Boolean.valueOf(cocos2dxWebView != null && cocos2dxWebView.canGoForward());
                }
            })).booleanValue();
        } catch (InterruptedException | ExecutionException unused) {
            return false;
        }
    }

    public static int createWebView() {
        final int i = viewTag;
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.1
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = new Cocos2dxWebView(Cocos2dxWebViewHelper.sCocos2dxActivity, i);
                Cocos2dxWebViewHelper.sLayout.addView(cocos2dxWebView, new FrameLayout.LayoutParams(-2, -2));
                Cocos2dxWebViewHelper.webViews.put(i, cocos2dxWebView);
            }
        });
        int i2 = viewTag;
        viewTag = i2 + 1;
        return i2;
    }

    private static native void didFailLoading(int i, String str);

    private static native void didFinishLoading(int i, String str);

    public static void evaluateJS(final int i, final String str) {
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.17
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                if (cocos2dxWebView != null) {
                    cocos2dxWebView.loadUrl("javascript:" + str);
                }
            }
        });
    }

    public static void goBack(final int i) {
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.15
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                if (cocos2dxWebView != null) {
                    cocos2dxWebView.goBack();
                }
            }
        });
    }

    public static void goForward(final int i) {
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.16
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                if (cocos2dxWebView != null) {
                    cocos2dxWebView.goForward();
                }
            }
        });
    }

    public static void loadData(final int i, final String str, final String str2, final String str3, final String str4) {
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.7
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                if (cocos2dxWebView != null) {
                    cocos2dxWebView.loadDataWithBaseURL(str4, str, str2, str3, null);
                }
            }
        });
    }

    public static void loadFile(final int i, final String str) {
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.10
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                if (cocos2dxWebView != null) {
                    cocos2dxWebView.loadUrl(str);
                }
            }
        });
    }

    public static void loadHTMLString(final int i, final String str, final String str2) {
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.8
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                if (cocos2dxWebView != null) {
                    cocos2dxWebView.loadDataWithBaseURL(str2, str, null, null, null);
                }
            }
        });
    }

    public static void loadUrl(final int i, final String str) {
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.9
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                if (cocos2dxWebView != null) {
                    cocos2dxWebView.loadUrl(str);
                }
            }
        });
    }

    private static native void onJsCallback(int i, String str);

    public static void reload(final int i) {
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.12
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                if (cocos2dxWebView != null) {
                    cocos2dxWebView.reload();
                }
            }
        });
    }

    public static void removeWebView(final int i) {
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.2
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                if (cocos2dxWebView != null) {
                    Cocos2dxWebViewHelper.webViews.remove(i);
                    Cocos2dxWebViewHelper.sLayout.removeView(cocos2dxWebView);
                    cocos2dxWebView.destroy();
                }
            }
        });
    }

    public static void setBackgroundTransparent(final int i, final boolean z) {
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.5
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                if (cocos2dxWebView != null) {
                    cocos2dxWebView.setBackgroundColor(z ? 0 : -1);
                    cocos2dxWebView.setLayerType(1, null);
                }
            }
        });
    }

    public static void setJavascriptInterfaceScheme(final int i, final String str) {
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.6
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                if (cocos2dxWebView != null) {
                    cocos2dxWebView.setJavascriptInterfaceScheme(str);
                }
            }
        });
    }

    public static void setScalesPageToFit(final int i, final boolean z) {
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.18
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                if (cocos2dxWebView != null) {
                    cocos2dxWebView.setScalesPageToFit(z);
                }
            }
        });
    }

    public static void setVisible(final int i, final boolean z) {
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.3
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                if (cocos2dxWebView != null) {
                    cocos2dxWebView.setVisibility(z ? 0 : 8);
                }
            }
        });
    }

    public static void setWebViewRect(final int i, final int i2, final int i3, final int i4, final int i5) {
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.4
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                if (cocos2dxWebView != null) {
                    cocos2dxWebView.setWebViewRect(i2, i3, i4, i5);
                }
            }
        });
    }

    private static native boolean shouldStartLoading(int i, String str);

    public static void stopLoading(final int i) {
        sCocos2dxActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxWebViewHelper.11
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxWebView cocos2dxWebView = (Cocos2dxWebView) Cocos2dxWebViewHelper.webViews.get(i);
                if (cocos2dxWebView != null) {
                    cocos2dxWebView.stopLoading();
                }
            }
        });
    }
}
