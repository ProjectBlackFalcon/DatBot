package protocol.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat.Field;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import protocol.network.util.DofusDataWriter;

public class Crypto {
	private static final int AES_KEY_LENGTH = 32;
	private static byte[] AES_KEY;
	
    private static final String publicKey = 
    		"MIIBUzANBgkqhkiG9w0BAQEFAAOCAUAAMIIBOwKCATIAgucoka9J2PXcNdjcu6CuDmgteIMB+rih"+
    		"2UZJIuSoNT/0J/lEKL/W4UYbDA4U/6TDS0dkMhOpDsSCIDpO1gPG6+6JfhADRfIJItyHZflyXNUj"+
    		"WOBG4zuxc/L6wldgX24jKo+iCvlDTNUedE553lrfSU23Hwwzt3+doEfgkgAf0l4ZBez5Z/ldp9it"+
    		"2NH6/2/7spHm0Hsvt/YPrJ+EK8ly5fdLk9cvB4QIQel9SQ3JE8UQrxOAx2wrivc6P0gXp5Q6bHQo"+
    		"ad1aUp81Ox77l5e8KBJXHzYhdeXaM91wnHTZNhuWmFS3snUHRCBpjDBCkZZ+CxPnKMtm2qJIi57R"+
    		"slALQVTykEZoAETKWpLBlSm92X/eXY2DdGf+a7vju9EigYbX0aXxQy2Ln2ZBWmUJyZE8B58CAwEA"+
    		"AQ==";
      
    public static byte[] encrypt(byte[] encryptedKey, String login, String password, String salt) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
        byte[] decryptedKey = decryptReceivedKey(encryptedKey);
        return encryptCredentials(decryptedKey, login, password, salt);
    }
    
    private static byte[] decryptReceivedKey(byte[] receivedKey) {
        byte[] resultKey = null;
        try {
            byte[] decodedKey = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            RSAPublicKey pk = (RSAPublicKey) kf.generatePublic(spec);
            
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, pk);  
            resultKey = cipher.doFinal(receivedKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultKey;
    }
    
    private static byte[] encryptCredentials(byte[] key, String login, String password, String salt) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
        byte[] encryptedCredentials = null;
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        DofusDataWriter buffer = new DofusDataWriter(ous);
        buffer.write(salt.getBytes());
        buffer.write(generateRandomAESKey());
        buffer.writeByte((byte)login.length());
        buffer.write(login.getBytes());
        buffer.write(password.getBytes());
        
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509 = new X509EncodedKeySpec(key);
            RSAPublicKey publicKey = (RSAPublicKey) kf.generatePublic(x509);
            
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedCredentials = cipher.doFinal(ous.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedCredentials;
    }
    
    private static byte[] generateRandomAESKey() throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        DofusDataWriter array = new DofusDataWriter(ous);
        for(int i = 0; i < AES_KEY_LENGTH; ++i)
            array.writeByte((byte)Math.floor(Math.random() * 256));
        AES_KEY = ous.toByteArray();
        return ous.toByteArray();
    }
    
    
    public static String decryptAESkey(byte[] encryptedData)
    {
        Cipher cipher = null;
        
        try
        {
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
            
        	byte[] iv = new byte[cipher.getBlockSize()];
        	System.arraycopy(AES_KEY, 0, iv, 0, 16);
        	IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        	
            SecretKeySpec secretKey = new SecretKeySpec(AES_KEY, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey,ivParameterSpec);
            byte[] resultbytes  = cipher.doFinal(encryptedData);
            return new String(resultbytes,Charset.forName("utf-8"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
    
	  //Simply takes every other two characters an terms them into a byte value 
	  //then stuffs them into  a byteArray
	public static byte[] convertToByteArray(String key)
	{
	    byte[] b = new byte[key.length()/2];
	
	    for(int i=0, bStepper=0; i<key.length()+2; i+=2)
	        if(i !=0)
	            b[bStepper++]=((byte) Integer.parseInt((key.charAt(i-2)+""+key.charAt(i-1)), 16));
	
	    return b;
	}
	
//	public static String bytesToString(byte[] bytes, String format, boolean spacer) {
//	    StringBuilder sb = new StringBuilder(bytes.length*2);
//	    for (byte b : bytes) {
//	        sb.append(String.format(format, b));
//	        if(spacer)
//	            sb.append(" ");
//	    }
//	    return sb.toString();
//	}


}
