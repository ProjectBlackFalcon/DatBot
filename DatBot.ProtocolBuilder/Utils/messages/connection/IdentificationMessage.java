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
import protocol.network.NetworkMessage;
import protocol.network.types.version.VersionExtended;

@SuppressWarnings("unused")
public class IdentificationMessage extends NetworkMessage {
	public static final int ProtocolId = 4;

	private VersionExtended version;
	private String lang;
	private List<Integer> credentials;
	private int serverId;
	private boolean autoconnect;
	private boolean useCertificate;
	private boolean useLoginToken;
	private long sessionOptionalSalt;
	private List<Integer> failedAttempts;

	public VersionExtended getVersion() { return this.version; };
	public void setVersion(VersionExtended version) { this.version = version; };
	public String getLang() { return this.lang; };
	public void setLang(String lang) { this.lang = lang; };
	public List<Integer> getCredentials() { return this.credentials; };
	public void setCredentials(List<Integer> credentials) { this.credentials = credentials; };
	public int getServerId() { return this.serverId; };
	public void setServerId(int serverId) { this.serverId = serverId; };
	public boolean isAutoconnect() { return this.autoconnect; };
	public void setAutoconnect(boolean autoconnect) { this.autoconnect = autoconnect; };
	public boolean isUseCertificate() { return this.useCertificate; };
	public void setUseCertificate(boolean useCertificate) { this.useCertificate = useCertificate; };
	public boolean isUseLoginToken() { return this.useLoginToken; };
	public void setUseLoginToken(boolean useLoginToken) { this.useLoginToken = useLoginToken; };
	public long getSessionOptionalSalt() { return this.sessionOptionalSalt; };
	public void setSessionOptionalSalt(long sessionOptionalSalt) { this.sessionOptionalSalt = sessionOptionalSalt; };
	public List<Integer> getFailedAttempts() { return this.failedAttempts; };
	public void setFailedAttempts(List<Integer> failedAttempts) { this.failedAttempts = failedAttempts; };

	public IdentificationMessage(){
	}

	public IdentificationMessage(VersionExtended version, String lang, List<Integer> credentials, int serverId, boolean autoconnect, boolean useCertificate, boolean useLoginToken, long sessionOptionalSalt, List<Integer> failedAttempts){
		this.version = version;
		this.lang = lang;
		this.credentials = credentials;
		this.serverId = serverId;
		this.autoconnect = autoconnect;
		this.useCertificate = useCertificate;
		this.useLoginToken = useLoginToken;
		this.sessionOptionalSalt = sessionOptionalSalt;
		this.failedAttempts = failedAttempts;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, autoconnect);
			flag = BooleanByteWrapper.SetFlag(1, flag, useCertificate);
			flag = BooleanByteWrapper.SetFlag(2, flag, useLoginToken);
			writer.writeByte(flag);
			version.Serialize(writer);
			writer.writeUTF(this.lang);
			writer.writeVarInt(this.credentials.size());
			int _loc2_ = 0;
			while( _loc2_ < this.credentials.size()){
				writer.writeByte(this.credentials.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.serverId);
			writer.writeVarLong(this.sessionOptionalSalt);
			writer.writeShort(this.failedAttempts.size());
			int _loc3_ = 0;
			while( _loc3_ < this.failedAttempts.size()){
				writer.writeVarShort(this.failedAttempts.get(_loc3_));
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.autoconnect = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.useCertificate = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.useLoginToken = BooleanByteWrapper.GetFlag(flag, (byte) 2);
			this.version = new VersionExtended();
			this.version.Deserialize(reader);
			this.lang = reader.readUTF();
			int _loc2_  = reader.readVarInt();
			int _loc3_  = 0;
			this.credentials = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readByte();
				this.credentials.add(_loc15_);
				_loc3_++;
			}
			this.serverId = reader.readShort();
			this.sessionOptionalSalt = reader.readVarLong();
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.failedAttempts = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarShort();
				this.failedAttempts.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
