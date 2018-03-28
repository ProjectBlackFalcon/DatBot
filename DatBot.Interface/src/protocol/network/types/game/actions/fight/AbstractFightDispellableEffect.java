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

	private int uid;
	private double targetId;
	private int turnDuration;
	private int dispelable;
	private int spellId;
	private int effectId;
	private int parentBoostUid;

	public int getUid() { return this.uid; }
	public void setUid(int uid) { this.uid = uid; };
	public double getTargetId() { return this.targetId; }
	public void setTargetId(double targetId) { this.targetId = targetId; };
	public int getTurnDuration() { return this.turnDuration; }
	public void setTurnDuration(int turnDuration) { this.turnDuration = turnDuration; };
	public int getDispelable() { return this.dispelable; }
	public void setDispelable(int dispelable) { this.dispelable = dispelable; };
	public int getSpellId() { return this.spellId; }
	public void setSpellId(int spellId) { this.spellId = spellId; };
	public int getEffectId() { return this.effectId; }
	public void setEffectId(int effectId) { this.effectId = effectId; };
	public int getParentBoostUid() { return this.parentBoostUid; }
	public void setParentBoostUid(int parentBoostUid) { this.parentBoostUid = parentBoostUid; };

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

}
