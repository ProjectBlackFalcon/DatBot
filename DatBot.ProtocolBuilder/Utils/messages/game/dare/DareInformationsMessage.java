package protocol.network.messages.game.dare;

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
import protocol.network.types.game.dare.DareInformations;
import protocol.network.types.game.dare.DareVersatileInformations;

@SuppressWarnings("unused")
public class DareInformationsMessage extends NetworkMessage {
	public static final int ProtocolId = 6656;

	private DareInformations dareFixedInfos;
	private DareVersatileInformations dareVersatilesInfos;

	public DareInformations getDareFixedInfos() { return this.dareFixedInfos; };
	public void setDareFixedInfos(DareInformations dareFixedInfos) { this.dareFixedInfos = dareFixedInfos; };
	public DareVersatileInformations getDareVersatilesInfos() { return this.dareVersatilesInfos; };
	public void setDareVersatilesInfos(DareVersatileInformations dareVersatilesInfos) { this.dareVersatilesInfos = dareVersatilesInfos; };

	public DareInformationsMessage(){
	}

	public DareInformationsMessage(DareInformations dareFixedInfos, DareVersatileInformations dareVersatilesInfos){
		this.dareFixedInfos = dareFixedInfos;
		this.dareVersatilesInfos = dareVersatilesInfos;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			dareFixedInfos.Serialize(writer);
			dareVersatilesInfos.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.dareFixedInfos = new DareInformations();
			this.dareFixedInfos.Deserialize(reader);
			this.dareVersatilesInfos = new DareVersatileInformations();
			this.dareVersatilesInfos.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
