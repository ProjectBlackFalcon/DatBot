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
public class ExchangeMountsPaddockAddMessage extends NetworkMessage {
	public static final int ProtocolId = 6561;

	private List<MountClientData> mountDescription;

	public List<MountClientData> getMountDescription() { return this.mountDescription; }
	public void setMountDescription(List<MountClientData> mountDescription) { this.mountDescription = mountDescription; };

	public ExchangeMountsPaddockAddMessage(){
	}

	public ExchangeMountsPaddockAddMessage(List<MountClientData> mountDescription){
		this.mountDescription = mountDescription;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.mountDescription.size());
			int _loc2_ = 0;
			while( _loc2_ < this.mountDescription.size()){
				this.mountDescription.get(_loc2_).Serialize(writer);
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
			this.mountDescription = new ArrayList<MountClientData>();
			while( _loc3_ <  _loc2_){
				MountClientData _loc15_ = new MountClientData();
				_loc15_.Deserialize(reader);
				this.mountDescription.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
