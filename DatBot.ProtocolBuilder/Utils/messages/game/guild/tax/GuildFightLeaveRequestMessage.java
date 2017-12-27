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
public class GuildFightLeaveRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5715;

	private double taxCollectorId;
	private long characterId;

	public double getTaxCollectorId() { return this.taxCollectorId; };
	public void setTaxCollectorId(double taxCollectorId) { this.taxCollectorId = taxCollectorId; };
	public long getCharacterId() { return this.characterId; };
	public void setCharacterId(long characterId) { this.characterId = characterId; };

	public GuildFightLeaveRequestMessage(){
	}

	public GuildFightLeaveRequestMessage(double taxCollectorId, long characterId){
		this.taxCollectorId = taxCollectorId;
		this.characterId = characterId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.taxCollectorId);
			writer.writeVarLong(this.characterId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.taxCollectorId = reader.readDouble();
			this.characterId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
