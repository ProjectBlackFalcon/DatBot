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
public class GuildFightPlayersEnemiesListMessage extends NetworkMessage {
	public static final int ProtocolId = 5928;

	private double fightId;
	private List<CharacterMinimalPlusLookInformations> playerInfo;

	public double getFightId() { return this.fightId; }
	public void setFightId(double fightId) { this.fightId = fightId; };
	public List<CharacterMinimalPlusLookInformations> getPlayerInfo() { return this.playerInfo; }
	public void setPlayerInfo(List<CharacterMinimalPlusLookInformations> playerInfo) { this.playerInfo = playerInfo; };

	public GuildFightPlayersEnemiesListMessage(){
	}

	public GuildFightPlayersEnemiesListMessage(double fightId, List<CharacterMinimalPlusLookInformations> playerInfo){
		this.fightId = fightId;
		this.playerInfo = playerInfo;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.fightId);
			writer.writeShort(this.playerInfo.size());
			int _loc2_ = 0;
			while( _loc2_ < this.playerInfo.size()){
				this.playerInfo.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readDouble();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.playerInfo = new ArrayList<CharacterMinimalPlusLookInformations>();
			while( _loc3_ <  _loc2_){
				CharacterMinimalPlusLookInformations _loc15_ = new CharacterMinimalPlusLookInformations();
				_loc15_.Deserialize(reader);
				this.playerInfo.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
