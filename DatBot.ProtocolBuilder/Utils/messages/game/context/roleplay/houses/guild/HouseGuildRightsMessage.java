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

	private int houseId;
	private int instanceId;
	private boolean secondHand;
	private GuildInformations guildInfo;
	private int rights;

	public int getHouseId() { return this.houseId; };
	public void setHouseId(int houseId) { this.houseId = houseId; };
	public int getInstanceId() { return this.instanceId; };
	public void setInstanceId(int instanceId) { this.instanceId = instanceId; };
	public boolean isSecondHand() { return this.secondHand; };
	public void setSecondHand(boolean secondHand) { this.secondHand = secondHand; };
	public GuildInformations getGuildInfo() { return this.guildInfo; };
	public void setGuildInfo(GuildInformations guildInfo) { this.guildInfo = guildInfo; };
	public int getRights() { return this.rights; };
	public void setRights(int rights) { this.rights = rights; };

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
	}

}
