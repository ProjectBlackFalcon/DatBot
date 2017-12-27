package protocol.network.messages.game.context.roleplay.paddock;

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
import protocol.network.types.game.paddock.PaddockInstancesInformations;

@SuppressWarnings("unused")
public class PaddockPropertiesMessage extends NetworkMessage {
	public static final int ProtocolId = 5824;

	private PaddockInstancesInformations properties;

	public PaddockInstancesInformations getProperties() { return this.properties; };
	public void setProperties(PaddockInstancesInformations properties) { this.properties = properties; };

	public PaddockPropertiesMessage(){
	}

	public PaddockPropertiesMessage(PaddockInstancesInformations properties){
		this.properties = properties;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			properties.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.properties = new PaddockInstancesInformations();
			this.properties.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
