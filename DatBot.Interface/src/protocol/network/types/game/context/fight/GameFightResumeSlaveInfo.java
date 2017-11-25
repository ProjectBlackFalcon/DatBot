package protocol.network.types.game.context.fight;

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
import protocol.network.types.game.context.fight.GameFightSpellCooldown;

@SuppressWarnings("unused")
public class GameFightResumeSlaveInfo extends NetworkMessage {
	public static final int ProtocolId = 364;

	public double slaveId;
	public List<GameFightSpellCooldown> spellCooldowns;
	public int summonCount;
	public int bombCount;

	public GameFightResumeSlaveInfo(){
	}

	public GameFightResumeSlaveInfo(double slaveId, List<GameFightSpellCooldown> spellCooldowns, int summonCount, int bombCount){
		this.slaveId = slaveId;
		this.spellCooldowns = spellCooldowns;
		this.summonCount = summonCount;
		this.bombCount = bombCount;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.slaveId);
			writer.writeShort(this.spellCooldowns.size());
			int _loc2_ = 0;
			while( _loc2_ < this.spellCooldowns.size()){
				this.spellCooldowns.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeByte(this.summonCount);
			writer.writeByte(this.bombCount);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.slaveId = reader.readDouble();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.spellCooldowns = new ArrayList<GameFightSpellCooldown>();
			while( _loc3_ <  _loc2_){
				GameFightSpellCooldown _loc15_ = new GameFightSpellCooldown();
				_loc15_.Deserialize(reader);
				this.spellCooldowns.add(_loc15_);
				_loc3_++;
			}
			this.summonCount = reader.readByte();
			this.bombCount = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("slaveId : " + this.slaveId);
		//for(GameFightSpellCooldown a : spellCooldowns) {
			//Network.appendDebug("spellCooldowns : " + a);
		//}
		//Network.appendDebug("summonCount : " + this.summonCount);
		//Network.appendDebug("bombCount : " + this.bombCount);
	//}
}