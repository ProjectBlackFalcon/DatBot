package utils.d2o.modules;

import java.util.Arrays;

import utils.d2i.d2iManager;
import utils.d2o.GameData;
import utils.d2o.GameDataFileAccessor;

public class SuperArea {
	public static final String MODULE = "SuperAreas";
	private static SuperArea[] _allSuperAreas;

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
	public int worldmapId;
	public boolean hasWorldMap;
	private String _name;
	private WorldMap _worldmap;

	public static SuperArea getSuperAreaById(int id) {
		return (SuperArea) GameData.getObject(MODULE, id);
	}

	public synchronized static SuperArea[] getAllSuperArea() {
		if(_allSuperAreas != null)
			return _allSuperAreas;
		Object[] objArray = GameData.getObjects(MODULE);
		_allSuperAreas = Arrays.copyOf(objArray, objArray.length, SuperArea[].class);
		return _allSuperAreas;
	}

	public String getName() {
		if(this._name == null)
			this._name = d2iManager.getText(this.nameId);
		return this._name;
	}

	public WorldMap getWorldMap() {
		if(this._worldmap == null) {
			if(!this.hasWorldMap)
				this.worldmapId = 1;
			this._worldmap = WorldMap.getWorldMapById(this.worldmapId);
		}
		return this._worldmap;	
	}
}