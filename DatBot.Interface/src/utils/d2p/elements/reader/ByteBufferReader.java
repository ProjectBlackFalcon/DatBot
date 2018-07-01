package utils.d2p.elements.reader;

import utils.d2p.elements.behaviorismanaged.DataReader;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Optional;

public final class ByteBufferReader extends LegacyReader {
    private final ByteBuffer buf;
    private final Optional<ByteBufferAllocator> allocator;

    private ByteBufferReader(ByteBuffer buf, Optional<ByteBufferAllocator> allocator) {
        this.buf = buf;
        this.allocator = allocator;
    }

    public static ByteBufferReader of(ByteBuffer buf) {
        return new ByteBufferReader(buf, Optional.empty());
    }
    public static ByteBufferReader of(ByteBuffer buf, ByteBufferAllocator allocator) {
        return new ByteBufferReader(buf, Optional.of(allocator));
    }

    public static ByteBufferReader of(int... vals) {
        byte[] bytes = new byte[vals.length];
        for (int i = 0; i < vals.length; i++) {
            bytes[i] = (byte) vals[i];
        }
        return of(ByteBuffer.wrap(bytes));
    }

    private void failFast(int n) {
        if (!readable(n)) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public long getPosition() {
        return buf.position();
    }

    @Override
    public void setPosition(long position) {
        buf.position((int) position);
    }

    @Override
    public boolean readable(int n) {
        return buf.remaining() >= n;
    }

    @Override
    public byte[] readBytes(int n) {
        failFast(n);

        byte[] res = new byte[n];
        buf.get(res);
        return res;
    }

    @Override
    public DataReader read(int n) {
        failFast(n);

        // duplicate buffer and set the read length
        ByteBuffer dup = buf.duplicate();
        dup.limit(dup.position() + n);

        // move buffer position so that user can continue to read
        buf.position(buf.position() + n);

        // return the duplicate
        return of(dup);
    }

    @Override
    public ByteBuffer all() {
        ByteBuffer dup = buf.duplicate();
        dup.rewind();
        return dup.asReadOnlyBuffer();
    }

    @Override
    public byte readInt8() {
        failFast(1);
        return buf.get();
    }

    @Override
    public short readUInt8() {
        failFast(1);
        return (short) Byte.toUnsignedInt(readInt8());
    }

    @Override
    public short readInt16() {
        failFast(2);
        return buf.getShort();
    }

    @Override
    public int readUInt16() {
        failFast(2);
        return Short.toUnsignedInt(readInt16());
    }

    @Override
    public int readInt32() {
        failFast(4);
        return buf.getInt();
    }

    @Override
    public long readUInt32() {
        failFast(4);
        return Integer.toUnsignedLong(readInt32());
    }

    @Override
    public long readInt64() {
        failFast(8);
        return buf.getLong();
    }

    @Override
    public BigInteger readUInt64() {
        failFast(8);
        return BigInteger.valueOf(readUInt32()).shiftLeft(32).or(BigInteger.valueOf(readUInt32()));
    }

    @Override
    protected void finalize() throws Throwable {
        allocator.ifPresent(it -> it.release(buf));
        super.finalize();
    }
}
