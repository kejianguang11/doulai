package androidx.lifecycle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class ViewModelStore {
    private final HashMap<String, ViewModel> mMap = new HashMap<>();

    final ViewModel a(String str) {
        return this.mMap.get(str);
    }

    Set<String> a() {
        return new HashSet(this.mMap.keySet());
    }

    final void a(String str, ViewModel viewModel) {
        ViewModel viewModelPut = this.mMap.put(str, viewModel);
        if (viewModelPut != null) {
            viewModelPut.a();
        }
    }

    public final void clear() {
        Iterator<ViewModel> it = this.mMap.values().iterator();
        while (it.hasNext()) {
            it.next().e();
        }
        this.mMap.clear();
    }
}
