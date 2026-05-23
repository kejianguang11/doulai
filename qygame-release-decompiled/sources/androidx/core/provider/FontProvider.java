package androidx.core.provider;

import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import com.android.vending.expansion.zipfile.APEZProvider;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class FontProvider {
    private static final Comparator<byte[]> sByteArrayComparator = new Comparator<byte[]>() { // from class: androidx.core.provider.FontProvider.1
        @Override // java.util.Comparator
        public int compare(byte[] bArr, byte[] bArr2) {
            int length;
            int length2;
            if (bArr.length == bArr2.length) {
                for (int i = 0; i < bArr.length; i++) {
                    if (bArr[i] != bArr2[i]) {
                        length = bArr[i];
                        length2 = bArr2[i];
                    }
                }
                return 0;
            }
            length = bArr.length;
            length2 = bArr2.length;
            return length - length2;
        }
    };

    private FontProvider() {
    }

    @Nullable
    @VisibleForTesting
    static ProviderInfo a(@NonNull PackageManager packageManager, @NonNull FontRequest fontRequest, @Nullable Resources resources) throws PackageManager.NameNotFoundException {
        String providerAuthority = fontRequest.getProviderAuthority();
        ProviderInfo providerInfoResolveContentProvider = packageManager.resolveContentProvider(providerAuthority, 0);
        if (providerInfoResolveContentProvider == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + providerAuthority);
        }
        if (!providerInfoResolveContentProvider.packageName.equals(fontRequest.getProviderPackage())) {
            throw new PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + fontRequest.getProviderPackage());
        }
        List<byte[]> listConvertToByteArrayList = convertToByteArrayList(packageManager.getPackageInfo(providerInfoResolveContentProvider.packageName, 64).signatures);
        Collections.sort(listConvertToByteArrayList, sByteArrayComparator);
        List<List<byte[]>> certificates = getCertificates(fontRequest, resources);
        for (int i = 0; i < certificates.size(); i++) {
            ArrayList arrayList = new ArrayList(certificates.get(i));
            Collections.sort(arrayList, sByteArrayComparator);
            if (equalsByteArrayList(listConvertToByteArrayList, arrayList)) {
                return providerInfoResolveContentProvider;
            }
        }
        return null;
    }

    @NonNull
    static FontsContractCompat.FontFamilyResult a(@NonNull Context context, @NonNull FontRequest fontRequest, @Nullable CancellationSignal cancellationSignal) throws PackageManager.NameNotFoundException {
        ProviderInfo providerInfoA = a(context.getPackageManager(), fontRequest, context.getResources());
        return providerInfoA == null ? FontsContractCompat.FontFamilyResult.a(1, null) : FontsContractCompat.FontFamilyResult.a(0, a(context, fontRequest, providerInfoA.authority, cancellationSignal));
    }

    @NonNull
    @VisibleForTesting
    static FontsContractCompat.FontInfo[] a(Context context, FontRequest fontRequest, String str, CancellationSignal cancellationSignal) {
        int i;
        Uri uriWithAppendedId;
        int i2;
        ArrayList arrayList = new ArrayList();
        Uri uriBuild = new Uri.Builder().scheme(DBHelpTool.RecordEntry.COLUMN_NAME_CONTENT).authority(str).build();
        Uri uriBuild2 = new Uri.Builder().scheme(DBHelpTool.RecordEntry.COLUMN_NAME_CONTENT).authority(str).appendPath("file").build();
        Cursor cursorQuery = null;
        try {
            String[] strArr = {APEZProvider.FILEID, FontsContractCompat.Columns.FILE_ID, FontsContractCompat.Columns.TTC_INDEX, FontsContractCompat.Columns.VARIATION_SETTINGS, FontsContractCompat.Columns.WEIGHT, FontsContractCompat.Columns.ITALIC, FontsContractCompat.Columns.RESULT_CODE};
            boolean z = true;
            int i3 = 0;
            cursorQuery = Build.VERSION.SDK_INT > 16 ? context.getContentResolver().query(uriBuild, strArr, "query = ?", new String[]{fontRequest.getQuery()}, null, cancellationSignal) : context.getContentResolver().query(uriBuild, strArr, "query = ?", new String[]{fontRequest.getQuery()}, null);
            if (cursorQuery != null && cursorQuery.getCount() > 0) {
                int columnIndex = cursorQuery.getColumnIndex(FontsContractCompat.Columns.RESULT_CODE);
                ArrayList arrayList2 = new ArrayList();
                int columnIndex2 = cursorQuery.getColumnIndex(APEZProvider.FILEID);
                int columnIndex3 = cursorQuery.getColumnIndex(FontsContractCompat.Columns.FILE_ID);
                int columnIndex4 = cursorQuery.getColumnIndex(FontsContractCompat.Columns.TTC_INDEX);
                int columnIndex5 = cursorQuery.getColumnIndex(FontsContractCompat.Columns.WEIGHT);
                int columnIndex6 = cursorQuery.getColumnIndex(FontsContractCompat.Columns.ITALIC);
                while (cursorQuery.moveToNext()) {
                    int i4 = columnIndex != -1 ? cursorQuery.getInt(columnIndex) : i3;
                    int i5 = columnIndex4 != -1 ? cursorQuery.getInt(columnIndex4) : i3;
                    if (columnIndex3 == -1) {
                        i = i4;
                        uriWithAppendedId = ContentUris.withAppendedId(uriBuild, cursorQuery.getLong(columnIndex2));
                    } else {
                        i = i4;
                        uriWithAppendedId = ContentUris.withAppendedId(uriBuild2, cursorQuery.getLong(columnIndex3));
                    }
                    int i6 = columnIndex5 != -1 ? cursorQuery.getInt(columnIndex5) : 400;
                    if (columnIndex6 == -1 || cursorQuery.getInt(columnIndex6) != z) {
                        i2 = i;
                        z = false;
                    } else {
                        i2 = i;
                    }
                    arrayList2.add(FontsContractCompat.FontInfo.a(uriWithAppendedId, i5, i6, z, i2));
                    z = true;
                    i3 = 0;
                }
                arrayList = arrayList2;
            }
            return (FontsContractCompat.FontInfo[]) arrayList.toArray(new FontsContractCompat.FontInfo[0]);
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    private static List<byte[]> convertToByteArrayList(Signature[] signatureArr) {
        ArrayList arrayList = new ArrayList();
        for (Signature signature : signatureArr) {
            arrayList.add(signature.toByteArray());
        }
        return arrayList;
    }

    private static boolean equalsByteArrayList(List<byte[]> list, List<byte[]> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!Arrays.equals(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<List<byte[]>> getCertificates(FontRequest fontRequest, Resources resources) {
        return fontRequest.getCertificates() != null ? fontRequest.getCertificates() : FontResourcesParserCompat.readCerts(resources, fontRequest.getCertificatesArrayResId());
    }
}
