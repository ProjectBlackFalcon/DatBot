package protocol.network.types.version;

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
public class Version extends NetworkMessage {
	public static final int ProtocolId = 11;

	public int major;
	public int minor;
	public int release;
	public int revision;
	public int patch;
	public int buildType;

	public Version(){
	}

	public Version(int major, int minor, int release, int revision, int patch, int buildType){
		this.major = major;
		this.minor = minor;
		this.release = release;
		this.revision = revision;
		this.patch = patch;
		this.buildType = buildType;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.major);
			writer.writeByte(this.minor);
			writer.writeByte(this.release);
			writer.writeInt(this.revision);
			writer.writeByte(this.patch);
			writer.writeByte(this.buildType);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.major = reader.readByte();
			this.minor = reader.readByte();
			this.release = reader.readByte();
			this.revision = reader.readInt();
			this.patch = reader.readByte();
			this.buildType = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("major : " + this.major);
		//Network.appendDebug("minor : " + this.minor);
		//Network.appendDebug("release : " + this.release);
		//Network.appendDebug("revision : " + this.revision);
		//Network.appendDebug("patch : " + this.patch);
		//Network.appendDebug("buildType : " + this.buildType);
	//}
}
