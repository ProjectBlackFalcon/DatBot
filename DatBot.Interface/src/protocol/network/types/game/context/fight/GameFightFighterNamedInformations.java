package protocol.network.types.game.context.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.fight.GameFightFighterInformations;
import protocol.network.types.game.character.status.PlayerStatus;

@SuppressWarnings("unused")
public class GameFightFighterNamedInformations extends GameFightFighterInformations {
	public static final int ProtocolId = 158;

	private String name;
	private PlayerStatus status;

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; };
	public PlayerStatus getStatus() { return this.status; }
	public void setStatus(PlayerStatus status) { this.status = status; };

	public GameFightFighterNamedInformations(){
	}

	public GameFightFighterNamedInformations(String name, PlayerStatus status){
		this.name = name;
		this.status = status;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.name);
			status.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.name = reader.readUTF();
			this.status = new PlayerStatus();
			this.status.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
