package org.cocos2dx.okhttp3.internal.cache2;

import java.io.IOException;
import java.nio.channels.FileChannel;
import org.cocos2dx.okio.Buffer;

/* JADX INFO: loaded from: classes.dex */
final class FileOperator {
    private final FileChannel fileChannel;

    FileOperator(FileChannel fileChannel) {
        this.fileChannel = fileChannel;
    }

    public void read(long j, Buffer buffer, long j2) throws IOException {
        if (j2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        while (j2 > 0) {
            long jTransferTo = this.fileChannel.transferTo(j, j2, buffer);
            j += jTransferTo;
            j2 -= jTransferTo;
        }
    }

    public void write(long j, Buffer buffer, long j2) throws IOException {
        if (j2 < 0 || j2 > buffer.size()) {
            throw new IndexOutOfBoundsException();
        }
        while (j2 > 0) {
            long jTransferFrom = this.fileChannel.transferFrom(buffer, j, j2);
            j += jTransferFrom;
            j2 -= jTransferFrom;
        }
    }
}
