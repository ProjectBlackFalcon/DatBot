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

@SuppressWarnings("unused")
public class IdentificationSuccessMessage extends NetworkMessage {
	public static final int ProtocolId = 22;

	public String login;
	public String nickname;
	public int accountId;
	public int communityId;
	public boolean hasRights;
	public String secretQuestion;
	public double accountCreation;
	public double subscriptionElapsedDuration;
	public double subscriptionEndDate;
	public boolean wasAlreadyConnected;
	public int havenbagAvailableRoom;

	public IdentificationSuccessMessage(){
	}

	public IdentificationSuccessMessage(String login, String nickname, int accountId, int communityId, boolean hasRights, String secretQuestion, double accountCreation, double subscriptionElapsedDuration, double subscriptionEndDate, boolean wasAlreadyConnected, int havenbagAvailableRoom){
		this.login = login;
		this.nickname = nickname;
		this.accountId = accountId;
		this.communityId = communityId;
		this.hasRights = hasRights;
		this.secretQuestion = secretQuestion;
		this.accountCreation = accountCreation;
		this.subscriptionElapsedDuration = subscriptionElapsedDuration;
		this.subscriptionEndDate = subscriptionEndDate;
		this.wasAlreadyConnected = wasAlreadyConnected;
		this.havenbagAvailableRoom = havenbagAvailableRoom;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, hasRights);
			flag = BooleanByteWrapper.SetFlag(1, flag, wasAlreadyConnected);
			writer.writeByte(flag);
			writer.writeUTF(this.login);
			writer.writeUTF(this.nickname);
			writer.writeInt(this.accountId);
			writer.writeByte(this.communityId);
			writer.writeUTF(this.secretQuestion);
			writer.writeDouble(this.accountCreation);
			writer.writeDouble(this.subscriptionElapsedDuration);
			writer.writeDouble(this.subscriptionEndDate);
			writer.writeByte(this.havenbagAvailableRoom);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.hasRights = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.wasAlreadyConnected = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.login = reader.readUTF();
			this.nickname = reader.readUTF();
			this.accountId = reader.readInt();
			this.communityId = reader.readByte();
			this.secretQuestion = reader.readUTF();
			this.accountCreation = reader.readDouble();
			this.subscriptionElapsedDuration = reader.readDouble();
			this.subscriptionEndDate = reader.readDouble();
			this.havenbagAvailableRoom = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("login : " + this.login);
		//Network.appendDebug("nickname : " + this.nickname);
		//Network.appendDebug("accountId : " + this.accountId);
		//Network.appendDebug("communityId : " + this.communityId);
		//Network.appendDebug("hasRights : " + this.hasRights);
		//Network.appendDebug("secretQuestion : " + this.secretQuestion);
		//Network.appendDebug("accountCreation : " + this.accountCreation);
		//Network.appendDebug("subscriptionElapsedDuration : " + this.subscriptionElapsedDuration);
		//Network.appendDebug("subscriptionEndDate : " + this.subscriptionEndDate);
		//Network.appendDebug("wasAlreadyConnected : " + this.wasAlreadyConnected);
		//Network.appendDebug("havenbagAvailableRoom : " + this.havenbagAvailableRoom);
	//}
}
