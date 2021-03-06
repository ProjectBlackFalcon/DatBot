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

@SuppressWarnings("unused")
public class GuildCharacsUpgradeRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5706;

	private int charaTypeTarget;

	public int getCharaTypeTarget() { return this.charaTypeTarget; }
	public void setCharaTypeTarget(int charaTypeTarget) { this.charaTypeTarget = charaTypeTarget; };

	public GuildCharacsUpgradeRequestMessage(){
	}

	public GuildCharacsUpgradeRequestMessage(int charaTypeTarget){
		this.charaTypeTarget = charaTypeTarget;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.charaTypeTarget);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.charaTypeTarget = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
