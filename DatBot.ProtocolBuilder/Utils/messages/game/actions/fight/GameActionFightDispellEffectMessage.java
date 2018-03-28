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
import protocol.network.messages.game.actions.fight.GameActionFightDispellMessage;

@SuppressWarnings("unused")
public class GameActionFightDispellEffectMessage extends GameActionFightDispellMessage {
	public static final int ProtocolId = 6113;

	private int boostUID;

	public int getBoostUID() { return this.boostUID; }
	public void setBoostUID(int boostUID) { this.boostUID = boostUID; };

	public GameActionFightDispellEffectMessage(){
	}

	public GameActionFightDispellEffectMessage(int boostUID){
		this.boostUID = boostUID;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.boostUID);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.boostUID = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
