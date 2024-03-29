package protocol.network.types.game.interactive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.interactive.InteractiveElement;

@SuppressWarnings("unused")
public class InteractiveElementWithAgeBonus extends InteractiveElement {
	public static final int ProtocolId = 398;

	private int ageBonus;

	public int getAgeBonus() { return this.ageBonus; }
	public void setAgeBonus(int ageBonus) { this.ageBonus = ageBonus; };

	public InteractiveElementWithAgeBonus(){
	}

	public InteractiveElementWithAgeBonus(int ageBonus){
		this.ageBonus = ageBonus;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.ageBonus);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.ageBonus = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
