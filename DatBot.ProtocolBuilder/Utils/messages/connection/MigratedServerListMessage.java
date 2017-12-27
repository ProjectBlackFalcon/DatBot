package protocol.network.messages.connection;

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
public class MigratedServerListMessage extends NetworkMessage {
	public static final int ProtocolId = 6731;

	private List<Integer> migratedServerIds;

	public List<Integer> getMigratedServerIds() { return this.migratedServerIds; };
	public void setMigratedServerIds(List<Integer> migratedServerIds) { this.migratedServerIds = migratedServerIds; };

	public MigratedServerListMessage(){
	}

	public MigratedServerListMessage(List<Integer> migratedServerIds){
		this.migratedServerIds = migratedServerIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.migratedServerIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.migratedServerIds.size()){
				writer.writeVarShort(this.migratedServerIds.get(_loc2_));
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
			this.migratedServerIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.migratedServerIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
