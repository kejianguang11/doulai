package com.gcloudsdk.apollo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.alipay.sdk.sys.a;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class ApolloVoiceConfig {
    private static String VPbinName = "GCloudVoice/cldnn_spkvector.mnn";
    private static String VPbinPath = "/cldnn_spkvector.mnn";
    private static String VPbinSourcePath = null;
    private static String aiDecBinName = "GCloudVoice/decoder_v4_small.nn";
    private static String aiDecBinPath = "/decoder_v4_small.nn";
    private static String aiDecBinSourcePath = null;
    private static String aiEncBinName = "GCloudVoice/encoder_v4_small.nn";
    private static String aiEncBinPath = "/encoder_v4_small.nn";
    private static String aiEncBinSourcePath = null;
    private static String binName = "GCloudVoice/libwxvoiceembed.bin";
    private static String binPath = "/libwxvoiceembed.bin";
    private static String binSourcePath = null;
    private static final String cfgName = "GCloudVoice/config.json";
    private static String dafxBinName = "GCloudVoice/wave_dafx_data.bin";
    private static String dafxBinPath = "/wave_dafx_data.bin";
    private static String dafxBinSourcePath = null;
    private static String dseAlignBinName = "GCloudVoice/dse_v1_align.nn";
    private static String dseAlignBinPath = "/dse_v1_align.nn";
    private static String dseAlignBinSourcePath = null;
    private static String dseBinName = "GCloudVoice/dse_v1.nn";
    private static String dseBinPath = "/dse_v1.nn";
    private static String dseBinSourcePath = null;
    private static String dseMonoBinName = "GCloudVoice/dse_v1_mono.nn";
    private static String dseMonoBinPath = "/dse_v1_mono.nn";
    private static String dseMonoBinSourcePath = null;
    private static String dyCfgPath = "/com.gcloudsdk.gcloud.gvoice/config/gvoice.cfg";
    private static Context mainContext = null;
    private static String nsBinName = "GCloudVoice/libgvoicensmodel.bin";
    private static String nsBinPath = "/libgvoicensmodel.bin";
    private static String nsBinSourcePath = null;
    private static String pcmPath = null;
    private static String storageCfgPath = null;
    private static String tdBinName = "GCloudVoice/wave_3d_data.bin";
    private static String tdBinPath = "/wave_3d_data.bin";
    private static String tdBinSourcePath;

    public static String ConfigFromManifest(String str) {
        ApplicationInfo applicationInfo;
        String string = "invalied";
        if (str != null && str.trim().length() != 0) {
            try {
                applicationInfo = mainContext.getPackageManager().getApplicationInfo(mainContext.getPackageName(), 128);
            } catch (Exception e) {
                ApolloVoiceLog.LogE("getApplicationInfo meets errors");
                e.printStackTrace();
            }
            if (applicationInfo != null && applicationInfo.metaData != null) {
                string = applicationInfo.metaData.getString(str);
                ApolloVoiceLog.LogI("configItem: " + str + " configValue: " + string);
            }
            ApolloVoiceLog.LogE("appInfo or appInfo.metaData is null");
            return "invalied";
        }
        return string;
    }

    public static void Copy3DSourceToDest(String str) throws Throwable {
        InputStream inputStreamOpen;
        File file;
        byte[] bArr;
        FileOutputStream fileOutputStream = null;
        try {
            inputStreamOpen = mainContext.getResources().getAssets().open(tdBinName);
            try {
                tdBinSourcePath = str + tdBinPath;
                file = new File(tdBinSourcePath);
            } catch (Exception unused) {
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception unused2) {
            inputStreamOpen = null;
        } catch (Throwable th2) {
            th = th2;
            inputStreamOpen = null;
        }
        try {
            if (!file.exists() || file.length() == 0) {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    bArr = new byte[4096];
                } catch (Exception unused3) {
                    fileOutputStream = fileOutputStream2;
                    if (inputStreamOpen != null) {
                        try {
                            inputStreamOpen.close();
                        } catch (Exception unused4) {
                        }
                    }
                    if (fileOutputStream == null) {
                        return;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = fileOutputStream2;
                    if (inputStreamOpen != null) {
                        try {
                            inputStreamOpen.close();
                        } catch (Exception unused5) {
                        }
                    }
                    if (fileOutputStream == null) {
                        throw th;
                    }
                    try {
                        fileOutputStream.close();
                        throw th;
                    } catch (Exception unused6) {
                        throw th;
                    }
                }
                while (true) {
                    int i = inputStreamOpen.read(bArr);
                    if (i == -1) {
                        break;
                    } else {
                        fileOutputStream2.write(bArr, 0, i);
                    }
                    fileOutputStream.close();
                    return;
                }
                fileOutputStream2.flush();
                fileOutputStream = fileOutputStream2;
            }
            fileOutputStream.close();
            return;
        } catch (Exception unused7) {
            return;
        }
        if (inputStreamOpen != null) {
            try {
                inputStreamOpen.close();
            } catch (Exception unused8) {
            }
        }
        if (fileOutputStream == null) {
        }
    }

    public static void CopyAiDecSourceToDest(String str) throws Throwable {
        InputStream inputStreamOpen;
        FileOutputStream fileOutputStream;
        byte[] bArr;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                inputStreamOpen = mainContext.getResources().getAssets().open(aiDecBinName);
                try {
                    aiDecBinSourcePath = str + aiDecBinPath;
                    fileOutputStream = new FileOutputStream(new File(aiDecBinSourcePath));
                    try {
                        bArr = new byte[4096];
                    } catch (Exception unused) {
                        fileOutputStream2 = fileOutputStream;
                        if (inputStreamOpen != null) {
                            try {
                                inputStreamOpen.close();
                            } catch (Exception unused2) {
                            }
                        }
                        if (fileOutputStream2 == null) {
                            return;
                        } else {
                            fileOutputStream2.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream2 = fileOutputStream;
                        if (inputStreamOpen != null) {
                            try {
                                inputStreamOpen.close();
                            } catch (Exception unused3) {
                            }
                        }
                        if (fileOutputStream2 == null) {
                            throw th;
                        }
                        try {
                            fileOutputStream2.close();
                            throw th;
                        } catch (Exception unused4) {
                            throw th;
                        }
                    }
                } catch (Exception unused5) {
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception unused6) {
                return;
            }
        } catch (Exception unused7) {
            inputStreamOpen = null;
        } catch (Throwable th3) {
            th = th3;
            inputStreamOpen = null;
        }
        while (true) {
            int i = inputStreamOpen.read(bArr);
            if (i == -1) {
                break;
            } else {
                fileOutputStream.write(bArr, 0, i);
            }
        }
        fileOutputStream.flush();
        if (inputStreamOpen != null) {
            try {
                inputStreamOpen.close();
            } catch (Exception unused8) {
            }
        }
        fileOutputStream.close();
    }

    public static void CopyAiEncSourceToDest(String str) throws Throwable {
        InputStream inputStreamOpen;
        FileOutputStream fileOutputStream;
        byte[] bArr;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                inputStreamOpen = mainContext.getResources().getAssets().open(aiEncBinName);
                try {
                    aiEncBinSourcePath = str + aiEncBinPath;
                    fileOutputStream = new FileOutputStream(new File(aiEncBinSourcePath));
                    try {
                        bArr = new byte[4096];
                    } catch (Exception unused) {
                        fileOutputStream2 = fileOutputStream;
                        if (inputStreamOpen != null) {
                            try {
                                inputStreamOpen.close();
                            } catch (Exception unused2) {
                            }
                        }
                        if (fileOutputStream2 == null) {
                            return;
                        } else {
                            fileOutputStream2.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream2 = fileOutputStream;
                        if (inputStreamOpen != null) {
                            try {
                                inputStreamOpen.close();
                            } catch (Exception unused3) {
                            }
                        }
                        if (fileOutputStream2 == null) {
                            throw th;
                        }
                        try {
                            fileOutputStream2.close();
                            throw th;
                        } catch (Exception unused4) {
                            throw th;
                        }
                    }
                } catch (Exception unused5) {
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception unused6) {
                return;
            }
        } catch (Exception unused7) {
            inputStreamOpen = null;
        } catch (Throwable th3) {
            th = th3;
            inputStreamOpen = null;
        }
        while (true) {
            int i = inputStreamOpen.read(bArr);
            if (i == -1) {
                break;
            } else {
                fileOutputStream.write(bArr, 0, i);
            }
        }
        fileOutputStream.flush();
        if (inputStreamOpen != null) {
            try {
                inputStreamOpen.close();
            } catch (Exception unused8) {
            }
        }
        fileOutputStream.close();
    }

    public static void CopyDafxSourceToDest(String str) throws Throwable {
        InputStream inputStreamOpen;
        File file;
        byte[] bArr;
        FileOutputStream fileOutputStream = null;
        try {
            inputStreamOpen = mainContext.getResources().getAssets().open(dafxBinName);
            try {
                dafxBinSourcePath = str + dafxBinPath;
                file = new File(dafxBinSourcePath);
            } catch (Exception unused) {
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception unused2) {
            inputStreamOpen = null;
        } catch (Throwable th2) {
            th = th2;
            inputStreamOpen = null;
        }
        try {
            if (!file.exists() || file.length() == 0) {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    bArr = new byte[4096];
                } catch (Exception unused3) {
                    fileOutputStream = fileOutputStream2;
                    if (inputStreamOpen != null) {
                        try {
                            inputStreamOpen.close();
                        } catch (Exception unused4) {
                        }
                    }
                    if (fileOutputStream == null) {
                        return;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = fileOutputStream2;
                    if (inputStreamOpen != null) {
                        try {
                            inputStreamOpen.close();
                        } catch (Exception unused5) {
                        }
                    }
                    if (fileOutputStream == null) {
                        throw th;
                    }
                    try {
                        fileOutputStream.close();
                        throw th;
                    } catch (Exception unused6) {
                        throw th;
                    }
                }
                while (true) {
                    int i = inputStreamOpen.read(bArr);
                    if (i == -1) {
                        break;
                    } else {
                        fileOutputStream2.write(bArr, 0, i);
                    }
                    fileOutputStream.close();
                    return;
                }
                fileOutputStream2.flush();
                fileOutputStream = fileOutputStream2;
            }
            fileOutputStream.close();
            return;
        } catch (Exception unused7) {
            return;
        }
        if (inputStreamOpen != null) {
            try {
                inputStreamOpen.close();
            } catch (Exception unused8) {
            }
        }
        if (fileOutputStream == null) {
        }
    }

    public static void CopyDseAlignSourceToDest(String str) throws Throwable {
        InputStream inputStreamOpen;
        FileOutputStream fileOutputStream;
        byte[] bArr;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                inputStreamOpen = mainContext.getResources().getAssets().open(dseAlignBinName);
                try {
                    dseAlignBinSourcePath = str + dseAlignBinPath;
                    fileOutputStream = new FileOutputStream(new File(dseAlignBinSourcePath));
                    try {
                        bArr = new byte[4096];
                    } catch (Exception unused) {
                        fileOutputStream2 = fileOutputStream;
                        if (inputStreamOpen != null) {
                            try {
                                inputStreamOpen.close();
                            } catch (Exception unused2) {
                            }
                        }
                        if (fileOutputStream2 == null) {
                            return;
                        } else {
                            fileOutputStream2.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream2 = fileOutputStream;
                        if (inputStreamOpen != null) {
                            try {
                                inputStreamOpen.close();
                            } catch (Exception unused3) {
                            }
                        }
                        if (fileOutputStream2 == null) {
                            throw th;
                        }
                        try {
                            fileOutputStream2.close();
                            throw th;
                        } catch (Exception unused4) {
                            throw th;
                        }
                    }
                } catch (Exception unused5) {
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception unused6) {
                return;
            }
        } catch (Exception unused7) {
            inputStreamOpen = null;
        } catch (Throwable th3) {
            th = th3;
            inputStreamOpen = null;
        }
        while (true) {
            int i = inputStreamOpen.read(bArr);
            if (i == -1) {
                break;
            } else {
                fileOutputStream.write(bArr, 0, i);
            }
        }
        fileOutputStream.flush();
        if (inputStreamOpen != null) {
            try {
                inputStreamOpen.close();
            } catch (Exception unused8) {
            }
        }
        fileOutputStream.close();
    }

    public static void CopyDseMonoSourceToDest(String str) throws Throwable {
        InputStream inputStreamOpen;
        FileOutputStream fileOutputStream;
        byte[] bArr;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                inputStreamOpen = mainContext.getResources().getAssets().open(dseMonoBinName);
                try {
                    dseMonoBinSourcePath = str + dseMonoBinPath;
                    fileOutputStream = new FileOutputStream(new File(dseMonoBinSourcePath));
                    try {
                        bArr = new byte[4096];
                    } catch (Exception unused) {
                        fileOutputStream2 = fileOutputStream;
                        if (inputStreamOpen != null) {
                            try {
                                inputStreamOpen.close();
                            } catch (Exception unused2) {
                            }
                        }
                        if (fileOutputStream2 == null) {
                            return;
                        } else {
                            fileOutputStream2.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream2 = fileOutputStream;
                        if (inputStreamOpen != null) {
                            try {
                                inputStreamOpen.close();
                            } catch (Exception unused3) {
                            }
                        }
                        if (fileOutputStream2 == null) {
                            throw th;
                        }
                        try {
                            fileOutputStream2.close();
                            throw th;
                        } catch (Exception unused4) {
                            throw th;
                        }
                    }
                } catch (Exception unused5) {
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception unused6) {
                return;
            }
        } catch (Exception unused7) {
            inputStreamOpen = null;
        } catch (Throwable th3) {
            th = th3;
            inputStreamOpen = null;
        }
        while (true) {
            int i = inputStreamOpen.read(bArr);
            if (i == -1) {
                break;
            } else {
                fileOutputStream.write(bArr, 0, i);
            }
        }
        fileOutputStream.flush();
        if (inputStreamOpen != null) {
            try {
                inputStreamOpen.close();
            } catch (Exception unused8) {
            }
        }
        fileOutputStream.close();
    }

    public static void CopyDseSourceToDest(String str) throws Throwable {
        FileOutputStream fileOutputStream;
        InputStream inputStreamOpen;
        byte[] bArr;
        dseBinSourcePath = str + dseBinPath;
        InputStream inputStream = null;
        fileOutputStream = null;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                inputStreamOpen = mainContext.getResources().getAssets().open(dseBinName);
                try {
                    fileOutputStream = new FileOutputStream(new File(dseBinSourcePath));
                } catch (Exception unused) {
                } catch (Throwable th) {
                    fileOutputStream = null;
                    inputStream = inputStreamOpen;
                    th = th;
                }
            } catch (Exception unused2) {
                return;
            }
        } catch (Exception unused3) {
            inputStreamOpen = null;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
        }
        try {
            bArr = new byte[4096];
        } catch (Exception unused4) {
            fileOutputStream2 = fileOutputStream;
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (Exception unused5) {
                }
            }
            if (fileOutputStream2 == null) {
                return;
            } else {
                fileOutputStream2.close();
            }
        } catch (Throwable th3) {
            th = th3;
            inputStream = inputStreamOpen;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception unused6) {
                }
            }
            if (fileOutputStream == null) {
                throw th;
            }
            try {
                fileOutputStream.close();
                throw th;
            } catch (Exception unused7) {
                throw th;
            }
        }
        while (true) {
            int i = inputStreamOpen.read(bArr);
            if (i == -1) {
                break;
            } else {
                fileOutputStream.write(bArr, 0, i);
            }
        }
        fileOutputStream.flush();
        if (inputStreamOpen != null) {
            try {
                inputStreamOpen.close();
            } catch (Exception unused8) {
            }
        }
        fileOutputStream.close();
    }

    public static void CopyNsSourceToDest(String str) throws Throwable {
        InputStream inputStreamOpen;
        FileOutputStream fileOutputStream;
        byte[] bArr;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                inputStreamOpen = mainContext.getResources().getAssets().open(nsBinName);
                try {
                    nsBinSourcePath = str + nsBinPath;
                    fileOutputStream = new FileOutputStream(new File(nsBinSourcePath));
                    try {
                        bArr = new byte[4096];
                    } catch (Exception unused) {
                        fileOutputStream2 = fileOutputStream;
                        if (inputStreamOpen != null) {
                            try {
                                inputStreamOpen.close();
                            } catch (Exception unused2) {
                            }
                        }
                        if (fileOutputStream2 == null) {
                            return;
                        } else {
                            fileOutputStream2.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream2 = fileOutputStream;
                        if (inputStreamOpen != null) {
                            try {
                                inputStreamOpen.close();
                            } catch (Exception unused3) {
                            }
                        }
                        if (fileOutputStream2 == null) {
                            throw th;
                        }
                        try {
                            fileOutputStream2.close();
                            throw th;
                        } catch (Exception unused4) {
                            throw th;
                        }
                    }
                } catch (Exception unused5) {
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception unused6) {
                return;
            }
        } catch (Exception unused7) {
            inputStreamOpen = null;
        } catch (Throwable th3) {
            th = th3;
            inputStreamOpen = null;
        }
        while (true) {
            int i = inputStreamOpen.read(bArr);
            if (i == -1) {
                break;
            } else {
                fileOutputStream.write(bArr, 0, i);
            }
        }
        fileOutputStream.flush();
        if (inputStreamOpen != null) {
            try {
                inputStreamOpen.close();
            } catch (Exception unused8) {
            }
        }
        fileOutputStream.close();
    }

    public static void CopySourceToDest(String str) throws Throwable {
        InputStream inputStreamOpen;
        File file;
        byte[] bArr;
        FileOutputStream fileOutputStream = null;
        try {
            inputStreamOpen = mainContext.getResources().getAssets().open(binName);
            try {
                binSourcePath = str + binPath;
                file = new File(binSourcePath);
            } catch (Exception unused) {
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception unused2) {
            inputStreamOpen = null;
        } catch (Throwable th2) {
            th = th2;
            inputStreamOpen = null;
        }
        try {
            if (!file.exists() || file.length() == 0) {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    bArr = new byte[4096];
                } catch (Exception unused3) {
                    fileOutputStream = fileOutputStream2;
                    if (inputStreamOpen != null) {
                        try {
                            inputStreamOpen.close();
                        } catch (Exception unused4) {
                        }
                    }
                    if (fileOutputStream == null) {
                        return;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = fileOutputStream2;
                    if (inputStreamOpen != null) {
                        try {
                            inputStreamOpen.close();
                        } catch (Exception unused5) {
                        }
                    }
                    if (fileOutputStream == null) {
                        throw th;
                    }
                    try {
                        fileOutputStream.close();
                        throw th;
                    } catch (Exception unused6) {
                        throw th;
                    }
                }
                while (true) {
                    int i = inputStreamOpen.read(bArr);
                    if (i == -1) {
                        break;
                    } else {
                        fileOutputStream2.write(bArr, 0, i);
                    }
                    fileOutputStream.close();
                    return;
                }
                fileOutputStream2.flush();
                fileOutputStream = fileOutputStream2;
            }
            fileOutputStream.close();
            return;
        } catch (Exception unused7) {
            return;
        }
        if (inputStreamOpen != null) {
            try {
                inputStreamOpen.close();
            } catch (Exception unused8) {
            }
        }
        if (fileOutputStream == null) {
        }
    }

    public static void CopyVPSourceToDest(String str) throws Throwable {
        InputStream inputStreamOpen;
        File file;
        byte[] bArr;
        FileOutputStream fileOutputStream = null;
        try {
            inputStreamOpen = mainContext.getResources().getAssets().open(VPbinName);
            try {
                VPbinSourcePath = str + VPbinPath;
                file = new File(VPbinSourcePath);
            } catch (Exception unused) {
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception unused2) {
            inputStreamOpen = null;
        } catch (Throwable th2) {
            th = th2;
            inputStreamOpen = null;
        }
        try {
            if (!file.exists() || file.length() == 0) {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    bArr = new byte[4096];
                } catch (Exception unused3) {
                    fileOutputStream = fileOutputStream2;
                    if (inputStreamOpen != null) {
                        try {
                            inputStreamOpen.close();
                        } catch (Exception unused4) {
                        }
                    }
                    if (fileOutputStream == null) {
                        return;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = fileOutputStream2;
                    if (inputStreamOpen != null) {
                        try {
                            inputStreamOpen.close();
                        } catch (Exception unused5) {
                        }
                    }
                    if (fileOutputStream == null) {
                        throw th;
                    }
                    try {
                        fileOutputStream.close();
                        throw th;
                    } catch (Exception unused6) {
                        throw th;
                    }
                }
                while (true) {
                    int i = inputStreamOpen.read(bArr);
                    if (i == -1) {
                        break;
                    } else {
                        fileOutputStream2.write(bArr, 0, i);
                    }
                    fileOutputStream.close();
                    return;
                }
                fileOutputStream2.flush();
                fileOutputStream = fileOutputStream2;
            }
            fileOutputStream.close();
            return;
        } catch (Exception unused7) {
            return;
        }
        if (inputStreamOpen != null) {
            try {
                inputStreamOpen.close();
            } catch (Exception unused8) {
            }
        }
        if (fileOutputStream == null) {
        }
    }

    public static String DynamicCfgPath() {
        String str = "invalied";
        try {
            File filesDir = mainContext.getFilesDir();
            if (filesDir != null) {
                str = filesDir.toString() + dyCfgPath;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApolloVoiceLog.LogI("Read Dynamic: " + str);
        return str;
    }

    public static String Get3DSourcePath() {
        return tdBinSourcePath;
    }

    public static String GetAiDecSourcePath() {
        return aiDecBinSourcePath;
    }

    public static String GetAiEncSourcePath() {
        return aiEncBinSourcePath;
    }

    public static String GetDafxSourcePath() {
        return dafxBinSourcePath;
    }

    public static String GetDseAlignSourcePath() {
        return dseAlignBinSourcePath;
    }

    public static String GetDseMonoSourcePath() {
        return dseMonoBinSourcePath;
    }

    public static String GetDseSourcePath() {
        return dseBinSourcePath;
    }

    public static String GetNsSourcePath() {
        return nsBinSourcePath;
    }

    public static String GetPcmPath() {
        return pcmPath;
    }

    public static String GetSourcePath() {
        return binSourcePath;
    }

    public static String GetVPSourcePath() {
        return VPbinSourcePath;
    }

    public static boolean IsSDCardCfgExist() {
        return new File(storageCfgPath).exists();
    }

    public static String JSONCfg(boolean z) throws Throwable {
        Throwable th;
        InputStream inputStreamOpen;
        String str;
        try {
            if (z) {
                inputStreamOpen = new FileInputStream(storageCfgPath);
                try {
                    str = "Read config file from storage: " + storageCfgPath;
                } catch (Exception unused) {
                    if (inputStreamOpen != null) {
                        try {
                            inputStreamOpen.close();
                        } catch (Exception unused2) {
                        }
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (inputStreamOpen != null) {
                        try {
                            inputStreamOpen.close();
                        } catch (Exception unused3) {
                        }
                    }
                    throw th;
                }
            } else {
                inputStreamOpen = mainContext.getResources().getAssets().open(cfgName);
                str = "Read config file from: GCloudVoice/config.json";
            }
            ApolloVoiceLog.LogI(str);
            int iAvailable = inputStreamOpen.available();
            byte[] bArr = new byte[iAvailable];
            int i = 0;
            while (i < iAvailable) {
                int i2 = inputStreamOpen.read(bArr, i, iAvailable - i);
                if (i2 == -1) {
                    break;
                }
                i += i2;
            }
            String str2 = new String(bArr, a.m);
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (Exception unused4) {
                }
            }
            return str2;
        } catch (Exception unused5) {
            inputStreamOpen = null;
        } catch (Throwable th3) {
            th = th3;
            inputStreamOpen = null;
        }
    }

    public static void SetContext(Context context) {
        String str;
        mainContext = context;
        File externalFilesDir = mainContext.getExternalFilesDir(null);
        if (externalFilesDir != null) {
            storageCfgPath = externalFilesDir.getAbsolutePath() + "/" + cfgName;
            pcmPath = externalFilesDir.getAbsolutePath();
            str = "storageCfgPath: " + storageCfgPath;
        } else {
            str = "getExternalFilesDir failed !!!";
        }
        ApolloVoiceLog.LogI(str);
    }
}
