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
import protocol.network.types.game.actions.fight.AbstractFightDispellableEffect;

@SuppressWarnings("unused")
public class GameActionFightDispellableEffectMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 6070;

	private AbstractFightDispellableEffect effect;

	public AbstractFightDispellableEffect getEffect() { return this.effect; };
	public void setEffect(AbstractFightDispellableEffect effect) { this.effect = effect; };

	public GameActionFightDispellableEffectMessage(){
	}

	public GameActionFightDispellableEffectMessage(AbstractFightDispellableEffect effect){
		this.effect = effect;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(AbstractFightDispellableEffect.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.effect = (AbstractFightDispellableEffect) ProtocolTypeManager.getInstance(reader.readShort());
			this.effect.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
