package utils.d2o.modules;

import java.util.Arrays;
import java.util.Vector;

import utils.d2i.d2iManager;
import utils.d2o.GameData;
import utils.d2o.GameDataFileAccessor;


public class Spell {
	public static final String MODULE = "Spells";
	
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
	public int descriptionId;
	public int typeId;
	public int order;
	public String scriptParams;
	public String scriptParamsCritical;
	public int scriptId;
	public int scriptIdCritical;
	public int iconId;
	public Vector<Integer> spellLevels;
	public boolean useParamCache = true;
	public boolean verbose_cast;
	public String default_zone;
	private String _name;
	private String _description;
	private SpellLevel[] _spellLevels;
	
	public static Spell getSpellById(int id){
		return (Spell) GameData.getObject(MODULE, id);
	}
	
	public static Object[] getSpells(){
		return GameData.getObjects(MODULE);
	}
	
	public String getName() {
		if(this._name == null)
			this._name = d2iManager.getText(this.nameId);
		return this._name;
	}
	
	public String getDescription() {
		if(this._description == null)
			this._description = d2iManager.getText(this.descriptionId);
		return this._description;
	}
	
	public SpellLevel getSpellLevel(int lvl){
		this.spellLevelsInfo();
		int index = 0;
		if(this.spellLevels.size() >= lvl && lvl > 0){
			index = lvl -1;
		}
		return (SpellLevel) this._spellLevels[index];
	}
	
	public SpellLevel[] spellLevelsInfo(){
		int levelCount = 0;
		if(this._spellLevels == null || this._spellLevels.length != this.spellLevels.size()){
			levelCount = this.spellLevels.size();
			this._spellLevels = new SpellLevel[levelCount];
			for(int i = 0; i < levelCount ; i++){
				this._spellLevels[i] = SpellLevel.getLevelById(this.spellLevels.get(i));
			}
		}
		return this._spellLevels;
	}

	@Override
	public String toString() {
		return "Spell [id=" + id + ", nameId=" + nameId + ", descriptionId=" + descriptionId + ", typeId=" + typeId + ", order=" + order + ", scriptParams=" + scriptParams + ", scriptParamsCritical=" + scriptParamsCritical + ", scriptId=" + scriptId + ", scriptIdCritical=" + scriptIdCritical + ", iconId=" + iconId + ", spellLevels=" + spellLevels
			+ ", useParamCache=" + useParamCache + ", verbose_cast=" + verbose_cast + ", default_zone=" + default_zone + ", _name=" + _name + ", _description=" + _description + ", _spellLevels=" + Arrays.toString(_spellLevels) + "]";
	}

}
