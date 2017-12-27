package protocol.network.messages.game.context.mount;

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
public class MountRenameRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5987;

	private String name;
	private int mountId;

	public String getName() { return this.name; };
	public void setName(String name) { this.name = name; };
	public int getMountId() { return this.mountId; };
	public void setMountId(int mountId) { this.mountId = mountId; };

	public MountRenameRequestMessage(){
	}

	public MountRenameRequestMessage(String name, int mountId){
		this.name = name;
		this.mountId = mountId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.name);
			writer.writeVarInt(this.mountId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.name = reader.readUTF();
			this.mountId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
