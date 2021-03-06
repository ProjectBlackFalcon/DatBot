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
public class TaxCollectorMovementRemoveMessage extends NetworkMessage {
	public static final int ProtocolId = 5915;

	private double collectorId;

	public double getCollectorId() { return this.collectorId; }
	public void setCollectorId(double collectorId) { this.collectorId = collectorId; };

	public TaxCollectorMovementRemoveMessage(){
	}

	public TaxCollectorMovementRemoveMessage(double collectorId){
		this.collectorId = collectorId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.collectorId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.collectorId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
