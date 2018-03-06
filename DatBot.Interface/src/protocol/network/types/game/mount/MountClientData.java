package protocol.network.types.game.mount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.NetworkMessage;
import protocol.network.types.game.data.items.effects.ObjectEffectInteger;

@SuppressWarnings("unused")
public class MountClientData extends NetworkMessage {
	public static final int ProtocolId = 178;

	private double id;
	private int model;
	private List<Integer> ancestor;
	private List<Integer> behaviors;
	private String name;
	private boolean sex;
	private int ownerId;
	private long experience;
	private long experienceForLevel;
	private double experienceForNextLevel;
	private int level;
	private boolean isRideable;
	private int maxPods;
	private boolean isWild;
	private int stamina;
	private int staminaMax;
	private int maturity;
	private int maturityForAdult;
	private int energy;
	private int energyMax;
	private int serenity;
	private int aggressivityMax;
	private int serenityMax;
	private int love;
	private int loveMax;
	private int fecondationTime;
	private boolean isFecondationReady;
	private int boostLimiter;
	private double boostMax;
	private int reproductionCount;
	private int reproductionCountMax;
	private int harnessGID;
	private boolean useHarnessColors;
	private List<ObjectEffectInteger> effectList;

	public double getId() { return this.id; };
	public void setId(double id) { this.id = id; };
	public int getModel() { return this.model; };
	public void setModel(int model) { this.model = model; };
	public List<Integer> getAncestor() { return this.ancestor; };
	public void setAncestor(List<Integer> ancestor) { this.ancestor = ancestor; };
	public List<Integer> getBehaviors() { return this.behaviors; };
	public void setBehaviors(List<Integer> behaviors) { this.behaviors = behaviors; };
	public String getName() { return this.name; };
	public void setName(String name) { this.name = name; };
	public boolean isSex() { return this.sex; };
	public void setSex(boolean sex) { this.sex = sex; };
	public int getOwnerId() { return this.ownerId; };
	public void setOwnerId(int ownerId) { this.ownerId = ownerId; };
	public long getExperience() { return this.experience; };
	public void setExperience(long experience) { this.experience = experience; };
	public long getExperienceForLevel() { return this.experienceForLevel; };
	public void setExperienceForLevel(long experienceForLevel) { this.experienceForLevel = experienceForLevel; };
	public double getExperienceForNextLevel() { return this.experienceForNextLevel; };
	public void setExperienceForNextLevel(double experienceForNextLevel) { this.experienceForNextLevel = experienceForNextLevel; };
	public int getLevel() { return this.level; };
	public void setLevel(int level) { this.level = level; };
	public boolean isIsRideable() { return this.isRideable; };
	public void setIsRideable(boolean isRideable) { this.isRideable = isRideable; };
	public int getMaxPods() { return this.maxPods; };
	public void setMaxPods(int maxPods) { this.maxPods = maxPods; };
	public boolean isIsWild() { return this.isWild; };
	public void setIsWild(boolean isWild) { this.isWild = isWild; };
	public int getStamina() { return this.stamina; };
	public void setStamina(int stamina) { this.stamina = stamina; };
	public int getStaminaMax() { return this.staminaMax; };
	public void setStaminaMax(int staminaMax) { this.staminaMax = staminaMax; };
	public int getMaturity() { return this.maturity; };
	public void setMaturity(int maturity) { this.maturity = maturity; };
	public int getMaturityForAdult() { return this.maturityForAdult; };
	public void setMaturityForAdult(int maturityForAdult) { this.maturityForAdult = maturityForAdult; };
	public int getEnergy() { return this.energy; };
	public void setEnergy(int energy) { this.energy = energy; };
	public int getEnergyMax() { return this.energyMax; };
	public void setEnergyMax(int energyMax) { this.energyMax = energyMax; };
	public int getSerenity() { return this.serenity; };
	public void setSerenity(int serenity) { this.serenity = serenity; };
	public int getAggressivityMax() { return this.aggressivityMax; };
	public void setAggressivityMax(int aggressivityMax) { this.aggressivityMax = aggressivityMax; };
	public int getSerenityMax() { return this.serenityMax; };
	public void setSerenityMax(int serenityMax) { this.serenityMax = serenityMax; };
	public int getLove() { return this.love; };
	public void setLove(int love) { this.love = love; };
	public int getLoveMax() { return this.loveMax; };
	public void setLoveMax(int loveMax) { this.loveMax = loveMax; };
	public int getFecondationTime() { return this.fecondationTime; };
	public void setFecondationTime(int fecondationTime) { this.fecondationTime = fecondationTime; };
	public boolean isIsFecondationReady() { return this.isFecondationReady; };
	public void setIsFecondationReady(boolean isFecondationReady) { this.isFecondationReady = isFecondationReady; };
	public int getBoostLimiter() { return this.boostLimiter; };
	public void setBoostLimiter(int boostLimiter) { this.boostLimiter = boostLimiter; };
	public double getBoostMax() { return this.boostMax; };
	public void setBoostMax(double boostMax) { this.boostMax = boostMax; };
	public int getReproductionCount() { return this.reproductionCount; };
	public void setReproductionCount(int reproductionCount) { this.reproductionCount = reproductionCount; };
	public int getReproductionCountMax() { return this.reproductionCountMax; };
	public void setReproductionCountMax(int reproductionCountMax) { this.reproductionCountMax = reproductionCountMax; };
	public int getHarnessGID() { return this.harnessGID; };
	public void setHarnessGID(int harnessGID) { this.harnessGID = harnessGID; };
	public boolean isUseHarnessColors() { return this.useHarnessColors; };
	public void setUseHarnessColors(boolean useHarnessColors) { this.useHarnessColors = useHarnessColors; };
	public List<ObjectEffectInteger> getEffectList() { return this.effectList; };
	public void setEffectList(List<ObjectEffectInteger> effectList) { this.effectList = effectList; };

