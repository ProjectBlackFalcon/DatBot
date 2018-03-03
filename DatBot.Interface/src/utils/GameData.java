package utils;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;

import protocol.network.Network;
import utils.d2i.d2iManager;
import utils.d2o.D2oManager;

public class GameData {

	public GameData() {
	}
	
	public static String getPathDatBot() {
		JSONArray a;
		String s = Paths.get("").toAbsolutePath().toString();
		int i = s.indexOf("DatBot");
		if(i == -1){
            s = Paths.get("").toAbsolutePath().toString()+"\\DatBot";
        }else{
            s = s.substring(0, i + 6);
        }
		return s;
	}

	public static int getWorldMap(double mapId) {
		try {
			D2oManager d2oManager = new D2oManager(GameData.getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\MapPositions.d2o");
			String s = d2oManager.searchObjectById((int) mapId);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			String[] cmd = s.split(",");
			for (String si : cmd) {
				String[] cmd2 = si.split(":");
				if (cmd2[0].equals("worldMap")) { return (int) Double.parseDouble(cmd2[1]); }
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -99;
	}
	
	public static int[] getCoordMap(int mapId){
		try {
			D2oManager d2oManager = new D2oManager(getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\MapPositions.d2o");
			String s = d2oManager.searchObjectById((int) mapId);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			s = s.replaceAll(",", ":");
			String[] cmd2 = s.split(":");
			return new int[] {Integer.parseInt(cmd2[3]),Integer.parseInt(cmd2[5])};
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static int getSpellNameId(int id) {
		return getDataFromFile(id,"Spells");
	}

	public static int getWeaponNameId(int id) {
		return getDataFromFile(id,"Items");
	}
	
	public static String getClueName(int id){
		return d2iManager.getText(getDataFromFile(id,"PointOfInterest"));
	}
	
	public static String getItemName(int id){
		return d2iManager.getText(getDataFromFile(id,"Items"));
	}
	
	public static String getNpcName(int id){
		return d2iManager.getText(getDataFromFile(id,"Npcs"));
	}
	
	public static String getPodsFromItem(int id){
		return getDataFromFile(id,"Items","realWeight");
	}
	
	public static String getNameServer(int id){
		return d2iManager.getText(getDataFromFile(id,"Servers"));
	}
	

	
	private static String getDataFromFile(int id, String file, String value) {
		D2oManager d2oManager;
		try {
			d2oManager = new D2oManager(getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\" + file + ".d2o");
			String s = d2oManager.searchObjectById(id);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			String[] cmd = s.split(",");
			for (String si : cmd) {
				String[] cmd2 = si.split(":");
				if (cmd2[0].equals(value)) { return cmd2[1]; }
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static int getDataFromFile(int id, String file) {
		D2oManager d2oManager;
		try {
			d2oManager = new D2oManager(getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\" + file + ".d2o");
			String s = d2oManager.searchObjectById(id);
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			s = s.replaceAll("\n", "");
			String[] cmd = s.split(",");
			for (String si : cmd) {
				String[] cmd2 = si.split(":");
				if (cmd2[0].equals("nameId")) { return Integer.parseInt(cmd2[1]); }
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return -9999;
	}

}
