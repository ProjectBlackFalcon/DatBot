package protocol.network.messages.game.alliance;

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
import protocol.network.types.game.context.roleplay.BasicNamedAllianceInformations;

@SuppressWarnings("unused")
public class AllianceInvitedMessage extends NetworkMessage {
	public static final int ProtocolId = 6397;

	public long recruterId;
	public String recruterName;
	public BasicNamedAllianceInformations allianceInfo;

	public AllianceInvitedMessage(){
	}

	public AllianceInvitedMessage(long recruterId, String recruterName, BasicNamedAllianceInformations allianceInfo){
		this.recruterId = recruterId;
		this.recruterName = recruterName;
		this.allianceInfo = allianceInfo;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.recruterId);
			writer.writeUTF(this.recruterName);
			allianceInfo.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.recruterId = reader.readVarLong();
			this.recruterName = reader.readUTF();
			this.allianceInfo = new BasicNamedAllianceInformations();
			this.allianceInfo.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("recruterId : " + this.recruterId);
		//Network.appendDebug("recruterName : " + this.recruterName);
		//Network.appendDebug("allianceInfo : " + this.allianceInfo);
	//}
}