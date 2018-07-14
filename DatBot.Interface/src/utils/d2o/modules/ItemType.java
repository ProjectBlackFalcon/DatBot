package utils.d2o.modules;

import java.util.Arrays;

import utils.d2i.d2iManager;
import utils.d2o.GameData;
import utils.d2o.GameDataFileAccessor;

public class ItemType {
	public static final String MODULE = "ItemTypes";

	static {
		try {
			GameDataFileAccessor.getInstance().init(MODULE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int _zoneSize = 0xFFFFFFFF;
	private int _zoneShape = 0xFFFFFFFF;
	private int _zoneMinSize = 0xFFFFFFFF;
	public int id;
	public int nameId;
	public int superTypeId;
	public boolean plural;
	public int gender;
	public String rawZone;
	public boolean mimickable;
	public int craftXpRatio;
	private String _name;

	public static ItemType getItemTypeById(int id) {
		return (ItemType) GameData.getObject(MODULE, id);
	}
	
	public static ItemType[] getItemTypes() {
		Object[] objArray = GameData.getObjects(MODULE);
		return Arrays.copyOf(objArray, objArray.length, ItemType[].class);
	}

	public String getName() {
		if(this._name == null)
			this._name = d2iManager.getText(this.nameId);
		return this._name;
	}
	
	public int getZoneSize() {
		if(this._zoneSize == Integer.MAX_VALUE * 2)
			parseZone();
		return this._zoneSize;
	}
	
	public int getZoneShape() {
		if(this._zoneShape == Integer.MAX_VALUE * 2)
			parseZone();
		return this._zoneShape;
	}
	
	public int getZoneMinSize() {
		if(this._zoneMinSize == Integer.MAX_VALUE * 2)
			parseZone();
		return this._zoneMinSize;
	}
	
	private void parseZone() {
		if(this.rawZone != null && this.rawZone.length() > 0) {
			this._zoneShape = this.rawZone.charAt(0);
			String[] strArray = this.rawZone.substring(1).split(",");
			if(strArray.length > 0)
				this._zoneSize = Integer.valueOf(strArray[0]);
			else
				this._zoneSize = 0;
			if(strArray.length > 1)
				this._zoneMinSize = Integer.valueOf(strArray[1]);
			else
				this._zoneMinSize = 0;
		}
	}
}