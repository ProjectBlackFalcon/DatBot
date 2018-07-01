package utils.d2p.elements.reader;

import java.math.BigInteger;

public interface DataReader extends BaseDataReader {
    boolean read_bool();
    boolean[] read_array_bool(int length);
    byte read_i8();
    byte[] read_array_i8(int length);
    short read_ui8();
    short[] read_array_ui8(int length);
    short read_i16();
    short[] read_array_i16(int length);
    int read_ui16();
    int[] read_array_ui16(int length);
    int read_i32();
    int[] read_array_i32(int length);
    long read_ui32();
    long[] read_array_ui32(int length);
    long read_i64();
    long[] read_array_i64(int length);
    BigInteger read_ui64();
    BigInteger[] read_array_ui64(int length);
    float read_f32();
    float[] read_array_f32(int length);
    double read_f64();
    double[] read_array_f64(int length);
    String read_str();
    String[] read_array_str(int length);
    short read_vi16();
    short[] read_array_vi16(int length);
    int read_vi32();
    int[] read_array_vi32(int length);
    long read_vi64();
    long[] read_array_vi64(int length);
}
