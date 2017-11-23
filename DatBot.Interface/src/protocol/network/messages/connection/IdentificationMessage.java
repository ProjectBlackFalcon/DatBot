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

	public VersionExtended version;
	public String lang;
	public List<Integer> credentials;
	public int serverId;
	public boolean autoconnect;
	public boolean useCertificate;
	public boolean useLoginToken;
	public long sessionOptionalSalt;
	public List<Integer> failedAttempts;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("version : " + this.version);
		//Network.appendDebug("lang : " + this.lang);
		//for(Integer a : credentials) {
			//Network.appendDebug("credentials : " + a);
		//}
		//Network.appendDebug("serverId : " + this.serverId);
		//Network.appendDebug("autoconnect : " + this.autoconnect);
		//Network.appendDebug("useCertificate : " + this.useCertificate);
		//Network.appendDebug("useLoginToken : " + this.useLoginToken);
		//Network.appendDebug("sessionOptionalSalt : " + this.sessionOptionalSalt);
		//for(Integer a : failedAttempts) {
			//Network.appendDebug("failedAttempts : " + a);
		//}
	//}
}
