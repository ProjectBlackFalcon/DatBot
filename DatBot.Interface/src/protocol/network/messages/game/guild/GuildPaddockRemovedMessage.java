package protocol.network.messages.game.guild;

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

@SuppressWarnings("unused")
public class GuildPaddockRemovedMessage extends NetworkMessage {
	public static final int ProtocolId = 5955;

	private double paddockId;

	public double getPaddockId() { return this.paddockId; }
	public void setPaddockId(double paddockId) { this.paddockId = paddockId; };

	public GuildPaddockRemovedMessage(){
	}

	public GuildPaddockRemovedMessage(double paddockId){
		this.paddockId = paddockId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.paddockId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.paddockId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
