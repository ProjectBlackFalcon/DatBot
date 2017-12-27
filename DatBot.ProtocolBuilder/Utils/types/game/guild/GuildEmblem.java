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

	private int symbolShape;
	private int symbolColor;
	private int backgroundShape;
	private int backgroundColor;

	public int getSymbolShape() { return this.symbolShape; };
	public void setSymbolShape(int symbolShape) { this.symbolShape = symbolShape; };
	public int getSymbolColor() { return this.symbolColor; };
	public void setSymbolColor(int symbolColor) { this.symbolColor = symbolColor; };
	public int getBackgroundShape() { return this.backgroundShape; };
	public void setBackgroundShape(int backgroundShape) { this.backgroundShape = backgroundShape; };
	public int getBackgroundColor() { return this.backgroundColor; };
	public void setBackgroundColor(int backgroundColor) { this.backgroundColor = backgroundColor; };

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

}
