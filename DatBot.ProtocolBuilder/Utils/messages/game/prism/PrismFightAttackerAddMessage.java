package protocol.network.messages.game.prism;

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
import protocol.network.types.game.character.CharacterMinimalPlusLookInformations;

@SuppressWarnings("unused")
public class PrismFightAttackerAddMessage extends NetworkMessage {
	public static final int ProtocolId = 5893;

	private int subAreaId;
	private int fightId;
	private CharacterMinimalPlusLookInformations attacker;

	public int getSubAreaId() { return this.subAreaId; };
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public int getFightId() { return this.fightId; };
	public void setFightId(int fightId) { this.fightId = fightId; };
	public CharacterMinimalPlusLookInformations getAttacker() { return this.attacker; };
	public void setAttacker(CharacterMinimalPlusLookInformations attacker) { this.attacker = attacker; };

	public PrismFightAttackerAddMessage(){
	}

	public PrismFightAttackerAddMessage(int subAreaId, int fightId, CharacterMinimalPlusLookInformations attacker){
		this.subAreaId = subAreaId;
		this.fightId = fightId;
		this.attacker = attacker;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.subAreaId);
			writer.writeVarShort(this.fightId);
			writer.writeShort(CharacterMinimalPlusLookInformations.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.subAreaId = reader.readVarShort();
			this.fightId = reader.readVarShort();
			this.attacker = (CharacterMinimalPlusLookInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.attacker.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
