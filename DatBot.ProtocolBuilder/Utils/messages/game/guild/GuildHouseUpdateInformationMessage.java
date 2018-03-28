package protocol.network.messages.game.guild;

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
import protocol.network.types.game.house.HouseInformationsForGuild;

@SuppressWarnings("unused")
public class GuildHouseUpdateInformationMessage extends NetworkMessage {
	public static final int ProtocolId = 6181;

	private HouseInformationsForGuild housesInformations;

	public HouseInformationsForGuild getHousesInformations() { return this.housesInformations; }
	public void setHousesInformations(HouseInformationsForGuild housesInformations) { this.housesInformations = housesInformations; };

	public GuildHouseUpdateInformationMessage(){
	}

	public GuildHouseUpdateInformationMessage(HouseInformationsForGuild housesInformations){
		this.housesInformations = housesInformations;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			housesInformations.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.housesInformations = new HouseInformationsForGuild();
			this.housesInformations.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
