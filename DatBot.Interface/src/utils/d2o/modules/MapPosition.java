package utils.d2o.modules;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

import utils.d2i.d2iManager;
import utils.d2o.GameData;
import utils.d2o.GameDataFileAccessor;

public class MapPosition {
	public static final String MODULE = "MapPositions";
    private static final int CAPABILITY_ALLOW_CHALLENGE = 1;
    private static final int CAPABILITY_ALLOW_AGGRESSION = 2;
    private static final int CAPABILITY_ALLOW_TELEPORT_TO = 4;
    private static final int CAPABILITY_ALLOW_TELEPORT_FROM = 8;
    private static final int CAPABILITY_ALLOW_EXCHANGES_BETWEEN_PLAYERS = 16;
    private static final int CAPABILITY_ALLOW_HUMAN_VENDOR = 32;
    private static final int CAPABILITY_ALLOW_COLLECTOR = 64;
    private static final int CAPABILITY_ALLOW_SOUL_CAPTURE = 128;
    private static final int CAPABILITY_ALLOW_SOUL_SUMMON = 0x0100;
    private static final int CAPABILITY_ALLOW_TAVERN_REGEN = 0x0200;
    private static final int CAPABILITY_ALLOW_TOMB_MODE = 0x0400;
    private static final int CAPABILITY_ALLOW_TELEPORT_EVERYWHERE = 0x0800;
    private static final int CAPABILITY_ALLOW_FIGHT_CHALLENGES = 0x1000;
    private static Hashtable<Double, MapPosition> _mapPositions;
    
	static {
		try {
			GameDataFileAccessor.getInstance().init(MODULE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public double id;
    public int posX;
    public int posY;
    public boolean outdoor;
    public boolean allowPrism;
    public boolean isTransition;
    public int tacticalModeTemplateId;
    public int capabilities;
    public int nameId;
    public boolean showNameOnFingerpost;
    public Vector<AmbientSound> sounds;
    public Vector<Vector<Integer>> playlists;
    public int subAreaId;
    public int worldMap;
    public boolean hasPriorityOnWorldmap;
    private String _name;
    private SubArea _subArea;
    
    public synchronized static MapPosition getMapPositionById(double id) {
    	return (MapPosition) GameData.getObject(MODULE, (int) id);
    }
    
    public static MapPosition[] getMapPositions() {
    	Object[] objArray = GameData.getObjects(MODULE);
        return Arrays.copyOf(objArray, objArray.length, MapPosition[].class);
    }
    
    public static Vector<Integer> getMapIdByCoord(int x, int y) {
    	MapCoordinates mc = MapCoordinates.getMapCoordinatesByCoords(x, y);
    	if(mc != null)
    		return mc.mapIds;
    	return null;
    }
    
    public static Vector<MapPosition> getMapPositionByCoord(int x, int y) {
    	MapCoordinates mc = MapCoordinates.getMapCoordinatesByCoords(x, y);
    	if(mc != null)
    		return mc.getMaps();
    	return null;
    }
    
    public String getName() {
    	if(this._name == null)
    		this._name = d2iManager.getText(this.nameId);
    	return this._name;
    }
    
    public SubArea getSubArea() {
        if (this._subArea == null)
            this._subArea = SubArea.getSubAreaById(this.subAreaId);
        return this._subArea;
    }
    
    public int[] getCoords(){
    	return new int[] {this.posX,this.posY};
    }
    
    public String getCoordsString(){
    	return "[" + this.posX + "," + this.posY + "]";
    }
    
    public boolean allowChallenge() {
        return !((this.capabilities & CAPABILITY_ALLOW_CHALLENGE) == 0);
    }
    
    public boolean allowAggression() {
        return !((this.capabilities & CAPABILITY_ALLOW_AGGRESSION) == 0);
    }
    
    public boolean allowTeleportTo() {
        return !((this.capabilities & CAPABILITY_ALLOW_TELEPORT_TO) == 0);
    }
    
    public boolean allowTeleportFrom() {
        return !((this.capabilities & CAPABILITY_ALLOW_TELEPORT_FROM) == 0);
    }
    
    public boolean allowExchanges() {
        return !((this.capabilities & CAPABILITY_ALLOW_EXCHANGES_BETWEEN_PLAYERS) == 0);
    }
    
    public boolean allowHumanVendor() {
        return !((this.capabilities & CAPABILITY_ALLOW_HUMAN_VENDOR) == 0);
    }
    
    public boolean allowTaxCollector() {
        return !((this.capabilities & CAPABILITY_ALLOW_COLLECTOR) == 0);
    }
    
    public boolean allowSoulCapture() {
        return !((this.capabilities & CAPABILITY_ALLOW_SOUL_CAPTURE) == 0);
    }
    
    public boolean allowSoulSummon() {
        return !((this.capabilities & CAPABILITY_ALLOW_SOUL_SUMMON) == 0);
    }
    
    public boolean allowTavernRegen() {
        return !((this.capabilities & CAPABILITY_ALLOW_TAVERN_REGEN) == 0);
    }
    
    public boolean allowTombMode() {
        return !((this.capabilities & CAPABILITY_ALLOW_TOMB_MODE) == 0);
    }
    
    public boolean allowTeleportEverywhere() {
        return !((this.capabilities & CAPABILITY_ALLOW_TELEPORT_EVERYWHERE) == 0);
    }
    
    public boolean allowFightChallenges() {
        return !((this.capabilities & CAPABILITY_ALLOW_FIGHT_CHALLENGES) == 0);
    }
    
    public String toString() {
    	return "MapPosition : " + this.id + " [" + this.posX + ", " + this.posY + "]";
    }
}