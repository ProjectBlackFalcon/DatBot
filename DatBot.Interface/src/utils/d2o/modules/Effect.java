package utils.d2o.modules;

import utils.d2i.d2iManager;
import utils.d2o.GameData;
import utils.d2o.GameDataFileAccessor;

public class Effect {
	public static final String MODULE = "Effects";

	static {
		try {
			GameDataFileAccessor.getInstance().init(MODULE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int id;
	public int descriptionId;
	public int iconId;
	public int characteristic;
	public int category;
	public String operator;
	public boolean showInTooltip;
	public boolean useDice;
	public boolean forceMinMax;
	public boolean boost;
	public boolean active;
	public int oppositeId;
	public int theoreticalDescriptionId;
	public int theoreticalPattern;
	public boolean showInSet;
	public int bonusType;
	public boolean useInFight;
	public int effectPriority;
	public int elementId;
	private String _description;
	private String _theoricDescription;

	public static Effect getEffectById(int id) {
		return (Effect) GameData.getObject(MODULE, id);
	}

	public String getDescription() {
		if(this._description == null)
			this._description = d2iManager.getText(this.descriptionId);
		return this._description;
	}
	public String getTheoreticalDescription() {
		if(this._theoricDescription == null)
			this._theoricDescription = d2iManager.getText(this.theoreticalDescriptionId);
		return this._theoricDescription;
	}
}