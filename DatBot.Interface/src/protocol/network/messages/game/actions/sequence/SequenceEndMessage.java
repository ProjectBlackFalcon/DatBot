package protocol.network.messages.game.actions.sequence;

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
public class SequenceEndMessage extends NetworkMessage {
	public static final int ProtocolId = 956;

	public int actionId;
	public double authorId;
	public int sequenceType;

	public SequenceEndMessage(){
	}

	public SequenceEndMessage(int actionId, double authorId, int sequenceType){
		this.actionId = actionId;
		this.authorId = authorId;
		this.sequenceType = sequenceType;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.actionId);
			writer.writeDouble(this.authorId);
			writer.writeByte(this.sequenceType);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.actionId = reader.readVarShort();
			this.authorId = reader.readDouble();
			this.sequenceType = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("actionId : " + this.actionId);
		//Network.appendDebug("authorId : " + this.authorId);
		//Network.appendDebug("sequenceType : " + this.sequenceType);
	//}
}
