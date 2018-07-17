package utils.d2o.modules;

import java.util.Arrays;
import java.util.Vector;

import utils.d2i.d2iManager;
import utils.d2o.GameData;
import utils.d2o.GameDataFileAccessor;

public class ItemSet {
	public static final String MODULE = "ItemSets";

	static {
		try {
			GameDataFileAccessor.getInstance().init(MODULE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int id;
	public Vector<Integer> items;
	public int nameId;
	public Vector<Vector<EffectInstance>> effects;
	public boolean bonusIsSecret;
	private String _name;

	public static ItemSet getItemSetById(int id) {
		return (ItemSet) GameData.getObject(MODULE, id);
	}
	
	public static ItemSet[] getItemSets() {
		Object[] objArray = GameData.getObjects(MODULE);
		return Arrays.copyOf(objArray, objArray.length, ItemSet[].class);
	}

	public String getName() {
		if(this._name == null)
			this._name = d2iManager.getText(this.nameId);
		return this._name;
	}
}