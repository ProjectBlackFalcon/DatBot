package utils;

import java.util.List;

import protocol.network.Network;
import utils.d2o.D2oManager;

public class GameData{
	
	private D2oManager d2oManager;
	private static List<String> items;
	private static List<String> mapPositions;

	
	public GameData()
	{
		long time = System.currentTimeMillis();
		try
		{
			init();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Network.append("Duration : " + (System.currentTimeMillis()  - time) + "ms");
	}
	
	private void init() throws Exception{
		Network.append("Initializing...");
//		this.d2oManager = new D2oManager("D:\\Ankama\\Dofus2\\app\\data\\common\\Items.d2o");
//		items = d2oManager.returnJsonString();
		this.d2oManager = new D2oManager("D:\\Ankama\\Dofus2\\app\\data\\common\\MapPositions.d2o");
		mapPositions = d2oManager.returnJsonString();
		Network.append("Initialized!");
	}

	public static List<String> getItems()
	{
		return items;
	}

	public static List<String> getMapPositions()
	{
		return mapPositions;
	}
	
	public static int getWorldMap(double mapId){
		for(String s : mapPositions){
			s = s.replace("{", "");
			s = s.replace(" ", "");
			s = s.replace("}", "");
			String [] cmd = s.split("");
			for(String si : cmd){
				String [] cmd2 = si.split(":");
				if(cmd2[0].equals("id")){
					return Integer.parseInt(cmd2[1]);
				}
			}
		}
		return -1;
	}

}
