package Game;

import java.util.ArrayList;

public class InfoAccount {
	/*
	 * Game Info	 * 
	 */
	public static String name = "Reiner";
	public static double mapId;
	public static ArrayList<ArrayList<Integer>> cells = new ArrayList<ArrayList<Integer>>();
	public static int[] coords = new int[2];
	public static int cellId;
	public static int lvl;
	public static long actorId;
	public volatile static boolean waitForMov = true;

}
