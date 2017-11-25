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
import protocol.network.NetworkMessage;
import protocol.network.types.game.character.choice.CharacterBaseInformations;

@SuppressWarnings("unused")
public class CharacterSelectedSuccessMessage extends NetworkMessage {
	public static final int ProtocolId = 153;

	public CharacterBaseInformations infos;
	public boolean isCollectingStats;

	public CharacterSelectedSuccessMessage(){
	}

	public CharacterSelectedSuccessMessage(CharacterBaseInformations infos, boolean isCollectingStats){
		this.infos = infos;
		this.isCollectingStats = isCollectingStats;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			infos.Serialize(writer);
			writer.writeBoolean(this.isCollectingStats);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.infos = new CharacterBaseInformations();
			this.infos.Deserialize(reader);
			this.isCollectingStats = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("infos : " + this.infos);
		//Network.appendDebug("isCollectingStats : " + this.isCollectingStats);
	//}
}