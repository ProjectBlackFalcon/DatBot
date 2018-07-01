package utils.d2p.elements.reader;

import utils.d2p.elements.behaviorismanaged.DataReader;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public final class HeapDataReader extends LegacyReader {
	private final byte[] buf;
	private final int baseIndex;
	private final int length;
	private int index;

	public HeapDataReader(byte[] buf, int index, int length) {
		this.buf = buf;
		this.baseIndex = index;
		this.length = length;
		this.index = index;
	}

	private void failFast(int minBytes) {
		if (!readable(minBytes)) {
			throw new IndexOutOfBoundsException();
		}
	}

    @Override
    public long getPosition() {
        return index;
    }

    @Override
    public void setPosition(long position) {
        index = (int) position;
    }

    @Override
	public boolean readable(int n) {
		return index + n <= baseIndex + length;
	}

	@Override
	public byte[] readBytes(int n) {
		failFast(n);
		byte[] res = new byte[n];
		System.arraycopy(buf, index, res, 0, n);
		index += n;
		return res;
	}

    @Override
    public DataReader read(int n) {
        failFast(n);
        DataReader dup = new HeapDataReader(buf, index, n);
        index += n;
        return dup;
    }

    @Override
    public ByteBuffer all() {
        return ByteBuffer.wrap(buf, baseIndex, length).asReadOnlyBuffer();
    }

    @Override
    public byte[] allBytes() {
        byte[] dup = new byte[length];
        System.arraycopy(buf, baseIndex, dup, 0, length);
        return dup;
    }

    @Override
	public byte readInt8() {
        failFast(1);
		return buf[index++];
	}

	@Override
	public short readUInt8() {
        failFast(1);
		return (short) (readInt8() & 0xFF);
	}

	@Override
	public short readInt16() {
        failFast(2);
		return (short) (readUInt8() << 8 | readUInt8());
	}

	@Override
	public int readUInt16() {
        failFast(2);
		return readUInt8() << 8 | readUInt8();
	}

	@Override
	public int readInt32() {
        failFast(4);
		return readUInt8() << 24 | readUInt8() << 16 | readUInt8() << 8 | readUInt8();
	}

	@Override
	public long readUInt32() {
        failFast(4);
		return (long) readUInt8() << 24 | (long) readUInt8() << 16 | (long) readUInt8() << 8 | (long) readUInt8();
	}

	@Override
	public long readInt64() {
        failFast(8);
		return readUInt32() << 32 | readUInt32();
	}

	@Override
	public BigInteger readUInt64() {
        failFast(8);
		return BigInteger.valueOf(readUInt32()).shiftLeft(32).or(BigInteger.valueOf(readUInt32()));
	}
}
