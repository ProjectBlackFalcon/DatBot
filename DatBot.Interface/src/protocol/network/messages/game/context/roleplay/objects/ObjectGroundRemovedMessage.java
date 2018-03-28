package protocol.network.messages.game.context.roleplay.objects;

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
public class ObjectGroundRemovedMessage extends NetworkMessage {
	public static final int ProtocolId = 3014;

	private int cell;

	public int getCell() { return this.cell; }
	public void setCell(int cell) { this.cell = cell; };

	public ObjectGroundRemovedMessage(){
	}

	public ObjectGroundRemovedMessage(int cell){
		this.cell = cell;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.cell);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.cell = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
