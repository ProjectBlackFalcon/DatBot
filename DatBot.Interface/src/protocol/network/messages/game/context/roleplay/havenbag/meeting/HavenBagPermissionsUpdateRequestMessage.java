package protocol.network.messages.game.context.roleplay.havenbag.meeting;

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
public class HavenBagPermissionsUpdateRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6714;

	private int permissions;

	public int getPermissions() { return this.permissions; }
	public void setPermissions(int permissions) { this.permissions = permissions; };

	public HavenBagPermissionsUpdateRequestMessage(){
	}

	public HavenBagPermissionsUpdateRequestMessage(int permissions){
		this.permissions = permissions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.permissions);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.permissions = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
