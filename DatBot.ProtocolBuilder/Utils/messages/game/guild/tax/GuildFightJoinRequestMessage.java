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
public class GuildFightJoinRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5717;

	private double taxCollectorId;

	public double getTaxCollectorId() { return this.taxCollectorId; }
	public void setTaxCollectorId(double taxCollectorId) { this.taxCollectorId = taxCollectorId; };

	public GuildFightJoinRequestMessage(){
	}

	public GuildFightJoinRequestMessage(double taxCollectorId){
		this.taxCollectorId = taxCollectorId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.taxCollectorId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.taxCollectorId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
