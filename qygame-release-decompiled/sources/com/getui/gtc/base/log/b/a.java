package com.getui.gtc.base.log.b;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.getui.gtc.base.crypt.CryptTools;
import com.getui.gtc.base.log.ILogDestination;
import com.getui.gtc.base.util.CommonUtil;
import com.igexin.push.g.e;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class a implements ILogDestination {
    private static final Map<File, HandlerC0009a> a = new ConcurrentHashMap();
    private final Context b;
    private File c;

    /* JADX INFO: renamed from: com.getui.gtc.base.log.b.a$a, reason: collision with other inner class name */
    static class HandlerC0009a extends Handler {
        final SecretKey a;
        final IvParameterSpec b;
        final File c;

        HandlerC0009a(Looper looper, File file, SecretKey secretKey, IvParameterSpec ivParameterSpec) {
            super(looper);
            this.c = file;
            this.a = secretKey;
            this.b = ivParameterSpec;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) throws Throwable {
            RandomAccessFile randomAccessFile;
            FileLock fileLockLock;
            RandomAccessFile randomAccessFile2;
            FileLock fileLockLock2;
            DataOutputStream dataOutputStream = null;
            switch (message.what) {
                case 1:
                    File file = this.c;
                    try {
                        randomAccessFile = new RandomAccessFile(file, "rw");
                        try {
                            fileLockLock = randomAccessFile.getChannel().lock();
                            if (fileLockLock != null) {
                                try {
                                    try {
                                        if (fileLockLock.isValid()) {
                                            DataOutputStream dataOutputStream2 = new DataOutputStream(new FileOutputStream(file, true));
                                            try {
                                                byte[] bArrEncrypt = CryptTools.encrypt("RSA/ECB/OAEPWithSHA1AndMGF1Padding", CryptTools.parsePublicKey("RSA", e.a), this.a.getEncoded());
                                                int length = bArrEncrypt.length;
                                                dataOutputStream2.write(0);
                                                dataOutputStream2.write(this.b.getIV());
                                                dataOutputStream2.writeInt(length);
                                                dataOutputStream2.write(bArrEncrypt);
                                                dataOutputStream = dataOutputStream2;
                                            } catch (Throwable th) {
                                                th = th;
                                                dataOutputStream = dataOutputStream2;
                                                if (dataOutputStream != null) {
                                                    try {
                                                        dataOutputStream.flush();
                                                        dataOutputStream.close();
                                                        break;
                                                    } catch (IOException unused) {
                                                    }
                                                }
                                                if (fileLockLock != null && fileLockLock.isValid()) {
                                                    try {
                                                        fileLockLock.release();
                                                        break;
                                                    } catch (IOException unused2) {
                                                    }
                                                }
                                                if (randomAccessFile == null) {
                                                    throw th;
                                                }
                                                try {
                                                    randomAccessFile.close();
                                                    throw th;
                                                } catch (IOException unused3) {
                                                    throw th;
                                                }
                                            }
                                        }
                                    } catch (Throwable th2) {
                                        th = th2;
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                }
                            }
                            if (dataOutputStream != null) {
                                try {
                                    dataOutputStream.flush();
                                    dataOutputStream.close();
                                    break;
                                } catch (IOException unused4) {
                                }
                            }
                            if (fileLockLock != null && fileLockLock.isValid()) {
                                try {
                                    fileLockLock.release();
                                    break;
                                } catch (IOException unused5) {
                                }
                            }
                            try {
                                randomAccessFile.close();
                                return;
                            } catch (IOException unused6) {
                                return;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            fileLockLock = null;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        randomAccessFile = null;
                        fileLockLock = null;
                    }
                    break;
                case 2:
                    String str = (String) message.obj;
                    File file2 = this.c;
                    try {
                        randomAccessFile2 = new RandomAccessFile(file2, "rw");
                        try {
                            fileLockLock2 = randomAccessFile2.getChannel().lock();
                            if (fileLockLock2 != null) {
                                try {
                                    try {
                                        if (fileLockLock2.isValid()) {
                                            DataOutputStream dataOutputStream3 = new DataOutputStream(new FileOutputStream(file2, true));
                                            try {
                                                byte[] bArrDigest = CryptTools.digest("SHA1", this.a.getEncoded());
                                                byte[] bArrEncrypt2 = CryptTools.encrypt("AES/CBC/PKCS5Padding", this.a, this.b, str.getBytes());
                                                int length2 = bArrEncrypt2.length;
                                                dataOutputStream3.write(112);
                                                dataOutputStream3.write(bArrDigest);
                                                dataOutputStream3.writeInt(length2);
                                                dataOutputStream3.write(bArrEncrypt2);
                                                dataOutputStream = dataOutputStream3;
                                            } catch (Throwable th6) {
                                                th = th6;
                                                dataOutputStream = dataOutputStream3;
                                                if (dataOutputStream != null) {
                                                    try {
                                                        dataOutputStream.flush();
                                                        dataOutputStream.close();
                                                        break;
                                                    } catch (IOException unused7) {
                                                    }
                                                }
                                                if (fileLockLock2 != null && fileLockLock2.isValid()) {
                                                    try {
                                                        fileLockLock2.release();
                                                        break;
                                                    } catch (IOException unused8) {
                                                    }
                                                }
                                                if (randomAccessFile2 == null) {
                                                    throw th;
                                                }
                                                try {
                                                    randomAccessFile2.close();
                                                    throw th;
                                                } catch (IOException unused9) {
                                                    throw th;
                                                }
                                            }
                                        }
                                    } catch (Throwable th7) {
                                        th = th7;
                                    }
                                } catch (Throwable th8) {
                                    th = th8;
                                }
                            }
                            if (dataOutputStream != null) {
                                try {
                                    dataOutputStream.flush();
                                    dataOutputStream.close();
                                    break;
                                } catch (IOException unused10) {
                                }
                            }
                            if (fileLockLock2 != null && fileLockLock2.isValid()) {
                                try {
                                    fileLockLock2.release();
                                    break;
                                } catch (IOException unused11) {
                                }
                            }
                            try {
                                randomAccessFile2.close();
                                return;
                            } catch (IOException unused12) {
                                return;
                            }
                        } catch (Throwable th9) {
                            th = th9;
                            fileLockLock2 = null;
                        }
                    } catch (Throwable th10) {
                        th = th10;
                        randomAccessFile2 = null;
                        fileLockLock2 = null;
                    }
                    break;
                default:
                    return;
            }
        }
    }

    public a(Context context) {
        this.b = context;
        a(null);
    }

    public final void a(String str) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.getPackageName());
        if (str == null) {
            str2 = "-";
        } else {
            str2 = "-" + str + "-";
        }
        sb.append(str2);
        sb.append(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        sb.append(".log");
        this.c = new File(CommonUtil.getExternalFilesDir(this.b), sb.toString());
    }

    @Override // com.getui.gtc.base.log.ILogDestination
    public void log(int i, String str, String str2) {
        HandlerC0009a handlerC0009a = a.get(this.c);
        if (!this.c.exists()) {
            try {
                this.c.getParentFile().mkdirs();
                this.c.createNewFile();
            } catch (Throwable unused) {
            }
            if (handlerC0009a != null) {
                handlerC0009a.obtainMessage(1).sendToTarget();
            }
        }
        if (handlerC0009a == null) {
            synchronized (a.class) {
                handlerC0009a = a.get(this.c);
                if (handlerC0009a == null) {
                    try {
                        SecretKey secretKeyGenerateKey = CryptTools.generateKey("AES", 128);
                        HandlerThread handlerThread = new HandlerThread("File-Log-Thread");
                        handlerThread.start();
                        byte[] bArrGenerateSeed = new SecureRandom().generateSeed(16);
                        Map<File, HandlerC0009a> map = a;
                        File file = this.c;
                        HandlerC0009a handlerC0009a2 = new HandlerC0009a(handlerThread.getLooper(), this.c, secretKeyGenerateKey, new IvParameterSpec(bArrGenerateSeed));
                        map.put(file, handlerC0009a2);
                        handlerC0009a = handlerC0009a2;
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
            handlerC0009a.obtainMessage(1).sendToTarget();
        }
        handlerC0009a.obtainMessage(2, str2).sendToTarget();
    }
}
