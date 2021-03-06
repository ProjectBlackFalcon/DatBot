package protocol.network.messages.game.context.roleplay.party;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.party.AbstractPartyMessage;

@SuppressWarnings("unused")
public class PartyCannotJoinErrorMessage extends AbstractPartyMessage {
	public static final int ProtocolId = 5583;

	private int reason;

	public int getReason() { return this.reason; }
	public void setReason(int reason) { this.reason = reason; };

	public PartyCannotJoinErrorMessage(){
	}

	public PartyCannotJoinErrorMessage(int reason){
		this.reason = reason;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.reason);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.reason = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
