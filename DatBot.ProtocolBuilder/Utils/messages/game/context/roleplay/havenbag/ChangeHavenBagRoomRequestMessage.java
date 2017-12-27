package protocol.network.messages.game.context.roleplay.havenbag;

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
public class ChangeHavenBagRoomRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6638;

	private int roomId;

	public int getRoomId() { return this.roomId; };
	public void setRoomId(int roomId) { this.roomId = roomId; };

	public ChangeHavenBagRoomRequestMessage(){
	}

	public ChangeHavenBagRoomRequestMessage(int roomId){
		this.roomId = roomId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.roomId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.roomId = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
