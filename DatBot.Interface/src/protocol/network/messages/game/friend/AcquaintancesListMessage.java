package protocol.network.messages.game.friend;

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
import protocol.network.types.game.friend.AcquaintanceInformation;

@SuppressWarnings("unused")
public class AcquaintancesListMessage extends NetworkMessage {
	public static final int ProtocolId = 6820;

	private List<AcquaintanceInformation> acquaintanceList;

	public List<AcquaintanceInformation> getAcquaintanceList() { return this.acquaintanceList; }
	public void setAcquaintanceList(List<AcquaintanceInformation> acquaintanceList) { this.acquaintanceList = acquaintanceList; };

	public AcquaintancesListMessage(){
	}

	public AcquaintancesListMessage(List<AcquaintanceInformation> acquaintanceList){
		this.acquaintanceList = acquaintanceList;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.acquaintanceList.size());
			int _loc2_ = 0;
			while( _loc2_ < this.acquaintanceList.size()){
				writer.writeShort(AcquaintanceInformation.ProtocolId);
				this.acquaintanceList.get(_loc2_).Serialize(writer);
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
			this.acquaintanceList = new ArrayList<>();
			while( _loc3_ <  _loc2_){
				AcquaintanceInformation _loc15_ = (AcquaintanceInformation) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.acquaintanceList.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
