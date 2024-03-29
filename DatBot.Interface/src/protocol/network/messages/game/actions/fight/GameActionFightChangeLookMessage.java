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
import protocol.network.types.game.look.EntityLook;

@SuppressWarnings("unused")
public class GameActionFightChangeLookMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 5532;

	private double targetId;
	private EntityLook entityLook;

	public double getTargetId() { return this.targetId; }
	public void setTargetId(double targetId) { this.targetId = targetId; };
	public EntityLook getEntityLook() { return this.entityLook; }
	public void setEntityLook(EntityLook entityLook) { this.entityLook = entityLook; };

	public GameActionFightChangeLookMessage(){
	}

	public GameActionFightChangeLookMessage(double targetId, EntityLook entityLook){
		this.targetId = targetId;
		this.entityLook = entityLook;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.targetId);
			entityLook.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.targetId = reader.readDouble();
			this.entityLook = new EntityLook();
			this.entityLook.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
