package protocol.network.messages.game.inventory.exchanges;

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
public class ExchangeTypesExchangerDescriptionForUserMessage extends NetworkMessage {
	public static final int ProtocolId = 5765;

	private List<Integer> typeDescription;

	public List<Integer> getTypeDescription() { return this.typeDescription; }
	public void setTypeDescription(List<Integer> typeDescription) { this.typeDescription = typeDescription; };

	public ExchangeTypesExchangerDescriptionForUserMessage(){
	}

	public ExchangeTypesExchangerDescriptionForUserMessage(List<Integer> typeDescription){
		this.typeDescription = typeDescription;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.typeDescription.size());
			int _loc2_ = 0;
			while( _loc2_ < this.typeDescription.size()){
				writer.writeVarInt(this.typeDescription.get(_loc2_));
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
			this.typeDescription = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarInt();
				this.typeDescription.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