	public MountClientData(){
	}

	public MountClientData(double id, int model, List<Integer> ancestor, List<Integer> behaviors, String name, boolean sex, int ownerId, long experience, long experienceForLevel, double experienceForNextLevel, int level, boolean isRideable, int maxPods, boolean isWild, int stamina, int staminaMax, int maturity, int maturityForAdult, int energy, int energyMax, int serenity, int aggressivityMax, int serenityMax, int love, int loveMax, int fecondationTime, boolean isFecondationReady, int boostLimiter, double boostMax, int reproductionCount, int reproductionCountMax, int harnessGID, boolean useHarnessColors, List<ObjectEffectInteger> effectList){
		this.id = id;
		this.model = model;
		this.ancestor = ancestor;
		this.behaviors = behaviors;
		this.name = name;
		this.sex = sex;
		this.ownerId = ownerId;
		this.experience = experience;
		this.experienceForLevel = experienceForLevel;
		this.experienceForNextLevel = experienceForNextLevel;
		this.level = level;
		this.isRideable = isRideable;
		this.maxPods = maxPods;
		this.isWild = isWild;
		this.stamina = stamina;
		this.staminaMax = staminaMax;
		this.maturity = maturity;
		this.maturityForAdult = maturityForAdult;
		this.energy = energy;
		this.energyMax = energyMax;
		this.serenity = serenity;
		this.aggressivityMax = aggressivityMax;
		this.serenityMax = serenityMax;
		this.love = love;
		this.loveMax = loveMax;
		this.fecondationTime = fecondationTime;
		this.isFecondationReady = isFecondationReady;
		this.boostLimiter = boostLimiter;
		this.boostMax = boostMax;
		this.reproductionCount = reproductionCount;
		this.reproductionCountMax = reproductionCountMax;
		this.harnessGID = harnessGID;
		this.useHarnessColors = useHarnessColors;
		this.effectList = effectList;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, sex);
			flag = BooleanByteWrapper.SetFlag(1, flag, isRideable);
			flag = BooleanByteWrapper.SetFlag(2, flag, isWild);
			flag = BooleanByteWrapper.SetFlag(3, flag, isFecondationReady);
			flag = BooleanByteWrapper.SetFlag(4, flag, useHarnessColors);
			writer.writeByte(flag);
			writer.writeDouble(this.id);
			writer.writeVarInt(this.model);
			writer.writeShort(this.ancestor.size());
			int _loc2_ = 0;
			while( _loc2_ < this.ancestor.size()){
				writer.writeInt(this.ancestor.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.behaviors.size());
			int _loc3_ = 0;
			while( _loc3_ < this.behaviors.size()){
				writer.writeInt(this.behaviors.get(_loc3_));
				_loc3_++;
			}
			writer.writeUTF(this.name);
			writer.writeInt(this.ownerId);
			writer.writeVarLong(this.experience);
			writer.writeVarLong(this.experienceForLevel);
			writer.writeDouble(this.experienceForNextLevel);
			writer.writeByte(this.level);
			writer.writeVarInt(this.maxPods);
			writer.writeVarInt(this.stamina);
			writer.writeVarInt(this.staminaMax);
			writer.writeVarInt(this.maturity);
			writer.writeVarInt(this.maturityForAdult);
			writer.writeVarInt(this.energy);
			writer.writeVarInt(this.energyMax);
			writer.writeInt(this.serenity);
			writer.writeInt(this.aggressivityMax);
			writer.writeVarInt(this.serenityMax);
			writer.writeVarInt(this.love);
			writer.writeVarInt(this.loveMax);
			writer.writeInt(this.fecondationTime);
			writer.writeInt(this.boostLimiter);
			writer.writeDouble(this.boostMax);
			writer.writeInt(this.reproductionCount);
			writer.writeVarInt(this.reproductionCountMax);
			writer.writeVarShort(this.harnessGID);
			writer.writeShort(this.effectList.size());
			int _loc4_ = 0;
			while( _loc4_ < this.effectList.size()){
				this.effectList.get(_loc4_).Serialize(writer);
				_loc4_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.sex = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.isRideable = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.isWild = BooleanByteWrapper.GetFlag(flag, (byte) 2);
			this.isFecondationReady = BooleanByteWrapper.GetFlag(flag, (byte) 3);
			this.useHarnessColors = BooleanByteWrapper.GetFlag(flag, (byte) 4);
			this.id = reader.readDouble();
			this.model = reader.readVarInt();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.ancestor = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readInt();
				this.ancestor.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.behaviors = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readInt();
				this.behaviors.add(_loc16_);
				_loc5_++;
			}
			this.name = reader.readUTF();
			this.ownerId = reader.readInt();
			this.experience = reader.readVarLong();
			this.experienceForLevel = reader.readVarLong();
			this.experienceForNextLevel = reader.readDouble();
			this.level = reader.readByte();
			this.maxPods = reader.readVarInt();
			this.stamina = reader.readVarInt();
			this.staminaMax = reader.readVarInt();
			this.maturity = reader.readVarInt();
			this.maturityForAdult = reader.readVarInt();
			this.energy = reader.readVarInt();
			this.energyMax = reader.readVarInt();
			this.serenity = reader.readInt();
			this.aggressivityMax = reader.readInt();
			this.serenityMax = reader.readVarInt();
			this.love = reader.readVarInt();
			this.loveMax = reader.readVarInt();
			this.fecondationTime = reader.readInt();
			this.boostLimiter = reader.readInt();
			this.boostMax = reader.readDouble();
			this.reproductionCount = reader.readInt();
			this.reproductionCountMax = reader.readVarInt();
			this.harnessGID = reader.readVarShort();
			int _loc6_  = reader.readShort();
			int _loc7_  = 0;
			this.effectList = new ArrayList<ObjectEffectInteger>();
			while( _loc7_ <  _loc6_){
				ObjectEffectInteger _loc17_ = new ObjectEffectInteger();
				_loc17_.Deserialize(reader);
				this.effectList.add(_loc17_);
				_loc7_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
