package protocol.network.messages.game.context.roleplay.treasureHunt;

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
public class TreasureHuntShowLegendaryUIMessage extends NetworkMessage {
	public static final int ProtocolId = 6498;

	private List<Integer> availableLegendaryIds;

	public List<Integer> getAvailableLegendaryIds() { return this.availableLegendaryIds; };
	public void setAvailableLegendaryIds(List<Integer> availableLegendaryIds) { this.availableLegendaryIds = availableLegendaryIds; };

	public TreasureHuntShowLegendaryUIMessage(){
	}

	public TreasureHuntShowLegendaryUIMessage(List<Integer> availableLegendaryIds){
		this.availableLegendaryIds = availableLegendaryIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.availableLegendaryIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.availableLegendaryIds.size()){
				writer.writeVarShort(this.availableLegendaryIds.get(_loc2_));
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
			this.availableLegendaryIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.availableLegendaryIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
