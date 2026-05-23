package androidx.appcompat.app;

import android.R;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.appcompat.widget.TintContextWrapper;
import androidx.collection.SimpleArrayMap;
import androidx.core.view.ViewCompat;
import com.igexin.push.core.b.n;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class AppCompatViewInflater {
    private static final String LOG_TAG = "AppCompatViewInflater";
    private final Object[] mConstructorArgs = new Object[2];
    private static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    private static final int[] sOnClickAttrs = {R.attr.onClick};
    private static final String[] sClassPrefixList = {"android.widget.", "android.view.", "android.webkit."};
    private static final SimpleArrayMap<String, Constructor<? extends View>> sConstructorMap = new SimpleArrayMap<>();

    private static class DeclaredOnClickListener implements View.OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(@NonNull View view, @NonNull String str) {
            this.mHostView = view;
            this.mMethodName = str;
        }

        private void resolveMethod(@Nullable Context context) {
            String str;
            Method method;
            while (context != null) {
                try {
                    if (!context.isRestricted() && (method = context.getClass().getMethod(this.mMethodName, View.class)) != null) {
                        this.mResolvedMethod = method;
                        this.mResolvedContext = context;
                        return;
                    }
                } catch (NoSuchMethodException unused) {
                }
                context = context instanceof ContextWrapper ? ((ContextWrapper) context).getBaseContext() : null;
            }
            int id = this.mHostView.getId();
            if (id == -1) {
                str = "";
            } else {
                str = " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            }
            throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick attribute defined on view " + this.mHostView.getClass() + str);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(@NonNull View view) {
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext());
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, view);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e2) {
                throw new IllegalStateException("Could not execute method for android:onClick", e2);
            }
        }
    }

    private void checkOnClickListener(View view, AttributeSet attributeSet) {
        Context context = view.getContext();
        if (context instanceof ContextWrapper) {
            if (Build.VERSION.SDK_INT < 15 || ViewCompat.hasOnClickListeners(view)) {
                TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, sOnClickAttrs);
                String string = typedArrayObtainStyledAttributes.getString(0);
                if (string != null) {
                    view.setOnClickListener(new DeclaredOnClickListener(view, string));
                }
                typedArrayObtainStyledAttributes.recycle();
            }
        }
    }

    private View createViewByPrefix(Context context, String str, String str2) throws InflateException, ClassNotFoundException {
        String str3;
        Constructor<? extends View> constructor = sConstructorMap.get(str);
        if (constructor == null) {
            if (str2 != null) {
                try {
                    str3 = str2 + str;
                } catch (Exception unused) {
                    return null;
                }
            } else {
                str3 = str;
            }
            constructor = Class.forName(str3, false, context.getClassLoader()).asSubclass(View.class).getConstructor(sConstructorSignature);
            sConstructorMap.put(str, constructor);
        }
        constructor.setAccessible(true);
        return constructor.newInstance(this.mConstructorArgs);
    }

    private View createViewFromTag(Context context, String str, AttributeSet attributeSet) {
        if (str.equals("view")) {
            str = attributeSet.getAttributeValue(null, "class");
        }
        try {
            this.mConstructorArgs[0] = context;
            this.mConstructorArgs[1] = attributeSet;
            if (-1 != str.indexOf(46)) {
                return createViewByPrefix(context, str, null);
            }
            for (int i = 0; i < sClassPrefixList.length; i++) {
                View viewCreateViewByPrefix = createViewByPrefix(context, str, sClassPrefixList[i]);
                if (viewCreateViewByPrefix != null) {
                    return viewCreateViewByPrefix;
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        } finally {
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
        }
    }

    private static Context themifyContext(Context context, AttributeSet attributeSet, boolean z, boolean z2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.appcompat.R.styleable.View, 0, 0);
        int resourceId = z ? typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.View_android_theme, 0) : 0;
        if (z2 && resourceId == 0 && (resourceId = typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.View_theme, 0)) != 0) {
            Log.i(LOG_TAG, "app:theme is now deprecated. Please move to using android:theme instead.");
        }
        typedArrayObtainStyledAttributes.recycle();
        return resourceId != 0 ? ((context instanceof ContextThemeWrapper) && ((ContextThemeWrapper) context).getThemeResId() == resourceId) ? context : new ContextThemeWrapper(context, resourceId) : context;
    }

    private void verifyNotNull(View view, String str) {
        if (view != null) {
            return;
        }
        throw new IllegalStateException(getClass().getName() + " asked to inflate view for <" + str + ">, but returned null");
    }

    @Nullable
    protected View a(Context context, String str, AttributeSet attributeSet) {
        return null;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    final View a(View view, String str, @NonNull Context context, @NonNull AttributeSet attributeSet, boolean z, boolean z2, boolean z3, boolean z4) {
        View viewA;
        Context context2 = (!z || view == null) ? context : view.getContext();
        if (z2 || z3) {
            context2 = themifyContext(context2, attributeSet, z2, z3);
        }
        if (z4) {
            context2 = TintContextWrapper.wrap(context2);
        }
        byte b = -1;
        switch (str.hashCode()) {
            case -1946472170:
                if (str.equals("RatingBar")) {
                    b = n.l;
                }
                break;
            case -1455429095:
                if (str.equals("CheckedTextView")) {
                    b = 8;
                }
                break;
            case -1346021293:
                if (str.equals("MultiAutoCompleteTextView")) {
                    b = 10;
                }
                break;
            case -938935918:
                if (str.equals("TextView")) {
                    b = 0;
                }
                break;
            case -937446323:
                if (str.equals("ImageButton")) {
                    b = 5;
                }
                break;
            case -658531749:
                if (str.equals("SeekBar")) {
                    b = 12;
                }
                break;
            case -339785223:
                if (str.equals("Spinner")) {
                    b = 4;
                }
                break;
            case 776382189:
                if (str.equals("RadioButton")) {
                    b = 7;
                }
                break;
            case 799298502:
                if (str.equals("ToggleButton")) {
                    b = 13;
                }
                break;
            case 1125864064:
                if (str.equals("ImageView")) {
                    b = 1;
                }
                break;
            case 1413872058:
                if (str.equals("AutoCompleteTextView")) {
                    b = 9;
                }
                break;
            case 1601505219:
                if (str.equals("CheckBox")) {
                    b = 6;
                }
                break;
            case 1666676343:
                if (str.equals("EditText")) {
                    b = 3;
                }
                break;
            case 2001146706:
                if (str.equals("Button")) {
                    b = 2;
                }
                break;
        }
        switch (b) {
            case 0:
                viewA = a(context2, attributeSet);
                verifyNotNull(viewA, str);
                break;
            case 1:
                viewA = b(context2, attributeSet);
                verifyNotNull(viewA, str);
                break;
            case 2:
                viewA = c(context2, attributeSet);
                verifyNotNull(viewA, str);
                break;
            case 3:
                viewA = d(context2, attributeSet);
                verifyNotNull(viewA, str);
                break;
            case 4:
                viewA = e(context2, attributeSet);
                verifyNotNull(viewA, str);
                break;
            case 5:
                viewA = f(context2, attributeSet);
                verifyNotNull(viewA, str);
                break;
            case 6:
                viewA = g(context2, attributeSet);
                verifyNotNull(viewA, str);
                break;
            case 7:
                viewA = h(context2, attributeSet);
                verifyNotNull(viewA, str);
                break;
            case 8:
                viewA = i(context2, attributeSet);
                verifyNotNull(viewA, str);
                break;
            case 9:
                viewA = j(context2, attributeSet);
                verifyNotNull(viewA, str);
                break;
            case 10:
                viewA = k(context2, attributeSet);
                verifyNotNull(viewA, str);
                break;
            case 11:
                viewA = l(context2, attributeSet);
                verifyNotNull(viewA, str);
                break;
            case 12:
                viewA = m(context2, attributeSet);
                verifyNotNull(viewA, str);
                break;
            case 13:
                viewA = n(context2, attributeSet);
                verifyNotNull(viewA, str);
                break;
            default:
                viewA = a(context2, str, attributeSet);
                break;
        }
        if (viewA == null && context != context2) {
            viewA = createViewFromTag(context2, str, attributeSet);
        }
        if (viewA != null) {
            checkOnClickListener(viewA, attributeSet);
        }
        return viewA;
    }

    @NonNull
    protected AppCompatTextView a(Context context, AttributeSet attributeSet) {
        return new AppCompatTextView(context, attributeSet);
    }

    @NonNull
    protected AppCompatImageView b(Context context, AttributeSet attributeSet) {
        return new AppCompatImageView(context, attributeSet);
    }

    @NonNull
    protected AppCompatButton c(Context context, AttributeSet attributeSet) {
        return new AppCompatButton(context, attributeSet);
    }

    @NonNull
    protected AppCompatEditText d(Context context, AttributeSet attributeSet) {
        return new AppCompatEditText(context, attributeSet);
    }

    @NonNull
    protected AppCompatSpinner e(Context context, AttributeSet attributeSet) {
        return new AppCompatSpinner(context, attributeSet);
    }

    @NonNull
    protected AppCompatImageButton f(Context context, AttributeSet attributeSet) {
        return new AppCompatImageButton(context, attributeSet);
    }

    @NonNull
    protected AppCompatCheckBox g(Context context, AttributeSet attributeSet) {
        return new AppCompatCheckBox(context, attributeSet);
    }

    @NonNull
    protected AppCompatRadioButton h(Context context, AttributeSet attributeSet) {
        return new AppCompatRadioButton(context, attributeSet);
    }

    @NonNull
    protected AppCompatCheckedTextView i(Context context, AttributeSet attributeSet) {
        return new AppCompatCheckedTextView(context, attributeSet);
    }

    @NonNull
    protected AppCompatAutoCompleteTextView j(Context context, AttributeSet attributeSet) {
        return new AppCompatAutoCompleteTextView(context, attributeSet);
    }

    @NonNull
    protected AppCompatMultiAutoCompleteTextView k(Context context, AttributeSet attributeSet) {
        return new AppCompatMultiAutoCompleteTextView(context, attributeSet);
    }

    @NonNull
    protected AppCompatRatingBar l(Context context, AttributeSet attributeSet) {
        return new AppCompatRatingBar(context, attributeSet);
    }

    @NonNull
    protected AppCompatSeekBar m(Context context, AttributeSet attributeSet) {
        return new AppCompatSeekBar(context, attributeSet);
    }

    @NonNull
    protected AppCompatToggleButton n(Context context, AttributeSet attributeSet) {
        return new AppCompatToggleButton(context, attributeSet);
    }
}
