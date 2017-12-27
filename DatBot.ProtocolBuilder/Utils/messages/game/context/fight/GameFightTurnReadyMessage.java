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

@SuppressWarnings("unused")
public class GameFightTurnReadyMessage extends NetworkMessage {
	public static final int ProtocolId = 716;

	private boolean isReady;

	public boolean isIsReady() { return this.isReady; };
	public void setIsReady(boolean isReady) { this.isReady = isReady; };

	public GameFightTurnReadyMessage(){
	}

	public GameFightTurnReadyMessage(boolean isReady){
		this.isReady = isReady;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.isReady);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.isReady = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
