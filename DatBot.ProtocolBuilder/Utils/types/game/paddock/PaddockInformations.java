package protocol.network.types.game.paddock;

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

@SuppressWarnings("unused")
public class PaddockInformations extends NetworkMessage {
	public static final int ProtocolId = 132;

	private int maxOutdoorMount;
	private int maxItems;

	public int getMaxOutdoorMount() { return this.maxOutdoorMount; };
	public void setMaxOutdoorMount(int maxOutdoorMount) { this.maxOutdoorMount = maxOutdoorMount; };
	public int getMaxItems() { return this.maxItems; };
	public void setMaxItems(int maxItems) { this.maxItems = maxItems; };

	public PaddockInformations(){
	}

	public PaddockInformations(int maxOutdoorMount, int maxItems){
		this.maxOutdoorMount = maxOutdoorMount;
		this.maxItems = maxItems;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.maxOutdoorMount);
			writer.writeVarShort(this.maxItems);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.maxOutdoorMount = reader.readVarShort();
			this.maxItems = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
