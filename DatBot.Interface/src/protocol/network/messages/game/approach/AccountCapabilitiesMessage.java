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

	public int accountId;
	public boolean tutorialAvailable;
	public int breedsVisible;
	public int breedsAvailable;
	public int status;
	public boolean canCreateNewCharacter;

	public AccountCapabilitiesMessage(){
	}

	public AccountCapabilitiesMessage(int accountId, boolean tutorialAvailable, int breedsVisible, int breedsAvailable, int status, boolean canCreateNewCharacter){
		this.accountId = accountId;
		this.tutorialAvailable = tutorialAvailable;
		this.breedsVisible = breedsVisible;
		this.breedsAvailable = breedsAvailable;
		this.status = status;
		this.canCreateNewCharacter = canCreateNewCharacter;
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
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("accountId : " + this.accountId);
		//Network.appendDebug("tutorialAvailable : " + this.tutorialAvailable);
		//Network.appendDebug("breedsVisible : " + this.breedsVisible);
		//Network.appendDebug("breedsAvailable : " + this.breedsAvailable);
		//Network.appendDebug("status : " + this.status);
		//Network.appendDebug("canCreateNewCharacter : " + this.canCreateNewCharacter);
	//}
}
