package protocol.network.messages.game.approach;

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
import protocol.network.types.game.approach.ServerSessionConstant;

@SuppressWarnings("unused")
public class ServerSessionConstantsMessage extends NetworkMessage {
	public static final int ProtocolId = 6434;

	private List<ServerSessionConstant> variables;

	public List<ServerSessionConstant> getVariables() { return this.variables; };
	public void setVariables(List<ServerSessionConstant> variables) { this.variables = variables; };

	public ServerSessionConstantsMessage(){
	}

	public ServerSessionConstantsMessage(List<ServerSessionConstant> variables){
		this.variables = variables;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.variables.size());
			int _loc2_ = 0;
			while( _loc2_ < this.variables.size()){
				writer.writeShort(ServerSessionConstant.ProtocolId);
				this.variables.get(_loc2_).Serialize(writer);
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
			this.variables = new ArrayList<ServerSessionConstant>();
			while( _loc3_ <  _loc2_){
				ServerSessionConstant _loc15_ = (ServerSessionConstant) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.variables.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
