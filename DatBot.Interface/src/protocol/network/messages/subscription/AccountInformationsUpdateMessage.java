package protocol.network.messages.subscription;

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
public class AccountInformationsUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6740;

	public double subscriptionEndDate;
	public double unlimitedRestatEndDate;

	public AccountInformationsUpdateMessage(){
	}

	public AccountInformationsUpdateMessage(double subscriptionEndDate, double unlimitedRestatEndDate){
		this.subscriptionEndDate = subscriptionEndDate;
		this.unlimitedRestatEndDate = unlimitedRestatEndDate;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.subscriptionEndDate);
			writer.writeDouble(this.unlimitedRestatEndDate);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.subscriptionEndDate = reader.readDouble();
			this.unlimitedRestatEndDate = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("subscriptionEndDate : " + this.subscriptionEndDate);
		//Network.appendDebug("unlimitedRestatEndDate : " + this.unlimitedRestatEndDate);
	//}
}
