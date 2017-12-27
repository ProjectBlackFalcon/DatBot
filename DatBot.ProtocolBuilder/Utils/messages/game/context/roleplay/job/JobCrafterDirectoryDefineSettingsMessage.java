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
public class JobCrafterDirectoryDefineSettingsMessage extends NetworkMessage {
	public static final int ProtocolId = 5649;

	private JobCrafterDirectorySettings settings;

	public JobCrafterDirectorySettings getSettings() { return this.settings; };
	public void setSettings(JobCrafterDirectorySettings settings) { this.settings = settings; };

	public JobCrafterDirectoryDefineSettingsMessage(){
	}

	public JobCrafterDirectoryDefineSettingsMessage(JobCrafterDirectorySettings settings){
		this.settings = settings;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			settings.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.settings = new JobCrafterDirectorySettings();
			this.settings.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
