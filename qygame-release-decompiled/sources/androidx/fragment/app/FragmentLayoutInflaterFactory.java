package androidx.fragment.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.R;

/* JADX INFO: loaded from: classes.dex */
class FragmentLayoutInflaterFactory implements LayoutInflater.Factory2 {
    private static final String TAG = "FragmentManager";
    final FragmentManager a;

    FragmentLayoutInflaterFactory(FragmentManager fragmentManager) {
        this.a = fragmentManager;
    }

    @Override // android.view.LayoutInflater.Factory2
    @Nullable
    public View onCreateView(@Nullable View view, @NonNull String str, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        final FragmentStateManager fragmentStateManagerH;
        String str2;
        StringBuilder sb;
        String str3;
        if (FragmentContainerView.class.getName().equals(str)) {
            return new FragmentContainerView(context, attributeSet, this.a);
        }
        if (!"fragment".equals(str)) {
            return null;
        }
        String attributeValue = attributeSet.getAttributeValue(null, "class");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Fragment);
        if (attributeValue == null) {
            attributeValue = typedArrayObtainStyledAttributes.getString(R.styleable.Fragment_android_name);
        }
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.Fragment_android_id, -1);
        String string = typedArrayObtainStyledAttributes.getString(R.styleable.Fragment_android_tag);
        typedArrayObtainStyledAttributes.recycle();
        if (attributeValue == null || !FragmentFactory.a(context.getClassLoader(), attributeValue)) {
            return null;
        }
        int id = view != null ? view.getId() : 0;
        if (id == -1 && resourceId == -1 && string == null) {
            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + attributeValue);
        }
        Fragment fragmentFindFragmentById = resourceId != -1 ? this.a.findFragmentById(resourceId) : null;
        if (fragmentFindFragmentById == null && string != null) {
            fragmentFindFragmentById = this.a.findFragmentByTag(string);
        }
        if (fragmentFindFragmentById == null && id != -1) {
            fragmentFindFragmentById = this.a.findFragmentById(id);
        }
        if (fragmentFindFragmentById == null) {
            fragmentFindFragmentById = this.a.getFragmentFactory().instantiate(context.getClassLoader(), attributeValue);
            fragmentFindFragmentById.n = true;
            fragmentFindFragmentById.w = resourceId != 0 ? resourceId : id;
            fragmentFindFragmentById.x = id;
            fragmentFindFragmentById.y = string;
            fragmentFindFragmentById.o = true;
            fragmentFindFragmentById.s = this.a;
            fragmentFindFragmentById.t = this.a.h();
            fragmentFindFragmentById.onInflate(this.a.h().b(), attributeSet, fragmentFindFragmentById.c);
            fragmentStateManagerH = this.a.i(fragmentFindFragmentById);
            if (FragmentManager.a(2)) {
                str2 = TAG;
                sb = new StringBuilder();
                sb.append("Fragment ");
                sb.append(fragmentFindFragmentById);
                str3 = " has been inflated via the <fragment> tag: id=0x";
                sb.append(str3);
                sb.append(Integer.toHexString(resourceId));
                Log.v(str2, sb.toString());
            }
        } else {
            if (fragmentFindFragmentById.o) {
                throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string + ", or parent id 0x" + Integer.toHexString(id) + " with another fragment for " + attributeValue);
            }
            fragmentFindFragmentById.o = true;
            fragmentFindFragmentById.s = this.a;
            fragmentFindFragmentById.t = this.a.h();
            fragmentFindFragmentById.onInflate(this.a.h().b(), attributeSet, fragmentFindFragmentById.c);
            fragmentStateManagerH = this.a.h(fragmentFindFragmentById);
            if (FragmentManager.a(2)) {
                str2 = TAG;
                sb = new StringBuilder();
                sb.append("Retained Fragment ");
                sb.append(fragmentFindFragmentById);
                str3 = " has been re-attached via the <fragment> tag: id=0x";
                sb.append(str3);
                sb.append(Integer.toHexString(resourceId));
                Log.v(str2, sb.toString());
            }
        }
        fragmentFindFragmentById.F = (ViewGroup) view;
        fragmentStateManagerH.c();
        fragmentStateManagerH.d();
        if (fragmentFindFragmentById.G == null) {
            throw new IllegalStateException("Fragment " + attributeValue + " did not create a view.");
        }
        if (resourceId != 0) {
            fragmentFindFragmentById.G.setId(resourceId);
        }
        if (fragmentFindFragmentById.G.getTag() == null) {
            fragmentFindFragmentById.G.setTag(string);
        }
        fragmentFindFragmentById.G.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: androidx.fragment.app.FragmentLayoutInflaterFactory.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view2) {
                Fragment fragmentA = fragmentStateManagerH.a();
                fragmentStateManagerH.c();
                SpecialEffectsController.a((ViewGroup) fragmentA.G.getParent(), FragmentLayoutInflaterFactory.this.a).d();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view2) {
            }
        });
        return fragmentFindFragmentById.G;
    }

    @Override // android.view.LayoutInflater.Factory
    @Nullable
    public View onCreateView(@NonNull String str, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }
}
