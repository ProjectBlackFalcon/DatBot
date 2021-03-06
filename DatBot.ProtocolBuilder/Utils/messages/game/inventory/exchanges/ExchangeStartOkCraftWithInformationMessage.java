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
import protocol.network.messages.game.inventory.exchanges.ExchangeStartOkCraftMessage;

@SuppressWarnings("unused")
public class ExchangeStartOkCraftWithInformationMessage extends ExchangeStartOkCraftMessage {
	public static final int ProtocolId = 5941;

	private int skillId;

	public int getSkillId() { return this.skillId; }
	public void setSkillId(int skillId) { this.skillId = skillId; };

	public ExchangeStartOkCraftWithInformationMessage(){
	}

	public ExchangeStartOkCraftWithInformationMessage(int skillId){
		this.skillId = skillId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.skillId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.skillId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
