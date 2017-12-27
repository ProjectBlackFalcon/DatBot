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
public class ObjectEffectMinMax extends ObjectEffect {
	public static final int ProtocolId = 82;

	private int min;
	private int max;

	public int getMin() { return this.min; };
	public void setMin(int min) { this.min = min; };
	public int getMax() { return this.max; };
	public void setMax(int max) { this.max = max; };

	public ObjectEffectMinMax(){
	}

	public ObjectEffectMinMax(int min, int max){
		this.min = min;
		this.max = max;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.min);
			writer.writeVarInt(this.max);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.min = reader.readVarInt();
			this.max = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
