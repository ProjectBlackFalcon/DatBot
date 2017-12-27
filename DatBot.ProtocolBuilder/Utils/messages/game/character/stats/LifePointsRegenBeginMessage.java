package protocol.network.messages.game.character.stats;

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
public class LifePointsRegenBeginMessage extends NetworkMessage {
	public static final int ProtocolId = 5684;

	private int regenRate;

	public int getRegenRate() { return this.regenRate; };
	public void setRegenRate(int regenRate) { this.regenRate = regenRate; };

	public LifePointsRegenBeginMessage(){
	}

	public LifePointsRegenBeginMessage(int regenRate){
		this.regenRate = regenRate;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.regenRate);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.regenRate = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
