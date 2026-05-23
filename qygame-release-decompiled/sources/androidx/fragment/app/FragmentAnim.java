package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.fragment.R;
import androidx.fragment.app.FragmentTransition;

/* JADX INFO: loaded from: classes.dex */
class FragmentAnim {

    static class AnimationOrAnimator {
        public final Animation animation;
        public final Animator animator;

        AnimationOrAnimator(Animator animator) {
            this.animation = null;
            this.animator = animator;
            if (animator == null) {
                throw new IllegalStateException("Animator cannot be null");
            }
        }

        AnimationOrAnimator(Animation animation) {
            this.animation = animation;
            this.animator = null;
            if (animation == null) {
                throw new IllegalStateException("Animation cannot be null");
            }
        }
    }

    static class EndViewTransitionAnimation extends AnimationSet implements Runnable {
        private boolean mAnimating;
        private final View mChild;
        private boolean mEnded;
        private final ViewGroup mParent;
        private boolean mTransitionEnded;

        EndViewTransitionAnimation(@NonNull Animation animation, @NonNull ViewGroup viewGroup, @NonNull View view) {
            super(false);
            this.mAnimating = true;
            this.mParent = viewGroup;
            this.mChild = view;
            addAnimation(animation);
            this.mParent.post(this);
        }

        @Override // android.view.animation.AnimationSet, android.view.animation.Animation
        public boolean getTransformation(long j, @NonNull Transformation transformation) {
            this.mAnimating = true;
            if (this.mEnded) {
                return !this.mTransitionEnded;
            }
            if (!super.getTransformation(j, transformation)) {
                this.mEnded = true;
                OneShotPreDrawListener.add(this.mParent, this);
            }
            return true;
        }

        @Override // android.view.animation.Animation
        public boolean getTransformation(long j, @NonNull Transformation transformation, float f) {
            this.mAnimating = true;
            if (this.mEnded) {
                return !this.mTransitionEnded;
            }
            if (!super.getTransformation(j, transformation, f)) {
                this.mEnded = true;
                OneShotPreDrawListener.add(this.mParent, this);
            }
            return true;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mEnded || !this.mAnimating) {
                this.mParent.endViewTransition(this.mChild);
                this.mTransitionEnded = true;
            } else {
                this.mAnimating = false;
                this.mParent.post(this);
            }
        }
    }

    private FragmentAnim() {
    }

    static AnimationOrAnimator a(@NonNull Context context, @NonNull Fragment fragment, boolean z, boolean z2) {
        int iW = fragment.w();
        int nextAnim = getNextAnim(fragment, z, z2);
        boolean z3 = false;
        fragment.a(0, 0, 0, 0);
        if (fragment.F != null && fragment.F.getTag(R.id.visible_removing_fragment_view_tag) != null) {
            fragment.F.setTag(R.id.visible_removing_fragment_view_tag, null);
        }
        if (fragment.F != null && fragment.F.getLayoutTransition() != null) {
            return null;
        }
        Animation animationOnCreateAnimation = fragment.onCreateAnimation(iW, z, nextAnim);
        if (animationOnCreateAnimation != null) {
            return new AnimationOrAnimator(animationOnCreateAnimation);
        }
        Animator animatorOnCreateAnimator = fragment.onCreateAnimator(iW, z, nextAnim);
        if (animatorOnCreateAnimator != null) {
            return new AnimationOrAnimator(animatorOnCreateAnimator);
        }
        if (nextAnim == 0 && iW != 0) {
            nextAnim = transitToAnimResourceId(iW, z);
        }
        if (nextAnim != 0) {
            boolean zEquals = "anim".equals(context.getResources().getResourceTypeName(nextAnim));
            if (zEquals) {
                try {
                    Animation animationLoadAnimation = AnimationUtils.loadAnimation(context, nextAnim);
                    if (animationLoadAnimation != null) {
                        return new AnimationOrAnimator(animationLoadAnimation);
                    }
                    z3 = true;
                } catch (Resources.NotFoundException e) {
                    throw e;
                } catch (RuntimeException unused) {
                }
            }
            if (!z3) {
                try {
                    Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(context, nextAnim);
                    if (animatorLoadAnimator != null) {
                        return new AnimationOrAnimator(animatorLoadAnimator);
                    }
                } catch (RuntimeException e2) {
                    if (zEquals) {
                        throw e2;
                    }
                    Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(context, nextAnim);
                    if (animationLoadAnimation2 != null) {
                        return new AnimationOrAnimator(animationLoadAnimation2);
                    }
                }
            }
        }
        return null;
    }

    static void a(@NonNull final Fragment fragment, @NonNull AnimationOrAnimator animationOrAnimator, @NonNull final FragmentTransition.Callback callback) {
        final View view = fragment.G;
        final ViewGroup viewGroup = fragment.F;
        viewGroup.startViewTransition(view);
        final CancellationSignal cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.FragmentAnim.1
            @Override // androidx.core.os.CancellationSignal.OnCancelListener
            public void onCancel() {
                if (fragment.B() != null) {
                    View viewB = fragment.B();
                    fragment.a((View) null);
                    viewB.clearAnimation();
                }
                fragment.a((Animator) null);
            }
        });
        callback.onStart(fragment, cancellationSignal);
        if (animationOrAnimator.animation != null) {
            EndViewTransitionAnimation endViewTransitionAnimation = new EndViewTransitionAnimation(animationOrAnimator.animation, viewGroup, view);
            fragment.a(fragment.G);
            endViewTransitionAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.fragment.app.FragmentAnim.2
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    viewGroup.post(new Runnable() { // from class: androidx.fragment.app.FragmentAnim.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (fragment.B() != null) {
                                fragment.a((View) null);
                                callback.onComplete(fragment, cancellationSignal);
                            }
                        }
                    });
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }
            });
            fragment.G.startAnimation(endViewTransitionAnimation);
            return;
        }
        Animator animator = animationOrAnimator.animator;
        fragment.a(animationOrAnimator.animator);
        animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.fragment.app.FragmentAnim.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                viewGroup.endViewTransition(view);
                Animator animatorC = fragment.C();
                fragment.a((Animator) null);
                if (animatorC == null || viewGroup.indexOfChild(view) >= 0) {
                    return;
                }
                callback.onComplete(fragment, cancellationSignal);
            }
        });
        animator.setTarget(fragment.G);
        animator.start();
    }

    private static int getNextAnim(Fragment fragment, boolean z, boolean z2) {
        return z2 ? z ? fragment.t() : fragment.u() : z ? fragment.r() : fragment.s();
    }

    @AnimRes
    private static int transitToAnimResourceId(int i, boolean z) {
        if (i == 4097) {
            return z ? R.animator.fragment_open_enter : R.animator.fragment_open_exit;
        }
        if (i == 4099) {
            return z ? R.animator.fragment_fade_enter : R.animator.fragment_fade_exit;
        }
        if (i != 8194) {
            return -1;
        }
        return z ? R.animator.fragment_close_enter : R.animator.fragment_close_exit;
    }
}
