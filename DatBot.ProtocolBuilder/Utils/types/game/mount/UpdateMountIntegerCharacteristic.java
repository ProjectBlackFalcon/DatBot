package protocol.network.types.game.mount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.mount.UpdateMountCharacteristic;

@SuppressWarnings("unused")
public class UpdateMountIntegerCharacteristic extends UpdateMountCharacteristic {
	public static final int ProtocolId = 537;

	private int value;

	public int getValue() { return this.value; }
	public void setValue(int value) { this.value = value; };

	public UpdateMountIntegerCharacteristic(){
	}

	public UpdateMountIntegerCharacteristic(int value){
		this.value = value;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.value);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.value = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
