package protocol.network.messages.game.context.roleplay.delay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.delay.GameRolePlayDelayedActionMessage;

@SuppressWarnings("unused")
public class GameRolePlayDelayedObjectUseMessage extends GameRolePlayDelayedActionMessage {
	public static final int ProtocolId = 6425;

	private int objectGID;

	public int getObjectGID() { return this.objectGID; };
	public void setObjectGID(int objectGID) { this.objectGID = objectGID; };

	public GameRolePlayDelayedObjectUseMessage(){
	}

	public GameRolePlayDelayedObjectUseMessage(int objectGID){
		this.objectGID = objectGID;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.objectGID);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.objectGID = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
