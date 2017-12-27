package protocol.network.messages.game.context.roleplay.emote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.emote.EmotePlayAbstractMessage;

@SuppressWarnings("unused")
public class EmotePlayMassiveMessage extends EmotePlayAbstractMessage {
	public static final int ProtocolId = 5691;

	private List<Double> actorIds;

	public List<Double> getActorIds() { return this.actorIds; };
	public void setActorIds(List<Double> actorIds) { this.actorIds = actorIds; };

	public EmotePlayMassiveMessage(){
	}

	public EmotePlayMassiveMessage(List<Double> actorIds){
		this.actorIds = actorIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.actorIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.actorIds.size()){
				writer.writeDouble(this.actorIds.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.actorIds = new ArrayList<Double>();
			while( _loc3_ <  _loc2_){
				double _loc15_ = reader.readDouble();
				this.actorIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
