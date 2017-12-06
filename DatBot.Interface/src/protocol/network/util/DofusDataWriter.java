package protocol.network.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import protocol.network.util.types.BitConverter;
import protocol.network.util.types.Int64;

@SuppressWarnings("unused")
public class DofusDataWriter implements IDofusDataOutput{
	
    public DataOutputStream dous;
    public ByteArrayOutputStream bous;
    private final int INT_SIZE = 32;
    private final int SHORT_SZE = 16;
    private final int SHORT_MIN_VALUE = -32768;
    private final int SHORT_MAX_VALUE = 32767;
    private final int UNSIGNED_SHORT_MAX_VALUE = 65536;
    private final int CHUNCK_BIT_SIZE = 7;
    private final int MAX_ENCODING_LENGTH = (int)Math.ceil(INT_SIZE / CHUNCK_BIT_SIZE);
    private final int MASK_10000000 = 128;
    private final int MASK_01111111 = 127;
    

    
    public DofusDataWriter(ByteArrayOutputStream bous) {
    	this.bous = bous;
    	this.dous = new DataOutputStream(this.bous);
	}
    
//    public byte[] getData(ByteArrayOutputStream baos) throws IOException{
//    	DataOutputStream data2 = this.data;
//    	byte[] array = new byte[this.data.size()];
//    	array = baos.toByteArray();
//    	this.data = data2;
//    	return array;
//    }
    
    
	public void Clear(){
		this.bous = new ByteArrayOutputStream();
		this.dous = new DataOutputStream(bous); 		
	}
	
	@Override
    public void writeUTF(String str) throws IOException
    {
        byte[] bytes = str.getBytes();
        int num = (short) bytes.length;
        this.dous.writeShort(num);
        for (int i = 0; i < num; i++)
            this.dous.write(bytes[i]);
    }
	
    public void writeByte(int v) throws IOException {
        dous.writeByte(v);
    }
    
	@Override
    public void writeVarShort(int value) throws IOException {
        if (value > SHORT_MAX_VALUE || value < SHORT_MIN_VALUE)
			try {
				throw new Exception("Forbidden value");
			} catch (Exception e) {
				e.printStackTrace();
			}
        int b = 0;
        if (value >= 0 && value <= MASK_01111111)
        {
            writeByte((byte) value);
            return;
        }
        int c = value & 65535;
        while (c != 0 && c != -1)
        {
            b = c & MASK_01111111;
            c = c >> CHUNCK_BIT_SIZE;
            if (c > 0)
                b = b | MASK_10000000;
            writeByte((byte) b);
        }
    }
	
    public void writeDouble(double v) throws IOException {
        dous.writeDouble(v);
    }
    
    public void writeShort(int v) throws IOException {
        dous.writeShort(v);
    }
    
    public void writeInt(int v) throws IOException {
        dous.writeInt(v);
    }
    
    public void writeUnsignedInt(int v) throws IOException {
        dous.writeInt(v);
    }
    
	@Override
//    public void writeVarInt(int value) throws IOException {
//        if (value >= 0 && value <= MASK_01111111)
//        {
//            writeByte((byte) value);
//            return;
//        }
//        int b = 0;
//        int c = value;
//        while (c != 0)
//        {
//            b = c & MASK_01111111;
//            c = c >> CHUNCK_BIT_SIZE;
//            if (c > 0)
//                b = b | MASK_10000000;
//            writeByte((byte) b);
//        }
//    }	
	
    public void writeVarInt(int value) throws IOException {
        int b = 0;
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        if ((value >= 0) && (value <= MASK_01111111)) {
            ba.write(value);
            dous.write(ba.toByteArray());
            return;
        }
        int c = value;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        while (c != 0) {
            buffer.write(c & MASK_01111111);
            int position = buffer.size() - 1;
            ByteArrayInputStream newBuffer = new ByteArrayInputStream(buffer.toByteArray());
            newBuffer.skip(position);
            b = newBuffer.read();
            c = c >>> CHUNCK_BIT_SIZE;
            if (c > 0)
                b = b | MASK_10000000;
            ba.write(b);
        }
        dous.write(ba.toByteArray());
    }

