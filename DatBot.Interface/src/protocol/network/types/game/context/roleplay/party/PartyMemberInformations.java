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
import protocol.network.types.game.context.roleplay.party.companion.PartyCompanionMemberInformations;

@SuppressWarnings("unused")
public class PartyMemberInformations extends CharacterBaseInformations {
	public static final int ProtocolId = 90;

	public int lifePoints;
	public int maxLifePoints;
	public int prospecting;
	public int regenRate;
	public int initiative;
	public int alignmentSide;
	public int worldX;
	public int worldY;
	public double mapId;
	public int subAreaId;
	public PlayerStatus status;
	public List<PartyCompanionMemberInformations> companions;

	public PartyMemberInformations(){
	}

	public PartyMemberInformations(int lifePoints, int maxLifePoints, int prospecting, int regenRate, int initiative, int alignmentSide, int worldX, int worldY, double mapId, int subAreaId, PlayerStatus status, List<PartyCompanionMemberInformations> companions){
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
			this.companions = new ArrayList<PartyCompanionMemberInformations>();
			while( _loc3_ <  _loc2_){
				PartyCompanionMemberInformations _loc15_ = new PartyCompanionMemberInformations();
				_loc15_.Deserialize(reader);
				this.companions.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("lifePoints : " + this.lifePoints);
		//Network.appendDebug("maxLifePoints : " + this.maxLifePoints);
		//Network.appendDebug("prospecting : " + this.prospecting);
		//Network.appendDebug("regenRate : " + this.regenRate);
		//Network.appendDebug("initiative : " + this.initiative);
		//Network.appendDebug("alignmentSide : " + this.alignmentSide);
		//Network.appendDebug("worldX : " + this.worldX);
		//Network.appendDebug("worldY : " + this.worldY);
		//Network.appendDebug("mapId : " + this.mapId);
		//Network.appendDebug("subAreaId : " + this.subAreaId);
		//Network.appendDebug("status : " + this.status);
		//for(PartyCompanionMemberInformations a : companions) {
			//Network.appendDebug("companions : " + a);
		//}
	//}
}
