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
import protocol.network.types.game.context.roleplay.party.companion.PartyCompanionBaseInformations;

@SuppressWarnings("unused")
public class PartyInvitationMemberInformations extends CharacterBaseInformations {
	public static final int ProtocolId = 376;

	private int worldX;
	private int worldY;
	private double mapId;
	private int subAreaId;
	private List<PartyCompanionBaseInformations> companions;

	public int getWorldX() { return this.worldX; }
	public void setWorldX(int worldX) { this.worldX = worldX; };
	public int getWorldY() { return this.worldY; }
	public void setWorldY(int worldY) { this.worldY = worldY; };
	public double getMapId() { return this.mapId; }
	public void setMapId(double mapId) { this.mapId = mapId; };
	public int getSubAreaId() { return this.subAreaId; }
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public List<PartyCompanionBaseInformations> getCompanions() { return this.companions; }
	public void setCompanions(List<PartyCompanionBaseInformations> companions) { this.companions = companions; };

	public PartyInvitationMemberInformations(){
	}

	public PartyInvitationMemberInformations(int worldX, int worldY, double mapId, int subAreaId, List<PartyCompanionBaseInformations> companions){
		this.worldX = worldX;
		this.worldY = worldY;
		this.mapId = mapId;
		this.subAreaId = subAreaId;
		this.companions = companions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeDouble(this.mapId);
			writer.writeVarShort(this.subAreaId);
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
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
			this.mapId = reader.readDouble();
			this.subAreaId = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.companions = new ArrayList<PartyCompanionBaseInformations>();
			while( _loc3_ <  _loc2_){
				PartyCompanionBaseInformations _loc15_ = new PartyCompanionBaseInformations();
				_loc15_.Deserialize(reader);
				this.companions.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
