package androidx.core.provider;

import android.graphics.Typeface;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.provider.FontRequestWorker;
import androidx.core.provider.FontsContractCompat;

/* JADX INFO: loaded from: classes.dex */
class CallbackWithHandler {

    @NonNull
    private final FontsContractCompat.FontRequestCallback mCallback;

    @NonNull
    private final Handler mCallbackHandler;

    CallbackWithHandler(@NonNull FontsContractCompat.FontRequestCallback fontRequestCallback) {
        this.mCallback = fontRequestCallback;
        this.mCallbackHandler = CalleeHandler.a();
    }

    CallbackWithHandler(@NonNull FontsContractCompat.FontRequestCallback fontRequestCallback, @NonNull Handler handler) {
        this.mCallback = fontRequestCallback;
        this.mCallbackHandler = handler;
    }

    private void onTypefaceRequestFailed(final int i) {
        final FontsContractCompat.FontRequestCallback fontRequestCallback = this.mCallback;
        this.mCallbackHandler.post(new Runnable() { // from class: androidx.core.provider.CallbackWithHandler.2
            @Override // java.lang.Runnable
            public void run() {
                fontRequestCallback.onTypefaceRequestFailed(i);
            }
        });
    }

    private void onTypefaceRetrieved(@NonNull final Typeface typeface) {
        final FontsContractCompat.FontRequestCallback fontRequestCallback = this.mCallback;
        this.mCallbackHandler.post(new Runnable() { // from class: androidx.core.provider.CallbackWithHandler.1
            @Override // java.lang.Runnable
            public void run() {
                fontRequestCallback.onTypefaceRetrieved(typeface);
            }
        });
    }

    void a(@NonNull FontRequestWorker.TypefaceResult typefaceResult) {
        if (typefaceResult.a()) {
            onTypefaceRetrieved(typefaceResult.a);
        } else {
            onTypefaceRequestFailed(typefaceResult.b);
        }
    }
}
