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
import protocol.network.messages.game.actions.fight.AbstractGameActionFightTargetedAbilityMessage;

@SuppressWarnings("unused")
public class GameActionFightCloseCombatMessage extends AbstractGameActionFightTargetedAbilityMessage {
	public static final int ProtocolId = 6116;

	private int weaponGenericId;

	public int getWeaponGenericId() { return this.weaponGenericId; };
	public void setWeaponGenericId(int weaponGenericId) { this.weaponGenericId = weaponGenericId; };

	public GameActionFightCloseCombatMessage(){
	}

	public GameActionFightCloseCombatMessage(int weaponGenericId){
		this.weaponGenericId = weaponGenericId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.weaponGenericId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.weaponGenericId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
