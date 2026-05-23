package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

/* JADX INFO: loaded from: classes.dex */
public class WXGameVideoFileObject implements WXMediaMessage.IMediaObject {
    private static final int FILE_SIZE_LIMIT = 104857600;
    private static final String TAG = "MicroMsg.SDK.WXGameVideoFileObject";
    private static final int URL_LENGTH_LIMIT = 10240;
    public String filePath;
    public String thumbUrl;
    public String videoUrl;

    public WXGameVideoFileObject() {
        this.filePath = null;
        this.videoUrl = null;
        this.thumbUrl = null;
    }

    public WXGameVideoFileObject(String str, String str2, String str3) {
        this.filePath = str;
        this.videoUrl = str2;
        this.thumbUrl = str3;
    }

    private int getFileSize(String str) {
        return b.a(str);
    }

    @Override // com.tencent.mm.opensdk.modelmsg.WXMediaMessage.IMediaObject
    public boolean checkArgs() {
        String str;
        String str2;
        String str3 = this.filePath;
        if (str3 == null || str3.length() == 0) {
            str = TAG;
            str2 = "checkArgs fail, filePath is null";
        } else if (getFileSize(this.filePath) > FILE_SIZE_LIMIT) {
            str = TAG;
            str2 = "checkArgs fail, video file size is too large";
        } else {
            String str4 = this.videoUrl;
            if (str4 == null || str4.length() <= URL_LENGTH_LIMIT) {
                String str5 = this.thumbUrl;
                if (str5 == null || str5.length() <= URL_LENGTH_LIMIT) {
                    return true;
                }
                str = TAG;
                str2 = "checkArgs fail, thumbUrl is too long";
            } else {
                str = TAG;
                str2 = "checkArgs fail, videoUrl is too long";
            }
        }
        Log.e(str, str2);
        return false;
    }

    @Override // com.tencent.mm.opensdk.modelmsg.WXMediaMessage.IMediaObject
    public void serialize(Bundle bundle) {
        bundle.putString("_wxvideofileobject_filePath", this.filePath);
        bundle.putString("_wxvideofileobject_cdnUrl", this.videoUrl);
        bundle.putString("_wxvideofileobject_thumbUrl", this.thumbUrl);
    }

    @Override // com.tencent.mm.opensdk.modelmsg.WXMediaMessage.IMediaObject
    public int type() {
        return 39;
    }

    @Override // com.tencent.mm.opensdk.modelmsg.WXMediaMessage.IMediaObject
    public void unserialize(Bundle bundle) {
        this.filePath = bundle.getString("_wxvideofileobject_filePath");
        this.videoUrl = bundle.getString("_wxvideofileobject_cdnUrl");
        this.thumbUrl = bundle.getString("_wxvideofileobject_thumbUrl");
    }
}
