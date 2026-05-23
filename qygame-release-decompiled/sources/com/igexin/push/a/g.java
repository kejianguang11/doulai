package com.igexin.push.a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Movie;
import android.graphics.Outline;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import com.getui.gtc.base.GtcProvider;
import com.igexin.push.a.e;
import com.igexin.push.core.a.c.k;
import com.igexin.push.core.b.m;
import com.igexin.push.core.b.s;
import com.igexin.push.core.i.a.h;
import com.igexin.push.core.l;
import com.igexin.push.extension.mod.PushTaskBean;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.main.FeedbackImpl;
import com.igexin.sdk.message.GTPopupMessage;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public final class g extends b {
    public static boolean a = false;
    private final String b = "popupAct";
    private final ArrayList<com.igexin.push.core.i.a.f> c = new ArrayList<>();

    /* JADX INFO: renamed from: com.igexin.push.a.g$1, reason: invalid class name */
    final class AnonymousClass1 implements c<m.b> {
        final /* synthetic */ AtomicLong a;
        final /* synthetic */ Activity b;
        final /* synthetic */ PushTaskBean c;
        final /* synthetic */ Context d;

        AnonymousClass1(AtomicLong atomicLong, Activity activity, PushTaskBean pushTaskBean, Context context) {
            this.a = atomicLong;
            this.b = activity;
            this.c = pushTaskBean;
            this.d = context;
        }

        /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
        private void a2(m.b bVar) {
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - this.a.getAndSet(jCurrentTimeMillis) < 350) {
                    com.igexin.c.a.c.a.b("popupAct", "repetition click");
                    return;
                }
                if (bVar.o.getAction().isClosePopup()) {
                    this.b.finish();
                }
                g.a(bVar, this.c, this.d);
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
        }

        @Override // com.igexin.push.a.c
        public final /* synthetic */ void a(m.b bVar) {
            m.b bVar2 = bVar;
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - this.a.getAndSet(jCurrentTimeMillis) < 350) {
                    com.igexin.c.a.c.a.b("popupAct", "repetition click");
                    return;
                }
                if (bVar2.o.getAction().isClosePopup()) {
                    this.b.finish();
                }
                g.a(bVar2, this.c, this.d);
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.a.g$2, reason: invalid class name */
    final class AnonymousClass2 implements Runnable {
        final /* synthetic */ m a;
        final /* synthetic */ PushTaskBean b;

        AnonymousClass2(m mVar, PushTaskBean pushTaskBean) {
            this.a = mVar;
            this.b = pushTaskBean;
        }

        @Override // java.lang.Runnable
        public final void run() {
            l lVarA = l.a();
            GTPopupMessage gTPopupMessage = this.a.j;
            Bundle bundle = new Bundle();
            bundle.putInt("action", PushConsts.ACTION_POPUP_SHOW);
            bundle.putSerializable(PushConsts.KEY_POPUP_SHOW, gTPopupMessage);
            lVarA.a(bundle);
            FeedbackImpl.getInstance().feedbackMessageAction(this.b, this.a.i, com.igexin.push.core.b.B);
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.a.g$3, reason: invalid class name */
    static class AnonymousClass3 implements Runnable {
        final /* synthetic */ m.b a;
        final /* synthetic */ PushTaskBean b;
        final /* synthetic */ Context c;

        AnonymousClass3(m.b bVar, PushTaskBean pushTaskBean, Context context) {
            this.a = bVar;
            this.b = pushTaskBean;
            this.c = context;
        }

        @Override // java.lang.Runnable
        public final void run() {
            l lVarA = l.a();
            GTPopupMessage gTPopupMessage = this.a.o;
            Bundle bundle = new Bundle();
            bundle.setClassLoader(GTPopupMessage.class.getClassLoader());
            bundle.putInt("action", PushConsts.ACTION_POPUP_CLICKED);
            bundle.putSerializable(PushConsts.KEY_POPUP_CLICKED, gTPopupMessage);
            lVarA.a(bundle);
            FeedbackImpl.getInstance().feedbackMessageAction(this.b, this.a.p, com.igexin.push.core.b.B);
            GTPopupMessage.GtAction action = this.a.o.getAction();
            String actionType = action.getActionType();
            if (m.a.intent.name().equals(actionType)) {
                new k();
                k.a(action.getIntent(), this.c);
                return;
            }
            if (!m.a.url.name().equals(actionType)) {
                m.a.closePopup.name().equals(actionType);
                return;
            }
            new com.igexin.push.core.a.c.l();
            String url = action.getUrl();
            Context context = this.c;
            try {
                if (TextUtils.isEmpty(url)) {
                    return;
                }
                s sVar = new s();
                sVar.a = url;
                com.igexin.push.core.a.c.l.a(sVar, com.igexin.push.core.b.A);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.setFlags(268435456);
                intent.setPackage(sVar.d);
                intent.setData(Uri.parse(sVar.a()));
                context.startActivity(intent);
            } catch (Exception e) {
                com.igexin.c.a.c.a.a(e);
            }
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.a.g$4, reason: invalid class name */
    final class AnonymousClass4 implements View.OnClickListener {
        final /* synthetic */ c a;
        final /* synthetic */ m.b b;

        AnonymousClass4(c cVar, m.b bVar) {
            this.a = cVar;
            this.b = bVar;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.a.a(this.b);
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.a.g$9, reason: invalid class name */
    final class AnonymousClass9 implements e.a<byte[]> {
        final /* synthetic */ m.b a;
        final /* synthetic */ Context b;
        final /* synthetic */ int c;
        final /* synthetic */ int d;
        final /* synthetic */ d e;

        AnonymousClass9(m.b bVar, Context context, int i, int i2, d dVar) {
            this.a = bVar;
            this.b = context;
            this.c = i;
            this.d = i2;
            this.e = dVar;
        }

        /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
        private void a2(byte[] bArr) {
            try {
                com.igexin.c.a.c.a.b("popupAct", "movie duration is 0 use glide " + this.a.h);
                com.igexin.push.core.i.a.f fVarA = new com.igexin.push.core.i.a.a(this.b).a(ByteBuffer.wrap(bArr), this.c, this.d);
                fVarA.b();
                com.igexin.push.core.i.a.e eVarA = fVarA.c();
                this.e.setImageDrawable(eVarA);
                com.igexin.push.core.i.a.k.a(!eVarA.d, "You cannot restart a currently running animation.");
                h hVar = eVarA.c.a;
                com.igexin.push.core.i.a.k.a(!hVar.c, "Can't restart a running animation");
                hVar.d = true;
                if (hVar.i != null) {
                    hVar.i = null;
                }
                eVarA.start();
                g.this.c.add(fVarA);
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
        }

        @Override // com.igexin.push.a.e.a
        public final /* synthetic */ void a(byte[] bArr) {
            byte[] bArr2 = bArr;
            try {
                com.igexin.c.a.c.a.b("popupAct", "movie duration is 0 use glide " + this.a.h);
                com.igexin.push.core.i.a.f fVarA = new com.igexin.push.core.i.a.a(this.b).a(ByteBuffer.wrap(bArr2), this.c, this.d);
                fVarA.b();
                com.igexin.push.core.i.a.e eVarA = fVarA.c();
                this.e.setImageDrawable(eVarA);
                com.igexin.push.core.i.a.k.a(!eVarA.d, "You cannot restart a currently running animation.");
                h hVar = eVarA.c.a;
                com.igexin.push.core.i.a.k.a(!hVar.c, "Can't restart a running animation");
                hVar.d = true;
                if (hVar.i != null) {
                    hVar.i = null;
                }
                eVarA.start();
                g.this.c.add(fVarA);
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
        }

        @Override // com.igexin.push.a.e.a
        public final void a(Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static int a(m.b bVar) {
        byte b;
        String str = bVar.b;
        int iHashCode = str.hashCode();
        if (iHashCode != -1364013995) {
            if (iHashCode != 3317767) {
                b = (iHashCode == 108511772 && str.equals("right")) ? (byte) 2 : (byte) -1;
            } else if (str.equals("left")) {
                b = 0;
            }
        } else if (str.equals("center")) {
            b = 1;
        }
        switch (b) {
        }
        return GravityCompat.START;
    }

    private View a(final m.b bVar, final Context context, c<m.b> cVar) {
        int i;
        View textView;
        if (bVar == null) {
            return null;
        }
        switch (bVar.a) {
            case "column":
            case "row":
            case "view":
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(!"row".equals(bVar.a) ? 1 : 0);
                View viewA = a(bVar, linearLayout, cVar);
                if (bVar.g != null) {
                    for (i = 0; i < bVar.g.size(); i++) {
                        linearLayout.addView(a(bVar.g.get(i), context, cVar));
                    }
                }
                return viewA;
            case "image":
            case "image_button":
                final d dVar = new d(context);
                View viewA2 = a(bVar, dVar, cVar);
                dVar.setScaleType(ImageView.ScaleType.CENTER_CROP);
                try {
                    if (!TextUtils.isEmpty(bVar.h)) {
                        if (bVar.h.endsWith(".gif")) {
                            final int iA = bVar.a();
                            final int iB = bVar.b();
                            e.b(bVar.h, new e.a<Movie>() { // from class: com.igexin.push.a.g.5
                                /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                                private void a2(Movie movie) {
                                    if (movie.duration() == 0) {
                                        g.a(g.this, bVar, context, iA, iB, dVar);
                                        return;
                                    }
                                    d dVar2 = dVar;
                                    dVar2.a = movie;
                                    dVar2.b = 0L;
                                    dVar2.c = 0;
                                    dVar2.setLayerType(1, null);
                                    dVar2.setImageDrawable(null);
                                    dVar2.requestLayout();
                                    dVar2.invalidate();
                                    d dVar3 = dVar;
                                    if (dVar3.d) {
                                        dVar3.d = false;
                                        if (dVar3.a != null) {
                                            dVar3.b = SystemClock.uptimeMillis() - ((long) dVar3.c);
                                            dVar3.invalidate();
                                        }
                                    }
                                }

                                @Override // com.igexin.push.a.e.a
                                public final /* synthetic */ void a(Movie movie) {
                                    Movie movie2 = movie;
                                    if (movie2.duration() == 0) {
                                        g.a(g.this, bVar, context, iA, iB, dVar);
                                        return;
                                    }
                                    d dVar2 = dVar;
                                    dVar2.a = movie2;
                                    dVar2.b = 0L;
                                    dVar2.c = 0;
                                    dVar2.setLayerType(1, null);
                                    dVar2.setImageDrawable(null);
                                    dVar2.requestLayout();
                                    dVar2.invalidate();
                                    d dVar3 = dVar;
                                    if (dVar3.d) {
                                        dVar3.d = false;
                                        if (dVar3.a != null) {
                                            dVar3.b = SystemClock.uptimeMillis() - ((long) dVar3.c);
                                            dVar3.invalidate();
                                        }
                                    }
                                }

                                @Override // com.igexin.push.a.e.a
                                public final void a(Throwable th) {
                                    com.igexin.c.a.c.a.a(th);
                                }
                            });
                        } else {
                            e.a(bVar.h, bVar.a(), new e.a<Bitmap>() { // from class: com.igexin.push.a.g.6
                                /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                                private void a2(Bitmap bitmap) {
                                    dVar.setImageBitmap(bitmap);
                                }

                                @Override // com.igexin.push.a.e.a
                                public final /* synthetic */ void a(Bitmap bitmap) {
                                    dVar.setImageBitmap(bitmap);
                                }

                                @Override // com.igexin.push.a.e.a
                                public final void a(Throwable th) {
                                    com.igexin.c.a.c.a.a(th);
                                }
                            });
                        }
                    }
                    break;
                } catch (Throwable th) {
                    com.igexin.c.a.c.a.a(th);
                }
                return viewA2;
            case "button":
                Button button = new Button(context);
                button.setAllCaps(false);
                textView = button;
                break;
            case "label":
                textView = new TextView(context);
                break;
            default:
                throw new RuntimeException("can't find type " + bVar.a);
        }
        return a(bVar, textView, cVar);
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0111  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private View a(final m.b bVar, final View view, final c<m.b> cVar) {
        view.setPadding(bVar.j(), bVar.g(), bVar.i(), bVar.h());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bVar.a(), bVar.b());
        layoutParams.setMargins(bVar.f(), bVar.c(), bVar.e(), bVar.d());
        if (bVar.b != null) {
            layoutParams.gravity = a(bVar);
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        if (bVar.m != 0) {
            gradientDrawable.setColor(bVar.m);
        }
        int iM = bVar.m();
        if (iM > 0) {
            gradientDrawable.setStroke(iM, bVar.n);
        }
        final int iK = bVar.k();
        if (iK > 0) {
            gradientDrawable.setCornerRadius(iK);
        }
        view.setBackground(gradientDrawable);
        if (!TextUtils.isEmpty(bVar.j) && !bVar.j.endsWith(".gif")) {
            e.a(bVar.j, bVar.a(), new e.a<Bitmap>() { // from class: com.igexin.push.a.g.7

                /* JADX INFO: renamed from: com.igexin.push.a.g$7$1, reason: invalid class name */
                final class AnonymousClass1 extends ViewOutlineProvider {
                    AnonymousClass1() {
                    }

                    @Override // android.view.ViewOutlineProvider
                    public final void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), iK);
                    }
                }

                /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                private void a2(Bitmap bitmap) {
                    try {
                        view.setBackground(new BitmapDrawable(view.getResources(), bitmap));
                        if (Build.VERSION.SDK_INT < 21 || iK <= 0) {
                            return;
                        }
                        view.setClipToOutline(true);
                        view.setOutlineProvider(new AnonymousClass1());
                    } catch (Throwable th) {
                        com.igexin.c.a.c.a.a(th);
                    }
                }

                @Override // com.igexin.push.a.e.a
                public final /* synthetic */ void a(Bitmap bitmap) {
                    try {
                        view.setBackground(new BitmapDrawable(view.getResources(), bitmap));
                        if (Build.VERSION.SDK_INT < 21 || iK <= 0) {
                            return;
                        }
                        view.setClipToOutline(true);
                        view.setOutlineProvider(new AnonymousClass1());
                    } catch (Throwable th) {
                        com.igexin.c.a.c.a.a(th);
                    }
                }

                @Override // com.igexin.push.a.e.a
                public final void a(Throwable th) {
                    com.igexin.c.a.c.a.a(th);
                }
            });
        }
        view.setLayoutParams(layoutParams);
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setText(!TextUtils.isEmpty(bVar.k) ? bVar.k : bVar.d);
            if (bVar.l != 0) {
                textView.setTextColor(bVar.l);
            }
            float fL = bVar.l();
            if (fL != 0.0f) {
                textView.setTextSize(0, fL);
            }
            if (bVar.i) {
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                textView.getPaint().setFakeBoldText(true);
            }
            if (bVar.n() != 0) {
                textView.setMaxHeight(bVar.n());
                textView.setMovementMethod(ScrollingMovementMethod.getInstance());
            }
            if (!TextUtils.isEmpty(bVar.c)) {
                textView.setGravity(a(bVar));
            }
        }
        if (bVar.o != null) {
            GTPopupMessage.GtAction action = bVar.o.getAction();
            if (action.isClosePopup() || !m.a.closePopup.name().equals(action.getActionType())) {
                view.setOnClickListener(new View.OnClickListener() { // from class: com.igexin.push.a.g.8
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        cVar.a(bVar);
                    }
                });
            } else {
                view.setClickable(true);
                view.setFocusable(true);
            }
        }
        return view;
    }

    private LinearLayout a(c cVar, m mVar, Context context) {
        m.b bVar = mVar.a;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        linearLayout.setBackgroundColor(bVar.m);
        if (bVar.o != null) {
            GTPopupMessage.GtAction action = bVar.o.getAction();
            if (action.isClosePopup() || !m.a.closePopup.name().equals(action.getActionType())) {
                linearLayout.setOnClickListener(new AnonymousClass4(cVar, bVar));
            } else {
                linearLayout.setClickable(true);
                linearLayout.setFocusable(true);
            }
        }
        View viewA = a(mVar.b, context, (c<m.b>) cVar);
        if (viewA != null) {
            linearLayout.addView(viewA);
        }
        return linearLayout;
    }

    static /* synthetic */ void a(g gVar, m.b bVar, Context context, int i, int i2, d dVar) {
        e.a(bVar.h, gVar.new AnonymousClass9(bVar, context, i, i2, dVar));
    }

    private void a(m.b bVar, Context context, int i, int i2, d dVar) {
        e.a(bVar.h, new AnonymousClass9(bVar, context, i, i2, dVar));
    }

    static /* synthetic */ void a(m.b bVar, PushTaskBean pushTaskBean, Context context) {
        com.igexin.b.a.a().a.execute(new AnonymousClass3(bVar, pushTaskBean, context));
    }

    private static void b(m.b bVar, PushTaskBean pushTaskBean, Context context) {
        com.igexin.b.a.a().a.execute(new AnonymousClass3(bVar, pushTaskBean, context));
    }

    private void c(Activity activity) {
        Intent intent = activity.getIntent();
        try {
            a = true;
            Context applicationContext = activity.getApplicationContext();
            GtcProvider.setContext(applicationContext);
            try {
                if (Build.VERSION.SDK_INT != 26) {
                    if (1 == applicationContext.getResources().getConfiguration().orientation) {
                        activity.setRequestedOrientation(7);
                    } else {
                        activity.setRequestedOrientation(6);
                    }
                }
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
            AtomicLong atomicLong = new AtomicLong(0L);
            m mVar = (m) intent.getExtras().getSerializable("bean");
            PushTaskBean pushTaskBean = new PushTaskBean();
            pushTaskBean.setAppid(mVar.e);
            pushTaskBean.setMessageId(mVar.f);
            pushTaskBean.setTaskId(mVar.g);
            pushTaskBean.setAppKey(mVar.h);
            m.a(applicationContext);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(atomicLong, activity, pushTaskBean, applicationContext);
            m.b bVar = mVar.a;
            LinearLayout linearLayout = new LinearLayout(applicationContext);
            linearLayout.setOrientation(1);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            linearLayout.setBackgroundColor(bVar.m);
            if (bVar.o != null) {
                GTPopupMessage.GtAction action = bVar.o.getAction();
                if (action.isClosePopup() || !m.a.closePopup.name().equals(action.getActionType())) {
                    linearLayout.setOnClickListener(new AnonymousClass4(anonymousClass1, bVar));
                } else {
                    linearLayout.setClickable(true);
                    linearLayout.setFocusable(true);
                }
            }
            View viewA = a(mVar.b, applicationContext, anonymousClass1);
            if (viewA != null) {
                linearLayout.addView(viewA);
            }
            activity.setContentView(linearLayout);
            com.igexin.b.a.a().a.execute(new AnonymousClass2(mVar, pushTaskBean));
        } catch (Throwable th2) {
            com.igexin.c.a.c.a.a(th2);
            activity.finish();
        }
    }

    @Override // com.igexin.push.a.b
    public final void a(Activity activity) {
        try {
            if (Build.VERSION.SDK_INT == 26) {
                activity.finish();
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }

    @Override // com.igexin.push.a.b
    public final boolean a() {
        return false;
    }

    @Override // com.igexin.push.a.b
    public final void b(Activity activity) {
        Intent intent = activity.getIntent();
        try {
            a = true;
            Context applicationContext = activity.getApplicationContext();
            GtcProvider.setContext(applicationContext);
            try {
                if (Build.VERSION.SDK_INT != 26) {
                    if (1 == applicationContext.getResources().getConfiguration().orientation) {
                        activity.setRequestedOrientation(7);
                    } else {
                        activity.setRequestedOrientation(6);
                    }
                }
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
            AtomicLong atomicLong = new AtomicLong(0L);
            m mVar = (m) intent.getExtras().getSerializable("bean");
            PushTaskBean pushTaskBean = new PushTaskBean();
            pushTaskBean.setAppid(mVar.e);
            pushTaskBean.setMessageId(mVar.f);
            pushTaskBean.setTaskId(mVar.g);
            pushTaskBean.setAppKey(mVar.h);
            m.a(applicationContext);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(atomicLong, activity, pushTaskBean, applicationContext);
            m.b bVar = mVar.a;
            LinearLayout linearLayout = new LinearLayout(applicationContext);
            linearLayout.setOrientation(1);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            linearLayout.setBackgroundColor(bVar.m);
            if (bVar.o != null) {
                GTPopupMessage.GtAction action = bVar.o.getAction();
                if (action.isClosePopup() || !m.a.closePopup.name().equals(action.getActionType())) {
                    linearLayout.setOnClickListener(new AnonymousClass4(anonymousClass1, bVar));
                } else {
                    linearLayout.setClickable(true);
                    linearLayout.setFocusable(true);
                }
            }
            View viewA = a(mVar.b, applicationContext, anonymousClass1);
            if (viewA != null) {
                linearLayout.addView(viewA);
            }
            activity.setContentView(linearLayout);
            com.igexin.b.a.a().a.execute(new AnonymousClass2(mVar, pushTaskBean));
        } catch (Throwable th2) {
            com.igexin.c.a.c.a.a(th2);
            activity.finish();
        }
    }

    @Override // com.igexin.push.a.b
    public final boolean b() {
        return false;
    }

    @Override // com.igexin.push.a.b
    public final void c() {
    }

    @Override // com.igexin.push.a.b
    public final void d() {
    }

    @Override // com.igexin.push.a.b
    public final void e() {
    }

    @Override // com.igexin.push.a.b
    public final void f() {
    }

    @Override // com.igexin.push.a.b
    public final void g() {
    }

    @Override // com.igexin.push.a.b
    public final void h() {
    }

    @Override // com.igexin.push.a.b
    public final void i() {
        a = false;
        for (com.igexin.push.core.i.a.f fVar : this.c) {
            if (fVar != null) {
                fVar.f();
            }
        }
        this.c.clear();
    }

    @Override // com.igexin.push.a.b
    public final void j() {
    }

    @Override // com.igexin.push.a.b
    public final void k() {
    }
}
