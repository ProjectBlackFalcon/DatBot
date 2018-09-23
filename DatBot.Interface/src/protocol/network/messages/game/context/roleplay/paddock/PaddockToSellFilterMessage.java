package protocol.network.messages.game.context.roleplay.paddock;

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
public class PaddockToSellFilterMessage extends NetworkMessage {
	public static final int ProtocolId = 6161;

	private int areaId;
	private int atLeastNbMount;
	private int atLeastNbMachine;
	private long maxPrice;
	private int orderBy;

	public int getAreaId() { return this.areaId; }
	public void setAreaId(int areaId) { this.areaId = areaId; };
	public int getAtLeastNbMount() { return this.atLeastNbMount; }
	public void setAtLeastNbMount(int atLeastNbMount) { this.atLeastNbMount = atLeastNbMount; };
	public int getAtLeastNbMachine() { return this.atLeastNbMachine; }
	public void setAtLeastNbMachine(int atLeastNbMachine) { this.atLeastNbMachine = atLeastNbMachine; };
	public long getMaxPrice() { return this.maxPrice; }
	public void setMaxPrice(long maxPrice) { this.maxPrice = maxPrice; };
	public int getOrderBy() { return this.orderBy; }
	public void setOrderBy(int orderBy) { this.orderBy = orderBy; };

	public PaddockToSellFilterMessage(){
	}

	public PaddockToSellFilterMessage(int areaId, int atLeastNbMount, int atLeastNbMachine, long maxPrice, int orderBy){
		this.areaId = areaId;
		this.atLeastNbMount = atLeastNbMount;
		this.atLeastNbMachine = atLeastNbMachine;
		this.maxPrice = maxPrice;
		this.orderBy = orderBy;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.areaId);
			writer.writeByte(this.atLeastNbMount);
			writer.writeByte(this.atLeastNbMachine);
			writer.writeVarLong(this.maxPrice);
			writer.writeByte(this.orderBy);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.areaId = reader.readInt();
			this.atLeastNbMount = reader.readByte();
			this.atLeastNbMachine = reader.readByte();
			this.maxPrice = reader.readVarLong();
			this.orderBy = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
