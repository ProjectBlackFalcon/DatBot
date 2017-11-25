package protocol.network.messages.game.context.fight;

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
import protocol.network.types.game.data.items.SpellItem;
import protocol.network.types.game.character.characteristic.CharacterCharacteristicsInformations;
import protocol.network.types.game.shortcut.Shortcut;

@SuppressWarnings("unused")
public class SlaveSwitchContextMessage extends NetworkMessage {
	public static final int ProtocolId = 6214;

	public double masterId;
	public double slaveId;
	public List<SpellItem> slaveSpells;
	public CharacterCharacteristicsInformations slaveStats;
	public List<Shortcut> shortcuts;

	public SlaveSwitchContextMessage(){
	}

	public SlaveSwitchContextMessage(double masterId, double slaveId, List<SpellItem> slaveSpells, CharacterCharacteristicsInformations slaveStats, List<Shortcut> shortcuts){
		this.masterId = masterId;
		this.slaveId = slaveId;
		this.slaveSpells = slaveSpells;
		this.slaveStats = slaveStats;
		this.shortcuts = shortcuts;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.masterId);
			writer.writeDouble(this.slaveId);
			writer.writeShort(this.slaveSpells.size());
			int _loc2_ = 0;
			while( _loc2_ < this.slaveSpells.size()){
				this.slaveSpells.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			slaveStats.Serialize(writer);
			writer.writeShort(this.shortcuts.size());
			int _loc3_ = 0;
			while( _loc3_ < this.shortcuts.size()){
				writer.writeShort(Shortcut.ProtocolId);
				this.shortcuts.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.masterId = reader.readDouble();
			this.slaveId = reader.readDouble();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.slaveSpells = new ArrayList<SpellItem>();
			while( _loc3_ <  _loc2_){
				SpellItem _loc15_ = new SpellItem();
				_loc15_.Deserialize(reader);
				this.slaveSpells.add(_loc15_);
				_loc3_++;
			}
			this.slaveStats = new CharacterCharacteristicsInformations();
			this.slaveStats.Deserialize(reader);
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.shortcuts = new ArrayList<Shortcut>();
			while( _loc5_ <  _loc4_){
				Shortcut _loc16_ = (Shortcut) ProtocolTypeManager.getInstance(reader.readShort());
				_loc16_.Deserialize(reader);
				this.shortcuts.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("masterId : " + this.masterId);
		//Network.appendDebug("slaveId : " + this.slaveId);
		//for(SpellItem a : slaveSpells) {
			//Network.appendDebug("slaveSpells : " + a);
		//}
		//Network.appendDebug("slaveStats : " + this.slaveStats);
		//for(Shortcut a : shortcuts) {
			//Network.appendDebug("shortcuts : " + a);
		//}
	//}
}