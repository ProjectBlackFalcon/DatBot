package protocol.network;

import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;

public abstract class NetworkMessage {

	public abstract void Serialize(DofusDataWriter writer);
	public abstract void Deserialize(DofusDataReader reader);

}
