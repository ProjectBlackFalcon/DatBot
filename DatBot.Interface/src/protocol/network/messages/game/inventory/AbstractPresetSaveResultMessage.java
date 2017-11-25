package protocol.network.messages.game.inventory;

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
public class AbstractPresetSaveResultMessage extends NetworkMessage {
	public static final int ProtocolId = 6734;

	public int presetId;
	public int code;

	public AbstractPresetSaveResultMessage(){
	}

	public AbstractPresetSaveResultMessage(int presetId, int code){
		this.presetId = presetId;
		this.code = code;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.presetId);
			writer.writeByte(this.code);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.presetId = reader.readByte();
			this.code = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("presetId : " + this.presetId);
		//Network.appendDebug("code : " + this.code);
	//}
}