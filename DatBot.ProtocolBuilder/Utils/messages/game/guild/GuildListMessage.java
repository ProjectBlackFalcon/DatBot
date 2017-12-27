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
import protocol.network.types.game.context.roleplay.GuildInformations;

@SuppressWarnings("unused")
public class GuildListMessage extends NetworkMessage {
	public static final int ProtocolId = 6413;

	private List<GuildInformations> guilds;

	public List<GuildInformations> getGuilds() { return this.guilds; };
	public void setGuilds(List<GuildInformations> guilds) { this.guilds = guilds; };

	public GuildListMessage(){
	}

	public GuildListMessage(List<GuildInformations> guilds){
		this.guilds = guilds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.guilds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.guilds.size()){
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
			this.guilds = new ArrayList<GuildInformations>();
			while( _loc3_ <  _loc2_){
				GuildInformations _loc15_ = new GuildInformations();
				_loc15_.Deserialize(reader);
				this.guilds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
