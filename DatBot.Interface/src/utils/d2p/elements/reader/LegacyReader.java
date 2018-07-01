package utils.d2p.elements.reader;

import java.math.BigInteger;

public abstract class LegacyReader extends utils.d2p.elements.behaviorismanaged.DataReader implements DataReader {
    @Override
    public long plusPosition(long position) {
        return addPosition(position);
    }

    @Override
    public boolean canRead(int n) {
        return readable(n);
    }

    public boolean read_bool() {
        return readBoolean();
    }
    public boolean[] read_array_bool(int len) {
        boolean[] array = new boolean[len];
        for (int i = 0; i < len; i++) {
            array[i] = read_bool();
        }
        return array;
    }
    public byte read_i8() {
        return readInt8();
    }
    public byte[] read_array_i8(int length) {
        return readBytes(length);
    }
    public short read_ui8() {
        return readUInt8();
    }
    public short[] read_array_ui8(int len) {
        short[] array = new short[len];
        for (int i = 0; i < len; i++) {
            array[i] = read_ui8();
        }
        return array;
    }
    public short read_i16() {
        return readInt16();
    }
    public short[] read_array_i16(int len) {
        short[] array = new short[len];
        for (int i = 0; i < len; i++) {
            array[i] = read_i16();
        }
        return array;
    }
    public int read_ui16() {
        return readUInt16();
    }
    public int[] read_array_ui16(int len) {
        int[] array = new int[len];
        for (int i = 0; i < len; i++) {
            array[i] = read_ui16();
        }
        return array;
    }
    public int read_i32() {
        return readInt32();
    }
    public int[] read_array_i32(int len) {
        int[] array = new int[len];
        for (int i = 0; i < len; i++) {
            array[i] = read_i32();
        }
        return array;
    }
    public long read_ui32() {
        return readUInt32();
    }
    public long[] read_array_ui32(int len) {
        long[] array = new long[len];
        for (int i = 0; i < len; i++) {
            array[i] = read_ui32();
        }
        return array;
    }
    public long read_i64() {
        return readInt64();
    }
    public long[] read_array_i64(int len) {
        long[] array = new long[len];
        for (int i = 0; i < len; i++) {
            array[i] = read_i64();
        }
        return array;
    }
    public BigInteger read_ui64() {
        return readUInt64();
    }
    public BigInteger[] read_array_ui64(int len) {
        BigInteger[] array = new BigInteger[len];
        for (int i = 0; i < len; i++) {
            array[i] = read_ui64();
        }
        return array;
    }
    public float read_f32() {
        return readFloat();
    }
    public float[] read_array_f32(int len) {
        float[] array = new float[len];
        for (int i = 0; i < len; i++) {
            array[i] = read_f32();
        }
        return array;
    }
    public double read_f64() {
        return readDouble();
    }
    public double[] read_array_f64(int len) {
        double[] array = new double[len];
        for (int i = 0; i < len; i++) {
            array[i] = read_f64();
        }
        return array;
    }
    public String read_str() {
        return readUTF();
    }
    public String[] read_array_str(int len) {
        String[] array = new String[len];
        for (int i = 0; i < len; i++) {
            array[i] = read_str();
        }
        return array;
    }

    @Override
    public short read_vi16() {
        short acc = 0;
        for (int i = 0; i < 16; i += 7) {
            byte b = read_i8();
            acc |= (b & 0x7f) << i;
            if ((b & 0x80) == 0x80) {
                return acc;
            }
        }
        throw new RuntimeException("too much data");
    }

    @Override
    public int read_vi32() {
        int acc = 0;
        for (int i = 0; i < 32; i += 7) {
            byte b = read_i8();
            acc |= (b & 0x7f) << i;
            if ((b & 0x80) == 0x80) {
                return acc;
            }
        }
        throw new RuntimeException("too much data");
    }

    @Override
    public long read_vi64() {
        long acc = 0;
        for (int i = 0; i < 64; i += 7) {
            byte b = read_i8();
            acc |= (long) (b & 0x7f) << i;
            if ((b & 0x80) == 0x80) {
                return acc;
            }
        }
        throw new RuntimeException("too much data");
    }

    @Override
    public short[] read_array_vi16(int length) {
        short[] array = new short[length];
        for (int i = 0; i < length; i++) {
            array[i] = read_vi16();
        }
        return array;
    }

    @Override
    public int[] read_array_vi32(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = read_vi32();
        }
        return array;
    }

    @Override
    public long[] read_array_vi64(int length) {
        long[] array = new long[length];
        for (int i = 0; i < length; i++) {
            array[i] = read_vi64();
        }
        return array;
    }
}
