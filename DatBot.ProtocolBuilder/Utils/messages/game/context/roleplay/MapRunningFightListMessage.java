package protocol.network.messages.game.context.roleplay;

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
import protocol.network.types.game.context.fight.FightExternalInformations;

@SuppressWarnings("unused")
public class MapRunningFightListMessage extends NetworkMessage {
	public static final int ProtocolId = 5743;

	private List<FightExternalInformations> fights;

	public List<FightExternalInformations> getFights() { return this.fights; }
	public void setFights(List<FightExternalInformations> fights) { this.fights = fights; };

	public MapRunningFightListMessage(){
	}

	public MapRunningFightListMessage(List<FightExternalInformations> fights){
		this.fights = fights;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.fights.size());
			int _loc2_ = 0;
			while( _loc2_ < this.fights.size()){
				this.fights.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.fights = new ArrayList<FightExternalInformations>();
			while( _loc3_ <  _loc2_){
				FightExternalInformations _loc15_ = new FightExternalInformations();
				_loc15_.Deserialize(reader);
				this.fights.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
