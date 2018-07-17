package utils.d2o.modules;

import java.util.Vector;

import utils.d2o.GameData;
import utils.d2o.GameDataFileAccessor;

public class SpellLevel {
	public static final String MODULE = "SpellLevels";
	
	static {
		try {
			GameDataFileAccessor.getInstance().init(MODULE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int range;
	public boolean needTakenCell;
	public boolean needFreeTrapCell;
	public int maxStack;
	public int minRange;
	public int spellBreed;
	public int numberCasted = 0;
	public int maxCastPerTurn;
	public int id;
	public boolean rangeCanBeBoosted;
	public int grade;
	public boolean castTestLos;
	public int globalCooldown;
	public boolean playAnimation;
	public boolean hideEffects;
	public Vector<Integer> statesRequired;
	public Vector<Integer> statesAuthorized;
	public Vector<Integer> statesForbidden;
	public Vector<String> additionalEffectsZones;
	public Vector<EffectInstanceDice> criticalEffect;
	public int criticalHitProbability;
	public Vector<EffectInstanceDice> effects;
	public int spellId;
	public boolean castInDiagonal;
	public int maxCastPerTarget;
	public int minPlayerLevel;
	public int initialCooldown;
	public int apCost;
	public boolean castInLine;
	public int turnLeftBeforeCast = 0;
	public int minCastInterval;
	public boolean hidden;
	public boolean needFreeCell;
	
	public static SpellLevel getLevelById(int id){
		return (SpellLevel) GameData.getObject(MODULE, id);
	}
	
	public Spell getSpell(){
		return Spell.getSpellById(this.spellId);
	}

	@Override
	public String toString() {
		return "SpellLevel [range=" + range + ", needTakenCell=" + needTakenCell + ", needFreeTrapCell=" + needFreeTrapCell + ", maxStack=" + maxStack + ", minRange=" + minRange + ", spellBreed=" + spellBreed + ", numberCasted=" + numberCasted + ", maxCastPerTurn=" + maxCastPerTurn + ", id=" + id + ", rangeCanBeBoosted="
			+ rangeCanBeBoosted + ", grade=" + grade + ", castTestLos=" + castTestLos + ", globalCooldown=" + globalCooldown + ", playAnimation=" + playAnimation + ", hideEffects=" + hideEffects + ", statesRequired=" + statesRequired + ", statesAuthorized=" + statesAuthorized + ", statesForbidden=" + statesForbidden + ", additionalEffectsZones="
			+ additionalEffectsZones + ", criticalEffect=" + criticalEffect + ", criticalHitProbability=" + criticalHitProbability + ", effects=" + effects + ", spellId=" + spellId + ", castInDiagonal=" + castInDiagonal + ", maxCastPerTarget=" + maxCastPerTarget + ", minPlayerLevel=" + minPlayerLevel + ", initialCooldown=" + initialCooldown
			+ ", apCost=" + apCost + ", castInLine=" + castInLine + ", turnLeftBeforeCast=" + turnLeftBeforeCast + ", minCastInterval=" + minCastInterval + ", hidden=" + hidden + ", needFreeCell=" + needFreeCell + "]";
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public boolean isNeedTakenCell() {
		return needTakenCell;
	}

	public void setNeedTakenCell(boolean needTakenCell) {
		this.needTakenCell = needTakenCell;
	}

	public boolean isNeedFreeTrapCell() {
		return needFreeTrapCell;
	}

	public void setNeedFreeTrapCell(boolean needFreeTrapCell) {
		this.needFreeTrapCell = needFreeTrapCell;
	}

	public int getMaxStack() {
		return maxStack;
	}

	public void setMaxStack(int maxStack) {
		this.maxStack = maxStack;
	}

	public int getMinRange() {
		return minRange;
	}

	public void setMinRange(int minRange) {
		this.minRange = minRange;
	}

	public int getSpellBreed() {
		return spellBreed;
	}

	public void setSpellBreed(int spellBreed) {
		this.spellBreed = spellBreed;
	}

	public int getNumberCasted() {
		return numberCasted;
	}

	public void setNumberCasted(int numberCasted) {
		this.numberCasted = numberCasted;
	}

	public int getMaxCastPerTurn() {
		return maxCastPerTurn;
	}

	public void setMaxCastPerTurn(int maxCastPerTurn) {
		this.maxCastPerTurn = maxCastPerTurn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isRangeCanBeBoosted() {
		return rangeCanBeBoosted;
	}

	public void setRangeCanBeBoosted(boolean rangeCanBeBoosted) {
		this.rangeCanBeBoosted = rangeCanBeBoosted;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public boolean isCastTestLos() {
		return castTestLos;
	}

	public void setCastTestLos(boolean castTestLos) {
		this.castTestLos = castTestLos;
	}

	public int getGlobalCooldown() {
		return globalCooldown;
	}

	public void setGlobalCooldown(int globalCooldown) {
		this.globalCooldown = globalCooldown;
	}

	public boolean isPlayAnimation() {
		return playAnimation;
	}

	public void setPlayAnimation(boolean playAnimation) {
		this.playAnimation = playAnimation;
	}

	public boolean isHideEffects() {
		return hideEffects;
	}

	public void setHideEffects(boolean hideEffects) {
		this.hideEffects = hideEffects;
	}

	public Vector<Integer> getStatesRequired() {
		return statesRequired;
	}

	public void setStatesRequired(Vector<Integer> statesRequired) {
		this.statesRequired = statesRequired;
	}

	public Vector<Integer> getStatesAuthorized() {
		return statesAuthorized;
	}

	public void setStatesAuthorized(Vector<Integer> statesAuthorized) {
		this.statesAuthorized = statesAuthorized;
	}

	public Vector<Integer> getStatesForbidden() {
		return statesForbidden;
	}

	public void setStatesForbidden(Vector<Integer> statesForbidden) {
		this.statesForbidden = statesForbidden;
	}

	public Vector<String> getAdditionalEffectsZones() {
		return additionalEffectsZones;
	}

	public void setAdditionalEffectsZones(Vector<String> additionalEffectsZones) {
		this.additionalEffectsZones = additionalEffectsZones;
	}

	public Vector<EffectInstanceDice> getCriticalEffect() {
		return criticalEffect;
	}

	public void setCriticalEffect(Vector<EffectInstanceDice> criticalEffect) {
		this.criticalEffect = criticalEffect;
	}

	public int getCriticalHitProbability() {
		return criticalHitProbability;
	}

	public void setCriticalHitProbability(int criticalHitProbability) {
		this.criticalHitProbability = criticalHitProbability;
	}

	public Vector<EffectInstanceDice> getEffects() {
		return effects;
	}

	public void setEffects(Vector<EffectInstanceDice> effects) {
		this.effects = effects;
	}

	public int getSpellId() {
		return spellId;
	}

	public void setSpellId(int spellId) {
		this.spellId = spellId;
	}

	public boolean isCastInDiagonal() {
		return castInDiagonal;
	}

	public void setCastInDiagonal(boolean castInDiagonal) {
		this.castInDiagonal = castInDiagonal;
	}

	public int getMaxCastPerTarget() {
		return maxCastPerTarget;
	}

	public void setMaxCastPerTarget(int maxCastPerTarget) {
		this.maxCastPerTarget = maxCastPerTarget;
	}

	public int getMinPlayerLevel() {
		return minPlayerLevel;
	}

	public void setMinPlayerLevel(int minPlayerLevel) {
		this.minPlayerLevel = minPlayerLevel;
	}

	public int getInitialCooldown() {
		return initialCooldown;
	}

	public void setInitialCooldown(int initialCooldown) {
		this.initialCooldown = initialCooldown;
	}

	public int getApCost() {
		return apCost;
	}

	public void setApCost(int apCost) {
		this.apCost = apCost;
	}

	public boolean isCastInLine() {
		return castInLine;
	}

	public void setCastInLine(boolean castInLine) {
		this.castInLine = castInLine;
	}

	public int getTurnLeftBeforeCast() {
		return turnLeftBeforeCast;
	}

	public void setTurnLeftBeforeCast(int turnLeftBeforeCast) {
		this.turnLeftBeforeCast = turnLeftBeforeCast;
	}

	public int getMinCastInterval() {
		return minCastInterval;
	}

	public void setMinCastInterval(int minCastInterval) {
		this.minCastInterval = minCastInterval;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isNeedFreeCell() {
		return needFreeCell;
	}

	public void setNeedFreeCell(boolean needFreeCell) {
		this.needFreeCell = needFreeCell;
	}
	
	
}
