package protocol.network.types.game.actions.fight;

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
public class AbstractFightDispellableEffect extends NetworkMessage {
	public static final int ProtocolId = 206;

	public int uid;
	public double targetId;
	public int turnDuration;
	public int dispelable;
	public int spellId;
	public int effectId;
	public int parentBoostUid;

	public AbstractFightDispellableEffect(){
	}

	public AbstractFightDispellableEffect(int uid, double targetId, int turnDuration, int dispelable, int spellId, int effectId, int parentBoostUid){
		this.uid = uid;
		this.targetId = targetId;
		this.turnDuration = turnDuration;
		this.dispelable = dispelable;
		this.spellId = spellId;
		this.effectId = effectId;
		this.parentBoostUid = parentBoostUid;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.uid);
			writer.writeDouble(this.targetId);
			writer.writeShort(this.turnDuration);
			writer.writeByte(this.dispelable);
			writer.writeVarShort(this.spellId);
			writer.writeVarInt(this.effectId);
			writer.writeVarInt(this.parentBoostUid);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.uid = reader.readVarInt();
			this.targetId = reader.readDouble();
			this.turnDuration = reader.readShort();
			this.dispelable = reader.readByte();
			this.spellId = reader.readVarShort();
			this.effectId = reader.readVarInt();
			this.parentBoostUid = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("uid : " + this.uid);
		//Network.appendDebug("targetId : " + this.targetId);
		//Network.appendDebug("turnDuration : " + this.turnDuration);
		//Network.appendDebug("dispelable : " + this.dispelable);
		//Network.appendDebug("spellId : " + this.spellId);
		//Network.appendDebug("effectId : " + this.effectId);
		//Network.appendDebug("parentBoostUid : " + this.parentBoostUid);
	//}
}
