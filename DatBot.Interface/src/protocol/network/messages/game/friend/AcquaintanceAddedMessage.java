package protocol.network.messages.game.friend;

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
import protocol.network.types.game.friend.AcquaintanceInformation;

@SuppressWarnings("unused")
public class AcquaintanceAddedMessage extends NetworkMessage {
	public static final int ProtocolId = 6818;

	private AcquaintanceInformation acquaintanceAdded;

	public AcquaintanceInformation getAcquaintanceAdded() { return this.acquaintanceAdded; }
	public void setAcquaintanceAdded(AcquaintanceInformation acquaintanceAdded) { this.acquaintanceAdded = acquaintanceAdded; };

	public AcquaintanceAddedMessage(){
	}

	public AcquaintanceAddedMessage(AcquaintanceInformation acquaintanceAdded){
		this.acquaintanceAdded = acquaintanceAdded;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(AcquaintanceInformation.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.acquaintanceAdded = (AcquaintanceInformation) ProtocolTypeManager.getInstance(reader.readShort());
			this.acquaintanceAdded.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
