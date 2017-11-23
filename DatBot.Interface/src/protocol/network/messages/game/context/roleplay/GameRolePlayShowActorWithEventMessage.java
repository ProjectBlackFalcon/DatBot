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
import protocol.network.messages.game.context.roleplay.GameRolePlayShowActorMessage;

@SuppressWarnings("unused")
public class GameRolePlayShowActorWithEventMessage extends GameRolePlayShowActorMessage {
	public static final int ProtocolId = 6407;

	public int actorEventId;

	public GameRolePlayShowActorWithEventMessage(){
	}

	public GameRolePlayShowActorWithEventMessage(int actorEventId){
		this.actorEventId = actorEventId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.actorEventId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.actorEventId = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("actorEventId : " + this.actorEventId);
	//}
}
