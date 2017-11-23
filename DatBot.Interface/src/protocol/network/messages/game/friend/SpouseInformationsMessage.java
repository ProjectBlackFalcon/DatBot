package protocol.network.messages.game.friend;

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
import protocol.network.types.game.friend.FriendSpouseInformations;

@SuppressWarnings("unused")
public class SpouseInformationsMessage extends NetworkMessage {
	public static final int ProtocolId = 6356;

	public FriendSpouseInformations spouse;

	public SpouseInformationsMessage(){
	}

	public SpouseInformationsMessage(FriendSpouseInformations spouse){
		this.spouse = spouse;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(FriendSpouseInformations.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.spouse = (FriendSpouseInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.spouse.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("spouse : " + this.spouse);
	//}
}