	@Override
    public void writeVarLong(long value) throws IOException {
        int i = 0;
        Int64 val = Int64.fromNumber(value);
        if (val.getHigh() == 0)
            writeint32((int)val.low);
        else {
            while (i < 4) {
                dous.writeByte((int)(val.low & 127 | 128));
                val.low = val.low >>> 7;
                i++;
            }
            if ((val.getHigh() & 268435455 << 3) == 0)
                dous.writeByte((int)(val.getHigh() << 4 | val.low));
            else {
                dous.writeByte((int)((val.getHigh() << 4 | val.low) & 127 | 128));
                writeint32((int)(val.getHigh() >>> 3));
            }
        }
    }
	
    public void writeBoolean(boolean v) throws IOException {
        dous.writeBoolean(v);
    }
	
    public void writeFloat(float v) throws IOException {
        dous.writeFloat(v);
    }
    
    public void writeChar(int v) throws IOException {
        dous.writeChar(v);
    }

    public void writeChars(String s) throws IOException {
        dous.writeChars(s);
    }

    public void writeLong(long v) throws IOException {
        dous.writeLong(v);
    }
	
    public void write(byte[] b) throws IOException {
        dous.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        dous.write(b, off, len);
    }

    public void write(int b) throws IOException {
        dous.write(b);
    }
    
    public void writeBytes(String s) throws IOException {
        dous.writeBytes(s);
    }
    
//    public void writeUTFBytes(String str) throws IOException
//    {
//        byte[] bytes = str.getBytes();
//        int num = bytes.length;
//        for (int i = 0; i < num; i++)
//            this.dous.write(bytes[i]);
//    }
    
//    public void WriteChar(char c) throws IOException
//    {
//        WriteBigEndianBytes(BitConverter.GetBytes(c));
//    }
//
//    public void WriteDouble(double d) throws IOException
//    {
//        WriteBigEndianBytes(BitConverter.GetBytes((int) d));
//    }
//    
//    public void WriteSingle(float single) throws IOException
//    {
//        WriteBigEndianBytes(BitConverter.GetBytes((int) single));
//    }
//    
//    public void WriteInt(int i) throws IOException
//    {
//        WriteBigEndianBytes(BitConverter.GetBytes(i));
//    }
//
//    public void WriteLong(long l) throws IOException
//    {
//        WriteBigEndianBytes(BitConverter.GetBytes(l));
//    }
//
//    public void WriteUShort(short us) throws IOException
//    {
//        WriteBigEndianBytes(BitConverter.GetBytes(us));
//    }
//
//    public void WriteUInt(int i) throws IOException
//    {
//        WriteBigEndianBytes(BitConverter.GetBytes(i));
//    }
//
//    public void WriteULong(long l) throws IOException
//    {
//        WriteBigEndianBytes(BitConverter.GetBytes(l));
//    }
//    
//    public void WriteShort(short s) throws IOException
//    {
//        WriteBigEndianBytes(BitConverter.GetBytes(s));
//    }
    public void WriteVarUhShort(int value) throws Exception
    {
        if (value > UNSIGNED_SHORT_MAX_VALUE || value < SHORT_MIN_VALUE)
            throw new Exception("Forbidden value");
        int b = 0;
        if (value >= 0 && value <= MASK_01111111)
        {
            writeByte((byte) value);
            return;
        }
        int c = value & 65535;
        while (c != 0)
        {
            b = c & MASK_01111111;
            c = c >> CHUNCK_BIT_SIZE;
            if (c > 0)
                b = b | MASK_10000000;
            write((byte) b);
        }
    }
    
    public void WriteVarUhLong(long value) throws IOException
    {
        writeVarLong((long) value);
    }
    
    private void writeint32(int value) throws IOException {
        while (value >= 128) {
            dous.writeByte(value & 127 | 128);
            value = value >>> 7;
        }
        dous.writeByte(value);
    }
}
