package com.qygame.qzdlyx.mobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import com.igexin.push.core.b;
import java.io.File;
import java.io.FileOutputStream;
import org.cocos2dx.javascript.JSBridgeHelper;

/* JADX INFO: loaded from: classes.dex */
public class ModifyImageActivity extends Activity {
    private static final int MY_ADD_CASE_CALL_PHONE = 7;
    public static String chooseHeadImage = "chooseHeadImage";
    public static String imageName = "";
    public static String selectPhoto = "selectPhoto";
    public static Boolean noScale = false;
    public static String TAG = "ModifyImageActivity";

    private void choosePhoto() {
        startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 2);
    }

    protected Bitmap a(Bitmap bitmap) {
        float f;
        float f2;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (!noScale.booleanValue()) {
            float f3 = b.as;
            float f4 = f3 / width;
            f = f3 / height;
            if (f <= f4) {
                f = f4;
            }
        } else if (width > height) {
            if (width > 1600) {
                f2 = width;
                f = 1600.1f / f2;
            }
            f = 1.0f;
        } else {
            if (height > 1600) {
                f2 = height;
                f = 1600.1f / f2;
            }
            f = 1.0f;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(f, f);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    protected void a() {
        Log.i(TAG, "chooseHeadImage no scale = false");
        noScale = false;
        choosePhoto();
    }

    protected void b() {
        Log.i(TAG, "selectPhoto no scale = true");
        noScale = true;
        choosePhoto();
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && intent != null) {
            Uri data = intent.getData();
            try {
                Log.e(TAG, "保存图片");
                Bitmap bitmapA = a(MediaStore.Images.Media.getBitmap(getContentResolver(), data));
                bitmapA.getWidth();
                bitmapA.getHeight();
                File file = new File(imageName);
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmapA.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Log.i(TAG, "已经保存");
                if (noScale.booleanValue()) {
                    Log.i(TAG, "JSBridgeHelper.onSelectPhotoCallback is run");
                    JSBridgeHelper.onSelectPhotoCallback(true, null);
                } else {
                    JSBridgeHelper.changeHeadImageCallback(true, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finish();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent.hasExtra(chooseHeadImage)) {
            imageName = intent.getStringExtra("imageName");
            a();
        }
        if (intent.hasExtra(selectPhoto)) {
            imageName = intent.getStringExtra("imageName");
            b();
        }
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 7) {
            if (iArr[0] == 0) {
                choosePhoto();
            } else if (noScale.booleanValue()) {
                JSBridgeHelper.onSelectPhotoCallback(false, null);
            } else {
                JSBridgeHelper.changeHeadImageCallback(false, null);
            }
        }
        super.onRequestPermissionsResult(i, strArr, iArr);
    }
}
