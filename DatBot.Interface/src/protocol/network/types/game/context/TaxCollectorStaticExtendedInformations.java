package protocol.network.types.game.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.TaxCollectorStaticInformations;
import protocol.network.types.game.context.roleplay.AllianceInformations;

@SuppressWarnings("unused")
public class TaxCollectorStaticExtendedInformations extends TaxCollectorStaticInformations {
	public static final int ProtocolId = 440;

	public AllianceInformations allianceIdentity;

	public TaxCollectorStaticExtendedInformations(){
	}

	public TaxCollectorStaticExtendedInformations(AllianceInformations allianceIdentity){
		this.allianceIdentity = allianceIdentity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			allianceIdentity.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.allianceIdentity = new AllianceInformations();
			this.allianceIdentity.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("allianceIdentity : " + this.allianceIdentity);
	//}
}
