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

	private int major;
	private int minor;
	private int release;
	private int revision;
	private int patch;
	private int buildType;

	public int getMajor() { return this.major; }
	public void setMajor(int major) { this.major = major; };
	public int getMinor() { return this.minor; }
	public void setMinor(int minor) { this.minor = minor; };
	public int getRelease() { return this.release; }
	public void setRelease(int release) { this.release = release; };
	public int getRevision() { return this.revision; }
	public void setRevision(int revision) { this.revision = revision; };
	public int getPatch() { return this.patch; }
	public void setPatch(int patch) { this.patch = patch; };
	public int getBuildType() { return this.buildType; }
	public void setBuildType(int buildType) { this.buildType = buildType; };

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
	}

}
