package protocol.network.messages.game.context.mount;

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
import protocol.network.types.game.mount.MountClientData;

@SuppressWarnings("unused")
public class MountSetMessage extends NetworkMessage {
	public static final int ProtocolId = 5968;

	private MountClientData mountData;

	public MountClientData getMountData() { return this.mountData; };
	public void setMountData(MountClientData mountData) { this.mountData = mountData; };

	public MountSetMessage(){
	}

	public MountSetMessage(MountClientData mountData){
		this.mountData = mountData;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			mountData.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.mountData = new MountClientData();
			this.mountData.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
