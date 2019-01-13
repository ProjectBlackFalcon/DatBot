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
public class ExchangeBidHouseTypeMessage extends NetworkMessage {
	public static final int ProtocolId = 5803;

	private int type;
	private boolean follow;

	public int getType() { return this.type; }
	public void setType(int type) { this.type = type; };
	public boolean isFollow() { return this.follow; }
	public void setFollow(boolean follow) { this.follow = follow; };

	public ExchangeBidHouseTypeMessage(){
	}

	public ExchangeBidHouseTypeMessage(int type, boolean follow){
		this.type = type;
		this.follow = follow;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.type);
			writer.writeBoolean(this.follow);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.type = reader.readVarInt();
			this.follow = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
