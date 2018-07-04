package ia.entities;

import java.util.Arrays;

public class Spell
{
	private int range;

	private boolean needTakenCell;

	private boolean needFreeTrapCell;

	private int maxStack;

	private int minRange;

	private int spellBreed;

	private int numberCasted = 0;
	private int maxCastPerTurn;

	private int id;

	private String name;

	private boolean rangeCanBeBoosted;

	private int grade;

	private boolean castTestLos;

	private int globalCooldown;

	private boolean hideEffects;

	private Effect[] criticalEffect;

	private int criticalHitProbability;

	private Effect[] effects;

	private int spellId;

	private boolean castInDiagonal;

	private int maxCastPerTarget;

	private int minPlayerLevel;

	private int initialCooldown;

	private int apCost;

	private boolean castInLine;

	private int turnLeftBeforeCast = 0;
	private int minCastInterval;

	private boolean hidden;

	private boolean needFreeCell;

	@Override
	public String toString()
	{
		return "Spell [ name = " +name+ ", range = "+range+", needTakenCell = "+needTakenCell+", needFreeTrapCell = "+needFreeTrapCell+", maxStack = "+maxStack+", minRange = "+minRange+", spellBreed = "+spellBreed+", maxCastPerTurn = "+maxCastPerTurn+", id = "+id+", rangeCanBeBoosted = "+rangeCanBeBoosted+", grade = "+grade+", castTestLos = "+castTestLos+", globalCooldown = "+globalCooldown+", hideEffects = "+hideEffects+", criticalEffect = "+ Arrays.toString(criticalEffect)+", criticalHitProbability = "+criticalHitProbability+", effects = "+Arrays.toString(effects)+", spellId = "+spellId+", castInDiagonal = "+castInDiagonal+", maxCastPerTarget = "+maxCastPerTarget+", minPlayerLevel = "+minPlayerLevel+", initialCooldown = "+initialCooldown+", apCost = "+apCost+", castInLine = "+castInLine+", minCastInterval = "+minCastInterval+", hidden = "+hidden+", needFreeCell = "+needFreeCell + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean isHideEffects() {
		return hideEffects;
	}

	public void setHideEffects(boolean hideEffects) {
		this.hideEffects = hideEffects;
	}

	public Effect[] getCriticalEffect() {
		return criticalEffect;
	}

	public void setCriticalEffect(Effect[] criticalEffect) {
		this.criticalEffect = criticalEffect;
	}

	public int getCriticalHitProbability() {
		return criticalHitProbability;
	}

	public void setCriticalHitProbability(int criticalHitProbability) {
		this.criticalHitProbability = criticalHitProbability;
	}

	public Effect[] getEffects() {
		return effects;
	}

	public void setEffects(Effect[] effects) {
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

	public int getTurnLeftBeforeCast() {
		return turnLeftBeforeCast;
	}

	public void setTurnLeftBeforeCast(int turnLeftBeforeCast) {
		this.turnLeftBeforeCast = turnLeftBeforeCast;
	}

	public int getNumberCasted() {
		return numberCasted;
	}

	public void setNumberCasted(int numberCasted) {
		this.numberCasted = numberCasted;
	}
}


