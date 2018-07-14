package gamedata.d2o.modules;

public class EffectInstanceDice extends EffectInstanceInteger {
	public int diceNum;
	public int diceSide;
	@Override
	public String toString() {
		return "EffectInstanceDice [diceNum=" + diceNum + ", diceSide=" + diceSide + ", value=" + value + ", effectUid=" + effectUid + ", spellId=" + spellId + ", effectId=" + effectId + ", targetId=" + targetId + ", targetMask=" + targetMask + ", duration=" + duration + ", delay=" + delay + ", random=" + random + ", group=" + group
			+ ", modificator=" + modificator + ", trigger=" + trigger + ", triggers=" + triggers + ", visibleInTooltip=" + visibleInTooltip + ", visibleInBuffUi=" + visibleInBuffUi + ", visibleInFightLog=" + visibleInFightLog + ", zoneSize=" + zoneSize + ", zoneShape=" + zoneShape + ", zoneMinSize=" + zoneMinSize + ", zoneEfficiencyPercent="
			+ zoneEfficiencyPercent + ", zoneMaxEfficiency=" + zoneMaxEfficiency + ", zoneStopAtTarget=" + zoneStopAtTarget + ", effectElement=" + effectElement + "]";
	}
	public int getDiceNum() {
		return diceNum;
	}
	public void setDiceNum(int diceNum) {
		this.diceNum = diceNum;
	}
	public int getDiceSide() {
		return diceSide;
	}
	public void setDiceSide(int diceSide) {
		this.diceSide = diceSide;
	}
}