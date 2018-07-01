package utils.d2p.elements.behaviorismanaged;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public abstract class DataReader {
    public abstract long getPosition();
    public abstract void setPosition(long position);

	public abstract boolean readable(int n);

	public abstract byte[] readBytes(int n);
    public abstract DataReader read(int n);
    public abstract ByteBuffer all();

	public abstract byte readInt8();
	public abstract short readUInt8();
	public abstract short readInt16();
	public abstract int readUInt16();
	public abstract int readInt32();
	public abstract long readUInt32();
	public abstract long readInt64();
	public abstract BigInteger readUInt64();

    public byte[] allBytes() {
        return IO.toArray(all());
    }

	public boolean readBoolean() {
		return readInt8() == 1;
	}

	public float readFloat() {
		return Float.intBitsToFloat(readInt32());
	}

	public double readDouble() {
		return Double.longBitsToDouble(readInt64());
	}

	public char readChar() {
		return (char) readUInt8();
	}

	public String readUTF(int len) {
		char[] chars = new char[len];

		for (int i = 0; i < len; i++) {
			chars[i] = readChar();
		}

		return new String(chars);
	}

	public String readUTF() {
		return readUTF(readUInt16());
	}

    public String readMultibytes(int len, Charset ch) {
        byte[] bytes = readBytes(len);
        return ch.decode(ByteBuffer.wrap(bytes)).toString();
    }

    public long addPosition(long add) {
        setPosition(getPosition() + add);
        return getPosition();
    }

    public void resetPosition() {
        setPosition(0);
    }
}
