package utils.d2o.modules;

import java.util.Vector;

import utils.d2i.d2iManager;
import utils.d2o.GameData;
import utils.d2o.GameDataFileAccessor;

public class Monster {
	
	public static final String MODULE = "Monsters";
	
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
	public int gfxId;
	public int race;
	public Vector<MonsterGrade> grades;
	public String look;
	public boolean useSummonSlot;
	public boolean useBombSlot;
	public boolean canPlay;
	public boolean canTackle;
	public Vector<AnimFunMonsterData> animFunList;
	public boolean isBoss;
	public Vector<MonsterDrop> drops;
	public Vector<Integer> subareas;
	public Vector<Integer> spells;
	public int favoriteSubareaId;
	public boolean isMiniBoss;
	public boolean isQuestMonster;
	public int correspondingMiniBossId;
	public Integer speedAdjust = 0;
	public int creatureBoneId;
	public boolean canBePushed;
	public boolean fastAnimsFun;
	public boolean canSwitchPos;
	public Vector<Integer> incompatibleIdols;
	public boolean allIdolsDisabled;
	public boolean dareAvailable;
	public Vector<Integer> incompatibleChallenges;
	public boolean useRaceValues;
	public int aggressiveZoneSize;
	public int aggressiveLevelDiff;
	public String aggressiveImmunityCriterion;
	public int aggressiveAttackDelay;
	private String _name;
	
	public static Monster getMonsterById(int id){
		return (Monster) GameData.getObject(MODULE, id);
	}
	
	public static Object[] getMonsters(){
		return GameData.getObjects(MODULE);
	}
	
	public String getName() {
		if(this._name == null)
			this._name = d2iManager.getText(this.nameId);
		return this._name;
	}

	@Override
	public String toString() {
		return "Monster [id=" + id + ", nameId=" + nameId + ", gfxId=" + gfxId + ", race=" + race + ", grades=" + grades + ", look=" + look + ", useSummonSlot=" + useSummonSlot + ", useBombSlot=" + useBombSlot + ", canPlay=" + canPlay + ", canTackle=" + canTackle + ", animFunList=" + animFunList + ", isBoss=" + isBoss + ", drops=" + drops
			+ ", subareas=" + subareas + ", spells=" + spells + ", favoriteSubareaId=" + favoriteSubareaId + ", isMiniBoss=" + isMiniBoss + ", isQuestMonster=" + isQuestMonster + ", correspondingMiniBossId=" + correspondingMiniBossId + ", speedAdjust=" + speedAdjust + ", creatureBoneId=" + creatureBoneId + ", canBePushed=" + canBePushed
			+ ", fastAnimsFun=" + fastAnimsFun + ", canSwitchPos=" + canSwitchPos + ", incompatibleIdols=" + incompatibleIdols + ", allIdolsDisabled=" + allIdolsDisabled + ", dareAvailable=" + dareAvailable + ", incompatibleChallenges=" + incompatibleChallenges + ", useRaceValues=" + useRaceValues + ", aggressiveZoneSize=" + aggressiveZoneSize
			+ ", aggressiveLevelDiff=" + aggressiveLevelDiff + ", aggressiveImmunityCriterion=" + aggressiveImmunityCriterion + ", aggressiveAttackDelay=" + aggressiveAttackDelay + ", _name=" + _name + "]";
	}
}
