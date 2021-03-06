package protocol.network.messages.game.prism;

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
public class PrismsListRegisterMessage extends NetworkMessage {
	public static final int ProtocolId = 6441;

	private int listen;

	public int getListen() { return this.listen; }
	public void setListen(int listen) { this.listen = listen; };

	public PrismsListRegisterMessage(){
	}

	public PrismsListRegisterMessage(int listen){
		this.listen = listen;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.listen);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.listen = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
