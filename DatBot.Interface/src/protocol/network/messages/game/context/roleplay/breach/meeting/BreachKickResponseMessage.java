package protocol.network.messages.game.context.roleplay.breach.meeting;

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
import protocol.network.types.game.character.CharacterMinimalInformations;

@SuppressWarnings("unused")
public class BreachKickResponseMessage extends NetworkMessage {
	public static final int ProtocolId = 6789;

	private CharacterMinimalInformations target;
	private boolean kicked;

	public CharacterMinimalInformations getTarget() { return this.target; }
	public void setTarget(CharacterMinimalInformations target) { this.target = target; };
	public boolean isKicked() { return this.kicked; }
	public void setKicked(boolean kicked) { this.kicked = kicked; };

	public BreachKickResponseMessage(){
	}

	public BreachKickResponseMessage(CharacterMinimalInformations target, boolean kicked){
		this.target = target;
		this.kicked = kicked;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			target.Serialize(writer);
			writer.writeBoolean(this.kicked);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.target = new CharacterMinimalInformations();
			this.target.Deserialize(reader);
			this.kicked = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
