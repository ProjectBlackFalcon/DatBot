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

	private boolean isSecret;
	private boolean isRestrictedToPartyOnly;
	private boolean isClosed;
	private boolean isAskingForHelp;

	public boolean isIsSecret() { return this.isSecret; }
	public void setIsSecret(boolean isSecret) { this.isSecret = isSecret; };
	public boolean isIsRestrictedToPartyOnly() { return this.isRestrictedToPartyOnly; }
	public void setIsRestrictedToPartyOnly(boolean isRestrictedToPartyOnly) { this.isRestrictedToPartyOnly = isRestrictedToPartyOnly; };
	public boolean isIsClosed() { return this.isClosed; }
	public void setIsClosed(boolean isClosed) { this.isClosed = isClosed; };
	public boolean isIsAskingForHelp() { return this.isAskingForHelp; }
	public void setIsAskingForHelp(boolean isAskingForHelp) { this.isAskingForHelp = isAskingForHelp; };

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

}
