package androidx.core.app;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobServiceEngine;
import android.app.job.JobWorkItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.igexin.push.config.c;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public abstract class JobIntentService extends Service {
    static final Object h = new Object();
    static final HashMap<ComponentName, WorkEnqueuer> i = new HashMap<>();
    CompatJobEngine a;
    WorkEnqueuer b;
    CommandProcessor c;
    boolean d = false;
    boolean e = false;
    boolean f = false;
    final ArrayList<CompatWorkItem> g;

    final class CommandProcessor extends AsyncTask<Void, Void, Void> {
        CommandProcessor() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public Void doInBackground(Void... voidArr) {
            while (true) {
                GenericWorkItem genericWorkItemC = JobIntentService.this.c();
                if (genericWorkItemC == null) {
                    return null;
                }
                JobIntentService.this.a(genericWorkItemC.getIntent());
                genericWorkItemC.complete();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onCancelled(Void r1) {
            JobIntentService.this.b();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(Void r1) {
            JobIntentService.this.b();
        }
    }

    interface CompatJobEngine {
        IBinder compatGetBinder();

        GenericWorkItem dequeueWork();
    }

    static final class CompatWorkEnqueuer extends WorkEnqueuer {
        boolean a;
        boolean b;
        private final Context mContext;
        private final PowerManager.WakeLock mLaunchWakeLock;
        private final PowerManager.WakeLock mRunWakeLock;

        CompatWorkEnqueuer(Context context, ComponentName componentName) {
            super(componentName);
            this.mContext = context.getApplicationContext();
            PowerManager powerManager = (PowerManager) context.getSystemService("power");
            this.mLaunchWakeLock = powerManager.newWakeLock(1, componentName.getClassName() + ":launch");
            this.mLaunchWakeLock.setReferenceCounted(false);
            this.mRunWakeLock = powerManager.newWakeLock(1, componentName.getClassName() + ":run");
            this.mRunWakeLock.setReferenceCounted(false);
        }

        @Override // androidx.core.app.JobIntentService.WorkEnqueuer
        void a(Intent intent) {
            Intent intent2 = new Intent(intent);
            intent2.setComponent(this.c);
            if (this.mContext.startService(intent2) != null) {
                synchronized (this) {
                    if (!this.a) {
                        this.a = true;
                        if (!this.b) {
                            this.mLaunchWakeLock.acquire(60000L);
                        }
                    }
                }
            }
        }

        @Override // androidx.core.app.JobIntentService.WorkEnqueuer
        public void serviceProcessingFinished() {
            synchronized (this) {
                if (this.b) {
                    if (this.a) {
                        this.mLaunchWakeLock.acquire(60000L);
                    }
                    this.b = false;
                    this.mRunWakeLock.release();
                }
            }
        }

        @Override // androidx.core.app.JobIntentService.WorkEnqueuer
        public void serviceProcessingStarted() {
            synchronized (this) {
                if (!this.b) {
                    this.b = true;
                    this.mRunWakeLock.acquire(c.A);
                    this.mLaunchWakeLock.release();
                }
            }
        }

        @Override // androidx.core.app.JobIntentService.WorkEnqueuer
        public void serviceStartReceived() {
            synchronized (this) {
                this.a = false;
            }
        }
    }

    final class CompatWorkItem implements GenericWorkItem {
        final Intent a;
        final int b;

        CompatWorkItem(Intent intent, int i) {
            this.a = intent;
            this.b = i;
        }

        @Override // androidx.core.app.JobIntentService.GenericWorkItem
        public void complete() {
            JobIntentService.this.stopSelf(this.b);
        }

        @Override // androidx.core.app.JobIntentService.GenericWorkItem
        public Intent getIntent() {
            return this.a;
        }
    }

    interface GenericWorkItem {
        void complete();

        Intent getIntent();
    }

    @RequiresApi(26)
    static final class JobServiceEngineImpl extends JobServiceEngine implements CompatJobEngine {
        final JobIntentService a;
        final Object b;
        JobParameters c;

        final class WrapperWorkItem implements GenericWorkItem {
            final JobWorkItem a;

            WrapperWorkItem(JobWorkItem jobWorkItem) {
                this.a = jobWorkItem;
            }

            @Override // androidx.core.app.JobIntentService.GenericWorkItem
            public void complete() {
                synchronized (JobServiceEngineImpl.this.b) {
                    if (JobServiceEngineImpl.this.c != null) {
                        JobServiceEngineImpl.this.c.completeWork(this.a);
                    }
                }
            }

            @Override // androidx.core.app.JobIntentService.GenericWorkItem
            public Intent getIntent() {
                return this.a.getIntent();
            }
        }

        JobServiceEngineImpl(JobIntentService jobIntentService) {
            super(jobIntentService);
            this.b = new Object();
            this.a = jobIntentService;
        }

        @Override // androidx.core.app.JobIntentService.CompatJobEngine
        public IBinder compatGetBinder() {
            return getBinder();
        }

        @Override // androidx.core.app.JobIntentService.CompatJobEngine
        public GenericWorkItem dequeueWork() {
            synchronized (this.b) {
                if (this.c == null) {
                    return null;
                }
                JobWorkItem jobWorkItemDequeueWork = this.c.dequeueWork();
                if (jobWorkItemDequeueWork == null) {
                    return null;
                }
                jobWorkItemDequeueWork.getIntent().setExtrasClassLoader(this.a.getClassLoader());
                return new WrapperWorkItem(jobWorkItemDequeueWork);
            }
        }

        @Override // android.app.job.JobServiceEngine
        public boolean onStartJob(JobParameters jobParameters) {
            this.c = jobParameters;
            this.a.a(false);
            return true;
        }

        @Override // android.app.job.JobServiceEngine
        public boolean onStopJob(JobParameters jobParameters) {
            boolean zA = this.a.a();
            synchronized (this.b) {
                this.c = null;
            }
            return zA;
        }
    }

    @RequiresApi(26)
    static final class JobWorkEnqueuer extends WorkEnqueuer {
        private final JobInfo mJobInfo;
        private final JobScheduler mJobScheduler;

        JobWorkEnqueuer(Context context, ComponentName componentName, int i) {
            super(componentName);
            a(i);
            this.mJobInfo = new JobInfo.Builder(i, this.c).setOverrideDeadline(0L).build();
            this.mJobScheduler = (JobScheduler) context.getApplicationContext().getSystemService("jobscheduler");
        }

        @Override // androidx.core.app.JobIntentService.WorkEnqueuer
        void a(Intent intent) {
            this.mJobScheduler.enqueue(this.mJobInfo, new JobWorkItem(intent));
        }
    }

    static abstract class WorkEnqueuer {
        final ComponentName c;
        boolean d;
        int e;

        WorkEnqueuer(ComponentName componentName) {
            this.c = componentName;
        }

        void a(int i) {
            if (!this.d) {
                this.d = true;
                this.e = i;
            } else {
                if (this.e == i) {
                    return;
                }
                throw new IllegalArgumentException("Given job ID " + i + " is different than previous " + this.e);
            }
        }

        abstract void a(Intent intent);

        public void serviceProcessingFinished() {
        }

        public void serviceProcessingStarted() {
        }

        public void serviceStartReceived() {
        }
    }

    public JobIntentService() {
        this.g = Build.VERSION.SDK_INT >= 26 ? null : new ArrayList<>();
    }

    static WorkEnqueuer a(Context context, ComponentName componentName, boolean z, int i2) {
        WorkEnqueuer compatWorkEnqueuer;
        WorkEnqueuer workEnqueuer = i.get(componentName);
        if (workEnqueuer != null) {
            return workEnqueuer;
        }
        if (Build.VERSION.SDK_INT < 26) {
            compatWorkEnqueuer = new CompatWorkEnqueuer(context, componentName);
        } else {
            if (!z) {
                throw new IllegalArgumentException("Can't be here without a job id");
            }
            compatWorkEnqueuer = new JobWorkEnqueuer(context, componentName, i2);
        }
        WorkEnqueuer workEnqueuer2 = compatWorkEnqueuer;
        i.put(componentName, workEnqueuer2);
        return workEnqueuer2;
    }

    public static void enqueueWork(@NonNull Context context, @NonNull ComponentName componentName, int i2, @NonNull Intent intent) {
        if (intent == null) {
            throw new IllegalArgumentException("work must not be null");
        }
        synchronized (h) {
            WorkEnqueuer workEnqueuerA = a(context, componentName, true, i2);
            workEnqueuerA.a(i2);
            workEnqueuerA.a(intent);
        }
    }

    public static void enqueueWork(@NonNull Context context, @NonNull Class<?> cls, int i2, @NonNull Intent intent) {
        enqueueWork(context, new ComponentName(context, cls), i2, intent);
    }

    protected abstract void a(@NonNull Intent intent);

    void a(boolean z) {
        if (this.c == null) {
            this.c = new CommandProcessor();
            if (this.b != null && z) {
                this.b.serviceProcessingStarted();
            }
            this.c.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    boolean a() {
        if (this.c != null) {
            this.c.cancel(this.d);
        }
        this.e = true;
        return onStopCurrentWork();
    }

    void b() {
        if (this.g != null) {
            synchronized (this.g) {
                this.c = null;
                if (this.g != null && this.g.size() > 0) {
                    a(false);
                } else if (!this.f) {
                    this.b.serviceProcessingFinished();
                }
            }
        }
    }

    GenericWorkItem c() {
        if (this.a != null) {
            return this.a.dequeueWork();
        }
        synchronized (this.g) {
            if (this.g.size() <= 0) {
                return null;
            }
            return this.g.remove(0);
        }
    }

    public boolean isStopped() {
        return this.e;
    }

    @Override // android.app.Service
    public IBinder onBind(@NonNull Intent intent) {
        if (this.a != null) {
            return this.a.compatGetBinder();
        }
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            this.a = new JobServiceEngineImpl(this);
            this.b = null;
        } else {
            this.a = null;
            this.b = a(this, new ComponentName(this, getClass()), false, 0);
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        if (this.g != null) {
            synchronized (this.g) {
                this.f = true;
                this.b.serviceProcessingFinished();
            }
        }
    }

    @Override // android.app.Service
    public int onStartCommand(@Nullable Intent intent, int i2, int i3) {
        if (this.g == null) {
            return 2;
        }
        this.b.serviceStartReceived();
        synchronized (this.g) {
            ArrayList<CompatWorkItem> arrayList = this.g;
            if (intent == null) {
                intent = new Intent();
            }
            arrayList.add(new CompatWorkItem(intent, i3));
            a(true);
        }
        return 3;
    }

    public boolean onStopCurrentWork() {
        return true;
    }

    public void setInterruptIfStopped(boolean z) {
        this.d = z;
    }
}
