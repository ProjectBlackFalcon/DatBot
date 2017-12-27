package protocol.network.messages.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.connection.IdentificationFailedMessage;

@SuppressWarnings("unused")
public class IdentificationFailedBannedMessage extends IdentificationFailedMessage {
	public static final int ProtocolId = 6174;

	private double banEndDate;

	public double getBanEndDate() { return this.banEndDate; };
	public void setBanEndDate(double banEndDate) { this.banEndDate = banEndDate; };

	public IdentificationFailedBannedMessage(){
	}

	public IdentificationFailedBannedMessage(double banEndDate){
		this.banEndDate = banEndDate;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.banEndDate);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.banEndDate = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
