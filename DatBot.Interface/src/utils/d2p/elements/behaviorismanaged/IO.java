package utils.d2p.elements.behaviorismanaged;

import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.sql.Blob;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import utils.d2p.elements.reader.ByteArrayBlob;
import utils.d2p.elements.reader.ByteBufferReader;

public final class IO {
	private IO() {}


	@FunctionalInterface
	public static interface Reader {
		int read(byte[] buf, int offset, int length) throws Throwable;

		default int read(byte[] buf) throws Throwable {
			return read(buf, 0, buf.length);
		}
	}

	public static byte[] readAll(Reader reader) {
		try {
			byte[] buf = new byte[8096];
			int offset = 0;
			while (true) {
				int read = reader.read(buf, offset, buf.length - offset);
				if (read <= 0) break;

				if (offset + read >= buf.length) {
					byte[] cpy = new byte[buf.length * 2];
					System.arraycopy(buf, 0, cpy, 0, buf.length);
					buf = cpy;
				}

				offset += read;
			}
			if (offset < buf.length) {
				byte[] cpy = new byte[offset + 1];
				System.arraycopy(buf, 0, cpy, 0, offset + 1);
				buf = cpy;
			}
			return buf;
		} catch (RuntimeException|Error e) {
			throw e;
		} catch (Throwable t) {
            throw new RuntimeException(t);
        }
	}

	public static ByteBuffer readAllDirect(Reader reader) {
		try {
			ByteBuffer res = ByteBuffer.allocateDirect(8096);
			byte[] buf = new byte[4096];

			while (true) {
				int read = reader.read(buf);
				if (read <= 0) break;

				if (read > res.remaining()) {
					ByteBuffer cpy = ByteBuffer.allocateDirect(res.capacity() * 2);
					res.flip();
					cpy.put(res);
					res = cpy;
				}
				res.put(buf, 0, read);
			}

			if (res.hasRemaining()) {
				ByteBuffer cpy = ByteBuffer.allocateDirect(res.position());
				res.flip();
				cpy.put(res);
				res = cpy;
				res.rewind();
			}

			return res;
		} catch (RuntimeException|Error e) {
            throw e;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
	}

    public static byte[] toArray(ByteBuffer buf) {
        if (buf.hasArray()) {
            return buf.array();
        } else {
            buf = buf.duplicate();
            buf.rewind();
            byte[] arr = new byte[buf.remaining()];
            buf.get(arr);
            return arr;
        }
    }

	public static DataReader uncompress(DataReader reader) {
		Inflater infl = new Inflater();
		try {
			infl.setInput(reader.allBytes());
			return ByteBufferReader.of(readAllDirect(infl::inflate));
		} finally {
			infl.end();
		}
	}

    public static DataReader compress(DataReader reader) {
        Deflater defl = new Deflater();
        try {
            defl.setInput(reader.allBytes());
            defl.finish();
            return ByteBufferReader.of(readAllDirect(defl::deflate));
        } finally {
            defl.end();
        }
    }

    public static Blob asBlob(byte[] b) {
        return new ByteArrayBlob(b);
    }

    private static final Writer DEV_NULL = new Writer() {
        public void write(char[] cbuf, int off, int len) throws IOException { }
        public void flush() throws IOException {}
        public void close() throws IOException {}
    };

    public static Writer devNull() {
        return DEV_NULL;
    }
}
