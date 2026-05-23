package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.widget.SpinnerAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;

/* JADX INFO: loaded from: classes.dex */
public interface ThemedSpinnerAdapter extends SpinnerAdapter {

    public static final class Helper {
        private final Context mContext;
        private LayoutInflater mDropDownInflater;
        private final LayoutInflater mInflater;

        public Helper(@NonNull Context context) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
        }

        @NonNull
        public LayoutInflater getDropDownViewInflater() {
            return this.mDropDownInflater != null ? this.mDropDownInflater : this.mInflater;
        }

        @Nullable
        public Resources.Theme getDropDownViewTheme() {
            if (this.mDropDownInflater == null) {
                return null;
            }
            return this.mDropDownInflater.getContext().getTheme();
        }

        public void setDropDownViewTheme(@Nullable Resources.Theme theme) {
            this.mDropDownInflater = theme == null ? null : theme == this.mContext.getTheme() ? this.mInflater : LayoutInflater.from(new ContextThemeWrapper(this.mContext, theme));
        }
    }

    @Nullable
    Resources.Theme getDropDownViewTheme();

    void setDropDownViewTheme(@Nullable Resources.Theme theme);
}
