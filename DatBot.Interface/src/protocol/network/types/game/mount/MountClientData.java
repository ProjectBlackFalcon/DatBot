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

	public double id;
	public int model;
	public List<Integer> ancestor;
	public List<Integer> behaviors;
	public String name;
	public boolean sex;
	public int ownerId;
	public long experience;
	public long experienceForLevel;
	public double experienceForNextLevel;
	public int level;
	public boolean isRideable;
	public int maxPods;
	public boolean isWild;
	public int stamina;
	public int staminaMax;
	public int maturity;
	public int maturityForAdult;
	public int energy;
	public int energyMax;
	public int serenity;
	public int aggressivityMax;
	public int serenityMax;
	public int love;
	public int loveMax;
	public int fecondationTime;
	public boolean isFecondationReady;
	public int boostLimiter;
	public double boostMax;
	public int reproductionCount;
	public int reproductionCountMax;
	public int harnessGID;
	public boolean useHarnessColors;
	public List<ObjectEffectInteger> effectList;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("id : " + this.id);
		//Network.appendDebug("model : " + this.model);
		//for(Integer a : ancestor) {
			//Network.appendDebug("ancestor : " + a);
		//}
		//for(Integer a : behaviors) {
			//Network.appendDebug("behaviors : " + a);
		//}
		//Network.appendDebug("name : " + this.name);
		//Network.appendDebug("sex : " + this.sex);
		//Network.appendDebug("ownerId : " + this.ownerId);
		//Network.appendDebug("experience : " + this.experience);
		//Network.appendDebug("experienceForLevel : " + this.experienceForLevel);
		//Network.appendDebug("experienceForNextLevel : " + this.experienceForNextLevel);
		//Network.appendDebug("level : " + this.level);
		//Network.appendDebug("isRideable : " + this.isRideable);
		//Network.appendDebug("maxPods : " + this.maxPods);
		//Network.appendDebug("isWild : " + this.isWild);
		//Network.appendDebug("stamina : " + this.stamina);
		//Network.appendDebug("staminaMax : " + this.staminaMax);
		//Network.appendDebug("maturity : " + this.maturity);
		//Network.appendDebug("maturityForAdult : " + this.maturityForAdult);
		//Network.appendDebug("energy : " + this.energy);
		//Network.appendDebug("energyMax : " + this.energyMax);
		//Network.appendDebug("serenity : " + this.serenity);
		//Network.appendDebug("aggressivityMax : " + this.aggressivityMax);
		//Network.appendDebug("serenityMax : " + this.serenityMax);
		//Network.appendDebug("love : " + this.love);
		//Network.appendDebug("loveMax : " + this.loveMax);
		//Network.appendDebug("fecondationTime : " + this.fecondationTime);
		//Network.appendDebug("isFecondationReady : " + this.isFecondationReady);
		//Network.appendDebug("boostLimiter : " + this.boostLimiter);
		//Network.appendDebug("boostMax : " + this.boostMax);
		//Network.appendDebug("reproductionCount : " + this.reproductionCount);
		//Network.appendDebug("reproductionCountMax : " + this.reproductionCountMax);
		//Network.appendDebug("harnessGID : " + this.harnessGID);
		//Network.appendDebug("useHarnessColors : " + this.useHarnessColors);
		//for(ObjectEffectInteger a : effectList) {
			//Network.appendDebug("effectList : " + a);
		//}
	//}
}