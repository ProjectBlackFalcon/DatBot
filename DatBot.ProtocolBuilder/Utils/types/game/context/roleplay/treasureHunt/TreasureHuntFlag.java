package protocol.network.types.game.context.roleplay.treasureHunt;

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
public class TreasureHuntFlag extends NetworkMessage {
	public static final int ProtocolId = 473;

	private double mapId;
	private int state;

	public double getMapId() { return this.mapId; };
	public void setMapId(double mapId) { this.mapId = mapId; };
	public int getState() { return this.state; };
	public void setState(int state) { this.state = state; };

	public TreasureHuntFlag(){
	}

	public TreasureHuntFlag(double mapId, int state){
		this.mapId = mapId;
		this.state = state;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.mapId);
			writer.writeByte(this.state);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.mapId = reader.readDouble();
			this.state = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
