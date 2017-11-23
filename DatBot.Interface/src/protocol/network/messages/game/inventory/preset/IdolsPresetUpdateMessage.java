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
import protocol.network.types.game.inventory.preset.IdolsPreset;

@SuppressWarnings("unused")
public class IdolsPresetUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6606;

	public IdolsPreset idolsPreset;

	public IdolsPresetUpdateMessage(){
	}

	public IdolsPresetUpdateMessage(IdolsPreset idolsPreset){
		this.idolsPreset = idolsPreset;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			idolsPreset.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.idolsPreset = new IdolsPreset();
			this.idolsPreset.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("idolsPreset : " + this.idolsPreset);
	//}
}
