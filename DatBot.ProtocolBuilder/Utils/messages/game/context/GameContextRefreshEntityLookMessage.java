package protocol.network.messages.game.context;

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
public class GameContextRefreshEntityLookMessage extends NetworkMessage {
	public static final int ProtocolId = 5637;

	private double id;
	private EntityLook look;

	public double getId() { return this.id; };
	public void setId(double id) { this.id = id; };
	public EntityLook getLook() { return this.look; };
	public void setLook(EntityLook look) { this.look = look; };

	public GameContextRefreshEntityLookMessage(){
	}

	public GameContextRefreshEntityLookMessage(double id, EntityLook look){
		this.id = id;
		this.look = look;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.id);
			look.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readDouble();
			this.look = new EntityLook();
			this.look.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
