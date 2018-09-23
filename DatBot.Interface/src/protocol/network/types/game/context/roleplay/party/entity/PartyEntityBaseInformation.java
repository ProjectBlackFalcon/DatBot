package protocol.network.types.game.context.roleplay.party.entity;

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
import protocol.network.types.game.look.EntityLook;

@SuppressWarnings("unused")
public class PartyEntityBaseInformation extends NetworkMessage {
	public static final int ProtocolId = 552;

	private int indexId;
	private int entityModelId;
	private EntityLook entityLook;

	public int getIndexId() { return this.indexId; }
	public void setIndexId(int indexId) { this.indexId = indexId; };
	public int getEntityModelId() { return this.entityModelId; }
	public void setEntityModelId(int entityModelId) { this.entityModelId = entityModelId; };
	public EntityLook getEntityLook() { return this.entityLook; }
	public void setEntityLook(EntityLook entityLook) { this.entityLook = entityLook; };

	public PartyEntityBaseInformation(){
	}

	public PartyEntityBaseInformation(int indexId, int entityModelId, EntityLook entityLook){
		this.indexId = indexId;
		this.entityModelId = entityModelId;
		this.entityLook = entityLook;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.indexId);
			writer.writeByte(this.entityModelId);
			entityLook.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.indexId = reader.readByte();
			this.entityModelId = reader.readByte();
			this.entityLook = new EntityLook();
			this.entityLook.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
