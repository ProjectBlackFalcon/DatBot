package protocol.network.messages.game.character.stats;

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
public class UpdateLifePointsMessage extends NetworkMessage {
	public static final int ProtocolId = 5658;

	private int lifePoints;
	private int maxLifePoints;

	public int getLifePoints() { return this.lifePoints; }
	public void setLifePoints(int lifePoints) { this.lifePoints = lifePoints; };
	public int getMaxLifePoints() { return this.maxLifePoints; }
	public void setMaxLifePoints(int maxLifePoints) { this.maxLifePoints = maxLifePoints; };

	public UpdateLifePointsMessage(){
	}

	public UpdateLifePointsMessage(int lifePoints, int maxLifePoints){
		this.lifePoints = lifePoints;
		this.maxLifePoints = maxLifePoints;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.lifePoints);
			writer.writeVarInt(this.maxLifePoints);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.lifePoints = reader.readVarInt();
			this.maxLifePoints = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
