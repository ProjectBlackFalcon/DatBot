package protocol.network.types.secure;

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
public class TrustCertificate extends NetworkMessage {
	public static final int ProtocolId = 377;

	private int id;
	private String hash;

	public int getId() { return this.id; };
	public void setId(int id) { this.id = id; };
	public String getHash() { return this.hash; };
	public void setHash(String hash) { this.hash = hash; };

	public TrustCertificate(){
	}

	public TrustCertificate(int id, String hash){
		this.id = id;
		this.hash = hash;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.id);
			writer.writeUTF(this.hash);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readInt();
			this.hash = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
