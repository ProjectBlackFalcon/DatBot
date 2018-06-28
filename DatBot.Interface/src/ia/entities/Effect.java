package ia.entities;

public class Effect
{
    private boolean visibleInTooltip;

    private int random;

    private String rawZone;

    private long targetId;

    private String targetMask;

    private int effectId;

    private int diceNum;

    private long duration;

    private boolean visibleInFightLog;

    private long effectUid;

    private int diceSide;

    private int effectElement;

    public boolean isVisibleInTooltip() {
        return visibleInTooltip;
    }

    public void setVisibleInTooltip(boolean visibleInTooltip) {
        this.visibleInTooltip = visibleInTooltip;
    }

    public int getRandom() {
        return random;
    }

    public void setRandom(int random) {
        this.random = random;
    }

    public String getRawZone() {
        return rawZone;
    }

    public void setRawZone(String rawZone) {
        this.rawZone = rawZone;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public String getTargetMask() {
        return targetMask;
    }

    public void setTargetMask(String targetMask) {
        this.targetMask = targetMask;
    }

    public int getEffectId() {
        return effectId;
    }

    public void setEffectId(int effectId) {
        this.effectId = effectId;
    }

    public int getDiceNum() {
        return diceNum;
    }

    public void setDiceNum(int diceNum) {
        this.diceNum = diceNum;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isVisibleInFightLog() {
        return visibleInFightLog;
    }

    public void setVisibleInFightLog(boolean visibleInFightLog) {
        this.visibleInFightLog = visibleInFightLog;
    }

    public long getEffectUid() {
        return effectUid;
    }

    public void setEffectUid(long effectUid) {
        this.effectUid = effectUid;
    }

    public int getDiceSide() {
        return diceSide;
    }

    public void setDiceSide(int diceSide) {
        this.diceSide = diceSide;
    }

    public int getEffectElement() {
        return effectElement;
    }

    public void setEffectElement(int effectElement) {
        this.effectElement = effectElement;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isVisibleInBuffUi() {
        return visibleInBuffUi;
    }

    public void setVisibleInBuffUi(boolean visibleInBuffUi) {
        this.visibleInBuffUi = visibleInBuffUi;
    }

    public String getTriggers() {
        return triggers;
    }

    public void setTriggers(String triggers) {
        this.triggers = triggers;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    private int value;

    private boolean visibleInBuffUi;

    private String triggers;

    private long delay;

    private int group;


    @Override
    public String toString()
    {
        return "Effect [visibleInTooltip = "+visibleInTooltip+", random = "+random+", rawZone = "+rawZone+", targetId = "+targetId+", targetMask = "+targetMask+", effectId = "+effectId+", diceNum = "+diceNum+", duration = "+duration+", visibleInFightLog = "+visibleInFightLog+", effectUid = "+effectUid+", diceSide = "+diceSide+", effectElement = "+effectElement+", value = "+value+", visibleInBuffUi = "+visibleInBuffUi+", triggers = "+triggers+", delay = "+delay+", group = "+group+"]";
    }
}