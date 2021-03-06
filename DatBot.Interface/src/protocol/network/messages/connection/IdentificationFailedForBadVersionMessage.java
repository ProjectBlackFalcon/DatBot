package protocol.network.messages.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.connection.IdentificationFailedMessage;
import protocol.network.types.version.Version;

@SuppressWarnings("unused")
public class IdentificationFailedForBadVersionMessage extends IdentificationFailedMessage {
	public static final int ProtocolId = 21;

	private Version requiredVersion;

	public Version getRequiredVersion() { return this.requiredVersion; }
	public void setRequiredVersion(Version requiredVersion) { this.requiredVersion = requiredVersion; };

	public IdentificationFailedForBadVersionMessage(){
	}

	public IdentificationFailedForBadVersionMessage(Version requiredVersion){
		this.requiredVersion = requiredVersion;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			requiredVersion.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.requiredVersion = new Version();
			this.requiredVersion.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
