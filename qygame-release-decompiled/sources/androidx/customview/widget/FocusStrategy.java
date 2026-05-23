package androidx.customview.widget;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
class FocusStrategy {

    public interface BoundsAdapter<T> {
        void obtainBounds(T t, Rect rect);
    }

    public interface CollectionAdapter<T, V> {
        V get(T t, int i);

        int size(T t);
    }

    private static class SequentialComparator<T> implements Comparator<T> {
        private final BoundsAdapter<T> mAdapter;
        private final boolean mIsLayoutRtl;
        private final Rect mTemp1 = new Rect();
        private final Rect mTemp2 = new Rect();

        SequentialComparator(boolean z, BoundsAdapter<T> boundsAdapter) {
            this.mIsLayoutRtl = z;
            this.mAdapter = boundsAdapter;
        }

        @Override // java.util.Comparator
        public int compare(T t, T t2) {
            Rect rect = this.mTemp1;
            Rect rect2 = this.mTemp2;
            this.mAdapter.obtainBounds(t, rect);
            this.mAdapter.obtainBounds(t2, rect2);
            if (rect.top < rect2.top) {
                return -1;
            }
            if (rect.top > rect2.top) {
                return 1;
            }
            if (rect.left < rect2.left) {
                return this.mIsLayoutRtl ? 1 : -1;
            }
            if (rect.left > rect2.left) {
                return this.mIsLayoutRtl ? -1 : 1;
            }
            if (rect.bottom < rect2.bottom) {
                return -1;
            }
            if (rect.bottom > rect2.bottom) {
                return 1;
            }
            if (rect.right < rect2.right) {
                return this.mIsLayoutRtl ? 1 : -1;
            }
            if (rect.right > rect2.right) {
                return this.mIsLayoutRtl ? -1 : 1;
            }
            return 0;
        }
    }

    private FocusStrategy() {
    }

    private static boolean beamBeats(int i, @NonNull Rect rect, @NonNull Rect rect2, @NonNull Rect rect3) {
        boolean zBeamsOverlap = beamsOverlap(i, rect, rect2);
        if (beamsOverlap(i, rect, rect3) || !zBeamsOverlap) {
            return false;
        }
        return !isToDirectionOf(i, rect, rect3) || i == 17 || i == 66 || majorAxisDistance(i, rect, rect2) < majorAxisDistanceToFarEdge(i, rect, rect3);
    }

