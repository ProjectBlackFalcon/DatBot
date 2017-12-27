package protocol.network.messages.game.guild.tax;

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
import protocol.network.types.game.character.CharacterMinimalPlusLookInformations;

@SuppressWarnings("unused")
public class GuildFightPlayersHelpersJoinMessage extends NetworkMessage {
	public static final int ProtocolId = 5720;

	private double fightId;
	private CharacterMinimalPlusLookInformations playerInfo;

	public double getFightId() { return this.fightId; };
	public void setFightId(double fightId) { this.fightId = fightId; };
	public CharacterMinimalPlusLookInformations getPlayerInfo() { return this.playerInfo; };
	public void setPlayerInfo(CharacterMinimalPlusLookInformations playerInfo) { this.playerInfo = playerInfo; };

	public GuildFightPlayersHelpersJoinMessage(){
	}

	public GuildFightPlayersHelpersJoinMessage(double fightId, CharacterMinimalPlusLookInformations playerInfo){
		this.fightId = fightId;
		this.playerInfo = playerInfo;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.fightId);
			playerInfo.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readDouble();
			this.playerInfo = new CharacterMinimalPlusLookInformations();
			this.playerInfo.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
