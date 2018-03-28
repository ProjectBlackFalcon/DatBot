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
public class IdentifiedEntityDispositionInformations extends EntityDispositionInformations {
	public static final int ProtocolId = 107;

	private double id;

	public double getId() { return this.id; }
	public void setId(double id) { this.id = id; };

	public IdentifiedEntityDispositionInformations(){
	}

	public IdentifiedEntityDispositionInformations(double id){
		this.id = id;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.id);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.id = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
