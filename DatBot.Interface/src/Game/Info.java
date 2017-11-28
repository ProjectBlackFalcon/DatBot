package Game;

import java.util.ArrayList;
import java.util.List;

import protocol.network.types.game.context.roleplay.job.JobExperience;

public class Info {
	/*
	 * Game Info	 * 
	 */
	
	// Map info
	public static double mapId;
	public static int[] coords = new int[2];
	public static int cellId;
	public static ArrayList<ArrayList<Integer>> cells = new ArrayList<ArrayList<Integer>>();
	public static long worldmap;
	
	
	// Account info
	public static String nameAccount = "";
	public static String password = "";
	public static String name = "";
	public static String server = "";
	public static long actorId = -1;
	public static boolean isConnected = false;
	
	// Stats info
	public static int lvl = -1;
	public static int weigthMax;
	public static int weight;
	public static List<JobExperience> job = new ArrayList<JobExperience>();

	
	// Game utils
	public volatile static boolean waitForMov = true;
	public volatile static boolean waitForHarvestSuccess = false;
	public volatile static boolean waitForHarvestFailure = false;
	

}
