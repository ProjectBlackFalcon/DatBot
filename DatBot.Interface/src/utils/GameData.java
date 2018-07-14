package utils;

import java.nio.file.Paths;

import utils.d2o.modules.InfoMessage;
import utils.d2o.modules.Item;
import utils.d2o.modules.MapPosition;
import utils.d2o.modules.Monster;
import utils.d2o.modules.MountBehavior;
import utils.d2o.modules.Npc;
import utils.d2o.modules.PointOfInterest;
import utils.d2o.modules.Server;

public class GameData {

	public GameData() {
	}
	
	public static String getPathDatBot() {
		String s = Paths.get("").toAbsolutePath().toString();
		int i = s.indexOf("DatBot");
		if(i == -1){
            s = Paths.get("").toAbsolutePath().toString()+"/DatBot";
        }else{
            s = s.substring(0, i + 6);
        }
		return s;
	}
	
	public static String getClueName(int id){
		return PointOfInterest.getPointOfInterestById(id).getName();
	}
	
	public static String getItemName(int id){
		return Item.getItemById(id).getName();
	}
	
	public static String getMountBehaviorName(int id){
		return MountBehavior.getMountBehaviorById(id).getName();
	}
	
	public static String getNpcName(int id){
		return Npc.getNpcById(id).getName();
	}
	
	public static int getPodsFromItem(int id){
		return Item.getItemById(id).realWeight;
	}

	public static String getNameServer(int id){
		return Server.getServerById(id).getName();
	}
	
	public static String getTextInfo(int id){
		return InfoMessage.getInfoMessageById(id).getText();
	}
	
	public static boolean isMonsterArchi(int id){
		return Monster.getMonsterById(id).isMiniBoss;
	}
	
	public static String getMonsterName(int id){
		return Monster.getMonsterById(id).getName();
	}

	public static String getCoordMapString(double id) {
		return MapPosition.getMapPositionById(id).getCoordsString();
	}

}
