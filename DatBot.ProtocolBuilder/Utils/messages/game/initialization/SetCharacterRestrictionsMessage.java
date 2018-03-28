package protocol.network.messages.game.initialization;

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
import protocol.network.types.game.character.restriction.ActorRestrictionsInformations;

@SuppressWarnings("unused")
public class SetCharacterRestrictionsMessage extends NetworkMessage {
	public static final int ProtocolId = 170;

	private double actorId;
	private ActorRestrictionsInformations restrictions;

	public double getActorId() { return this.actorId; }
	public void setActorId(double actorId) { this.actorId = actorId; };
	public ActorRestrictionsInformations getRestrictions() { return this.restrictions; }
	public void setRestrictions(ActorRestrictionsInformations restrictions) { this.restrictions = restrictions; };

	public SetCharacterRestrictionsMessage(){
	}

	public SetCharacterRestrictionsMessage(double actorId, ActorRestrictionsInformations restrictions){
		this.actorId = actorId;
		this.restrictions = restrictions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.actorId);
			restrictions.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.actorId = reader.readDouble();
			this.restrictions = new ActorRestrictionsInformations();
			this.restrictions.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
