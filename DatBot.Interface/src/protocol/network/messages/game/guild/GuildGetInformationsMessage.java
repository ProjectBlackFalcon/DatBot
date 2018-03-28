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
public class GuildGetInformationsMessage extends NetworkMessage {
	public static final int ProtocolId = 5550;

	private int infoType;

	public int getInfoType() { return this.infoType; }
	public void setInfoType(int infoType) { this.infoType = infoType; };

	public GuildGetInformationsMessage(){
	}

	public GuildGetInformationsMessage(int infoType){
		this.infoType = infoType;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.infoType);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.infoType = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
