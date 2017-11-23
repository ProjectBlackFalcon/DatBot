package protocol.network.messages.game.actions.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.actions.AbstractGameActionMessage;

@SuppressWarnings("unused")
public class AbstractGameActionFightTargetedAbilityMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 6118;

	public double targetId;
	public int destinationCellId;
	public int critical;
	public boolean silentCast;
	public boolean verboseCast;

	public AbstractGameActionFightTargetedAbilityMessage(){
	}

	public AbstractGameActionFightTargetedAbilityMessage(double targetId, int destinationCellId, int critical, boolean silentCast, boolean verboseCast){
		this.targetId = targetId;
		this.destinationCellId = destinationCellId;
		this.critical = critical;
		this.silentCast = silentCast;
		this.verboseCast = verboseCast;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, silentCast);
			flag = BooleanByteWrapper.SetFlag(1, flag, verboseCast);
			writer.writeByte(flag);
			writer.writeDouble(this.targetId);
			writer.writeShort(this.destinationCellId);
			writer.writeByte(this.critical);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.silentCast = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.verboseCast = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.targetId = reader.readDouble();
			this.destinationCellId = reader.readShort();
			this.critical = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("targetId : " + this.targetId);
		//Network.appendDebug("destinationCellId : " + this.destinationCellId);
		//Network.appendDebug("critical : " + this.critical);
		//Network.appendDebug("silentCast : " + this.silentCast);
		//Network.appendDebug("verboseCast : " + this.verboseCast);
	//}
}
