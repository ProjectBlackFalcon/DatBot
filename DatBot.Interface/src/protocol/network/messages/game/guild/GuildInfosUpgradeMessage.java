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

	public int maxTaxCollectorsCount;
	public int taxCollectorsCount;
	public int taxCollectorLifePoints;
	public int taxCollectorDamagesBonuses;
	public int taxCollectorPods;
	public int taxCollectorProspecting;
	public int taxCollectorWisdom;
	public int boostPoints;
	public List<Integer> spellId;
	public List<Integer> spellLevel;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("maxTaxCollectorsCount : " + this.maxTaxCollectorsCount);
		//Network.appendDebug("taxCollectorsCount : " + this.taxCollectorsCount);
		//Network.appendDebug("taxCollectorLifePoints : " + this.taxCollectorLifePoints);
		//Network.appendDebug("taxCollectorDamagesBonuses : " + this.taxCollectorDamagesBonuses);
		//Network.appendDebug("taxCollectorPods : " + this.taxCollectorPods);
		//Network.appendDebug("taxCollectorProspecting : " + this.taxCollectorProspecting);
		//Network.appendDebug("taxCollectorWisdom : " + this.taxCollectorWisdom);
		//Network.appendDebug("boostPoints : " + this.boostPoints);
		//for(Integer a : spellId) {
			//Network.appendDebug("spellId : " + a);
		//}
		//for(Integer a : spellLevel) {
			//Network.appendDebug("spellLevel : " + a);
		//}
	//}
}