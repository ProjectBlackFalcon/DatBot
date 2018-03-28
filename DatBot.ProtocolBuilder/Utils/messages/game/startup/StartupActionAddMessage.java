package protocol.network.messages.game.startup;

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
import protocol.network.types.game.startup.StartupActionAddObject;

@SuppressWarnings("unused")
public class StartupActionAddMessage extends NetworkMessage {
	public static final int ProtocolId = 6538;

	private StartupActionAddObject newAction;

	public StartupActionAddObject getNewAction() { return this.newAction; }
	public void setNewAction(StartupActionAddObject newAction) { this.newAction = newAction; };

	public StartupActionAddMessage(){
	}

	public StartupActionAddMessage(StartupActionAddObject newAction){
		this.newAction = newAction;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			newAction.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.newAction = new StartupActionAddObject();
			this.newAction.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
