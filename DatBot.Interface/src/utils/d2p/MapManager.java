package utils.d2p;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;

import main.Main;
import protocol.network.util.DofusDataReader;
import utils.d2p.informations.D2pFileManager;
import utils.d2p.map.Map;


public class MapManager {

    private static D2pFileManager D2pFileManager;
    private static boolean init = false;
    
    public static void init(String directory){
    	try {
			D2pFileManager = new D2pFileManager(directory);
			init = true;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static synchronized Map FromId(int id) throws IOException
    {
    	if(!init)
    		init(Main.D2P_PATH);
        String str = id % 10 + "/" + id + ".dlm";
        byte[] mapBytes = D2pFileManager.GetMapBytes(str);
        if (mapBytes != null)
        {
            ByteArrayInputStream stream = new ByteArrayInputStream(D2pFileManager.GetMapBytes(str));
            stream.mark(2);
            InflaterInputStream stream2 = new InflaterInputStream(stream);
            byte[] buffer = new byte[8192];
            ByteArrayOutputStream destination = new ByteArrayOutputStream();
            int read;
            while ((read = stream2.read(buffer, 0, buffer.length)) > 0)
                destination.write(buffer, 0, read);
            DofusDataReader reader = new DofusDataReader(new ByteArrayInputStream(destination.toByteArray()));
            Map map = new Map();
            map.Init(reader);
            mapBytes = new byte[mapBytes.length];
            destination.close();
            stream.close();
            stream2.close();
            reader.bis.close();
            reader.dis.close();
            return map;
        }
        return null;
    }    
    

}
