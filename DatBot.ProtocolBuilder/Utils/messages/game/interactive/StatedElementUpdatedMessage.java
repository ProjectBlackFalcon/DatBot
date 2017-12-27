package protocol.network.messages.game.interactive;

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
import protocol.network.types.game.interactive.StatedElement;

@SuppressWarnings("unused")
public class StatedElementUpdatedMessage extends NetworkMessage {
	public static final int ProtocolId = 5709;

	private StatedElement statedElement;

	public StatedElement getStatedElement() { return this.statedElement; };
	public void setStatedElement(StatedElement statedElement) { this.statedElement = statedElement; };

	public StatedElementUpdatedMessage(){
	}

	public StatedElementUpdatedMessage(StatedElement statedElement){
		this.statedElement = statedElement;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			statedElement.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.statedElement = new StatedElement();
			this.statedElement.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
