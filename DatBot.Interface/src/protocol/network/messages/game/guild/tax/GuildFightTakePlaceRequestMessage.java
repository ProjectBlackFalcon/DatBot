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
import protocol.network.messages.game.guild.tax.GuildFightJoinRequestMessage;

@SuppressWarnings("unused")
public class GuildFightTakePlaceRequestMessage extends GuildFightJoinRequestMessage {
	public static final int ProtocolId = 6235;

	public int replacedCharacterId;

	public GuildFightTakePlaceRequestMessage(){
	}

	public GuildFightTakePlaceRequestMessage(int replacedCharacterId){
		this.replacedCharacterId = replacedCharacterId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.replacedCharacterId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.replacedCharacterId = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("replacedCharacterId : " + this.replacedCharacterId);
	//}
}
