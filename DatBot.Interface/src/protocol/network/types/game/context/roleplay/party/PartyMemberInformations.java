package protocol.network.types.game.context.roleplay.party;

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
import protocol.network.types.game.character.status.PlayerStatus;
import protocol.network.types.game.context.roleplay.party.companion.PartyCompanionBaseInformations;

@SuppressWarnings("unused")
public class PartyMemberInformations extends CharacterBaseInformations {
	public static final int ProtocolId = 90;

	private int lifePoints;
	private int maxLifePoints;
	private int prospecting;
	private int regenRate;
	private int initiative;
	private int alignmentSide;
	private int worldX;
	private int worldY;
	private double mapId;
	private int subAreaId;
	private PlayerStatus status;
	private List<PartyCompanionBaseInformations> companions;

	public int getLifePoints() { return this.lifePoints; }
	public void setLifePoints(int lifePoints) { this.lifePoints = lifePoints; };
	public int getMaxLifePoints() { return this.maxLifePoints; }
	public void setMaxLifePoints(int maxLifePoints) { this.maxLifePoints = maxLifePoints; };
	public int getProspecting() { return this.prospecting; }
	public void setProspecting(int prospecting) { this.prospecting = prospecting; };
	public int getRegenRate() { return this.regenRate; }
	public void setRegenRate(int regenRate) { this.regenRate = regenRate; };
	public int getInitiative() { return this.initiative; }
	public void setInitiative(int initiative) { this.initiative = initiative; };
	public int getAlignmentSide() { return this.alignmentSide; }
	public void setAlignmentSide(int alignmentSide) { this.alignmentSide = alignmentSide; };
	public int getWorldX() { return this.worldX; }
	public void setWorldX(int worldX) { this.worldX = worldX; };
	public int getWorldY() { return this.worldY; }
	public void setWorldY(int worldY) { this.worldY = worldY; };
	public double getMapId() { return this.mapId; }
	public void setMapId(double mapId) { this.mapId = mapId; };
	public int getSubAreaId() { return this.subAreaId; }
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public PlayerStatus getStatus() { return this.status; }
	public void setStatus(PlayerStatus status) { this.status = status; };
	public List<PartyCompanionBaseInformations> getCompanions() { return this.companions; }
	public void setCompanions(List<PartyCompanionBaseInformations> companions) { this.companions = companions; };

	public PartyMemberInformations(){
	}

	public PartyMemberInformations(int lifePoints, int maxLifePoints, int prospecting, int regenRate, int initiative, int alignmentSide, int worldX, int worldY, double mapId, int subAreaId, PlayerStatus status, List<PartyCompanionBaseInformations> companions){
		this.lifePoints = lifePoints;
		this.maxLifePoints = maxLifePoints;
		this.prospecting = prospecting;
		this.regenRate = regenRate;
		this.initiative = initiative;
		this.alignmentSide = alignmentSide;
		this.worldX = worldX;
		this.worldY = worldY;
		this.mapId = mapId;
		this.subAreaId = subAreaId;
		this.status = status;
		this.companions = companions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.lifePoints);
			writer.writeVarInt(this.maxLifePoints);
			writer.writeVarShort(this.prospecting);
			writer.writeByte(this.regenRate);
			writer.writeVarShort(this.initiative);
			writer.writeByte(this.alignmentSide);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeDouble(this.mapId);
			writer.writeVarShort(this.subAreaId);
			writer.writeShort(PlayerStatus.ProtocolId);
			writer.writeShort(this.companions.size());
			int _loc2_ = 0;
			while( _loc2_ < this.companions.size()){
				writer.writeShort(PartyCompanionBaseInformations.ProtocolId);
				this.companions.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.lifePoints = reader.readVarInt();
			this.maxLifePoints = reader.readVarInt();
			this.prospecting = reader.readVarShort();
			this.regenRate = reader.readByte();
			this.initiative = reader.readVarShort();
			this.alignmentSide = reader.readByte();
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
			this.mapId = reader.readDouble();
			this.subAreaId = reader.readVarShort();
			this.status = (PlayerStatus) ProtocolTypeManager.getInstance(reader.readShort());
			this.status.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.companions = new ArrayList<PartyCompanionBaseInformations>();
			while( _loc3_ <  _loc2_){
				PartyCompanionBaseInformations _loc15_ = (PartyCompanionBaseInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.companions.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
