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
import protocol.network.types.game.guild.GuildMember;

@SuppressWarnings("unused")
public class GuildInformationsMembersMessage extends NetworkMessage {
	public static final int ProtocolId = 5558;

	public List<GuildMember> members;

	public GuildInformationsMembersMessage(){
	}

	public GuildInformationsMembersMessage(List<GuildMember> members){
		this.members = members;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
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
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.members = new ArrayList<GuildMember>();
			while( _loc3_ <  _loc2_){
				GuildMember _loc15_ = new GuildMember();
				_loc15_.Deserialize(reader);
				this.members.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(GuildMember a : members) {
			//Network.appendDebug("members : " + a);
		//}
	//}
}
