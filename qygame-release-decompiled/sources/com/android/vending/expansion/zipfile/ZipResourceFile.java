package com.android.vending.expansion.zipfile;

import android.content.res.AssetFileDescriptor;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.core.internal.view.SupportMenu;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* JADX INFO: loaded from: classes.dex */
public class ZipResourceFile {
    private HashMap<String, ZipEntryRO> mHashMap = new HashMap<>();
    public HashMap<File, ZipFile> mZipFiles = new HashMap<>();
    ByteBuffer a = ByteBuffer.allocate(4);

    public static final class ZipEntryRO {
        public long mCRC32;
        public long mCompressedLength;
        public final File mFile;
        public final String mFileName;
        public long mLocalHdrOffset;
        public int mMethod;
        public long mOffset = -1;
        public long mUncompressedLength;
        public long mWhenModified;
        public final String mZipFileName;

        public ZipEntryRO(String str, File file, String str2) {
            this.mFileName = str2;
            this.mZipFileName = str;
            this.mFile = file;
        }

        public AssetFileDescriptor getAssetFileDescriptor() {
            if (this.mMethod != 0) {
                return null;
            }
            try {
                return new AssetFileDescriptor(ParcelFileDescriptor.open(this.mFile, 268435456), getOffset(), this.mUncompressedLength);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }

        public long getOffset() {
            return this.mOffset;
        }

        public File getZipFile() {
            return this.mFile;
        }

        public String getZipFileName() {
            return this.mZipFileName;
        }

        public boolean isUncompressed() {
            return this.mMethod == 0;
        }

        public void setOffsetFromFile(RandomAccessFile randomAccessFile, ByteBuffer byteBuffer) throws IOException {
            long j = this.mLocalHdrOffset;
            try {
                randomAccessFile.seek(j);
                randomAccessFile.readFully(byteBuffer.array());
                if (byteBuffer.getInt(0) != 67324752) {
                    Log.w("zipro", "didn't find signature at start of lfh");
                    throw new IOException();
                }
                this.mOffset = j + 30 + ((long) (byteBuffer.getShort(26) & 65535)) + ((long) (byteBuffer.getShort(28) & 65535));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public ZipResourceFile(String str) throws IOException {
        a(str);
    }

    private static int read4LE(RandomAccessFile randomAccessFile) throws IOException {
        return swapEndian(randomAccessFile.readInt());
    }

    private static int swapEndian(int i) {
        return ((i & 255) << 24) + ((65280 & i) << 8) + ((16711680 & i) >>> 8) + ((i >>> 24) & 255);
    }

    private static int swapEndian(short s) {
        return ((s & 65280) >>> 8) | ((s & 255) << 8);
    }

    void a(String str) throws IOException {
        File file = new File(str);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        long length = randomAccessFile.length();
        if (length < 22) {
            throw new IOException();
        }
        long j = 65557 > length ? length : 65557L;
        randomAccessFile.seek(0L);
        int i = read4LE(randomAccessFile);
        if (i == 101010256) {
            Log.i("zipro", "Found Zip archive, but it looks empty");
            throw new IOException();
        }
        if (i != 67324752) {
            Log.v("zipro", "Not a Zip archive");
            throw new IOException();
        }
        randomAccessFile.seek(length - j);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate((int) j);
        byte[] bArrArray = byteBufferAllocate.array();
        randomAccessFile.readFully(bArrArray);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        int length2 = bArrArray.length - 22;
        while (length2 >= 0 && (bArrArray[length2] != 80 || byteBufferAllocate.getInt(length2) != 101010256)) {
            length2--;
        }
        if (length2 < 0) {
            Log.d("zipro", "Zip: EOCD not found, " + str + " is not zip");
        }
        short s = byteBufferAllocate.getShort(length2 + 8);
        long j2 = ((long) byteBufferAllocate.getInt(length2 + 12)) & 4294967295L;
        long j3 = ((long) byteBufferAllocate.getInt(length2 + 16)) & 4294967295L;
        if (j3 + j2 > length) {
            Log.w("zipro", "bad offsets (dir " + j3 + ", size " + j2 + ", eocd " + length2 + ")");
            throw new IOException();
        }
        if (s == 0) {
            Log.w("zipro", "empty archive?");
            throw new IOException();
        }
        MappedByteBuffer map = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, j3, j2);
        map.order(ByteOrder.LITTLE_ENDIAN);
        short s2 = 65535;
        byte[] bArr = new byte[SupportMenu.USER_MASK];
        ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(30);
        byteBufferAllocate2.order(ByteOrder.LITTLE_ENDIAN);
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i3 < s) {
            if (map.getInt(i4) != 33639248) {
                Log.w("zipro", "Missed a central dir sig (at " + i4 + ")");
                throw new IOException();
            }
            int i5 = map.getShort(i4 + 28) & s2;
            int i6 = map.getShort(i4 + 30) & s2;
            int i7 = map.getShort(i4 + 32) & s2;
            map.position(i4 + 46);
            map.get(bArr, i2, i5);
            map.position(i2);
            String str2 = new String(bArr, i2, i5);
            ZipEntryRO zipEntryRO = new ZipEntryRO(str, file, str2);
            zipEntryRO.mMethod = map.getShort(i4 + 10) & s2;
            zipEntryRO.mWhenModified = ((long) map.getInt(i4 + 12)) & 4294967295L;
            zipEntryRO.mCRC32 = map.getLong(i4 + 16) & 4294967295L;
            zipEntryRO.mCompressedLength = map.getLong(i4 + 20) & 4294967295L;
            zipEntryRO.mUncompressedLength = map.getLong(i4 + 24) & 4294967295L;
            zipEntryRO.mLocalHdrOffset = ((long) map.getInt(i4 + 42)) & 4294967295L;
            byteBufferAllocate2.clear();
            zipEntryRO.setOffsetFromFile(randomAccessFile, byteBufferAllocate2);
            this.mHashMap.put(str2, zipEntryRO);
            i4 += i5 + 46 + i6 + i7;
            i3++;
            bArr = bArr;
            s2 = 65535;
            i2 = 0;
        }
    }

    public ZipEntryRO[] getAllEntries() {
        Collection<ZipEntryRO> collectionValues = this.mHashMap.values();
        return (ZipEntryRO[]) collectionValues.toArray(new ZipEntryRO[collectionValues.size()]);
    }

    public AssetFileDescriptor getAssetFileDescriptor(String str) {
        ZipEntryRO zipEntryRO = this.mHashMap.get(str);
        if (zipEntryRO != null) {
            return zipEntryRO.getAssetFileDescriptor();
        }
        return null;
    }

    public InputStream getInputStream(String str) throws IOException {
        ZipEntryRO zipEntryRO = this.mHashMap.get(str);
        if (zipEntryRO == null) {
            return null;
        }
        if (zipEntryRO.isUncompressed()) {
            return zipEntryRO.getAssetFileDescriptor().createInputStream();
        }
        ZipFile zipFile = this.mZipFiles.get(zipEntryRO.getZipFile());
        if (zipFile == null) {
            zipFile = new ZipFile(zipEntryRO.getZipFile(), 1);
            this.mZipFiles.put(zipEntryRO.getZipFile(), zipFile);
        }
        ZipEntry entry = zipFile.getEntry(str);
        if (entry != null) {
            return zipFile.getInputStream(entry);
        }
        return null;
    }
}
