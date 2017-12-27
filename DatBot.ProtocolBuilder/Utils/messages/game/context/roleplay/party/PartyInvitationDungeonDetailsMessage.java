package protocol.network.messages.game.context.roleplay.party;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.party.PartyInvitationDetailsMessage;

@SuppressWarnings("unused")
public class PartyInvitationDungeonDetailsMessage extends PartyInvitationDetailsMessage {
	public static final int ProtocolId = 6262;

	private int dungeonId;
	private List<Boolean> playersDungeonReady;

	public int getDungeonId() { return this.dungeonId; };
	public void setDungeonId(int dungeonId) { this.dungeonId = dungeonId; };
	public List<Boolean> getPlayersDungeonReady() { return this.playersDungeonReady; };
	public void setPlayersDungeonReady(List<Boolean> playersDungeonReady) { this.playersDungeonReady = playersDungeonReady; };

	public PartyInvitationDungeonDetailsMessage(){
	}

	public PartyInvitationDungeonDetailsMessage(int dungeonId, List<Boolean> playersDungeonReady){
		this.dungeonId = dungeonId;
		this.playersDungeonReady = playersDungeonReady;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.dungeonId);
			writer.writeShort(this.playersDungeonReady.size());
			int _loc2_ = 0;
			while( _loc2_ < this.playersDungeonReady.size()){
				writer.writeBoolean(this.playersDungeonReady.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.dungeonId = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.playersDungeonReady = new ArrayList<Boolean>();
			while( _loc3_ <  _loc2_){
				boolean _loc15_ = reader.readBoolean();
				this.playersDungeonReady.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
