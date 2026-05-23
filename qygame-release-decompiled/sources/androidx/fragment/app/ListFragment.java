package androidx.fragment.app;

import android.R;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class ListFragment extends Fragment {
    ListAdapter W;
    ListView X;
    View Y;
    TextView Z;
    View aa;
    View ab;
    CharSequence ac;
    boolean ad;
    private final Handler mHandler = new Handler();
    private final Runnable mRequestFocus = new Runnable() { // from class: androidx.fragment.app.ListFragment.1
        @Override // java.lang.Runnable
        public void run() {
            ListFragment.this.X.focusableViewAvailable(ListFragment.this.X);
        }
    };
    private final AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() { // from class: androidx.fragment.app.ListFragment.2
        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            ListFragment.this.onListItemClick((ListView) adapterView, view, i, j);
        }
    };

    private void ensureList() {
        ListView listView;
        View view;
        if (this.X != null) {
            return;
        }
        View view2 = getView();
        if (view2 == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        if (view2 instanceof ListView) {
            this.X = (ListView) view2;
        } else {
            this.Z = (TextView) view2.findViewById(16711681);
            if (this.Z == null) {
                this.Y = view2.findViewById(R.id.empty);
            } else {
                this.Z.setVisibility(8);
            }
            this.aa = view2.findViewById(16711682);
            this.ab = view2.findViewById(16711683);
            View viewFindViewById = view2.findViewById(R.id.list);
            if (!(viewFindViewById instanceof ListView)) {
                if (viewFindViewById != null) {
                    throw new RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class");
                }
                throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
            }
            this.X = (ListView) viewFindViewById;
            if (this.Y != null) {
                listView = this.X;
                view = this.Y;
            } else if (this.ac != null) {
                this.Z.setText(this.ac);
                listView = this.X;
                view = this.Z;
            }
            listView.setEmptyView(view);
        }
        this.ad = true;
        this.X.setOnItemClickListener(this.mOnClickListener);
        if (this.W != null) {
            ListAdapter listAdapter = this.W;
            this.W = null;
            setListAdapter(listAdapter);
        } else if (this.aa != null) {
            setListShown(false, false);
        }
        this.mHandler.post(this.mRequestFocus);
    }

    private void setListShown(boolean z, boolean z2) {
        ensureList();
        if (this.aa == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        if (this.ad == z) {
            return;
        }
        this.ad = z;
        if (z) {
            if (z2) {
                this.aa.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
                this.ab.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            } else {
                this.aa.clearAnimation();
                this.ab.clearAnimation();
            }
            this.aa.setVisibility(8);
            this.ab.setVisibility(0);
            return;
        }
        if (z2) {
            this.aa.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            this.ab.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
        } else {
            this.aa.clearAnimation();
            this.ab.clearAnimation();
        }
        this.aa.setVisibility(0);
        this.ab.setVisibility(8);
    }

    @Nullable
    public ListAdapter getListAdapter() {
        return this.W;
    }

    @NonNull
    public ListView getListView() {
        ensureList();
        return this.X;
    }

    public long getSelectedItemId() {
        ensureList();
        return this.X.getSelectedItemId();
    }

    public int getSelectedItemPosition() {
        ensureList();
        return this.X.getSelectedItemPosition();
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Context contextRequireContext = requireContext();
        FrameLayout frameLayout = new FrameLayout(contextRequireContext);
        LinearLayout linearLayout = new LinearLayout(contextRequireContext);
        linearLayout.setId(16711682);
        linearLayout.setOrientation(1);
        linearLayout.setVisibility(8);
        linearLayout.setGravity(17);
        linearLayout.addView(new ProgressBar(contextRequireContext, null, R.attr.progressBarStyleLarge), new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView(linearLayout, new FrameLayout.LayoutParams(-1, -1));
        FrameLayout frameLayout2 = new FrameLayout(contextRequireContext);
        frameLayout2.setId(16711683);
        TextView textView = new TextView(contextRequireContext);
        textView.setId(16711681);
        textView.setGravity(17);
        frameLayout2.addView(textView, new FrameLayout.LayoutParams(-1, -1));
        ListView listView = new ListView(contextRequireContext);
        listView.setId(R.id.list);
        listView.setDrawSelectorOnTop(false);
        frameLayout2.addView(listView, new FrameLayout.LayoutParams(-1, -1));
        frameLayout.addView(frameLayout2, new FrameLayout.LayoutParams(-1, -1));
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        return frameLayout;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.mHandler.removeCallbacks(this.mRequestFocus);
        this.X = null;
        this.ad = false;
        this.ab = null;
        this.aa = null;
        this.Y = null;
        this.Z = null;
        super.onDestroyView();
    }

    public void onListItemClick(@NonNull ListView listView, @NonNull View view, int i, long j) {
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        ensureList();
    }

    @NonNull
    public final ListAdapter requireListAdapter() {
        ListAdapter listAdapter = getListAdapter();
        if (listAdapter != null) {
            return listAdapter;
        }
        throw new IllegalStateException("ListFragment " + this + " does not have a ListAdapter.");
    }

    public void setEmptyText(@Nullable CharSequence charSequence) {
        ensureList();
        if (this.Z == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        this.Z.setText(charSequence);
        if (this.ac == null) {
            this.X.setEmptyView(this.Z);
        }
        this.ac = charSequence;
    }

    public void setListAdapter(@Nullable ListAdapter listAdapter) {
        boolean z = this.W != null;
        this.W = listAdapter;
        if (this.X != null) {
            this.X.setAdapter(listAdapter);
            if (this.ad || z) {
                return;
            }
            setListShown(true, requireView().getWindowToken() != null);
        }
    }

    public void setListShown(boolean z) {
        setListShown(z, true);
    }

    public void setListShownNoAnimation(boolean z) {
        setListShown(z, false);
    }

    public void setSelection(int i) {
        ensureList();
        this.X.setSelection(i);
    }
}
