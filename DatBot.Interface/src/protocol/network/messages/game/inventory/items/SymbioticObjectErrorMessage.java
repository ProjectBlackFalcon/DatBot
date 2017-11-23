package protocol.network.messages.game.inventory.items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.inventory.items.ObjectErrorMessage;

@SuppressWarnings("unused")
public class SymbioticObjectErrorMessage extends ObjectErrorMessage {
	public static final int ProtocolId = 6526;

	public int errorCode;

	public SymbioticObjectErrorMessage(){
	}

	public SymbioticObjectErrorMessage(int errorCode){
		this.errorCode = errorCode;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.errorCode);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.errorCode = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("errorCode : " + this.errorCode);
	//}
}
