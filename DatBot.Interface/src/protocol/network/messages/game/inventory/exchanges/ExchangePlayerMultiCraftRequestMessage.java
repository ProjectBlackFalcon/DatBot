package protocol.network.messages.game.inventory.exchanges;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.inventory.exchanges.ExchangeRequestMessage;

@SuppressWarnings("unused")
public class ExchangePlayerMultiCraftRequestMessage extends ExchangeRequestMessage {
	public static final int ProtocolId = 5784;

	private long target;
	private int skillId;

	public long getTarget() { return this.target; }
	public void setTarget(long target) { this.target = target; };
	public int getSkillId() { return this.skillId; }
	public void setSkillId(int skillId) { this.skillId = skillId; };

	public ExchangePlayerMultiCraftRequestMessage(){
	}

	public ExchangePlayerMultiCraftRequestMessage(long target, int skillId){
		this.target = target;
		this.skillId = skillId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.target);
			writer.writeVarInt(this.skillId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.target = reader.readVarLong();
			this.skillId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
