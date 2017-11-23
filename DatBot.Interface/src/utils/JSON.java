package utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Game.InfoAccount;
import Game.map.CellData;
import Game.map.Fixture;
import Game.map.Layer;
import Game.map.Map;

public class JSON implements Runnable{
	private String file;
	
	// Map position
	private long id;
	
	// Map info
	public ArrayList<ArrayList<Integer>> cells = new ArrayList<ArrayList<Integer>>();
	

	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		
        JSONParser parser = new JSONParser();
        try {
			JSONArray a;

			switch(file){
			
			case "MapInfo" :

				a = (JSONArray) parser.parse(new FileReader("C:\\Users\\baptiste\\Documents\\Dofus Bot\\MapInfo.json"));
		        for (Object o : a)
		        {
		          JSONObject person = (JSONObject) o;
		          if((long) person.get("id") == id){
		        	  String [] temp = ((String) person.get("coord")).split(";");
		        	  InfoAccount.coords[0] = Integer.parseInt(temp[0]);
		        	  InfoAccount.coords[1] = Integer.parseInt(temp[1]);
		        	  InfoAccount.cells = (ArrayList<ArrayList<Integer>>) person.get("cells");
		        	  cells = (ArrayList<ArrayList<Integer>>) person.get("cells");

		          }
		        }
				break;
			case "MapInfo2" :
				parseMapArray((JSONArray) parser.parse(new FileReader("C:\\Users\\baptiste\\Documents\\Dofus Bot\\MapInfo2.json")));
				break;
		}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	private List<Map> parseMapArray(JSONArray array) {

	    List<Map> list = new ArrayList<>();

	    for (Object item : array) {
	        JSONObject object = (JSONObject) item;
	        Map foo = new Map();
	        foo.setId((long) object.get("Id"));
	        foo.setIsUsingNewMovementSystem((boolean) object.get("IsUsingNewMovementSystem"));
	        foo.setRelativeId((long) object.get("RelativeId"));
	        foo.setLeftNeighbourId((long) object.get("LeftNeighbourId"));
	        foo.setTopNeighbourId((long) object.get("TopNeighbourId"));
	        foo.setBottomNeighbourId((long) object.get("BottomNeighbourId"));
	        foo.setRightNeighbourId((long) object.get("RightNeighbourId"));
	        foo.setUseLowPassFilter((boolean) object.get("UseLowPassFilter"));
	        foo.setUseReverb((boolean) object.get("UseReverb"));
	        foo.setCells(parseCellDataArray((JSONArray) object.get("Cells")));
	        list.add(foo);
	    }

	    return list;
	}
	
	private List<CellData> parseCellDataArray(JSONArray array) {

	    List<CellData> list = new ArrayList<>();

	    for (Object item : array) {
	        JSONObject object = (JSONObject) item;
	        CellData obj = new CellData();
	        obj.setArrow((long) object.get("Arrow"));
	        obj.setBlue((boolean) object.get("Blue"));
	        obj.setFarmCell((boolean) object.get("FarmCell"));
	        obj.setFloor((long) object.get("Floor"));
	        obj.setHavenbagCell((boolean) object.get("HavenbagCell"));
	        obj.setLos((boolean) object.get("Los"));
	        obj.setLosmov((long) object.get("Losmov"));
	        obj.setMapChangeData((long) object.get("MapChangeData"));
	        obj.setMov((boolean) object.get("Mov"));
	        obj.setMoveZone((long) object.get("MoveZone"));
	        obj.setNonWalkableDuringFight((boolean) object.get("NonWalkableDuringFight"));
	        obj.setNonWalkableDuringRP((boolean) object.get("NonWalkableDuringRP"));
	        obj.setRed((boolean) object.get("Red"));
	        obj.setSpeed((long) object.get("Speed"));
	        obj.setVisible((boolean) object.get("Visible"));
	        list.add(obj);
	    }

	    return list;
	}
	
	
	public JSON(String file,double mapId){
		this.file = file;
		this.id = (long) mapId;
		run();
	}
	
}