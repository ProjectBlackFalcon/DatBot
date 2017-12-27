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
public class PrismFightDefenderAddMessage extends NetworkMessage {
	public static final int ProtocolId = 5895;

	private int subAreaId;
	private int fightId;
	private CharacterMinimalPlusLookInformations defender;

	public int getSubAreaId() { return this.subAreaId; };
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public int getFightId() { return this.fightId; };
	public void setFightId(int fightId) { this.fightId = fightId; };
	public CharacterMinimalPlusLookInformations getDefender() { return this.defender; };
	public void setDefender(CharacterMinimalPlusLookInformations defender) { this.defender = defender; };

	public PrismFightDefenderAddMessage(){
	}

	public PrismFightDefenderAddMessage(int subAreaId, int fightId, CharacterMinimalPlusLookInformations defender){
		this.subAreaId = subAreaId;
		this.fightId = fightId;
		this.defender = defender;
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
			this.defender = (CharacterMinimalPlusLookInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.defender.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
