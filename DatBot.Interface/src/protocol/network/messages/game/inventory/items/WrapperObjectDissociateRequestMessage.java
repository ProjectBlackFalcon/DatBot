package protocol.network.messages.game.inventory.items;

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
public class WrapperObjectDissociateRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6524;

	private int hostUID;
	private int hostPos;

	public int getHostUID() { return this.hostUID; }
	public void setHostUID(int hostUID) { this.hostUID = hostUID; };
	public int getHostPos() { return this.hostPos; }
	public void setHostPos(int hostPos) { this.hostPos = hostPos; };

	public WrapperObjectDissociateRequestMessage(){
	}

	public WrapperObjectDissociateRequestMessage(int hostUID, int hostPos){
		this.hostUID = hostUID;
		this.hostPos = hostPos;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.hostUID);
			writer.writeByte(this.hostPos);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.hostUID = reader.readVarInt();
			this.hostPos = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
