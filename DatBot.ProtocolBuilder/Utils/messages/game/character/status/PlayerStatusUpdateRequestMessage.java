package protocol.network.messages.game.character.status;

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
import protocol.network.types.game.character.status.PlayerStatus;

@SuppressWarnings("unused")
public class PlayerStatusUpdateRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6387;

	private PlayerStatus status;

	public PlayerStatus getStatus() { return this.status; };
	public void setStatus(PlayerStatus status) { this.status = status; };

	public PlayerStatusUpdateRequestMessage(){
	}

	public PlayerStatusUpdateRequestMessage(PlayerStatus status){
		this.status = status;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(PlayerStatus.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.status = (PlayerStatus) ProtocolTypeManager.getInstance(reader.readShort());
			this.status.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
