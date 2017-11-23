package protocol.network.types.game.guild.tax;

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
public class AdditionalTaxCollectorInformations extends NetworkMessage {
	public static final int ProtocolId = 165;

	public String collectorCallerName;
	public int date;

	public AdditionalTaxCollectorInformations(){
	}

	public AdditionalTaxCollectorInformations(String collectorCallerName, int date){
		this.collectorCallerName = collectorCallerName;
		this.date = date;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.collectorCallerName);
			writer.writeInt(this.date);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.collectorCallerName = reader.readUTF();
			this.date = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("collectorCallerName : " + this.collectorCallerName);
		//Network.appendDebug("date : " + this.date);
	//}
}
