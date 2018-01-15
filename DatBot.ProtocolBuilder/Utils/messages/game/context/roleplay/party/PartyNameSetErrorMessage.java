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
public class PartyNameSetErrorMessage extends AbstractPartyMessage {
	public static final int ProtocolId = 6501;

	private int result;

	public int getResult() { return this.result; };
	public void setResult(int result) { this.result = result; };

	public PartyNameSetErrorMessage(){
	}

	public PartyNameSetErrorMessage(int result){
		this.result = result;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.result);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.result = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}