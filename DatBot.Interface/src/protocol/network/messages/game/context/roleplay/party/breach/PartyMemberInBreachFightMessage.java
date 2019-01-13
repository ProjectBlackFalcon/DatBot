package protocol.network.messages.game.context.roleplay.party.breach;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.party.AbstractPartyMemberInFightMessage;

@SuppressWarnings("unused")
public class PartyMemberInBreachFightMessage extends AbstractPartyMemberInFightMessage {
	public static final int ProtocolId = 6824;

	private int floor;
	private int room;

	public int getFloor() { return this.floor; }
	public void setFloor(int floor) { this.floor = floor; };
	public int getRoom() { return this.room; }
	public void setRoom(int room) { this.room = room; };

	public PartyMemberInBreachFightMessage(){
	}

	public PartyMemberInBreachFightMessage(int floor, int room){
		this.floor = floor;
		this.room = room;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.floor);
			writer.writeByte(this.room);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.floor = reader.readVarInt();
			this.room = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
