package utils.d2p.elements.reader;

import com.google.common.primitives.Ints;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Bytes {
    private Bytes() {}

    public static byte[] from(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] from(Path path, long offset, int length) {
        try (SeekableByteChannel ch = Files.newByteChannel(path)) {
            if (offset < 0) {
                offset = ch.size() + offset;
            }
            if (length <= 0) {
                length = Ints.checkedCast(ch.size() - offset);
            }
            ByteBuffer buf = ByteBuffer.allocate(length);
            ch.position(offset);
            ch.read(buf);
            return buf.array();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
