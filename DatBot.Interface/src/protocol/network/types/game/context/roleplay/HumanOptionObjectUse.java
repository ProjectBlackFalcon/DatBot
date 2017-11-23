package protocol.network.types.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.HumanOption;

@SuppressWarnings("unused")
public class HumanOptionObjectUse extends HumanOption {
	public static final int ProtocolId = 449;

	public int delayTypeId;
	public double delayEndTime;
	public int objectGID;

	public HumanOptionObjectUse(){
	}

	public HumanOptionObjectUse(int delayTypeId, double delayEndTime, int objectGID){
		this.delayTypeId = delayTypeId;
		this.delayEndTime = delayEndTime;
		this.objectGID = objectGID;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.delayTypeId);
			writer.writeDouble(this.delayEndTime);
			writer.writeVarShort(this.objectGID);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.delayTypeId = reader.readByte();
			this.delayEndTime = reader.readDouble();
			this.objectGID = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("delayTypeId : " + this.delayTypeId);
		//Network.appendDebug("delayEndTime : " + this.delayEndTime);
		//Network.appendDebug("objectGID : " + this.objectGID);
	//}
}
