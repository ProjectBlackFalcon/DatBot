package protocol.network.types.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.GameRolePlayActorInformations;
import protocol.network.types.game.context.roleplay.treasureHunt.PortalInformation;

@SuppressWarnings("unused")
public class GameRolePlayPortalInformations extends GameRolePlayActorInformations {
	public static final int ProtocolId = 467;

	private PortalInformation portal;

	public PortalInformation getPortal() { return this.portal; };
	public void setPortal(PortalInformation portal) { this.portal = portal; };

	public GameRolePlayPortalInformations(){
	}

	public GameRolePlayPortalInformations(PortalInformation portal){
		this.portal = portal;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(PortalInformation.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.portal = (PortalInformation) ProtocolTypeManager.getInstance(reader.readShort());
			this.portal.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
