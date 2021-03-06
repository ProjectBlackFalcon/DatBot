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
public class NpcDialogQuestionMessage extends NetworkMessage {
	public static final int ProtocolId = 5617;

	private int messageId;
	private List<String> dialogParams;
	private List<Integer> visibleReplies;

	public int getMessageId() { return this.messageId; }
	public void setMessageId(int messageId) { this.messageId = messageId; };
	public List<String> getDialogParams() { return this.dialogParams; }
	public void setDialogParams(List<String> dialogParams) { this.dialogParams = dialogParams; };
	public List<Integer> getVisibleReplies() { return this.visibleReplies; }
	public void setVisibleReplies(List<Integer> visibleReplies) { this.visibleReplies = visibleReplies; };

	public NpcDialogQuestionMessage(){
	}

	public NpcDialogQuestionMessage(int messageId, List<String> dialogParams, List<Integer> visibleReplies){
		this.messageId = messageId;
		this.dialogParams = dialogParams;
		this.visibleReplies = visibleReplies;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.messageId);
			writer.writeShort(this.dialogParams.size());
			int _loc2_ = 0;
			while( _loc2_ < this.dialogParams.size()){
				writer.writeUTF(this.dialogParams.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.visibleReplies.size());
			int _loc3_ = 0;
			while( _loc3_ < this.visibleReplies.size()){
				writer.writeVarInt(this.visibleReplies.get(_loc3_));
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.messageId = reader.readVarInt();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.dialogParams = new ArrayList<String>();
			while( _loc3_ <  _loc2_){
				String _loc15_ = reader.readUTF();
				this.dialogParams.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.visibleReplies = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarInt();
				this.visibleReplies.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
