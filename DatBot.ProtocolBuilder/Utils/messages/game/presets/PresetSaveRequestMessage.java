package protocol.network.messages.game.presets;

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
public class PresetSaveRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6761;

	private int presetId;
	private int symbolId;
	private boolean updateData;

	public int getPresetId() { return this.presetId; }
	public void setPresetId(int presetId) { this.presetId = presetId; };
	public int getSymbolId() { return this.symbolId; }
	public void setSymbolId(int symbolId) { this.symbolId = symbolId; };
	public boolean isUpdateData() { return this.updateData; }
	public void setUpdateData(boolean updateData) { this.updateData = updateData; };

	public PresetSaveRequestMessage(){
	}

	public PresetSaveRequestMessage(int presetId, int symbolId, boolean updateData){
		this.presetId = presetId;
		this.symbolId = symbolId;
		this.updateData = updateData;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.presetId);
			writer.writeByte(this.symbolId);
			writer.writeBoolean(this.updateData);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.presetId = reader.readShort();
			this.symbolId = reader.readByte();
			this.updateData = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
