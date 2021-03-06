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
import protocol.network.types.game.context.EntityDispositionInformations;

@SuppressWarnings("unused")
public class FightEntityDispositionInformations extends EntityDispositionInformations {
	public static final int ProtocolId = 217;

	private double carryingCharacterId;

	public double getCarryingCharacterId() { return this.carryingCharacterId; }
	public void setCarryingCharacterId(double carryingCharacterId) { this.carryingCharacterId = carryingCharacterId; };

	public FightEntityDispositionInformations(){
	}

	public FightEntityDispositionInformations(double carryingCharacterId){
		this.carryingCharacterId = carryingCharacterId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.carryingCharacterId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.carryingCharacterId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
