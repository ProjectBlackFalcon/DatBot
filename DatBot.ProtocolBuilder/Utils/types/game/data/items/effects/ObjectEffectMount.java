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
public class ObjectEffectMount extends ObjectEffect {
	public static final int ProtocolId = 179;

	private int mountId;
	private double date;
	private int modelId;

	public int getMountId() { return this.mountId; };
	public void setMountId(int mountId) { this.mountId = mountId; };
	public double getDate() { return this.date; };
	public void setDate(double date) { this.date = date; };
	public int getModelId() { return this.modelId; };
	public void setModelId(int modelId) { this.modelId = modelId; };

	public ObjectEffectMount(){
	}

	public ObjectEffectMount(int mountId, double date, int modelId){
		this.mountId = mountId;
		this.date = date;
		this.modelId = modelId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.mountId);
			writer.writeDouble(this.date);
			writer.writeVarShort(this.modelId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.mountId = reader.readInt();
			this.date = reader.readDouble();
			this.modelId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
