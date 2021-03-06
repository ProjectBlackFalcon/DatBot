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
import protocol.network.messages.game.inventory.exchanges.ExchangeStartOkMountWithOutPaddockMessage;
import protocol.network.types.game.mount.MountClientData;

@SuppressWarnings("unused")
public class ExchangeStartOkMountMessage extends ExchangeStartOkMountWithOutPaddockMessage {
	public static final int ProtocolId = 5979;

	private List<MountClientData> paddockedMountsDescription;

	public List<MountClientData> getPaddockedMountsDescription() { return this.paddockedMountsDescription; }
	public void setPaddockedMountsDescription(List<MountClientData> paddockedMountsDescription) { this.paddockedMountsDescription = paddockedMountsDescription; };

	public ExchangeStartOkMountMessage(){
	}

	public ExchangeStartOkMountMessage(List<MountClientData> paddockedMountsDescription){
		this.paddockedMountsDescription = paddockedMountsDescription;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.paddockedMountsDescription.size());
			int _loc2_ = 0;
			while( _loc2_ < this.paddockedMountsDescription.size()){
				this.paddockedMountsDescription.get(_loc2_).Serialize(writer);
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
			this.paddockedMountsDescription = new ArrayList<MountClientData>();
			while( _loc3_ <  _loc2_){
				MountClientData _loc15_ = new MountClientData();
				_loc15_.Deserialize(reader);
				this.paddockedMountsDescription.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
