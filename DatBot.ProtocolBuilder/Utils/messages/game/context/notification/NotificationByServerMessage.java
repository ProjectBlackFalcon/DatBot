package protocol.network.messages.game.context.notification;

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
public class NotificationByServerMessage extends NetworkMessage {
	public static final int ProtocolId = 6103;

	private int id;
	private List<String> parameters;
	private boolean forceOpen;

	public int getId() { return this.id; };
	public void setId(int id) { this.id = id; };
	public List<String> getParameters() { return this.parameters; };
	public void setParameters(List<String> parameters) { this.parameters = parameters; };
	public boolean isForceOpen() { return this.forceOpen; };
	public void setForceOpen(boolean forceOpen) { this.forceOpen = forceOpen; };

	public NotificationByServerMessage(){
	}

	public NotificationByServerMessage(int id, List<String> parameters, boolean forceOpen){
		this.id = id;
		this.parameters = parameters;
		this.forceOpen = forceOpen;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.id);
			writer.writeShort(this.parameters.size());
			int _loc2_ = 0;
			while( _loc2_ < this.parameters.size()){
				writer.writeUTF(this.parameters.get(_loc2_));
				_loc2_++;
			}
			writer.writeBoolean(this.forceOpen);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.parameters = new ArrayList<String>();
			while( _loc3_ <  _loc2_){
				String _loc15_ = reader.readUTF();
				this.parameters.add(_loc15_);
				_loc3_++;
			}
			this.forceOpen = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