    private static boolean beamsOverlap(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        if (i != 17) {
            if (i != 33) {
                if (i != 66) {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                }
            }
            return rect2.right >= rect.left && rect2.left <= rect.right;
        }
        return rect2.bottom >= rect.top && rect2.top <= rect.bottom;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static <L, T> T findNextFocusInAbsoluteDirection(@NonNull L l, @NonNull CollectionAdapter<L, T> collectionAdapter, @NonNull BoundsAdapter<T> boundsAdapter, @Nullable T t, @NonNull Rect rect, int i) {
        int iWidth;
        int size;
        int iHeight;
        Rect rect2 = new Rect(rect);
        if (i != 17) {
            if (i == 33) {
                iHeight = rect.height() + 1;
            } else if (i == 66) {
                iWidth = -(rect.width() + 1);
            } else {
                if (i != 130) {
                    throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                }
                iHeight = -(rect.height() + 1);
            }
            rect2.offset(0, iHeight);
            T t2 = null;
            size = collectionAdapter.size(l);
            Rect rect3 = new Rect();
            for (int i2 = 0; i2 < size; i2++) {
                T t3 = collectionAdapter.get(l, i2);
                if (t3 != t) {
                    boundsAdapter.obtainBounds(t3, rect3);
                    if (isBetterCandidate(i, rect, rect3, rect2)) {
                        rect2.set(rect3);
                        t2 = t3;
                    }
                }
            }
            return t2;
        }
        iWidth = rect.width() + 1;
        rect2.offset(iWidth, 0);
        T t22 = null;
        size = collectionAdapter.size(l);
        Rect rect32 = new Rect();
        while (i2 < size) {
        }
        return t22;
    }

    public static <L, T> T findNextFocusInRelativeDirection(@NonNull L l, @NonNull CollectionAdapter<L, T> collectionAdapter, @NonNull BoundsAdapter<T> boundsAdapter, @Nullable T t, int i, boolean z, boolean z2) {
        int size = collectionAdapter.size(l);
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            arrayList.add(collectionAdapter.get(l, i2));
        }
        Collections.sort(arrayList, new SequentialComparator(z, boundsAdapter));
        switch (i) {
            case 1:
                return (T) getPreviousFocusable(t, arrayList, z2);
            case 2:
                return (T) getNextFocusable(t, arrayList, z2);
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD}.");
        }
    }

    private static <T> T getNextFocusable(T t, ArrayList<T> arrayList, boolean z) {
        int size = arrayList.size();
        int iLastIndexOf = (t == null ? -1 : arrayList.lastIndexOf(t)) + 1;
        if (iLastIndexOf < size) {
            return arrayList.get(iLastIndexOf);
        }
        if (!z || size <= 0) {
            return null;
        }
        return arrayList.get(0);
    }

    private static <T> T getPreviousFocusable(T t, ArrayList<T> arrayList, boolean z) {
        int size = arrayList.size();
        int iIndexOf = (t == null ? size : arrayList.indexOf(t)) - 1;
        if (iIndexOf >= 0) {
            return arrayList.get(iIndexOf);
        }
        if (!z || size <= 0) {
            return null;
        }
        return arrayList.get(size - 1);
    }

    private static int getWeightedDistanceFor(int i, int i2) {
        return (i * 13 * i) + (i2 * i2);
    }

    private static boolean isBetterCandidate(int i, @NonNull Rect rect, @NonNull Rect rect2, @NonNull Rect rect3) {
        if (!isCandidate(rect, rect2, i)) {
            return false;
        }
        if (isCandidate(rect, rect3, i) && !beamBeats(i, rect, rect2, rect3)) {
            return !beamBeats(i, rect, rect3, rect2) && getWeightedDistanceFor(majorAxisDistance(i, rect, rect2), minorAxisDistance(i, rect, rect2)) < getWeightedDistanceFor(majorAxisDistance(i, rect, rect3), minorAxisDistance(i, rect, rect3));
        }
        return true;
    }

    private static boolean isCandidate(@NonNull Rect rect, @NonNull Rect rect2, int i) {
        if (i == 17) {
            return (rect.right > rect2.right || rect.left >= rect2.right) && rect.left > rect2.left;
        }
        if (i == 33) {
            return (rect.bottom > rect2.bottom || rect.top >= rect2.bottom) && rect.top > rect2.top;
        }
        if (i == 66) {
            return (rect.left < rect2.left || rect.right <= rect2.left) && rect.right < rect2.right;
        }
        if (i == 130) {
            return (rect.top < rect2.top || rect.bottom <= rect2.top) && rect.bottom < rect2.bottom;
        }
        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
    }

    private static boolean isToDirectionOf(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        if (i == 17) {
            return rect.left >= rect2.right;
        }
        if (i == 33) {
            return rect.top >= rect2.bottom;
        }
        if (i == 66) {
            return rect.right <= rect2.left;
        }
        if (i == 130) {
            return rect.bottom <= rect2.top;
        }
        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
    }

    private static int majorAxisDistance(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        return Math.max(0, majorAxisDistanceRaw(i, rect, rect2));
    }

    private static int majorAxisDistanceRaw(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        int i2;
        int i3;
        if (i == 17) {
            i2 = rect.left;
            i3 = rect2.right;
        } else if (i == 33) {
            i2 = rect.top;
            i3 = rect2.bottom;
        } else if (i == 66) {
            i2 = rect2.left;
            i3 = rect.right;
        } else {
            if (i != 130) {
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
            }
            i2 = rect2.top;
            i3 = rect.bottom;
        }
        return i2 - i3;
    }

    private static int majorAxisDistanceToFarEdge(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        return Math.max(1, majorAxisDistanceToFarEdgeRaw(i, rect, rect2));
    }

    private static int majorAxisDistanceToFarEdgeRaw(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        int i2;
        int i3;
        if (i == 17) {
            i2 = rect.left;
            i3 = rect2.left;
        } else if (i == 33) {
            i2 = rect.top;
            i3 = rect2.top;
        } else if (i == 66) {
            i2 = rect2.right;
            i3 = rect.right;
        } else {
            if (i != 130) {
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
            }
            i2 = rect2.bottom;
            i3 = rect.bottom;
        }
        return i2 - i3;
    }

    private static int minorAxisDistance(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        int iHeight;
        int i2;
        int iHeight2;
        if (i == 17) {
            iHeight = rect.top + (rect.height() / 2);
            i2 = rect2.top;
            iHeight2 = rect2.height();
        } else {
            if (i != 33) {
                if (i != 66) {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                }
                iHeight = rect.top + (rect.height() / 2);
                i2 = rect2.top;
                iHeight2 = rect2.height();
            }
            iHeight = rect.left + (rect.width() / 2);
            i2 = rect2.left;
            iHeight2 = rect2.width();
        }
        return Math.abs(iHeight - (i2 + (iHeight2 / 2)));
    }
}
