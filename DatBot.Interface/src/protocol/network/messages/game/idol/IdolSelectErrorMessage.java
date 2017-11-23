package protocol.network.messages.game.idol;

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
public class IdolSelectErrorMessage extends NetworkMessage {
	public static final int ProtocolId = 6584;

	public int reason;
	public int idolId;
	public boolean activate;
	public boolean party;

	public IdolSelectErrorMessage(){
	}

	public IdolSelectErrorMessage(int reason, int idolId, boolean activate, boolean party){
		this.reason = reason;
		this.idolId = idolId;
		this.activate = activate;
		this.party = party;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, activate);
			flag = BooleanByteWrapper.SetFlag(1, flag, party);
			writer.writeByte(flag);
			writer.writeByte(this.reason);
			writer.writeVarShort(this.idolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.activate = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.party = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.reason = reader.readByte();
			this.idolId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("reason : " + this.reason);
		//Network.appendDebug("idolId : " + this.idolId);
		//Network.appendDebug("activate : " + this.activate);
		//Network.appendDebug("party : " + this.party);
	//}
}
