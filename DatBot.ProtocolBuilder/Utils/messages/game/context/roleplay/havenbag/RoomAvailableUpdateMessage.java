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
public class RoomAvailableUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6630;

	private int nbRoom;

	public int getNbRoom() { return this.nbRoom; }
	public void setNbRoom(int nbRoom) { this.nbRoom = nbRoom; };

	public RoomAvailableUpdateMessage(){
	}

	public RoomAvailableUpdateMessage(int nbRoom){
		this.nbRoom = nbRoom;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.nbRoom);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.nbRoom = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
