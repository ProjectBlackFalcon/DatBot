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

	private DareInformations dareInfos;
	private boolean needNotifications;

	public DareInformations getDareInfos() { return this.dareInfos; }
	public void setDareInfos(DareInformations dareInfos) { this.dareInfos = dareInfos; };
	public boolean isNeedNotifications() { return this.needNotifications; }
	public void setNeedNotifications(boolean needNotifications) { this.needNotifications = needNotifications; };

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
	}

}
