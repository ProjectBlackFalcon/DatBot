package protocol.network.messages.game.actions.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.actions.fight.GameActionFightLifePointsLostMessage;

@SuppressWarnings("unused")
public class GameActionFightLifeAndShieldPointsLostMessage extends GameActionFightLifePointsLostMessage {
	public static final int ProtocolId = 6310;

	private int shieldLoss;

	public int getShieldLoss() { return this.shieldLoss; };
	public void setShieldLoss(int shieldLoss) { this.shieldLoss = shieldLoss; };

	public GameActionFightLifeAndShieldPointsLostMessage(){
	}

	public GameActionFightLifeAndShieldPointsLostMessage(int shieldLoss){
		this.shieldLoss = shieldLoss;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.shieldLoss);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.shieldLoss = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
