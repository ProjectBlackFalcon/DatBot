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
public class SymbioticObjectAssociateRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6522;

	public int symbioteUID;
	public int symbiotePos;
	public int hostUID;
	public int hostPos;

	public SymbioticObjectAssociateRequestMessage(){
	}

	public SymbioticObjectAssociateRequestMessage(int symbioteUID, int symbiotePos, int hostUID, int hostPos){
		this.symbioteUID = symbioteUID;
		this.symbiotePos = symbiotePos;
		this.hostUID = hostUID;
		this.hostPos = hostPos;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.symbioteUID);
			writer.writeByte(this.symbiotePos);
			writer.writeVarInt(this.hostUID);
			writer.writeByte(this.hostPos);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.symbioteUID = reader.readVarInt();
			this.symbiotePos = reader.readByte();
			this.hostUID = reader.readVarInt();
			this.hostPos = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("symbioteUID : " + this.symbioteUID);
		//Network.appendDebug("symbiotePos : " + this.symbiotePos);
		//Network.appendDebug("hostUID : " + this.hostUID);
		//Network.appendDebug("hostPos : " + this.hostPos);
	//}
}