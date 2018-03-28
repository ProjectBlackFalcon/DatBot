package protocol.network.messages.game.dialog;

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
public class PauseDialogMessage extends NetworkMessage {
	public static final int ProtocolId = 6012;

	private int dialogType;

	public int getDialogType() { return this.dialogType; }
	public void setDialogType(int dialogType) { this.dialogType = dialogType; };

	public PauseDialogMessage(){
	}

	public PauseDialogMessage(int dialogType){
		this.dialogType = dialogType;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.dialogType);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.dialogType = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
