package protocol.network.types.game.prism;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.prism.PrismInformation;
import protocol.network.types.game.context.roleplay.AllianceInformations;

@SuppressWarnings("unused")
public class AlliancePrismInformation extends PrismInformation {
	public static final int ProtocolId = 427;

	private AllianceInformations alliance;

	public AllianceInformations getAlliance() { return this.alliance; }
	public void setAlliance(AllianceInformations alliance) { this.alliance = alliance; };

	public AlliancePrismInformation(){
	}

	public AlliancePrismInformation(AllianceInformations alliance){
		this.alliance = alliance;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			alliance.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.alliance = new AllianceInformations();
			this.alliance.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
