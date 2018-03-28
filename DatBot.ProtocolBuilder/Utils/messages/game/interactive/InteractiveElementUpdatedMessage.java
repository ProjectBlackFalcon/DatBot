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
import protocol.network.types.game.interactive.InteractiveElement;

@SuppressWarnings("unused")
public class InteractiveElementUpdatedMessage extends NetworkMessage {
	public static final int ProtocolId = 5708;

	private InteractiveElement interactiveElement;

	public InteractiveElement getInteractiveElement() { return this.interactiveElement; }
	public void setInteractiveElement(InteractiveElement interactiveElement) { this.interactiveElement = interactiveElement; };

	public InteractiveElementUpdatedMessage(){
	}

	public InteractiveElementUpdatedMessage(InteractiveElement interactiveElement){
		this.interactiveElement = interactiveElement;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			interactiveElement.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.interactiveElement = new InteractiveElement();
			this.interactiveElement.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
