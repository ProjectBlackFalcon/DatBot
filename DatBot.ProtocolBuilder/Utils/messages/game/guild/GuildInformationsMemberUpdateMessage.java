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
public class GuildInformationsMemberUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 5597;

	private GuildMember member;

	public GuildMember getMember() { return this.member; };
	public void setMember(GuildMember member) { this.member = member; };

	public GuildInformationsMemberUpdateMessage(){
	}

	public GuildInformationsMemberUpdateMessage(GuildMember member){
		this.member = member;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			member.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.member = new GuildMember();
			this.member.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
