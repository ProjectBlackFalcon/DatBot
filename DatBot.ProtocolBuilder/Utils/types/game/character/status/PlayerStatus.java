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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class PlayerStatus extends NetworkMessage {
	public static final int ProtocolId = 415;

	private int statusId;

	public int getStatusId() { return this.statusId; }
	public void setStatusId(int statusId) { this.statusId = statusId; };

	public PlayerStatus(){
	}

	public PlayerStatus(int statusId){
		this.statusId = statusId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.statusId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.statusId = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
