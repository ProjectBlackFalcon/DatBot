package protocol.network.util;


import io.netty.util.internal.ThreadLocalRandom;

public class FlashKeyGenerator {
    public static String GetRandomFlashKey(String accountName)
    {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int seed = 0x2A;
        int num3 = 0;
        int length = accountName.length();
        while (num3 < length)
        {
            char ch = accountName.charAt(num3);
            seed = seed + (Character.getNumericValue(ch) - 3);
            num3 += 1;
        }
        String str3 = "";
        int num2 = 1;
        do
        {
        	str3 += str.charAt(ThreadLocalRandom.current().nextInt(0, str.length()));
            num2 += 1;
        } while (num2 <= 0x15);
        return str3;
    }
}
