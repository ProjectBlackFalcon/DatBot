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
import protocol.network.types.game.context.roleplay.GameRolePlayActorInformations;

@SuppressWarnings("unused")
public class GameRolePlayShowActorMessage extends NetworkMessage {
	public static final int ProtocolId = 5632;

	public GameRolePlayActorInformations informations;

	public GameRolePlayShowActorMessage(){
	}

	public GameRolePlayShowActorMessage(GameRolePlayActorInformations informations){
		this.informations = informations;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(GameRolePlayActorInformations.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.informations = (GameRolePlayActorInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.informations.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("informations : " + this.informations);
	//}
}
