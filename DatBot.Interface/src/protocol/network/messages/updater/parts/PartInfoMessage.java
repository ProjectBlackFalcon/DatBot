package protocol.network.messages.updater.parts;

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
import protocol.network.types.updater.ContentPart;

@SuppressWarnings("unused")
public class PartInfoMessage extends NetworkMessage {
	public static final int ProtocolId = 1508;

	public ContentPart part;
	public double installationPercent;

	public PartInfoMessage(){
	}

	public PartInfoMessage(ContentPart part, double installationPercent){
		this.part = part;
		this.installationPercent = installationPercent;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			part.Serialize(writer);
			writer.writeDouble(this.installationPercent);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.part = new ContentPart();
			this.part.Deserialize(reader);
			this.installationPercent = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("part : " + this.part);
		//Network.appendDebug("installationPercent : " + this.installationPercent);
	//}
}
