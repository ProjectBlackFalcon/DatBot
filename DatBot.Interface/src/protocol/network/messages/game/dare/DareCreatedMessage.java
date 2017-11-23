package protocol.network.messages.game.dare;

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
import protocol.network.types.game.dare.DareInformations;

@SuppressWarnings("unused")
public class DareCreatedMessage extends NetworkMessage {
	public static final int ProtocolId = 6668;

	public DareInformations dareInfos;
	public boolean needNotifications;

	public DareCreatedMessage(){
	}

	public DareCreatedMessage(DareInformations dareInfos, boolean needNotifications){
		this.dareInfos = dareInfos;
		this.needNotifications = needNotifications;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			dareInfos.Serialize(writer);
			writer.writeBoolean(this.needNotifications);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.dareInfos = new DareInformations();
			this.dareInfos.Deserialize(reader);
			this.needNotifications = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("dareInfos : " + this.dareInfos);
		//Network.appendDebug("needNotifications : " + this.needNotifications);
	//}
}
