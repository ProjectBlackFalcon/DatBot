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
import protocol.network.types.game.context.IdentifiedEntityDispositionInformations;

@SuppressWarnings("unused")
public class GameEntitiesDispositionMessage extends NetworkMessage {
	public static final int ProtocolId = 5696;

	private List<IdentifiedEntityDispositionInformations> dispositions;

	public List<IdentifiedEntityDispositionInformations> getDispositions() { return this.dispositions; };
	public void setDispositions(List<IdentifiedEntityDispositionInformations> dispositions) { this.dispositions = dispositions; };

	public GameEntitiesDispositionMessage(){
	}

	public GameEntitiesDispositionMessage(List<IdentifiedEntityDispositionInformations> dispositions){
		this.dispositions = dispositions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.dispositions.size());
			int _loc2_ = 0;
			while( _loc2_ < this.dispositions.size()){
				this.dispositions.get(_loc2_).Serialize(writer);
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
			this.dispositions = new ArrayList<IdentifiedEntityDispositionInformations>();
			while( _loc3_ <  _loc2_){
				IdentifiedEntityDispositionInformations _loc15_ = new IdentifiedEntityDispositionInformations();
				_loc15_.Deserialize(reader);
				this.dispositions.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
