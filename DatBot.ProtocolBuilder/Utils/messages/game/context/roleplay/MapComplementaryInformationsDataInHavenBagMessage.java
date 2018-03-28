package protocol.network.messages.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.MapComplementaryInformationsDataMessage;
import protocol.network.types.game.character.CharacterMinimalInformations;

@SuppressWarnings("unused")
public class MapComplementaryInformationsDataInHavenBagMessage extends MapComplementaryInformationsDataMessage {
	public static final int ProtocolId = 6622;

	private CharacterMinimalInformations ownerInformations;
	private int theme;
	private int roomId;
	private int maxRoomId;

	public CharacterMinimalInformations getOwnerInformations() { return this.ownerInformations; }
	public void setOwnerInformations(CharacterMinimalInformations ownerInformations) { this.ownerInformations = ownerInformations; };
	public int getTheme() { return this.theme; }
	public void setTheme(int theme) { this.theme = theme; };
	public int getRoomId() { return this.roomId; }
	public void setRoomId(int roomId) { this.roomId = roomId; };
	public int getMaxRoomId() { return this.maxRoomId; }
	public void setMaxRoomId(int maxRoomId) { this.maxRoomId = maxRoomId; };

	public MapComplementaryInformationsDataInHavenBagMessage(){
	}

	public MapComplementaryInformationsDataInHavenBagMessage(CharacterMinimalInformations ownerInformations, int theme, int roomId, int maxRoomId){
		this.ownerInformations = ownerInformations;
		this.theme = theme;
		this.roomId = roomId;
		this.maxRoomId = maxRoomId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			ownerInformations.Serialize(writer);
			writer.writeByte(this.theme);
			writer.writeByte(this.roomId);
			writer.writeByte(this.maxRoomId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.ownerInformations = new CharacterMinimalInformations();
			this.ownerInformations.Deserialize(reader);
			this.theme = reader.readByte();
			this.roomId = reader.readByte();
			this.maxRoomId = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
