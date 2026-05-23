package androidx.activity.result;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityResultRegistry {
    private static final int INITIAL_REQUEST_CODE_VALUE = 65536;
    private static final String KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS = "KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS";
    private static final String KEY_COMPONENT_ACTIVITY_PENDING_RESULTS = "KEY_COMPONENT_ACTIVITY_PENDING_RESULT";
    private static final String KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT = "KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT";
    private static final String KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS = "KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS";
    private static final String KEY_COMPONENT_ACTIVITY_REGISTERED_RCS = "KEY_COMPONENT_ACTIVITY_REGISTERED_RCS";
    private static final String LOG_TAG = "ActivityResultRegistry";
    private Random mRandom = new Random();
    private final Map<Integer, String> mRcToKey = new HashMap();
    final Map<String, Integer> b = new HashMap();
    private final Map<String, LifecycleContainer> mKeyToLifecycleContainers = new HashMap();
    ArrayList<String> c = new ArrayList<>();
    final transient Map<String, CallbackAndContract<?>> d = new HashMap();
    final Map<String, Object> e = new HashMap();
    final Bundle f = new Bundle();

    private static class CallbackAndContract<O> {
        final ActivityResultCallback<O> a;
        final ActivityResultContract<?, O> b;

        CallbackAndContract(ActivityResultCallback<O> activityResultCallback, ActivityResultContract<?, O> activityResultContract) {
            this.a = activityResultCallback;
            this.b = activityResultContract;
        }
    }

    private static class LifecycleContainer {
        final Lifecycle a;
        private final ArrayList<LifecycleEventObserver> mObservers = new ArrayList<>();

        LifecycleContainer(@NonNull Lifecycle lifecycle) {
            this.a = lifecycle;
        }

        void a() {
            Iterator<LifecycleEventObserver> it = this.mObservers.iterator();
            while (it.hasNext()) {
                this.a.removeObserver(it.next());
            }
            this.mObservers.clear();
        }

        void a(@NonNull LifecycleEventObserver lifecycleEventObserver) {
            this.a.addObserver(lifecycleEventObserver);
            this.mObservers.add(lifecycleEventObserver);
        }
    }

    private void bindRcKey(int i, String str) {
        this.mRcToKey.put(Integer.valueOf(i), str);
        this.b.put(str, Integer.valueOf(i));
    }

    private <O> void doDispatch(String str, int i, @Nullable Intent intent, @Nullable CallbackAndContract<O> callbackAndContract) {
        if (callbackAndContract != null && callbackAndContract.a != null) {
            callbackAndContract.a.onActivityResult(callbackAndContract.b.parseResult(i, intent));
        } else {
            this.e.remove(str);
            this.f.putParcelable(str, new ActivityResult(i, intent));
        }
    }

    private int generateRandomNumber() {
        int iNextInt = this.mRandom.nextInt(2147418112);
        while (true) {
            int i = iNextInt + 65536;
            if (!this.mRcToKey.containsKey(Integer.valueOf(i))) {
                return i;
            }
            iNextInt = this.mRandom.nextInt(2147418112);
        }
    }

    private int registerKey(String str) {
        Integer num = this.b.get(str);
        if (num != null) {
            return num.intValue();
        }
        int iGenerateRandomNumber = generateRandomNumber();
        bindRcKey(iGenerateRandomNumber, str);
        return iGenerateRandomNumber;
    }

    @MainThread
    final void a(@NonNull String str) {
        Integer numRemove;
        if (!this.c.contains(str) && (numRemove = this.b.remove(str)) != null) {
            this.mRcToKey.remove(numRemove);
        }
        this.d.remove(str);
        if (this.e.containsKey(str)) {
            Log.w(LOG_TAG, "Dropping pending result for request " + str + ": " + this.e.get(str));
            this.e.remove(str);
        }
        if (this.f.containsKey(str)) {
            Log.w(LOG_TAG, "Dropping pending result for request " + str + ": " + this.f.getParcelable(str));
            this.f.remove(str);
        }
        LifecycleContainer lifecycleContainer = this.mKeyToLifecycleContainers.get(str);
        if (lifecycleContainer != null) {
            lifecycleContainer.a();
            this.mKeyToLifecycleContainers.remove(str);
        }
    }

    @MainThread
    public final boolean dispatchResult(int i, int i2, @Nullable Intent intent) {
        String str = this.mRcToKey.get(Integer.valueOf(i));
        if (str == null) {
            return false;
        }
        this.c.remove(str);
        doDispatch(str, i2, intent, this.d.get(str));
        return true;
    }

    @MainThread
    public final <O> boolean dispatchResult(int i, @SuppressLint({"UnknownNullness"}) O o) {
        String str = this.mRcToKey.get(Integer.valueOf(i));
        if (str == null) {
            return false;
        }
        this.c.remove(str);
        CallbackAndContract<?> callbackAndContract = this.d.get(str);
        if (callbackAndContract != null && callbackAndContract.a != null) {
            callbackAndContract.a.onActivityResult(o);
            return true;
        }
        this.f.remove(str);
        this.e.put(str, o);
        return true;
    }

    @MainThread
    public abstract <I, O> void onLaunch(int i, @NonNull ActivityResultContract<I, O> activityResultContract, @SuppressLint({"UnknownNullness"}) I i2, @Nullable ActivityOptionsCompat activityOptionsCompat);

    public final void onRestoreInstanceState(@Nullable Bundle bundle) {
        if (bundle == null) {
            return;
        }
        ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList(KEY_COMPONENT_ACTIVITY_REGISTERED_RCS);
        ArrayList<String> stringArrayList = bundle.getStringArrayList(KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS);
        if (stringArrayList == null || integerArrayList == null) {
            return;
        }
        this.c = bundle.getStringArrayList(KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS);
        this.mRandom = (Random) bundle.getSerializable(KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT);
        this.f.putAll(bundle.getBundle(KEY_COMPONENT_ACTIVITY_PENDING_RESULTS));
        for (int i = 0; i < stringArrayList.size(); i++) {
            String str = stringArrayList.get(i);
            if (this.b.containsKey(str)) {
                Integer numRemove = this.b.remove(str);
                if (!this.f.containsKey(str)) {
                    this.mRcToKey.remove(numRemove);
                }
            }
            bindRcKey(integerArrayList.get(i).intValue(), stringArrayList.get(i));
        }
    }

    public final void onSaveInstanceState(@NonNull Bundle bundle) {
        bundle.putIntegerArrayList(KEY_COMPONENT_ACTIVITY_REGISTERED_RCS, new ArrayList<>(this.b.values()));
        bundle.putStringArrayList(KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS, new ArrayList<>(this.b.keySet()));
        bundle.putStringArrayList(KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS, new ArrayList<>(this.c));
        bundle.putBundle(KEY_COMPONENT_ACTIVITY_PENDING_RESULTS, (Bundle) this.f.clone());
        bundle.putSerializable(KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT, this.mRandom);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NonNull
    public final <I, O> ActivityResultLauncher<I> register(@NonNull final String str, @NonNull final ActivityResultContract<I, O> activityResultContract, @NonNull ActivityResultCallback<O> activityResultCallback) {
        final int iRegisterKey = registerKey(str);
        this.d.put(str, new CallbackAndContract<>(activityResultCallback, activityResultContract));
        if (this.e.containsKey(str)) {
            Object obj = this.e.get(str);
            this.e.remove(str);
            activityResultCallback.onActivityResult(obj);
        }
        ActivityResult activityResult = (ActivityResult) this.f.getParcelable(str);
        if (activityResult != null) {
            this.f.remove(str);
            activityResultCallback.onActivityResult(activityResultContract.parseResult(activityResult.getResultCode(), activityResult.getData()));
        }
        return new ActivityResultLauncher<I>() { // from class: androidx.activity.result.ActivityResultRegistry.3
            @Override // androidx.activity.result.ActivityResultLauncher
            @NonNull
            public ActivityResultContract<I, ?> getContract() {
                return activityResultContract;
            }

            @Override // androidx.activity.result.ActivityResultLauncher
            public void launch(I i, @Nullable ActivityOptionsCompat activityOptionsCompat) {
                ActivityResultRegistry.this.c.add(str);
                Integer num = ActivityResultRegistry.this.b.get(str);
                ActivityResultRegistry.this.onLaunch(num != null ? num.intValue() : iRegisterKey, activityResultContract, i, activityOptionsCompat);
            }

            @Override // androidx.activity.result.ActivityResultLauncher
            public void unregister() {
                ActivityResultRegistry.this.a(str);
            }
        };
    }

    @NonNull
    public final <I, O> ActivityResultLauncher<I> register(@NonNull final String str, @NonNull LifecycleOwner lifecycleOwner, @NonNull final ActivityResultContract<I, O> activityResultContract, @NonNull final ActivityResultCallback<O> activityResultCallback) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            throw new IllegalStateException("LifecycleOwner " + lifecycleOwner + " is attempting to register while current state is " + lifecycle.getCurrentState() + ". LifecycleOwners must call register before they are STARTED.");
        }
        final int iRegisterKey = registerKey(str);
        LifecycleContainer lifecycleContainer = this.mKeyToLifecycleContainers.get(str);
        if (lifecycleContainer == null) {
            lifecycleContainer = new LifecycleContainer(lifecycle);
        }
        lifecycleContainer.a(new LifecycleEventObserver() { // from class: androidx.activity.result.ActivityResultRegistry.1
            @Override // androidx.lifecycle.LifecycleEventObserver
            public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner2, @NonNull Lifecycle.Event event) {
                if (!Lifecycle.Event.ON_START.equals(event)) {
                    if (Lifecycle.Event.ON_STOP.equals(event)) {
                        ActivityResultRegistry.this.d.remove(str);
                        return;
                    } else {
                        if (Lifecycle.Event.ON_DESTROY.equals(event)) {
                            ActivityResultRegistry.this.a(str);
                            return;
                        }
                        return;
                    }
                }
                ActivityResultRegistry.this.d.put(str, new CallbackAndContract<>(activityResultCallback, activityResultContract));
                if (ActivityResultRegistry.this.e.containsKey(str)) {
                    Object obj = ActivityResultRegistry.this.e.get(str);
                    ActivityResultRegistry.this.e.remove(str);
                    activityResultCallback.onActivityResult(obj);
                }
                ActivityResult activityResult = (ActivityResult) ActivityResultRegistry.this.f.getParcelable(str);
                if (activityResult != null) {
                    ActivityResultRegistry.this.f.remove(str);
                    activityResultCallback.onActivityResult(activityResultContract.parseResult(activityResult.getResultCode(), activityResult.getData()));
                }
            }
        });
        this.mKeyToLifecycleContainers.put(str, lifecycleContainer);
        return new ActivityResultLauncher<I>() { // from class: androidx.activity.result.ActivityResultRegistry.2
            @Override // androidx.activity.result.ActivityResultLauncher
            @NonNull
            public ActivityResultContract<I, ?> getContract() {
                return activityResultContract;
            }

            @Override // androidx.activity.result.ActivityResultLauncher
            public void launch(I i, @Nullable ActivityOptionsCompat activityOptionsCompat) {
                ActivityResultRegistry.this.c.add(str);
                Integer num = ActivityResultRegistry.this.b.get(str);
                ActivityResultRegistry.this.onLaunch(num != null ? num.intValue() : iRegisterKey, activityResultContract, i, activityOptionsCompat);
            }

            @Override // androidx.activity.result.ActivityResultLauncher
            public void unregister() {
                ActivityResultRegistry.this.a(str);
            }
        };
    }
}
