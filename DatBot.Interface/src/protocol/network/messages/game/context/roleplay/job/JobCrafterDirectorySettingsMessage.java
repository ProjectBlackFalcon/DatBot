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
import protocol.network.types.game.context.roleplay.job.JobCrafterDirectorySettings;

@SuppressWarnings("unused")
public class JobCrafterDirectorySettingsMessage extends NetworkMessage {
	public static final int ProtocolId = 5652;

	private List<JobCrafterDirectorySettings> craftersSettings;

	public List<JobCrafterDirectorySettings> getCraftersSettings() { return this.craftersSettings; }
	public void setCraftersSettings(List<JobCrafterDirectorySettings> craftersSettings) { this.craftersSettings = craftersSettings; };

	public JobCrafterDirectorySettingsMessage(){
	}

	public JobCrafterDirectorySettingsMessage(List<JobCrafterDirectorySettings> craftersSettings){
		this.craftersSettings = craftersSettings;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.craftersSettings.size());
			int _loc2_ = 0;
			while( _loc2_ < this.craftersSettings.size()){
				this.craftersSettings.get(_loc2_).Serialize(writer);
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
			this.craftersSettings = new ArrayList<JobCrafterDirectorySettings>();
			while( _loc3_ <  _loc2_){
				JobCrafterDirectorySettings _loc15_ = new JobCrafterDirectorySettings();
				_loc15_.Deserialize(reader);
				this.craftersSettings.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
