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

@SuppressWarnings("unused")
public class GuildFightPlayersHelpersLeaveMessage extends NetworkMessage {
	public static final int ProtocolId = 5719;

	public double fightId;
	public long playerId;

	public GuildFightPlayersHelpersLeaveMessage(){
	}

	public GuildFightPlayersHelpersLeaveMessage(double fightId, long playerId){
		this.fightId = fightId;
		this.playerId = playerId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.fightId);
			writer.writeVarLong(this.playerId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readDouble();
			this.playerId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("fightId : " + this.fightId);
		//Network.appendDebug("playerId : " + this.playerId);
	//}
}
