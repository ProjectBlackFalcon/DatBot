package utils.d2p.elements.reader;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public final class Zlib {
    private Zlib() {}

    public static byte[] uncompress(byte[] bytes) {
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();

            Inflater inflater = new Inflater(false);
            inflater.setInput(bytes);

            byte[] buf = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buf);
                result.write(buf, 0, count);
            }

            return result.toByteArray();
        } catch (DataFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
