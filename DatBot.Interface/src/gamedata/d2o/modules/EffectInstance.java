package gamedata.d2o.modules;

@SuppressWarnings("unused")
public class EffectInstance {
	private static final String UNKNOWN_NAME = "???";
	private static final int UNDEFINED_CATEGORY = -2;
	private static final int UNDEFINED_SHOW = -1;
	private static final String UNDEFINED_DESCRIPTION = "undefined";

	public int effectUid;
	public int spellId;
	public int effectId;
	public int targetId;
	public String targetMask;
	public int duration;
	public int delay;
	public int random;
	public int group;
	public int modificator;
	public boolean trigger;
	public String triggers;
	public boolean visibleInTooltip = true;
	public boolean visibleInBuffUi = true;
	public boolean visibleInFightLog = true;
	public Object zoneSize;
	public int zoneShape;
	public Object zoneMinSize;
	public Object zoneEfficiencyPercent;
	public Object zoneMaxEfficiency;
	public Object zoneStopAtTarget;
	public int effectElement;
	private Effect _effectData;
	private int _durationStringValue;
	private int _delayStringValue;
	private String _durationString;
	private int _order = 0;
	private int _bonusType = -2;
	private int _oppositeId = -1;
	private int _category = -2;
	private String _description = "undefined";
	private String _theoricDescription = "undefined";
	private int _showSet = -1;
	private boolean _rawZoneInit;
	private String rawZone;
	public int getEffectUid() {
		return effectUid;
	}
	public void setEffectUid(int effectUid) {
		this.effectUid = effectUid;
	}
	public int getSpellId() {
		return spellId;
	}
	public void setSpellId(int spellId) {
		this.spellId = spellId;
	}
	public int getEffectId() {
		return effectId;
	}
	public void setEffectId(int effectId) {
		this.effectId = effectId;
	}
	public int getTargetId() {
		return targetId;
	}
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}
	public String getTargetMask() {
		return targetMask;
	}
	public void setTargetMask(String targetMask) {
		this.targetMask = targetMask;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	public int getRandom() {
		return random;
	}
	public void setRandom(int random) {
		this.random = random;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public int getModificator() {
		return modificator;
	}
	public void setModificator(int modificator) {
		this.modificator = modificator;
	}
	public boolean isTrigger() {
		return trigger;
	}
	public void setTrigger(boolean trigger) {
		this.trigger = trigger;
	}
	public String getTriggers() {
		return triggers;
	}
	public void setTriggers(String triggers) {
		this.triggers = triggers;
	}
	public boolean isVisibleInTooltip() {
		return visibleInTooltip;
	}
	public void setVisibleInTooltip(boolean visibleInTooltip) {
		this.visibleInTooltip = visibleInTooltip;
	}
	public boolean isVisibleInBuffUi() {
		return visibleInBuffUi;
	}
	public void setVisibleInBuffUi(boolean visibleInBuffUi) {
		this.visibleInBuffUi = visibleInBuffUi;
	}
	public boolean isVisibleInFightLog() {
		return visibleInFightLog;
	}
	public void setVisibleInFightLog(boolean visibleInFightLog) {
		this.visibleInFightLog = visibleInFightLog;
	}
	public Object getZoneSize() {
		return zoneSize;
	}
	public void setZoneSize(Object zoneSize) {
		this.zoneSize = zoneSize;
	}
	public int getZoneShape() {
		return zoneShape;
	}
	public void setZoneShape(int zoneShape) {
		this.zoneShape = zoneShape;
	}
	public Object getZoneMinSize() {
		return zoneMinSize;
	}
	public void setZoneMinSize(Object zoneMinSize) {
		this.zoneMinSize = zoneMinSize;
	}
	public Object getZoneEfficiencyPercent() {
		return zoneEfficiencyPercent;
	}
	public void setZoneEfficiencyPercent(Object zoneEfficiencyPercent) {
		this.zoneEfficiencyPercent = zoneEfficiencyPercent;
	}
	public Object getZoneMaxEfficiency() {
		return zoneMaxEfficiency;
	}
	public void setZoneMaxEfficiency(Object zoneMaxEfficiency) {
		this.zoneMaxEfficiency = zoneMaxEfficiency;
	}
	public Object getZoneStopAtTarget() {
		return zoneStopAtTarget;
	}
	public void setZoneStopAtTarget(Object zoneStopAtTarget) {
		this.zoneStopAtTarget = zoneStopAtTarget;
	}
	public int getEffectElement() {
		return effectElement;
	}
	public void setEffectElement(int effectElement) {
		this.effectElement = effectElement;
	}
	public Effect get_effectData() {
		return _effectData;
	}
	public void set_effectData(Effect _effectData) {
		this._effectData = _effectData;
	}
	public int get_durationStringValue() {
		return _durationStringValue;
	}
	public void set_durationStringValue(int _durationStringValue) {
		this._durationStringValue = _durationStringValue;
	}
	public int get_delayStringValue() {
		return _delayStringValue;
	}
	public void set_delayStringValue(int _delayStringValue) {
		this._delayStringValue = _delayStringValue;
	}
	public String get_durationString() {
		return _durationString;
	}
	public void set_durationString(String _durationString) {
		this._durationString = _durationString;
	}
	public int get_order() {
		return _order;
	}
	public void set_order(int _order) {
		this._order = _order;
	}
	public int get_bonusType() {
		return _bonusType;
	}
	public void set_bonusType(int _bonusType) {
		this._bonusType = _bonusType;
	}
	public int get_oppositeId() {
		return _oppositeId;
	}
	public void set_oppositeId(int _oppositeId) {
		this._oppositeId = _oppositeId;
	}
	public int get_category() {
		return _category;
	}
	public void set_category(int _category) {
		this._category = _category;
	}
	public String get_description() {
		return _description;
	}
	public void set_description(String _description) {
		this._description = _description;
	}
	public String get_theoricDescription() {
		return _theoricDescription;
	}
	public void set_theoricDescription(String _theoricDescription) {
		this._theoricDescription = _theoricDescription;
	}
	public int get_showSet() {
		return _showSet;
	}
	public void set_showSet(int _showSet) {
		this._showSet = _showSet;
	}
	public boolean is_rawZoneInit() {
		return _rawZoneInit;
	}
	public void set_rawZoneInit(boolean _rawZoneInit) {
		this._rawZoneInit = _rawZoneInit;
	}
	public String getRawZone() {
		return rawZone;
	}
	public void setRawZone(String rawZone) {
		this.rawZone = rawZone;
	}
}