package utils.d2p.informations;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import protocol.network.util.DofusDataReader;

public class D2PFileDlm {
	
    
    // Fields
    private Map<String, int[]> FilenameDataDictionnary;
    private DofusDataReader Reader;
    
    public D2PFileDlm(String D2pFilePath) throws IOException
    {
    	byte[] bytesFile = Files.readAllBytes(Paths.get(D2pFilePath));
        this.Reader = new DofusDataReader(new ByteArrayInputStream(bytesFile));
        FilenameDataDictionnary = new HashMap<String, int[]>();
        byte num = BigInteger.valueOf(Reader.readByte() + Reader.readByte()).byteValue();
        if (num == 3)
        {
        	this.Reader.setPosition(bytesFile.length - 24);
            int num2 = Reader.readInt();
            Reader.readInt();
            int num3 = Reader.readInt();
            int num4 = Reader.readInt();
            int num1 = Reader.readInt();
            int num9 = Reader.readInt();
            this.Reader.setPosition(num3);
            int num5 = num4;
            int i = 1;
            while (i <= num5)
            {
                String key = Reader.readUTF();
                int num7 = Reader.readInt() + num2;
                int num8 = Reader.readInt();
                this.FilenameDataDictionnary.put(key, new int[]
                {
                    num7,
                    num8
                });
                i += 1;
            }
        }
        this.Reader.dis.close();
        this.Reader.bis.close();
    }

    public boolean ExistsDlm(String DlmName)
    {
        return FilenameDataDictionnary.containsKey(DlmName);
    }
    
    public byte[] ReadFile(String fileName) throws IOException
    {
        int[] numArray = FilenameDataDictionnary.get(fileName);
        this.Reader.setPosition(numArray[0]);
        return Reader.readBytes(numArray[1]);
    }
}
