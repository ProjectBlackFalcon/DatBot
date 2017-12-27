package protocol.network.types.version;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.types.version.Version;

@SuppressWarnings("unused")
public class VersionExtended extends Version {
	public static final int ProtocolId = 393;

	private int install;
	private int technology;
	
	public int getInstall(){ return this.install; };
	public int getTechnology(){ return this.technology; };

	public VersionExtended(){
	}

	public VersionExtended(int major, int minor, int release, int revision, int patch, int buildType, int install, int technology){
		super(major, minor, release, revision, patch, buildType);
		this.install = install;
		this.technology = technology;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.install);
			writer.writeByte(this.technology);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.install = reader.readByte();
			this.technology = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
