package protocol.network.types.game.paddock;

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
public class MountInformationsForPaddock extends NetworkMessage {
	public static final int ProtocolId = 184;

	public int modelId;
	public String name;
	public String ownerName;

	public MountInformationsForPaddock(){
	}

	public MountInformationsForPaddock(int modelId, String name, String ownerName){
		this.modelId = modelId;
		this.name = name;
		this.ownerName = ownerName;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.modelId);
			writer.writeUTF(this.name);
			writer.writeUTF(this.ownerName);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.modelId = reader.readVarShort();
			this.name = reader.readUTF();
			this.ownerName = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("modelId : " + this.modelId);
		//Network.appendDebug("name : " + this.name);
		//Network.appendDebug("ownerName : " + this.ownerName);
	//}
}
