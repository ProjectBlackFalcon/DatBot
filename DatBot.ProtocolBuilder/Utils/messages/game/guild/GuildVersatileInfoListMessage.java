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
import protocol.network.types.game.social.GuildVersatileInformations;

@SuppressWarnings("unused")
public class GuildVersatileInfoListMessage extends NetworkMessage {
	public static final int ProtocolId = 6435;

	private List<GuildVersatileInformations> guilds;

	public List<GuildVersatileInformations> getGuilds() { return this.guilds; }
	public void setGuilds(List<GuildVersatileInformations> guilds) { this.guilds = guilds; };

	public GuildVersatileInfoListMessage(){
	}

	public GuildVersatileInfoListMessage(List<GuildVersatileInformations> guilds){
		this.guilds = guilds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.guilds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.guilds.size()){
				writer.writeShort(GuildVersatileInformations.ProtocolId);
				this.guilds.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.guilds = new ArrayList<GuildVersatileInformations>();
			while( _loc3_ <  _loc2_){
				GuildVersatileInformations _loc15_ = (GuildVersatileInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.guilds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
