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
import protocol.network.messages.game.inventory.exchanges.ExchangeCraftResultWithObjectDescMessage;

@SuppressWarnings("unused")
public class ExchangeCraftResultMagicWithObjectDescMessage extends ExchangeCraftResultWithObjectDescMessage {
	public static final int ProtocolId = 6188;

	private int magicPoolStatus;

	public int getMagicPoolStatus() { return this.magicPoolStatus; }
	public void setMagicPoolStatus(int magicPoolStatus) { this.magicPoolStatus = magicPoolStatus; };

	public ExchangeCraftResultMagicWithObjectDescMessage(){
	}

	public ExchangeCraftResultMagicWithObjectDescMessage(int magicPoolStatus){
		this.magicPoolStatus = magicPoolStatus;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.magicPoolStatus);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.magicPoolStatus = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
