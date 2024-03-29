package protocol.network.types.game.guild.tax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.guild.tax.TaxCollectorComplementaryInformations;
import protocol.network.types.game.fight.ProtectedEntityWaitingForHelpInfo;

@SuppressWarnings("unused")
public class TaxCollectorWaitingForHelpInformations extends TaxCollectorComplementaryInformations {
	public static final int ProtocolId = 447;

	private ProtectedEntityWaitingForHelpInfo waitingForHelpInfo;

	public ProtectedEntityWaitingForHelpInfo getWaitingForHelpInfo() { return this.waitingForHelpInfo; }
	public void setWaitingForHelpInfo(ProtectedEntityWaitingForHelpInfo waitingForHelpInfo) { this.waitingForHelpInfo = waitingForHelpInfo; };

	public TaxCollectorWaitingForHelpInformations(){
	}

	public TaxCollectorWaitingForHelpInformations(ProtectedEntityWaitingForHelpInfo waitingForHelpInfo){
		this.waitingForHelpInfo = waitingForHelpInfo;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			waitingForHelpInfo.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.waitingForHelpInfo = new ProtectedEntityWaitingForHelpInfo();
			this.waitingForHelpInfo.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
