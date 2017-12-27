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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class EmoteListMessage extends NetworkMessage {
	public static final int ProtocolId = 5689;

	private List<Integer> emoteIds;

	public List<Integer> getEmoteIds() { return this.emoteIds; };
	public void setEmoteIds(List<Integer> emoteIds) { this.emoteIds = emoteIds; };

	public EmoteListMessage(){
	}

	public EmoteListMessage(List<Integer> emoteIds){
		this.emoteIds = emoteIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.emoteIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.emoteIds.size()){
				writer.writeByte(this.emoteIds.get(_loc2_));
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
			this.emoteIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readByte();
				this.emoteIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
