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

	private int modelId;
	private String name;
	private String ownerName;

	public int getModelId() { return this.modelId; };
	public void setModelId(int modelId) { this.modelId = modelId; };
	public String getName() { return this.name; };
	public void setName(String name) { this.name = name; };
	public String getOwnerName() { return this.ownerName; };
	public void setOwnerName(String ownerName) { this.ownerName = ownerName; };

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

}
