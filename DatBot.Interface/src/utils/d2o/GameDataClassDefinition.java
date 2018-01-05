package utils.d2o;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import protocol.network.util.DofusDataReader;

public class GameDataClassDefinition {

	 public String Name;
     public List<GameDataField> Fields;

     public GameDataClassDefinition(String packageName,String className)
     {
         Fields = new ArrayList<GameDataField>();
         Name = className;
     }

     public void AddField(DofusDataReader reader) throws IOException
     {
         GameDataField field = new GameDataField(reader);
         Fields.add(field);
     }
     
}
