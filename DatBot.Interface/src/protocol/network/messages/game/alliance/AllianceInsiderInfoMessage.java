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
import protocol.network.types.game.social.GuildInsiderFactSheetInformations;
import protocol.network.types.game.prism.PrismSubareaEmptyInfo;

@SuppressWarnings("unused")
public class AllianceInsiderInfoMessage extends NetworkMessage {
	public static final int ProtocolId = 6403;

	private AllianceFactSheetInformations allianceInfos;
	private List<GuildInsiderFactSheetInformations> guilds;
	private List<PrismSubareaEmptyInfo> prisms;

	public AllianceFactSheetInformations getAllianceInfos() { return this.allianceInfos; }
	public void setAllianceInfos(AllianceFactSheetInformations allianceInfos) { this.allianceInfos = allianceInfos; };
	public List<GuildInsiderFactSheetInformations> getGuilds() { return this.guilds; }
	public void setGuilds(List<GuildInsiderFactSheetInformations> guilds) { this.guilds = guilds; };
	public List<PrismSubareaEmptyInfo> getPrisms() { return this.prisms; }
	public void setPrisms(List<PrismSubareaEmptyInfo> prisms) { this.prisms = prisms; };

	public AllianceInsiderInfoMessage(){
	}

	public AllianceInsiderInfoMessage(AllianceFactSheetInformations allianceInfos, List<GuildInsiderFactSheetInformations> guilds, List<PrismSubareaEmptyInfo> prisms){
		this.allianceInfos = allianceInfos;
		this.guilds = guilds;
		this.prisms = prisms;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			allianceInfos.Serialize(writer);
			writer.writeShort(this.guilds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.guilds.size()){
				this.guilds.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.prisms.size());
			int _loc3_ = 0;
			while( _loc3_ < this.prisms.size()){
				writer.writeShort(PrismSubareaEmptyInfo.ProtocolId);
				this.prisms.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.allianceInfos = new AllianceFactSheetInformations();
			this.allianceInfos.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.guilds = new ArrayList<GuildInsiderFactSheetInformations>();
			while( _loc3_ <  _loc2_){
				GuildInsiderFactSheetInformations _loc15_ = new GuildInsiderFactSheetInformations();
				_loc15_.Deserialize(reader);
				this.guilds.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.prisms = new ArrayList<PrismSubareaEmptyInfo>();
			while( _loc5_ <  _loc4_){
				PrismSubareaEmptyInfo _loc16_ = (PrismSubareaEmptyInfo) ProtocolTypeManager.getInstance(reader.readShort());
				_loc16_.Deserialize(reader);
				this.prisms.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
