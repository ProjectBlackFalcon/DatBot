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
import protocol.network.types.game.paddock.PaddockContentInformations;

@SuppressWarnings("unused")
public class GuildPaddockBoughtMessage extends NetworkMessage {
	public static final int ProtocolId = 5952;

	private PaddockContentInformations paddockInfo;

	public PaddockContentInformations getPaddockInfo() { return this.paddockInfo; };
	public void setPaddockInfo(PaddockContentInformations paddockInfo) { this.paddockInfo = paddockInfo; };

	public GuildPaddockBoughtMessage(){
	}

	public GuildPaddockBoughtMessage(PaddockContentInformations paddockInfo){
		this.paddockInfo = paddockInfo;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			paddockInfo.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.paddockInfo = new PaddockContentInformations();
			this.paddockInfo.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
