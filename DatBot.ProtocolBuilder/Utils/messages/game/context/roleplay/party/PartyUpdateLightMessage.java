package protocol.network.messages.game.context.roleplay.party;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.party.AbstractPartyEventMessage;

@SuppressWarnings("unused")
public class PartyUpdateLightMessage extends AbstractPartyEventMessage {
	public static final int ProtocolId = 6054;

	private long id;
	private int lifePoints;
	private int maxLifePoints;
	private int prospecting;
	private int regenRate;

	public long getId() { return this.id; };
	public void setId(long id) { this.id = id; };
	public int getLifePoints() { return this.lifePoints; };
	public void setLifePoints(int lifePoints) { this.lifePoints = lifePoints; };
	public int getMaxLifePoints() { return this.maxLifePoints; };
	public void setMaxLifePoints(int maxLifePoints) { this.maxLifePoints = maxLifePoints; };
	public int getProspecting() { return this.prospecting; };
	public void setProspecting(int prospecting) { this.prospecting = prospecting; };
	public int getRegenRate() { return this.regenRate; };
	public void setRegenRate(int regenRate) { this.regenRate = regenRate; };

	public PartyUpdateLightMessage(){
	}

	public PartyUpdateLightMessage(long id, int lifePoints, int maxLifePoints, int prospecting, int regenRate){
		this.id = id;
		this.lifePoints = lifePoints;
		this.maxLifePoints = maxLifePoints;
		this.prospecting = prospecting;
		this.regenRate = regenRate;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.id);
			writer.writeVarInt(this.lifePoints);
			writer.writeVarInt(this.maxLifePoints);
			writer.writeVarShort(this.prospecting);
			writer.writeByte(this.regenRate);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.id = reader.readVarLong();
			this.lifePoints = reader.readVarInt();
			this.maxLifePoints = reader.readVarInt();
			this.prospecting = reader.readVarShort();
			this.regenRate = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
