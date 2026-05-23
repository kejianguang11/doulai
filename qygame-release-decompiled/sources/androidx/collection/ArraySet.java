package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class ArraySet<E> implements Collection<E>, Set<E> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean DEBUG = false;
    private static final int[] INT = new int[0];
    private static final Object[] OBJECT = new Object[0];
    private static final String TAG = "ArraySet";

    @Nullable
    private static Object[] sBaseCache;
    private static int sBaseCacheSize;

    @Nullable
    private static Object[] sTwiceBaseCache;
    private static int sTwiceBaseCacheSize;
    Object[] a;
    int b;
    private MapCollections<E, E> mCollections;
    private int[] mHashes;

    public ArraySet() {
        this(0);
    }

    public ArraySet(int i) {
        if (i == 0) {
            this.mHashes = INT;
            this.a = OBJECT;
        } else {
            allocArrays(i);
        }
        this.b = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ArraySet(@Nullable ArraySet<E> arraySet) {
        this();
        if (arraySet != 0) {
            addAll((ArraySet) arraySet);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ArraySet(@Nullable Collection<E> collection) {
        this();
        if (collection != 0) {
            addAll(collection);
        }
    }

    private void allocArrays(int i) {
        if (i == 8) {
            synchronized (ArraySet.class) {
                if (sTwiceBaseCache != null) {
                    Object[] objArr = sTwiceBaseCache;
                    this.a = objArr;
                    sTwiceBaseCache = (Object[]) objArr[0];
                    this.mHashes = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    sTwiceBaseCacheSize--;
                    return;
                }
            }
        } else if (i == 4) {
            synchronized (ArraySet.class) {
                if (sBaseCache != null) {
                    Object[] objArr2 = sBaseCache;
                    this.a = objArr2;
                    sBaseCache = (Object[]) objArr2[0];
                    this.mHashes = (int[]) objArr2[1];
                    objArr2[1] = null;
                    objArr2[0] = null;
                    sBaseCacheSize--;
                    return;
                }
            }
        }
        this.mHashes = new int[i];
        this.a = new Object[i];
    }

    private static void freeArrays(int[] iArr, Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (ArraySet.class) {
                if (sTwiceBaseCacheSize < 10) {
                    objArr[0] = sTwiceBaseCache;
                    objArr[1] = iArr;
                    for (int i2 = i - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    sTwiceBaseCache = objArr;
                    sTwiceBaseCacheSize++;
                }
            }
            return;
        }
        if (iArr.length == 4) {
            synchronized (ArraySet.class) {
                if (sBaseCacheSize < 10) {
                    objArr[0] = sBaseCache;
                    objArr[1] = iArr;
                    for (int i3 = i - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    sBaseCache = objArr;
                    sBaseCacheSize++;
                }
            }
        }
    }

    private MapCollections<E, E> getCollection() {
        if (this.mCollections == null) {
            this.mCollections = new MapCollections<E, E>() { // from class: androidx.collection.ArraySet.1
                @Override // androidx.collection.MapCollections
                protected int a() {
                    return ArraySet.this.b;
                }

                @Override // androidx.collection.MapCollections
                protected int a(Object obj) {
                    return ArraySet.this.indexOf(obj);
                }

                @Override // androidx.collection.MapCollections
                protected Object a(int i, int i2) {
                    return ArraySet.this.a[i];
                }

                @Override // androidx.collection.MapCollections
                protected E a(int i, E e) {
                    throw new UnsupportedOperationException("not a map");
                }

                @Override // androidx.collection.MapCollections
                protected void a(int i) {
                    ArraySet.this.removeAt(i);
                }

                @Override // androidx.collection.MapCollections
                protected void a(E e, E e2) {
                    ArraySet.this.add(e);
                }

                @Override // androidx.collection.MapCollections
                protected int b(Object obj) {
                    return ArraySet.this.indexOf(obj);
                }

                @Override // androidx.collection.MapCollections
                protected Map<E, E> b() {
                    throw new UnsupportedOperationException("not a map");
                }

                @Override // androidx.collection.MapCollections
                protected void c() {
                    ArraySet.this.clear();
                }
            };
        }
        return this.mCollections;
    }

    private int indexOf(Object obj, int i) {
        int i2 = this.b;
        if (i2 == 0) {
            return -1;
        }
        int iA = ContainerHelpers.a(this.mHashes, i2, i);
        if (iA < 0 || obj.equals(this.a[iA])) {
            return iA;
        }
        int i3 = iA + 1;
        while (i3 < i2 && this.mHashes[i3] == i) {
            if (obj.equals(this.a[i3])) {
                return i3;
            }
            i3++;
        }
        for (int i4 = iA - 1; i4 >= 0 && this.mHashes[i4] == i; i4--) {
            if (obj.equals(this.a[i4])) {
                return i4;
            }
        }
        return ~i3;
    }

    private int indexOfNull() {
        int i = this.b;
        if (i == 0) {
            return -1;
        }
        int iA = ContainerHelpers.a(this.mHashes, i, 0);
        if (iA < 0 || this.a[iA] == null) {
            return iA;
        }
        int i2 = iA + 1;
        while (i2 < i && this.mHashes[i2] == 0) {
            if (this.a[i2] == null) {
                return i2;
            }
            i2++;
        }
        for (int i3 = iA - 1; i3 >= 0 && this.mHashes[i3] == 0; i3--) {
            if (this.a[i3] == null) {
                return i3;
            }
        }
        return ~i2;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean add(@Nullable E e) {
        int i;
        int iIndexOf;
        if (e == null) {
            iIndexOf = indexOfNull();
            i = 0;
        } else {
            int iHashCode = e.hashCode();
            i = iHashCode;
            iIndexOf = indexOf(e, iHashCode);
        }
        if (iIndexOf >= 0) {
            return false;
        }
        int i2 = ~iIndexOf;
        if (this.b >= this.mHashes.length) {
            int i3 = 4;
            if (this.b >= 8) {
                i3 = (this.b >> 1) + this.b;
            } else if (this.b >= 4) {
                i3 = 8;
            }
            int[] iArr = this.mHashes;
            Object[] objArr = this.a;
            allocArrays(i3);
            if (this.mHashes.length > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, iArr.length);
                System.arraycopy(objArr, 0, this.a, 0, objArr.length);
            }
            freeArrays(iArr, objArr, this.b);
        }
        if (i2 < this.b) {
            int i4 = i2 + 1;
            System.arraycopy(this.mHashes, i2, this.mHashes, i4, this.b - i2);
            System.arraycopy(this.a, i2, this.a, i4, this.b - i2);
        }
        this.mHashes[i2] = i;
        this.a[i2] = e;
        this.b++;
        return true;
    }

    public void addAll(@NonNull ArraySet<? extends E> arraySet) {
        int i = arraySet.b;
        ensureCapacity(this.b + i);
        if (this.b != 0) {
            for (int i2 = 0; i2 < i; i2++) {
                add(arraySet.valueAt(i2));
            }
        } else if (i > 0) {
            System.arraycopy(arraySet.mHashes, 0, this.mHashes, 0, i);
            System.arraycopy(arraySet.a, 0, this.a, 0, i);
            this.b = i;
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean addAll(@NonNull Collection<? extends E> collection) {
        ensureCapacity(this.b + collection.size());
        Iterator<? extends E> it = collection.iterator();
        boolean zAdd = false;
        while (it.hasNext()) {
            zAdd |= add(it.next());
        }
        return zAdd;
    }

    @Override // java.util.Collection, java.util.Set
    public void clear() {
        if (this.b != 0) {
            freeArrays(this.mHashes, this.a, this.b);
            this.mHashes = INT;
            this.a = OBJECT;
            this.b = 0;
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean contains(@Nullable Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean containsAll(@NonNull Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public void ensureCapacity(int i) {
        if (this.mHashes.length < i) {
            int[] iArr = this.mHashes;
            Object[] objArr = this.a;
            allocArrays(i);
            if (this.b > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, this.b);
                System.arraycopy(objArr, 0, this.a, 0, this.b);
            }
            freeArrays(iArr, objArr, this.b);
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            if (size() != set.size()) {
                return false;
            }
            for (int i = 0; i < this.b; i++) {
                try {
                    if (!set.contains(valueAt(i))) {
                        return false;
                    }
                } catch (ClassCastException | NullPointerException unused) {
                }
            }
            return true;
        }
        return false;
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int[] iArr = this.mHashes;
        int i = this.b;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += iArr[i3];
        }
        return i2;
    }

    public int indexOf(@Nullable Object obj) {
        return obj == null ? indexOfNull() : indexOf(obj, obj.hashCode());
    }

    @Override // java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.b <= 0;
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<E> iterator() {
        return getCollection().getKeySet().iterator();
    }

    @Override // java.util.Collection, java.util.Set
    public boolean remove(@Nullable Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf < 0) {
            return false;
        }
        removeAt(iIndexOf);
        return true;
    }

    public boolean removeAll(@NonNull ArraySet<? extends E> arraySet) {
        int i = arraySet.b;
        int i2 = this.b;
        for (int i3 = 0; i3 < i; i3++) {
            remove(arraySet.valueAt(i3));
        }
        return i2 != this.b;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean removeAll(@NonNull Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        boolean zRemove = false;
        while (it.hasNext()) {
            zRemove |= remove(it.next());
        }
        return zRemove;
    }

    public E removeAt(int i) {
        E e = (E) this.a[i];
        if (this.b <= 1) {
            freeArrays(this.mHashes, this.a, this.b);
            this.mHashes = INT;
            this.a = OBJECT;
            this.b = 0;
        } else {
            int i2 = 8;
            if (this.mHashes.length <= 8 || this.b >= this.mHashes.length / 3) {
                this.b--;
                if (i < this.b) {
                    int i3 = i + 1;
                    System.arraycopy(this.mHashes, i3, this.mHashes, i, this.b - i);
                    System.arraycopy(this.a, i3, this.a, i, this.b - i);
                }
                this.a[this.b] = null;
            } else {
                if (this.b > 8) {
                    i2 = (this.b >> 1) + this.b;
                }
                int[] iArr = this.mHashes;
                Object[] objArr = this.a;
                allocArrays(i2);
                this.b--;
                if (i > 0) {
                    System.arraycopy(iArr, 0, this.mHashes, 0, i);
                    System.arraycopy(objArr, 0, this.a, 0, i);
                }
                if (i < this.b) {
                    int i4 = i + 1;
                    System.arraycopy(iArr, i4, this.mHashes, i, this.b - i);
                    System.arraycopy(objArr, i4, this.a, i, this.b - i);
                }
            }
        }
        return e;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean retainAll(@NonNull Collection<?> collection) {
        boolean z = false;
        for (int i = this.b - 1; i >= 0; i--) {
            if (!collection.contains(this.a[i])) {
                removeAt(i);
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.Collection, java.util.Set
    public int size() {
        return this.b;
    }

    @Override // java.util.Collection, java.util.Set
    @NonNull
    public Object[] toArray() {
        Object[] objArr = new Object[this.b];
        System.arraycopy(this.a, 0, objArr, 0, this.b);
        return objArr;
    }

    @Override // java.util.Collection, java.util.Set
    @NonNull
    public <T> T[] toArray(@NonNull T[] tArr) {
        if (tArr.length < this.b) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), this.b));
        }
        System.arraycopy(this.a, 0, tArr, 0, this.b);
        if (tArr.length > this.b) {
            tArr[this.b] = null;
        }
        return tArr;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.b * 14);
        sb.append('{');
        for (int i = 0; i < this.b; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            E eValueAt = valueAt(i);
            if (eValueAt != this) {
                sb.append(eValueAt);
            } else {
                sb.append("(this Set)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    @Nullable
    public E valueAt(int i) {
        return (E) this.a[i];
    }
}
