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
public class MountSterilizedMessage extends NetworkMessage {
	public static final int ProtocolId = 5977;

	private int mountId;

	public int getMountId() { return this.mountId; }
	public void setMountId(int mountId) { this.mountId = mountId; };

	public MountSterilizedMessage(){
	}

	public MountSterilizedMessage(int mountId){
		this.mountId = mountId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.mountId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.mountId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
