package protocol.network.messages.game.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.GameContextRemoveElementMessage;

@SuppressWarnings("unused")
public class GameContextRemoveElementWithEventMessage extends GameContextRemoveElementMessage {
	public static final int ProtocolId = 6412;

	private int elementEventId;

	public int getElementEventId() { return this.elementEventId; }
	public void setElementEventId(int elementEventId) { this.elementEventId = elementEventId; };

	public GameContextRemoveElementWithEventMessage(){
	}

	public GameContextRemoveElementWithEventMessage(int elementEventId){
		this.elementEventId = elementEventId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.elementEventId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.elementEventId = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
