package protocol.network.messages.game.social;

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
import protocol.network.types.game.look.EntityLook;

@SuppressWarnings("unused")
public class ContactLookMessage extends NetworkMessage {
	public static final int ProtocolId = 5934;

	public int requestId;
	public String playerName;
	public long playerId;
	public EntityLook look;

	public ContactLookMessage(){
	}

	public ContactLookMessage(int requestId, String playerName, long playerId, EntityLook look){
		this.requestId = requestId;
		this.playerName = playerName;
		this.playerId = playerId;
		this.look = look;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.requestId);
			writer.writeUTF(this.playerName);
			writer.writeVarLong(this.playerId);
			look.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.requestId = reader.readVarInt();
			this.playerName = reader.readUTF();
			this.playerId = reader.readVarLong();
			this.look = new EntityLook();
			this.look.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("requestId : " + this.requestId);
		//Network.appendDebug("playerName : " + this.playerName);
		//Network.appendDebug("playerId : " + this.playerId);
		//Network.appendDebug("look : " + this.look);
	//}
}
