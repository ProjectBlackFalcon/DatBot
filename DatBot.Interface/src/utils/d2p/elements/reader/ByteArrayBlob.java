package utils.d2p.elements.reader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

public class ByteArrayBlob implements Blob {
    private final byte[] bytes;

    public ByteArrayBlob(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public long length() throws SQLException {
        return bytes.length;
    }

    @Override
    public byte[] getBytes(long pos, int length) throws SQLException {
        byte[] result = new byte[length];
        System.arraycopy(bytes, (int) pos, result, 0, length);
        return result;
    }

    @Override
    public InputStream getBinaryStream() throws SQLException {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public InputStream getBinaryStream(long pos, long length) throws SQLException {
        return new ByteArrayInputStream(bytes, (int) pos, (int) length);
    }

    @Override
    public void free() throws SQLException { }

    @Override
    public long position(byte[] pattern, long start) throws SQLException {
        throw new SQLException("not implemented");
    }

    @Override
    public long position(Blob pattern, long start) throws SQLException {
        throw new SQLException("not implemented");
    }

    @Override
    public int setBytes(long pos, byte[] b) throws SQLException {
        throw new SQLException("not implemented");
    }

    @Override
    public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
        throw new SQLException("not implemented");
    }

    @Override
    public OutputStream setBinaryStream(long pos) throws SQLException {
        throw new SQLException("not implemented");
    }

    @Override
    public void truncate(long len) throws SQLException {
        throw new SQLException("not implemented");
    }
}
