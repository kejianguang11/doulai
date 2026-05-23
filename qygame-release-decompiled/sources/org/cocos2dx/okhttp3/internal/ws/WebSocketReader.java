package org.cocos2dx.okhttp3.internal.ws;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import org.cocos2dx.okio.Buffer;
import org.cocos2dx.okio.BufferedSource;
import org.cocos2dx.okio.ByteString;

/* JADX INFO: loaded from: classes.dex */
final class WebSocketReader {
    final boolean a;
    final BufferedSource b;
    final FrameCallback c;
    boolean d;
    int e;
    long f;
    boolean g;
    boolean h;
    private final Buffer.UnsafeCursor maskCursor;
    private final byte[] maskKey;
    private final Buffer controlFrameBuffer = new Buffer();
    private final Buffer messageFrameBuffer = new Buffer();

    public interface FrameCallback {
        void onReadClose(int i, String str);

        void onReadMessage(String str) throws IOException;

        void onReadMessage(ByteString byteString) throws IOException;

        void onReadPing(ByteString byteString);

        void onReadPong(ByteString byteString);
    }

    WebSocketReader(boolean z, BufferedSource bufferedSource, FrameCallback frameCallback) {
        if (bufferedSource == null) {
            throw new NullPointerException("source == null");
        }
        if (frameCallback == null) {
            throw new NullPointerException("frameCallback == null");
        }
        this.a = z;
        this.b = bufferedSource;
        this.c = frameCallback;
        this.maskKey = z ? null : new byte[4];
        this.maskCursor = z ? null : new Buffer.UnsafeCursor();
    }

    private void readControlFrame() throws IOException {
        if (this.f > 0) {
            this.b.readFully(this.controlFrameBuffer, this.f);
            if (!this.a) {
                this.controlFrameBuffer.readAndWriteUnsafe(this.maskCursor);
                this.maskCursor.seek(0L);
                WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
                this.maskCursor.close();
            }
        }
        switch (this.e) {
            case 8:
                short s = 1005;
                String utf8 = "";
                long size = this.controlFrameBuffer.size();
                if (size == 1) {
                    throw new ProtocolException("Malformed close payload length of 1.");
                }
                if (size != 0) {
                    s = this.controlFrameBuffer.readShort();
                    utf8 = this.controlFrameBuffer.readUtf8();
                    String strCloseCodeExceptionMessage = WebSocketProtocol.closeCodeExceptionMessage(s);
                    if (strCloseCodeExceptionMessage != null) {
                        throw new ProtocolException(strCloseCodeExceptionMessage);
                    }
                }
                this.c.onReadClose(s, utf8);
                this.d = true;
                return;
            case 9:
                this.c.onReadPing(this.controlFrameBuffer.readByteString());
                return;
            case 10:
                this.c.onReadPong(this.controlFrameBuffer.readByteString());
                return;
            default:
                throw new ProtocolException("Unknown control opcode: " + Integer.toHexString(this.e));
        }
    }

    /* JADX WARN: Finally extract failed */
    private void readHeader() throws IOException {
        if (this.d) {
            throw new IOException("closed");
        }
        long jTimeoutNanos = this.b.timeout().timeoutNanos();
        this.b.timeout().clearTimeout();
        try {
            int i = this.b.readByte() & 255;
            this.b.timeout().timeout(jTimeoutNanos, TimeUnit.NANOSECONDS);
            this.e = i & 15;
            this.g = (i & 128) != 0;
            this.h = (i & 8) != 0;
            if (this.h && !this.g) {
                throw new ProtocolException("Control frames must be final.");
            }
            boolean z = (i & 64) != 0;
            boolean z2 = (i & 32) != 0;
            boolean z3 = (i & 16) != 0;
            if (z || z2 || z3) {
                throw new ProtocolException("Reserved flags are unsupported.");
            }
            boolean z4 = ((this.b.readByte() & 255) & 128) != 0;
            if (z4 == this.a) {
                throw new ProtocolException(this.a ? "Server-sent frames must not be masked." : "Client-sent frames must be masked.");
            }
            this.f = r0 & 127;
            if (this.f == 126) {
                this.f = ((long) this.b.readShort()) & 65535;
            } else if (this.f == 127) {
                this.f = this.b.readLong();
                if (this.f < 0) {
                    throw new ProtocolException("Frame length 0x" + Long.toHexString(this.f) + " > 0x7FFFFFFFFFFFFFFF");
                }
            }
            if (this.h && this.f > 125) {
                throw new ProtocolException("Control frame must be less than 125B.");
            }
            if (z4) {
                this.b.readFully(this.maskKey);
            }
        } catch (Throwable th) {
            this.b.timeout().timeout(jTimeoutNanos, TimeUnit.NANOSECONDS);
            throw th;
        }
    }

    private void readMessage() throws IOException {
        while (!this.d) {
            if (this.f > 0) {
                this.b.readFully(this.messageFrameBuffer, this.f);
                if (!this.a) {
                    this.messageFrameBuffer.readAndWriteUnsafe(this.maskCursor);
                    this.maskCursor.seek(this.messageFrameBuffer.size() - this.f);
                    WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
                    this.maskCursor.close();
                }
            }
            if (this.g) {
                return;
            }
            readUntilNonControlFrame();
            if (this.e != 0) {
                throw new ProtocolException("Expected continuation opcode. Got: " + Integer.toHexString(this.e));
            }
        }
        throw new IOException("closed");
    }

    private void readMessageFrame() throws IOException {
        int i = this.e;
        if (i != 1 && i != 2) {
            throw new ProtocolException("Unknown opcode: " + Integer.toHexString(i));
        }
        readMessage();
        if (i == 1) {
            this.c.onReadMessage(this.messageFrameBuffer.readUtf8());
        } else {
            this.c.onReadMessage(this.messageFrameBuffer.readByteString());
        }
    }

    private void readUntilNonControlFrame() throws IOException {
        while (!this.d) {
            readHeader();
            if (!this.h) {
                return;
            } else {
                readControlFrame();
            }
        }
    }

    void a() throws IOException {
        readHeader();
        if (this.h) {
            readControlFrame();
        } else {
            readMessageFrame();
        }
    }
}
