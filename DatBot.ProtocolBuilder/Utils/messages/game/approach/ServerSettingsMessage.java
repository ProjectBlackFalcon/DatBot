package protocol.network.messages.game.approach;

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
public class ServerSettingsMessage extends NetworkMessage {
	public static final int ProtocolId = 6340;

	private String lang;
	private int community;
	private int gameType;
	private int arenaLeaveBanTime;

	public String getLang() { return this.lang; };
	public void setLang(String lang) { this.lang = lang; };
	public int getCommunity() { return this.community; };
	public void setCommunity(int community) { this.community = community; };
	public int getGameType() { return this.gameType; };
	public void setGameType(int gameType) { this.gameType = gameType; };
	public int getArenaLeaveBanTime() { return this.arenaLeaveBanTime; };
	public void setArenaLeaveBanTime(int arenaLeaveBanTime) { this.arenaLeaveBanTime = arenaLeaveBanTime; };

	public ServerSettingsMessage(){
	}

	public ServerSettingsMessage(String lang, int community, int gameType, int arenaLeaveBanTime){
		this.lang = lang;
		this.community = community;
		this.gameType = gameType;
		this.arenaLeaveBanTime = arenaLeaveBanTime;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.lang);
			writer.writeByte(this.community);
			writer.writeByte(this.gameType);
			writer.writeVarShort(this.arenaLeaveBanTime);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.lang = reader.readUTF();
			this.community = reader.readByte();
			this.gameType = reader.readByte();
			this.arenaLeaveBanTime = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
