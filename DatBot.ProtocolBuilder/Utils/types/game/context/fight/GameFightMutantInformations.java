package protocol.network.types.game.context.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.fight.GameFightFighterNamedInformations;

@SuppressWarnings("unused")
public class GameFightMutantInformations extends GameFightFighterNamedInformations {
	public static final int ProtocolId = 50;

	private int powerLevel;

	public int getPowerLevel() { return this.powerLevel; }
	public void setPowerLevel(int powerLevel) { this.powerLevel = powerLevel; };

	public GameFightMutantInformations(){
	}

	public GameFightMutantInformations(int powerLevel){
		this.powerLevel = powerLevel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.powerLevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.powerLevel = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
