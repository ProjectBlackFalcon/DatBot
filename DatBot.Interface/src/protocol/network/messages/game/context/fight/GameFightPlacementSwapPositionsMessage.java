package protocol.network.messages.game.context.fight;

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
public class GameFightPlacementSwapPositionsMessage extends NetworkMessage {
	public static final int ProtocolId = 6544;

	private List<IdentifiedEntityDispositionInformations> dispositions;

	public List<IdentifiedEntityDispositionInformations> getDispositions() { return this.dispositions; }
	public void setDispositions(List<IdentifiedEntityDispositionInformations> dispositions) { this.dispositions = dispositions; };

	public GameFightPlacementSwapPositionsMessage(){
	}

	public GameFightPlacementSwapPositionsMessage(List<IdentifiedEntityDispositionInformations> dispositions){
		this.dispositions = dispositions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			int _loc2_ = 0;
			while( _loc2_ < 2){
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
			int _loc2_  = 0;
			this.dispositions = new ArrayList<>();
			while( _loc2_ < 2){
				this.dispositions.add(new IdentifiedEntityDispositionInformations());
				this.dispositions.get( _loc2_).Deserialize(reader);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
