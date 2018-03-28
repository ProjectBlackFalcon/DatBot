package protocol.network.messages.game.guild;

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
import protocol.network.types.game.social.GuildFactSheetInformations;
import protocol.network.types.game.character.CharacterMinimalInformations;

@SuppressWarnings("unused")
public class GuildFactsMessage extends NetworkMessage {
	public static final int ProtocolId = 6415;

	private GuildFactSheetInformations infos;
	private int creationDate;
	private int nbTaxCollectors;
	private List<CharacterMinimalInformations> members;

	public GuildFactSheetInformations getInfos() { return this.infos; }
	public void setInfos(GuildFactSheetInformations infos) { this.infos = infos; };
	public int getCreationDate() { return this.creationDate; }
	public void setCreationDate(int creationDate) { this.creationDate = creationDate; };
	public int getNbTaxCollectors() { return this.nbTaxCollectors; }
	public void setNbTaxCollectors(int nbTaxCollectors) { this.nbTaxCollectors = nbTaxCollectors; };
	public List<CharacterMinimalInformations> getMembers() { return this.members; }
	public void setMembers(List<CharacterMinimalInformations> members) { this.members = members; };

	public GuildFactsMessage(){
	}

	public GuildFactsMessage(GuildFactSheetInformations infos, int creationDate, int nbTaxCollectors, List<CharacterMinimalInformations> members){
		this.infos = infos;
		this.creationDate = creationDate;
		this.nbTaxCollectors = nbTaxCollectors;
		this.members = members;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(GuildFactSheetInformations.ProtocolId);
			writer.writeInt(this.creationDate);
			writer.writeVarShort(this.nbTaxCollectors);
			writer.writeShort(this.members.size());
			int _loc2_ = 0;
			while( _loc2_ < this.members.size()){
				this.members.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.infos = (GuildFactSheetInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.infos.Deserialize(reader);
			this.creationDate = reader.readInt();
			this.nbTaxCollectors = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.members = new ArrayList<CharacterMinimalInformations>();
			while( _loc3_ <  _loc2_){
				CharacterMinimalInformations _loc15_ = new CharacterMinimalInformations();
				_loc15_.Deserialize(reader);
				this.members.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
