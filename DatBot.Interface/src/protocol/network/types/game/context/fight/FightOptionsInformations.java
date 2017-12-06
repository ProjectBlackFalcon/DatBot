package protocol.network.types.game.context.fight;

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
public class FightOptionsInformations extends NetworkMessage {
	public static final int ProtocolId = 20;

	public boolean isSecret;
	public boolean isRestrictedToPartyOnly;
	public boolean isClosed;
	public boolean isAskingForHelp;

	public FightOptionsInformations(){
	}

	public FightOptionsInformations(boolean isSecret, boolean isRestrictedToPartyOnly, boolean isClosed, boolean isAskingForHelp){
		this.isSecret = isSecret;
		this.isRestrictedToPartyOnly = isRestrictedToPartyOnly;
		this.isClosed = isClosed;
		this.isAskingForHelp = isAskingForHelp;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, isSecret);
			flag = BooleanByteWrapper.SetFlag(1, flag, isRestrictedToPartyOnly);
			flag = BooleanByteWrapper.SetFlag(2, flag, isClosed);
			flag = BooleanByteWrapper.SetFlag(3, flag, isAskingForHelp);
			writer.writeByte(flag);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.isSecret = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.isRestrictedToPartyOnly = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.isClosed = BooleanByteWrapper.GetFlag(flag, (byte) 2);
			this.isAskingForHelp = BooleanByteWrapper.GetFlag(flag, (byte) 3);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("isSecret : " + this.isSecret);
		//Network.appendDebug("isRestrictedToPartyOnly : " + this.isRestrictedToPartyOnly);
		//Network.appendDebug("isClosed : " + this.isClosed);
		//Network.appendDebug("isAskingForHelp : " + this.isAskingForHelp);
	//}
}
