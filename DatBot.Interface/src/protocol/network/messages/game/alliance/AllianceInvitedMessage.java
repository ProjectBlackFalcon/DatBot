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

	private long recruterId;
	private String recruterName;
	private BasicNamedAllianceInformations allianceInfo;

	public long getRecruterId() { return this.recruterId; }
	public void setRecruterId(long recruterId) { this.recruterId = recruterId; };
	public String getRecruterName() { return this.recruterName; }
	public void setRecruterName(String recruterName) { this.recruterName = recruterName; };
	public BasicNamedAllianceInformations getAllianceInfo() { return this.allianceInfo; }
	public void setAllianceInfo(BasicNamedAllianceInformations allianceInfo) { this.allianceInfo = allianceInfo; };

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
	}

}
