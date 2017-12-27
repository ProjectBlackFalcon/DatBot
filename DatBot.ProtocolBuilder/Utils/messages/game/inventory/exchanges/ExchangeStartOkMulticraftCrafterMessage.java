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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class ExchangeStartOkMulticraftCrafterMessage extends NetworkMessage {
	public static final int ProtocolId = 5818;

	private int skillId;

	public int getSkillId() { return this.skillId; };
	public void setSkillId(int skillId) { this.skillId = skillId; };

	public ExchangeStartOkMulticraftCrafterMessage(){
	}

	public ExchangeStartOkMulticraftCrafterMessage(int skillId){
		this.skillId = skillId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.skillId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.skillId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
