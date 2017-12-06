package protocol.network.types.game.context.roleplay;

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
import protocol.network.types.game.context.MapCoordinatesExtended;

@SuppressWarnings("unused")
public class AtlasPointsInformations extends NetworkMessage {
	public static final int ProtocolId = 175;

	public int type;
	public List<MapCoordinatesExtended> coords;

	public AtlasPointsInformations(){
	}

	public AtlasPointsInformations(int type, List<MapCoordinatesExtended> coords){
		this.type = type;
		this.coords = coords;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.type);
			writer.writeShort(this.coords.size());
			int _loc2_ = 0;
			while( _loc2_ < this.coords.size()){
				this.coords.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.type = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.coords = new ArrayList<MapCoordinatesExtended>();
			while( _loc3_ <  _loc2_){
				MapCoordinatesExtended _loc15_ = new MapCoordinatesExtended();
				_loc15_.Deserialize(reader);
				this.coords.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("type : " + this.type);
		//for(MapCoordinatesExtended a : coords) {
			//Network.appendDebug("coords : " + a);
		//}
	//}
}
