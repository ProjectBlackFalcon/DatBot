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
import protocol.network.types.game.context.roleplay.HumanOption;
import protocol.network.types.game.context.roleplay.AllianceInformations;

@SuppressWarnings("unused")
public class HumanOptionAlliance extends HumanOption {
	public static final int ProtocolId = 425;

	public AllianceInformations allianceInformations;
	public int aggressable;

	public HumanOptionAlliance(){
	}

	public HumanOptionAlliance(AllianceInformations allianceInformations, int aggressable){
		this.allianceInformations = allianceInformations;
		this.aggressable = aggressable;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			allianceInformations.Serialize(writer);
			writer.writeByte(this.aggressable);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.allianceInformations = new AllianceInformations();
			this.allianceInformations.Deserialize(reader);
			this.aggressable = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("allianceInformations : " + this.allianceInformations);
		//Network.appendDebug("aggressable : " + this.aggressable);
	//}
}
