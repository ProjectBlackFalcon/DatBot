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
public class AbstractPresetSaveMessage extends NetworkMessage {
	public static final int ProtocolId = 6736;

	private int presetId;
	private int symbolId;

	public int getPresetId() { return this.presetId; };
	public void setPresetId(int presetId) { this.presetId = presetId; };
	public int getSymbolId() { return this.symbolId; };
	public void setSymbolId(int symbolId) { this.symbolId = symbolId; };

	public AbstractPresetSaveMessage(){
	}

	public AbstractPresetSaveMessage(int presetId, int symbolId){
		this.presetId = presetId;
		this.symbolId = symbolId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.presetId);
			writer.writeByte(this.symbolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.presetId = reader.readByte();
			this.symbolId = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
