package com.gme.liteav.base.util;

import android.net.Uri;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.annotations.JNINamespace;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav")
public class UrlReader {
    private static final int AVSEEK_SIZE = 65536;
    private static final int SEEK_CUR = 1;
    private static final int SEEK_END = 2;
    private static final int SEEK_SET = 0;
    private static final String TAG = "UrlReader";
    private int mFileSize;
    private long mOffset;
    private InputStream mStream;
    private Uri mUri;

    public UrlReader(String str) {
        this.mUri = Uri.parse(str);
        open();
    }

    private void open() {
        try {
            this.mStream = ContextUtils.getApplicationContext().getContentResolver().openInputStream(this.mUri);
            this.mFileSize = this.mStream.available();
        } catch (FileNotFoundException unused) {
            Log.e(TAG, "Fail to open uri " + this.mUri.toString(), new Object[0]);
            this.mStream = null;
        } catch (IOException e) {
            Log.e(TAG, "Fail to get file size " + e.getMessage(), new Object[0]);
            this.mStream = null;
        }
    }

    private long seekFromBegin(long j) {
        if (j < 0) {
            return -1L;
        }
        close();
        open();
        if (this.mStream == null) {
            return -1L;
        }
        try {
            this.mOffset = this.mStream.skip(j);
            return this.mOffset;
        } catch (IOException e) {
            Log.e(TAG, "Fail to seek " + j + " exception " + e.getMessage(), new Object[0]);
            return -1L;
        }
    }

    private long seekFromCurrent(long j) {
        if (j < 0) {
            return seekFromBegin(this.mOffset + j);
        }
        try {
            this.mOffset += this.mStream.skip(j);
            return this.mOffset;
        } catch (IOException e) {
            Log.e(TAG, "Fail to seek " + j + " exception " + e.getMessage(), new Object[0]);
            return -1L;
        }
    }

    private long seekFromEnd(long j) {
        if (j > 0) {
            return -1L;
        }
        long j2 = ((long) this.mFileSize) + j;
        if (j2 < 0) {
            return -1L;
        }
        if (j2 < this.mOffset) {
            return seekFromBegin(j2);
        }
        try {
            this.mOffset += this.mStream.skip(j2 - this.mOffset);
            return this.mOffset;
        } catch (IOException e) {
            Log.e(TAG, "Fail to seek " + j + " exception " + e.getMessage(), new Object[0]);
            return -1L;
        }
    }

    public void close() {
        if (this.mStream != null) {
            try {
                this.mStream.close();
            } catch (IOException e) {
                Log.e(TAG, "Close exception " + e.getMessage(), new Object[0]);
            }
        }
        this.mStream = null;
        this.mOffset = 0L;
        this.mFileSize = 0;
    }

    public int read(byte[] bArr, int i) {
        int i2;
        if (this.mStream == null) {
            return -1;
        }
        try {
            i2 = this.mStream.read(bArr, 0, i);
        } catch (IOException e) {
            e = e;
            i2 = -1;
        }
        try {
            this.mOffset += (long) i2;
        } catch (IOException e2) {
            e = e2;
            Log.e(TAG, "Read exception " + e.getMessage(), new Object[0]);
        }
        return i2;
    }

    public long seek(long j, int i) {
        if (this.mStream == null) {
            return -1L;
        }
        if (i == 65536) {
            return this.mFileSize;
        }
        switch (i) {
            case 0:
                return seekFromBegin(j);
            case 1:
                return seekFromCurrent(j);
            case 2:
                return seekFromEnd(j);
            default:
                return -1L;
        }
    }
}
