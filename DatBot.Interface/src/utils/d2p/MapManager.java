package utils.d2p;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DeflaterInputStream;
import java.util.zip.InflaterInputStream;

import protocol.network.util.DofusDataReader;
import utils.d2p.informations.D2pFileManager;
import utils.d2p.map.Map;


public class MapManager {

    private D2pFileManager D2pFileManager;
    private java.util.Map<Integer,Map> MapId_Map;
    private Object CheckLock;

    public Map FromId(int id) throws IOException
    {
        if (this.MapId_Map.size() > 20)
            this.MapId_Map.remove(0);

        for (int i = 0; i < this.MapId_Map.size() ; i++){
        	if(this.MapId_Map.get(i).getId() == id){
        		return this.MapId_Map.get(i);
        	}
        }


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
            this.MapId_Map.put(id, map);
            mapBytes = new byte[mapBytes.length];
            return map;
        }
        this.MapId_Map.put(id, null);
        return null;
    }
    
    public MapManager(String directory) throws IOException
    {
        MapId_Map = new java.util.HashMap<Integer, Map>();
        D2pFileManager = new D2pFileManager(directory);
        CheckLock = new Object();
    }


}
