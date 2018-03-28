package protocol.network.types.game.data.items.effects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.data.items.effects.ObjectEffect;

@SuppressWarnings("unused")
public class ObjectEffectDuration extends ObjectEffect {
	public static final int ProtocolId = 75;

	private int days;
	private int hours;
	private int minutes;

	public int getDays() { return this.days; }
	public void setDays(int days) { this.days = days; };
	public int getHours() { return this.hours; }
	public void setHours(int hours) { this.hours = hours; };
	public int getMinutes() { return this.minutes; }
	public void setMinutes(int minutes) { this.minutes = minutes; };

	public ObjectEffectDuration(){
	}

	public ObjectEffectDuration(int days, int hours, int minutes){
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.days);
			writer.writeByte(this.hours);
			writer.writeByte(this.minutes);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.days = reader.readVarShort();
			this.hours = reader.readByte();
			this.minutes = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
