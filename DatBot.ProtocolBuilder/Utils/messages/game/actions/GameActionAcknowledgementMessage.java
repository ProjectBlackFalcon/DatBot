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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class GameActionAcknowledgementMessage extends NetworkMessage {
	public static final int ProtocolId = 957;

	private boolean valid;
	private int actionId;

	public boolean isValid() { return this.valid; }
	public void setValid(boolean valid) { this.valid = valid; };
	public int getActionId() { return this.actionId; }
	public void setActionId(int actionId) { this.actionId = actionId; };

	public GameActionAcknowledgementMessage(){
	}

	public GameActionAcknowledgementMessage(boolean valid, int actionId){
		this.valid = valid;
		this.actionId = actionId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.valid);
			writer.writeByte(this.actionId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.valid = reader.readBoolean();
			this.actionId = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
