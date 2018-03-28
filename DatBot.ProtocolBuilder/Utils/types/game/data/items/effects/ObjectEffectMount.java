package protocol.network.types.game.data.items.effects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.data.items.effects.ObjectEffect;
import protocol.network.types.game.data.items.effects.ObjectEffectInteger;

@SuppressWarnings("unused")
public class ObjectEffectMount extends ObjectEffect {
	public static final int ProtocolId = 179;

	private long id;
	private long expirationDate;
	private int model;
	private String name;
	private String owner;
	private int level;
	private boolean sex;
	private boolean isRideable;
	private boolean isFeconded;
	private boolean isFecondationReady;
	private int reproductionCount;
	private int reproductionCountMax;
	private List<ObjectEffectInteger> effects;
	private List<Integer> capacities;

	public long getId() { return this.id; }
	public void setId(long id) { this.id = id; };
	public long getExpirationDate() { return this.expirationDate; }
	public void setExpirationDate(long expirationDate) { this.expirationDate = expirationDate; };
	public int getModel() { return this.model; }
	public void setModel(int model) { this.model = model; };
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; };
	public String getOwner() { return this.owner; }
	public void setOwner(String owner) { this.owner = owner; };
	public int getLevel() { return this.level; }
	public void setLevel(int level) { this.level = level; };
	public boolean isSex() { return this.sex; }
	public void setSex(boolean sex) { this.sex = sex; };
	public boolean isIsRideable() { return this.isRideable; }
	public void setIsRideable(boolean isRideable) { this.isRideable = isRideable; };
	public boolean isIsFeconded() { return this.isFeconded; }
	public void setIsFeconded(boolean isFeconded) { this.isFeconded = isFeconded; };
	public boolean isIsFecondationReady() { return this.isFecondationReady; }
	public void setIsFecondationReady(boolean isFecondationReady) { this.isFecondationReady = isFecondationReady; };
	public int getReproductionCount() { return this.reproductionCount; }
	public void setReproductionCount(int reproductionCount) { this.reproductionCount = reproductionCount; };
	public int getReproductionCountMax() { return this.reproductionCountMax; }
	public void setReproductionCountMax(int reproductionCountMax) { this.reproductionCountMax = reproductionCountMax; };
	public List<ObjectEffectInteger> getEffects() { return this.effects; }
	public void setEffects(List<ObjectEffectInteger> effects) { this.effects = effects; };
	public List<Integer> getCapacities() { return this.capacities; }
	public void setCapacities(List<Integer> capacities) { this.capacities = capacities; };

	public ObjectEffectMount(){
	}

	public ObjectEffectMount(long id, long expirationDate, int model, String name, String owner, int level, boolean sex, boolean isRideable, boolean isFeconded, boolean isFecondationReady, int reproductionCount, int reproductionCountMax, List<ObjectEffectInteger> effects, List<Integer> capacities){
		this.id = id;
		this.expirationDate = expirationDate;
		this.model = model;
		this.name = name;
		this.owner = owner;
		this.level = level;
		this.sex = sex;
		this.isRideable = isRideable;
		this.isFeconded = isFeconded;
		this.isFecondationReady = isFecondationReady;
		this.reproductionCount = reproductionCount;
		this.reproductionCountMax = reproductionCountMax;
		this.effects = effects;
		this.capacities = capacities;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, sex);
			flag = BooleanByteWrapper.SetFlag(1, flag, isRideable);
			flag = BooleanByteWrapper.SetFlag(2, flag, isFeconded);
			flag = BooleanByteWrapper.SetFlag(3, flag, isFecondationReady);
			writer.writeByte(flag);
			writer.writeVarLong(this.id);
			writer.writeVarLong(this.expirationDate);
			writer.writeVarInt(this.model);
			writer.writeUTF(this.name);
			writer.writeUTF(this.owner);
			writer.writeByte(this.level);
			writer.writeVarInt(this.reproductionCount);
			writer.writeVarInt(this.reproductionCountMax);
			writer.writeShort(this.effects.size());
			int _loc2_ = 0;
			while( _loc2_ < this.effects.size()){
				this.effects.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.capacities.size());
			int _loc3_ = 0;
			while( _loc3_ < this.capacities.size()){
				writer.writeVarInt(this.capacities.get(_loc3_));
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.sex = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.isRideable = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.isFeconded = BooleanByteWrapper.GetFlag(flag, (byte) 2);
			this.isFecondationReady = BooleanByteWrapper.GetFlag(flag, (byte) 3);
			this.id = reader.readVarLong();
			this.expirationDate = reader.readVarLong();
			this.model = reader.readVarInt();
			this.name = reader.readUTF();
			this.owner = reader.readUTF();
			this.level = reader.readByte();
			this.reproductionCount = reader.readVarInt();
			this.reproductionCountMax = reader.readVarInt();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.effects = new ArrayList<ObjectEffectInteger>();
			while( _loc3_ <  _loc2_){
				ObjectEffectInteger _loc15_ = new ObjectEffectInteger();
				_loc15_.Deserialize(reader);
				this.effects.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.capacities = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarInt();
				this.capacities.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
