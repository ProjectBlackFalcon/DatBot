package Game;

import java.util.ArrayList;

public class Info {
	/*
	 * Game Info	 * 
	 */
	
	// Map info
	public static double mapId = 8.4675589E7;
	public static int[] coords = new int[2];
	public static int cellId = -1;
	public static ArrayList<ArrayList<Integer>> cells = new ArrayList<ArrayList<Integer>>();
	public static long worldmap = -9999;
	
	
	//Account info
	public static String nameAccount = "";
	public static String password = "";
	public static String name = "";
	public static String server = "";
	public static int lvl = -1;
	public static long actorId = -1;
	public static boolean isConnected = false;
	
	public volatile static boolean waitForMov = true;
	
	

}
