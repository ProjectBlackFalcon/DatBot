package Utilitaires;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ProtocolBuilder.Field;
import ProtocolBuilder.Message;

public class JSON implements Runnable{
	// Protocol builder
	public List<Message> Messages;
	public List<Message> Types;

	@Override
	public void run() {
		
        JSONParser parser = new JSONParser();
        try {
			JSONObject obj = (JSONObject) parser.parse(new FileReader("C:\\Users\\baptiste\\Documents\\Dofus Bot\\DofusBuilder.json"));
			Messages = parseJsonBuilderArray((JSONArray) obj.get("Messages"));
			Types = parseJsonBuilderArray((JSONArray) obj.get("Types"));
			for (Object object : types) {
				System.out.println(object);
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	private List<Message> parseJsonBuilderArray(JSONArray array) {

	    List<Message> list = new ArrayList<>();

	    for (Object item : array) {
	        JSONObject object = (JSONObject) item;
	        Message msg = new Message();
	        msg.setName((String) object.get("Name"));
	        msg.setNamespace((String) object.get("Namespace"));
	        msg.setParents((String) object.get("Parent"));
	        msg.setProtocolId((long) object.get("ProtocolID"));
//	        System.out.println("Name : " + msg.getName() + " - ID : " + msg.getProtocolId() + " - Namespace : " + msg.getNamespace().substring(30));
	        msg.setFields(parseFieldArray((JSONArray) object.get("Fields")));
	        msg.setUseHashFunc((boolean) object.get("UseHashFunc"));
	        list.add(msg);
	    }

	    return list;
	}
	
	public List<String> types = new ArrayList<String>();
	
	private List<Field> parseFieldArray(JSONArray array) {

	    List<Field> list = new ArrayList<>();
	    
	    if (array == null) return null;

	    for (Object item : array) {
	        JSONObject object = (JSONObject) item;
	        Field obj = new Field();
	        obj.setName((String) object.get("Name")); 
	        obj.setType((String) object.get("Type")); 
	        obj.setWriteMethod((String) object.get("WriteMethod")); 
	        if(!types.contains(obj.getWriteMethod())){
	        	types.add(obj.getWriteMethod());
	        }
	        obj.setMethod((String) object.get("Method")); 
	        obj.setIsVector((boolean) object.get("IsVector"));
	        obj.setDynamicLength((boolean) object.get("IsDynamicLength"));
	        obj.setLength((long) object.get("Length")); 
	        obj.setWriteLengthMethod((String) object.get("WriteLengthMethod")); 
	        obj.setUseTypeManager((boolean) object.get("UseTypeManager"));
	        obj.setUseBBW((boolean) object.get("UseBBW"));
	        obj.setBbwPosition((long) object.get("BBWPosition")); 
	        list.add(obj);
	    }

	    return list;
	}
	
	
	
	public JSON(){
		run();
	}
	
}