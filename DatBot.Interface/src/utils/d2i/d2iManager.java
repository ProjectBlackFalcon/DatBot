package utils.d2i;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import org.apache.mina.core.buffer.IoBuffer;

import protocol.network.Network;

public class d2iManager {
	
//	public String getText(int id){
//		String s = "";
//		try {
//			RandomAccessFile f = new RandomAccessFile(Network.getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\i18n_fr.d2i", "r");
//			byte[] binary = new byte[(int)f.length()];
//			f.readFully(binary);
//	        IoBuffer buf = IoBuffer.wrap(binary);
//	        int key = 0;
//	        int pointer = 0;
//	        boolean diacriticalText;
//	        int indexesPointer = buf.getInt();
//	        buf.position(indexesPointer);
//	        int indexesLength = buf.getInt();
//
//	        int i = 0;
//	        while (i < indexesLength) {
//	            key = buf.getInt();
//	            diacriticalText = readBoolean(buf);
//	            pointer = buf.getInt();
//	            i = (i + 9);
//	            if(key == id){
//	                buf.position(pointer);
//	                s =  readUTF(buf);
//	            }
//	            if(diacriticalText){
//	            	i += 4;
//	            	buf.getInt();
//	            }
//	        };
//	        f.close();
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
//		return s;
//	}
//	
//    public boolean readBoolean(IoBuffer buf) {
//        return buf.get() == 1;
//    }
//
//    public String readUTF(IoBuffer buf) {
//        int len = buf.getUnsignedShort();
//        byte[] bytes = new byte[len];
//        for (int i = 0; i < len; ++i) {
//        	bytes[i] = (byte) buf.getUnsigned();
//        }
//        return new String(bytes, StandardCharsets.UTF_8); 
//    }

    public static IoBuffer buf;
    private static Hashtable _indexes;

    public d2iManager(String filePath) {

        try {

            long startAt = System.currentTimeMillis();
            byte[] binary = Files.readAllBytes(Paths.get(filePath));
            buf = IoBuffer.wrap(binary);
            this.Initialize(binary);
            Calendar target = Calendar.getInstance();
            String HOUR = Integer.toString(target.get(Calendar.HOUR_OF_DAY));
            HOUR = HOUR.length() == 1 ? ("0" + HOUR) : (HOUR);
            String MIN = Integer.toString(target.get(Calendar.MINUTE));
            MIN = MIN.length() == 1 ? ("0" + MIN) : (MIN);
            String SEC = Integer.toString(target.get(Calendar.SECOND));
            SEC = SEC.length() == 1 ? ("0" + SEC) : (SEC);
            System.out.println("[" + HOUR + ":" + MIN + ":" + SEC + "] " + filePath + " read in " + (System.currentTimeMillis() - startAt) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void Initialize(byte[] binary) {
        int key = 0;
        int pointer = 0;
        boolean diacriticalText;
        int position;
        String textKey;
        this._indexes = new Hashtable();
        int indexesPointer = buf.getInt();
        buf.position(indexesPointer);
        int indexesLength = buf.getInt();
        int i = 0;
        while (i < indexesLength) {
            key = buf.getInt();
            diacriticalText = readBoolean(buf);
            pointer = buf.getInt();
            this._indexes.put(key, pointer);
            if (diacriticalText) {
                i = (i + 4);
            } else {
            };
            i = (i + 9);
        };
    }

    public static String getText(int key) {
        try {
            int pointer = (int) _indexes.get(key);

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
