package protocol.network.messages.game.context.roleplay.breach;

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
import protocol.network.types.game.data.items.effects.ObjectEffectInteger;

@SuppressWarnings("unused")
public class BreachBonusMessage extends NetworkMessage {
	public static final int ProtocolId = 6800;

	private ObjectEffectInteger bonus;

	public ObjectEffectInteger getBonus() { return this.bonus; }
	public void setBonus(ObjectEffectInteger bonus) { this.bonus = bonus; };

	public BreachBonusMessage(){
	}

	public BreachBonusMessage(ObjectEffectInteger bonus){
		this.bonus = bonus;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			bonus.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.bonus = new ObjectEffectInteger();
			this.bonus.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
