package androidx.appcompat.view;

import android.view.View;
import android.view.animation.Interpolator;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class ViewPropertyAnimatorCompatSet {
    ViewPropertyAnimatorListener b;
    private Interpolator mInterpolator;
    private boolean mIsStarted;
    private long mDuration = -1;
    private final ViewPropertyAnimatorListenerAdapter mProxyListener = new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.view.ViewPropertyAnimatorCompatSet.1
        private boolean mProxyStarted = false;
        private int mProxyEndCount = 0;

        void a() {
            this.mProxyEndCount = 0;
            this.mProxyStarted = false;
            ViewPropertyAnimatorCompatSet.this.a();
        }

        @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationEnd(View view) {
            int i = this.mProxyEndCount + 1;
            this.mProxyEndCount = i;
            if (i == ViewPropertyAnimatorCompatSet.this.a.size()) {
                if (ViewPropertyAnimatorCompatSet.this.b != null) {
                    ViewPropertyAnimatorCompatSet.this.b.onAnimationEnd(null);
                }
                a();
            }
        }

        @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationStart(View view) {
            if (this.mProxyStarted) {
                return;
            }
            this.mProxyStarted = true;
            if (ViewPropertyAnimatorCompatSet.this.b != null) {
                ViewPropertyAnimatorCompatSet.this.b.onAnimationStart(null);
            }
        }
    };
    final ArrayList<ViewPropertyAnimatorCompat> a = new ArrayList<>();

    void a() {
        this.mIsStarted = false;
    }

    public void cancel() {
        if (this.mIsStarted) {
            Iterator<ViewPropertyAnimatorCompat> it = this.a.iterator();
            while (it.hasNext()) {
                it.next().cancel();
            }
            this.mIsStarted = false;
        }
    }

    public ViewPropertyAnimatorCompatSet play(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
        if (!this.mIsStarted) {
            this.a.add(viewPropertyAnimatorCompat);
        }
        return this;
    }

    public ViewPropertyAnimatorCompatSet playSequentially(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2) {
        this.a.add(viewPropertyAnimatorCompat);
        viewPropertyAnimatorCompat2.setStartDelay(viewPropertyAnimatorCompat.getDuration());
        this.a.add(viewPropertyAnimatorCompat2);
        return this;
    }

    public ViewPropertyAnimatorCompatSet setDuration(long j) {
        if (!this.mIsStarted) {
            this.mDuration = j;
        }
        return this;
    }

    public ViewPropertyAnimatorCompatSet setInterpolator(Interpolator interpolator) {
        if (!this.mIsStarted) {
            this.mInterpolator = interpolator;
        }
        return this;
    }

    public ViewPropertyAnimatorCompatSet setListener(ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        if (!this.mIsStarted) {
            this.b = viewPropertyAnimatorListener;
        }
        return this;
    }

    public void start() {
        if (this.mIsStarted) {
            return;
        }
        for (ViewPropertyAnimatorCompat viewPropertyAnimatorCompat : this.a) {
            if (this.mDuration >= 0) {
                viewPropertyAnimatorCompat.setDuration(this.mDuration);
            }
            if (this.mInterpolator != null) {
                viewPropertyAnimatorCompat.setInterpolator(this.mInterpolator);
            }
            if (this.b != null) {
                viewPropertyAnimatorCompat.setListener(this.mProxyListener);
            }
            viewPropertyAnimatorCompat.start();
        }
        this.mIsStarted = true;
    }
}
