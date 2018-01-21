package utils.d2i;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import org.apache.mina.core.buffer.IoBuffer;

import protocol.network.Network;

/**
 *
 * @author Neo-Craft
 */
public class d2iManager {

    private String fileName;
    public static IoBuffer buf;

    private static Hashtable _indexes;
    private Hashtable _unDiacriticalIndex;
    private Hashtable _textIndexes;
    private Hashtable _textIndexesOverride;
    private Hashtable _textSortIndex;
    private int _startTextIndex = 4;
    public Map<String, String> _aLang = new HashMap<>();
    private int _textCount;

    public d2iManager(String filePath) {

        try {

            this.fileName = filePath;
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
            System.out.println("[" + HOUR + ":" + MIN + ":" + SEC + "] " + filePath + " readed in " + (System.currentTimeMillis() - startAt) + "ms");
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
        this._unDiacriticalIndex = new Hashtable();
        this._textIndexes = new Hashtable();
        this._textIndexesOverride = new Hashtable();
        this._textSortIndex = new Hashtable();
        this._textCount = 0;

        int indexesPointer = buf.getInt();
        int keyCount = 0;
        buf.position(indexesPointer);
        int indexesLength = buf.getInt();
        int i = 0;
        while (i < indexesLength) {
            key = buf.getInt();
            diacriticalText = readBoolean(buf);
            pointer = buf.getInt();
            this._indexes.put(key, pointer);
            keyCount++;
            if (diacriticalText) {
                keyCount++;
                i = (i + 4);
                this._unDiacriticalIndex.put(key, buf.getInt());
            } else {
                this._unDiacriticalIndex.put(key, pointer);
            };
            i = (i + 9);
        };
        indexesLength = this.buf.getInt();
        while (indexesLength > 0) {
            position = this.buf.position();
            textKey = readUTF(buf);
            pointer = buf.getInt();
            this._textCount++;
            this._textIndexes.put(textKey, pointer);
            indexesLength = (indexesLength - (this.buf.position() - position));
        };
        indexesLength = this.buf.getInt();
        i = 0;
        while (indexesLength > 0) {
            position = this.buf.position();
            this._textSortIndex.put(buf.getInt(), ++i);
            indexesLength = (indexesLength - (this.buf.position() - position));
        };
        Set<String> keys = this._textIndexes.keySet();
        for (String textKeya : keys) {
            setEntry(textKeya, this.getNamedText(textKeya));
        };
    }

    public void setEntry(String Key, String sValue) {
        this._aLang.put(Key, sValue);
    }

    public String getNamedText(String textKey) {
        if (this._textIndexesOverride.get(textKey) != null) {
            textKey = (String) this._textIndexesOverride.get(textKey);
        };
        int pointer;
        try {
            pointer = (int) this._textIndexes.get(textKey);
        } catch (Exception e) {
            return null;
        }
        this.buf.position(pointer);
        return (readUTF(buf));
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

    public String getUnDiacriticalText(int key) {
        try {
            int pointer = (int) this._unDiacriticalIndex.get(key);
            this.buf.position(pointer);
            return readUTF(buf);

        } catch (Exception e) {
            return null;
        }
    }

    public String getNamedText(int textKey) {

        try {

            if (this._textIndexesOverride.get(textKey) != null) {
                textKey = (int) this._textIndexesOverride.get(textKey);
            }
            int pointer = (int) this._textIndexes.get(textKey);
            this.buf.position(pointer);
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
