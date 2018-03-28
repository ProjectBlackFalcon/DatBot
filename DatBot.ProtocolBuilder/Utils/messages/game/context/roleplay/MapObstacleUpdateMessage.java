package protocol.network.messages.game.context.roleplay;

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
import protocol.network.types.game.interactive.MapObstacle;

@SuppressWarnings("unused")
public class MapObstacleUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6051;

	private List<MapObstacle> obstacles;

	public List<MapObstacle> getObstacles() { return this.obstacles; }
	public void setObstacles(List<MapObstacle> obstacles) { this.obstacles = obstacles; };

	public MapObstacleUpdateMessage(){
	}

	public MapObstacleUpdateMessage(List<MapObstacle> obstacles){
		this.obstacles = obstacles;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.obstacles.size());
			int _loc2_ = 0;
			while( _loc2_ < this.obstacles.size()){
				this.obstacles.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.obstacles = new ArrayList<MapObstacle>();
			while( _loc3_ <  _loc2_){
				MapObstacle _loc15_ = new MapObstacle();
				_loc15_.Deserialize(reader);
				this.obstacles.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
