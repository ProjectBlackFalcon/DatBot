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
public class RecycleResultMessage extends NetworkMessage {
	public static final int ProtocolId = 6601;

	private int nuggetsForPrism;
	private int nuggetsForPlayer;

	public int getNuggetsForPrism() { return this.nuggetsForPrism; }
	public void setNuggetsForPrism(int nuggetsForPrism) { this.nuggetsForPrism = nuggetsForPrism; };
	public int getNuggetsForPlayer() { return this.nuggetsForPlayer; }
	public void setNuggetsForPlayer(int nuggetsForPlayer) { this.nuggetsForPlayer = nuggetsForPlayer; };

	public RecycleResultMessage(){
	}

	public RecycleResultMessage(int nuggetsForPrism, int nuggetsForPlayer){
		this.nuggetsForPrism = nuggetsForPrism;
		this.nuggetsForPlayer = nuggetsForPlayer;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.nuggetsForPrism);
			writer.writeVarInt(this.nuggetsForPlayer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.nuggetsForPrism = reader.readVarInt();
			this.nuggetsForPlayer = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
