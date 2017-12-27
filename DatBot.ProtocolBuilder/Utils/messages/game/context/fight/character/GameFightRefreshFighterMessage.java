package protocol.network.messages.game.context.fight.character;

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
import protocol.network.types.game.context.GameContextActorInformations;

@SuppressWarnings("unused")
public class GameFightRefreshFighterMessage extends NetworkMessage {
	public static final int ProtocolId = 6309;

	private GameContextActorInformations informations;

	public GameContextActorInformations getInformations() { return this.informations; };
	public void setInformations(GameContextActorInformations informations) { this.informations = informations; };

	public GameFightRefreshFighterMessage(){
	}

	public GameFightRefreshFighterMessage(GameContextActorInformations informations){
		this.informations = informations;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(GameContextActorInformations.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.informations = (GameContextActorInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.informations.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
