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

	private String login;
	private String nickname;
	private int accountId;
	private int communityId;
	private boolean hasRights;
	private String secretQuestion;
	private double accountCreation;
	private double subscriptionElapsedDuration;
	private double subscriptionEndDate;
	private boolean wasAlreadyConnected;
	private int havenbagAvailableRoom;

	public String getLogin() { return this.login; };
	public void setLogin(String login) { this.login = login; };
	public String getNickname() { return this.nickname; };
	public void setNickname(String nickname) { this.nickname = nickname; };
	public int getAccountId() { return this.accountId; };
	public void setAccountId(int accountId) { this.accountId = accountId; };
	public int getCommunityId() { return this.communityId; };
	public void setCommunityId(int communityId) { this.communityId = communityId; };
	public boolean isHasRights() { return this.hasRights; };
	public void setHasRights(boolean hasRights) { this.hasRights = hasRights; };
	public String getSecretQuestion() { return this.secretQuestion; };
	public void setSecretQuestion(String secretQuestion) { this.secretQuestion = secretQuestion; };
	public double getAccountCreation() { return this.accountCreation; };
	public void setAccountCreation(double accountCreation) { this.accountCreation = accountCreation; };
	public double getSubscriptionElapsedDuration() { return this.subscriptionElapsedDuration; };
	public void setSubscriptionElapsedDuration(double subscriptionElapsedDuration) { this.subscriptionElapsedDuration = subscriptionElapsedDuration; };
	public double getSubscriptionEndDate() { return this.subscriptionEndDate; };
	public void setSubscriptionEndDate(double subscriptionEndDate) { this.subscriptionEndDate = subscriptionEndDate; };
	public boolean isWasAlreadyConnected() { return this.wasAlreadyConnected; };
	public void setWasAlreadyConnected(boolean wasAlreadyConnected) { this.wasAlreadyConnected = wasAlreadyConnected; };
	public int getHavenbagAvailableRoom() { return this.havenbagAvailableRoom; };
	public void setHavenbagAvailableRoom(int havenbagAvailableRoom) { this.havenbagAvailableRoom = havenbagAvailableRoom; };

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
	}

}
