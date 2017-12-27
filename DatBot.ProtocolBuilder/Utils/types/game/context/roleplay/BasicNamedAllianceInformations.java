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
import protocol.network.types.game.context.roleplay.BasicAllianceInformations;

@SuppressWarnings("unused")
public class BasicNamedAllianceInformations extends BasicAllianceInformations {
	public static final int ProtocolId = 418;

	private String allianceName;

	public String getAllianceName() { return this.allianceName; };
	public void setAllianceName(String allianceName) { this.allianceName = allianceName; };

	public BasicNamedAllianceInformations(){
	}

	public BasicNamedAllianceInformations(String allianceName){
		this.allianceName = allianceName;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.allianceName);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.allianceName = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
