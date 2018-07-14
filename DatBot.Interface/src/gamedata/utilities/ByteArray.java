package gamedata.utilities;

import java.io.FileInputStream;
import java.nio.ByteBuffer;


public class ByteArray {	
	public static final int BUFFER_DEFAULT_SIZE = 8192;
    private static final int INT_SIZE = 32;  
    private static final int SHORT_SIZE = 16;
    private static final int SHORT_MAX_VALUE = 32767;
    private static final int UNSIGNED_SHORT_MAX_VALUE = 65536;
    private static final int CHUNCK_BIT_SIZE = 7;
    private static final int MASK_10000000 = 128;
    private static final int MASK_01111111 = 127;
	private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

	private byte[] array;
	private int pos;
	private int size;

	// construit un tableau vide de taille par d�faut
	public ByteArray() {
		this(BUFFER_DEFAULT_SIZE);
	}

	// construit un tableau vide de taille "size"
	public ByteArray(int size) {
		this.array = new byte[size];
		this.pos = 0;
		this.size = 0;
	}

	// constructeur destin� � construire un tableau complet
	public ByteArray(byte[] array) {
		this.array = array;
		this.pos = 0;
		this.size = array.length;
	}
	
	// constructeur destin� � construire un tableau incomplet,
	// "array" a donc une taille "size" et sera compl�t� plus tard
	public ByteArray(byte[] array, int size) {
		this.array = new byte[size];
		for(int i = 0; i < array.length; ++i)
			this.array[i] = array[i];
		this.pos = 0;
		this.size = array.length;
	}
	
	// agrandit le tableau courant en multipliant sa taille par 2
	private void extendArray() {
		byte[] newArray = new byte[this.array.length * 2];
		for(int i = 0; i < this.array.length; ++i)
			newArray[i] = this.array[i];
		this.array = newArray;
	}
	
	// remplace le tableau courant par un nouveau tableau complet
	public void setArray(byte[] array) {
		this.array = array;
		this.size = array.length;
		this.pos = 0;
	}
	
	// remplace le tableau courant par un nouveau tableau complet
	// mais qui est limit� � "size", c'est une sorte de d�coupage
	public void setArray(byte[] array, int size) {
		this.array = array;
		this.size = size;
		this.pos = 0;
	}
	
	public void appendBefore(byte[] array) {
		byte[] newArray = new byte[array.length + this.array.length];
		int i = 0;
		for(; i < array.length; ++i)
			newArray[i] = array[i];
		for(int j = 0; j < this.array.length; ++j, ++i)
			newArray[i] = this.array[j];
		this.array = newArray;
		this.pos = 0;
		this.size += array.length;
	}

	public int getPos() {
		return this.pos;
	}
	
	public void incPos(int nb) {
		this.pos += nb;
	}
	
	public void setPos(int nb) {
		this.pos = nb;
	}

	public int getSize() {
		return this.size;
	}

	public int remaining() {
		return this.size - this.pos;
	}

	public boolean endOfArray() {
		return this.pos == this.size;
	}
	
	public void trimArray(int nb) {
		this.size -= nb;
	}
	
	public void flushArray() {
		this.size = 0;
		this.pos = 0;
	}
	
	public static ByteArray fileToByteArray(String filepath) throws Exception {
		FileInputStream is = new FileInputStream(filepath);
		byte[] bytes = new byte[is.available()];
		is.read(bytes);
		is.close();
		return new ByteArray(bytes);
	}
	
	public ByteArray clonePart(int from, int size) {
		byte[] splitArray = new byte[size];
		for(int i = 0; i < splitArray.length; ++i)
			splitArray[i] = this.array[i + from];
		return new ByteArray(splitArray);
	}
	
	public void split(int from, int size) {
		byte[] splitArray = new byte[size];
		for(int i = 0; i < splitArray.length; ++i)
			splitArray[i] = this.array[i + from]; 
		this.array = splitArray;
	}
	
	public static byte[] trimBuffer(byte[] buffer, int limit) {
		byte[] trimmedBuffer = new byte[limit];
		for(int i = 0; i < limit; ++i)
			trimmedBuffer[i] = buffer[i];
		return trimmedBuffer;
	}
	
	public byte[] bytesFromPos() {
		byte[] clone = new byte[this.size - this.pos];
		for(int i = 0; i < clone.length; ++i)
			clone[i] = this.array[i + this.pos];
		return clone;
	}

	public byte[] bytes() {
		byte[] clone = new byte[this.size];
		for(int i = 0; i < this.size; ++i)
			clone[i] = this.array[i];
		return clone;
	}
	
