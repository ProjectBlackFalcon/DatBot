package protocol.network.messages.game.context.roleplay.npc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.npc.TaxCollectorDialogQuestionExtendedMessage;
import protocol.network.types.game.context.roleplay.BasicNamedAllianceInformations;

@SuppressWarnings("unused")
public class AllianceTaxCollectorDialogQuestionExtendedMessage extends TaxCollectorDialogQuestionExtendedMessage {
	public static final int ProtocolId = 6445;

	private BasicNamedAllianceInformations alliance;

	public BasicNamedAllianceInformations getAlliance() { return this.alliance; };
	public void setAlliance(BasicNamedAllianceInformations alliance) { this.alliance = alliance; };

	public AllianceTaxCollectorDialogQuestionExtendedMessage(){
	}

	public AllianceTaxCollectorDialogQuestionExtendedMessage(BasicNamedAllianceInformations alliance){
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
			this.alliance = new BasicNamedAllianceInformations();
			this.alliance.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
