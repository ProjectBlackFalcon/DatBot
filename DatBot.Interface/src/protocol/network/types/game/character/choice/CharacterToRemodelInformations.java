package protocol.network.types.game.character.choice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.character.choice.CharacterRemodelingInformation;

@SuppressWarnings("unused")
public class CharacterToRemodelInformations extends CharacterRemodelingInformation {
	public static final int ProtocolId = 477;

	private int possibleChangeMask;
	private int mandatoryChangeMask;

	public int getPossibleChangeMask() { return this.possibleChangeMask; }
	public void setPossibleChangeMask(int possibleChangeMask) { this.possibleChangeMask = possibleChangeMask; };
	public int getMandatoryChangeMask() { return this.mandatoryChangeMask; }
	public void setMandatoryChangeMask(int mandatoryChangeMask) { this.mandatoryChangeMask = mandatoryChangeMask; };

	public CharacterToRemodelInformations(){
	}

	public CharacterToRemodelInformations(int possibleChangeMask, int mandatoryChangeMask){
		this.possibleChangeMask = possibleChangeMask;
		this.mandatoryChangeMask = mandatoryChangeMask;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.possibleChangeMask);
			writer.writeByte(this.mandatoryChangeMask);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.possibleChangeMask = reader.readByte();
			this.mandatoryChangeMask = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
