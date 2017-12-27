package protocol.network.messages.game.dare;

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
import protocol.network.types.game.dare.DareInformations;

@SuppressWarnings("unused")
public class DareListMessage extends NetworkMessage {
	public static final int ProtocolId = 6661;

	private List<DareInformations> dares;

	public List<DareInformations> getDares() { return this.dares; };
	public void setDares(List<DareInformations> dares) { this.dares = dares; };

	public DareListMessage(){
	}

	public DareListMessage(List<DareInformations> dares){
		this.dares = dares;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.dares.size());
			int _loc2_ = 0;
			while( _loc2_ < this.dares.size()){
				this.dares.get(_loc2_).Serialize(writer);
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
			this.dares = new ArrayList<DareInformations>();
			while( _loc3_ <  _loc2_){
				DareInformations _loc15_ = new DareInformations();
				_loc15_.Deserialize(reader);
				this.dares.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
