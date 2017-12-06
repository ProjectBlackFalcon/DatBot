package protocol.network.types.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.social.AbstractSocialGroupInfos;

@SuppressWarnings("unused")
public class BasicAllianceInformations extends AbstractSocialGroupInfos {
	public static final int ProtocolId = 419;

	public int allianceId;
	public String allianceTag;

	public BasicAllianceInformations(){
	}

	public BasicAllianceInformations(int allianceId, String allianceTag){
		this.allianceId = allianceId;
		this.allianceTag = allianceTag;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.allianceId);
			writer.writeUTF(this.allianceTag);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.allianceId = reader.readVarInt();
			this.allianceTag = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("allianceId : " + this.allianceId);
		//Network.appendDebug("allianceTag : " + this.allianceTag);
	//}
}
