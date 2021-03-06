package protocol.network.types.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.MonsterInGroupLightInformations;
import protocol.network.types.game.look.EntityLook;

@SuppressWarnings("unused")
public class MonsterInGroupInformations extends MonsterInGroupLightInformations {
	public static final int ProtocolId = 144;

	private EntityLook look;

	public EntityLook getLook() { return this.look; }
	public void setLook(EntityLook look) { this.look = look; };

	public MonsterInGroupInformations(){
	}

	public MonsterInGroupInformations(EntityLook look){
		this.look = look;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			look.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.look = new EntityLook();
			this.look.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
