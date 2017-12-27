package protocol.network.types.game.action.fight;

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
import protocol.network.types.game.actions.fight.AbstractFightDispellableEffect;

@SuppressWarnings("unused")
public class FightDispellableEffectExtendedInformations extends NetworkMessage {
	public static final int ProtocolId = 208;

	private int actionId;
	private double sourceId;
	private AbstractFightDispellableEffect effect;

	public int getActionId() { return this.actionId; };
	public void setActionId(int actionId) { this.actionId = actionId; };
	public double getSourceId() { return this.sourceId; };
	public void setSourceId(double sourceId) { this.sourceId = sourceId; };
	public AbstractFightDispellableEffect getEffect() { return this.effect; };
	public void setEffect(AbstractFightDispellableEffect effect) { this.effect = effect; };

	public FightDispellableEffectExtendedInformations(){
	}

	public FightDispellableEffectExtendedInformations(int actionId, double sourceId, AbstractFightDispellableEffect effect){
		this.actionId = actionId;
		this.sourceId = sourceId;
		this.effect = effect;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.actionId);
			writer.writeDouble(this.sourceId);
			writer.writeShort(AbstractFightDispellableEffect.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.actionId = reader.readVarShort();
			this.sourceId = reader.readDouble();
			this.effect = (AbstractFightDispellableEffect) ProtocolTypeManager.getInstance(reader.readShort());
			this.effect.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
