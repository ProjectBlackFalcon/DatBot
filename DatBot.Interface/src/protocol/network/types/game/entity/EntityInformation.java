package protocol.network.types.game.entity;

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
public class EntityInformation extends NetworkMessage {
	public static final int ProtocolId = 546;

	private int id;
	private int experience;
	private boolean status;

	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; };
	public int getExperience() { return this.experience; }
	public void setExperience(int experience) { this.experience = experience; };
	public boolean isStatus() { return this.status; }
	public void setStatus(boolean status) { this.status = status; };

	public EntityInformation(){
	}

	public EntityInformation(int id, int experience, boolean status){
		this.id = id;
		this.experience = experience;
		this.status = status;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.id);
			writer.writeVarInt(this.experience);
			writer.writeBoolean(this.status);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readVarShort();
			this.experience = reader.readVarInt();
			this.status = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
