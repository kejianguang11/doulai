package org.cocos2dx.javascript;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public class Util {
    public static final int IMAGE_SIZE = 32768;
    private static final int MAX_DECODE_PICTURE_SIZE = 2764800;
    private static final String TAG = "WeixinSDK.Util";

    public static byte[] bmpToByteArray(Bitmap bitmap, boolean z) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
        int i = 100;
        while (true) {
            bitmap.compress(compressFormat, i, byteArrayOutputStream);
            if (byteArrayOutputStream.toByteArray().length <= 32768 || i <= 10) {
                break;
            }
            i -= 10;
            byteArrayOutputStream.reset();
            compressFormat = Bitmap.CompressFormat.JPEG;
        }
        if (z) {
            bitmap.recycle();
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    public static Bitmap extractThumbNail(String str, int i, int i2, boolean z) {
        double d;
        double d2;
        int i3;
        double d3;
        int i4;
        int i5;
        int i6;
        Bitmap bitmapCreateBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            options.inJustDecodeBounds = true;
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str, options);
            if (bitmapDecodeFile != null) {
                bitmapDecodeFile.recycle();
            }
            Log.d(TAG, "extractThumbNail: round=" + i2 + "x" + i + ", crop=" + z);
            double d4 = (double) i;
            double d5 = (((double) options.outHeight) * 1.0d) / d4;
            double d6 = (double) i2;
            double d7 = (((double) options.outWidth) * 1.0d) / d6;
            Log.d(TAG, "extractThumbNail: extract beX = " + d7 + ", beY = " + d5);
            if (z) {
                if (d5 > d7) {
                }
            } else {
                d = d5 < d7 ? d7 : d5;
            }
            options.inSampleSize = (int) d;
            if (options.inSampleSize <= 1) {
                options.inSampleSize = 1;
            }
            while ((options.outHeight * options.outWidth) / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
                options.inSampleSize++;
            }
            if (z) {
                if (d5 > d7) {
                    d3 = d6 * 1.0d;
                    i4 = options.outHeight;
                    i6 = (int) ((d3 * ((double) i4)) / ((double) options.outWidth));
                    i5 = i2;
                } else {
                    d2 = d4 * 1.0d;
                    i3 = options.outWidth;
                    i5 = (int) ((d2 * ((double) i3)) / ((double) options.outHeight));
                    i6 = i;
                }
            } else if (d5 < d7) {
                d3 = d6 * 1.0d;
                i4 = options.outHeight;
                i6 = (int) ((d3 * ((double) i4)) / ((double) options.outWidth));
                i5 = i2;
            } else {
                d2 = d4 * 1.0d;
                i3 = options.outWidth;
                i5 = (int) ((d2 * ((double) i3)) / ((double) options.outHeight));
                i6 = i;
            }
            options.inJustDecodeBounds = false;
            Log.i(TAG, "bitmap required size=" + i5 + "x" + i6 + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);
            Bitmap bitmapDecodeFile2 = BitmapFactory.decodeFile(str, options);
            if (bitmapDecodeFile2 == null) {
                Log.e(TAG, "bitmap decode failed");
                return null;
            }
            Log.i(TAG, "bitmap decoded size=" + bitmapDecodeFile2.getWidth() + "x" + bitmapDecodeFile2.getHeight());
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapDecodeFile2, i5, i6, true);
            if (bitmapCreateScaledBitmap != null) {
                bitmapDecodeFile2.recycle();
                bitmapDecodeFile2 = bitmapCreateScaledBitmap;
            }
            if (!z || (bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeFile2, (bitmapDecodeFile2.getWidth() - i2) >> 1, (bitmapDecodeFile2.getHeight() - i) >> 1, i2, i)) == null) {
                return bitmapDecodeFile2;
            }
            bitmapDecodeFile2.recycle();
            Log.i(TAG, "bitmap croped size=" + bitmapCreateBitmap.getWidth() + "x" + bitmapCreateBitmap.getHeight());
            return bitmapCreateBitmap;
        } catch (OutOfMemoryError e) {
            Log.e(TAG, "decode bitmap failed: " + e.getMessage());
            return null;
        }
    }

    public static byte[] getHtmlByteArray(String str) {
        InputStream inputStream = null;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return inputStreamToByte(inputStream);
    }

    public static byte[] inputStreamToByte(InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int i = inputStream.read();
                if (i == -1) {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return byteArray;
                }
                byteArrayOutputStream.write(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] readFromFile(String str, int i, int i2) {
        byte[] bArr;
        if (str == null) {
            return null;
        }
        File file = new File(str);
        if (!file.exists()) {
            Log.i(TAG, "readFromFile: file not found");
            return null;
        }
        if (i2 == -1) {
            i2 = (int) file.length();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("readFromFile : offset = ");
        sb.append(i);
        sb.append(" len = ");
        sb.append(i2);
        sb.append(" offset + len = ");
        int i3 = i + i2;
        sb.append(i3);
        Log.d(TAG, sb.toString());
        if (i < 0) {
            Log.e(TAG, "readFromFile invalid offset:" + i);
            return null;
        }
        if (i2 <= 0) {
            Log.e(TAG, "readFromFile invalid len:" + i2);
            return null;
        }
        if (i3 > ((int) file.length())) {
            Log.e(TAG, "readFromFile invalid file len:" + file.length());
            return null;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
            bArr = new byte[i2];
            try {
                randomAccessFile.seek(i);
                randomAccessFile.readFully(bArr);
                randomAccessFile.close();
            } catch (Exception e) {
                e = e;
                Log.e(TAG, "readFromFile : errMsg = " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e = e2;
            bArr = null;
        }
        return bArr;
    }
}
