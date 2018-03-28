package protocol.network.messages.game.guild;

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

@SuppressWarnings("unused")
public class GuildInfosUpgradeMessage extends NetworkMessage {
	public static final int ProtocolId = 5636;

	private int maxTaxCollectorsCount;
	private int taxCollectorsCount;
	private int taxCollectorLifePoints;
	private int taxCollectorDamagesBonuses;
	private int taxCollectorPods;
	private int taxCollectorProspecting;
	private int taxCollectorWisdom;
	private int boostPoints;
	private List<Integer> spellId;
	private List<Integer> spellLevel;

	public int getMaxTaxCollectorsCount() { return this.maxTaxCollectorsCount; }
	public void setMaxTaxCollectorsCount(int maxTaxCollectorsCount) { this.maxTaxCollectorsCount = maxTaxCollectorsCount; };
	public int getTaxCollectorsCount() { return this.taxCollectorsCount; }
	public void setTaxCollectorsCount(int taxCollectorsCount) { this.taxCollectorsCount = taxCollectorsCount; };
	public int getTaxCollectorLifePoints() { return this.taxCollectorLifePoints; }
	public void setTaxCollectorLifePoints(int taxCollectorLifePoints) { this.taxCollectorLifePoints = taxCollectorLifePoints; };
	public int getTaxCollectorDamagesBonuses() { return this.taxCollectorDamagesBonuses; }
	public void setTaxCollectorDamagesBonuses(int taxCollectorDamagesBonuses) { this.taxCollectorDamagesBonuses = taxCollectorDamagesBonuses; };
	public int getTaxCollectorPods() { return this.taxCollectorPods; }
	public void setTaxCollectorPods(int taxCollectorPods) { this.taxCollectorPods = taxCollectorPods; };
	public int getTaxCollectorProspecting() { return this.taxCollectorProspecting; }
	public void setTaxCollectorProspecting(int taxCollectorProspecting) { this.taxCollectorProspecting = taxCollectorProspecting; };
	public int getTaxCollectorWisdom() { return this.taxCollectorWisdom; }
	public void setTaxCollectorWisdom(int taxCollectorWisdom) { this.taxCollectorWisdom = taxCollectorWisdom; };
	public int getBoostPoints() { return this.boostPoints; }
	public void setBoostPoints(int boostPoints) { this.boostPoints = boostPoints; };
	public List<Integer> getSpellId() { return this.spellId; }
	public void setSpellId(List<Integer> spellId) { this.spellId = spellId; };
	public List<Integer> getSpellLevel() { return this.spellLevel; }
	public void setSpellLevel(List<Integer> spellLevel) { this.spellLevel = spellLevel; };

	public GuildInfosUpgradeMessage(){
	}

	public GuildInfosUpgradeMessage(int maxTaxCollectorsCount, int taxCollectorsCount, int taxCollectorLifePoints, int taxCollectorDamagesBonuses, int taxCollectorPods, int taxCollectorProspecting, int taxCollectorWisdom, int boostPoints, List<Integer> spellId, List<Integer> spellLevel){
		this.maxTaxCollectorsCount = maxTaxCollectorsCount;
		this.taxCollectorsCount = taxCollectorsCount;
		this.taxCollectorLifePoints = taxCollectorLifePoints;
		this.taxCollectorDamagesBonuses = taxCollectorDamagesBonuses;
		this.taxCollectorPods = taxCollectorPods;
		this.taxCollectorProspecting = taxCollectorProspecting;
		this.taxCollectorWisdom = taxCollectorWisdom;
		this.boostPoints = boostPoints;
		this.spellId = spellId;
		this.spellLevel = spellLevel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.maxTaxCollectorsCount);
			writer.writeByte(this.taxCollectorsCount);
			writer.writeVarShort(this.taxCollectorLifePoints);
			writer.writeVarShort(this.taxCollectorDamagesBonuses);
			writer.writeVarShort(this.taxCollectorPods);
			writer.writeVarShort(this.taxCollectorProspecting);
			writer.writeVarShort(this.taxCollectorWisdom);
			writer.writeVarShort(this.boostPoints);
			writer.writeShort(this.spellId.size());
			int _loc2_ = 0;
			while( _loc2_ < this.spellId.size()){
				writer.writeVarShort(this.spellId.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.spellLevel.size());
			int _loc3_ = 0;
			while( _loc3_ < this.spellLevel.size()){
				writer.writeShort(this.spellLevel.get(_loc3_));
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.maxTaxCollectorsCount = reader.readByte();
			this.taxCollectorsCount = reader.readByte();
			this.taxCollectorLifePoints = reader.readVarShort();
			this.taxCollectorDamagesBonuses = reader.readVarShort();
			this.taxCollectorPods = reader.readVarShort();
			this.taxCollectorProspecting = reader.readVarShort();
			this.taxCollectorWisdom = reader.readVarShort();
			this.boostPoints = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.spellId = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.spellId.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.spellLevel = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readShort();
				this.spellLevel.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
