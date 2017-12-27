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
public class GuildModificationStartedMessage extends NetworkMessage {
	public static final int ProtocolId = 6324;

	private boolean canChangeName;
	private boolean canChangeEmblem;

	public boolean isCanChangeName() { return this.canChangeName; };
	public void setCanChangeName(boolean canChangeName) { this.canChangeName = canChangeName; };
	public boolean isCanChangeEmblem() { return this.canChangeEmblem; };
	public void setCanChangeEmblem(boolean canChangeEmblem) { this.canChangeEmblem = canChangeEmblem; };

	public GuildModificationStartedMessage(){
	}

	public GuildModificationStartedMessage(boolean canChangeName, boolean canChangeEmblem){
		this.canChangeName = canChangeName;
		this.canChangeEmblem = canChangeEmblem;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, canChangeName);
			flag = BooleanByteWrapper.SetFlag(1, flag, canChangeEmblem);
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
			this.canChangeName = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.canChangeEmblem = BooleanByteWrapper.GetFlag(flag, (byte) 1);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
