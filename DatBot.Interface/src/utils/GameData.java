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
		System.out.println("Duration : " + (System.currentTimeMillis()  - time) + "ms");
	}
	
	private void init() throws Exception{
		System.out.println("Initializing...");
//		this.d2oManager = new D2oManager("D:\\Ankama\\Dofus2\\app\\data\\common\\Items.d2o");
//		items = d2oManager.returnJsonString();
		this.d2oManager = new D2oManager("D:\\Ankama\\Dofus2\\app\\data\\common\\MapPositions.d2o");
		mapPositions = d2oManager.returnJsonString();
		System.out.println("Initialized!");
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
			s = s.replaceAll("\n", "");
			String [] cmd = s.split(",");
			for(String si : cmd){
				String [] cmd2 = si.split(":");
				if(cmd2[0].equals("id")){
					if(Double.parseDouble(cmd2[1]) == mapId){
						for(String si1 : cmd){
							String [] cmd21 = si1.split(":");
							if(cmd21[0].equals("worldMap")){
								System.out.println(si);
								return Integer.parseInt(cmd21[1]);
							}
						}
					}
				}
			}
		}
		return -1;
	}

}
