package ia.fight.map;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ia.fight.brain.Game;
import utils.GameData;

public class CreateMap {

	public static Map getMapById(int map_id){
		JSONParser parser = new JSONParser();
		ArrayList<int[]> blocks = new ArrayList<>();
		try{
			String path = GameData.getPathDatBot() + "\\Model\\WreckThemFaces\\src\\ia\\fight\\data\\MapInfo.json";
			System.out.println(path);
			JSONArray arr = (JSONArray) parser.parse(new FileReader(path));
			JSONObject map;
			
			if(map_id == -1) {
				Random rand = new Random();
				map = (JSONObject) arr.get(rand.nextInt(arr.size()));
			}else {
				map = null;
				boolean found = false;
				int counter = 0;
				while(!found){
					try {
						map = (JSONObject) arr.get(++counter);
					}catch(java.lang.IndexOutOfBoundsException e) {
						System.err.println("Fatal Error : no map has this ID. ID : "+map_id);
						System.exit(0);
					}
					
					long id = (java.lang.Long) map.get("id");
					
					if(id == map_id) {
						found = true;
					}
				}
			}

			long id = (java.lang.Long) map.get("id");
			String coords = (String) map.get("coord");
			Game.log.println("Map id : "+id+", loc : "+coords);
			JSONArray cells = (JSONArray) map.get("cells");

			for(int i = 0; i < cells.size(); i++){
				JSONArray cellLine = (JSONArray) cells.get(i);
				
				for(int j = 0; j < cellLine.size(); j++) {
					Long value = new Long((long) cellLine.get(j));
					if(value == 1) {
						value = (long) 2;
					}else if(value == 2) {
						value = (long) 1;
					}
					
					int[] rot = rotate(new int[] {j, i});
					blocks.add(new int[] {rot[0], rot[1], value.intValue()});
				}
			}
			
			for(int i = 0; i <= 12; i++) {
				for(int j = 0; j <= 12-i; j++) {
					blocks.add(new int[] {i, j, 2});
				}
			}
			
			for(int i = 32; i >= 13; i--) {
				for(int j = 0; j <= 17-(32-i); j++) {
					blocks.add(new int[] {i, j, 2});
				}
			}
			
			for(int i = 0; i <= 18; i++) {
				for(int j = 32; j >= 14+i; j--) {
					blocks.add(new int[] {i, j, 2});
				}
			}
			
			for(int i = 21; i <= 32; i++) {
				for(int j = 32; j >= 32-(i-21); j--) {
					blocks.add(new int[] {i, j, 2});
				}
			}
			
		}catch(ParseException | IOException pe){
			pe.printStackTrace();
		}
		
		Map map = new Map(blocks);
		return map;
	}
	
	public static int[] rotate(int[] val) {
		int input_i = val[0];
		int input_j = val[1];
		
		int output_i, output_j;
		
		if(input_j % 2 == 0) {
			output_i = input_i + input_j / 2;
			output_j = 13 + (input_j / 2) - input_i;
		}else {
			output_i = 1 + input_i + input_j / 2;
			output_j = 13 + input_j / 2 - input_i;
		}

		if(output_i < 33 & output_j < 33) {
			return new int[] {output_i, output_j};
		}else {
			return new int[] {0, 0};
		}
	}
	
}
