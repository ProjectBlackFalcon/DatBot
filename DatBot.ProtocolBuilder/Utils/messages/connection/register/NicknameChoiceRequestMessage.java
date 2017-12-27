package protocol.network.messages.connection.register;

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

@SuppressWarnings("unused")
public class NicknameChoiceRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5639;

	private String nickname;

	public String getNickname() { return this.nickname; };
	public void setNickname(String nickname) { this.nickname = nickname; };

	public NicknameChoiceRequestMessage(){
	}

	public NicknameChoiceRequestMessage(String nickname){
		this.nickname = nickname;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.nickname);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.nickname = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
