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
public class ExchangeCrafterJobLevelupMessage extends NetworkMessage {
	public static final int ProtocolId = 6598;

	private int crafterJobLevel;

	public int getCrafterJobLevel() { return this.crafterJobLevel; };
	public void setCrafterJobLevel(int crafterJobLevel) { this.crafterJobLevel = crafterJobLevel; };

	public ExchangeCrafterJobLevelupMessage(){
	}

	public ExchangeCrafterJobLevelupMessage(int crafterJobLevel){
		this.crafterJobLevel = crafterJobLevel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.crafterJobLevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.crafterJobLevel = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
