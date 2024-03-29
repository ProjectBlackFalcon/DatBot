package protocol.network.messages.game.inventory.preset;

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
public class InventoryPresetItemUpdateErrorMessage extends NetworkMessage {
	public static final int ProtocolId = 6211;

	private int code;

	public int getCode() { return this.code; };
	public void setCode(int code) { this.code = code; };

	public InventoryPresetItemUpdateErrorMessage(){
	}

	public InventoryPresetItemUpdateErrorMessage(int code){
		this.code = code;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.code);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.code = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
