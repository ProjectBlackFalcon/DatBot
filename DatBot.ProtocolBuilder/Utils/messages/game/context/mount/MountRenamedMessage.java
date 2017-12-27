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
public class MountRenamedMessage extends NetworkMessage {
	public static final int ProtocolId = 5983;

	private int mountId;
	private String name;

	public int getMountId() { return this.mountId; };
	public void setMountId(int mountId) { this.mountId = mountId; };
	public String getName() { return this.name; };
	public void setName(String name) { this.name = name; };

	public MountRenamedMessage(){
	}

	public MountRenamedMessage(int mountId, String name){
		this.mountId = mountId;
		this.name = name;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.mountId);
			writer.writeUTF(this.name);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.mountId = reader.readVarInt();
			this.name = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
