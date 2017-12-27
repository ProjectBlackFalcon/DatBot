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
import protocol.network.types.game.actions.fight.FightTemporaryBoostEffect;

@SuppressWarnings("unused")
public class FightTemporaryBoostWeaponDamagesEffect extends FightTemporaryBoostEffect {
	public static final int ProtocolId = 211;

	private int weaponTypeId;

	public int getWeaponTypeId() { return this.weaponTypeId; };
	public void setWeaponTypeId(int weaponTypeId) { this.weaponTypeId = weaponTypeId; };

	public FightTemporaryBoostWeaponDamagesEffect(){
	}

	public FightTemporaryBoostWeaponDamagesEffect(int weaponTypeId){
		this.weaponTypeId = weaponTypeId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.weaponTypeId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.weaponTypeId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
