package protocol.network.types.game.friend;

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
public class AbstractContactInformations extends NetworkMessage {
	public static final int ProtocolId = 380;

	private int accountId;
	private String accountName;

	public int getAccountId() { return this.accountId; }
	public void setAccountId(int accountId) { this.accountId = accountId; };
	public String getAccountName() { return this.accountName; }
	public void setAccountName(String accountName) { this.accountName = accountName; };

	public AbstractContactInformations(){
	}

	public AbstractContactInformations(int accountId, String accountName){
		this.accountId = accountId;
		this.accountName = accountName;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.accountId);
			writer.writeUTF(this.accountName);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.accountId = reader.readInt();
			this.accountName = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
