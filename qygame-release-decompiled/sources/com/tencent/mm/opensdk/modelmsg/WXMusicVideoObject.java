package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

/* JADX INFO: loaded from: classes.dex */
public class WXMusicVideoObject implements WXMediaMessage.IMediaObject {
    private static final int HD_ALBUM_FILE_LENGTH = 10485760;
    private static final int LYRIC_LENGTH_LIMIT = 32768;
    private static final int STRING_LIMIT = 1024;
    private static final String TAG = "MicroMsg.SDK.WXMusicVideoObject";
    private static final int URL_LENGTH_LIMIT = 10240;
    public String albumName;
    public int duration;
    public String hdAlbumThumbFilePath;
    public String identification;
    public long issueDate;
    public String musicDataUrl;
    public String musicGenre;
    public String musicOperationUrl;
    public String musicUrl;
    public String singerName;
    public String songLyric;

    private int getFileSize(String str) {
        return b.a(str);
    }

    @Override // com.tencent.mm.opensdk.modelmsg.WXMediaMessage.IMediaObject
    public boolean checkArgs() {
        String str;
        String str2;
        if (b.b(this.musicUrl) || this.musicUrl.length() > URL_LENGTH_LIMIT) {
            str = TAG;
            str2 = "musicUrl.length exceeds the limit";
        } else if (b.b(this.musicDataUrl) || this.musicDataUrl.length() > URL_LENGTH_LIMIT) {
            str = TAG;
            str2 = "musicDataUrl.length exceeds the limit";
        } else if (b.b(this.singerName) || this.singerName.length() > 1024) {
            str = TAG;
            str2 = "singerName.length exceeds the limit";
        } else if (!b.b(this.songLyric) && this.songLyric.length() > 32768) {
            str = TAG;
            str2 = "songLyric.length exceeds the limit";
        } else if (!b.b(this.hdAlbumThumbFilePath) && this.hdAlbumThumbFilePath.length() > 1024) {
            str = TAG;
            str2 = "hdAlbumThumbFilePath.length exceeds the limit";
        } else if (!b.b(this.hdAlbumThumbFilePath) && getFileSize(this.hdAlbumThumbFilePath) > HD_ALBUM_FILE_LENGTH) {
            str = TAG;
            str2 = "hdAlbumThumbFilePath file length exceeds the limit";
        } else if (!b.b(this.musicGenre) && this.musicGenre.length() > 1024) {
            str = TAG;
            str2 = "musicGenre.length exceeds the limit";
        } else if (!b.b(this.identification) && this.identification.length() > 1024) {
            str = TAG;
            str2 = "identification.length exceeds the limit";
        } else {
            if (b.b(this.musicOperationUrl) || this.musicOperationUrl.length() <= URL_LENGTH_LIMIT) {
                return true;
            }
            str = TAG;
            str2 = "musicOperationUrl.length exceeds the limit";
        }
        Log.e(str, str2);
        return false;
    }

    @Override // com.tencent.mm.opensdk.modelmsg.WXMediaMessage.IMediaObject
    public void serialize(Bundle bundle) {
        bundle.putString("_wxmusicvideoobject_musicUrl", this.musicUrl);
        bundle.putString("_wxmusicvideoobject_musicDataUrl", this.musicDataUrl);
        bundle.putString("_wxmusicvideoobject_singerName", this.singerName);
        bundle.putString("_wxmusicvideoobject_songLyric", this.songLyric);
        bundle.putString("_wxmusicvideoobject_hdAlbumThumbFilePath", this.hdAlbumThumbFilePath);
        bundle.putString("_wxmusicvideoobject_albumName", this.albumName);
        bundle.putString("_wxmusicvideoobject_musicGenre", this.musicGenre);
        bundle.putLong("_wxmusicvideoobject_issueDate", this.issueDate);
        bundle.putString("_wxmusicvideoobject_identification", this.identification);
        bundle.putInt("_wxmusicvideoobject_duration", this.duration);
        bundle.putString("_wxmusicvideoobject_musicOperationUrl", this.musicOperationUrl);
    }

    @Override // com.tencent.mm.opensdk.modelmsg.WXMediaMessage.IMediaObject
    public int type() {
        return 76;
    }

    @Override // com.tencent.mm.opensdk.modelmsg.WXMediaMessage.IMediaObject
    public void unserialize(Bundle bundle) {
        this.musicUrl = bundle.getString("_wxmusicvideoobject_musicUrl");
        this.musicDataUrl = bundle.getString("_wxmusicvideoobject_musicDataUrl");
        this.singerName = bundle.getString("_wxmusicvideoobject_singerName");
        this.songLyric = bundle.getString("_wxmusicvideoobject_songLyric");
        this.hdAlbumThumbFilePath = bundle.getString("_wxmusicvideoobject_hdAlbumThumbFilePath");
        this.albumName = bundle.getString("_wxmusicvideoobject_albumName");
        this.musicGenre = bundle.getString("_wxmusicvideoobject_musicGenre");
        this.issueDate = bundle.getLong("_wxmusicvideoobject_issueDate", 0L);
        this.identification = bundle.getString("_wxmusicvideoobject_identification");
        this.duration = bundle.getInt("_wxmusicvideoobject_duration", 0);
        this.musicOperationUrl = bundle.getString("_wxmusicvideoobject_musicOperationUrl");
    }
}
