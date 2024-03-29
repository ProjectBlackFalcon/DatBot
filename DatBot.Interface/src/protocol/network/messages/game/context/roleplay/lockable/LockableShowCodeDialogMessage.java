package protocol.network.messages.game.context.roleplay.lockable;

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
public class LockableShowCodeDialogMessage extends NetworkMessage {
	public static final int ProtocolId = 5740;

	private boolean changeOrUse;
	private int codeSize;

	public boolean isChangeOrUse() { return this.changeOrUse; }
	public void setChangeOrUse(boolean changeOrUse) { this.changeOrUse = changeOrUse; };
	public int getCodeSize() { return this.codeSize; }
	public void setCodeSize(int codeSize) { this.codeSize = codeSize; };

	public LockableShowCodeDialogMessage(){
	}

	public LockableShowCodeDialogMessage(boolean changeOrUse, int codeSize){
		this.changeOrUse = changeOrUse;
		this.codeSize = codeSize;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.changeOrUse);
			writer.writeByte(this.codeSize);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.changeOrUse = reader.readBoolean();
			this.codeSize = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
