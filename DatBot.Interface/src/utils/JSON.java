package utils;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Game.Info;
import Game.Plugin.Interactive;
import Game.map.Cell;
import Game.map.CellData;
import Game.map.ColorMultiplicator;
import Game.map.Fixture;
import Game.map.Layer;
import Game.map.Map;
import Game.map.elements.GraphicalElement;

public class JSON implements Runnable{
	private String file;	
	private long id;
	
	// Map info
	public ArrayList<ArrayList<Integer>> cells = new ArrayList<ArrayList<Integer>>();
	
	// Name
	public static long nameId;
	public static String name;
	
	public JSON(String file,long id){
		this.file = file;
		this.id = id;
		run();
	}

	
	public JSON(String file, double id) {
		this.file = file;
		this.id = (long) id;
		run();
	}


	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		
        JSONParser parser = new JSONParser();
        try {
			JSONArray a;
			String s = Paths.get("").toAbsolutePath().toString();
			int i = s.indexOf("DatBot");
			s = s.substring(0, i + 7);

			switch(file){
			
			case "MapInfo" :
				a = (JSONArray) parser.parse(new FileReader(s + "\\Utils\\MapInfo.json"));
		        for (Object o : a)
		        {
		          JSONObject person = (JSONObject) o;
		          if((long) person.get("id") == id){
		        	  String [] temp = ((String) person.get("coord")).split(";");
		        	  Info.coords[0] = Integer.parseInt(temp[0]);
		        	  Info.coords[1] = Integer.parseInt(temp[1]);
		        	  Info.cells = (ArrayList<ArrayList<Integer>>) person.get("cells");
		        	  Info.worldmap = (long) person.get("worldMap");
		        	  cells = (ArrayList<ArrayList<Integer>>) person.get("cells");
		          }
		        }
				break;
			case "MapInfoComplete" :
				parseMapArray((JSONArray) parser.parse(new FileReader(s + "\\DatBot.Interface\\utils\\maps\\MapInfoComplete.json")));
				break;
			case "Name" :
				Object obj = parser.parse(new FileReader(s + "\\Utils\\Names.json"));
				JSONObject jsonObject =  (JSONObject) obj;
				JSONObject texts =  (JSONObject) jsonObject.get("texts");
	            name = (String) texts.get(String.valueOf(id));
	            break;
			case "Item" :
				a = (JSONArray) parser.parse(new FileReader(s + "\\Utils\\Items.json"));
		        for (Object o : a)
		        {
		          JSONObject person = (JSONObject) o;
		          if((long) person.get("id") == id){
		        	  nameId = (long) person.get("nameId");
		          }
		        }
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
	        foo.setLayers(parseLayersArray((JSONArray) object.get("Layers")));
	        foo.setLayersCount((long) object.get("LayersCount"));
	        list.add(foo);
	    }

	    return list;
	}
	
	private List<CellData> parseCellDataArray(JSONArray array) {

	    List<CellData> list = new ArrayList<CellData>();

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
	
	private List<Layer> parseLayersArray(JSONArray array) {

	    List<Layer> list = new ArrayList<Layer>();

	    for (Object item : array) {
	        JSONObject object = (JSONObject) item;
	        Layer obj = new Layer();
	        obj.setCellsCount((long) object.get("CellsCount"));
	        obj.setLayerId((long) object.get("LayerId"));
	        obj.setCells(parseCellArray((JSONArray) object.get("Cells")));
	        list.add(obj);
	    }
	    return list;
	}
	
	private List<Cell> parseCellArray(JSONArray array) {

	    List<Cell> list = new ArrayList<Cell>();

	    for (Object item : array) {
	        JSONObject object = (JSONObject) item;
	        Cell obj = new Cell();
	        obj.setCellId((long) object.get("CellId"));
	        obj.setElementsCount((long) object.get("ElementsCount"));
	        obj.setElements(parseElementArray((JSONArray) object.get("Elements")));
	        list.add(obj);
	    }
	    return list;
	}
	
	
	private List<GraphicalElement> parseElementArray(JSONArray array) {
		
	    List<GraphicalElement> list = new ArrayList<GraphicalElement>();

	    for (Object item : array) {
	        JSONObject object = (JSONObject) item;
	        GraphicalElement obj = new GraphicalElement();
	        obj.setAltitude((long) object.get("Altitude"));
	        obj.setElementId((long) object.get("ElementId"));
//	        obj.setFinalTeint((ColorMultiplicator) object.get("FinalTeint"));
//	        obj.setHue((ColorMultiplicator) object.get("Hue"));
	        obj.setIdentifier((long) object.get("Identifier"));
	        obj.setOffsetX((double) object.get("OffsetX"));
	        obj.setOffsetY((double) object.get("OffsetY"));
	        obj.setPixelOffsetX((double) object.get("PixelOffsetX"));
	        obj.setPixelOffsetY((double) object.get("PixelOffsetY"));
//	        obj.setShadow((ColorMultiplicator) object.get("Shadow"));
	        list.add(obj);
	    }
	    return list;	}	
}