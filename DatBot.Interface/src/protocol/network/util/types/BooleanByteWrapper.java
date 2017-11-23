package protocol.network.util.types;

public class BooleanByteWrapper {
	
    public static byte SetFlag(byte flag, byte offset, boolean value) throws Exception
    {
        if (offset >= 8)
            throw new Exception("offset must be lesser than 8");

        return value ? (byte) (flag | (1 << offset)) : (byte) (flag & (255 - (1 << offset)));
    }

    public static byte SetFlag(int flag, byte offset, boolean value) throws Exception
    {
        if (offset >= 8)
            throw new Exception("offset must be lesser than 8");

        return value ? (byte) (flag | (1 << offset)) : (byte) (flag & (255 - (1 << offset)));
    }

    public static boolean GetFlag(byte flag, byte offset) throws Exception
    {
        if (offset >= 8)
            throw new Exception("offset must be lesser than 8");

        return (flag & (byte) (1 << offset)) != 0;
    }

}