	public static byte[] toBytes(int[] array) {
		byte[] bytes = new byte[array.length];
		for(int i = 0; i < array.length; ++i)
			bytes[i] = (byte) array[i];
		return bytes;
	}

	public static void printBytes(byte[] bytes, String format, int size) {
		System.out.print(bytes.length + " bytes : ");
		if(format == "dec") {
			for(int i = 0; i < size; ++i)
				System.out.print(bytes[i] + " ");
			System.out.println();
		}
		else if(format == "hex") {
			char[] hexChars = new char[size * 3];
			for ( int j = 0; j < size; j++ ) {
				int v = bytes[j] & 0xFF;
				hexChars[j * 3] = hexArray[v >>> 4];
				hexChars[j * 3 + 1] = hexArray[v & 0x0F];
				hexChars[j * 3 + 2] = ' ';
			}
			System.out.println(new String(hexChars));
		}
		else if(format == "ascii") {
			for(int i = 0; i < size; ++i)
				System.out.print((char) bytes[i]);
			System.out.println();
		}
		else
			System.out.println("Unknown display format.");
	}
	
	public static void printBytes(byte[] bytes, String format) {
		printBytes(bytes, format, bytes.length);
	}
	
	public static void printBytes(byte[] bytes) {
		printBytes(bytes, "hex", bytes.length);
	}
	
	public void printArray(String format, int size) {
		printBytes(bytes(), format, size);
	}

	public void printArray(String format) {
		printBytes(bytes(), format);
	}

