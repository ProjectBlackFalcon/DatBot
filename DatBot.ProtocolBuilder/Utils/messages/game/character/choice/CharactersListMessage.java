package protocol.network.messages.game.character.choice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.character.choice.BasicCharactersListMessage;

@SuppressWarnings("unused")
public class CharactersListMessage extends BasicCharactersListMessage {
	public static final int ProtocolId = 151;

	private boolean hasStartupActions;

	public boolean isHasStartupActions() { return this.hasStartupActions; }
	public void setHasStartupActions(boolean hasStartupActions) { this.hasStartupActions = hasStartupActions; };

	public CharactersListMessage(){
	}

	public CharactersListMessage(boolean hasStartupActions){
		this.hasStartupActions = hasStartupActions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeBoolean(this.hasStartupActions);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.hasStartupActions = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
