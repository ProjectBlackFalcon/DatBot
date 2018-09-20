package protocol.network.types.game.character.characteristic;

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
public class CharacterBaseCharacteristic extends NetworkMessage {
	public static final int ProtocolId = 4;

	private int base;
	private int additionnal;
	private int objectsAndMountBonus;
	private int alignGiftBonus;
	private int contextModif;

	public int getBase() { return this.base; };
	public void setBase(int base) { this.base = base; };
	public int getAdditionnal() { return this.additionnal; };
	public void setAdditionnal(int additionnal) { this.additionnal = additionnal; };
	public int getObjectsAndMountBonus() { return this.objectsAndMountBonus; };
	public void setObjectsAndMountBonus(int objectsAndMountBonus) { this.objectsAndMountBonus = objectsAndMountBonus; };
	public int getAlignGiftBonus() { return this.alignGiftBonus; };
	public void setAlignGiftBonus(int alignGiftBonus) { this.alignGiftBonus = alignGiftBonus; };
	public int getContextModif() { return this.contextModif; };
	public void setContextModif(int contextModif) { this.contextModif = contextModif; };
	public int getTotal() { return this.base + this.additionnal + this.objectsAndMountBonus + this.alignGiftBonus + this.contextModif; };

	public CharacterBaseCharacteristic(){
	}

	public CharacterBaseCharacteristic(int base, int additionnal, int objectsAndMountBonus, int alignGiftBonus, int contextModif){
		this.base = base;
		this.additionnal = additionnal;
		this.objectsAndMountBonus = objectsAndMountBonus;
		this.alignGiftBonus = alignGiftBonus;
		this.contextModif = contextModif;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.base);
			writer.writeVarShort(this.additionnal);
			writer.writeVarShort(this.objectsAndMountBonus);
			writer.writeVarShort(this.alignGiftBonus);
			writer.writeVarShort(this.contextModif);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.base = reader.readVarShort();
			this.additionnal = reader.readVarShort();
			this.objectsAndMountBonus = reader.readVarShort();
			this.alignGiftBonus = reader.readVarShort();
			this.contextModif = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