	public int readByte() {
		if(this.pos >= this.size) try {
			throw new Exception("Invalid reading, end of byte array reached.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return this.array[this.pos++] & 0xFF;
	}
	
	public int readSignedByte() { // pour readVarShort (temporaire)
		if(this.pos >= this.size) try {
			throw new Exception("Invalid reading, end of byte array reached.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return this.array[this.pos++];
	}

	public boolean readBoolean() {
		return readByte() == 1 ? true : false;
	}

	public byte[] readBytes(int size) {
		byte[] bytes = new byte[size];
		for(int i = 0; i < size; ++i)
			bytes[i] = (byte) readByte();
		return bytes;
	}

	public int readShort() { // pas de unsigned short en Java
		return readByte() * 256 + readByte();
	}
	
	public int readInt() {
		return readByte() * 16777216 + readByte() * 65536 + readByte() * 256 + readByte();
	}
	
	public float readFloat() {
		return ByteBuffer.wrap(readBytes(4)).getFloat();
	}
	
	public double readDouble() {
		return ByteBuffer.wrap(readBytes(8)).getDouble();
	}

	public String readUTF() {
		return readUTFBytes(readShort());
	}
	
	public String readUTFBytes(int len) {
		char[] utf = new char[len];
		for(int i = 0; i < len; ++i)
			utf[i] = (char) readByte();
		return new String(utf);
	}

	public void writeByte(int b) {
		if(this.size == this.array.length)
			extendArray();
		this.array[this.pos++] = (byte) b;
		this.size++;
	}
	
	public void writeBoolean(boolean b) {
		if(b) writeByte(1);
		else writeByte(0);
	}

	public void writeBytes(ByteArray buffer) {
		for(int i = 0; i < buffer.size; ++i)
			writeByte(buffer.array[i]);
	}
	
	public void writeBytes(byte[] bytes, int limit) {
		for(int i = 0; i < limit; ++i)
			writeByte(bytes[i]);
	}

	public void writeBytes(byte[] bytes) {
		writeBytes(bytes, bytes.length);
	}

	public void writeShort(int s) {
		writeByte(s >> 8);
		writeByte(s & 0xff);
	}

	public void writeInt(int i) {
		writeByte(i >>> 24);
		writeByte(i >>> 16);
		writeByte(i >>> 8);
		writeByte(i);
	}
	
	public void writeDouble(double d) {
		byte[] bytes = new byte[8];
		ByteBuffer.wrap(bytes).putDouble(d);
		writeBytes(bytes);
	}

	public void writeUTF(String utf) {
		writeShort(utf.length());
		writeUTFBytes(utf);
	}

	public void writeUTFBytes(String utf) {
		int length = utf.length();
		for(int i = 0; i < length; ++i)
			writeByte(utf.charAt(i));
	}

	public int readVarInt() {
		int pos = getPos();
		int var4 = 0;
		int var1 = 0;
		int var2 = 0;
		boolean var3 = false;
		do {
			if(var2 >= INT_SIZE) try {
				throw new Exception("Too much data");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			var4 = readByte();
			var3 = (var4 & MASK_10000000) == MASK_10000000;
			if(var2 > 0)
				var1 += ((var4 & MASK_01111111) << var2);
			else
				var1 += var4 & MASK_01111111;
			var2 += CHUNCK_BIT_SIZE;
		} while(var3);
		if(getPos() - pos > 4) try {
			throw new Exception("So many bytes read.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return var1;
	}
	
	public int readVarShort() {
		//int pos = getPos();
		int var4 = 0;
		int var1 = 0;
		int var2 = 0;
		boolean var3 = false;
		do {
			if(var2 >= SHORT_SIZE) try {
				throw new Exception("Too much data");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			var4 = readSignedByte();
			var3 = (var4 & MASK_10000000) == MASK_10000000;
			if(var2 > 0)
				var1 += (var4 & MASK_01111111) << var2;
			else
				var1 += var4 & MASK_01111111;
			var2 += CHUNCK_BIT_SIZE;
		} while(var3);
		if(var1 > SHORT_MAX_VALUE)
			var1 -= UNSIGNED_SHORT_MAX_VALUE;	
		//if(getPos() - pos > 2)
			//throw new FatalError("So many bytes read.");
		return var1;
	}
	
	public double readVarLong() {
		int var3 = 0;
		Int64 var2 = new Int64();
		int var4 = 0;
		while(true) {
			var3 = readByte();
			if(var4 == 28)
				break;
			if(var3 >= 128)
				var2.low = var2.low | (var3 & 127) << var4;
			else {
				var2.low = var2.low | (var3 << var4);
				return var2.toNumber();
			}
			var4 += 7;
		}
		if(var3 >= 128) {
			var3 = var3 & 127;
			var2.low = var2.low | (var3 << var4);
			var2.high = var3 >>> 4;
		}
		else {
			var2.low = var2.low | (var3 << var4);
			var2.high = var3 >>> 4;
			return var2.toNumber();
		}
		var4 = 3;
		while(true) {
			var3 = readByte();
			if(var4 < 32)
				if(var3 >= 128)
					var2.high = var2.high | ((var3 & 127) << var4);
				else {
					var2.high = var2.high | (var3 << var4);
					break;
				}
			var4 = var4 + 7;
		}
		return var2.toNumber();
	}

	public void writeVarInt(int i) {
		int var5 = 0;
		ByteArray var2 = new ByteArray();
		if(i >= 0 && i <= MASK_01111111) {
			var2.writeByte(i); 
			writeBytes(var2);
			return;
		}
		int var3 = i;
		ByteArray var4 = new ByteArray();
		while(var3 != 0) {
			var4.writeByte(var3 & MASK_01111111);
			var5 = var3 & MASK_01111111;
			var3 = var3 >>> CHUNCK_BIT_SIZE;
				if(var3 > 0)
					var5 = var5 | MASK_10000000;
				var2.writeByte(var5);
		}
		writeBytes(var2);
	}

	public void writeVarShort(int s) {
		int var5 = 0;
		ByteArray var2 = new ByteArray();
		if(s >= 0 && s <= MASK_01111111) {
			var2.writeByte(s);
			writeBytes(var2);
			return;
		}
		int var3 = s & 65535;
		ByteArray var4 = new ByteArray();
		while(var3 != 0) {
			var4.writeByte(var3 & MASK_01111111);
			var5 = var3 & MASK_01111111;
			var3 = var3 >>> CHUNCK_BIT_SIZE;
			if(var3 > 0)
				var5 = var5 | MASK_10000000;
			var2.writeByte(var5);
		}
		writeBytes(var2);
	}

	public void writeVarLong(double d) {
		int var3 = 0;
		Int64 var2 = Int64.fromNumber(d);
		if(var2.low == 0)
			writeInt32((int) var2.low);
		else {
			var3 = 0;
			while(var3 < 4) {
				writeByte(var2.low & 127 | 128);
				var2.low = var2.low >>> 7;
				var3++;
			}
			if((var2.high & 268435455 << 3) == 0)
				writeByte(var2.high << 4 | var2.low);
			else {
				writeByte((var2.high << 4 | var2.low) & 127 | 128);
				writeInt32((int) (var2.high >>> 3));
			}
		}
	}
	
	private void writeInt32(int i) {
		while(i >= 128) {
			writeByte(i & 127 | 128);
			i = i >>> 7;
		}
		writeByte(i);
	}
}