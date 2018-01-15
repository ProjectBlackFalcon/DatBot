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
import protocol.network.types.game.context.ActorOrientation;

@SuppressWarnings("unused")
public class GameMapChangeOrientationMessage extends NetworkMessage {
	public static final int ProtocolId = 946;

	private ActorOrientation orientation;

	public ActorOrientation getOrientation() { return this.orientation; };
	public void setOrientation(ActorOrientation orientation) { this.orientation = orientation; };

	public GameMapChangeOrientationMessage(){
	}

	public GameMapChangeOrientationMessage(ActorOrientation orientation){
		this.orientation = orientation;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			orientation.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.orientation = new ActorOrientation();
			this.orientation.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}