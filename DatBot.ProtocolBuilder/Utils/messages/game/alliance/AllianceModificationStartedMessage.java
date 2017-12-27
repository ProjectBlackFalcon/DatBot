package protocol.network.messages.game.alliance;

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
public class AllianceModificationStartedMessage extends NetworkMessage {
	public static final int ProtocolId = 6444;

	private boolean canChangeName;
	private boolean canChangeTag;
	private boolean canChangeEmblem;

	public boolean isCanChangeName() { return this.canChangeName; };
	public void setCanChangeName(boolean canChangeName) { this.canChangeName = canChangeName; };
	public boolean isCanChangeTag() { return this.canChangeTag; };
	public void setCanChangeTag(boolean canChangeTag) { this.canChangeTag = canChangeTag; };
	public boolean isCanChangeEmblem() { return this.canChangeEmblem; };
	public void setCanChangeEmblem(boolean canChangeEmblem) { this.canChangeEmblem = canChangeEmblem; };

	public AllianceModificationStartedMessage(){
	}

	public AllianceModificationStartedMessage(boolean canChangeName, boolean canChangeTag, boolean canChangeEmblem){
		this.canChangeName = canChangeName;
		this.canChangeTag = canChangeTag;
		this.canChangeEmblem = canChangeEmblem;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, canChangeName);
			flag = BooleanByteWrapper.SetFlag(1, flag, canChangeTag);
			flag = BooleanByteWrapper.SetFlag(2, flag, canChangeEmblem);
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
			this.canChangeTag = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.canChangeEmblem = BooleanByteWrapper.GetFlag(flag, (byte) 2);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
