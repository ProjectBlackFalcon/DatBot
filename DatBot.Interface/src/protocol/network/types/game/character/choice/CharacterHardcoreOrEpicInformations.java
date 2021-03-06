package protocol.network.types.game.character.choice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.character.choice.CharacterBaseInformations;

@SuppressWarnings("unused")
public class CharacterHardcoreOrEpicInformations extends CharacterBaseInformations {
	public static final int ProtocolId = 474;

	private int deathState;
	private int deathCount;
	private int deathMaxLevel;

	public int getDeathState() { return this.deathState; }
	public void setDeathState(int deathState) { this.deathState = deathState; };
	public int getDeathCount() { return this.deathCount; }
	public void setDeathCount(int deathCount) { this.deathCount = deathCount; };
	public int getDeathMaxLevel() { return this.deathMaxLevel; }
	public void setDeathMaxLevel(int deathMaxLevel) { this.deathMaxLevel = deathMaxLevel; };

	public CharacterHardcoreOrEpicInformations(){
	}

	public CharacterHardcoreOrEpicInformations(int deathState, int deathCount, int deathMaxLevel){
		this.deathState = deathState;
		this.deathCount = deathCount;
		this.deathMaxLevel = deathMaxLevel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.deathState);
			writer.writeVarShort(this.deathCount);
			writer.writeVarShort(this.deathMaxLevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.deathState = reader.readByte();
			this.deathCount = reader.readVarShort();
			this.deathMaxLevel = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
