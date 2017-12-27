package protocol.network.messages.game.approach;

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
public class AccountCapabilitiesMessage extends NetworkMessage {
	public static final int ProtocolId = 6216;

	private int accountId;
	private boolean tutorialAvailable;
	private int breedsVisible;
	private int breedsAvailable;
	private int status;
	private boolean canCreateNewCharacter;
	private double unlimitedRestatEndDate;

	public int getAccountId() { return this.accountId; };
	public void setAccountId(int accountId) { this.accountId = accountId; };
	public boolean isTutorialAvailable() { return this.tutorialAvailable; };
	public void setTutorialAvailable(boolean tutorialAvailable) { this.tutorialAvailable = tutorialAvailable; };
	public int getBreedsVisible() { return this.breedsVisible; };
	public void setBreedsVisible(int breedsVisible) { this.breedsVisible = breedsVisible; };
	public int getBreedsAvailable() { return this.breedsAvailable; };
	public void setBreedsAvailable(int breedsAvailable) { this.breedsAvailable = breedsAvailable; };
	public int getStatus() { return this.status; };
	public void setStatus(int status) { this.status = status; };
	public boolean isCanCreateNewCharacter() { return this.canCreateNewCharacter; };
	public void setCanCreateNewCharacter(boolean canCreateNewCharacter) { this.canCreateNewCharacter = canCreateNewCharacter; };
	public double getUnlimitedRestatEndDate() { return this.unlimitedRestatEndDate; };
	public void setUnlimitedRestatEndDate(double unlimitedRestatEndDate) { this.unlimitedRestatEndDate = unlimitedRestatEndDate; };

	public AccountCapabilitiesMessage(){
	}

	public AccountCapabilitiesMessage(int accountId, boolean tutorialAvailable, int breedsVisible, int breedsAvailable, int status, boolean canCreateNewCharacter, double unlimitedRestatEndDate){
		this.accountId = accountId;
		this.tutorialAvailable = tutorialAvailable;
		this.breedsVisible = breedsVisible;
		this.breedsAvailable = breedsAvailable;
		this.status = status;
		this.canCreateNewCharacter = canCreateNewCharacter;
		this.unlimitedRestatEndDate = unlimitedRestatEndDate;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, tutorialAvailable);
			flag = BooleanByteWrapper.SetFlag(1, flag, canCreateNewCharacter);
			writer.writeByte(flag);
			writer.writeInt(this.accountId);
			writer.writeVarInt(this.breedsVisible);
			writer.writeVarInt(this.breedsAvailable);
			writer.writeByte(this.status);
			writer.writeDouble(this.unlimitedRestatEndDate);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.tutorialAvailable = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.canCreateNewCharacter = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.accountId = reader.readInt();
			this.breedsVisible = reader.readVarInt();
			this.breedsAvailable = reader.readVarInt();
			this.status = reader.readByte();
			this.unlimitedRestatEndDate = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
