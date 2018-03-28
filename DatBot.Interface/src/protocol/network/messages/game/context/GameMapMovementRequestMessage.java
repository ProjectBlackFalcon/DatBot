package protocol.network.messages.game.context;

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
public class GameMapMovementRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 950;

	private List<Integer> keyMovements;
	private double mapId;

	public List<Integer> getKeyMovements() { return this.keyMovements; }
	public void setKeyMovements(List<Integer> keyMovements) { this.keyMovements = keyMovements; };
	public double getMapId() { return this.mapId; }
	public void setMapId(double mapId) { this.mapId = mapId; };

	public GameMapMovementRequestMessage(){
	}

	public GameMapMovementRequestMessage(List<Integer> keyMovements, double mapId){
		this.keyMovements = keyMovements;
		this.mapId = mapId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.keyMovements.size());
			int _loc2_ = 0;
			while( _loc2_ < this.keyMovements.size()){
				writer.writeShort(this.keyMovements.get(_loc2_));
				_loc2_++;
			}
			writer.writeDouble(this.mapId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.keyMovements = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readShort();
				this.keyMovements.add(_loc15_);
				_loc3_++;
			}
			this.mapId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
