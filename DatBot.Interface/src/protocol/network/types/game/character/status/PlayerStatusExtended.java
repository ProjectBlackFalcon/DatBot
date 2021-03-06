package protocol.network.types.game.character.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.character.status.PlayerStatus;

@SuppressWarnings("unused")
public class PlayerStatusExtended extends PlayerStatus {
	public static final int ProtocolId = 414;

	private String message;

	public String getMessage() { return this.message; }
	public void setMessage(String message) { this.message = message; };

	public PlayerStatusExtended(){
	}

	public PlayerStatusExtended(String message){
		this.message = message;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.message);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.message = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
