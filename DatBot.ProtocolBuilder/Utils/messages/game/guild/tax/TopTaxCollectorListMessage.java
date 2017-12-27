package protocol.network.messages.game.guild.tax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.guild.tax.AbstractTaxCollectorListMessage;

@SuppressWarnings("unused")
public class TopTaxCollectorListMessage extends AbstractTaxCollectorListMessage {
	public static final int ProtocolId = 6565;

	private boolean isDungeon;

	public boolean isIsDungeon() { return this.isDungeon; };
	public void setIsDungeon(boolean isDungeon) { this.isDungeon = isDungeon; };

	public TopTaxCollectorListMessage(){
	}

	public TopTaxCollectorListMessage(boolean isDungeon){
		this.isDungeon = isDungeon;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeBoolean(this.isDungeon);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.isDungeon = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
