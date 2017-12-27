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
import protocol.network.types.game.context.roleplay.job.JobCrafterDirectoryListEntry;

@SuppressWarnings("unused")
public class JobCrafterDirectoryListMessage extends NetworkMessage {
	public static final int ProtocolId = 6046;

	private List<JobCrafterDirectoryListEntry> listEntries;

	public List<JobCrafterDirectoryListEntry> getListEntries() { return this.listEntries; };
	public void setListEntries(List<JobCrafterDirectoryListEntry> listEntries) { this.listEntries = listEntries; };

	public JobCrafterDirectoryListMessage(){
	}

	public JobCrafterDirectoryListMessage(List<JobCrafterDirectoryListEntry> listEntries){
		this.listEntries = listEntries;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.listEntries.size());
			int _loc2_ = 0;
			while( _loc2_ < this.listEntries.size()){
				this.listEntries.get(_loc2_).Serialize(writer);
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
			this.listEntries = new ArrayList<JobCrafterDirectoryListEntry>();
			while( _loc3_ <  _loc2_){
				JobCrafterDirectoryListEntry _loc15_ = new JobCrafterDirectoryListEntry();
				_loc15_.Deserialize(reader);
				this.listEntries.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
