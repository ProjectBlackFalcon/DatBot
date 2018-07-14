package gamedata.d2o.modules;

import gamedata.d2o.GameData;
import gamedata.d2o.GameDataFileAccessor;
import utils.d2i.d2iManager;

import java.util.Arrays;

public class Area {
	public static final String MODULE = "Areas";
    private static Area[] _allAreas;
    
	static {
		try {
			GameDataFileAccessor.getInstance().init(MODULE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

    public int id;
    public int nameId;
    public int superAreaId;
    public boolean containHouses;
    public boolean containPaddocks;
    public Rectangle bounds;
    public int worldmapId;
    public boolean hasWorldMap;
    private String _name;
    private SuperArea _superArea;
    private boolean _hasVisibleSubAreas;
    private boolean _hasVisibleSubAreasInitialized;
    private WorldMap _worldMap;
    
    public static Area getAreaById(int id) {
    	Area area = (Area) GameData.getObject(MODULE, id);
    	if(area == null || area.getSuperArea() == null || !area.hasVisibleSubAreas())
    		return null;
    	return area;
    }
    
    public synchronized static Area[] getAllArea() {
    	if(_allAreas != null)
    		return _allAreas;
    	Object[] objArray = GameData.getObjects(MODULE);
    	_allAreas = Arrays.copyOf(objArray, objArray.length, Area[].class);
    	return _allAreas;
    }
    
    public String getName() {
    	if(this._name == null)
    		this._name = d2iManager.getText(this.nameId);
    	return this._name;
    }
    
    public SuperArea getSuperArea() {
    	if(this._superArea == null)
    		this._superArea = SuperArea.getSuperAreaById(this.superAreaId);
    	return this._superArea;
    }
    
    public boolean hasVisibleSubAreas() {
    	if(!this._hasVisibleSubAreasInitialized) {
    		this._hasVisibleSubAreas = true;
    		this._hasVisibleSubAreasInitialized = true;
    	}
    	return this._hasVisibleSubAreas;
    }
    
    public WorldMap getWorldMap() {
    	if(this._worldMap == null) {
    		if(!hasWorldMap)
    			this._worldMap = getSuperArea().getWorldMap();
    		else
    			this._worldMap = WorldMap.getWorldMapById(this.worldmapId);
    	}
    	return this._worldMap;
    }
}