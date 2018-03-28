package protocol.network.types.game.context.roleplay.party.companion;

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
public class PartyCompanionBaseInformations extends NetworkMessage {
	public static final int ProtocolId = 453;

	private int indexId;
	private int companionGenericId;
	private EntityLook entityLook;

	public int getIndexId() { return this.indexId; }
	public void setIndexId(int indexId) { this.indexId = indexId; };
	public int getCompanionGenericId() { return this.companionGenericId; }
	public void setCompanionGenericId(int companionGenericId) { this.companionGenericId = companionGenericId; };
	public EntityLook getEntityLook() { return this.entityLook; }
	public void setEntityLook(EntityLook entityLook) { this.entityLook = entityLook; };

	public PartyCompanionBaseInformations(){
	}

	public PartyCompanionBaseInformations(int indexId, int companionGenericId, EntityLook entityLook){
		this.indexId = indexId;
		this.companionGenericId = companionGenericId;
		this.entityLook = entityLook;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.indexId);
			writer.writeByte(this.companionGenericId);
			entityLook.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.indexId = reader.readByte();
			this.companionGenericId = reader.readByte();
			this.entityLook = new EntityLook();
			this.entityLook.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
