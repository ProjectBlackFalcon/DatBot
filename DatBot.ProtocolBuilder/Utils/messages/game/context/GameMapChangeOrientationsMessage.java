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
import protocol.network.types.game.context.ActorOrientation;

@SuppressWarnings("unused")
public class GameMapChangeOrientationsMessage extends NetworkMessage {
	public static final int ProtocolId = 6155;

	private List<ActorOrientation> orientations;

	public List<ActorOrientation> getOrientations() { return this.orientations; }
	public void setOrientations(List<ActorOrientation> orientations) { this.orientations = orientations; };

	public GameMapChangeOrientationsMessage(){
	}

	public GameMapChangeOrientationsMessage(List<ActorOrientation> orientations){
		this.orientations = orientations;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.orientations.size());
			int _loc2_ = 0;
			while( _loc2_ < this.orientations.size()){
				this.orientations.get(_loc2_).Serialize(writer);
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
			this.orientations = new ArrayList<ActorOrientation>();
			while( _loc3_ <  _loc2_){
				ActorOrientation _loc15_ = new ActorOrientation();
				_loc15_.Deserialize(reader);
				this.orientations.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
