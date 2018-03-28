package protocol.network.messages.game.context.roleplay.npc;

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
public class EntityTalkMessage extends NetworkMessage {
	public static final int ProtocolId = 6110;

	private double entityId;
	private int textId;
	private List<String> parameters;

	public double getEntityId() { return this.entityId; }
	public void setEntityId(double entityId) { this.entityId = entityId; };
	public int getTextId() { return this.textId; }
	public void setTextId(int textId) { this.textId = textId; };
	public List<String> getParameters() { return this.parameters; }
	public void setParameters(List<String> parameters) { this.parameters = parameters; };

	public EntityTalkMessage(){
	}

	public EntityTalkMessage(double entityId, int textId, List<String> parameters){
		this.entityId = entityId;
		this.textId = textId;
		this.parameters = parameters;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.entityId);
			writer.writeVarShort(this.textId);
			writer.writeShort(this.parameters.size());
			int _loc2_ = 0;
			while( _loc2_ < this.parameters.size()){
				writer.writeUTF(this.parameters.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.entityId = reader.readDouble();
			this.textId = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.parameters = new ArrayList<String>();
			while( _loc3_ <  _loc2_){
				String _loc15_ = reader.readUTF();
				this.parameters.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
