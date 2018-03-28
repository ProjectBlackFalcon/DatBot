package protocol.network.messages.game.alliance;

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
import protocol.network.types.game.social.AllianceFactSheetInformations;
import protocol.network.types.game.context.roleplay.GuildInAllianceInformations;

@SuppressWarnings("unused")
public class AllianceFactsMessage extends NetworkMessage {
	public static final int ProtocolId = 6414;

	private AllianceFactSheetInformations infos;
	private List<GuildInAllianceInformations> guilds;
	private List<Integer> controlledSubareaIds;
	private long leaderCharacterId;
	private String leaderCharacterName;

	public AllianceFactSheetInformations getInfos() { return this.infos; }
	public void setInfos(AllianceFactSheetInformations infos) { this.infos = infos; };
	public List<GuildInAllianceInformations> getGuilds() { return this.guilds; }
	public void setGuilds(List<GuildInAllianceInformations> guilds) { this.guilds = guilds; };
	public List<Integer> getControlledSubareaIds() { return this.controlledSubareaIds; }
	public void setControlledSubareaIds(List<Integer> controlledSubareaIds) { this.controlledSubareaIds = controlledSubareaIds; };
	public long getLeaderCharacterId() { return this.leaderCharacterId; }
	public void setLeaderCharacterId(long leaderCharacterId) { this.leaderCharacterId = leaderCharacterId; };
	public String getLeaderCharacterName() { return this.leaderCharacterName; }
	public void setLeaderCharacterName(String leaderCharacterName) { this.leaderCharacterName = leaderCharacterName; };

	public AllianceFactsMessage(){
	}

	public AllianceFactsMessage(AllianceFactSheetInformations infos, List<GuildInAllianceInformations> guilds, List<Integer> controlledSubareaIds, long leaderCharacterId, String leaderCharacterName){
		this.infos = infos;
		this.guilds = guilds;
		this.controlledSubareaIds = controlledSubareaIds;
		this.leaderCharacterId = leaderCharacterId;
		this.leaderCharacterName = leaderCharacterName;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(AllianceFactSheetInformations.ProtocolId);
			writer.writeShort(this.guilds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.guilds.size()){
				this.guilds.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.controlledSubareaIds.size());
			int _loc3_ = 0;
			while( _loc3_ < this.controlledSubareaIds.size()){
				writer.writeVarShort(this.controlledSubareaIds.get(_loc3_));
				_loc3_++;
			}
			writer.writeVarLong(this.leaderCharacterId);
			writer.writeUTF(this.leaderCharacterName);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.infos = (AllianceFactSheetInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.infos.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.guilds = new ArrayList<GuildInAllianceInformations>();
			while( _loc3_ <  _loc2_){
				GuildInAllianceInformations _loc15_ = new GuildInAllianceInformations();
				_loc15_.Deserialize(reader);
				this.guilds.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.controlledSubareaIds = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarShort();
				this.controlledSubareaIds.add(_loc16_);
				_loc5_++;
			}
			this.leaderCharacterId = reader.readVarLong();
			this.leaderCharacterName = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
