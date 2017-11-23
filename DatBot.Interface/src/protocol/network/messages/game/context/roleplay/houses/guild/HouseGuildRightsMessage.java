package protocol.network.messages.game.context.roleplay.houses.guild;

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
import protocol.network.types.game.context.roleplay.GuildInformations;

@SuppressWarnings("unused")
public class HouseGuildRightsMessage extends NetworkMessage {
	public static final int ProtocolId = 5703;

	public int houseId;
	public int instanceId;
	public boolean secondHand;
	public GuildInformations guildInfo;
	public int rights;

	public HouseGuildRightsMessage(){
	}

	public HouseGuildRightsMessage(int houseId, int instanceId, boolean secondHand, GuildInformations guildInfo, int rights){
		this.houseId = houseId;
		this.instanceId = instanceId;
		this.secondHand = secondHand;
		this.guildInfo = guildInfo;
		this.rights = rights;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.houseId);
			writer.writeInt(this.instanceId);
			writer.writeBoolean(this.secondHand);
			guildInfo.Serialize(writer);
			writer.writeVarInt(this.rights);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.houseId = reader.readVarInt();
			this.instanceId = reader.readInt();
			this.secondHand = reader.readBoolean();
			this.guildInfo = new GuildInformations();
			this.guildInfo.Deserialize(reader);
			this.rights = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("houseId : " + this.houseId);
		//Network.appendDebug("instanceId : " + this.instanceId);
		//Network.appendDebug("secondHand : " + this.secondHand);
		//Network.appendDebug("guildInfo : " + this.guildInfo);
		//Network.appendDebug("rights : " + this.rights);
	//}
}
