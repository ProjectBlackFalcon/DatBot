package protocol.network.messages.game.context.mount;

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
import protocol.network.types.game.paddock.PaddockItem;

@SuppressWarnings("unused")
public class GameDataPaddockObjectAddMessage extends NetworkMessage {
	public static final int ProtocolId = 5990;

	private PaddockItem paddockItemDescription;

	public PaddockItem getPaddockItemDescription() { return this.paddockItemDescription; };
	public void setPaddockItemDescription(PaddockItem paddockItemDescription) { this.paddockItemDescription = paddockItemDescription; };

	public GameDataPaddockObjectAddMessage(){
	}

	public GameDataPaddockObjectAddMessage(PaddockItem paddockItemDescription){
		this.paddockItemDescription = paddockItemDescription;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			paddockItemDescription.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.paddockItemDescription = new PaddockItem();
			this.paddockItemDescription.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
