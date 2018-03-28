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
public class SequenceStartMessage extends NetworkMessage {
	public static final int ProtocolId = 955;

	private int sequenceType;
	private double authorId;

	public int getSequenceType() { return this.sequenceType; }
	public void setSequenceType(int sequenceType) { this.sequenceType = sequenceType; };
	public double getAuthorId() { return this.authorId; }
	public void setAuthorId(double authorId) { this.authorId = authorId; };

	public SequenceStartMessage(){
	}

	public SequenceStartMessage(int sequenceType, double authorId){
		this.sequenceType = sequenceType;
		this.authorId = authorId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.sequenceType);
			writer.writeDouble(this.authorId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.sequenceType = reader.readByte();
			this.authorId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
