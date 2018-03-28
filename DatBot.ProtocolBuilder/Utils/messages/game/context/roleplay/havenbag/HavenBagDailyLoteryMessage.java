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
	private String gameActionId;

	public int getReturnType() { return this.returnType; }
	public void setReturnType(int returnType) { this.returnType = returnType; };
	public String getGameActionId() { return this.gameActionId; }
	public void setGameActionId(String gameActionId) { this.gameActionId = gameActionId; };

	public HavenBagDailyLoteryMessage(){
	}

	public HavenBagDailyLoteryMessage(int returnType, String gameActionId){
		this.returnType = returnType;
		this.gameActionId = gameActionId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.returnType);
			writer.writeUTF(this.gameActionId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.returnType = reader.readByte();
			this.gameActionId = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
