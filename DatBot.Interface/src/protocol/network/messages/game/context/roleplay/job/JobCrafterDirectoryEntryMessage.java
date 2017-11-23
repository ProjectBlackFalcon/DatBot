package protocol.network.messages.game.context.roleplay.job;

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
import protocol.network.types.game.context.roleplay.job.JobCrafterDirectoryEntryPlayerInfo;
import protocol.network.types.game.context.roleplay.job.JobCrafterDirectoryEntryJobInfo;
import protocol.network.types.game.look.EntityLook;

@SuppressWarnings("unused")
public class JobCrafterDirectoryEntryMessage extends NetworkMessage {
	public static final int ProtocolId = 6044;

	public JobCrafterDirectoryEntryPlayerInfo playerInfo;
	public List<JobCrafterDirectoryEntryJobInfo> jobInfoList;
	public EntityLook playerLook;

	public JobCrafterDirectoryEntryMessage(){
	}

	public JobCrafterDirectoryEntryMessage(JobCrafterDirectoryEntryPlayerInfo playerInfo, List<JobCrafterDirectoryEntryJobInfo> jobInfoList, EntityLook playerLook){
		this.playerInfo = playerInfo;
		this.jobInfoList = jobInfoList;
		this.playerLook = playerLook;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			playerInfo.Serialize(writer);
			writer.writeShort(this.jobInfoList.size());
			int _loc2_ = 0;
			while( _loc2_ < this.jobInfoList.size()){
				this.jobInfoList.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			playerLook.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.playerInfo = new JobCrafterDirectoryEntryPlayerInfo();
			this.playerInfo.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.jobInfoList = new ArrayList<JobCrafterDirectoryEntryJobInfo>();
			while( _loc3_ <  _loc2_){
				JobCrafterDirectoryEntryJobInfo _loc15_ = new JobCrafterDirectoryEntryJobInfo();
				_loc15_.Deserialize(reader);
				this.jobInfoList.add(_loc15_);
				_loc3_++;
			}
			this.playerLook = new EntityLook();
			this.playerLook.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("playerInfo : " + this.playerInfo);
		//for(JobCrafterDirectoryEntryJobInfo a : jobInfoList) {
			//Network.appendDebug("jobInfoList : " + a);
		//}
		//Network.appendDebug("playerLook : " + this.playerLook);
	//}
}
