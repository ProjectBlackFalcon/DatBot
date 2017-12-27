package protocol.network.messages.game.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.actions.AbstractGameActionMessage;

@SuppressWarnings("unused")
public class AbstractGameActionWithAckMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 1001;

	private int waitAckId;

	public int getWaitAckId() { return this.waitAckId; };
	public void setWaitAckId(int waitAckId) { this.waitAckId = waitAckId; };

	public AbstractGameActionWithAckMessage(){
	}

	public AbstractGameActionWithAckMessage(int waitAckId){
		this.waitAckId = waitAckId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.waitAckId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.waitAckId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
