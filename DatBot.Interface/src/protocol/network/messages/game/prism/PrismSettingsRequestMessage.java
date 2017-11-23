package protocol.network.messages.game.prism;

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
public class PrismSettingsRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6437;

	public int subAreaId;
	public int startDefenseTime;

	public PrismSettingsRequestMessage(){
	}

	public PrismSettingsRequestMessage(int subAreaId, int startDefenseTime){
		this.subAreaId = subAreaId;
		this.startDefenseTime = startDefenseTime;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.subAreaId);
			writer.writeByte(this.startDefenseTime);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.subAreaId = reader.readVarShort();
			this.startDefenseTime = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("subAreaId : " + this.subAreaId);
		//Network.appendDebug("startDefenseTime : " + this.startDefenseTime);
	//}
}
