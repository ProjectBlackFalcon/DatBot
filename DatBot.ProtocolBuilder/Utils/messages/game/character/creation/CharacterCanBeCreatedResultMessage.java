package protocol.network.messages.game.character.creation;

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
public class CharacterCanBeCreatedResultMessage extends NetworkMessage {
	public static final int ProtocolId = 6733;

	private boolean yesYouCan;

	public boolean isYesYouCan() { return this.yesYouCan; }
	public void setYesYouCan(boolean yesYouCan) { this.yesYouCan = yesYouCan; };

	public CharacterCanBeCreatedResultMessage(){
	}

	public CharacterCanBeCreatedResultMessage(boolean yesYouCan){
		this.yesYouCan = yesYouCan;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.yesYouCan);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.yesYouCan = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
