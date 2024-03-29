package protocol.network.types.game.context;

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
import protocol.network.types.game.context.EntityDispositionInformations;

@SuppressWarnings("unused")
public class GameContextActorInformations extends NetworkMessage {
	public static final int ProtocolId = 150;

	private double contextualId;
	private EntityLook look;
	private EntityDispositionInformations disposition;

	public double getContextualId() { return this.contextualId; }
	public void setContextualId(double contextualId) { this.contextualId = contextualId; };
	public EntityLook getLook() { return this.look; }
	public void setLook(EntityLook look) { this.look = look; };
	public EntityDispositionInformations getDisposition() { return this.disposition; }
	public void setDisposition(EntityDispositionInformations disposition) { this.disposition = disposition; };

	public GameContextActorInformations(){
	}

	public GameContextActorInformations(double contextualId, EntityLook look, EntityDispositionInformations disposition){
		this.contextualId = contextualId;
		this.look = look;
		this.disposition = disposition;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.contextualId);
			look.Serialize(writer);
			writer.writeShort(EntityDispositionInformations.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.contextualId = reader.readDouble();
			this.look = new EntityLook();
			this.look.Deserialize(reader);
			this.disposition = (EntityDispositionInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.disposition.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
