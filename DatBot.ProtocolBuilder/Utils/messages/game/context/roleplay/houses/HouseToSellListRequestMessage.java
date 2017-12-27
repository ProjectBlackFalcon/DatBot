package protocol.network.messages.game.context.roleplay.houses;

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
public class HouseToSellListRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6139;

	private int pageIndex;

	public int getPageIndex() { return this.pageIndex; };
	public void setPageIndex(int pageIndex) { this.pageIndex = pageIndex; };

	public HouseToSellListRequestMessage(){
	}

	public HouseToSellListRequestMessage(int pageIndex){
		this.pageIndex = pageIndex;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.pageIndex);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.pageIndex = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
