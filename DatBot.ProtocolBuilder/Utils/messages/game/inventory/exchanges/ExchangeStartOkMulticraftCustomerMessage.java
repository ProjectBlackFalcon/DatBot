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
public class ExchangeStartOkMulticraftCustomerMessage extends NetworkMessage {
	public static final int ProtocolId = 5817;

	private int skillId;
	private int crafterJobLevel;

	public int getSkillId() { return this.skillId; }
	public void setSkillId(int skillId) { this.skillId = skillId; };
	public int getCrafterJobLevel() { return this.crafterJobLevel; }
	public void setCrafterJobLevel(int crafterJobLevel) { this.crafterJobLevel = crafterJobLevel; };

	public ExchangeStartOkMulticraftCustomerMessage(){
	}

	public ExchangeStartOkMulticraftCustomerMessage(int skillId, int crafterJobLevel){
		this.skillId = skillId;
		this.crafterJobLevel = crafterJobLevel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.skillId);
			writer.writeByte(this.crafterJobLevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.skillId = reader.readVarInt();
			this.crafterJobLevel = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
