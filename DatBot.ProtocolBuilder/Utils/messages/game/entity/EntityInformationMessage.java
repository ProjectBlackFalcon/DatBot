package protocol.network.messages.game.entity;

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
import protocol.network.types.game.entity.EntityInformation;

@SuppressWarnings("unused")
public class EntityInformationMessage extends NetworkMessage {
	public static final int ProtocolId = 6771;

	private EntityInformation entity;

	public EntityInformation getEntity() { return this.entity; }
	public void setEntity(EntityInformation entity) { this.entity = entity; };

	public EntityInformationMessage(){
	}

	public EntityInformationMessage(EntityInformation entity){
		this.entity = entity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			entity.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.entity = new EntityInformation();
			this.entity.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
