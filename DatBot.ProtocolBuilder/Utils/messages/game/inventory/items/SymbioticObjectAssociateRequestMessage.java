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

	private int symbioteUID;
	private int symbiotePos;
	private int hostUID;
	private int hostPos;

	public int getSymbioteUID() { return this.symbioteUID; };
	public void setSymbioteUID(int symbioteUID) { this.symbioteUID = symbioteUID; };
	public int getSymbiotePos() { return this.symbiotePos; };
	public void setSymbiotePos(int symbiotePos) { this.symbiotePos = symbiotePos; };
	public int getHostUID() { return this.hostUID; };
	public void setHostUID(int hostUID) { this.hostUID = hostUID; };
	public int getHostPos() { return this.hostPos; };
	public void setHostPos(int hostPos) { this.hostPos = hostPos; };

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
	}

}
