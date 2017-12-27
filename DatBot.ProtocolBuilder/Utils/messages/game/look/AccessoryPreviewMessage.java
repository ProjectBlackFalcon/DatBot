package protocol.network.messages.game.look;

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
import protocol.network.types.game.look.EntityLook;

@SuppressWarnings("unused")
public class AccessoryPreviewMessage extends NetworkMessage {
	public static final int ProtocolId = 6517;

	private EntityLook look;

	public EntityLook getLook() { return this.look; };
	public void setLook(EntityLook look) { this.look = look; };

	public AccessoryPreviewMessage(){
	}

	public AccessoryPreviewMessage(EntityLook look){
		this.look = look;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			look.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.look = new EntityLook();
			this.look.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
