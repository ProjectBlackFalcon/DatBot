package protocol.network.messages.game.guild.tax;

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
import protocol.network.types.game.guild.tax.TaxCollectorInformations;

@SuppressWarnings("unused")
public class TaxCollectorMovementAddMessage extends NetworkMessage {
	public static final int ProtocolId = 5917;

	private TaxCollectorInformations informations;

	public TaxCollectorInformations getInformations() { return this.informations; }
	public void setInformations(TaxCollectorInformations informations) { this.informations = informations; };

	public TaxCollectorMovementAddMessage(){
	}

	public TaxCollectorMovementAddMessage(TaxCollectorInformations informations){
		this.informations = informations;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(TaxCollectorInformations.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.informations = (TaxCollectorInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.informations.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
