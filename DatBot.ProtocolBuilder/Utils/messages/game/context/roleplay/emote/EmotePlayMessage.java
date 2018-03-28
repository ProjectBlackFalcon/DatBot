package protocol.network.messages.game.context.roleplay.emote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.emote.EmotePlayAbstractMessage;

@SuppressWarnings("unused")
public class EmotePlayMessage extends EmotePlayAbstractMessage {
	public static final int ProtocolId = 5683;

	private double actorId;
	private int accountId;

	public double getActorId() { return this.actorId; }
	public void setActorId(double actorId) { this.actorId = actorId; };
	public int getAccountId() { return this.accountId; }
	public void setAccountId(int accountId) { this.accountId = accountId; };

	public EmotePlayMessage(){
	}

	public EmotePlayMessage(double actorId, int accountId){
		this.actorId = actorId;
		this.accountId = accountId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.actorId);
			writer.writeInt(this.accountId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.actorId = reader.readDouble();
			this.accountId = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
