package protocol.network.types.game.context.roleplay.quest;

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
public class QuestObjectiveInformations extends NetworkMessage {
	public static final int ProtocolId = 385;

	private int objectiveId;
	private boolean objectiveStatus;
	private List<String> dialogParams;

	public int getObjectiveId() { return this.objectiveId; }
	public void setObjectiveId(int objectiveId) { this.objectiveId = objectiveId; };
	public boolean isObjectiveStatus() { return this.objectiveStatus; }
	public void setObjectiveStatus(boolean objectiveStatus) { this.objectiveStatus = objectiveStatus; };
	public List<String> getDialogParams() { return this.dialogParams; }
	public void setDialogParams(List<String> dialogParams) { this.dialogParams = dialogParams; };

	public QuestObjectiveInformations(){
	}

	public QuestObjectiveInformations(int objectiveId, boolean objectiveStatus, List<String> dialogParams){
		this.objectiveId = objectiveId;
		this.objectiveStatus = objectiveStatus;
		this.dialogParams = dialogParams;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.objectiveId);
			writer.writeBoolean(this.objectiveStatus);
			writer.writeShort(this.dialogParams.size());
			int _loc2_ = 0;
			while( _loc2_ < this.dialogParams.size()){
				writer.writeUTF(this.dialogParams.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.objectiveId = reader.readVarShort();
			this.objectiveStatus = reader.readBoolean();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.dialogParams = new ArrayList<String>();
			while( _loc3_ <  _loc2_){
				String _loc15_ = reader.readUTF();
				this.dialogParams.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
