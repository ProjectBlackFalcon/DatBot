package utils.d2i;


import org.apache.mina.core.buffer.IoBuffer;

import main.Main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;

public class d2iManager {

    public static IoBuffer buf;
    public static Hashtable<Integer, Integer> _indexes;
    public static boolean init = false;

    public static void init(String filePath){
        byte[] binary = null;
        System.out.println("Filepath : " +filePath);
		try {
			binary = Files.readAllBytes(Paths.get(filePath));
		}
		catch (IOException e) {
			System.out.println("Cannot read file : " + e.getMessage());
		}
		System.out.println("Null ? " +binary);
        buf = IoBuffer.wrap(binary);
        int key = 0;
        int pointer = 0;
        boolean diacriticalText;
        _indexes = new Hashtable<>();
        int indexesPointer = buf.getInt();
        buf.position(indexesPointer);
        int indexesLength = buf.getInt();
        int i = 0;
        while (i < indexesLength) {
            key = buf.getInt();
            diacriticalText = readBoolean(buf);
            pointer = buf.getInt();
            i = (i + 9);
            _indexes.put(key, pointer);
            if(diacriticalText){
            	i += 4;
            	buf.getInt();
            }
        }
        init = true;
    }


    public static synchronized String getText(int key) {
    	if(!init)
    		init(Main.D2I_PATH);
        try {
            int pointer = _indexes.get(key);
            buf.position(pointer);
            return readUTF(buf);

        } catch (Exception e) {
            return null;
        }
    }

    public static boolean readBoolean(IoBuffer buf) {
        return buf.get() == 1;
    }

    public static String readUTF(IoBuffer buf) {
        int len = buf.getUnsignedShort();
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; ++i) {
        	bytes[i] = (byte) buf.getUnsigned();
        }
        return new String(bytes, StandardCharsets.UTF_8); 
    }
}
