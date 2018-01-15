package protocol.network.messages.game.context.roleplay.havenbag;

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
public class HavenBagDailyLoteryMessage extends NetworkMessage {
	public static final int ProtocolId = 6644;

	private int returnType;
	private String tokenId;

	public int getReturnType() { return this.returnType; };
	public void setReturnType(int returnType) { this.returnType = returnType; };
	public String getTokenId() { return this.tokenId; };
	public void setTokenId(String tokenId) { this.tokenId = tokenId; };

	public HavenBagDailyLoteryMessage(){
	}

	public HavenBagDailyLoteryMessage(int returnType, String tokenId){
		this.returnType = returnType;
		this.tokenId = tokenId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.returnType);
			writer.writeUTF(this.tokenId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.returnType = reader.readByte();
			this.tokenId = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}