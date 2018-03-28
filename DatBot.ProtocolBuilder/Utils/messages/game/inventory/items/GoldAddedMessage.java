package protocol.network.messages.game.inventory.items;

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
import protocol.network.types.game.data.items.GoldItem;

@SuppressWarnings("unused")
public class GoldAddedMessage extends NetworkMessage {
	public static final int ProtocolId = 6030;

	private GoldItem gold;

	public GoldItem getGold() { return this.gold; }
	public void setGold(GoldItem gold) { this.gold = gold; };

	public GoldAddedMessage(){
	}

	public GoldAddedMessage(GoldItem gold){
		this.gold = gold;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			gold.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.gold = new GoldItem();
			this.gold.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
