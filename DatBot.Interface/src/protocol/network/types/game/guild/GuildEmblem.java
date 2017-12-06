package protocol.network.types.game.guild;

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
public class GuildEmblem extends NetworkMessage {
	public static final int ProtocolId = 87;

	public int symbolShape;
	public int symbolColor;
	public int backgroundShape;
	public int backgroundColor;

	public GuildEmblem(){
	}

	public GuildEmblem(int symbolShape, int symbolColor, int backgroundShape, int backgroundColor){
		this.symbolShape = symbolShape;
		this.symbolColor = symbolColor;
		this.backgroundShape = backgroundShape;
		this.backgroundColor = backgroundColor;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.symbolShape);
			writer.writeInt(this.symbolColor);
			writer.writeByte(this.backgroundShape);
			writer.writeInt(this.backgroundColor);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.symbolShape = reader.readVarShort();
			this.symbolColor = reader.readInt();
			this.backgroundShape = reader.readByte();
			this.backgroundColor = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("symbolShape : " + this.symbolShape);
		//Network.appendDebug("symbolColor : " + this.symbolColor);
		//Network.appendDebug("backgroundShape : " + this.backgroundShape);
		//Network.appendDebug("backgroundColor : " + this.backgroundColor);
	//}
}
