package protocol.network.types.game.data.items.effects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.data.items.effects.ObjectEffect;

@SuppressWarnings("unused")
public class ObjectEffectInteger extends ObjectEffect {
	public static final int ProtocolId = 70;

	public int value;

	public ObjectEffectInteger(){
	}

	public ObjectEffectInteger(int value){
		this.value = value;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.value);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.value = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("value : " + this.value);
	//}
}
