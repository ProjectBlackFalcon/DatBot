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
public class ObjectEffectDate extends ObjectEffect {
	public static final int ProtocolId = 72;

	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;

	public int getYear() { return this.year; }
	public void setYear(int year) { this.year = year; };
	public int getMonth() { return this.month; }
	public void setMonth(int month) { this.month = month; };
	public int getDay() { return this.day; }
	public void setDay(int day) { this.day = day; };
	public int getHour() { return this.hour; }
	public void setHour(int hour) { this.hour = hour; };
	public int getMinute() { return this.minute; }
	public void setMinute(int minute) { this.minute = minute; };

	public ObjectEffectDate(){
	}

	public ObjectEffectDate(int year, int month, int day, int hour, int minute){
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.year);
			writer.writeByte(this.month);
			writer.writeByte(this.day);
			writer.writeByte(this.hour);
			writer.writeByte(this.minute);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.year = reader.readVarShort();
			this.month = reader.readByte();
			this.day = reader.readByte();
			this.hour = reader.readByte();
			this.minute = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
