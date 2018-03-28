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

	private boolean abandonnedPaddock;
	private int level;
	private long expLevelFloor;
	private long experience;
	private long expNextLevelFloor;
	private int creationDate;
	private int nbTotalMembers;
	private int nbConnectedMembers;

	public boolean isAbandonnedPaddock() { return this.abandonnedPaddock; }
	public void setAbandonnedPaddock(boolean abandonnedPaddock) { this.abandonnedPaddock = abandonnedPaddock; };
	public int getLevel() { return this.level; }
	public void setLevel(int level) { this.level = level; };
	public long getExpLevelFloor() { return this.expLevelFloor; }
	public void setExpLevelFloor(long expLevelFloor) { this.expLevelFloor = expLevelFloor; };
	public long getExperience() { return this.experience; }
	public void setExperience(long experience) { this.experience = experience; };
	public long getExpNextLevelFloor() { return this.expNextLevelFloor; }
	public void setExpNextLevelFloor(long expNextLevelFloor) { this.expNextLevelFloor = expNextLevelFloor; };
	public int getCreationDate() { return this.creationDate; }
	public void setCreationDate(int creationDate) { this.creationDate = creationDate; };
	public int getNbTotalMembers() { return this.nbTotalMembers; }
	public void setNbTotalMembers(int nbTotalMembers) { this.nbTotalMembers = nbTotalMembers; };
	public int getNbConnectedMembers() { return this.nbConnectedMembers; }
	public void setNbConnectedMembers(int nbConnectedMembers) { this.nbConnectedMembers = nbConnectedMembers; };

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
	}

}
