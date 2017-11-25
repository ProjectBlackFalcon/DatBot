package protocol.network.messages.game.guild;

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
public class GuildInformationsGeneralMessage extends NetworkMessage {
	public static final int ProtocolId = 5557;

	public boolean abandonnedPaddock;
	public int level;
	public long expLevelFloor;
	public long experience;
	public long expNextLevelFloor;
	public int creationDate;
	public int nbTotalMembers;
	public int nbConnectedMembers;

	public GuildInformationsGeneralMessage(){
	}

	public GuildInformationsGeneralMessage(boolean abandonnedPaddock, int level, long expLevelFloor, long experience, long expNextLevelFloor, int creationDate, int nbTotalMembers, int nbConnectedMembers){
		this.abandonnedPaddock = abandonnedPaddock;
		this.level = level;
		this.expLevelFloor = expLevelFloor;
		this.experience = experience;
		this.expNextLevelFloor = expNextLevelFloor;
		this.creationDate = creationDate;
		this.nbTotalMembers = nbTotalMembers;
		this.nbConnectedMembers = nbConnectedMembers;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.abandonnedPaddock);
			writer.writeByte(this.level);
			writer.writeVarLong(this.expLevelFloor);
			writer.writeVarLong(this.experience);
			writer.writeVarLong(this.expNextLevelFloor);
			writer.writeInt(this.creationDate);
			writer.writeVarShort(this.nbTotalMembers);
			writer.writeVarShort(this.nbConnectedMembers);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.abandonnedPaddock = reader.readBoolean();
			this.level = reader.readByte();
			this.expLevelFloor = reader.readVarLong();
			this.experience = reader.readVarLong();
			this.expNextLevelFloor = reader.readVarLong();
			this.creationDate = reader.readInt();
			this.nbTotalMembers = reader.readVarShort();
			this.nbConnectedMembers = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("abandonnedPaddock : " + this.abandonnedPaddock);
		//Network.appendDebug("level : " + this.level);
		//Network.appendDebug("expLevelFloor : " + this.expLevelFloor);
		//Network.appendDebug("experience : " + this.experience);
		//Network.appendDebug("expNextLevelFloor : " + this.expNextLevelFloor);
		//Network.appendDebug("creationDate : " + this.creationDate);
		//Network.appendDebug("nbTotalMembers : " + this.nbTotalMembers);
		//Network.appendDebug("nbConnectedMembers : " + this.nbConnectedMembers);
	//}
}