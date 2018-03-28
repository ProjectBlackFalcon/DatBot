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
import protocol.network.types.game.mount.MountClientData;

@SuppressWarnings("unused")
public class ExchangeStartOkMountWithOutPaddockMessage extends NetworkMessage {
	public static final int ProtocolId = 5991;

	private List<MountClientData> stabledMountsDescription;

	public List<MountClientData> getStabledMountsDescription() { return this.stabledMountsDescription; }
	public void setStabledMountsDescription(List<MountClientData> stabledMountsDescription) { this.stabledMountsDescription = stabledMountsDescription; };

	public ExchangeStartOkMountWithOutPaddockMessage(){
	}

	public ExchangeStartOkMountWithOutPaddockMessage(List<MountClientData> stabledMountsDescription){
		this.stabledMountsDescription = stabledMountsDescription;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.stabledMountsDescription.size());
			int _loc2_ = 0;
			while( _loc2_ < this.stabledMountsDescription.size()){
				this.stabledMountsDescription.get(_loc2_).Serialize(writer);
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
			this.stabledMountsDescription = new ArrayList<MountClientData>();
			while( _loc3_ <  _loc2_){
				MountClientData _loc15_ = new MountClientData();
				_loc15_.Deserialize(reader);
				this.stabledMountsDescription.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
